package com.vista.Recibo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.controladores.ReciboControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Factura.ObteniendoFacturasException;
import com.excepciones.Recibo.*;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.FacturaSaldoAux;
import com.valueObject.MonedaVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Docum.ReciboDetalleVO;
import com.valueObject.Docum.ReciboVO;
import com.valueObject.banco.BancoVO;
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
import com.vista.Validaciones.Validaciones;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

public class ReciboViewExtended extends ReciboViews implements IBusqueda, IMensaje{

	private BeanFieldGroup<ReciboVO> fieldGroup;
	private ArrayList<ReciboDetalleVO> lstDetalleVO; /*Lista de detalle del recibo*/
	private ArrayList<ReciboDetalleVO> lstDetalleAgregar; /*Lista de detalle a agregar*/
	private ArrayList<ReciboDetalleVO> lstDetalleQuitar; /*Lista de detalle a quitar*/
	private ReciboControlador controlador;
	private String operacion;
	private ReciboPanelExtended mainView;
	BeanItemContainer<ReciboDetalleVO> container;
	private ReciboDetalleVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
	UsuarioPermisosVO permisoAux;
	CotizacionVO cotizacion =  new CotizacionVO();
	Double cotizacionVenta = null;
	Double importeTotalCalculado;
	Validaciones val = new Validaciones();
	
	MonedaVO monedaNacional = new MonedaVO();
	
	private ArrayList<FacturaSaldoAux> saldoOriginalFact; /*Variable auxliar para poder
	 															 controlar que el saldo de la factura quede
	 															 en negativo*/
	
	ReciboVO ingresoCopia; /*Variable utilizada para la modificacion del recibo,
	 							 para poder detectar las lineas eliminadas del cobro */
	
	boolean cambioMoneda;
	
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
	
