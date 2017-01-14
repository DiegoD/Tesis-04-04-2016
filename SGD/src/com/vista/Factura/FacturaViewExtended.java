package com.vista.Factura;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import com.controladores.FacturaControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Factura.*;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.NotaCredito.ExisteNotaCreditoException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.Recibo.ExisteReciboException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.AuxDetalleVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Docum.FacturaDetalleVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Gasto.GtoSaldoAux;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;
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
import com.vista.IngresoCobro.SeleccionViewExtended;
import com.vista.Validaciones.Validaciones;
import com.vista.detFactura.DetFacturaViewExtended;
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
	//private FacturaPanelExtended mainView;
	private IFacturaMain mainView;
	BeanItemContainer<FacturaDetalleVO> container;
	private FacturaDetalleVO lineaSelecccionada; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
	UsuarioPermisosVO permisoAux;
	CotizacionVO cotizacion =  new CotizacionVO();
	Double cotizacionVenta = null;
	TitularVO titularVO = new TitularVO();
	Double tcAux; /*Varible utilizada para control, si hay lineas ingresadas, no deja cambiar TC
	 				esta es variable auxiliar para tomar el tc anterior*/
	
	private Hashtable<Integer, GtoSaldoAux> saldoOriginalGastos; /*Variable auxliar para poder
	 															 controlar que el saldo del gasto quede
	 															 en negativo*/
	//String procesosCliente;
	
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
	//public FacturaViewExtended(String opera, FacturaPanelExtended main){
	public FacturaViewExtended(String opera, IFacturaMain main){
	
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
		
		form = new BusquedaViewExtended(this, new ClienteVO());
		
	
		
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
			
			lstClientes = this.controlador.getClientes(permisoAux);
			
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
				 ObteniendoClientesException  e) {

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
		
		sub = new MySub("85%", "65%" );
		sub.setModal(true);
		sub.center();
		sub.setModal(true);
		sub.setVista(form);
		sub.center();
		sub.setDraggable(true);
		UI.getCurrent().addWindow(sub);
		
	});
	

	this.btnBuscarProceso.addClickListener(click -> {
		
		BusquedaViewExtended form = new BusquedaViewExtended(this, new ProcesoVO());
		ArrayList<Object> lst = new ArrayList<Object>();
		ArrayList<ProcesoVO> lstProcesos = new ArrayList<ProcesoVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_GASTOS,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		try {
			/*Si selecciono un cliente, permitimos seleccionar proceso*/
			if(this.codTitular.getValue().toString().equals("")){
				
				Mensajes.mostrarMensajeWarning("Debe seleccionar un cliente");
			}
			else{
				
				lstProcesos = this.controlador.getProcesosCliente(permisoAux, this.codTitular.getValue().toString());
				
				
				Object obj;
				for (ProcesoVO i: lstProcesos) {
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
			}
			
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoProcesosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
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
						Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
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
   			
   			//CtaBcoVO ctaBcoAux;
   			//ctaBcoAux = new CtaBcoVO();
   	
   			
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
						
							calculos();
						}
						else{
							Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
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
					
						calculos();
					}
					else{
						Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
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
	    	
	    	if(lstDetalleVO != null ){
	    		
		    	if(lstDetalleVO.size() == 0)  /*Si no hay lineas dejo modificar TC*/
		    	{
		    		
		    		if("ProgramaticallyChanged".equals(tcMov.getData())){
			    		 tcMov.setData(null);
			             return;
			         }
			    	 
			        String value = (String) event.getProperty().getValue();
			        if(value != ""){
			        	
			        	try {
			        		cotizacionVenta = Double.valueOf(tcMov.getConvertedValue().toString());
						} catch (Exception e) {
							// TODO: handle exception
							return;
						}
			        	
			        	
			        	Double truncatedDouble = new BigDecimal(cotizacionVenta)
							    .setScale(3, BigDecimal.ROUND_HALF_UP)
							    .doubleValue();
						
			        	cotizacionVenta = truncatedDouble;
			        	
			        	if(operacion != Variables.OPERACION_LECTURA){

				        	calculos();
				        }
			        	
			    	}
		    	}else{
		    		/*DIEGO ACA ES DONDE QUIERO PONER EL VALOR ANTERIOR*/
		    		
		    		Mensajes.mostrarMensajeWarning("No se puede cambiar TC si hay lineas ingresadas");
		    		tcMov.setConvertedValue(cotizacionVenta);
		    		return;
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
				
				if(!this.chkDiferencia.getValue()){
					int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
					
					if(comp != 0){
						Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
						return;
					}
				}
				FacturaVO factVO = new FacturaVO();	
				
				factVO.setImpTotMo((Double) impTotMo.getConvertedValue());
				
				/*Obtenemos la cotizacion y calculamos el importe MN*/
				Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
				CotizacionVO coti = null;
				
				
				try {
					
					if(this.comboContCred.getValue()!= null){
						
						if(this.comboContCred.getValue().equals("contado")){
							factVO.setTipoContCred("contado");
						}
						else{
							factVO.setTipoContCred("credito");
						}
						
					}
					
					if(this.comboTipo.getValue()!= null){
						
						if(this.comboTipo.getValue().equals("Factura")){
							factVO.setTipoFactura("Factura");
						}
						else{
							factVO.setTipoFactura("PreFactura");
						}
						
					}
					
					
/////////////////////////////MONEDA//////////////////////////////////////////////////
					MonedaVO auxMoneda = null;
					
					//Obtenemos la moneda del cabezal
					auxMoneda = new MonedaVO();
					if(this.comboMoneda.getValue() != null){
						
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						
						factVO.setCodMoneda(auxMoneda.getCodMoneda());
						factVO.setNomMoneda(auxMoneda.getDescripcion());
						factVO.setSimboloMoneda(auxMoneda.getSimbolo());
						
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
				
				
				factVO.setCodProceso(Integer.valueOf(this.codProceso.getValue()));
				
			
				
				factVO.setImpSubMn((Double) impSubMn.getConvertedValue());
				factVO.setImpSubMo((Double) impSubMo.getConvertedValue());
				factVO.setImpTotMn((Double) impTotMn.getConvertedValue());
				
				factVO.setImpTotMo((Double) impTotMo.getConvertedValue());
				factVO.setImpuTotMn((Double) impuTotMn.getConvertedValue());
				factVO.setImpuTotMo((Double) impuTotMo.getConvertedValue());
				
				factVO.setNroDocum(nroDocum.getValue());
				factVO.setSerieDocum(this.serieDocum.getValue().trim());
				
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
					factVO.setNroDocum(nroDocum.getValue());
				
			
				factVO.setUsuarioMod(this.permisos.getUsuario());
				
				if(this.operacion != Variables.OPERACION_NUEVO){
					factVO.setNroTrans((long)this.nroTrans.getConvertedValue());
				}
				else{
					factVO.setNroTrans(0);
				}
				
				
				/*Si hay detalle nuevo agregado
				 * lo agregamos a la lista */
				/*
				if(this.lstDetalleAgregar.size() > 0)
				{
					for (FacturaDetalleVO f : this.lstDetalleAgregar) {
						
						//Si no esta lo agregamos
						if(!this.existeFormularioenLista(f.getNroDocum()))
							this.lstDetalleVO.add(f);
					}
				}
			 	*/
					
				factVO.setCodCuenta("factura");
				factVO.setDetalle(this.lstDetalleVO);
				
				if(factVO.getDetalle().size() <= 0){
					Mensajes.mostrarMensajeError("La factura no tiene detalle");
					return;
				}
				
				factVO.setCodDocum("fact");
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
					
					this.controlador.insertarFactura(factVO, permisoAux, true);
					this.mainView.actulaizarGrilla();
					
					Mensajes.mostrarMensajeOK("Se ha guardado la Factura");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*Si es edicion tomamos una copia del ingreso cobro, para detectar
					 * que lineas del cobro se eliminaron*/
					
					
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarFactura(factVO,ingresoCopia, permisoAux, false);
					
					this.mainView.actulaizarGrilla();
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Cobro");
					main.cerrarVentana();
					
				}
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoFacturaException | ModificandoFacturaException| NoExisteFacturaException |ExisteFacturaException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException e) {
				
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
				Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
				return;
			}
			
			try {
				
				if(val.existeReciboFactura(permisoAux, Integer.parseInt(this.nroDocum.getValue()), this.serieDocum.getValue())){
					Mensajes.mostrarMensajeError("Existe un recibo para la factura");
					UI.getCurrent().removeWindow(sub);
					return;
				}
			} catch (ExisteReciboException e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError(e.toString());
			}
			
			try {
				
				if(val.existeNCFactura(permisoAux, Integer.parseInt(this.nroDocum.getValue()), this.serieDocum.getValue())){
					Mensajes.mostrarMensajeError("Existe una nota de crédito para la factura");
					UI.getCurrent().removeWindow(sub);
					return;
				}
			} catch (ExisteNotaCreditoException e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError(e.toString());
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
					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
					return;
				}
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			try {
				if(val.existeReciboFactura(permisoAux, Integer.parseInt(this.nroDocum.getValue()), this.serieDocum.getValue())){
					Mensajes.mostrarMensajeError("Existe un recibo para la factura");
					UI.getCurrent().removeWindow(sub);
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError("Error inesperado");
			}
			try {
				if(val.existeNCFactura(permisoAux, Integer.parseInt(this.nroDocum.getValue()), this.serieDocum.getValue())){
					Mensajes.mostrarMensajeError("Existe una nota de crédito para la factura");
					UI.getCurrent().removeWindow(sub);
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError("Error inesperado");
			}
			
			MensajeExtended form = new MensajeExtended("Desea eliminar factura?",this);
		
			sub = new MySub("18%", "16%" );
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
		
		
		/*Inicalizamos listener para boton de Agregar gastos*/
		this.btnAgregarGto.addClickListener(click -> { 
			
			if(this.codTitular.getValue() != null && this.codTitular.getValue() != "" 
					&& this.fecValor.getValue() != null && this.comboMoneda.getValue() != null)
			{
				
				if(this.codProceso.getValue() != null && this.codProceso.getValue() != "" 
						&& this.codProceso.getValue() != "0" )
				{
				
					SeleccionViewExtended form = new SeleccionViewExtended(this);
					
					sub = new MySub("35%", "25%" );
					sub.setModal(true);
					sub.center();
					sub.setModal(true);
					sub.setVista(form);
					sub.center();
					sub.setClosable(false);
					sub.setResizable(false);
					sub.setDraggable(true);
					UI.getCurrent().addWindow(sub);
					
				}else{
					Mensajes.mostrarMensajeError("Debe ingresar un proceso para seleccionar gastos");
				}

			}
			else{
				Mensajes.mostrarMensajeError("Debe ingresar los datos de cabecera");
			}
		});
		
		this.cancelar.addClickListener(click -> {
			
			if(this.mainView != null){
				this.mainView.actulaizarGrilla();
				main.cerrarVentana();
			}
			else{
				UI.getCurrent().removeWindow(sub);
			}
		});
			
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya una linea seleccionada para
				 * eliminar*/
				if(lineaSelecccionada != null)
				{

					/*Recorremos las lineas
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstDetalleVO.size() && !esta)
					{
						if(lstDetalleVO.get(i).getLinea()==lineaSelecccionada.getLinea())
						{
														
							/*Quitamos el formulario seleccionado de la lista*/
							lstDetalleVO.remove(lstDetalleVO.get(i));
							//this.lstDetalleAgregar.remove(lstDetalleAgregar.get(i)); /*ver esosss*/
							lstDetalleQuitar.add(lineaSelecccionada);
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
				    	lineaSelecccionada = item.getBean(); /*Seteamos la linea
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
				if(lineaSelecccionada != null){
					
					
					GastoVO bean = new GastoVO();
					BeanItem<GastoVO> item = new BeanItem<GastoVO> (bean);
					
					item.getBean().copiar(lineaSelecccionada);
					
					/*
					item.getBean().setNroDocum(lineaSelecccionada.getNroDocum());
					item.getBean().setFecDoc(lineaSelecccionada.getFecDoc());
					item.getBean().setFecValor(lineaSelecccionada.getFecValor());
					item.getBean().setCodProceso(lineaSelecccionada.getCodProceso());
					item.getBean().setDescProceso(lineaSelecccionada.getDescProceso());
					item.getBean().setCodTitular(lineaSelecccionada.getCodTitular());
					item.getBean().setNomTitular(lineaSelecccionada.getNomTitular());
					item.getBean().setCodCuenta(lineaSelecccionada.getCodCuenta());
					item.getBean().setNomCuenta(lineaSelecccionada.getNomCuenta());
					item.getBean().setCodCtaInd(lineaSelecccionada.getCodCtaInd());
					item.getBean().setCodRubro(lineaSelecccionada.getCodRubro());
					item.getBean().setNomRubro(lineaSelecccionada.getNomRubro());
					item.getBean().setCodImpuesto(lineaSelecccionada.getCodImpuesto());
					item.getBean().setNomImpuesto(lineaSelecccionada.getNomImpuesto());
					item.getBean().setPorcentajeImpuesto(lineaSelecccionada.getPorcentajeImpuesto());
					item.getBean().setCodMoneda(lineaSelecccionada.getCodMoneda());
					item.getBean().setNomMoneda(lineaSelecccionada.getNomMoneda());
					item.getBean().setSimboloMoneda(lineaSelecccionada.getSimboloMoneda());
					item.getBean().setTcMov(lineaSelecccionada.getTcMov());
					item.getBean().setImpTotMo(lineaSelecccionada.getImpTotMo());
					item.getBean().setImpTotMn(lineaSelecccionada.getImpTotMn());
					item.getBean().setImpImpuMo(lineaSelecccionada.getImpImpuMo());
					item.getBean().setImpImpuMn(lineaSelecccionada.getImpImpuMn());
					item.getBean().setImpSubMo(lineaSelecccionada.getImpSubMo());
					item.getBean().setImpSubMn(lineaSelecccionada.getImpSubMn());
					item.getBean().setReferencia(lineaSelecccionada.getReferencia());
					item.getBean().setEstadoGasto(lineaSelecccionada.getEstadoGasto());
					*/
					
					/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
			    	if(item.getBean().getFechaMod() == null)
			    	{
			    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
			    	}
					
			    	/*Si es un gasto inicalizamos el formulario de gasto*/
			    	
			    	if(item.getBean().getCodDocum().equals("Gasto")){
			    		
			    		GastoViewExtended form = new GastoViewExtended(Variables.OPERACION_LECTURA, this, titularVO, "Factura");
				    	sub = new MySub("92%","50%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
			    		
			    	}else { /*De lo contrario es detalle (no gasto)*/
			    	
			    		/*Inicializamos variable para pasarle info al detalle*/
			   			AuxDetalleVO datosCab = this.obtenerDatosCabezalParaDetalle(); 
			    		
			    		DetFacturaViewExtended form = new DetFacturaViewExtended(Variables.OPERACION_LECTURA, this, datosCab);
				    	sub = new MySub("75%","55%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
			    	
			    	}
			    	
					
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
	
	/**
	 * Inicializamos el proceso en no asignado
	 */
	private void inicializarProcesoNoAsignado(){
		
		this.codProceso.setValue(String.valueOf("0"));
		this.descProceso.setValue(String.valueOf("No asignado"));
		
	}

	public  void inicializarForm(){
		
		this.controlador = new FacturaControlador();
					
		this.fieldGroup =  new BeanFieldGroup<FacturaVO>(FacturaVO.class);
		
		this.inicializarProcesoNoAsignado();
		
		this.impSubMo.setEnabled(false);
		this.impuTotMo.setEnabled(false);
		
		/*Inicializamos los combos*/
		this.inicializarComboMoneda(null);
		this.total.setEnabled(false);
		
		
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
		
	
		this.fecDoc.setRequired(setear);
		this.fecDoc.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.comboTipo.setRequired(setear);
		this.comboTipo.setRequiredError("Es requerido");
		
		this.comboContCred.setRequired(setear);
		this.comboContCred.setRequiredError("Es requerido");
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
		this.referencia.setRequired(setear);
		this.referencia.setRequiredError("Es requerido");
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
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
		
		
		auditoria.setDescription(
			
			"Usuario: " + fact.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + fact.getOperacion());
		
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
		
		this.inicializarComboMoneda(item.getBean().getCodMoneda());
		this.inicializarComboTipo(item.getBean());
		inicializarComboTipoContCred(item.getBean());
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
		
		
		this.btnBuscarCliente.setVisible(false);
		this.impTotMo.setEnabled(false);
		this.impSubMo.setEnabled(false);
		this.impuTotMo.setEnabled(false);
		this.comboMoneda.setEnabled(false);
		this.serieDocum.setEnabled(false);
		this.btnBuscarProceso.setVisible(false);
		this.tcMov.setEnabled(false);
		this.comboTipo.setEnabled(false);
		this.comboContCred.setEnabled(false);
		
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
		
		if(mainView == null){
			this.btnEditar.setEnabled(false);
			this.btnEliminar.setEnabled(false);
			
		}
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
		
		this.btnBuscarCliente.setVisible(false);
		this.impTotMo.setEnabled(true);
		
		if(comboTipo.getValue()!=null){
			if(comboTipo.getValue().equals("Pre Factura")){
				this.comboTipo.setEnabled(true);
			}
		}
		
		this.comboContCred.setEnabled(true);
		
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
		/*En principio no ocultamose el nro y serie que lo agregen a mano*/
		//this.nroDocum.setVisible(false);
		//this.nroDocum.setEnabled(false);
		this.impTotMo.setEnabled(true);
		this.impTotMo.setReadOnly(false);
		this.tcMov.setEnabled(true);
		importeTotalCalculado = (double) 0;
		
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
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
		
		Calendar c = Calendar.getInstance();    
		this.fecDoc.setValue(new java.sql.Date(c.getTimeInMillis()));
		this.fecValor.setValue(new java.sql.Date(c.getTimeInMillis()));
		
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
		this.btnAgregarGto.setEnabled(true);
		this.btnAgregarGto.setVisible(true);
		
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
		this.btnAgregarGto.setEnabled(false);
		this.btnAgregarGto.setVisible(false);
		
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
		
		this.nroDocum.setReadOnly(setear);
		
		this.fecDoc.setReadOnly(setear);
		
		this.fecValor.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.referencia.setReadOnly(setear);
		
		this.codTitular.setReadOnly(false);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		
		this.codProceso.setEnabled(false);
		this.descProceso.setEnabled(false);
		
		this.codTitular.setReadOnly(setear);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.referencia.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 255, false));
        
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
		
		if(this.referencia.isValid())
			valido = true;
		
		if(!this.tryParseInt(nroDocum.getValue())){
			nroDocum.setComponentError(new UserError("Debe ingresar un número entero"));
			valido = false;
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
		if(this.existeFormularioenLista(Integer.parseInt(det.getNroDocum())))
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
			if(nro==Integer.parseInt(aux.getNroDocum()))
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
			if(nro==Integer.parseInt(aux.getNroDocum()))
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
		  //lstGastos.getColumn("impImpuMo").setHidden(true);
		  lstGastos.getColumn("impSubMn").setHidden(true);
		  //lstGastos.getColumn("impSubMo").setHidden(true);
		  lstGastos.getColumn("linea").setHidden(true);
		  lstGastos.getColumn("impTotMn").setHidden(true);
		  lstGastos.getColumn("nomCuenta").setHidden(true);
		  lstGastos.getColumn("nomImpuesto").setHidden(true);
		  lstGastos.getColumn("nomMoneda").setHidden(true);
		  lstGastos.getColumn("nomRubro").setHidden(true);
		  lstGastos.getColumn("nomTitular").setHidden(true);
		  lstGastos.getColumn("nroTrans").setHidden(true);
		  lstGastos.getColumn("porcentajeImpuesto").setHidden(true);
		  lstGastos.getColumn("codProceso").setHidden(true);
		  lstGastos.getColumn("estadoGasto").setHidden(true);
		  lstGastos.getColumn("serieDocum").setHidden(true);
		  //lstGastos.getColumn("simboloMoneda").setHidden(true);
		  lstGastos.getColumn("tcMov").setHidden(true);
		  lstGastos.getColumn("usuarioMod").setHidden(true);
		  lstGastos.getColumn("nacional").setHidden(true);
		  lstGastos.getColumn("tipo").setHidden(true);
		  //lstGastos.getColumn("tipoContCred").setHidden(true);
		  
		lstGastos.setColumnOrder("nroDocum", "simboloMoneda", "impSubMo",  "impImpuMo",  "impTotMo", "referencia");
		
		lstGastos.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		lstGastos.getColumn("impTotMo").setHeaderCaption("Total");
		lstGastos.getColumn("impSubMo").setHeaderCaption("Subtotal");
		lstGastos.getColumn("impImpuMo").setHeaderCaption("Impuesto");
		
		//lst.get(1).setWidth(400);
		
		//List<Column> lstColumn


		/*Formateamos los tamaños*/
		lstGastos.getColumn("referencia").setWidth(250);
		lstGastos.getColumn("nroDocum").setWidth(90);
		//lstGastos.getColumn("codProceso").setWidth(90);
		lstGastos.getColumn("simboloMoneda").setWidth(95);
		lstGastos.getColumn("porcentajeImpuesto").setWidth(95);
		lstGastos.getColumn("impTotMo").setWidth(100);
		lstGastos.getColumn("impSubMo").setWidth(100);
		lstGastos.getColumn("impImpuMo").setWidth(100);
		
		lstGastos.getColumn("nroDocum").setHeaderCaption("Doc");
		//lstGastos.getColumn("codProceso").setHeaderCaption("Proceso");
		
		lstGastos.getColumn("nroDocum").setEditable(false);
		lstGastos.getColumn("referencia").setEditable(false);
		lstGastos.getColumn("codProceso").setEditable(false);
		lstGastos.getColumn("impTotMo").setEditable(false);
		
		lstGastos.setEditorSaveCaption("Guardar");
		lstGastos.setEditorCancelCaption("Cancelar");
		
		
		
		lstGastos.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
		
		    IngresoCobroDetalleVO aux;
			GtoSaldoAux gtoSaldo;
			
			@Override
			public void preCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
	
				if(lineaSelecccionada != null){
	
					FacturaDetalleVO aux = obtenerGastoEnLista(Integer.parseInt(lineaSelecccionada.getNroDocum()));
			    	gtoSaldo = saldoOriginalGastos.get(lineaSelecccionada.getNroDocum());
			    	
		  		}
			}
	
	        @Override
	        public void postCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
	      	
	    	  
	        	FacturaDetalleVO aux2 = obtenerGastoEnLista(Integer.parseInt(lineaSelecccionada.getNroDocum()));
	    	  
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
			if(nro==Integer.parseInt(aux.getNroDocum()))
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
	
	public void inicializarComboTipo(FacturaVO fact){
		
		if(fact.getTipoFactura().equals("Factura")){
			this.comboTipo.setValue("Factura");
		}
		else{
			this.comboTipo.setValue("Pre Factura");
		}
	}
	
	public void inicializarComboTipoContCred(FacturaVO fact){
		
		if(fact.getTipoContCred().equals("contado")){
			this.comboContCred.setValue("Contado");
		}
		else{
			this.comboContCred.setValue("Credito");
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
		
		if(datos instanceof ProcesoVO){
			//codProceso descProceso
			ProcesoVO procesoVO = (ProcesoVO) datos;
			this.codProceso.setValue(String.valueOf(procesoVO.getCodigo()));
			this.descProceso.setValue(String.valueOf(procesoVO.getDescripcion()));
		}
		
	}
	
	/**
	 * Controlamos que el total del detalle sea igual al total
	 * ingresado
	 *
	 */
	private void calcularImporteTotal(){
		
		
		double impuTotMO = 0;
		double impuTotMN = 0;
		
		double impSubMO = 0;
		double impSubMN = 0;
		
		double impMo = 0;
		double impMn = 0;
		
		double tcMonedaNacional = 0;
		
		Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
		
		try{
			tcMonedaNacional = Double.valueOf(tcMov.getConvertedValue().toString());
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

			
			impMo += det.getImpTotMo();
			impMn += det.getImpTotMn();
			
			impuTotMO += det.getImpImpuMo();
			impuTotMN += det.getImpImpuMn();
			
			impSubMO += det.getImpSubMo();
			impSubMN += det.getImpSubMn();
			
		}
		Double truncatedImpTotMo = new BigDecimal(impMo).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double truncatedImpTotMn = new BigDecimal(impMn).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		
		Double truncatedImpuTotMo = new BigDecimal(impuTotMO).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double truncatedImpuTotMn = new BigDecimal(impuTotMN).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Double truncatedImpSubMo = new BigDecimal(impSubMO).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double truncatedImpSubMn = new BigDecimal(impSubMN).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		//this.impTotMo.setValue(Double.toString(impMo));
		this.impTotMo.setConvertedValue(truncatedImpTotMo);
		

		
		this.impTotMn.setConvertedValue(truncatedImpTotMn);
		
		this.impuTotMo.setConvertedValue(truncatedImpuTotMo);
		this.impuTotMn.setConvertedValue(truncatedImpuTotMn);

		this.impSubMo.setConvertedValue(truncatedImpSubMo);
		this.impSubMn.setConvertedValue(truncatedImpSubMn);
		
		importeTotalCalculado = impMo;
		Double truncatedDouble = new BigDecimal(importeTotalCalculado)
			    .setScale(2, BigDecimal.ROUND_HALF_UP)
			    .doubleValue();
		
		importeTotalCalculado = truncatedDouble;
		this.total.setConvertedValue(importeTotalCalculado);
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
		calcularImporteTotal();
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
		
//		nroDocum.setConverter(Integer.class);
//		nroDocum.setConversionError("Ingrese un número entero");
		
		tcMov.setConverter(BigDecimal.class);
		tcMov.setConversionError("Error en formato de número");
		
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de número");
		
		impTotMn.setConverter(Double.class);
		impTotMn.setConversionError("Error en formato de número");
		
		impuTotMn.setConverter(Double.class);
		impSubMo.setConverter(Double.class);
		impSubMn.setConverter(Double.class);
		
		
		impuTotMo.setConverter(Double.class);
		impuTotMn.setConverter(Double.class);
		
		impuTotMn.setConversionError("Error en formato de número");
		impSubMo.setConversionError("Error en formato de número");
		impSubMn.setConversionError("Error en formato de número");
		
		this.impuTotMo.setConversionError("Error en formato de número");
		
		total.setConverter(Double.class);
		total.setConversionError("Error en formato de número");
		
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
		
		try{
		
			int j = 0;
			boolean salir = false;
			MonedaVO monedaVO;
			FacturaDetalleVO g;
				
			g = new FacturaDetalleVO();
			g.copiar((DocumDetalleVO)gasto);
			
			/*Seteamos el nro de linea para poder identificarlo 
			 * si se modifica  */
			g.setLinea(lstDetalleVO.size() + 1);
			
			double tcAux = Double.valueOf(tcMov.getConvertedValue().toString());
			
			if(!g.isNacional()){
				while(lstMonedas.size()>j && !salir){
					monedaVO = new MonedaVO();
					monedaVO = lstMonedas.get(j);
					j++;
					if(g.getCodMoneda().equals(monedaVO.getCodMoneda())){
						salir = true;
						
						if(tcAux != 0){
							this.lstDetalleVO.add(g);
							this.lstDetalleAgregar.add(g);
							
						}
						else{
							Mensajes.mostrarMensajeError("Debe ingresar la cotización de la moneda " + monedaVO.getDescripcion());
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
		
		}catch(Exception e){
			
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
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
		int linea = this.lineaSelecccionada.getLinea();
		
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
		int lineaSeleccionada = this.lineaSelecccionada.getLinea();
		
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
		
		return "Factura";
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		
		int j= 0;
		boolean salir = false;
		MonedaVO monedaVO;
		FacturaDetalleVO f;
		for (Object obj : lstDatos) {
			j = 0;
			salir = false;
			
			f = new FacturaDetalleVO();
			f.copiar((DocumDetalleVO)obj);
			
			if(!f.isNacional()){
				
				while(lstMonedas.size()>j && !salir){
					monedaVO = new MonedaVO();
					monedaVO = lstMonedas.get(j);
					j++;
					if(f.getCodMoneda().equals(monedaVO.getCodMoneda())){
						salir = true;
						
						double tcAux = Double.valueOf(tcMov.getConvertedValue().toString());
						
						if(tcAux != 0){
							this.lstDetalleVO.add(f);
							this.lstDetalleAgregar.add(f);
							
							/*Tambien agrefamos a la lista de los saldos originales
							 * para poder controlar que no ingresen un saldo mayor al que tien el gasto*/
							GtoSaldoAux saldoAux =  new GtoSaldoAux(Integer.parseInt(f.getNroDocum()), f.getImpTotMo());
							this.saldoOriginalGastos.put(saldoAux.getNroDocum(),saldoAux);
						}
						else{
							Mensajes.mostrarMensajeError("Debe ingresar la cotización de la moneda " + monedaVO.getDescripcion());
						}
					}
				}
			}
			else{
				this.lstDetalleVO.add(f);
				this.lstDetalleAgregar.add(f);
				
				/*Tambien agrefamos a la lista de los saldos originales
				 * para poder controlar que no ingresen un saldo mayor al que tien el gasto*/
				GtoSaldoAux saldoAux =  new GtoSaldoAux(Integer.parseInt(f.getNroDocum()), f.getImpTotMo());
				this.saldoOriginalGastos.put(saldoAux.getNroDocum(),saldoAux);
			}
		}
			
		/*Actualizamos el container y la grilla*/
		container.removeAllItems();
		container.addAll(lstDetalleVO);
		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
		/*Calculamos el importe total de todos los gastos*/
		
		this.calcularImporteTotal();
		
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
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_FACTURA,
							VariablesPermisos.OPERACION_BORRAR);	
			
			this.controlador.eliminarFactura(fieldGroup.getItemDataSource().getBean(), permisoAux);
			
			this.mainView.actulaizarGrilla();
			
			UI.getCurrent().removeWindow(sub);
			
			this.mainView.cerrarVentana();
			
			} catch (NoExisteFacturaException |ExisteFacturaException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException | EliminandoFacturaException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
	}
	
	public void agregarGasto(){
		try {
		
		UI.getCurrent().removeWindow(sub);
		BusquedaViewExtended form = new BusquedaViewExtended(this, new GastoVO());
		
		sub = new MySub("93%", "64%" );
		sub.setModal(true);
		sub.setVista(form);

		sub.center();
		
		String codCliente;/*Codigo del cliente para obtener los gastos a cobrar del mismo*/
		int codProceso;
		String codMoneda = null;
		
		/*Obtenemos moneda*/
		MonedaVO auxMoneda = null;
		
		//Obtenemos la moneda del cabezal
		auxMoneda = new MonedaVO();
		if(this.comboMoneda.getValue() != null){
			
			auxMoneda = (MonedaVO) this.comboMoneda.getValue();
			
			codMoneda = auxMoneda.getCodMoneda(); 
		}
		
		/*Obtenemos los formularios que no estan en el grupo
		 * para mostrarlos en la grilla para seleccionar*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO) )
		{
			/*Si la operacion es nuevo, ponemos el  codGrupo vacio
			 * asi nos trae todos los grupos disponibles*/
			codCliente = this.codTitular.getValue().toString().trim();
			codProceso = Integer.valueOf(this.codProceso.getValue().toString());
		}
		else 
		{
			/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
			codCliente = fieldGroup.getItemDataSource().getBean().getCodTitular();
			codProceso = fieldGroup.getItemDataSource().getBean().getCodProceso();
			
		}
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_FACTURA,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);
		


		
		/*Obtenemos los gastos con saldo del cliente*/
		ArrayList<GastoVO> lstGastosConSaldo = this.controlador.getGastosConSaldo(permisoAux, codCliente, codProceso, codMoneda);
		
		/*Hacemos una lista auxliar para pasarselo al BusquedaViewExtended*/
		ArrayList<Object> lst = new ArrayList<Object>();
		Object obj;
		for (GastoVO i: lstGastosConSaldo) {
			
			/*Verificamos que el gasto ya no esta en la grilla*/
			if(!this.existeFormularioenLista(Integer.parseInt(i.getNroDocum())))
			{
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
		}
		
		form.inicializarGrilla(lst);
		
		UI.getCurrent().addWindow(sub);

		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
	}
	
	
	public void agregarDetalle(){
		try {
		
			UI.getCurrent().removeWindow(sub);
			
			AuxDetalleVO detalleCab = this.obtenerDatosCabezalParaDetalle();
			
			DetFacturaViewExtended form = new DetFacturaViewExtended(Variables.OPERACION_NUEVO, this, detalleCab );
			
			sub = new MySub("75%","55%");
			sub.setModal(true);
			sub.setVista(form);
			//sub.setWidth("50%");
			//sub.setHeight("50%");
			sub.center();
			
			UI.getCurrent().addWindow(sub);

		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
	}
	
	/***
	 * Nos retorna los campos necesarios para 
	 * pasarle al detalle
	 */
	private AuxDetalleVO obtenerDatosCabezalParaDetalle(){
		
		AuxDetalleVO datosCab = new AuxDetalleVO();
		
		/*Obtenemos moneda del cabezal para pasarle al detalle*/
		MonedaVO auxMoneda = new MonedaVO();
		auxMoneda = (MonedaVO) comboMoneda.getValue();
			
		datosCab.setCodMoneda(auxMoneda.getCodMoneda());
		datosCab.setTc(Double.valueOf(tcMov.getConvertedValue().toString()));
			
		if(this.codProceso != null ){
			if(this.codProceso.getValue() != "")
				datosCab.setCodProceso(Integer.valueOf(this.codProceso.getValue()));
			else{
				datosCab.setCodProceso(0);
			}
		}
		else{
			datosCab.setCodProceso(0);
		}
		
		datosCab.setCodTitular(codTitular.getValue());
		
		datosCab.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
		datosCab.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
		
		return datosCab;
	}
	

	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	    }  
	}

	
}
