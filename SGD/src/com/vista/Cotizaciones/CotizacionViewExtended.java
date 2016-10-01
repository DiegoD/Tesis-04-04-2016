package com.vista.Cotizaciones;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.CodigoGeneralizadoControlador;
import com.controladores.CotizacionControlador;
import com.controladores.RubroControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ExisteCotizacionException;
import com.excepciones.Cotizaciones.InsertandoCotizacionException;
import com.excepciones.Cotizaciones.ModificandoCotizacionException;
import com.excepciones.Cotizaciones.NoExisteCotizacionException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.gargoylesoftware.htmlunit.javascript.host.PermissionStatus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.TipoRubro.TipoRubroVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class CotizacionViewExtended extends CotizacionView implements IBusqueda{
	
	private BeanFieldGroup<CotizacionVO> fieldGroup;
	private CotizacionControlador controlador;
	private String operacion;
	private CotizacionesPanelExtended mainView;
	private BeanItemContainer<CotizacionVO> containerCotizaciones;
	MySub sub;
	private PermisosUsuario permisos;
	ArrayList<CotizacionVO> lstCotizaciones = new ArrayList<CotizacionVO>();
	UsuarioPermisosVO permisoAux;
	
	public CotizacionViewExtended(String opera, CotizacionesPanelExtended main) {
		
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
					 /* para confirmar los permisos del usuario*/
					permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_COTIZACIONES,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
					
									
					CotizacionVO cotizacionVO = new CotizacionVO();		

					java.util.Date fechaCotiz = new java.util.Date();
					fechaCotiz = fecha.getValue();
					//cotizacionVO.setFecha(new java.sql.Timestamp(fechaCotiz.getTime()));
					cotizacionVO.setFecha(new java.sql.Timestamp(fecha.getValue().getTime()));
					
					String aux = cotizacionCompra.getValue().toString().trim().replace(",", ".");
					cotizacionVO.setCotizacionCompra(Float.parseFloat(aux));
					
					aux = cotizacionVenta.getValue().toString().trim().replace(",", ".");
					cotizacionVO.setCotizacionVenta(Float.parseFloat(aux));
					
					MonedaVO auxMoneda = new MonedaVO();
					auxMoneda = (MonedaVO) this.comboMoneda.getValue();
					cotizacionVO.setCodMoneda(auxMoneda.getCodMoneda());
					cotizacionVO.setDescripcionMoneda(auxMoneda.getDescripcion());
					
//					cotizacionVO.setCodMoneda(codMoneda.getValue().trim());
//					cotizacionVO.setDescripcionMoneda(descripcionMoneda.getValue().trim());
					
					cotizacionVO.setUsuarioMod(this.permisos.getUsuario());
					cotizacionVO.setOperacion(operacion);
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarCotizacion(cotizacionVO, permisoAux);
						
						this.mainView.actulaizarGrilla(cotizacionVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado la cotización");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarCotizacion(cotizacionVO, permisoAux);
						
						this.mainView.actulaizarGrilla(cotizacionVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado la cotización");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
			} 
			catch (ConexionException | NoExisteCotizacionException | ModificandoCotizacionException | ExisteCotizacionException | 
					 InicializandoException | InsertandoCotizacionException | ErrorInesperadoException 
					 | ObteniendoPermisosException| NoTienePermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			catch(NumberFormatException e){
				Mensajes.mostrarMensajeError("Cotización no válida");
			
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
		
//		this.btnBuscarMoneda.addClickListener(click -> {
//			
//			BusquedaViewExtended form = new BusquedaViewExtended(this, new MonedaVO());
//			ArrayList<Object> lst = new ArrayList<Object>();
//			ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
//			//controladorImpuestos = new ImpuestoControlador();
//			try {
//				 /* para confirmar los permisos del usuario*/
//				permisoAux = 
//						new UsuarioPermisosVO(this.permisos.getCodEmp(),
//								this.permisos.getUsuario(),
//								VariablesPermisos.FORMULARIO_COTIZACIONES,
//								VariablesPermisos.OPERACION_NUEVO_EDITAR);
//				lstMonedas = this.controlador.getMonedas(permisoAux);
//				
//			} catch (ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException e) {
//
//				Mensajes.mostrarMensajeError(e.getMessage());
//			}
//			catch (ObteniendoMonedaException e){
//				Mensajes.mostrarMensajeError(e.getMessage());
//			}
//			Object obj;
//			for (MonedaVO i: lstMonedas) {
//				obj = new Object();
//				obj = (Object)i;
//				lst.add(obj);
//			}
//			try {
//				
//				form.inicializarGrilla(lst);
//				
//			} catch (Exception e) {
//				
//				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
//			}
//			
//			
//			
//			sub = new MySub("65%", "65%" );
//			sub.setModal(true);
//			sub.center();
//			sub.setModal(true);
//			sub.setVista(form);
//			sub.center();
//			sub.setDraggable(true);
//			UI.getCurrent().addWindow(sub);
//			
//		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
	}
		
	public  void inicializarForm(){
		
		this.controlador = new CotizacionControlador();
					
		this.fieldGroup =  new BeanFieldGroup<CotizacionVO>(CotizacionVO.class);
		
		//inicializar los valores de los combos impuesto y tipo de rubro
		inicializarComboMoneda(null);
		
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
		
//		this.codMoneda.setRequired(setear);
//		this.codMoneda.setRequiredError("Es requerido");
//		
//		this.descripcionMoneda.setRequired(setear);
//		this.descripcionMoneda.setRequiredError("Es requerido");
		
		this.fecha.setRequired(setear);
		this.fecha.setRequiredError("Es requerido");
		
		this.cotizacionCompra.setRequired(setear);
		this.cotizacionCompra.setRequiredError("Es requerido");
		
		this.cotizacionVenta.setRequired(setear);
		this.cotizacionVenta.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item CotizacionVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<CotizacionVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		CotizacionVO cotizacion = new CotizacionVO();
		cotizacion = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaMod());
		
		auditoria.setDescription(
				"Usuario: " + cotizacion.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operación: " + cotizacion.getOperacion());
		
		this.inicializarComboMoneda(cotizacion.getCodMoneda());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		this.cotizacionCompra.setReadOnly(false);
		this.cotizacionVenta.setReadOnly(false);
//		this.codMoneda.setReadOnly(false);
//		this.descripcionMoneda.setReadOnly(false);
//		this.codMoneda.setEnabled(true);
//		this.descripcionMoneda.setEnabled(true);
		
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
//		this.btnBuscarMoneda.setEnabled(false);
//		this.btnBuscarMoneda.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
//		this.btnBuscarMoneda.setEnabled(true);
//		this.btnBuscarMoneda.setVisible(true);
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
//		this.codMoneda.setReadOnly(setear);
//		this.descripcionMoneda.setReadOnly(setear);
		this.cotizacionCompra.setReadOnly(setear);
		this.cotizacionVenta.setReadOnly(setear);
		this.fecha.setReadOnly(setear);
		this.comboMoneda.setEnabled(false);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
//        this.codMoneda.addValidator(
//                new StringLengthValidator(
//                     " 20 caracteres máximo", 1, 20, false));
//        
//        this.descripcion.addValidator(
//                new StringLengthValidator(
//                        " 45 caracteres máximo", 1, 45, false));
        
		
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
		
		if(this.comboMoneda.isValid() && this.fecha.isValid() && this.cotizacionCompra.isValid() && this.cotizacionVenta.isValid())
			valido = true;
			
		return valido;
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}

	@Override
	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		if(datos instanceof MonedaVO){
			MonedaVO monedaVO = (MonedaVO) datos;
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
							VariablesPermisos.FORMULARIO_COTIZACIONES,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			lstMonedas = this.controlador.getMonedasActivas(permisoAux);
			
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

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
}
