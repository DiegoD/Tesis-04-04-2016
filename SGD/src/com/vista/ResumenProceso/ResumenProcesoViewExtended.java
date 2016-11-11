package com.vista.ResumenProceso;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.ProcesoControlador;
import com.controladores.ResumenProcesoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Gastos.GastoViewExtended;
import com.vista.Gastos.GastosPanelExtended;
import com.vista.Gastos.IGastosMain;

public class ResumenProcesoViewExtended extends ResumenProcesoView implements IBusqueda, IGastosMain{
	
	private BeanFieldGroup<ProcesoVO> fieldGroup;
	private ResumenProcesoControlador controlador;
	private String operacion;
	private ResProcesosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	UsuarioPermisosVO permisoAux;
	int codigoInsert;
	CotizacionVO cotizacion = new CotizacionVO();
	Double cotizacionVenta = null, importeMoneda = null;
	MonedaVO monedaNacional = new MonedaVO();
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
	
	/*Variable para saber si hay que setear los filtros de la grilla*/
	boolean filtroNoCobrablesSeteado; 
	boolean filtroCobrablesSeteado; 
	boolean filtroAPagarSeteado;
	boolean filtroAnuladosSeteado; 
	private GastoViewExtended form;  /*Para visualizar los gastos de las grillas*/
	
	
	 /*Variable utilizada para setear que se cargaron los gastos, 
	y no volver a traerlos de base a menos que se presione actualizar*/
	boolean gtosNoCobrablesCargados;
	boolean gtosCobrablesSeteado; 
	boolean gtosAPagarSeteado;
	boolean gtosAnuladosSeteado; 
	
	
	BeanItemContainer<GastoVO> containerGastosNoCobrables;
	BeanItemContainer<GastoVO> containerGastosCobrables;
	BeanItemContainer<GastoVO> containerGastosAPagar;
	BeanItemContainer<GastoVO> containerGastosAnulados;
	
	/*Listas que contiene los gastos no cobrables para el proceso*/
	ArrayList<GastoVO> lstGastosNoCobrables; 
	ArrayList<GastoVO> lstGastosCobrables; 
	ArrayList<GastoVO> lstGastosGastosAPagar; 
	ArrayList<GastoVO> lstGastosGastosAnulados; 
	
	private String grillaDesde; /*Variable utilizada para indicar desde que grilla se 
	 							modifica un gasto*/
	
