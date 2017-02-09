package com.vista.detFactura;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


//import org.vaadin.csvalidation.CSValidator;

import com.controladores.GastoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.excepciones.DocLog.InsertandoLogException;
import com.excepciones.DocLog.ModificandoLogException;
import com.excepciones.Gastos.EliminandoGastoException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.NoExisteGastoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.FuncionarioVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.RubroCuentaVO;
import com.valueObject.RubroVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Docum.AuxDetalleVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Gastos.IGastosMain;
import com.vista.Validaciones.Validaciones;
import com.vaadin.data.Property;

public class DetFacturaViewExtended extends DetFacturaView implements IBusqueda{
	
	final FieldGroup binder = new FieldGroup();
	private BeanFieldGroup<GastoVO> fieldGroup;
	private GastoControlador controlador;
	private String operacion;
	private IGastosMain mainView;
	MySub sub;
	private PermisosUsuario permisos;
	UsuarioPermisosVO permisoAux;
	Integer codigoInsert;
	String aux;
	NumeradoresVO codigos;
	String tipoTitular;
	String procesosCliente;
	Double importeMoneda = null, porcImpuesto = null, tipoCambio = null, importeImpuesto = null, cotizacionVenta = null;
	CotizacionVO cotizacion;
	MonedaVO monedaNacional;
	TitularVO titularVO;
	ArrayList<MonedaVO> lstMonedas;
	AuxDetalleVO datosCabezal;
	Validaciones val = new Validaciones();
	

