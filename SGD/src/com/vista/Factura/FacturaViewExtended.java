package com.vista.Factura;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.controladores.FacturaControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Factura.*;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.sun.org.apache.xpath.internal.operations.Variable;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Docum.FacturaDetalleVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Gasto.GtoSaldoAux;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.IMensaje;
import com.vista.MensajeExtended;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Gastos.GastoViewExtended;
import com.vista.Gastos.IGastosMain;
import com.vista.Validaciones.Validaciones;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;


public class FacturaViewExtended extends FacturaViews implements IBusqueda, IGastosMain, IMensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BeanFieldGroup<FacturaVO> fieldGroup;
	private ArrayList<FacturaDetalleVO> lstDetalleVO; /*Lista de detalle de Factura*/
	private ArrayList<FacturaDetalleVO> lstDetalleAgregar; /*Lista de detalle a agregar*/
	private ArrayList<FacturaDetalleVO> lstDetalleQuitar; /*Lista de detalle a agregar*/
	private FacturaControlador controlador;
	private String operacion;
	private FacturaPanelExtended mainView;
	BeanItemContainer<FacturaDetalleVO> container;
	private FacturaDetalleVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
	UsuarioPermisosVO permisoAux;
	CotizacionVO cotizacion =  new CotizacionVO();
	Double cotizacionVenta = null;
	TitularVO titularVO = new TitularVO();
	private Hashtable<Integer, GtoSaldoAux> saldoOriginalGastos; /*Variable auxliar para poder
	 															 controlar que el saldo del gasto quede
	 															 en negativo*/
	
	Double importeTotalCalculado;
	FacturaVO ingresoCopia; /*Variable utilizada para la modificacion de factura,
	 							 para poder detectar las lineas eliminadas de la misma */
	
	boolean cambioMoneda;
	MonedaVO monedaNacional = new MonedaVO();
	
	MySub sub;
	
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
	Validaciones val = new Validaciones();
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public FacturaViewExtended(String opera, FacturaPanelExtended main){
	
	
	this.cambioMoneda = false;
	
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	saldoOriginalGastos = new Hashtable<Integer, GtoSaldoAux>();
	
	this.operacion = opera;
	this.mainView = main;
	lstGastos.setEditorBuffered(true);
	
	
	/*Esta lista es utilizada solamente para detalle  agregados*/
	this.lstDetalleAgregar = new ArrayList<FacturaDetalleVO>();
	this.lstDetalleQuitar = new ArrayList<FacturaDetalleVO>();
	this.inicializarForm();
	
	
	
	this.btnBuscarCliente.addClickListener(click -> {
		
		BusquedaViewExtended form;
		
		if(this.chkFuncionario.getValue()){
			form = new BusquedaViewExtended(this, new TitularVO());
		}
		else{
			form = new BusquedaViewExtended(this, new ClienteVO());
		}	
		
		ArrayList<Object> lst = new ArrayList<Object>();
		ArrayList<TitularVO> lstTitulares = new ArrayList<TitularVO>();
		ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_INGRESO_COBRO,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		try {
			
			if(this.chkFuncionario.getValue()){
				lstTitulares = this.controlador.getTitulares(permisoAux);
			}
			else{
				
				lstClientes = this.controlador.getClientes(permisoAux);
			}
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
				 ObteniendoClientesException | ObteniendoTitularesException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		if(this.chkFuncionario.getValue()){
			Object obj;
			for (TitularVO i: lstTitulares) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
		}
		else{
			Object obj;
			for (ClienteVO i: lstClientes) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
		}
		try {
			
			form.inicializarGrilla(lst);
		
			
		} catch (Exception e) {
			
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		sub = new MySub("85%", "65%" );
		sub.setModal(true);
		sub.center();
		sub.setModal(true);
		sub.setVista(form);
		sub.center();
		sub.setDraggable(true);
		UI.getCurrent().addWindow(sub);
		
	});
	

	
	
	fecValor.addValueChangeListener(new Property.ValueChangeListener() {

   		@Override
		public void valueChange(ValueChangeEvent event) {
   			
   			if("ProgramaticallyChanged".equals(fecValor.getData())){
				fecValor.setData(null);
   	            return;
   	        }
   			
   			if(!operacion.equals(Variables.OPERACION_LECTURA)){
				try {
					permisoAux = 
							new UsuarioPermisosVO(permisos.getCodEmp(),
									permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_GASTOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
					
					if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
						String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
						fecValor.setData("ProgramaticallyChanged");
						fecValor.setValue(null);
						Mensajes.mostrarMensajeError("El per�odo est� cerrado para la fecha " + fecha);
						return;
					}
				} catch (NumberFormatException | ExistePeriodoException | ConexionException | SQLException
						| NoExistePeriodoException | InicializandoException | ObteniendoPermisosException
						| NoTienePermisosException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
			}
   			
   			for (MonedaVO monedaVO : lstMonedas) {
					
				monedaVO = seteaCotizaciones(monedaVO);
			}	
   			
   			/*Inicializamos VO de permisos para el usuario, formulario y operacion
   			 * para confirmar los permisos del usuario*/
   			UsuarioPermisosVO permisoAux = 
   					new UsuarioPermisosVO(permisos.getCodEmp(),
   							permisos.getUsuario(),
   							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
   			
   			CtaBcoVO ctaBcoAux;
   			ctaBcoAux = new CtaBcoVO();
   			if(comboCuentas.getValue() != null){
   				ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
   			
	   			MonedaVO auxMoneda = new MonedaVO();
	   			Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
	   			
	   			auxMoneda = (MonedaVO) ctaBcoAux.getMonedaVO();
	   			
	   			try {
	   				if(!auxMoneda.isNacional()){
	   					cotizacion = controlador.getCotizacion(permisoAux, fecha, ctaBcoAux.getMonedaVO().getCodMoneda());
	   				}
	   				else{
	   					cotizacion.setCotizacionVenta(1);
	   				}
				} catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
						| InicializandoException | NoTienePermisosException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				if(cotizacion.getCotizacionVenta() != 0){
					cotizacionVenta = cotizacion.getCotizacionVenta();
				}
				else{
					Mensajes.mostrarMensajeError("Debe cargar la cotizaci�n para la moneda");
					comboCuentas.setErrorHandler(null);
					comboCuentas.setData("ProgramaticallyChanged");
					comboCuentas.setValue(null);
					monedaBanco.setValue("");
					cuentaBanco.setValue("");
					
				}
   			
				if(comboCuentas.getValue()!=null){
					if(ctaBcoAux != null && !comboCuentas.getValue().equals("")){
		   				monedaBanco.setValue(ctaBcoAux.getMonedaVO().getSimbolo());
		   				cuentaBanco.setValue(ctaBcoAux.getCodigo());
		   				if(comboMoneda.getValue()!= ""){
		   					auxMoneda = (MonedaVO) comboMoneda.getValue();
		   					if(auxMoneda != null){
		   						if(!auxMoneda.getCodMoneda().equals(ctaBcoAux.getMonedaVO().getCodMoneda()) && opera != Variables.OPERACION_LECTURA){
			   						Mensajes.mostrarMensajeWarning("La moneda del banco es diferente a la moneda del documento");
			   					}
		   					}
		   					
		   				}
		   			}
				}
	   			
   			}
   			
   			if(comboMoneda.getValue()!= null){
   				
   	   			
	   			MonedaVO auxMoneda = new MonedaVO();
	   			Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
	   			auxMoneda = (MonedaVO) comboMoneda.getValue();
	   			
	   			if(auxMoneda.getCodMoneda() != null && !auxMoneda.isNacional()){
	   				try {
	   					
	   					tcMov.setVisible(true);
						cotizacion = controlador.getCotizacion(permisoAux, fecha, auxMoneda.getCodMoneda());
						if(cotizacion.getCotizacionVenta() != 0 && !auxMoneda.isNacional()){
							cotizacionVenta = cotizacion.getCotizacionVenta();
							if(comboCuentas.getValue() != "" && comboCuentas.getValue()!=null){
								
					   			ctaBcoAux = new CtaBcoVO();
					   			ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
					   			if(!auxMoneda.getCodMoneda().equals(ctaBcoAux.getMonedaVO().getCodMoneda())&& opera != Variables.OPERACION_LECTURA){
					   				Mensajes.mostrarMensajeWarning("La moneda del banco es diferente a la moneda del documento");
					   			}
				   			}
							calculos();
						}
						else{
							Mensajes.mostrarMensajeError("Debe cargar la cotizaci�n para la moneda");
							comboMoneda.setValue(monedaNacional);
						}
					} 
	   				catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
							| InicializandoException | NoTienePermisosException e) {
	   					
	   					Mensajes.mostrarMensajeError(e.getMessage());
					}
	   				if(fecha != null){
	   					
	   					for (MonedaVO monedaVO : lstMonedas) {
	   						
	   						monedaVO = seteaCotizaciones(monedaVO);
	   					}
	   					
	   				}
	   			}
   			}
		}
    });
	
	
	/**
	* Agregamos listener al combo de tipo (banco, caja), determinamos si mostramos
	* los campos del banco o no;
	*
	*/
    comboTipo.addValueChangeListener(new Property.ValueChangeListener() {

   		@Override
		public void valueChange(ValueChangeEvent event) {

   			mostrarDatosDeBanco();
		}
    });
    
    comboCuentas.addValueChangeListener(new Property.ValueChangeListener() {

    	
    	
   		@Override
		public void valueChange(ValueChangeEvent event) {
   			
   			if("ProgramaticallyChanged".equals(comboCuentas.getData())){
   				comboCuentas.setData(null);
   	            return;
   	        }
   			
   			/*Inicializamos VO de permisos para el usuario, formulario y operacion
   			 * para confirmar los permisos del usuario*/
   			UsuarioPermisosVO permisoAux = 
   					new UsuarioPermisosVO(permisos.getCodEmp(),
   							permisos.getUsuario(),
   							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
   			
   			CtaBcoVO ctaBcoAux;
   			ctaBcoAux = new CtaBcoVO();
   			if(comboCuentas.getValue() != null){
   				ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
   			
	   			MonedaVO auxMoneda = new MonedaVO();
	   			Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
	   			
	   			auxMoneda = (MonedaVO) ctaBcoAux.getMonedaVO();
	   			
	   			try {
	   				if(!auxMoneda.isNacional()){
	   					cotizacion = controlador.getCotizacion(permisoAux, fecha, ctaBcoAux.getMonedaVO().getCodMoneda());
	   				}
	   				else{
	   					cotizacion.setCotizacionVenta(1);
	   				}
				} catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
						| InicializandoException | NoTienePermisosException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				if(cotizacion.getCotizacionVenta() != 0){
					cotizacionVenta = cotizacion.getCotizacionVenta();
				}
				else{
					Mensajes.mostrarMensajeError("Debe cargar la cotizaci�n para la moneda");
					comboCuentas.setErrorHandler(null);
					comboCuentas.setData("ProgramaticallyChanged");
					comboCuentas.setValue(null);
					monedaBanco.setValue("");
					cuentaBanco.setValue("");
					
					return;
				}
				
	   			if(ctaBcoAux != null && !comboCuentas.getValue().equals("")){
	   				monedaBanco.setValue(ctaBcoAux.getMonedaVO().getSimbolo());
	   				cuentaBanco.setValue(ctaBcoAux.getCodigo());
	   				if(comboMoneda.getValue()!= ""){
	   					auxMoneda = (MonedaVO) comboMoneda.getValue();
	   					if(auxMoneda != null){
	   						if(!auxMoneda.getCodMoneda().equals(ctaBcoAux.getMonedaVO().getCodMoneda()) && opera != Variables.OPERACION_LECTURA){
		   						Mensajes.mostrarMensajeWarning("La moneda del banco es diferente a la moneda del documento");
		   					}
	   					}
	   					
	   				}
	   				
	   				
	   			}
   			}
   			
   			
			
		}
    });
    /**
	* Agregamos listener al combo de monedas, para verificar que no modifique la moneda
	* una vez ingresado un gasto ya 
	*
	*/
    comboMoneda.addValueChangeListener(new Property.ValueChangeListener() {
   		@Override
		public void valueChange(ValueChangeEvent event) {
   			
   			
   			
   			UsuarioPermisosVO permisoAux = 
   					new UsuarioPermisosVO(permisos.getCodEmp(),
   							permisos.getUsuario(),
   							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
   			
   			MonedaVO auxMoneda = new MonedaVO();
   			Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
   			CtaBcoVO ctaBcoAux;
   			auxMoneda = (MonedaVO) comboMoneda.getValue();
   			
   			
   			
   			if(auxMoneda.getCodMoneda() != null && !auxMoneda.isNacional()){
   				try {
   					
   					tcMov.setVisible(true);
					cotizacion = controlador.getCotizacion(permisoAux, fecha, auxMoneda.getCodMoneda());
					if(cotizacion.getCotizacionVenta() != 0 && !auxMoneda.isNacional()){
						cotizacionVenta = cotizacion.getCotizacionVenta();
						if(comboCuentas.getValue() != "" && comboCuentas.getValue()!=null){
							
				   			ctaBcoAux = new CtaBcoVO();
				   			ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
				   			if(!auxMoneda.getCodMoneda().equals(ctaBcoAux.getMonedaVO().getCodMoneda())&& opera != Variables.OPERACION_LECTURA){
				   				Mensajes.mostrarMensajeWarning("La moneda del banco es diferente a la moneda del documento");
				   			}
			   			}
						calculos();
					}
					else{
						Mensajes.mostrarMensajeError("Debe cargar la cotizaci�n para la moneda");
						comboMoneda.setValue(monedaNacional);
						return;
					}
				} 
   				catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
						| InicializandoException | NoTienePermisosException e) {
   					
   					Mensajes.mostrarMensajeError(e.getMessage());
				}
   				if(fecha != null){
   					
   					for (MonedaVO monedaVO : lstMonedas) {
   						
   						monedaVO = seteaCotizaciones(monedaVO);
   					}
   					
   				}
   			}
   			else{
   				
   				if(comboCuentas.getValue() != "" && comboCuentas.getValue()!=null){
		   			ctaBcoAux = new CtaBcoVO();
		   			ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
		   			
		   			if(!auxMoneda.getCodMoneda().equals(ctaBcoAux.getMonedaVO().getCodMoneda())){
		   				Mensajes.mostrarMensajeWarning("La moneda del banco es diferente a la moneda del documento");
		   			}
   				}
   				tcMov.setVisible(false);
   				cotizacionVenta = (double)1;
   				calculos();
   			}
   			/*Si ya hay ingresado una linea  no dejamos cambiar la moneda*/
   			if(lstDetalleVO.size()>0)
   			{
   				calcularImporteTotal();
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
	        		cotizacionVenta = (Double) tcMov.getConvertedValue();
				} catch (Exception e) {
					// TODO: handle exception
					return;
				}
	        	
	        	
	        	Double truncatedDouble = new BigDecimal(cotizacionVenta)
					    .setScale(2, BigDecimal.ROUND_HALF_UP)
					    .doubleValue();
				
	        	cotizacionVenta = truncatedDouble;
	        	
	        	if(operacion != Variables.OPERACION_LECTURA){

		        	calculos();
		        }
	        	
	    	}
	    }
	});
	
	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
		try {
			
			/*Seteamos validaciones en nuevo, cuando es editar
			 * solamente cuando apreta el boton editar*/
			this.setearValidaciones(true);
			
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_FACTURA,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				
				int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
				
				if(comp != 0){
					Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
					return;
				}
				
				FacturaVO factVO = new FacturaVO();	
				
				factVO.setImpTotMo((Double) impTotMo.getConvertedValue());
				
				/*Obtenemos la cotizacion y calculamos el importe MN*/
				Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
				CotizacionVO coti = null;
				
				
				try {
					
					/////////////////////////////MONEDA//////////////////////////////////////////////////
					
					MonedaVO auxMoneda = null;
					
					//Obtenemos la moneda del cabezal
					auxMoneda = new MonedaVO();
					if(this.comboMoneda.getValue() != null){
						
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						
						/*SI EL TIPO ES CAJA TOMAMOS LA MONEDA DEL CABEZAL DEL COBRO*/
						//if(((String)comboTipo.getValue()).equals("Caja")) { 
						
							factVO.setCodMoneda(auxMoneda.getCodMoneda());
							factVO.setNomMoneda(auxMoneda.getDescripcion());
							factVO.setSimboloMoneda(auxMoneda.getSimbolo());
						//}
					}
					
					
					/////////////////////////////FIN MONEDA////////////////////////////////////////////
					
					/*Si la moneda del cabezal es nacional*/
					if(auxMoneda.isNacional()) /*Si la moneda del cabezal del cobro seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						factVO.setTcMov(1);
						factVO.setImpTotMn(factVO.getImpTotMo());
						
					}else
					{
						coti = this.controlador.getCotizacion(permisoAux, fecha, this.getCodMonedaSeleccionada());
						factVO.setTcMov(coti.getCotizacionVenta());
						factVO.setImpTotMn((factVO.getImpTotMo()*factVO.getTcMov()));
					}
					
				} catch (Exception e) {
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				factVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
				factVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
			
				factVO.setCodEmp(permisos.getCodEmp());
				factVO.setReferencia(referencia.getValue());
				
				factVO.setCodCtaInd("fact");
				
				
				factVO.setCodTitular(codTitular.getValue());
				factVO.setNomTitular(nomTitular.getValue());
				
				factVO.setOperacion(operacion);
				
				
				/*Si es nuevo aun no tenemos el nro de factura*/
				if(this.nroDocum.getValue() != null)
					factVO.setNroDocum(Integer.parseInt(this.nroDocum.getValue().toString().trim()));
				
			
				factVO.setUsuarioMod(this.permisos.getUsuario());
				
				if(this.operacion != Variables.OPERACION_NUEVO){
					factVO.setNroTrans((long)this.nroTrans.getConvertedValue());
				}
				else{
					factVO.setNroTrans(0);
				}
				
				
				/*Si hay detalle nuevo agregado
				 * lo agregamos a la lista */
				if(this.lstDetalleAgregar.size() > 0)
				{
					for (FacturaDetalleVO f : this.lstDetalleAgregar) {
						
						/*Si no esta lo agregamos*/
						if(!this.existeFormularioenLista(f.getNroDocum()))
							this.lstDetalleVO.add(f);
					}
				}
					
				factVO.setCodCuenta("factura");
				factVO.setDetalle(this.lstDetalleVO);
				
				if(factVO.getDetalle().size() <= 0){
					Mensajes.mostrarMensajeError("La factura no tiene detalle");
					return;
				}
				
				factVO.setCodDocum("fact");
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
					this.controlador.insertarFactura(factVO, permisoAux);
					
					this.mainView.actulaizarGrilla(factVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado la Factura");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*Si es edicion tomamos una copia del ingreso cobro, para detectar
					 * que lineas del cobro se eliminaron*/
					
					
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarFactura(factVO,ingresoCopia, permisoAux);
					
					this.mainView.actulaizarGrilla(factVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Cobro");
					main.cerrarVentana();
					
				}
			}
			else /*Si los campos no son v�lidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (ModificandoFacturaException| NoExisteFacturaException |InsertandoFacturaException| ExisteFacturaException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
		});
	
	
	
	
	
	/*Inicalizamos listener para boton de Editar*/
	this.btnEditar.addClickListener(click -> {
				
		try {
			
			permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_FACTURA,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
				Mensajes.mostrarMensajeError("El per�odo est� cerrado para la fecha " + fecha);
				return;
			}
			
			/*Inicializamos el Form en modo Edicion*/
			this.iniFormEditar();
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
	});
			
		this.btnEliminar.addClickListener(click -> {
			
			permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_FACTURA,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
					Mensajes.mostrarMensajeError("El per�odo est� cerrado para la fecha " + fecha);
					return;
				}
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			MensajeExtended form = new MensajeExtended("Desea eliminar factura?",this);
		
			sub = new MySub("25%", "20%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setClosable(false);
			sub.setResizable(false);
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
		});
		
		/*Inicalizamos listener para boton de Agregar gastos a cobrar*/
		this.btnAgregar.addClickListener(click -> {
						
			if(this.codTitular.getValue() != null && this.codTitular.getValue() != "" 
					&& this.fecValor.getValue() != null && this.comboMoneda.getValue() != null)
			{
				try {
				
					GastoViewExtended form = new GastoViewExtended(Variables.OPERACION_NUEVO, this, titularVO);
					
					sub = new MySub("100%","45%");
					sub.setModal(true);
					
					sub.setVista((Component) form);
					
					sub.center();
					
					String codCliente;/*Codigo del cliente para obtener los gastos a facturar del mismo*/
					
	
					if(this.operacion.equals(Variables.OPERACION_NUEVO) )
					{
						/*Si la operacion es nuevo, ponemos el  codGrupo vacio
						 * asi nos trae todos los  disponibles*/
						codCliente = this.codTitular.getValue().toString().trim();
					}
					else 
					{
						/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
						codCliente = fieldGroup.getItemDataSource().getBean().getCodTitular();
					}
					
					UI.getCurrent().addWindow(sub);

				}
				catch(Exception e){
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			}
			else{
				Mensajes.mostrarMensajeError("Debe ingresar los datos de cabecera");
			}
		});
			
		this.cancelar.addClickListener(click -> {
			
			if(this.lstDetalleAgregar.size() > 0)
			{
				
				for (FacturaDetalleVO f : this.lstDetalleAgregar) {
					
					/*Si no esta lo agregamos*/
					if(this.existeFormularioenLista(f.getNroDocum()))
						this.lstDetalleVO.remove(f);
				}
				
				
				
				this.calcularImporteTotal();
			}
			
			if(this.lstDetalleQuitar.size() > 0)
			{
				for (FacturaDetalleVO f : this.lstDetalleQuitar) {
					
					/*Si no esta lo agregamos*/
					if(!this.existeFormularioenLista(f.getNroDocum()))
						this.lstDetalleVO.add(f);
				}
				
				this.calcularImporteTotal();
			}
			
			main.cerrarVentana();
		});
			
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya una linea seleccionada para
				 * eliminar*/
				if(formSelecccionado != null)
				{

					/*Recorremos las lineas
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstDetalleVO.size() && !esta)
					{
						if(lstDetalleVO.get(i).getLinea()==formSelecccionado.getLinea())
						{
														
							/*Quitamos el formulario seleccionado de la lista*/
							lstDetalleVO.remove(lstDetalleVO.get(i));
							lstDetalleQuitar.add(formSelecccionado);
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstDetalleVO);
						
						this.actualizarGrillaContainer(container);
						
						this.calcularImporteTotal();
					}
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un gasto*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un gasto para quitar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
		});
			
		/*Inicializamos el listener de la grilla de gastos*/
		lstGastos.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		if(lstGastos.getSelectedRow() != null){
		    			BeanItem<FacturaDetalleVO> item = container.getItem(lstGastos.getSelectedRow());
				    	formSelecccionado = item.getBean(); /*Seteamos la linea
			    	 									seleccionado para poder quitarlo*/
		    		}
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
			
			
			
		/*Listener boton editar de la grilla de formularios*/
		this.btnEditarForm.addClickListener(click -> {
			
			boolean esta = false;
			
			this.titularVO.setCodigo(Integer.parseInt(codTitular.getValue()));
			this.titularVO.setNombre(nomTitular.getValue());
			this.titularVO.setTipo(tipo.getValue());
			
			
			
			try {
				
				/*Verificamos que haya una linea seleccionada para
				 * eliminar*/
				if(formSelecccionado != null){
					
					
					GastoVO bean = new GastoVO();
					BeanItem<GastoVO> item = new BeanItem<GastoVO> (bean);
					
					item.getBean().setNroDocum(formSelecccionado.getNroDocum());
					item.getBean().setFecDoc(formSelecccionado.getFecDoc());
					item.getBean().setFecValor(formSelecccionado.getFecValor());
					item.getBean().setCodProceso(formSelecccionado.getCodProceso());
					item.getBean().setDescProceso(formSelecccionado.getDescProceso());
					item.getBean().setCodTitular(formSelecccionado.getCodTitular());
					item.getBean().setNomTitular(formSelecccionado.getNomTitular());
					item.getBean().setCodCuenta(formSelecccionado.getCodCuenta());
					item.getBean().setNomCuenta(formSelecccionado.getNomCuenta());
					item.getBean().setCodCtaInd(formSelecccionado.getCodCtaInd());
					item.getBean().setCodRubro(formSelecccionado.getCodRubro());
					item.getBean().setNomRubro(formSelecccionado.getNomRubro());
					item.getBean().setCodImpuesto(formSelecccionado.getCodImpuesto());
					item.getBean().setNomImpuesto(formSelecccionado.getNomImpuesto());
					item.getBean().setPorcentajeImpuesto(formSelecccionado.getPorcentajeImpuesto());
					item.getBean().setCodMoneda(formSelecccionado.getCodMoneda());
					item.getBean().setNomMoneda(formSelecccionado.getNomMoneda());
					item.getBean().setSimboloMoneda(formSelecccionado.getSimboloMoneda());
					item.getBean().setTcMov(formSelecccionado.getTcMov());
					item.getBean().setImpTotMo(formSelecccionado.getImpTotMo());
					item.getBean().setImpTotMn(formSelecccionado.getImpTotMn());
					item.getBean().setImpImpuMo(formSelecccionado.getImpImpuMo());
					item.getBean().setImpImpuMn(formSelecccionado.getImpImpuMn());
					item.getBean().setImpSubMo(formSelecccionado.getImpSubMo());
					item.getBean().setImpSubMn(formSelecccionado.getImpSubMn());
					item.getBean().setReferencia(formSelecccionado.getReferencia());
					item.getBean().setEstadoGasto(formSelecccionado.getEstadoGasto());
					
					
					/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
			    	if(item.getBean().getFechaMod() == null)
			    	{
			    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
			    	}
					
			    	GastoViewExtended form = new GastoViewExtended(Variables.OPERACION_LECTURA, this, titularVO);
			    	sub = new MySub("100%","50%");
					sub.setModal(true);
					sub.setVista((Component) form);
					/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
					form.setDataSourceFormulario(item);
					
					UI.getCurrent().addWindow(sub);
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un gasto*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un gasto para editar");
				}
		
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		
		});
			
			
	///////////////////
	}

	public  void inicializarForm(){
		
		this.controlador = new FacturaControlador();
					
		this.fieldGroup =  new BeanFieldGroup<FacturaVO>(FacturaVO.class);
		
		/*Mostramos o ocultamos los datos del Banco, dependiendo del combo tipo (banco, caja)*/
		this.mostrarDatosDeBanco();
		
		/*Inicializamos los combos*/
		this.inicializarComboMoneda(null);
		
		inicializarCampos();
		
		importeTotalCalculado = (Double) impTotMo.getConvertedValue();
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
			
			/*Agregamos los filtros a la grilla*/
			//this.filtroGrilla();
	
		}else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
		} 
		/*LA OPERACION EDITAR ES DESDE EL DE LECTURA*/
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
		
		this.comboTipo.setRequired(setear);
		this.comboTipo.setRequiredError("Es requerido");
		
		
		
		this.fecDoc.setRequired(setear);
		this.fecDoc.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
		this.referencia.setRequired(setear);
		this.referencia.setRequiredError("Es requerido");
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
		this.comboMPagos.setRequired(setear);
		this.comboMPagos.setRequiredError("Es requerido");
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
		/*De Bco*/
		if(this.comboTipo.equals("Banco"))
		{
			this.comboMPagos.setRequired(setear);
			this.comboMPagos.setRequiredError("Es requerido");
			
			this.serieDocRef.setRequired(setear);
			this.serieDocRef.setRequiredError("Es requerido");
			
			this.nroDocRef.setRequired(setear);
			this.nroDocRef.setRequiredError("Es requerido");
			
			this.comboBancos.setRequired(setear);
			this.comboBancos.setRequiredError("Es requerido");
			
			this.comboCuentas.setRequired(setear);
			this.comboCuentas.setRequiredError("Es requerido");
		}
		else
		{
			this.serieDocRef.setReadOnly(false);
			this.nroDocRef.setReadOnly(false);
			this.comboBancos.setReadOnly(false);
			this.comboCuentas.setReadOnly(false);
			this.comboMPagos.setReadOnly(false);
			
			this.serieDocRef.setValue("0");
			this.serieDocRef.setRequired(false);
			this.nroDocRef.setRequired(false);
			this.comboBancos.setRequired(false);
			this.comboCuentas.setRequired(false);
			this.comboMPagos.setRequired(false);
			
			this.serieDocRef.setReadOnly(true);
			this.nroDocRef.setReadOnly(true);
			this.comboBancos.setReadOnly(true);
			this.comboCuentas.setReadOnly(true);
			this.comboMPagos.setReadOnly(true);
			
		
		}
		
	}
	
	/**
	 * Dado un item VO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<FacturaVO> item)
	{
		try{
			
		this.fieldGroup.setItemDataSource(item);
		
		
		FacturaVO fact = new FacturaVO();
		fact = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fact.getFechaMod());
		
		
		//Obtenemos bco
		BancoVO auxBco = new BancoVO();
		if(this.comboBancos.getValue() != null){
			
			auxBco = (BancoVO) this.comboBancos.getValue();
			
		}
		else{
			auxBco.setCodigo("0");
		}
		this.comboTipo.setImmediate(true);
		this.comboTipo.setReadOnly(false);
		this.comboTipo.setNullSelectionAllowed(false);
		this.comboTipo.setBuffered(true);
		
		/*Seteamos el tipo*/
		//this.comboTipo = new ComboBox();
		if(auxBco.getCodigo().equals("0"))
			this.comboTipo.setValue("Caja");
		else
			this.comboTipo.setValue("Banco");
		
		this.comboMPagos = new ComboBox();
		
		this.comboMPagos.setImmediate(true);
		this.comboMPagos.setNullSelectionAllowed(false);
		
		this.comboMPagos.addItem("Sin Asignar");
		this.comboMPagos.addItem("Cheque");
		this.comboMPagos.addItem("Transferencia");
	
		
		auditoria.setDescription(
			
			"Usuario: " + fact.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operaci�n: " + fact.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		/*Copiamos la variable para la modificacion*/
		this.ingresoCopia = new FacturaVO();
		this.ingresoCopia.copiar(fact);
		
		}catch(Exception e)
		{
			e.printStackTrace();
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
	}
	
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_BORRAR);
		
		
		this.chkFuncionario.setVisible(false);
		this.lblFuncionario.setVisible(false);
		this.btnBuscarCliente.setVisible(false);
		this.impTotMo.setEnabled(false);
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		/*Deshabilitamos boton aceptar*/
		this.disableBotonAceptar();
		this.disableBotonAgregarQuitar();
		
		
		if(permisoEliminar)
			this.enableBotonEliminar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		this.importeTotalCalculado = (Double)impTotMo.getConvertedValue();
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		/*Seteamos la grilla */
		this.container = 
				new BeanItemContainer<FacturaDetalleVO>(FacturaDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (FacturaDetalleVO detVO : this.lstDetalleVO) {
				container.addBean(detVO);
			}
		}
		
		this.actualizarGrillaContainer(container);
						
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		this.chkFuncionario.setVisible(false);
		this.lblFuncionario.setVisible(false);
		this.btnBuscarCliente.setVisible(false);
		this.impTotMo.setEnabled(true);
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		this.importeTotalCalculado = (Double)impTotMo.getConvertedValue();
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar*/
			this.enableBotonAceptar();
			this.disableBotonLectura();
			this.enableBotonAgregarQuitar();
			
			this.disableBotonEliminar();
			
			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
			
			this.comboTipo.setReadOnly(false);
			this.comboTipo.setEnabled(false);
			
			this.titularVO.setCodigo(Integer.parseInt(codTitular.getValue()));
			this.titularVO.setNombre(nomTitular.getValue());
			this.titularVO.setTipo(tipo.getValue());
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
		/*Si es nuevo ocultamos el nroDocum (ya que aun no tenemos el numero)*/
		this.nroDocum.setVisible(false);
		this.nroDocum.setEnabled(false);
		this.chkFuncionario.setVisible(true);
		this.lblFuncionario.setVisible(true);
		this.impTotMo.setEnabled(true);
		this.impTotMo.setReadOnly(false);
		importeTotalCalculado = (double) 0;
		
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
		/*Mostramos o ocultamos los datos del Banco, dependiendo del combo tipo (banco, caja)*/
		this.mostrarDatosDeBanco();
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.enableBotonEliminar();
		this.disableBotonLectura();
		this.enableBotonAgregarQuitar();
		this.lstDetalleAgregar = new ArrayList<FacturaDetalleVO>();
		this.lstDetalleQuitar = new ArrayList<FacturaDetalleVO>();
		this.lstDetalleVO = new ArrayList<FacturaDetalleVO>();
		
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<FacturaDetalleVO>(FacturaDetalleVO.class);
		
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
		
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
		/*Agregar control si se puede editar si el mes esta abierto*/
		//TODO
		this.fecDoc.setReadOnly(false);
		
		this.serieDocRef.setReadOnly(false);
		
		this.nroDocRef.setReadOnly(false);
		
		this.impTotMo.setReadOnly(false);
		this.impTotMo.setEnabled(true);
		
		this.referencia.setReadOnly(false);
	}
	
	/**
	 * Deshabilitamos el boton editar y permisos
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar y permisos
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
	}
	
	/**
	 * Habilitamos los botones de agregar y quitar
	 *
	 */
	private void enableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		this.btnQuitar.setEnabled(true);
		this.btnQuitar.setVisible(true);
		
		this.btnEditarForm.setEnabled(true);
		this.btnEditarForm.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnQuitar.setEnabled(false);
		this.btnQuitar.setVisible(false);
		
		this.btnEditarForm.setEnabled(false);
		this.btnEditarForm.setVisible(false);
	}
	
	/**
	 * Deshabilitamos el boton aceptar
	 *
	 */
	private void disableBotonAceptar()
	{
		this.aceptar.setEnabled(false);
		this.aceptar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	private void enableBotonEliminar()
	{
		if(operacion != Variables.OPERACION_NUEVO){
			this.btnEliminar.setEnabled(true);
			this.btnEliminar.setVisible(true);
			this.botones.setWidth("270px");
		}
		else{
			disableBotonEliminar();
		}
	}
	
	private void disableBotonEliminar()
	{
		this.btnEliminar.setEnabled(false);
		this.btnEliminar.setVisible(false);
		this.botones.setWidth("187px");
		
		
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		
		this.comboTipo.setReadOnly(setear);
		this.nroDocum.setReadOnly(setear);
		
		this.comboBancos.setReadOnly(setear);
		
		this.comboCuentas.setReadOnly(setear);
		
		this.fecDoc.setReadOnly(setear);
		
		this.fecValor.setReadOnly(setear);
		
		this.comboMPagos.setReadOnly(setear);
		
		this.serieDocRef.setReadOnly(setear);
		
		this.nroDocRef.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.referencia.setReadOnly(setear);
		
		this.codTitular.setReadOnly(false);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		
		this.codTitular.setReadOnly(setear);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		this.monedaBanco.setEnabled(false);
		this.cuentaBanco.setEnabled(false);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
		
        this.serieDocRef.addValidator(
                new StringLengthValidator(
                     " 4 caracteres m�ximo", 1, 4, false));
        
        this.referencia.addValidator(
                new StringLengthValidator(
                        " 45 caracteres m�ximo", 1, 255, false));
        
	}
	
	
	
	/**
	 * Nos retorna true si los campos
	 * son v�lidos, se debe invocar antes
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
			if(this.nroDocRef.isValid() && this.impTotMo.isValid()
					&& this.serieDocRef.isValid()
					&& this.referencia.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}

	/**
	 * Seteamos la lista del detalle para mostrarlos
	 * en la grilla
	 */
	public void setLstFormularios(ArrayList<FacturaDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FacturaDetalleVO>(FacturaDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (FacturaDetalleVO det : this.lstDetalleVO) {
				container.addBean(det);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos las lineas seleccionadas
	 */
	public void agregarFormulariosSeleccionados(ArrayList<FacturaDetalleVO> lst)
	{

		FacturaDetalleVO bean = new FacturaDetalleVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, FacturaDetalleVO> hForms = new Hashtable<String, FacturaDetalleVO>();
        
		if(lst.size() > 0)
		{
			
			/*Recorremos hash e isertamos en lista de forms a agregar*/
			/*para no duplicar formularios*/
			for (FacturaDetalleVO det : lst) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new FacturaDetalleVO();
		        bean.copiar(det);
				
		        boolean saco = this.lstDetalleAgregar.remove(det);
		        this.lstDetalleVO.add(det);
		        this.lstDetalleAgregar.add(det);
		        
				this.container.addBean(bean);
			}
			
		}
		
		this.actualizarGrillaContainer(container);

	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = lstGastos.appendHeaderRow();
	
			// Seteamos filtros de las columnas
			for (Object pid: lstGastos.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    /*Agregar field para usar el filtro*/
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				     /*Actualizar el filtro cuando este tenga un cambio en texto*/
				    filterField.addTextChangeListener(change -> {
				        
				    	/*No se pueden modificar los filtros,
				    	 * necesitamos reemplazarlos*/
				    	this.container.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.container.addContainerFilter(
				                new SimpleStringFilter(pid,
				                    change.getText(), true, false));
				    });

				    cell.setComponent(filterField);
				}
			}
			
		}catch(Exception e)
		{
			 System.out.println(e.getStackTrace());
		}
	}
	
	
	/////////////////////////////////////////////////
	
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una linea 
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(FacturaDetalleVO det)
	{

		/*Si esta el detalle en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeFormularioenLista(det.getNroDocum()))
		{
			this.actualizarFormularioLista(det);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstDetalleVO.add(det);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstDetalleVO);
		
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un VO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarFormularioLista(FacturaDetalleVO det)
	{
		int i =0;
		boolean salir = false;
		
		FacturaDetalleVO detEnLista;
		
		while( i < this.lstDetalleVO.size() && !salir)
		{
			detEnLista = this.lstDetalleVO.get(i);
			if(det.getNroDocum()== detEnLista.getNroDocum())
			{
				this.lstDetalleVO.get(i).copiar(det);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el VO en la lista
	 * de grupos de la vista
	 *
	 */
	private boolean existeFormularioenLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		FacturaDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro==aux.getNroDocum())
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void existeFormularioenListaElimina(int nro)
	{
		int i =0;
		boolean esta = false;
		
		FacturaDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro==aux.getNroDocum())
			{
				esta = true;
				this.lstDetalleVO.remove(i);
			}
			
			i++;
		}
		
	}
	
	private void actualizarGrillaContainer(BeanItemContainer<FacturaDetalleVO> container)
	{
		lstGastos.setContainerDataSource(container);
		
		//lstFormularios.getColumn("borrar").setHidable(true);
		
		List<Column> lst = lstGastos.getColumns();
		
		
		//lstGastos.getColumn("impTotMo").setHidden(true);
		
		 lstGastos.getColumn("operacion").setHidden(true);
		  lstGastos.getColumn("fechaMod").setHidden(true);
		  
		  lstGastos.getColumn("codCtaInd").setHidden(true);
		  lstGastos.getColumn("codCuenta").setHidden(true);
		  lstGastos.getColumn("codDocum").setHidden(true);
		  lstGastos.getColumn("codEmp").setHidden(true);
		  lstGastos.getColumn("codImpuesto").setHidden(true);
		  lstGastos.getColumn("codMoneda").setHidden(true);
		  //lstGastos.getColumn("codProceso").setHidable(true);;
		  lstGastos.getColumn("codRubro").setHidden(true);
		  lstGastos.getColumn("codTitular").setHidden(true);
		  //lstGastos.getColumn("cuenta").setHidable(true);;
		  lstGastos.getColumn("descProceso").setHidden(true);
		  lstGastos.getColumn("fecDoc").setHidden(true);
		  lstGastos.getColumn("fecValor").setHidden(true);
		  lstGastos.getColumn("impImpuMn").setHidden(true);
		  lstGastos.getColumn("impImpuMo").setHidden(true);
		  lstGastos.getColumn("impSubMn").setHidden(true);
		  lstGastos.getColumn("impSubMo").setHidden(true);
		  lstGastos.getColumn("linea").setHidden(true);
		  lstGastos.getColumn("impTotMn").setHidden(true);
		  lstGastos.getColumn("nomCuenta").setHidden(true);
		  lstGastos.getColumn("nomImpuesto").setHidden(true);
		  lstGastos.getColumn("nomMoneda").setHidden(true);
		  lstGastos.getColumn("nomRubro").setHidden(true);
		  lstGastos.getColumn("nomTitular").setHidden(true);
		  lstGastos.getColumn("nroTrans").setHidden(true);
		  lstGastos.getColumn("porcentajeImpuesto").setHidden(true);
		  lstGastos.getColumn("serieDocum").setHidden(true);
		  //lstGastos.getColumn("simboloMoneda").setHidden(true);
		  lstGastos.getColumn("tcMov").setHidden(true);
		  lstGastos.getColumn("usuarioMod").setHidden(true);
		  lstGastos.getColumn("nacional").setHidden(true);
		  lstGastos.getColumn("tipo").setHidden(true);
		  
		lstGastos.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		lstGastos.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		lstGastos.getColumn("impTotMo").setHeaderCaption("Importe");
		
		//lst.get(1).setWidth(400);
		
		//List<Column> lstColumn


		/*Formateamos los tama�os*/
		lstGastos.getColumn("referencia").setWidth(250);
		lstGastos.getColumn("nroDocum").setWidth(90);
		lstGastos.getColumn("codProceso").setWidth(90);
		lstGastos.getColumn("simboloMoneda").setWidth(95);
		lstGastos.getColumn("impTotMo").setWidth(100);
		
		lstGastos.getColumn("nroDocum").setHeaderCaption("Doc");
		lstGastos.getColumn("codProceso").setHeaderCaption("Proceso");
		
		lstGastos.getColumn("nroDocum").setEditable(false);
		lstGastos.getColumn("referencia").setEditable(false);
		lstGastos.getColumn("codProceso").setEditable(false);
		lstGastos.getColumn("impTotMo").setEditable(true);
		
		lstGastos.setEditorSaveCaption("Guardar");
		lstGastos.setEditorCancelCaption("Cancelar");
		
		
		
		lstGastos.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
		
		    IngresoCobroDetalleVO aux;
			GtoSaldoAux gtoSaldo;
			
			@Override
			public void preCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
	
				if(formSelecccionado != null){
	
					FacturaDetalleVO aux = obtenerGastoEnLista(formSelecccionado.getNroDocum());
			    	gtoSaldo = saldoOriginalGastos.get(formSelecccionado.getNroDocum());
			    	
		  		}
			}
	
	        @Override
	        public void postCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
	      	
	    	  
	        	FacturaDetalleVO aux2 = obtenerGastoEnLista(formSelecccionado.getNroDocum());
	    	  
	        	/*Si el importe modificado es mayor al saldo no dejamos modificar*/
	    	  
		    	if(aux2.getImpTotMo()> gtoSaldo.getSaldo())
		    	{
		    		throw new FieldGroup.CommitException("El importe no puede ser mayor al saldo ");
		    	}
		    	
		    	calcularImporteTotal();
	        }
		});
		
	}
	
	/**
	 * Retornanoms true si esta el grupoVO en la lista
	 * de grupos de la vista
	 *
	 */
	private FacturaDetalleVO obtenerGastoEnLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		FacturaDetalleVO aux = null;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro==aux.getNroDocum())
			{
				esta = true;
			}
			
			i++;
		}
		
		return aux;
	}
	
	public void inicializarComboMoneda(String cod){
		
		//this.comboMoneda = new ComboBox();
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		UsuarioPermisosVO permisosAux;
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_INGRESO_COBRO,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			
			lstMonedas = this.controlador.getMonedas(permisosAux);
			
			
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedaVO = this.seteaCotizaciones(monedaVO);
			monedasObj.addBean(monedaVO);
			
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		
		
		if(cod!=null)
		{
			try{
				this.comboMoneda.setReadOnly(false);
				this.comboMoneda.setValue(moneda);
				this.comboMoneda.setReadOnly(true);
			}catch(Exception e)
			{}
		}
		
	}
	
	
	
	@Override
	public void setInfo(Object datos) {
		
		if(datos instanceof ClienteVO){
			ClienteVO clienteVO = (ClienteVO) datos;
			this.codTitular.setValue(String.valueOf(clienteVO.getCodigo()));
			this.nomTitular.setValue(clienteVO.getNombre());
			this.tipo.setValue(clienteVO.getTipo());
		}
		
		if(datos instanceof TitularVO){
			titularVO = (TitularVO) datos;
			this.codTitular.setValue(String.valueOf(titularVO.getCodigo()));
			this.nomTitular.setValue(titularVO.getNombre());
			this.tipo.setValue(titularVO.getTipo());
		}
		
	}
	
	/**
	 * Controlamos que el total del detalle sea igual al total
	 * ingresado
	 *
	 */
	private void calcularImporteTotal(){
		
		/*Inicializamos el permisos auxilar, para obterer el TC de moneda no nacional en detalle y distinta a la moneda del cabezal*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_FACTURA,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);	
		
		double impMoCab = 0;
		double impMo = 0;
		double tcMonedaNacional = 0;
		
		double aux;
		double aux2;
		double tcAux = 0;
		CotizacionVO cotAux = null;
		
		Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
		
		try{
			tcMonedaNacional = (Double) tcMov.getConvertedValue();
		}
		catch(Exception e)
		{
			/*Si hay error en el formato del tipo de cambio quitamos todosl
			 * los detalles seleccionados*/
			
			this.tcMov.setValue(""); /*limpiamos el campo*/
			
			/*Actualizamos el container y la grilla*/
			container.removeAllItems();
			this.lstDetalleVO.clear();
			container.addAll(lstDetalleVO);
			this.actualizarGrillaContainer(container);
			
			Mensajes.mostrarMensajeError("Error en formato de Tipo de Cambio");
		}
		
		String codMonedaCab = this.getCodMonedaSeleccionada();
		//double tcAux;asd
		
		for (FacturaDetalleVO det : lstDetalleVO) {

			/*Si la moneda del cobro es igual  a la del documento*/
			if(codMonedaCab.equals(det.getCodMoneda()))
			{
				impMo += det.getImpTotMo();
			}
			/*Si la moneda del cobro es distinta a la del documento pero
			 * igual a la moneda nacional, hago el calculo al tipo de cambio
			 * de la fecha valor del cobro*/
			else if(det.isNacional() &&  !codMonedaCab.equals(det.getCodMoneda()))
			{
				aux = det.getImpTotMo() / tcMonedaNacional;
				impMo += aux;
			}
			else  /*Si no es moneda nacional y es distinto al moneda del cobro*/
			{
				
				/*Obtenemos el tipo de cambio a pesos de la moneda de la linea */
				try {
					
					if(det.isNacional()) /*si es moneda nacional el tc es 1*/
					{
						tcAux = 1;
					}
					else /*si no es nacional tomo la cotizacion de la moneda*/
					{
						cotAux = this.controlador.getCotizacion(permisoAux, fecha, det.getCodMoneda());
						tcAux = cotAux.getCotizacionVenta();
					}
					
				} catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
						| InicializandoException | NoTienePermisosException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				
				
				aux = det.getImpTotMo() * tcAux; /*Paso a moneda nacional*/
				
				aux2 = aux / tcMonedaNacional; /*Paso la moneda nacional a la del cobro*/
				
				impMo += aux2;
			}
		}
		
		//this.impTotMo.setValue(Double.toString(impMo));
		//this.impTotMo.setConvertedValue(impMo);
		importeTotalCalculado = impMo;
	}
	
	
	

	
	/**
	 * Seteamos la lista de detalle para mostrarlos
	 * en la grilla
	 */
	public void setLstDetalle(ArrayList<FacturaDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FacturaDetalleVO>(FacturaDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (FacturaDetalleVO det : this.lstDetalleVO) {
				container.addBean(det); /*Lo agregamos a la grilla*/
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	
	
	/**
	* Si el combo de Tipo es caja: ocultamos los datos del banco
	* Si el combo tipo es Banco: mostramos los datos de bando
	*
	*/
	private void mostrarDatosDeBanco(){
		
		boolean activo = false;
		
		if(this.comboTipo.getValue() != null)
		{
			String tipo = this.comboTipo.getValue().toString().trim();
			
			if(tipo.equals("Banco"))
				activo = true;
		}
		
		//this.comboBancos.setVisible(activo);
		this.comboBancos.setEnabled(activo);
		
		//this.comboCuentas.setVisible(activo);
		this.comboCuentas.setEnabled(activo);
		
		
		//this.comboMPagos.setVisible(activo);
		this.comboMPagos.setEnabled(activo);
		
		//this.serieDocRef.setVisible(activo);
		this.serieDocRef.setEnabled(activo);
		
		//this.nroDocRef.setVisible(activo);
		this.nroDocRef.setEnabled(activo);
		
	}

	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	private String getCodMonedaSeleccionada(){
		
		String codMoneda = null;
		
		//Moneda
		if(this.comboMoneda.getValue() != null){
			MonedaVO auxMoneda = new MonedaVO();
			auxMoneda = (MonedaVO) this.comboMoneda.getValue();
			codMoneda = auxMoneda.getCodMoneda();
		}
		
		return codMoneda;
	}


	
	public void inicializarCampos(){
		
		nroTrans.setConverter(Long.class);
		
		nroDocum.setConverter(Integer.class);
		nroDocum.setConversionError("Ingrese un n�mero entero");
		
		nroDocRef.setConverter(Integer.class);
		nroDocRef.setConversionError("Ingrese un n�mero entero");
		
		tcMov.setConverter(Double.class);
		tcMov.setConversionError("Error en formato de n�mero");
		
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de n�mero");
		
		tcMov.setData("ProgramaticallyChanged");
	}
	
	public void calculos(){
	
		if(cotizacionVenta != null){
			try {
				tcMov.setConvertedValue(cotizacionVenta);
			} catch (Exception e) {
				return;
				// TODO: handle exception
			}
		}
		
	}
	
	
	
	@Override
	public void setInfoLst(GastoVO gasto) {
		
		int j = 0;
		boolean salir = false;
		MonedaVO monedaVO;
		FacturaDetalleVO g;
			
		g = new FacturaDetalleVO();
		g.copiar((DocumDetalleVO)gasto);
		
		/*Seteamos el nro de linea para poder identificarlo 
		 * si se modifica  */
		g.setLinea(lstDetalleVO.size() + 1);
		
		if(!g.isNacional()){
			while(lstMonedas.size()>j && !salir){
				monedaVO = new MonedaVO();
				monedaVO = lstMonedas.get(j);
				j++;
				if(g.getCodMoneda().equals(monedaVO.getCodMoneda())){
					salir = true;
					
					if(monedaVO.getCotizacion() != 0){
						this.lstDetalleVO.add(g);
						this.lstDetalleAgregar.add(g);
						
					}
					else{
						Mensajes.mostrarMensajeError("Debe ingresar la cotizaci�n de la moneda " + monedaVO.getDescripcion());
					}
				}
			}
		}
		else{
			this.lstDetalleVO.add(g);
			this.lstDetalleAgregar.add(g);
		}
		/*Actualizamos el container y la grilla*/
		container.removeAllItems();
		container.addAll(lstDetalleVO);
		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
		/*Calculamos el importe total de todos los gastos*/
		this.calcularImporteTotal();
		
	}

	@Override
	public void setSub(String seleccion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actulaizarGrilla(GastoVO gastoVO) {
		// TODO Auto-generated method stub
		
		/*Tomo la linea selaccionada previamente para 
		 * tomar el nro de linea para poder acutalizar*/
		int linea = this.formSelecccionado.getLinea();
		
		IngresoCobroDetalleVO g;
		
		g = new IngresoCobroDetalleVO();
		g.copiar((DocumDetalleVO)gastoVO);
		
		/*Si esta el proceso en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeGastoenLista(linea))
		{
			this.actualizarGastoenLista(gastoVO);
		}
		
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstDetalleVO);
		
		this.lstGastos.setContainerDataSource(container);
		
		calcularImporteTotal();
		
		
	}
	
	/**
	 * Modificamos un gastoVO de la lista cuando
	 * se hace una acutalizacion de un gasto
	 *
	 */
	private void actualizarGastoenLista(GastoVO gastoVO)
	{
		/*Obtengo la linea del gasto seleccionado previamente*/
		int lineaSeleccionada = this.formSelecccionado.getLinea();
		
		/*Convertimos el Gasto en linea del Egreso*/
		FacturaDetalleVO g;
		g = new FacturaDetalleVO();
		g.copiar((DocumDetalleVO)gastoVO);
		g.setLinea(lineaSeleccionada);
		
		int i =0;
		boolean salir = false;
		
		FacturaDetalleVO gastoEnLista;
		
		while( i < this.lstDetalleVO.size() && !salir)
		{
			gastoEnLista = this.lstDetalleVO.get(i);
			
			if(lineaSeleccionada == gastoEnLista.getLinea()){
				
				this.lstDetalleVO.get(i).copiar(g);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el gastoVO en la lista
	 * de gastos de la vista
	 *
	 */
	private boolean existeGastoenLista(int nroLinea)
	{
		int i =0;
		boolean esta = false;
		
		FacturaDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nroLinea == aux.getLinea())
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	

	@Override
	public void actuilzarGrillaEliminado(long codigo) {
		
		/*Este no lo implementamos ya que se quita desde la linea
		 * y no desde el formulario de Gasto*/
		
	}

	@Override
	public void mostrarMensaje(String msj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String nomForm() {
		
		return "Egreso";
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
	
	public MonedaVO seteaCotizaciones(MonedaVO monedaVO){
		
		UsuarioPermisosVO permisosAux;
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_FACTURA,
						VariablesPermisos.OPERACION_LEER);
		
		Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
		CotizacionVO cotiz;
		if(monedaVO.isNacional()){
			monedaNacional = monedaVO;
		}
		else if(fecha!=null){
			
			cotiz = new CotizacionVO();
			
			try {
				
				cotiz = this.controlador.getCotizacion(permisosAux, fecha, monedaVO.getCodMoneda());
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

	@Override
	public void eliminarFact() {
		// TODO Auto-generated method stub
		try {
			
			/*Seteamos validaciones en nuevo, cuando es editar
			 * solamente cuando apreta el boton editar*/
			this.setearValidaciones(true);
			
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_FACTURA,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				
				int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
				
				if(comp != 0){
					Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
					return;
				}
				
				FacturaVO factVO = new FacturaVO();	
				
				factVO.setImpTotMo((Double) impTotMo.getConvertedValue());
				
				/*Obtenemos la cotizacion y calculamos el importe MN*/
				Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
				CotizacionVO coti = null;
				
				
				try {
					
					/////////////////////////////MONEDA//////////////////////////////////////////////////
					
					MonedaVO auxMoneda = null;
					
					//Obtenemos la moneda del cabezal
					auxMoneda = new MonedaVO();
					if(this.comboMoneda.getValue() != null){
						
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						
						/*SI EL TIPO ES CAJA TOMAMOS LA MONEDA DEL CABEZAL DEL COBRO*/
						//if(((String)comboTipo.getValue()).equals("Caja")) { 
						
							factVO.setCodMoneda(auxMoneda.getCodMoneda());
							factVO.setNomMoneda(auxMoneda.getDescripcion());
							factVO.setSimboloMoneda(auxMoneda.getSimbolo());
						//}
					}
					
					
					/////////////////////////////FIN MONEDA////////////////////////////////////////////
					
					/*Si la moneda del cabezal es nacional*/
					if(auxMoneda.isNacional()) /*Si la moneda del cabezal del cobro seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						factVO.setTcMov(1);
						factVO.setImpTotMn(factVO.getImpTotMo());
						
					}else
					{
						coti = this.controlador.getCotizacion(permisoAux, fecha, this.getCodMonedaSeleccionada());
						factVO.setTcMov(coti.getCotizacionVenta());
						factVO.setImpTotMn((factVO.getImpTotMo()*factVO.getTcMov()));
					}
					
				} catch (Exception e) {
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				factVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
				factVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
				/*Codigo y serie docum se inicializan en constructor*/
				//ingCobroVO.setNroDocum(Integer.parseInt(nroDocum.getValue()));  VER ESTO CON EL NUMERADOR
				factVO.setCodEmp(permisos.getCodEmp());
				factVO.setReferencia(referencia.getValue());
				
				factVO.setCodCtaInd("fact");
				
				
				factVO.setCodTitular(codTitular.getValue());
				factVO.setNomTitular(nomTitular.getValue());
				
				factVO.setOperacion(operacion);
				
				/*Ver los totales y tc*/
				//ingCobroVO.setImpTotMn(impTotMn);
				//ingCobroVO.setImpTotMo(impTotMn);
				//ingCobroVO.setTcMov(tcMov);
				
				/*Si es nuevo aun no tenemos el nro del cobro*/
				if(this.nroDocum.getValue() != null)
					factVO.setNroDocum(Integer.parseInt(this.nroDocum.getValue().toString().trim()));
				
				
			
				
				factVO.setUsuarioMod(this.permisos.getUsuario());
				
				if(this.operacion != Variables.OPERACION_NUEVO){
					factVO.setNroTrans((long)this.nroTrans.getConvertedValue());
				}
				else{
					factVO.setNroTrans(0);
				}
				
				
				/*Si hay detalle nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstDetalleAgregar.size() > 0)
				{
					for (FacturaDetalleVO f : this.lstDetalleAgregar) {
						
						/*Si no esta lo agregamos*/
						if(!this.existeFormularioenLista(f.getNroDocum()))
							this.lstDetalleVO.add(f);
					}
				}
					
				factVO.setCodCuenta("factura");
				factVO.setDetalle(this.lstDetalleVO);
				
				if(factVO.getDetalle().size() <= 0){
					Mensajes.mostrarMensajeError("La factura no tiene detalle");
					return;
				}
				
				
				factVO.setCodDocum("factura");
				
				this.controlador.eliminarFactura(factVO, permisoAux);
				
				this.mainView.actulaizarGrilla(factVO);
				Mensajes.mostrarMensajeOK("Se ha eliminado la factura");
				UI.getCurrent().removeWindow(sub);
				this.mainView.cerrarVentana();
			}
					
			else /*Si los campos no son v�lidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (NoExisteFacturaException |ExisteFacturaException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException | EliminandoFacturaException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
	}
	
}