	public ResumenProcesoViewExtended(String opera, ResProcesosPanelExtended main){
		
		this.filtroNoCobrablesSeteado = false;
		this.filtroCobrablesSeteado = false; 
		this.filtroAPagarSeteado = false;
		this.filtroAnuladosSeteado = false; 
		
		
		this.gtosNoCobrablesCargados = false;
		this.gtosCobrablesSeteado = false; 
		this.gtosAPagarSeteado = false;
		this.gtosAnuladosSeteado = false; 
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		this.operacion = opera;
		this.mainView = main;
		
		
		/**
		 * Listener para grilla no cobrables, para inicializar al gasto
		 * 
		 */
		this.gridNoCobrables.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		grillaDesde = "gridNoCobrables"; 
		    		
		    		if(gridNoCobrables.getSelectedRow() != null){
		    			
		    			BeanItem<GastoVO> item = containerGastosNoCobrables.getItem(gridNoCobrables.getSelectedRow());
		    			
		    			
		    			
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						
				    	form = new GastoViewExtended(Variables.OPERACION_LECTURA, ResumenProcesoViewExtended.this, null);
				    	sub = new MySub("88%","50%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
			    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    }
		      
		    }
		});
		
		
		/**
		 * Listener para grilla cobrables, para inicializar al gasto
		 * 
		 */
		this.gridCobrables.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		grillaDesde = "gridCobrables"; 
		    		
		    		if(gridCobrables.getSelectedRow() != null){
		    			
		    			BeanItem<GastoVO> item = containerGastosCobrables.getItem(gridCobrables.getSelectedRow());
		    			
		    			
		    			
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						
				    	form = new GastoViewExtended(Variables.OPERACION_LECTURA, ResumenProcesoViewExtended.this, null);
				    	sub = new MySub("88%","50%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
			    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    }
		      
		    }
		});
		
		
		/**
		 * Listener para grilla a pagar, para inicializar al gasto
		 * 
		 */
		this.gridAPagar.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		grillaDesde = "gridAPagar"; 
		    		
		    		if(gridAPagar.getSelectedRow() != null){
		    			
		    			BeanItem<GastoVO> item = containerGastosAPagar.getItem(gridAPagar.getSelectedRow());
		    			
		    			
		    			
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						
				    	form = new GastoViewExtended(Variables.OPERACION_LECTURA, ResumenProcesoViewExtended.this, null);
				    	sub = new MySub("88%","50%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
			    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    }
		      
		    }
		});
		
		
		/**
		 * Listener para grilla a pagar, para inicializar al gasto
		 * 
		 */
		this.gridAnular.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		grillaDesde = "gridAnular"; 
		    		
		    		if(gridAnular.getSelectedRow() != null){
		    			
		    			BeanItem<GastoVO> item = containerGastosAnulados.getItem(gridAnular.getSelectedRow());
		    			
		    			
		    			
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						
				    	form = new GastoViewExtended(Variables.OPERACION_LECTURA, ResumenProcesoViewExtended.this, null);
				    	sub = new MySub("88%","50%");
						sub.setModal(true);
						sub.setVista((Component) form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
			    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    }
		      
		    }
		});
		
		
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
						procesoVO.setTcMov((double) tcMov.getConvertedValue());
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
						procesoVO.setNroDocum((Integer) nroDocum.getConvertedValue());
					}
					else{
						procesoVO.setNroDocum(0);
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
				cotizacionVenta = (Double) tcMov.getConvertedValue();
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
		
		this.btnInfo.addClickListener(click -> {
			
			this.gastos_no_cobrables.setVisible(false);
			this.info_form.setVisible(true);
			
		});
		
		this.btnGtosNoCobrables.addClickListener(click -> {
			
			this.info_form.setVisible(false);
			this.gastos_apagar.setVisible(false);
			this.gastos_anulados.setVisible(false);
			this.gastos_cobrables.setVisible(false);
			
			this.actualizarGastosNoCobrables();
			
			this.gastos_no_cobrables.setVisible(true);
			
			/*Si el filtro no fue seteado, lo seteamos*/
			if(this.filtroNoCobrablesSeteado == false){
				this.filtroGrillaGridNoCobrables();
				this.filtroNoCobrablesSeteado = true;
			}
		});
		
		this.btnGtosCobrables.addClickListener(click -> {
			
			this.info_form.setVisible(false);
			this.gastos_apagar.setVisible(false);
			this.gastos_anulados.setVisible(false);
			this.gastos_no_cobrables.setVisible(false);
			
			this.actualizarGastosCobrables();
			
			this.gastos_cobrables.setVisible(true);
			
			/*Si el filtro no fue seteado, lo seteamos*/
			if(this.filtroCobrablesSeteado == false){
				this.filtroGrillaGridCobrables();
				this.filtroCobrablesSeteado = true; 
			}
		});
		
		
		this.btnGtosAPagar.addClickListener(click -> {
			
			this.info_form.setVisible(false);
			this.gastos_cobrables.setVisible(false);
			this.gastos_anulados.setVisible(false);
			this.gastos_no_cobrables.setVisible(false);
			
			this.actualizarGastosAPagar();
			
			this.gastos_apagar.setVisible(true);
			
			/*Si el filtro no fue seteado, lo seteamos*/
			if(this.filtroAPagarSeteado == false){
				this.filtroGrillaGridAPagar(); 
				this.filtroAPagarSeteado = true; 
			}
		});
		
		this.btnGtosAnulados.addClickListener(click -> {
			
			this.info_form.setVisible(false);
			this.gastos_cobrables.setVisible(false);
			this.gastos_apagar.setVisible(false);
			this.gastos_no_cobrables.setVisible(false);
			
			this.actualizarGastosAnulados();
			
			this.gastos_anulados.setVisible(true);
			
			/*Si el filtro no fue seteado, lo seteamos*/
			if(this.filtroAnuladosSeteado == false){
				this.filtroGrillaGridAnular(); 
				this.filtroAnuladosSeteado = true; 
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
							VariablesPermisos.FORMULARIO_RESUMEN_PROCESO,
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
		        		cotizacionVenta = (Double) tcMov.getConvertedValue();
					} catch (Exception e) {
						return;
						// TODO: handle exception
						//Mensajes.mostrarMensajeError("Formato de número incorrecto");
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
	}
	
	public  void inicializarForm(){
		
		this.controlador = new ResumenProcesoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ProcesoVO>(ProcesoVO.class);
		
		
		/*inicializar los valores de los combos impuesto y tipo de rubro*/
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
		
		
		/*Inicializamos los conteiners de grillas*/
		this.containerGastosNoCobrables = 
				new BeanItemContainer<GastoVO>(GastoVO.class);
		
		this.containerGastosCobrables = 
				new BeanItemContainer<GastoVO>(GastoVO.class);
		
		this.containerGastosAPagar = 
				new BeanItemContainer<GastoVO>(GastoVO.class);
		
		this.containerGastosAnulados = 
				new BeanItemContainer<GastoVO>(GastoVO.class);
		
		
		/*Dejamos no visibles las grillas de los gastos
		 * dejando visible solamenta la solapa de informacion del proceso*/
		
		this.gastos_no_cobrables.setVisible(false);
		this.gastos_cobrables.setVisible(false);
		this.gastos_apagar.setVisible(false);
		this.gastos_anulados.setVisible(false);
	
		
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
		
		tcMov.setConverter(Double.class);
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
	
	
	
	/**
	 *Obtenemos los gastos no cobrables para el proceso
	 * e inicializamos la grilla de los mismos
	 *
	 */
	private void actualizarGastosNoCobrables()
	{
		this.lstGastosNoCobrables = new ArrayList<GastoVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RESUMEN_PROCESO,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			/*Si ya no fueron cargados, se cargan, de lo contrario ya estan en la grilla*/
			if(this.gtosNoCobrablesCargados == false) {
				
				String cod = this.codigo.getValue(); 
			
				this.lstGastosNoCobrables = this.controlador.getGastosNoCobrablesxProceso(permisoAux, Integer.valueOf(cod));
				
				/*Actualizamos el container y la grilla*/
				this.containerGastosNoCobrables.removeAllItems();
				this.containerGastosNoCobrables.addAll(this.lstGastosNoCobrables);
				//lstFormularios.setContainerDataSource(container);
				this.inicializarGrillaGastosNoCobrables(containerGastosNoCobrables);
				
				this.gtosNoCobrablesCargados = true;
			}
			
		} catch (NumberFormatException | ObteniendoProcesosException | ConexionException | InicializandoException
				| ObteniendoPermisosException | NoTienePermisosException | ObteniendoGastosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
			
		}
		
	}
	
	/**
	 *Obtenemos los gastos cobrables para el proceso
	 * e inicializamos la grilla de los mismos
	 *
	 */
	private void actualizarGastosCobrables()
	{
		this.lstGastosCobrables = new ArrayList<GastoVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RESUMEN_PROCESO,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			/*Si ya no fueron cargados, se cargan, de lo contrario ya estan en la grilla*/
			if(this.gtosCobrablesSeteado == false) {
				
				String cod = this.codigo.getValue(); 
			
				this.lstGastosCobrables = this.controlador.getGastosCobrablesxProceso(permisoAux, Integer.valueOf(cod));
				
				/*Actualizamos el container y la grilla*/
				this.containerGastosCobrables.removeAllItems();
				this.containerGastosCobrables.addAll(this.lstGastosCobrables);
				//lstFormularios.setContainerDataSource(container);
				this.inicializarGrillaGastosCobrables(containerGastosCobrables);
				
				this.gtosCobrablesSeteado = true;
			}
			
		} catch (NumberFormatException | ObteniendoProcesosException | ConexionException | InicializandoException
				| ObteniendoPermisosException | NoTienePermisosException | ObteniendoGastosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
			
		}
		
	}
	
	/**
	 *Obtenemos los gastos a pagar para el proceso
	 * e inicializamos la grilla de los mismos
	 *
	 */
	private void actualizarGastosAPagar()
	{
		this.lstGastosGastosAPagar = new ArrayList<GastoVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RESUMEN_PROCESO,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			/*Si ya no fueron cargados, se cargan, de lo contrario ya estan en la grilla*/
			if(this.gtosAPagarSeteado == false) {
				
				String cod = this.codigo.getValue(); 
			
				this.lstGastosGastosAPagar = this.controlador.getGastosAPagarxProceso(permisoAux, Integer.valueOf(cod));
				
				/*Actualizamos el container y la grilla*/
				this.containerGastosAPagar.removeAllItems();
				this.containerGastosAPagar.addAll(this.lstGastosGastosAPagar);
				//lstFormularios.setContainerDataSource(container);
				this.inicializarGrillaGastosAPagar(containerGastosAPagar); 
				
				this.gtosAPagarSeteado = true;
			}
			
		} catch (NumberFormatException | ObteniendoProcesosException | ConexionException | InicializandoException
				| ObteniendoPermisosException | NoTienePermisosException | ObteniendoGastosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
			
		}
		
	}
	
	/**
	 *Obtenemos los gastos no cobrables para el proceso
	 * e inicializamos la grilla de los mismos
	 *
	 */
	private void actualizarGastosAnulados()
	{
		this.lstGastosGastosAnulados = new ArrayList<GastoVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_RESUMEN_PROCESO,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			/*Si ya no fueron cargados, se cargan, de lo contrario ya estan en la grilla*/
			if(this.gtosAnuladosSeteado == false) {
				
				String cod = this.codigo.getValue(); 
			
				this.lstGastosGastosAnulados = this.controlador.getGastosAnuladosxProceso(permisoAux, Integer.valueOf(cod));
				
				/*Actualizamos el container y la grilla*/
				this.containerGastosAnulados.removeAllItems();
				this.containerGastosAnulados.addAll(this.lstGastosGastosAnulados);
				//lstFormularios.setContainerDataSource(container);
				this.inicializarGrillaGastosAnular(containerGastosAnulados); 
				
				this.gtosAnuladosSeteado = true;
				
				
			}
			
		} catch (NumberFormatException | ObteniendoProcesosException | ConexionException | InicializandoException
				| ObteniendoPermisosException | NoTienePermisosException | ObteniendoGastosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
			
		}
		
	}
	
	
	/**
	 *Inicializamos la Grilla de Gastos no cobrables
	 *para el proceso
	 *
	 */
	private void inicializarGrillaGastosNoCobrables(BeanItemContainer<GastoVO> container){
		
		  gridNoCobrables.setContainerDataSource(container);
		
		//gridNoCobrables.getColumn("impTotMo").setHidden(true);
		
		  gridNoCobrables.getColumn("operacion").setHidden(true);
		  gridNoCobrables.getColumn("fechaMod").setHidden(true);
		  
		  gridNoCobrables.getColumn("codCtaInd").setHidden(true);
		  gridNoCobrables.getColumn("codCuenta").setHidden(true);
		  gridNoCobrables.getColumn("codDocum").setHidden(true);
		  gridNoCobrables.getColumn("codEmp").setHidden(true);
		  gridNoCobrables.getColumn("codImpuesto").setHidden(true);
		  gridNoCobrables.getColumn("codMoneda").setHidden(true);
		  //gridNoCobrables.getColumn("codProceso").setHidable(true);;
		  gridNoCobrables.getColumn("codRubro").setHidden(true);
		  gridNoCobrables.getColumn("codTitular").setHidden(true);
		  //gridNoCobrables.getColumn("cuenta").setHidable(true);;
		  gridNoCobrables.getColumn("descProceso").setHidden(true);
		  gridNoCobrables.getColumn("fecDoc").setHidden(true);
		  gridNoCobrables.getColumn("fecValor").setHidden(true);
		  gridNoCobrables.getColumn("impImpuMn").setHidden(true);
		  gridNoCobrables.getColumn("impImpuMo").setHidden(true);
		  gridNoCobrables.getColumn("impSubMn").setHidden(true);
		  gridNoCobrables.getColumn("impSubMo").setHidden(true);
		  gridNoCobrables.getColumn("linea").setHidden(true);
		  gridNoCobrables.getColumn("impTotMn").setHidden(true);
		  gridNoCobrables.getColumn("nomCuenta").setHidden(true);
		  gridNoCobrables.getColumn("nomImpuesto").setHidden(true);
		  gridNoCobrables.getColumn("nomMoneda").setHidden(true);
		  gridNoCobrables.getColumn("nomRubro").setHidden(true);
		  gridNoCobrables.getColumn("nomTitular").setHidden(true);
		  gridNoCobrables.getColumn("nroTrans").setHidden(true);
		  gridNoCobrables.getColumn("porcentajeImpuesto").setHidden(true);
		  gridNoCobrables.getColumn("serieDocum").setHidden(true);
		  
		  gridNoCobrables.getColumn("codProceso").setHidden(true);
		  gridNoCobrables.getColumn("estadoGasto").setHidden(true);
		  gridNoCobrables.getColumn("tipo").setHidden(true);
		  
		  gridNoCobrables.getColumn("tcMov").setHidden(true);
		  gridNoCobrables.getColumn("usuarioMod").setHidden(true);
		  gridNoCobrables.getColumn("nacional").setHidden(true);
		  
		  
		  /*Seteamos tamanios*/
		  gridNoCobrables.getColumn("nroDocum").setWidth(100);
		  gridNoCobrables.getColumn("referencia").setWidth(280);
		  gridNoCobrables.getColumn("simboloMoneda").setWidth(95);
		  
		  gridNoCobrables.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		  gridNoCobrables.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		  gridNoCobrables.getColumn("impTotMo").setHeaderCaption("Importe");
		
		
	}
	
	
	/**
	 *Inicializamos la Grilla de Gastos no cobrables
	 *para el proceso
	 *
	 */
	private void inicializarGrillaGastosCobrables(BeanItemContainer<GastoVO	> container){
		
		  gridCobrables.setContainerDataSource(container);
		
		//gridNoCobrables.getColumn("impTotMo").setHidden(true);
		
		  gridCobrables.getColumn("operacion").setHidden(true);
		  gridCobrables.getColumn("fechaMod").setHidden(true);
		  
		  gridCobrables.getColumn("codCtaInd").setHidden(true);
		  gridCobrables.getColumn("codCuenta").setHidden(true);
		  gridCobrables.getColumn("codDocum").setHidden(true);
		  gridCobrables.getColumn("codEmp").setHidden(true);
		  gridCobrables.getColumn("codImpuesto").setHidden(true);
		  gridCobrables.getColumn("codMoneda").setHidden(true);
		  //gridNoCobrables.getColumn("codProceso").setHidable(true);;
		  gridCobrables.getColumn("codRubro").setHidden(true);
		  gridCobrables.getColumn("codTitular").setHidden(true);
		  //gridNoCobrables.getColumn("cuenta").setHidable(true);;
		  gridCobrables.getColumn("descProceso").setHidden(true);
		  gridCobrables.getColumn("fecDoc").setHidden(true);
		  gridCobrables.getColumn("fecValor").setHidden(true);
		  gridCobrables.getColumn("impImpuMn").setHidden(true);
		  gridCobrables.getColumn("impImpuMo").setHidden(true);
		  gridCobrables.getColumn("impSubMn").setHidden(true);
		  gridCobrables.getColumn("impSubMo").setHidden(true);
		  gridCobrables.getColumn("linea").setHidden(true);
		  gridCobrables.getColumn("impTotMn").setHidden(true);
		  gridCobrables.getColumn("nomCuenta").setHidden(true);
		  gridCobrables.getColumn("nomImpuesto").setHidden(true);
		  gridCobrables.getColumn("nomMoneda").setHidden(true);
		  gridCobrables.getColumn("nomRubro").setHidden(true);
		  gridCobrables.getColumn("nomTitular").setHidden(true);
		  gridCobrables.getColumn("nroTrans").setHidden(true);
		  gridCobrables.getColumn("porcentajeImpuesto").setHidden(true);
		  gridCobrables.getColumn("serieDocum").setHidden(true);
		  
		  gridCobrables.getColumn("codProceso").setHidden(true);
		  gridCobrables.getColumn("estadoGasto").setHidden(true);
		  gridCobrables.getColumn("tipo").setHidden(true);
		  
		  gridCobrables.getColumn("tcMov").setHidden(true);
		  gridCobrables.getColumn("usuarioMod").setHidden(true);
		  gridCobrables.getColumn("nacional").setHidden(true);
		  
		  
		  /*Seteamos tamanios*/
		  gridCobrables.getColumn("nroDocum").setWidth(100);
		  gridCobrables.getColumn("referencia").setWidth(280);
		  gridCobrables.getColumn("simboloMoneda").setWidth(95);
		  
		  gridCobrables.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		  gridCobrables.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		  gridCobrables.getColumn("impTotMo").setHeaderCaption("Importe");
		
		
	}
	
	/**
	 *Inicializamos la Grilla de Gastos no cobrables
	 *para el proceso
	 *
	 */
	private void inicializarGrillaGastosAPagar(BeanItemContainer<GastoVO> container){
		
		gridAPagar.setContainerDataSource(container);
		
		//gridNoCobrables.getColumn("impTotMo").setHidden(true);
		
		gridAPagar.getColumn("operacion").setHidden(true);
		gridAPagar.getColumn("fechaMod").setHidden(true);
		  
		gridAPagar.getColumn("codCtaInd").setHidden(true);
		gridAPagar.getColumn("codCuenta").setHidden(true);
		gridAPagar.getColumn("codDocum").setHidden(true);
		gridAPagar.getColumn("codEmp").setHidden(true);
		gridAPagar.getColumn("codImpuesto").setHidden(true);
		gridAPagar.getColumn("codMoneda").setHidden(true);
		  //gridAPagar.getColumn("codProceso").setHidable(true);;
		gridAPagar.getColumn("codRubro").setHidden(true);
		gridAPagar.getColumn("codTitular").setHidden(true);
		  //gridAPagar.getColumn("cuenta").setHidable(true);;
		gridAPagar.getColumn("descProceso").setHidden(true);
		gridAPagar.getColumn("fecDoc").setHidden(true);
		gridAPagar.getColumn("fecValor").setHidden(true);
		gridAPagar.getColumn("impImpuMn").setHidden(true);
		gridAPagar.getColumn("impImpuMo").setHidden(true);
		gridAPagar.getColumn("impSubMn").setHidden(true);
		gridAPagar.getColumn("impSubMo").setHidden(true);
		gridAPagar.getColumn("linea").setHidden(true);
		gridAPagar.getColumn("impTotMn").setHidden(true);
		gridAPagar.getColumn("nomCuenta").setHidden(true);
		gridAPagar.getColumn("nomImpuesto").setHidden(true);
		gridAPagar.getColumn("nomMoneda").setHidden(true);
		gridAPagar.getColumn("nomRubro").setHidden(true);
		gridAPagar.getColumn("nomTitular").setHidden(true);
		gridAPagar.getColumn("nroTrans").setHidden(true);
		gridAPagar.getColumn("porcentajeImpuesto").setHidden(true);
		gridAPagar.getColumn("serieDocum").setHidden(true);
		  
		gridAPagar.getColumn("codProceso").setHidden(true);
		gridAPagar.getColumn("estadoGasto").setHidden(true);
		gridAPagar.getColumn("tipo").setHidden(true);
		  
		gridAPagar.getColumn("tcMov").setHidden(true);
		gridAPagar.getColumn("usuarioMod").setHidden(true);
		gridAPagar.getColumn("nacional").setHidden(true);
		  
		  
		  /*Seteamos tamanios*/
		gridAPagar.getColumn("nroDocum").setWidth(100);
		gridAPagar.getColumn("referencia").setWidth(280);
		gridAPagar.getColumn("simboloMoneda").setWidth(95);
		  
		gridAPagar.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		gridAPagar.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		gridAPagar.getColumn("impTotMo").setHeaderCaption("Importe");
		
		
	}
	
	/**
	 *Inicializamos la Grilla de Gastos no cobrables
	 *para el proceso
	 *
	 */
	private void inicializarGrillaGastosAnular(BeanItemContainer<GastoVO> container){
		
		gridAnular.setContainerDataSource(container);
		
		//gridAnular.getColumn("impTotMo").setHidden(true);
		
		gridAnular.getColumn("operacion").setHidden(true);
		gridAnular.getColumn("fechaMod").setHidden(true);
		  
		gridAnular.getColumn("codCtaInd").setHidden(true);
		gridAnular.getColumn("codCuenta").setHidden(true);
		gridAnular.getColumn("codDocum").setHidden(true);
		gridAnular.getColumn("codEmp").setHidden(true);
		gridAnular.getColumn("codImpuesto").setHidden(true);
		gridAnular.getColumn("codMoneda").setHidden(true);
		  //gridAnular.getColumn("codProceso").setHidable(true);;
		gridAnular.getColumn("codRubro").setHidden(true);
		gridAnular.getColumn("codTitular").setHidden(true);
		  //gridAnular.getColumn("cuenta").setHidable(true);;
		gridAnular.getColumn("descProceso").setHidden(true);
		gridAnular.getColumn("fecDoc").setHidden(true);
		gridAnular.getColumn("fecValor").setHidden(true);
		gridAnular.getColumn("impImpuMn").setHidden(true);
		gridAnular.getColumn("impImpuMo").setHidden(true);
		gridAnular.getColumn("impSubMn").setHidden(true);
		gridAnular.getColumn("impSubMo").setHidden(true);
		gridAnular.getColumn("linea").setHidden(true);
		gridAnular.getColumn("impTotMn").setHidden(true);
		gridAnular.getColumn("nomCuenta").setHidden(true);
		gridAnular.getColumn("nomImpuesto").setHidden(true);
		gridAnular.getColumn("nomMoneda").setHidden(true);
		gridAnular.getColumn("nomRubro").setHidden(true);
		gridAnular.getColumn("nomTitular").setHidden(true);
		gridAnular.getColumn("nroTrans").setHidden(true);
		gridAnular.getColumn("porcentajeImpuesto").setHidden(true);
		gridAnular.getColumn("serieDocum").setHidden(true);
		  
		gridAnular.getColumn("codProceso").setHidden(true);
		gridAnular.getColumn("estadoGasto").setHidden(true);
		gridAnular.getColumn("tipo").setHidden(true);
		  
		gridAnular.getColumn("tcMov").setHidden(true);
		gridAnular.getColumn("usuarioMod").setHidden(true);
		gridAnular.getColumn("nacional").setHidden(true);
		  
		  
		  /*Seteamos tamanios*/
		gridAnular.getColumn("nroDocum").setWidth(100);
		gridAnular.getColumn("referencia").setWidth(280);
		gridAnular.getColumn("simboloMoneda").setWidth(95);
		  
		gridAnular.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
		gridAnular.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		gridAnular.getColumn("impTotMo").setHeaderCaption("Importe");
		
		
	}
	
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrillaGridNoCobrables()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridNoCobrables.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridNoCobrables.getContainerDataSource()
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
				    	this.containerGastosNoCobrables.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.containerGastosNoCobrables.addContainerFilter(
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
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrillaGridCobrables()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridCobrables.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridCobrables.getContainerDataSource()
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
				    	this.containerGastosCobrables.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.containerGastosCobrables.addContainerFilter(
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
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrillaGridAPagar()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridAPagar.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridAPagar.getContainerDataSource()
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
				    	this.containerGastosAPagar.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.containerGastosAPagar.addContainerFilter(
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
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrillaGridAnular()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridAnular.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridAnular.getContainerDataSource()
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
				    	this.containerGastosAnulados.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.containerGastosAnulados.addContainerFilter(
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

	@Override
	public void setSub(String seleccion) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actuilzarGrillaEliminado(long codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarMensaje(String msj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInfoLst(GastoVO gasto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String nomForm() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un gasto
	 * desde ProcesoViewExtended
	 *
	 */
	public void actulaizarGrilla(GastoVO gastoVO) 
	{
		/*Segun la lista que se haya seleccionado*/
		BeanItemContainer<GastoVO> container =  null;
		ArrayList<GastoVO> lst = new ArrayList<>();
		
		switch(this.grillaDesde){
		
		case "gridNoCobrables" : container = containerGastosNoCobrables;
								 lst = lstGastosNoCobrables;
			break;
			
		case "gridCobrables" : container = containerGastosCobrables;
		 lst = lstGastosCobrables;
		    break;
		    
		case "gridAPagar" : container = containerGastosAPagar;
		 lst = lstGastosGastosAPagar;
		 	break;
		 	
		case "gridAnular" : container = containerGastosAnulados;
		 lst = lstGastosGastosAnulados;
		 	break;
		
	
		}
		this.actualizarGastoenLista(gastoVO);
		
		
		/*Actualizamos la grilla*/
		container.removeAllItems();
		container.addAll(lst);
		
		
		switch(this.grillaDesde){
			case "gridNoCobrables" : gridNoCobrables.setContainerDataSource(container);
				break;
		}
		
	}
	
	/**
	 * Modificamos un gastoVO de la lista cuando
	 * se hace una acutalizacion de un gasto
	 *
	 */
	private void actualizarGastoenLista(GastoVO gastoVO)
	{
		/*Segun la lista que se haya seleccionado*/
		ArrayList<GastoVO> lst = new ArrayList<>();
		
		switch(this.grillaDesde){
		
			case "gridNoCobrables" : lst = lstGastosNoCobrables;
			break;
			case "gridCobrables" : lst = lstGastosCobrables;
			break;
			case "gridAPagar" : lst = lstGastosGastosAPagar;
			break;
			case "gridAnular" : lst = lstGastosGastosAnulados;
			break;
		
		}
		
		
		int i =0;
		boolean salir = false;
		
		GastoVO gastoEnLista;
		
		while( i < lst.size() && !salir)
		{
			gastoEnLista = lst.get(i);
			
			if(gastoVO.getNroTrans() == gastoEnLista.getNroTrans()){
				
				lst.get(i).copiar(gastoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
}