	public DetFacturaViewExtended(String opera, IGastosMain main, AuxDetalleVO datosCabezal){

		
		
		this.datosCabezal = datosCabezal;
		
		this.cotizacion =  new CotizacionVO();
		this.monedaNacional = new MonedaVO();
		this.titularVO = new TitularVO();
		this.lstMonedas = new ArrayList<MonedaVO>();
		
		
		
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
									VariablesPermisos.FORMULARIO_GASTOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
					
					
					GastoVO gastoVO = new GastoVO();		
					
					gastoVO.setCodEmp(this.permisos.getCodEmp());
					gastoVO.setUsuarioMod(this.permisos.getUsuario());
					gastoVO.setOperacion(operacion);
					
					//if(comboSeleccion.getValue().equals("Proceso")){
						
						gastoVO.setDescProceso(descProceso.getValue().trim());
						
						if(codProceso.getValue() != "" && codProceso.getValue() != null){
							gastoVO.setCodProceso(codProceso.getValue());
						}
						else{
							gastoVO.setCodProceso("");
						}
					//}
					
					
					gastoVO.setCodDocum("Detalle");
					gastoVO.setSerieDocum("0");
					gastoVO.setCodImpuesto(codImpuesto.getValue().trim());
					gastoVO.setNomImpuesto(nomImpuesto.getValue().trim());
					
					
					gastoVO.setPorcentajeImpuesto((double) porcentajeImpuesto.getConvertedValue());
					
					
					gastoVO.setCodCtaInd("detalle");
					
				
					gastoVO.setCodTitular(codTitular.getValue().trim());
					gastoVO.setNomTitular(nomTitular.getValue().trim());
					
					
					
					//Moneda
					if(this.comboMoneda.getValue() != null){
						MonedaVO auxMoneda = new MonedaVO();
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						gastoVO.setCodMoneda(auxMoneda.getCodMoneda());
						gastoVO.setNomMoneda(auxMoneda.getDescripcion());
						gastoVO.setSimboloMoneda(auxMoneda.getSimbolo());
						gastoVO.setNacional(auxMoneda.isNacional());
					}
					else{
						gastoVO.setCodMoneda("");
						gastoVO.setNomMoneda("");
						gastoVO.setSimboloMoneda("");
					}
					
					gastoVO.setEstadoGasto("detalle");
					
					
					gastoVO.setReferencia(referencia.getValue().trim());
					gastoVO.setCodCuenta(codCuenta.getValue().trim());
					gastoVO.setNomCuenta(nomCuenta.getValue().trim());
					gastoVO.setCodRubro(codRubro.getValue().trim());
					gastoVO.setNomRubro(nomRubro.getValue().trim());
					
					
					if(impTotMn.getValue() != "" && impTotMn.getValue() != null){
						gastoVO.setImpTotMn((double) impTotMn.getConvertedValue());
					}
					else{
						gastoVO.setImpTotMn(0);
					}
					
					if(impTotMo.getValue() != "" && impTotMo.getValue() != null){
						gastoVO.setImpTotMo((double) impTotMo.getConvertedValue());
					}
					else{
						gastoVO.setImpTotMo(0);
					}
					
					if(impImpuMn.getValue() != "" && impImpuMn.getValue() != null){
						gastoVO.setImpImpuMn((double) impImpuMn.getConvertedValue());
					}
					else{
						gastoVO.setImpImpuMn(0);
					}
					
					if(impImpuMo.getValue() != "" && impImpuMo.getValue() != null){
						gastoVO.setImpImpuMo((double) impImpuMo.getConvertedValue());
					}
					else{
						gastoVO.setImpImpuMo(0);
					}
					
					
					gastoVO.setImpSubMn(gastoVO.getImpTotMn() - gastoVO.getImpImpuMn());  
					gastoVO.setImpSubMo(gastoVO.getImpTotMo() - gastoVO.getImpImpuMo());
					
					if(tcMov.getValue() != "" && tcMov.getValue() != null){
						gastoVO.setTcMov(Double.valueOf(tcMov.getConvertedValue().toString()));
					}
					else{
						gastoVO.setTcMov(0);
					}
					
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						gastoVO.setNroDocum((String.valueOf(0)));
					}
					else{
						gastoVO.setNroDocum((nroDocum.getValue()));
					}
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						gastoVO.setNroTrans((0));
					}
					else{
						gastoVO.setNroTrans(((Long) nroTrans.getConvertedValue()));;
					}
					
					gastoVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
					gastoVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						/*Si el mainView es el panel:*/
						if(this.mainView.nomForm().equals("Panel")){
							
							codigos = this.controlador.insertarGasto(gastoVO, permisoAux);
							gastoVO.setNroDocum(String.valueOf(codigos.getCodigo()));
							gastoVO.setNroTrans(codigos.getNumeroTrans());
							
							this.mainView.actulaizarGrilla(gastoVO);
							
							Mensajes.mostrarMensajeOK("Se ha guardado el gasto");
							main.cerrarVentana();
							
						}else if(this.mainView.nomForm().equals("Egreso")){ /*Si es del form de Egreso Cobro*/
							
							this.mainView.setInfoLst(gastoVO); 
							main.cerrarVentana();
						}
						else if(this.mainView.nomForm().equals("Factura")){ /*Si es del form de Factura*/
							
							this.mainView.setInfoLst(gastoVO); 
							main.cerrarVentana();
						}
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						/*Si el mainView es el panel:*/
						if(this.mainView.nomForm().equals("Panel")){
							this.controlador.actualizarGasto(gastoVO, permisoAux);
							this.mainView.actulaizarGrilla(gastoVO);
							
							Mensajes.mostrarMensajeOK("Se ha modificado el gasto");
							main.cerrarVentana();
							
						}else if(this.mainView.nomForm().equals("Egreso")){ /*Si es del form de Egreso Cobro*/
							
							this.mainView.actulaizarGrilla(gastoVO);
							main.cerrarVentana();
						}
						else if(this.mainView.nomForm().equals("Factura")){
							this.mainView.actulaizarGrilla(gastoVO);
							main.cerrarVentana();
						}
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
			} 
			catch (ErrorInesperadoException | ConexionException | NoExisteGastoException | 
					ModificandoGastoException | ExisteGastoException | InicializandoException | 
					ObteniendoPermisosException | NoTienePermisosException | IngresandoGastoException | 
					ModificandoSaldoException | EliminandoSaldoException | IngresandoSaldoException | 
					InsertandoLogException | ModificandoLogException | ExisteSaldoException e) {
				
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
				
		});
		
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
			int mes, anio;
			try {
				
				
			
				permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_GASTOS,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				
				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
					return;
				}
				
				this.iniFormEditar();
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
				
			}
			
		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
		
		this.btnEliminar.addClickListener(click -> {
			
			UsuarioPermisosVO permisoAux = 
			new UsuarioPermisosVO(this.permisos.getCodEmp(),
					this.permisos.getUsuario(),
					VariablesPermisos.FORMULARIO_GASTOS,
					VariablesPermisos.OPERACION_BORRAR);
			
			try {
				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
					return;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				GastoVO gastoVO = new GastoVO();
				
				gastoVO.setCodDocum(codDocum.getValue());
				gastoVO.setSerieDocum(serieDocum.getValue());
				gastoVO.setNroDocum(nroDocum.getValue());
				gastoVO.setCodEmp(permisoAux.getCodEmp());
				gastoVO.setCodTitular(codTitular.getValue());
				gastoVO.setNroTrans((long) nroTrans.getConvertedValue());
				
				controlador.eliminarGasto(gastoVO, permisoAux);
				this.mainView.actuilzarGrillaEliminado((long) nroTrans.getConvertedValue());
				Mensajes.mostrarMensajeOK("Se ha eliminado el gasto");
				main.cerrarVentana();
				
			} catch (ObteniendoPermisosException | ConexionException | InicializandoException | ExisteGastoException |
					EliminandoGastoException | EliminandoProcesoException | NoTienePermisosException | EliminandoSaldoException e) {
				// TODO Auto-generated catch block
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
		});
			

		this.btnBuscarRubro.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new RubroCuentaVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<RubroCuentaVO> lstRubros = new ArrayList<RubroCuentaVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstRubros = this.controlador.getRubrosCuentasActivosFacturable(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoRubrosException | com.excepciones.Rubros.ObteniendoRubrosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (RubroCuentaVO i: lstRubros) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("550px", "950px" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		
		this.btnBuscarImpuesto.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ImpuestoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ImpuestoVO> lstImpuestos = new ArrayList<ImpuestoVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstImpuestos = this.controlador.getImpuestos(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoImpuestosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (ImpuestoVO i: lstImpuestos) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("450px", "850px" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
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
					//comboMoneda.setData("ProgramaticallyChanged");
					
					auxMoneda = (MonedaVO) comboMoneda.getValue();
					Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
					
					try {
						
						if(auxMoneda.getCodMoneda() != null && !auxMoneda.isNacional()){
							cotizacion = controlador.getCotizacion(permisoAux, fecha, auxMoneda.getCodMoneda());
							if(cotizacion.getCotizacionVenta() != 0 && !auxMoneda.isNacional()){
								cotizacionVenta = cotizacion.getCotizacionVenta();
								//tcMov.setEnabled(true);
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
						
						Mensajes.mostrarMensajeError(e.getMessage());
					}
					if(fecha != null){
	   					
	   					for (MonedaVO monedaVO : lstMonedas) {
	   						
	   						monedaVO = seteaCotizaciones(monedaVO);
	   					}
	   					
	   				}
				}
			}
		});
		
		
		
		this.impTotMo.addValueChangeListener(new Property.ValueChangeListener() {
			
		    public void valueChange(ValueChangeEvent event) {
		    	
		    	if("ProgramaticallyChanged".equals(impTotMo.getData())){
		    		impTotMo.setData(null);
		             return;
		         }
		    	
		        String value = (String) event.getProperty().getValue();
		        
		        if(value != ""){
		        	
		        	try {
		        		importeMoneda = (Double) impTotMo.getConvertedValue();
					} catch (Exception e) {
						
						return;
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
		        		cotizacionVenta = Double.valueOf(tcMov.getConvertedValue().toString());
					} catch (Exception e) {
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
		    }
		});

		this.porcentajeImpuesto.addValueChangeListener(new Property.ValueChangeListener() {
			
		    public void valueChange(ValueChangeEvent event) {
		    	
		    	if("ProgramaticallyChanged".equals(porcentajeImpuesto.getData())){
		    		porcentajeImpuesto.setData(null);
		             return;
		         }
		    	
		        String value = (String) event.getProperty().getValue();
		        
		        if(value != ""){
		        	
		        	try {
		        		porcImpuesto = (Double) porcentajeImpuesto.getConvertedValue();
					} catch (Exception e) {
						return;
					}
		        	
		        	
		        	Double truncatedDouble = new BigDecimal(porcImpuesto)
						    .setScale(2, BigDecimal.ROUND_HALF_UP)
						    .doubleValue();
					
		        	porcImpuesto = truncatedDouble;
		        	
		        	if(operacion != Variables.OPERACION_LECTURA){

			        	calculos();
			        }
		        	
		    	}
		    }
		});
		
		
	}
	
	public  void inicializarForm(){
		
		this.controlador = new GastoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<GastoVO>(GastoVO.class);
		
		//inicializar los valores de los combos impuesto y tipo de rubro
		inicializarComboMoneda(null);
		
		this.inicializarCampos();
		
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			
			/*Inicializamos al formulario como nuevo*/
			this.comboSeleccion.setValue("Proceso");
			this.iniFormNuevo();
			
			/*Inicalizamos moneda del cabezal de la factura pasada por parametro y tc*/
			this.inicializarComboMoneda(this.datosCabezal.getCodMoneda());
			this.tcMov.setConvertedValue(this.datosCabezal.getTc());
			this.cotizacionVenta = this.datosCabezal.getTc();
			
			this.fecDoc.setConvertedValue(this.datosCabezal.getFecDoc());
			this.fecValor.setConvertedValue(this.datosCabezal.getFecValor());
			
			this.codTitular.setConvertedValue(this.datosCabezal.getCodTitular());
			this.nomTitular.setValue("");
			this.codProceso.setConvertedValue(String.valueOf(this.datosCabezal.getCodProceso()));
			this.descProceso.setConvertedValue("");
			
			this.comboMoneda.setEnabled(false); /*La moneda es del cabezal y no se cambia*/
			
	
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))	{
			/*Inicializamos formulario como editar*/
			
			
			this.iniFormLectura();
		
		} 
		
		
		
//		CotizacionVO cotizacion;
//		cotizacion = getCotizacion(permisoAux, this.convertFromJAVADateToSQLDate(this.fecValor.getValue()),"1");
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
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
		this.tcMov.setRequired(setear);
		this.tcMov.setRequiredError("Es requerido");
		
		this.codImpuesto.setRequired(setear);
		this.codImpuesto.setRequiredError("Es requerido");
		
		this.impImpuMn.setRequired(setear);
		this.impImpuMn.setRequiredError("Es requerido");
		
		this.impImpuMo.setRequired(setear);
		this.impImpuMo.setRequiredError("Es requerido");
		
		this.impTotMn.setRequired(setear);
		this.impTotMn.setRequiredError("Es requerido");
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
		this.codRubro.setRequired(setear);
		this.codRubro.setRequiredError("Es requerido");
		
		this.codCuenta.setRequired(setear);
		this.codCuenta.setRequiredError("Es requerido");
		
		this.codProceso.setRequired(setear);
		this.codProceso.setRequiredError("Es requerido");
		
		this.comboEstado.setRequired(setear);
		this.comboEstado.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item ProcesoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<GastoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		GastoVO gasto = new GastoVO();
		gasto = fieldGroup.getItemDataSource().getBean();
		//this.obseAux.setValue(gasto.getObservaciones()); 
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(gasto.getFechaMod());
		
		
		auditoria.setDescription(
				"Usuario: " + gasto.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operación: " + gasto.getOperacion());
		
		this.inicializarComboMoneda(gasto.getCodMoneda());
		this.inicializarComboEstado(gasto.getEstadoGasto());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA)){
			this.iniFormLectura();
			this.comboSeleccion.setEnabled(false);
		}
		
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_BORRAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		if(permisoEliminar)
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			
			
			
			this.disableBotonLectura();

			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
			
			if(operacion.equals(Variables.OPERACION_EDITAR)){
				importeMoneda = (Double) impTotMo.getConvertedValue();
				porcImpuesto  = (Double) porcentajeImpuesto.getConvertedValue();
				tipoCambio = Double.valueOf(tcMov.getConvertedValue().toString());
				importeImpuesto = (Double) impImpuMo.getConvertedValue();
				Double.valueOf(tcMov.getConvertedValue().toString());
				this.comboEstado.setEnabled(true);
			}
			
		
		}
		else{
			
			/*Mostramos mensaje Sin permisos para operacion*/
			//Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		this.comboEstado.setEnabled(true);
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
		this.fecValor.setReadOnly(false);
		this.tcMov.setReadOnly(false);
		this.impImpuMn.setReadOnly(false);
		this.impImpuMn.setEnabled(false);
		this.impImpuMo.setReadOnly(false);
		this.impImpuMo.setEnabled(false);
		this.impTotMn.setReadOnly(false);
		this.impTotMn.setEnabled(false);
		this.impTotMo.setReadOnly(false);
		this.referencia.setReadOnly(false);
		
		this.codProceso.setReadOnly(false);
		this.descProceso.setReadOnly(false);
		this.codTitular.setReadOnly(false);
		this.nomTitular.setReadOnly(false);
		this.codRubro.setReadOnly(false);
		this.nomRubro.setReadOnly(false);
		this.codCuenta.setReadOnly(false);
		this.nomCuenta.setReadOnly(false);
		this.codImpuesto.setReadOnly(false);
		this.nomImpuesto.setReadOnly(false);
		this.porcentajeImpuesto.setReadOnly(false);
		this.nroDocum.setReadOnly(false);
		
		this.comboMoneda.setEnabled(true);
		
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
		
		this.btnBuscarRubro.setEnabled(false);
		this.btnBuscarRubro.setVisible(false);
		
		this.btnBuscarImpuesto.setEnabled(false);
		this.btnBuscarImpuesto.setVisible(false);
		
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
		
		this.btnBuscarRubro.setEnabled(true);
		this.btnBuscarRubro.setVisible(true);
		
		this.btnBuscarImpuesto.setEnabled(true);
		this.btnBuscarImpuesto.setVisible(true);
		
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
		this.fecDoc.setReadOnly(setear);
		this.fecValor.setReadOnly(setear);
		this.tcMov.setReadOnly(setear);
		this.impImpuMn.setReadOnly(setear);
		this.impImpuMn.setEnabled(false);
		this.impImpuMo.setReadOnly(setear);
		this.impImpuMo.setEnabled(false);
		this.impTotMn.setEnabled(false);
		this.impTotMn.setReadOnly(setear);
		this.impTotMo.setReadOnly(setear);
		this.referencia.setReadOnly(setear);
		
		this.codProceso.setEnabled(false);
		this.descProceso.setEnabled(false);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		this.codRubro.setEnabled(false);
		this.nomRubro.setEnabled(false);
		this.codCuenta.setEnabled(false);
		this.nomCuenta.setEnabled(false);
		this.codImpuesto.setEnabled(false);
		this.nomImpuesto.setEnabled(false);
		this.porcentajeImpuesto.setEnabled(false);
		this.comboMoneda.setEnabled(false);
		this.nroDocum.setEnabled(false);
		this.comboEstado.setEnabled(false);
		
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
						"50 caracteres máximo", 0, 50, true));
        
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
				
		boolean a = this.impImpuMn.isValid();
		boolean b = this.impTotMn.isValid();
		
		try
		{
			if(  this.codRubro.isValid() && this.codCuenta.isValid()
					&& this.codImpuesto.isValid() && this.comboMoneda.isValid()
					&& this.tcMov.isValid() && this.impTotMo.isValid() && this.impTotMn.isValid()
					&& this.impImpuMo.isValid() && this.impImpuMn.isValid() && this.referencia.isValid())
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

		if(datos instanceof ProcesoVO){
			ProcesoVO procesoVO = (ProcesoVO) datos;
			this.codProceso.setValue(String.valueOf(procesoVO.getCodigo()));
			this.descProceso.setValue(procesoVO.getDescripcion());
			this.codTitular.setValue((procesoVO.getCodCliente()));
			this.nomTitular.setValue(procesoVO.getNomCliente());
		}
		if(datos instanceof RubroVO){
			RubroVO rubroVO = (RubroVO) datos;
			this.codRubro.setValue(rubroVO.getcodRubro());
			this.nomRubro.setValue(rubroVO.getDescripcion());
 			this.nomImpuesto.setValue(rubroVO.getDescripcionImpuesto());
			this.codImpuesto.setValue(rubroVO.getCodigoImpuesto());
        	
        	Double truncatedDouble = new BigDecimal(rubroVO.getPorcentajeImpuesto())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
        	
			porcentajeImpuesto.setConvertedValue(truncatedDouble);
			porcImpuesto = truncatedDouble;
		}
		
		if(datos instanceof RubroCuentaVO){
			RubroCuentaVO rubroVO = (RubroCuentaVO) datos;
			this.codRubro.setValue(rubroVO.getCod_rubro());
			this.nomRubro.setValue(rubroVO.getDescripcionRubro());
 			this.nomImpuesto.setValue(rubroVO.getDescripcionImpuesto());
			this.codImpuesto.setValue(rubroVO.getCod_impuesto());
			this.codCuenta.setValue(rubroVO.getCod_cuenta());
			this.nomCuenta.setValue(rubroVO.getDescripcionCuenta());
        	
        	Double truncatedDouble = new BigDecimal(rubroVO.getPorcentaje())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
        	
			porcentajeImpuesto.setConvertedValue(truncatedDouble);
			porcImpuesto = truncatedDouble;
		}
		
		if(datos instanceof CuentaVO){
			CuentaVO cuentaVO = (CuentaVO) datos;
			this.codCuenta.setValue(cuentaVO.getCodCuenta());
			this.nomCuenta.setValue(cuentaVO.getDescripcion());
			
		}
		
		if(datos instanceof ImpuestoVO){
			
			ImpuestoVO impuestoVO = (ImpuestoVO) datos;
			String aux = String.valueOf(impuestoVO.getPorcentaje());
			aux = aux.replace(".", ",");
			this.codImpuesto.setValue(impuestoVO.getcodImpuesto());
			this.nomImpuesto.setValue(impuestoVO.getDescripcion());
			//this.porcentajeImpuesto.setValue(String.valueOf(impuestoVO.getPorcentaje()));
        	
        	Double truncatedDouble = new BigDecimal(impuestoVO.getPorcentaje())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
        	
			porcentajeImpuesto.setConvertedValue(truncatedDouble);
			porcImpuesto = truncatedDouble;
			
			calculos();
		}
		
		if(datos instanceof FuncionarioVO){
			FuncionarioVO funcionarioVO = (FuncionarioVO) datos;
			this.codTitular.setValue(String.valueOf(funcionarioVO.getCodigo()));
			this.nomTitular.setValue(funcionarioVO.getNombre());
			
		}
		
	}
	
	public void inicializarComboMoneda(String cod){
		
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		
		try {
			permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
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
			//tcMov.setEnabled(true);
		}
		else{
			tcMov.setEnabled(false);
		}
	}
	
	public void inicializarComboEstado(String estado){
		
		if(estado.equals("cobr")){
			this.comboEstado.setValue("Cobrable");
		}
		if(estado.equals("nocobr")){
			this.comboEstado.setValue("No cobrable");
		}
		if(estado.equals("fact")){
			this.comboEstado.setValue("Facturable");
		}
		
	}
	
	
	@Override
	 public void setInfoLst(ArrayList<Object> lstDatos) {
	  // TODO Auto-generated method stub
	  
	}
	
	public static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	public void calculos(){
		double aux;
		double aux2;
		
		if(porcImpuesto != null && importeMoneda != null){
			
			aux = (porcImpuesto/100)+1;
			aux2 = importeMoneda/aux;
			importeImpuesto = importeMoneda - aux2;
			
			Double truncatedDouble = new BigDecimal(importeImpuesto)
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
			
			importeImpuesto = truncatedDouble;
			impImpuMo.setData("ProgramaticallyChanged");
			impImpuMo.setConvertedValue(truncatedDouble);
		}
		
		if(importeMoneda != null && cotizacionVenta != null){
			
			Double truncatedDouble = new BigDecimal(importeMoneda.doubleValue() * cotizacionVenta.doubleValue())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
			
			impTotMn.setData("ProgramaticallyChanged");
			impTotMn.setConvertedValue(truncatedDouble);
		}
		
		if(importeImpuesto != null && cotizacionVenta != null){
			
			Double truncatedDouble = new BigDecimal(importeImpuesto.doubleValue() * cotizacionVenta.doubleValue())
				    .setScale(2, BigDecimal.ROUND_HALF_UP)
				    .doubleValue();
			
			impImpuMn.setData("ProgramaticallyChanged");
			impImpuMn.setConvertedValue(truncatedDouble);
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
		
		porcentajeImpuesto.setConverter(Double.class);
		porcentajeImpuesto.setConversionError("Error en formato de número");
		
		impImpuMn.setConverter(Double.class);
		impImpuMn.setConversionError("Error en formato de número");
		
		impImpuMo.setConverter(Double.class);
		impImpuMo.setConversionError("Error en formato de número");
		
		tcMov.setConverter(BigDecimal.class);
		tcMov.setConversionError("Error en formato de número");
		
		impTotMn.setConverter(Double.class);
		impTotMn.setConversionError("Error en formato de número");
		
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de número");
		
		nroDocum .setConverter(Integer.class);
		nroDocum .setConversionError("Ingrese un número entero");
		
		nroTrans .setConverter(Long.class);
		nroTrans .setConversionError("Ingrese un número entero");
		
		porcentajeImpuesto.setData("ProgramaticallyChanged");
		tcMov.setData("ProgramaticallyChanged");
		impTotMo.setData("ProgramaticallyChanged");
		
	}
	
	public MonedaVO seteaCotizaciones(MonedaVO monedaVO){
		
		UsuarioPermisosVO permisosAux;
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_GASTOS,
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
}
