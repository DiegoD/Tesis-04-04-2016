package com.vista.Procesos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.ProcesoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
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
					procesoVO.setCodigo(Integer.parseInt(codigo.getValue().trim()));
					procesoVO.setCodCliente(codCliente.getValue().trim());
					procesoVO.setNomCliente(nomCliente.getValue().trim());
					//procesoVO.setCodMoneda(codMoneda.getValue().trim());
					
					//Moneda
					MonedaVO auxMoneda = new MonedaVO();
					auxMoneda = (MonedaVO) this.comboMoneda.getValue();
					procesoVO.setCodMoneda(auxMoneda.getCodMoneda());
					procesoVO.setDescMoneda(auxMoneda.getDescripcion());
					procesoVO.setSimboloMoneda(auxMoneda.getSimbolo());
					
					//Documento
					DocumentoAduaneroVO auxDocumento = new DocumentoAduaneroVO();
					auxDocumento = (DocumentoAduaneroVO) this.comboDocumento.getValue();
					procesoVO.setCodDocum(auxDocumento.getcodDocumento());
					procesoVO.setNomDocum(auxDocumento.getdescripcion());
					
					String aux = impMo.getValue().toString().trim().replace(",", ".");
					procesoVO.setImpMo(Float.parseFloat(aux));
					
					aux = impMn.getValue().toString().trim().replace(",", ".");
					procesoVO.setImpMn(Float.parseFloat(aux));
					
					aux = tcMov.getValue().toString().trim().replace(",", ".");
					procesoVO.setTcMov(Float.parseFloat(aux));
					
					//procesoVO.setImpTr(Float.parseFloat(impTr.getValue().trim()));
					
					procesoVO.setCodigo(Integer.parseInt(codigo.getValue().trim()));
					procesoVO.setFecha(new java.sql.Timestamp(fecha.getValue().getTime()));
					procesoVO.setNroMega(Integer.parseInt(nroMega.getValue().trim()));
					procesoVO.setNroDocum(Integer.parseInt(nroDocum.getValue().trim()));
					procesoVO.setFecDocum(new java.sql.Timestamp(fecDocum.getValue().getTime()));
					procesoVO.setCarpeta(carpeta.getValue().trim());
					
					procesoVO.setKilos(Float.parseFloat(Kilos.getValue().trim()));
					procesoVO.setFecCruce(new java.sql.Timestamp(fecCruce.getValue().getTime()));
					procesoVO.setMarca(marca.getValue().trim());
					procesoVO.setMedio(medio.getValue().trim());
					procesoVO.setDescripcion(descripcion.getValue().trim());
					//VERprocesoVO.setObservaciones(observaciones.getValue.trim());

					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarProceso(procesoVO, permisoAux);
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
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
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
	}
	
	public  void inicializarForm(){
		
		this.controlador = new ProcesoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ProcesoVO>(ProcesoVO.class);
		
		//inicializar los valores de los combos impuesto y tipo de rubro
		inicializarComboMoneda(null);
		inicializarComboDocuemnto(null);
		
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
		
		this.fecDocum.setRequired(setear);
		this.fecDocum.setRequiredError("Es requerido");
		
		this.carpeta.setRequired(setear);
		this.carpeta.setRequiredError("Es requerido");
		
		this.impMo.setRequired(setear);
		this.impMo.setRequiredError("Es requerido");
		
		this.impMn.setRequired(setear);
		this.impMn.setRequiredError("Es requerido");
		
		this.tcMov.setRequired(setear);
		this.tcMov.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.comboDocumento.setRequired(setear);
		this.comboDocumento.setRequiredError("Es requerido");
	}
	
	/**
	 * Dado un item MonedaVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ProcesoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		ProcesoVO proceso = new ProcesoVO();
		proceso = fieldGroup.getItemDataSource().getBean();
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
		
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
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
		this.impMo.setReadOnly(false);
		this.tcMov.setReadOnly(false);
		this.Kilos.setReadOnly(false);
		this.fecCruce.setReadOnly(false);
		this.marca.setReadOnly(false);
		this.medio.setReadOnly(false);
		this.descripcion.setReadOnly(false);
		this.codCliente.setReadOnly(false);
		this.nomCliente.setReadOnly(false);
		
		this.codCliente.setEnabled(false);
		this.nomCliente.setEnabled(false);
		this.comboMoneda.setEnabled(false);
		this.comboDocumento.setEnabled(false);
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
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codigo.setReadOnly(setear);
		this.codCliente.setReadOnly(setear);
		this.nomCliente.setReadOnly(setear);
		this.fecha.setReadOnly(setear);
		this.nroMega.setReadOnly(setear);
		this.nroDocum.setReadOnly(setear);
		this.fecDocum.setReadOnly(setear);
		this.carpeta.setReadOnly(setear);
		this.impMn.setReadOnly(setear);
		this.impMo.setReadOnly(setear);
		this.tcMov.setReadOnly(setear);
		this.Kilos.setReadOnly(setear);
		this.fecCruce.setReadOnly(setear);
		this.marca.setReadOnly(setear);
		this.medio.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);

		this.comboMoneda.setEnabled(false);
		this.comboDocumento.setEnabled(false);
				
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
                     " 20 caracteres máximo", 1, 20, false));
        
        this.marca.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        this.medio.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        
        
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
			if(this.codigo.isValid() && this.codCliente.isValid() && this.fecha.isValid() 
					&& this.nroMega.isValid() && this.nroDocum.isValid()
					&& this.fecDocum.isValid() && this.carpeta.isValid()
					&& this.impMn.isValid() && this.impMo.isValid()
					&& this.tcMov.isValid() && this.Kilos.isValid()
					&& this.fecCruce.isValid() && this.marca.isValid()
					&& this.medio.isValid() && this.descripcion.isValid())
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
			this.codCliente.setValue(clienteVO.getCodigoDoc());
			this.nomCliente.setValue(clienteVO.getNombre());
//			this.descripcionMoneda.setValue(monedaVO.getDescripcion());
//			this.codMoneda.setValue(monedaVO.getCodMoneda());
		}
		
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
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			lstMonedas = this.controlador.getMonedas(permisoAux);
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedasObj.addBean(monedaVO);
			
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		this.comboMoneda.setValue(moneda);
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
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
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
}
