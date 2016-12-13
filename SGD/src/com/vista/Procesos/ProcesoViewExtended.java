package com.vista.Procesos;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.ProcesoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class ProcesoViewExtended extends ProcesoView implements IBusqueda{
	
	private BeanFieldGroup<ProcesoVO> fieldGroup;
	private ProcesoControlador controlador;
	private String operacion;
	private ProcesosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	UsuarioPermisosVO permisoAux;
	int codigoInsert;
	CotizacionVO cotizacion = new CotizacionVO();
	Double cotizacionVenta = null, importeMoneda = null;
	MonedaVO monedaNacional = new MonedaVO();
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
	
	public ProcesoViewExtended(String opera, ProcesosPanelExtended main){
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		this.operacion = opera;
		this.mainView = main;
		
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
				
			try {
				
				/*Validamos los campos antes de invocar al controlador*/
				if(this.fieldsValidos())
				{
					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_PROCESOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
									
					ProcesoVO procesoVO = new ProcesoVO();		
					
					procesoVO.setUsuarioMod(this.permisos.getUsuario());
					procesoVO.setOperacion(operacion);
					
					//Cliente
					procesoVO.setCodCliente(codCliente.getValue().trim());
					procesoVO.setNomCliente(nomCliente.getValue().trim());
					//procesoVO.setCodMoneda(codMoneda.getValue().trim());
					
					//Moneda
					if(this.comboMoneda.getValue() != null){
						MonedaVO auxMoneda = new MonedaVO();
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						procesoVO.setCodMoneda(auxMoneda.getCodMoneda());
						procesoVO.setDescMoneda(auxMoneda.getDescripcion());
						procesoVO.setSimboloMoneda(auxMoneda.getSimbolo());
					}
					else{
						procesoVO.setCodMoneda("");
						procesoVO.setDescMoneda("");
						procesoVO.setSimboloMoneda("");
					}
					
					//Documento
					if(this.comboDocumento.getValue() != null){
						DocumentoAduaneroVO auxDocumento = new DocumentoAduaneroVO();
						auxDocumento = (DocumentoAduaneroVO) this.comboDocumento.getValue();
						procesoVO.setCodDocum(auxDocumento.getcodDocumento());
						procesoVO.setNomDocum(auxDocumento.getdescripcion());
					}
					else{
						procesoVO.setCodDocum("");
						procesoVO.setNomDocum("");
					}
					
					
					procesoVO.setObservaciones(obseAux.getValue());
					
					if(impMo.getValue() != "" && impMo.getValue() != null){
						procesoVO.setImpMo((double) impMo.getConvertedValue());
					}
					else{
						procesoVO.setImpMo(0);
					}
					
					if(impMn.getValue() != "" && impMn.getValue() != null){
						procesoVO.setImpMn((double) impMn.getConvertedValue());
					}
					else{
						procesoVO.setImpMn(0);
					}
					
					if(tcMov.getValue() != "" && tcMov.getValue() != null){
						procesoVO.setTcMov(Double.valueOf(tcMov.getConvertedValue().toString()));
					}
					else{
						procesoVO.setTcMov(0);
					}
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						procesoVO.setCodigo(0);
					}
					else{
						procesoVO.setCodigo((Integer) codigo.getConvertedValue());
					}
					
					procesoVO.setFecha(new java.sql.Timestamp(fecha.getValue().getTime()));
					
					if(nroMega.getValue() != "" && nroMega.getValue() != null){
						procesoVO.setNroMega((Integer) nroMega.getConvertedValue());
					}
					else{
						procesoVO.setNroMega(0);
					}
					
					if(nroDocum.getValue() != "" && nroDocum.getValue() != null){
						procesoVO.setNroDocum(nroDocum.getValue());
					}
					else{
						procesoVO.setNroDocum("0");
					}
						
					if(Kilos.getValue() != "" && Kilos.getValue() != null){
						procesoVO.setKilos((double) Kilos.getConvertedValue());
					}
					else{
						procesoVO.setKilos(0);
					}
					
					if(fecDocum.getValue() != null){
						procesoVO.setFecDocum(new java.sql.Timestamp(fecDocum.getValue().getTime()));
					}
					else{
						procesoVO.setFecDocum(null);
					}
					procesoVO.setCarpeta(carpeta.getValue().trim());
					
					if(fecCruce.getValue() != null){
						procesoVO.setFecCruce(new java.sql.Timestamp(fecCruce.getValue().getTime()));
					}
					else{
						procesoVO.setFecCruce(null);
					}
					
					procesoVO.setMarca(marca.getValue().trim());
					procesoVO.setMedio(medio.getValue().trim());
					procesoVO.setDescripcion(descripcion.getValue().trim());

					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						codigoInsert = this.controlador.insertarProceso(procesoVO, permisoAux);
						procesoVO.setCodigo(codigoInsert);
						
						this.mainView.actulaizarGrilla(procesoVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el proceso");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarProceso(procesoVO, permisoAux);
						this.mainView.actulaizarGrilla(procesoVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado el proceso");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
			} 
			catch (ConexionException | ModificandoProcesoException | ExisteProcesoException | 
					 InicializandoException | IngresandoProcesoException | NoExisteProcesoException |
					 ErrorInesperadoException| ObteniendoPermisosException| NoTienePermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
				
		});
		
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
			try {
			
				/*Inicializamos el Form en modo Edicion*/
				this.iniFormEditar();
				cotizacionVenta = Double.valueOf(tcMov.getConvertedValue().toString());
				importeMoneda = (Double) impMo.getConvertedValue();
				
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
		
		this.eliminar.addClickListener(click -> {
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PROCESOS,
							VariablesPermisos.OPERACION_BORRAR);
			
			try {
				controlador.eliminarProceso((Integer)codigo.getConvertedValue(), permisoAux);
				this.mainView.actuilzarGrillaEliminado((Integer)codigo.getConvertedValue());
				Mensajes.mostrarMensajeOK("Se ha eliminado el proceso");
				main.cerrarVentana();
				
			} catch (ConexionException | NoExisteProcesoException | ExisteProcesoException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | EliminandoProcesoException e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.btnBuscarCliente.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ClienteVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PROCESOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstClientes = this.controlador.getClientes(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
					 ObteniendoClientesException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (ClienteVO i: lstClientes) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("65%", "65%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		/*Inicalizamos listener para boton de Editar*/
		this.observaciones.addClickListener(click -> {
			
			ProcesoObservacionesViewExtended form = new ProcesoObservacionesViewExtended(this, obseAux.getValue(), this.operacion);
			try {
			
				sub = new MySub("65%", "35%" );
				sub.setModal(true);
				sub.center();
				sub.setModal(true);
				sub.setVista(form);
				sub.center();
				sub.setDraggable(true);
				UI.getCurrent().addWindow(sub);
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		comboMoneda.addValueChangeListener(new Property.ValueChangeListener(){
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				
				if("ProgramaticallyChanged".equals(comboMoneda.getData())){
					comboMoneda.setData(null);
		             return;
		         }
				
				MonedaVO auxMoneda = new MonedaVO();
				
				if(operacion != Variables.OPERACION_LECTURA){
					
					auxMoneda = (MonedaVO) comboMoneda.getValue();
					Date fechaproces = convertFromJAVADateToSQLDate(fecha.getValue());
					
					try {
						
						if(auxMoneda.getCodMoneda() != null && !auxMoneda.isNacional()){
							cotizacion = controlador.getCotizacion(permisoAux, fechaproces, auxMoneda.getCodMoneda());
							if(cotizacion.getCotizacionVenta() != 0 && !auxMoneda.isNacional()){
								cotizacionVenta = cotizacion.getCotizacionVenta();
								tcMov.setEnabled(true);
								calculos();
							}
							else{
								Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
								comboMoneda.setValue(monedaNacional);
								return;
							}
						}
						else if(auxMoneda.getCodMoneda() != null){
							cotizacionVenta = (double) 1;
							tcMov.setEnabled(false);
							tcMov.setConvertedValue(1);
							calculos();
						}
					}
					catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
							| InicializandoException | NoTienePermisosException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(fecha != null){
	   					
	   					for (MonedaVO monedaVO : lstMonedas) {
	   						
	   						monedaVO = seteaCotizaciones(monedaVO);
	   					}
	   					
	   				}
				}
			}
		});
		
		this.impMo.addValueChangeListener(new Property.ValueChangeListener() {
			
		    public void valueChange(ValueChangeEvent event) {
		    	
		    	if("ProgramaticallyChanged".equals(impMo.getData())){
		    		impMo.setData(null);
		            return;
		         }
		    	
		        String value = (String) event.getProperty().getValue();
		        
		        if(value != ""){
		        	
		        	try {
		        		impMo.setValue(value);
		        		importeMoneda = (Double) impMo.getConvertedValue();
					} catch (Exception e) {
						return;
						// TODO: handle exception
						//Mensajes.mostrarMensajeError("Formato de número incorrecto");
					}
		        	
		        	Double truncatedDouble = new BigDecimal(importeMoneda)
						    .setScale(2, BigDecimal.ROUND_HALF_UP)
						    .doubleValue();
					
		        	importeMoneda = truncatedDouble;
		        	
		        	if(operacion != Variables.OPERACION_LECTURA){

			        	calculos();
			        }
		    	}
		    }
		});
		
		this.tcMov.addValueChangeListener(new Property.ValueChangeListener() {
			
		    public void valueChange(ValueChangeEvent event) {
		    	
		    	 if("ProgramaticallyChanged".equals(tcMov.getData())){
		    		 tcMov.setData(null);
		             return;
		         }
		    	
		        String value = (String) event.getProperty().getValue();
		        
		        if(value != ""){
		        	
		        	try {
		        		tcMov.setValue(value);
		        		cotizacionVenta = Double.valueOf(tcMov.getConvertedValue().toString());
					} catch (Exception e) {
						return;
						// TODO: handle exception
						//Mensajes.mostrarMensajeError("Formato de número incorrecto");
					}
		        	
		        	
		        	Double truncatedDouble = new BigDecimal(cotizacionVenta)
						    .setScale(3, BigDecimal.ROUND_HALF_UP)
						    .doubleValue();
					
		        	cotizacionVenta = truncatedDouble;
		        	
		        	if(operacion != Variables.OPERACION_LECTURA){

			        	calculos();
			        }
		        	
		    	}
		    }
		});
	}
	
	public  void inicializarForm(){
		
		this.controlador = new ProcesoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ProcesoVO>(ProcesoVO.class);
		
		//inicializar los valores de los combos impuesto y tipo de rubro
		inicializarComboMoneda(null);
		inicializarComboDocuemnto(null);
		
		this.inicializarCampos();
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))	{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
		} 
	}
	
	/**
	 * Seteamos las validaciones del Formulario
	 * pasamos un booleano para activarlos y desactivarlos
	 * EN modo LEER: las deshabilitamos (para que no aparezcan los asteriscos, etc)
	 * EN modo NUEVO: las habilitamos
	 * EN modo EDITAR: las habilitamos
	 *
	 */
	private void setearValidaciones(boolean setear){
		
		this.codCliente.setRequired(setear);
		this.codCliente.setRequiredError("Es requerido");
		
		this.fecha.setRequired(setear);
		this.fecha.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item ProcesoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ProcesoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		ProcesoVO proceso = new ProcesoVO();
		proceso = fieldGroup.getItemDataSource().getBean();
		this.obseAux.setValue(proceso.getObservaciones()); 
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(proceso.getFechaMod());
		
		
		auditoria.setDescription(
				"Usuario: " + proceso.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operación: " + proceso.getOperacion());
		
		this.inicializarComboMoneda(proceso.getCodMoneda());
		this.inicializarComboDocuemnto(proceso.getCodDocum());
		
		
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
				
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoBorrar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_BORRAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		if(permisoBorrar)
			this.enableBotonEliminar();
		
		/*Deshabilitamos botn aceptar*/
		this.disableBotonAceptar();
		
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		this.disableBotonEliminar();
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			
			this.disableBotonLectura();

			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
		}
		else{
			
			/*Mostramos mensaje Sin permisos para operacion*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		this.operacion = Variables.OPERACION_NUEVO;
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.enableBotonEliminar();
		this.disableBotonLectura();
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
		
		this.enableCombos();
	}
	
	private void enableCombos(){
		this.comboMoneda.setEnabled(true);
		this.comboDocumento.setEnabled(true);
		
	}
	
	/**
	 * Dejamos setear los texFields correspondientes, 
	 *  
	 * Solamente aquellos campos posibles de editar
	 * EJ: el codigo no se deja editar
	 *
	 */
	private void setearFieldsEditar()
	{
		this.fecha.setReadOnly(false);
		this.nroMega.setReadOnly(false);
		this.nroDocum.setReadOnly(false);
		this.fecDocum.setReadOnly(false);
		this.carpeta.setReadOnly(false);
		this.impMn.setReadOnly(false);
		this.impMn.setEnabled(false);
		this.impMo.setReadOnly(false);
		this.tcMov.setReadOnly(false);
		this.Kilos.setReadOnly(false);
		this.fecCruce.setReadOnly(false);
		this.marca.setReadOnly(false);
		this.medio.setReadOnly(false);
		this.descripcion.setReadOnly(false);
		this.codCliente.setReadOnly(false);
		this.codCliente.setEnabled(false);
		this.nomCliente.setReadOnly(false);
		this.nomCliente.setEnabled(false);
		
		this.comboMoneda.setEnabled(true);
		this.comboDocumento.setEnabled(true);
	}
	
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
		
		
	}
	
	/**
	 * Habilitamos el boton editar 
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton aceptar
	 *
	 */
	private void disableBotonAceptar()
	{
		this.aceptar.setEnabled(false);
		this.aceptar.setVisible(false);
		
		this.btnBuscarCliente.setEnabled(false);
		this.btnBuscarCliente.setVisible(false);
	}
	
	private void disableBotonEliminar()
	{
		this.eliminar.setEnabled(false);
		this.eliminar.setVisible(false);
		this.botones.setWidth("187px");
		
		
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
		this.btnBuscarCliente.setEnabled(true);
		this.btnBuscarCliente.setVisible(true);
	}
	
	private void enableBotonEliminar()
	{
		if(operacion != Variables.OPERACION_NUEVO){
			this.eliminar.setEnabled(true);
			this.eliminar.setVisible(true);
			this.botones.setWidth("270px");
		}
		else{
			disableBotonEliminar();
		}
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codigo.setReadOnly(true);
		
		this.fecha.setReadOnly(setear);
		this.nroMega.setReadOnly(setear);
		this.nroDocum.setReadOnly(setear);
		this.fecDocum.setReadOnly(setear);
		this.carpeta.setReadOnly(setear);
		this.impMn.setReadOnly(setear);
		this.impMn.setEnabled(false);
		this.impMo.setReadOnly(setear);
		this.tcMov.setReadOnly(setear);
		this.Kilos.setReadOnly(setear);
		this.fecCruce.setReadOnly(setear);
		this.marca.setReadOnly(setear);
		this.medio.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);

		this.comboMoneda.setEnabled(false);
		this.comboDocumento.setEnabled(false);
		this.codCliente.setEnabled(false);
		this.nomCliente.setEnabled(false);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
		
        this.carpeta.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 0, 20, true));
        
        this.marca.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, true));
        this.medio.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, true));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, true));
        
        
	}
	
	/**
	 * Nos retorna true si los campos
	 * son válidos, se debe invocar antes
	 * de consumir al controlador
	 *
	 */
	private boolean fieldsValidos()
	{
		boolean valido = false;
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
				
		try
		{
			if(this.codCliente.isValid() && this.fecha.isValid() 
					&& this.nroMega.isValid() && this.nroDocum.isValid()
					&& this.fecDocum.isValid() && this.carpeta.isValid()
					&& this.impMn.isValid() && this.impMo.isValid()
					&& this.tcMov.isValid() && this.Kilos.isValid()
					&& this.fecCruce.isValid() && this.marca.isValid()
					&& this.medio.isValid() && this.descripcion.isValid()
					&& this.comboDocumento.isValid() && this.comboMoneda.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	public void cerrarVentana()	{
		UI.getCurrent().removeWindow(sub);
	}

	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		if(datos instanceof ClienteVO){
			ClienteVO clienteVO = (ClienteVO) datos;
			this.codCliente.setValue(String.valueOf(clienteVO.getCodigo()));
			this.nomCliente.setValue(clienteVO.getNombre());
//			this.descripcionMoneda.setValue(monedaVO.getDescripcion());
//			this.codMoneda.setValue(monedaVO.getCodMoneda());
		}
		
	}
	
	public void setObservaciones(String observaciones){
		this.obseAux.setValue(observaciones); 
	}
	
	public void inicializarComboMoneda(String cod){
		
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		
		try {
			permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PROCESOS,
							VariablesPermisos.OPERACION_LEER);
			lstMonedas = this.controlador.getMonedas(permisoAux);
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedasObj.addBean(monedaVO);
			
			if(monedaVO.isNacional()){
				monedaNacional = monedaVO;
			}
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		this.comboMoneda.setValue(moneda);
		if(!moneda.isNacional()){
			tcMov.setEnabled(true);
		}
		else{
			tcMov.setEnabled(false);
		}
	}
	
	public void inicializarComboDocuemnto(String cod){
		
		BeanItemContainer<DocumentoAduaneroVO> documentosObj = new BeanItemContainer<DocumentoAduaneroVO>(DocumentoAduaneroVO.class);
		DocumentoAduaneroVO documento = new DocumentoAduaneroVO();
		ArrayList<DocumentoAduaneroVO> lstDocumentos = new ArrayList<DocumentoAduaneroVO>();
		
		try {
			permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PROCESOS,
							VariablesPermisos.OPERACION_LEER);
			lstDocumentos = this.controlador.getDocumentos(permisoAux);
			
		} catch (InicializandoException | ObteniendoDocumentosException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (DocumentoAduaneroVO documentoVO : lstDocumentos) {
			
			documentosObj.addBean(documentoVO);
			
			if(cod != null){
				if(cod.equals(documentoVO.getcodDocumento())){
					documento = documentoVO;
				}
			}
		}
		
		this.comboDocumento.setContainerDataSource(documentosObj);
		this.comboDocumento.setItemCaptionPropertyId("descripcion");
		this.comboDocumento.setValue(documento);
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
	
	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	public void calculos(){
		
		if(importeMoneda != null && cotizacionVenta != null){
			
			Double truncatedDouble = new BigDecimal(importeMoneda.doubleValue() * cotizacionVenta.doubleValue())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
			
			try {
				impMn.setConvertedValue(truncatedDouble);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		if(cotizacionVenta != null){
			try {
				tcMov.setConvertedValue(cotizacionVenta);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	public void inicializarCampos(){
		
		impMo.setConverter(Double.class);
		impMo.setConversionError("Error en formato de número");
		
		impMn.setConverter(Double.class);
		impMn.setConversionError("Error en formato de número");
		
		impTr.setConverter(Double.class);
		impTr.setConversionError("Error en formato de número");
		
		tcMov.setConverter(BigDecimal.class);
		tcMov.setConversionError("Error en formato de número");
		
		Kilos.setConverter(Double.class);
		Kilos.setConversionError("Error en formato de número");
		
		nroMega .setConverter(Integer.class);
		nroMega .setConversionError("Ingrese un número entero");
		
		nroDocum .setConverter(Integer.class);
		nroDocum .setConversionError("Ingrese un número entero");
		
		impMo.setData("ProgramaticallyChanged");
		tcMov.setData("ProgramaticallyChanged");
		
	}

	public MonedaVO seteaCotizaciones(MonedaVO monedaVO){
		
		UsuarioPermisosVO permisosAux;
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_PROCESOS,
						VariablesPermisos.OPERACION_LEER);
		
		Date fechaValor = convertFromJAVADateToSQLDate(fecha.getValue());
		CotizacionVO cotiz;
		if(monedaVO.isNacional()){
			monedaNacional = monedaVO;
		}
		else if(fecha!=null){
			
			cotiz = new CotizacionVO();
			
			try {
				
				cotiz = this.controlador.getCotizacion(permisosAux, fechaValor, monedaVO.getCodMoneda());
				monedaVO.setCotizacion(cotiz.getCotizacionVenta());
			} 
			catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
					| InicializandoException | NoTienePermisosException e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError(e.getMessage().toString());
			}
		}
		return monedaVO;
	}
}