	MySub sub;
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ReciboViewExtended(String opera, ReciboPanelExtended main){
	
	
	this.cambioMoneda = false;
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	saldoOriginalFact = new ArrayList<FacturaSaldoAux>();
	
	this.operacion = opera;
	this.mainView = main;
	lstGastos.setEditorBuffered(true);
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstDetalleAgregar = new ArrayList<ReciboDetalleVO>();
	this.lstDetalleQuitar = new ArrayList<ReciboDetalleVO>();
	
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
						VariablesPermisos.FORMULARIO_RECIBO,
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
		
		sub = new MySub("85%", "65%" );
		sub.setModal(true);
		sub.center();
		sub.setModal(true);
		sub.setVista(form);
		sub.center();
		sub.setDraggable(true);
		UI.getCurrent().addWindow(sub);
		
	});
	
	/**
	* Agregamos listener al combo de tipo (banco, caja), determinamos si mostramos
	* los campos del banco o no;
	*
	*/
	comboBancos.addValueChangeListener(new Property.ValueChangeListener() {

   		@Override
		public void valueChange(ValueChangeEvent event) {
   			BancoVO bcoAux;
			
			if(comboBancos.getValue() != null){
				bcoAux = new BancoVO();
				bcoAux = (BancoVO) comboBancos.getValue();
				
				inicializarComboCuentas(bcoAux.getCodigo(), "Banco");
			}		
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
									VariablesPermisos.FORMULARIO_RECIBO,
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
					// TODO Auto-generated catch block
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
   							VariablesPermisos.FORMULARIO_RECIBO,
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cotizacion.getCotizacionVenta() != 0){
					cotizacionVenta = cotizacion.getCotizacionVenta();
				}
				else{
					Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
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
							Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
							comboMoneda.setValue(monedaNacional);
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
   							VariablesPermisos.FORMULARIO_RECIBO,
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cotizacion.getCotizacionVenta() != 0){
					cotizacionVenta = cotizacion.getCotizacionVenta();
				}
				else{
					Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
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
   							VariablesPermisos.FORMULARIO_RECIBO,
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
						Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
						comboMoneda.setValue(monedaNacional);
						return;
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
   			/*Si ya hay ingresado un gasto no lo dejamos cambiar la moneda*/
   			if(lstDetalleVO.size()>0)
   			{
   				calcularImporteTotal();
   			}
		}
    });
    
    this.tcMov.addValueChangeListener(new Property.ValueChangeListener() {
		
	    public void valueChange(ValueChangeEvent event) {
	    	
	    	if(lstDetalleVO != null) {
	    		
	    		if(lstDetalleVO.size() == 0) /*Si no hay lineas dejo modificar TC*/
		    	{
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
				/*Inicializamos VO d	e permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_RECIBO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				this.calcularImporteTotal(); /*Calculamos nuevamente por si se cambio en grilla el importe de un gasto*/		
				
				int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
				
				if(comp != 0){
					Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
					return;
				}
				ReciboVO ingCobroVO = new ReciboVO();	
				
				ingCobroVO.setImpTotMo((Double) impTotMo.getConvertedValue());
				
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
						
						ingCobroVO.setCodMoneda(auxMoneda.getCodMoneda());
						ingCobroVO.setNomMoneda(auxMoneda.getDescripcion());
						ingCobroVO.setSimboloMoneda(auxMoneda.getSimbolo());
					}


					/////////////////////////////FIN MONEDA////////////////////////////////////////////
					if(auxMoneda.isNacional()) /*Si la moneda seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						ingCobroVO.setTcMov(1);
						ingCobroVO.setImpTotMn(ingCobroVO.getImpTotMo());
						
					}else
					{
						ingCobroVO.setTcMov((double) tcMov.getConvertedValue());
						ingCobroVO.setImpTotMn((ingCobroVO.getImpTotMo()*ingCobroVO.getTcMov()));
					}
					
				} catch (Exception e) {
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				ingCobroVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
				ingCobroVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
				ingCobroVO.setCodEmp(permisos.getCodEmp());
				ingCobroVO.setReferencia(referencia.getValue());
				
				ingCobroVO.setCodCtaInd("ingcobro");
				
				
				ingCobroVO.setCodTitular(codTitular.getValue());
				ingCobroVO.setNomTitular(nomTitular.getValue());
				
				ingCobroVO.setOperacion(operacion);
				
				/*Si es nuevo aun no tenemos el nro del cobro*/
				if(this.nroDocum.getValue() != null)
					ingCobroVO.setNroDocum(this.nroDocum.getValue().toString().trim());
				
				
				/*Si es banco tomamos estos cmapos de lo contrario caja*/
				if(this.comboTipo.getValue().toString().equals("Banco")){
				
					ingCobroVO.setmPago((String)comboMPagos.getValue());
					
					if(ingCobroVO.getmPago().equals("transferencia"))
					{
						ingCobroVO.setCodDocRef("tranrec");
						
						ingCobroVO.setSerieDocRef("0");
					}
					else if(ingCobroVO.getmPago().equals("Cheque"))
					{
						ingCobroVO.setCodDocRef("cheqrec");
						ingCobroVO.setNroDocRef(nroDocRef.getValue().trim());
						ingCobroVO.setSerieDocRef(serieDocRef.getValue().trim());
						
					}else
					{
						
						ingCobroVO.setCodDocRef("0");
						ingCobroVO.setNroDocRef("0");
						ingCobroVO.setSerieDocRef("0");
					}
												
					//Datos del banco y cuenta
					CtaBcoVO auxctaBco = new CtaBcoVO();
					if(this.comboCuentas.getValue() != null){
						
						auxctaBco = (CtaBcoVO) this.comboCuentas.getValue();
						
					}
					
					ingCobroVO.setCodBanco(auxctaBco.getCodBco());
					ingCobroVO.setCodCtaBco(auxctaBco.getCodigo());
					ingCobroVO.setNomCtaBco(auxctaBco.getNombre());
					ingCobroVO.setCodMonedaCtaBco(auxctaBco.getMonedaVO().getCodMoneda());
					ingCobroVO.setNacional(auxctaBco.getMonedaVO().isNacional());
					/*Falta poner el nombre de la cuenta*/
					
				}
				else {
					
					if(((String)comboTipo.getValue()).equals("Caja"))
					{
						ingCobroVO.setCodBanco("0");
						ingCobroVO.setNomBanco("0");
						
						ingCobroVO.setCodCtaBco("0");
						ingCobroVO.setNomCtaBco("0");
						
						ingCobroVO.setCodDocRef("0");
						ingCobroVO.setNroDocRef("0");
						ingCobroVO.setSerieDocRef("0");
						
						ingCobroVO.setmPago("Caja");
					}
								
				}
				
				ingCobroVO.setUsuarioMod(this.permisos.getUsuario());
				
				if(this.operacion != Variables.OPERACION_NUEVO){
					ingCobroVO.setNroTrans((long)this.nroTrans.getConvertedValue());
				}
				else{
					ingCobroVO.setNroTrans(0);
				}
				
				

				ingCobroVO.setCodCuenta("recibo");
				
				
				
				for (ReciboDetalleVO detVO : this.lstDetalleVO) {
					
					
					detVO.setImpTotMn(detVO.getImpTotMo() * detVO.getTcMov());
					
				}
				
				ingCobroVO.setDetalle(this.lstDetalleVO);
				
				if(ingCobroVO.getDetalle().size() <= 0){
					Mensajes.mostrarMensajeError("El cobro no tiene detalle");
					return;
				}
				
				 /*Obtenemos la moneda de la cuenta*/
			    //Datos del banco y cuenta y moneda de la cuenta
			    CtaBcoVO auxctaBco = new CtaBcoVO();
			    if(this.comboCuentas.getValue() != null){
			     
			    	auxctaBco = (CtaBcoVO) this.comboCuentas.getValue();
			     
			    }
			    
			    /*Seteamos la moneda de la cta del banco*/
			    ingCobroVO.setCodMonedaCtaBco(auxctaBco.getMonedaVO().getCodMoneda());
			    ingCobroVO.setNacionalMonedaCtaBco(auxctaBco.getMonedaVO().isNacional());
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
					this.controlador.insertarRecibo(ingCobroVO, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Cobro");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*Si es edicion tomamos una copia del ingreso cobro, para detectar
					 * que lineas del cobro se eliminaron*/
					
					
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarRecibo(ingCobroVO,ingresoCopia, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Cobro");
					main.cerrarVentana();
					
				}
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (ModificandoReciboException| NoExisteReciboException |InsertandoReciboException| ExisteReciboException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}
			
		});
	
	
	
	
	
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
		try {
			
			permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_RECIBO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
				Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
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
							VariablesPermisos.FORMULARIO_RECIBO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MensajeExtended form = new MensajeExtended("Elimina el recibo?",this);
			
			
			
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
				ArrayList<FacturaVO> lstFacturas = new ArrayList<FacturaVO>();
				ArrayList<Object> lst = new ArrayList<Object>();
				MonedaVO auxMoneda = null;
				
				BusquedaViewExtended form2 = new BusquedaViewExtended(this, new FacturaVO());
				
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_RECIBO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(comboMoneda.getValue()!= ""){
   					auxMoneda = (MonedaVO) comboMoneda.getValue();
				}
				
				try {	
					
					lstFacturas = this.controlador.getFacturasConSaldo(permisoAux, auxMoneda.getCodMoneda(), this.codTitular.getValue().trim());
				
				}catch(ConexionException| InicializandoException| ObteniendoPermisosException| NoTienePermisosException| ObteniendoFacturasException e){
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				Object obj;
				for (FacturaVO i: lstFacturas) {
					obj = new Object();
					obj = (Object)i;
					lst.add(obj);
				}
				
				try {
					
					form2.inicializarGrilla(lst);
				
					
				} catch (Exception e) {
					
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
				
				sub = new MySub("85%", "65%" );//sub = new MySub("40%", "25%" );
				sub.setModal(true);
				sub.center();
				sub.setModal(true);
				sub.setVista(form2);
				sub.center();
				sub.setClosable(false);
				sub.setResizable(false);
				sub.setDraggable(true);
				UI.getCurrent().addWindow(sub);
				

			}
			else{
				Mensajes.mostrarMensajeError("Debe ingresar los datos de cabecera");
			}
		});
			
		this.cancelar.addClickListener(click -> {
			
			this.mainView.actulaizarGrilla(this.ingresoCopia);
			main.cerrarVentana();
		});
			
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
			
			boolean esta = false;	

			try {
			
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(formSelecccionado != null)
				{
	
					/*Recorremos las facturas del recibo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstDetalleVO.size() && !esta)
					{
						//if(lstDetalleVO.get(i).getCodDocum().equals("Gasto")){
							if(lstDetalleVO.get(i).getLinea()==formSelecccionado.getLinea())
							{
								/*Tambien lo quitamos de la lista de los saldos originales
								 * para poder controlar que no ingresen un saldo mayor al que tien la factura*/
							
								FacturaSaldoAux saldoAux = new FacturaSaldoAux();
								saldoAux.setCodDocum(lstDetalleVO.get(i).getCodDocum());
								saldoAux.setNroDocum(lstDetalleVO.get(i).getNroDocum());
								saldoAux.setSerie(lstDetalleVO.get(i).getSerieDocum());
								saldoAux.setSaldo(lstDetalleVO.get(i).getImpTotMo());
								
								this.quitarSaldoFacturaAux(saldoAux.getNroDocum(),saldoAux.getSerie(), saldoAux.getCodDocum());
								
								/*Quitamos el formulario seleccionado de la lista*/
								lstDetalleVO.remove(lstDetalleVO.get(i));
								lstDetalleQuitar.add(formSelecccionado);
							
								esta = true;
							}
						//}
						//else {
							
							
						//}
	
						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstDetalleVO);
						//lstFormularios.setContainerDataSource(container);
						this.actualizarGrillaContainer(container);
						
						this.calcularImporteTotal();
					}
					
					formSelecccionado = null;
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un gasto*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un gasto para quitar");
				}
	
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
			
		this.btnEditarItem.addClickListener(click -> {
			
			boolean esta = false;	

			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(formSelecccionado != null){
					
					if(formSelecccionado.getCodDocum().equals("Proc")){
						
						ProcesoVO proceso = new ProcesoVO();
						ProcesoVO datos = new ProcesoVO();
						
						UsuarioPermisosVO permisosAux;
						permisosAux = 
								new UsuarioPermisosVO(this.permisos.getCodEmp(),
										this.permisos.getUsuario(),
										VariablesPermisos.FORMULARIO_RECIBO,
										VariablesPermisos.OPERACION_LEER);
						
						
						
						proceso.setCodCliente(codTitular.getValue());
						proceso.setNomCliente(nomTitular.getValue());
						
						MonedaVO auxMoneda = new MonedaVO();
			   			auxMoneda = (MonedaVO) comboMoneda.getValue();
			   			
			   			
						proceso.setCodMoneda(auxMoneda.getCodMoneda());
						proceso.setDescMoneda(auxMoneda.getDescripcion());
						proceso.setSimboloMoneda(auxMoneda.getSimbolo());
						proceso.setCodigo(Integer.valueOf(formSelecccionado.getCodProceso()));
						proceso.setDescripcion(formSelecccionado.getDescProceso());
						proceso.setCodRubro(formSelecccionado.getCodRubro());
						proceso.setNomRubro(formSelecccionado.getNomRubro());
						proceso.setCodCuenta(formSelecccionado.getCodCuenta());
						proceso.setNomCuenta(formSelecccionado.getNomCuenta());
						proceso.setImpMo(formSelecccionado.getImpTotMo());
						proceso.setNroDocum(formSelecccionado.getNroDocum());
						proceso.setLinea(formSelecccionado.getLinea());
						proceso.setObservaciones(formSelecccionado.getReferencia());
						proceso.setCodImpuesto(formSelecccionado.getCodImpuesto());
						
						//datos = controlador.getProceso(permisosAux, proceso.getCodigo());
						
						//proceso.setCarpeta(datos.getCarpeta());
						//proceso.setNomDocum(datos.getNomDocum());
						
						
						//BusquedaViewExtended form = new BusquedaViewExtended(this, new ProcesoVO());
						IngresoCobroProcesoViewExtended form = new IngresoCobroProcesoViewExtended("EDITAR", this, proceso);
						sub = new MySub("77%", "45%" );
						sub.setModal(true);
						sub.setVista(form);
						sub.center();
						
						UI.getCurrent().addWindow(sub);
						
					}
					else if(formSelecccionado.getCodDocum().equals("Gasto")){
						Mensajes.mostrarMensajeWarning("El importe del gasto se edita en la grilla directamente");
					}
				}
			
				else /*De lo contrario mostramos mensaje que debe selcionar un gasto*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un gasto para editar");
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
		    			BeanItem<ReciboDetalleVO> item = container.getItem(lstGastos.getSelectedRow());
				    	formSelecccionado = item.getBean(); /*Seteamos el formulario
			    	 									seleccionado para poder quitarlo*/
		    		}
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
			
	}

	

	public  void inicializarForm(){
		
		this.controlador = new ReciboControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ReciboVO>(ReciboVO.class);
		
		/*Mostramos o ocultamos los datos del Banco, dependiendo del combo tipo (banco, caja)*/
		this.mostrarDatosDeBanco();
		
		/*Inicializamos los combos*/
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null, "");
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
		
		/*De Bco*/
		if(this.comboTipo.getValue()!=null){
			if(this.comboTipo.getValue().equals("Banco") && this.comboTipo.getValue()!=null)
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
		
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ReciboVO> item)
	{
		try{
			
		this.fieldGroup.setItemDataSource(item);
		
		
		ReciboVO rec = new ReciboVO();
		rec = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(rec.getFechaMod());
		
		/*Inicializamos los combos*/
		this.inicializarComboBancos(rec.getCodBanco());
		this.inicializarComboCuentas(rec.getCodCtaBco(), "CuentaBanco");
		this.inicializarComboMoneda(rec.getCodMoneda());
		
		//Se setea manual ya que si no lo carga del detalle
		this.tcMov.setConvertedValue(rec.getTcMov());
		
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
		
		this.nroDocum.setReadOnly(true);
		this.serieDocRef.setReadOnly(false);
		this.serieDocRef.setValue(item.getBean().getSerieDocRef());
		//this.comboMPagos = new ComboBox();
		
		this.comboMPagos.setImmediate(true);
		this.comboMPagos.setNullSelectionAllowed(false);
		this.comboMPagos.setReadOnly(false);
		this.comboMPagos.addItem("Sin Asignar");
		this.comboMPagos.addItem("Cheque");
		this.comboMPagos.addItem("Transferencia");
		/*Seteamos el combo de medio de pago*/
		if(item.getBean().getmPago().equals("0"))
		{
			this.comboMPagos.setValue("Sin Asignar");
			
		}else {
			this.comboMPagos.setValue(item.getBean().getmPago());
		}
		
		auditoria.setDescription(
			
			"Usuario: " + rec.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + rec.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		/*Copiamos la variable para la modificacion*/
		this.ingresoCopia = new ReciboVO();
		this.ingresoCopia.copiar(rec);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RECIBO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RECIBO, VariablesPermisos.OPERACION_BORRAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		/*Deshabilitamos botn aceptar*/
		this.disableBotonAceptar();
		this.disableBotonAgregarQuitar();
		
		if(permisoEliminar)
			this.enableBotonEliminar();
		
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ReciboDetalleVO>(ReciboDetalleVO.class);
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		this.importeTotalCalculado = (Double)impTotMo.getConvertedValue();
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		int linea = 0;
		
		if(this.lstDetalleVO != null)
		{
			for (ReciboDetalleVO detVO : this.lstDetalleVO) {
				
				detVO.setLinea(linea);
				container.addBean(detVO);
				FacturaSaldoAux saldoAux =  new FacturaSaldoAux(detVO.getCodDocum(), detVO.getSerieDocum() ,detVO.getNroDocum(), detVO.getImpTotMo());
				this.saldoOriginalFact.add(saldoAux);
				
				linea ++;
			}
		}
		this.actualizarGrillaContainer(container);
		
		this.btnBuscarCliente.setVisible(false);
						
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RECIBO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		this.importeTotalCalculado = (Double)impTotMo.getConvertedValue();
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			this.disableBotonLectura();
			this.enableBotonAgregarQuitar();
			
			this.disableBotonEliminar();
			
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
		/*Si es nuevo ocultamos el nroDocum (ya que aun no tenemos el numero)*/
		this.nroDocum.setVisible(false);
		this.nroDocum.setEnabled(false);
		importeTotalCalculado = (double) 0;
		
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RECIBO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
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
		this.lstDetalleAgregar = new ArrayList<ReciboDetalleVO>();
		this.lstDetalleQuitar = new ArrayList<ReciboDetalleVO>();
		this.lstDetalleVO = new ArrayList<ReciboDetalleVO>();
		
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<ReciboDetalleVO>(ReciboDetalleVO.class);
		
		
		
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
		
		this.fecDoc.setReadOnly(false);
		
		this.serieDocRef.setReadOnly(false);
		
		this.nroDocRef.setReadOnly(false);
		
		this.impTotMo.setReadOnly(false);
		this.impTotMo.setEnabled(true);
		this.tcMov.setReadOnly(false);
		this.monedaBanco.setEnabled(false);
		this.cuentaBanco.setEnabled(false);
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
		
		this.btnEditarItem.setEnabled(true);
		this.btnEditarItem.setVisible(true);
		
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
		
		this.btnEditarItem.setEnabled(false);
		this.btnEditarItem.setVisible(false);
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
		
		this.nomTitular.setReadOnly(setear);
		
		this.comboTipo.setReadOnly(setear);
		
		this.comboBancos.setReadOnly(setear);
		
		this.comboCuentas.setReadOnly(setear);
		
		this.fecDoc.setReadOnly(setear);
		
		this.fecValor.setReadOnly(setear);
		
		this.comboMPagos.setReadOnly(setear);
		
		this.serieDocRef.setReadOnly(setear);
		
		this.nroDocRef.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.impTotMo.setReadOnly(setear);
		
		this.tcMov.setReadOnly(setear);
		
		this.referencia.setReadOnly(setear);
		
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
		//nroDocRef.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
		//impTotMo.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
        this.serieDocRef.addValidator(
                new StringLengthValidator(
                     " 4 caracteres máximo", 1, 4, false));
        
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
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstFormularios(ArrayList<ReciboDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ReciboDetalleVO>(ReciboDetalleVO.class);
		
		int linea = 0;
		if(this.lstDetalleVO != null)
		{
			for (ReciboDetalleVO det : this.lstDetalleVO) {
				
				det.setLinea(linea);
				
				container.addBean(det);
				
				FacturaSaldoAux saldoAux =  new FacturaSaldoAux(det.getCodDocum(), det.getSerieDocum() , det.getNroDocum(), det.getImpTotMo());
				this.saldoOriginalFact.add(saldoAux);
				
				linea ++;
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos los formularios seleccionados
	 */
	public void agregarFormulariosSeleccionados(ArrayList<ReciboDetalleVO> lst)
	{

		ReciboDetalleVO bean = new ReciboDetalleVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, ReciboDetalleVO> hForms = new Hashtable<String, ReciboDetalleVO>();
        
		if(lst.size() > 0)
		{
			
			/*Recorremos hash e isertamos en lista de forms a agregar*/
			/*para no duplicar formularios*/
			for (ReciboDetalleVO det : lst) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new ReciboDetalleVO();
		        bean.copiar(det);
				
		        boolean saco = this.lstDetalleAgregar.remove(det);
		        this.lstDetalleVO.add(det);
		        this.lstDetalleAgregar.add(det);
		        
				this.container.addBean(bean);
			}
			
		}
		
		//lstFormularios.setContainerDataSource(container);
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
	
			// Set up a filter for all columns
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
	 * Actualizamos Grilla si se agrega o modigfica un formulario y sus permisos
	 * desde GrupoViewExtended
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(ReciboDetalleVO det)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		
		//String nro, String serie, String codDocum
		if(this.existeFormularioenLista(det.getNroDocum(), det.getSerieDocum(), det.getCodDocum()))
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
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un grupoVO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarFormularioLista(ReciboDetalleVO det)
	{
		int i =0;
		boolean salir = false;
		
		ReciboDetalleVO detEnLista;
		
		while( i < this.lstDetalleVO.size() && !salir)
		{
			detEnLista = this.lstDetalleVO.get(i);
			if(det.getNroDocum()== detEnLista.getNroDocum())
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstDetalleVO.get(i).copiar(det);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el grupoVO en la lista
	 * de grupos de la vista
	 *
	 */
	private boolean existeFormularioenLista(String nro, String serie, String codDocum)
	{
		int i =0;
		boolean esta = false;
		
		ReciboDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro.trim().equals(aux.getNroDocum().trim()) 
				&& aux.getSerieDocum().toUpperCase().trim().equals(serie.toUpperCase().trim())
				&& aux.getCodDocum().toUpperCase().trim().equals(codDocum.toUpperCase().trim()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	/**
	 * Quitamos objeto de la lista de saldo de factura auxiliar
	 *
	 */
	private void quitarSaldoFacturaAux(String nro, String serie, String codDocum) 
	{
		int i =0;
		boolean esta = false;
		
		FacturaSaldoAux aux;
		
		while( i < this.saldoOriginalFact.size() && !esta)
		{
			aux = this.saldoOriginalFact.get(i);
			if(nro.trim().equals(aux.getNroDocum().trim()) 
				&& aux.getSerie().toUpperCase().trim().equals(serie.toUpperCase().trim())
				&& aux.getCodDocum().toUpperCase().trim().equals(codDocum.toUpperCase().trim()))
			{
				esta = true;
				
				this.saldoOriginalFact.remove(i);
			}
			
			i++;
		}
		
	}
	
	private void existeFormularioenListaElimina(int linea)
	{
		int i =0;
		boolean esta = false;
		
		ReciboDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(linea==aux.getLinea())
			{
				esta = true;
				this.lstDetalleVO.remove(i);
			}
			
			i++;
		}
		
	}
	
//	private boolean existeProcesoenLista(int nro)
//	{
//		int i =0;
//		boolean esta = false;
//		
//		ReciboDetalleVO aux;
//		
//		while( i < this.lstDetalleVO.size() && !esta)
//		{
//			aux = this.lstDetalleVO.get(i);
//			if(nro==aux.getNroDocum())
//			{
//				esta = true;
//			}
//			
//			i++;
//		}
//		
//		return esta;
//	}
	
	private void actualizarGrillaContainer(BeanItemContainer<ReciboDetalleVO> container)
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
		  
		lstGastos.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		lstGastos.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		lstGastos.getColumn("impTotMo").setHeaderCaption("Importe");
		
		//lst.get(1).setWidth(400);
		
		//List<Column> lstColumn


		/*Formateamos los tamaños*/
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
		lstGastos.getColumn("simboloMoneda").setEditable(false);
		lstGastos.getColumn("impTotMo").setEditable(true);
		
		lstGastos.setEditorSaveCaption("Guardar");
		lstGastos.setEditorCancelCaption("Cancelar");
		
		
		
	lstGastos.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
		ReciboDetalleVO aux;
		FacturaSaldoAux factSaldo;
		
		@Override
		public void preCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
      	
	      	if(formSelecccionado != null && (formSelecccionado.getSerieDocum() != "Proc")){
	      		
	      		
	      		ReciboDetalleVO aux = obtenerFactEnLista(formSelecccionado.getNroDocum(), formSelecccionado.getSerieDocum(), formSelecccionado.getCodDocum());
		    	//factSaldo = saldoOriginalFact.get(formSelecccionado.getNroDocum());
		    	
	  		}
	      	else if(formSelecccionado != null && (formSelecccionado.getSerieDocum() == "Proc")){
	      		
	      		ReciboDetalleVO aux = obtenerFactEnLista(formSelecccionado.getNroDocum(), formSelecccionado.getSerieDocum(), formSelecccionado.getCodDocum());
		    	//factSaldo = saldoOriginalFact.get(formSelecccionado.getNroDocum());
	      	}
      }

      @Override
      public void postCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
      	
    	  double aux, importeMoneda = 0, importeImpuesto = 0;
  		  double aux3;
    	  ReciboDetalleVO aux2 = obtenerFactEnLista(formSelecccionado.getNroDocum(), formSelecccionado.getSerieDocum(), formSelecccionado.getCodDocum());
    	  
		  /*Si el importe modificado es mayor al saldo no dejamos modificar*/
		  if(aux2.getImpTotMo()> factSaldo.getSaldo())
		  {

			  try {
				  throw new FieldGroup.CommitException("El importe no puede ser mayor al saldo ");
				  
			} 
			  catch (Exception e) {
				formSelecccionado.setImpTotMo(factSaldo.getSaldo());
				Mensajes.mostrarMensajeError("El importe no puede ser mayor al saldo");
				return;
//				666	acaaa setear los otros campos por si cambia el importe
//					
//					Mensajes.mostrarMensajeError(e.getMessage());// TODO: handle exception
			}
		  }
    	  
    	  if(formSelecccionado.getTcMov() != 0){
			  formSelecccionado.setImpTotMn(formSelecccionado.getImpTotMo() * formSelecccionado.getTcMov());
		  }
		  else{
			  formSelecccionado.setImpTotMn(formSelecccionado.getImpTotMo());
		  }
		  
    	  importeMoneda = formSelecccionado.getImpTotMo();
    	  
    	  //Importe Impuesto Moneda
    	  aux = (formSelecccionado.getPorcentajeImpuesto() /100)+1;
		  aux3 = importeMoneda/aux;
		  importeImpuesto = importeMoneda - aux3;
			
		  Double truncatedDouble = new BigDecimal(importeImpuesto)
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
		  
		  importeImpuesto = truncatedDouble;
		  formSelecccionado.setImpImpuMo(truncatedDouble);
		  
		  //Importe Impuesto Moneda Nacional
		  truncatedDouble = new BigDecimal( formSelecccionado.getImpImpuMo() * formSelecccionado.getTcMov())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
			
		  formSelecccionado.setImpImpuMn(truncatedDouble);
		  
		  
		  formSelecccionado.setImpSubMn(formSelecccionado.getImpTotMn() - formSelecccionado.getImpImpuMn());  
		  formSelecccionado.setImpSubMo(formSelecccionado.getImpTotMo() - formSelecccionado.getImpImpuMo());
		  
    	  calcularImporteTotal();
      }
	 });
		
	}
	
	/**
	 * Retornanoms true si esta la factura en la lista
	 * de grupos de la vista
	 *
	 */
	private ReciboDetalleVO obtenerFactEnLista(String nro, String serie, String codDocum)
	{
		int i =0;
		boolean esta = false;
		
		ReciboDetalleVO aux = null;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro.trim().equals(aux.getNroDocum()) 
					&& aux.getSerieDocum().trim().toUpperCase().equals(serie.trim().toUpperCase())
					&& aux.getCodDocum().trim().toUpperCase().equals(codDocum.trim().toUpperCase()))
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
						VariablesPermisos.FORMULARIO_RECIBO,
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
	
	public void inicializarComboCuentas(String cod, String llamador ){
		
		if(cod != "0" && cod != null){
			BeanItemContainer<CtaBcoVO> ctaObj = new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
			CtaBcoVO cta = new CtaBcoVO();
			ArrayList<CtaBcoVO> lstctas = new ArrayList<CtaBcoVO>();
			UsuarioPermisosVO permisosAux;
			
			try {
				permisosAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_RECIBO,
								VariablesPermisos.OPERACION_LEER);
				
				/*Si se selacciona un banco buscamos las cuentas, de lo contrario no*/
				if(this.comboBancos.getValue() != null)
					lstctas = this.controlador.getCtaBcos(permisosAux,((BancoVO) this.comboBancos.getValue()).getCodigo());
				
			} catch (ObteniendoCuentasBcoException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
			
			for (CtaBcoVO ctav : lstctas) {
					
				ctaObj.addBean(ctav);
				
				if(llamador.equals("CuentaBanco")){
					if(cod != null){
						if(cod.equals(ctav.getCodigo())){
							cta = ctav;
							this.monedaBanco.setValue(cta.getMonedaVO().getSimbolo());
							this.cuentaBanco.setValue(cta.getCodigo());
						}
					}
				}
			}
			
			
			if(lstctas.size()>0){
				
				//this.comboCuentas.setData("ProgramaticallyChanged");
				
				this.comboCuentas.setContainerDataSource(ctaObj);
				
				this.comboCuentas.setItemCaptionPropertyId("nombre");
			}
			
			
			
			
			if(cod!=null)
			{
				try{
					this.comboCuentas.setReadOnly(false);
					this.comboCuentas.setValue(cta);
					//this.comboCuentas.setReadOnly(true);
				}catch(Exception e)
				{}
			}
			
			if(this.operacion.equals(Variables.OPERACION_EDITAR) || this.operacion.equals(Variables.OPERACION_NUEVO))
			{
				this.comboCuentas.setReadOnly(false);
			}
		}
		else{
			this.comboCuentas.setEnabled(false);
			this.cuentaBanco.setValue("");
			this.monedaBanco.setValue("");
		}
		
		
	}
	
	public void inicializarComboBancos(String cod){
		
		BeanItemContainer<BancoVO> bcoObj = new BeanItemContainer<BancoVO>(BancoVO.class);
		BancoVO bcoVO = new BancoVO();
		ArrayList<BancoVO> lstBcos = new ArrayList<BancoVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_RECIBO,
							VariablesPermisos.OPERACION_LEER);
			
			lstBcos = this.controlador.getBcos(permisosAux);
			
		} catch ( InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoBancosException | ObteniendoCuentasBcoException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (BancoVO bco : lstBcos) {
			
			if(bco.getCodigo().equals("0")){
				lstBcos.remove(bco);
			}
			else{
				bcoObj.addBean(bco);
				
				if(cod != null){
					if(cod.equals(bco.getCodigo())){
						bcoVO = bco;
						bcoVO.setCodEmp("0");
					}
				}
			}
			
		}
		
		this.comboBancos.setContainerDataSource(bcoObj);
		this.comboBancos.setItemCaptionPropertyId("nombre");
		
		if(cod!=null)
		{
			this.comboBancos.setReadOnly(false);
			this.comboBancos.setValue(bcoVO);
			this.comboBancos.setReadOnly(true);
		}
	}
	
	
	@Override
	public void setInfo(Object datos) {
		
		
		
		if(datos instanceof ClienteVO){
			
			ClienteVO clienteVO = (ClienteVO) datos;
			this.codTitular.setValue(String.valueOf(clienteVO.getCodigo()));
			this.nomTitular.setValue(clienteVO.getNombre());
		}
		
		if(datos instanceof TitularVO){
			
			TitularVO titularVO = (TitularVO) datos;
			this.codTitular.setValue(String.valueOf(titularVO.getCodigo()));
			this.nomTitular.setValue(titularVO.getNombre());
		}
		
//		if(datos instanceof ProcesoVO){
//			ProcesoVO procesoVO = (ProcesoVO) datos;
//			ReciboDetalleVO docum = new IngresoCobroDetalleVO();
//			docum = (IngresoCobroDetalleVO) procesoVO.crearDocumDetalle(procesoVO);
//			MonedaVO auxMoneda = new MonedaVO();
//			UsuarioPermisosVO permisosAux;
//			
//   			
//   			auxMoneda = (MonedaVO) comboMoneda.getValue();
//   			permisosAux = 
//					new UsuarioPermisosVO(this.permisos.getCodEmp(),
//							this.permisos.getUsuario(),
//							VariablesPermisos.FORMULARIO_RECIBO,
//							VariablesPermisos.OPERACION_NUEVO_EDITAR);
//   			
//   			/*Seteo la moneda del cobro a cuenta de proceso con la moneda ingresada de cobro*/
//   			docum.setCodMoneda(auxMoneda.getCodMoneda());
//   			docum.setNomMoneda(auxMoneda.getDescripcion());
//   			docum.setSimboloMoneda(auxMoneda.getSimbolo());
//   			docum.setCodDocum("Proc");
//   			docum.setSerieDocum("Proc");
//   			docum.setNroDocum("0"); /*Ponemos 0 para que no aparezca el reandom en la grilla*/
//   			docum.setCodCtaInd("Proc");
//   			docum.setCodProceso(String.valueOf(procesoVO.getCodigo()));
//   			docum.setUsuarioMod(permisosAux.getUsuario());
//   			docum.setOperacion("NUEVO");
//   			docum.setCodEmp(permisosAux.getCodEmp());
//   			docum.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
//   			docum.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
//   			
//   			
//   			/*VER*/ docum.setCodRubro(procesoVO.getCodRubro()); 
//   			docum.setCodCuenta(procesoVO.getCodCuenta());
//   			docum.setCodImpuesto(procesoVO.getCodImpuesto());
//   			
//   			docum.setTcMov(procesoVO.getTcMov());
//   			docum.setImpTotMo(procesoVO.getImpMo());
//   			docum.setCodRubro(procesoVO.getCodRubro());
//   			docum.setNomRubro(procesoVO.getNomRubro());
//   			docum.setCodCuenta(procesoVO.getCodCuenta());
//   			docum.setNomCuenta(procesoVO.getNomCuenta());
//   			docum.setImpTotMn(procesoVO.getImpMn());
//   			docum.setImpSubMn(procesoVO.getImpMn()); /*Al no tener impuesto es el mismo que MN*/
//   			docum.setImpSubMo(procesoVO.getImpMo()); /*Al no tener impuesto es el mismo que MO*/
//   			docum.setLinea(procesoVO.getLinea());
//   			docum.setReferencia(procesoVO.getObservaciones());
//   			
//   			existeFormularioenListaElimina(docum.getLinea());
//   			
//			this.lstDetalleVO.add(docum);
//				
////			/*Tambien agrefamos a la lista de los saldos originales
////			 * para poder controlar que no ingresen un saldo mayor al que tien el gasto*/
////			GtoSaldoAux saldoAux =  new GtoSaldoAux(g.getNroDocum(), g.getImpTotMo());
////			this.saldoOriginalGastos.put(saldoAux.getNroDocum(),saldoAux);
//				
//			/*Actualizamos el container y la grilla*/
//			container.removeAllItems();
//			container.addAll(lstDetalleVO);
//			//lstFormularios.setContainerDataSource(container);
//			this.actualizarGrillaContainer(container);
//			
//			calcularImporteTotal();
//			
//		}
		
	}
	
	/**
	 * Controlamos que el total de los gastos sea igual al total
	 * ingresado
	 *
	 */
	private void calcularImporteTotal(){
		
		/*Inicializamos el permisos auxilar, para obterer el TC de moneda no nacional en detalle y distinta a la moneda del cabezal*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RECIBO,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);	
		
		double impMoCab = 0;
		double impMo = 0;
		double tcMonedaNacional = 0;
		
		double aux;
		double aux2;
		double tcAux;
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
		
		for (ReciboDetalleVO det : lstDetalleVO) {

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
					cotAux = this.controlador.getCotizacion(permisoAux, fecha, det.getCodMoneda());
					
				} catch (ObteniendoCotizacionesException | ConexionException | ObteniendoPermisosException
						| InicializandoException | NoTienePermisosException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				tcAux = cotAux.getCotizacionVenta();
				
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
	 * Nos retorna el importe total a ingresar en la cuenta del banco
	 * Ya que la moneda de la cuenta bancaria puede ser distinta a la 
	 * del cobro
	 *
	 */

	
	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstDetalle(ArrayList<ReciboDetalleVO> lst)
	{
		
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ReciboDetalleVO>(ReciboDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			int linea = 0;
			for (ReciboDetalleVO det : this.lstDetalleVO) {
				det.setLinea(linea); /*Seteamos la linea*/
				container.addBean(det); /*Lo agregamos a la grilla*/
				FacturaSaldoAux saldoAux =  new FacturaSaldoAux(det.getCodDocum(), det.getSerieDocum(), det.getNroDocum(), det.getImpTotMo());
				this.saldoOriginalFact.add(saldoAux);
				
				linea ++;
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
	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		
		int j = 0;
		boolean salir = false;
		MonedaVO monedaVO;
		
		int linea = this.getLineaMaxima() + 1; /*Obtenemos linea maxima y sumamos uno*/
		
		
		
		ReciboDetalleVO g;
		for (Object obj : lstDatos) {
			j = 0;
			salir = false;
			
			g = new ReciboDetalleVO();
			g.copiar((DocumDetalleVO)obj);
			g.setLinea(linea);
			
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
							
							/*Tambien agrefamos a la lista de los saldos originales
							 * para poder controlar que no ingresen un saldo mayor al que tiene la factura*/
							FacturaSaldoAux saldoAux =  new FacturaSaldoAux(g.getCodDocum(), g.getSerieDocum(), g.getNroDocum(), g.getImpTotMo());
							this.saldoOriginalFact.add(saldoAux);
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
				
				/*Tambien agrefamos a la lista de los saldos originales
				 * para poder controlar que no ingresen un saldo mayor al que tiene la factura*/
				FacturaSaldoAux saldoAux =  new FacturaSaldoAux(g.getCodDocum(), g.getSerieDocum(), g.getNroDocum(), g.getImpTotMo());
				this.saldoOriginalFact.add(saldoAux);
			}
			
			linea ++;
			
			if(lstDetalleVO.size() > 0){
				this.tcMov.setEnabled(false);
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
	
	public void inicializarCampos(){
		
		nroTrans.setConverter(Long.class);
		
		nroDocum.setConverter(Integer.class);
		nroDocum.setConversionError("Ingrese un número entero");
		
		nroDocRef.setConverter(Integer.class);
		nroDocRef.setConversionError("Ingrese un número entero");
		
		tcMov.setConverter(BigDecimal.class);
		tcMov.setConversionError("Error en formato de número");
		
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de número");
		
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
	
	public MonedaVO seteaCotizaciones(MonedaVO monedaVO){
		
		UsuarioPermisosVO permisosAux;
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RECIBO,
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
	
	public void eliminarFact(){
		try {
		
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RECIBO,
						VariablesPermisos.OPERACION_BORRAR);
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Validamos los campos antes de invocar al controlador*/
		if(this.fieldsValidos())
		{
			/*Inicializamos VO d	e permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			
			ReciboVO recVO = new ReciboVO();	
			
			recVO.setImpTotMo((Double) impTotMo.getConvertedValue());
			
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
					
					recVO.setCodMoneda(auxMoneda.getCodMoneda());
					recVO.setNomMoneda(auxMoneda.getDescripcion());
					recVO.setSimboloMoneda(auxMoneda.getSimbolo());
					//}
				}


				/////////////////////////////FIN MONEDA////////////////////////////////////////////
				if(auxMoneda.isNacional()) /*Si la moneda seleccionada es nacional*/
				{
					/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
					recVO.setTcMov(1);
					recVO.setImpTotMn(recVO.getImpTotMo());
					
				}else
				{
					coti = this.controlador.getCotizacion(permisoAux, fecha, this.getCodMonedaSeleccionada());
					recVO.setTcMov(coti.getCotizacionVenta());
					recVO.setImpTotMn((recVO.getImpTotMo()*recVO.getTcMov()));
				}
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
			recVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
			recVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
			/*Codigo y serie docum se inicializan en constructor*/
			//ingCobroVO.setNroDocum(Integer.parseInt(nroDocum.getValue()));  VER ESTO CON EL NUMERADOR
			recVO.setCodEmp(permisos.getCodEmp());
			recVO.setReferencia(referencia.getValue());
			
			recVO.setCodCtaInd("ingcobro");
			
			
			recVO.setCodTitular(codTitular.getValue());
			recVO.setNomTitular(nomTitular.getValue());
			
			recVO.setOperacion(operacion);
			
			/*Ver los totales y tc*/
			//ingCobroVO.setImpTotMn(impTotMn);
			//ingCobroVO.setImpTotMo(impTotMn);
			//ingCobroVO.setTcMov(tcMov);
			
			/*Si es nuevo aun no tenemos el nro del cobro*/
			if(this.nroDocum.getValue() != null)
				recVO.setNroDocum(this.nroDocum.getValue().toString().trim());
			
			
			/*Si es banco tomamos estos cmapos de lo contrario caja*/
			if(this.comboTipo.getValue().toString().equals("Banco")){
			
				recVO.setmPago((String)comboMPagos.getValue());
				
				if(recVO.getmPago().equals("transferencia"))
				{
					recVO.setCodDocRef("tranrec");
					
					recVO.setSerieDocRef("0");
				}
				else if(recVO.getmPago().equals("Cheque"))
				{
					recVO.setCodDocRef("cheqrec");
					recVO.setNroDocRef(nroDocRef.getValue().trim());
					recVO.setSerieDocRef(serieDocRef.getValue().trim());
					
				}else
				{
					
					recVO.setCodDocRef("0");
					recVO.setNroDocRef("0");
					recVO.setSerieDocRef("0");
				}
											
				//Datos del banco y cuenta
				CtaBcoVO auxctaBco = new CtaBcoVO();
				if(this.comboCuentas.getValue() != null){
					
					auxctaBco = (CtaBcoVO) this.comboCuentas.getValue();
					
				}
				
				recVO.setCodBanco(auxctaBco.getCodBco());
				recVO.setCodCtaBco(auxctaBco.getCodigo());
				recVO.setNomCtaBco(auxctaBco.getNombre());
				recVO.setCodMonedaCtaBco(auxctaBco.getMonedaVO().getCodMoneda());
				/*Falta poner el nombre de la cuenta*/
				
			}
			else {
				
				if(((String)comboTipo.getValue()).equals("Caja"))
				{
					recVO.setCodBanco("0");
					recVO.setNomBanco("0");
					
					recVO.setCodCtaBco("0");
					recVO.setNomCtaBco("0");
					
					recVO.setCodDocRef("0");
					recVO.setNroDocRef("0");
					recVO.setSerieDocRef("0");
					
					recVO.setmPago("Caja");
				}
							
			}
			
			recVO.setUsuarioMod(this.permisos.getUsuario());
			
			if(this.operacion != Variables.OPERACION_NUEVO){
				recVO.setNroTrans((long)this.nroTrans.getConvertedValue());
			}
			else{
				recVO.setNroTrans(0);
			}
			
			
				
			recVO.setCodCuenta("recibo");
			recVO.setDetalle(this.lstDetalleVO);
			
			if(recVO.getDetalle().size() <= 0){
				Mensajes.mostrarMensajeError("El cobro no tiene detalle");
				return;
			}
			
			 /*Obtenemos la moneda de la cuenta*/
		    //Datos del banco y cuenta y moneda de la cuenta
		    CtaBcoVO auxctaBco = new CtaBcoVO();
		    if(this.comboCuentas.getValue() != null){
		     
		    	auxctaBco = (CtaBcoVO) this.comboCuentas.getValue();
		     
		    }
		    
		    /*Seteamos la moneda de la cta del banco*/
		    recVO.setCodMonedaCtaBco(auxctaBco.getMonedaVO().getCodMoneda());
		    recVO.setNacionalMonedaCtaBco(auxctaBco.getMonedaVO().isNacional());
			
		    this.controlador.eliminarRecibo(recVO, permisoAux);
			
			this.mainView.actulaizarGrilla(recVO);
			
			Mensajes.mostrarMensajeOK("Se ha eliminado el Cobro");
			UI.getCurrent().removeWindow(sub);
			this.mainView.cerrarVentana();
			
			
		}
		else /*Si los campos no son válidos mostramos warning*/
		{
			Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
		}
			
		} catch (NoExisteReciboException |EliminandoReciboException| ExisteReciboException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
			
		}
	}
	
	public void agregarGasto(){
		
//		try {
//		
//		UI.getCurrent().removeWindow(sub);
//		BusquedaViewExtended form = new BusquedaViewExtended(this, new GastoVO());
//		
//		sub = new MySub("93%", "64%" );
//		sub.setModal(true);
//		sub.setVista(form);
//		sub.center();
//		
//		String codCliente;/*Codigo del cliente para obtener los gastos a cobrar del mismo*/
//		
//		/*Obtenemos los formularios que no estan en el grupo
//		 * para mostrarlos en la grilla para seleccionar*/
//		if(this.operacion.equals(Variables.OPERACION_NUEVO) )
//		{
//			/*Si la operacion es nuevo, ponemos el  codGrupo vacio
//			 * asi nos trae todos los grupos disponibles*/
//			codCliente = this.codTitular.getValue().toString().trim();
//		}
//		else 
//		{
//			/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
//			codCliente = fieldGroup.getItemDataSource().getBean().getCodTitular();
//		}
//		
//		/*Inicializamos VO de permisos para el usuario, formulario y operacion
//		 * para confirmar los permisos del usuario*/
//		UsuarioPermisosVO permisoAux = 
//				new UsuarioPermisosVO(this.permisos.getCodEmp(),
//						this.permisos.getUsuario(),
//						VariablesPermisos.FORMULARIO_RECIBO,
//						VariablesPermisos.OPERACION_NUEVO_EDITAR);
//		
//
//		//Moneda
//		MonedaVO auxMoneda = new MonedaVO();
//		if(this.comboMoneda.getValue() != null){
//			auxMoneda = (MonedaVO) this.comboMoneda.getValue();
//		}
//		
//		/*Obtenemos los gastos con saldo del cliente*/
//		ArrayList<GastoVO> lstGastosConSaldo = this.controlador.getGastosConSaldo(permisoAux, codCliente);
//		
//		/*Hacemos una lista auxliar para pasarselo al BusquedaViewExtended*/
//		ArrayList<Object> lst = new ArrayList<Object>();
//		Object obj;
//		for (GastoVO i: lstGastosConSaldo) {
//			
//			/*Verificamos que el gasto ya no esta en la grilla*/
//			if(!this.existeFormularioenLista(i.getNroDocum()))
//			{
//				obj = new Object();
//				obj = (Object)i;
//				lst.add(obj);
//			}
//		}
//		
//		form.inicializarGrilla(lst);
//		
//		UI.getCurrent().addWindow(sub);
//
//		}catch(Exception e)
//		{
//			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
//		}
//		
		
	}
	
	public void agregarProceso(){
		
//		try {
//			
//			UI.getCurrent().removeWindow(sub);
//			ProcesoVO proceso = new ProcesoVO();
//			proceso.setCodCliente(codTitular.getValue());
//			proceso.setNomCliente(nomTitular.getValue());
//			
//			MonedaVO auxMoneda = new MonedaVO();
//   			auxMoneda = (MonedaVO) comboMoneda.getValue();
//   			
//   			
//			proceso.setCodMoneda(auxMoneda.getCodMoneda());
//			proceso.setDescMoneda(auxMoneda.getDescripcion());
//			proceso.setSimboloMoneda(auxMoneda.getSimbolo());
//			proceso.setLinea(this.getLineaMaxima() + 1);
//			
//			//BusquedaViewExtended form = new BusquedaViewExtended(this, new ProcesoVO());
//			IngresoCobroProcesoViewExtended form = new IngresoCobroProcesoViewExtended("NUEVO", this, proceso);
//			sub = new MySub("77%", "45%" );
//			sub.setModal(true);
//			sub.setVista(form);
//			sub.center();
//			
//			UI.getCurrent().addWindow(sub);
//
//			}
//		catch(Exception e){
//			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
//		}
	}
	
	/***
	 * Nos retorna la cotizacion del ingreso de cobro
	 * Lo utilizamos para agregar a cuenta de proceso
	 * porder calcular MN 
	 */
	public double getCotizacion(){
		
		return this.cotizacionVenta;
	}
	
	/***
	 * Nos retorna la linea maxima del detalle
	 * para poder asignar correctamente la proxima linea
	 * a la hora de quitar
	 */
	private int getLineaMaxima(){
		
		int max = 0;
		
		if(this.lstDetalleVO != null)
		{
			for (ReciboDetalleVO d : this.lstDetalleVO) {
				
				if(d.getLinea() > max)
					max = d.getLinea();
				
			}
		}
		
		return max;
	}
}
