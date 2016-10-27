package com.vista.Egreso;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.controladores.IngresoEgresoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Egresos.*;
import com.excepciones.Monedas.ObteniendoMonedaException;
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
import com.valueObject.DocumDGIVO;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.MonedaVO;
import com.valueObject.TipoInCobVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Gasto.GtoSaldoAux;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Gastos.GastoViewExtended;
import com.vista.Gastos.IGastosMain;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

public class IngresoEgresoViewExtended extends IngresoEgresoViews implements IBusqueda, IGastosMain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BeanFieldGroup<IngresoCobroVO> fieldGroup;
	private ArrayList<IngresoCobroDetalleVO> lstDetalleVO; /*Lista de detalle del Cobro*/
	private ArrayList<IngresoCobroDetalleVO> lstDetalleAgregar; /*Lista de detalle a agregar*/
	private IngresoEgresoControlador controlador;
	private String operacion;
	private IngresoEgresoPanelExtended mainView;
	BeanItemContainer<IngresoCobroDetalleVO> container;
	private IngresoCobroDetalleVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
	UsuarioPermisosVO permisoAux;
	CotizacionVO cotizacion =  new CotizacionVO();
	Double cotizacionVenta = null;
	TitularVO titularVO = new TitularVO();
	private Hashtable<Integer, GtoSaldoAux> saldoOriginalGastos; /*Variable auxliar para poder
	 															 controlar que el saldo del gasto quede
	 															 en negativo*/
	
	IngresoCobroVO ingresoCopia; /*Variable utilizada para la modificacion del cobro,
	 							 para poder detectar las lineas eliminadas del cobro */
	
	boolean cambioMoneda;
	MonedaVO monedaNacional = new MonedaVO();
	
	MySub sub;
	//private UsuarioPermisosVO permisos; /*Variable con los permisos del usuario*/
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public IngresoEgresoViewExtended(String opera, IngresoEgresoPanelExtended main){
	
	
	this.cambioMoneda = false;
	
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	saldoOriginalGastos = new Hashtable<Integer, GtoSaldoAux>();
	
	this.operacion = opera;
	this.mainView = main;
	lstGastos.setEditorBuffered(true);
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstDetalleAgregar = new ArrayList<IngresoCobroDetalleVO>();
	
	this.inicializarForm();
	
	
	
	this.btnBuscarCliente.addClickListener(click -> {
		
//		BusquedaViewExtended form = new BusquedaViewExtended(this, new ClienteVO());
//		ArrayList<Object> lst = new ArrayList<Object>();
//		ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();
		
		BusquedaViewExtended form = new BusquedaViewExtended(this, new TitularVO());
		ArrayList<Object> lst = new ArrayList<Object>();
		ArrayList<TitularVO> lstClientes = new ArrayList<TitularVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
						VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		try {
			
			lstClientes = this.controlador.getTitulares(permisoAux);
			//lstClientes = this.controlador.getClientes(permisoAux);
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
				 ObteniendoClientesException | ObteniendoTitularesException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		Object obj;
		for (TitularVO i: lstClientes) {
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
				
				inicializarComboCuentas(bcoAux.getCodigo());
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
  //  combobox.setImmediate(true);

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
   							VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
   			
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
						Mensajes.mostrarMensajeError("Debe cargar la cotizaci�n para la moneda");
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
								VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				
				IngresoCobroVO ingCobroVO = new IngresoCobroVO();	
				
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
						
						/*SI EL TIPO ES CAJA TOMAMOS LA MONEDA DEL CABEZAL DEL COBRO*/
						//if(((String)comboTipo.getValue()).equals("Caja")) { 
						
							ingCobroVO.setCodMoneda(auxMoneda.getCodMoneda());
							ingCobroVO.setNomMoneda(auxMoneda.getDescripcion());
							ingCobroVO.setSimboloMoneda(auxMoneda.getSimbolo());
						//}
					}
					
					
					/////////////////////////////FIN MONEDA////////////////////////////////////////////
					
					/*Si la moneda del cabezal es nacional*/
					if(auxMoneda.isNacional()) /*Si la moneda del cabezal del cobro seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						ingCobroVO.setTcMov(1);
						ingCobroVO.setImpTotMn(ingCobroVO.getImpTotMo());
						
					}else
					{
						coti = this.controlador.getCotizacion(permisoAux, fecha, this.getCodMonedaSeleccionada());
						ingCobroVO.setTcMov(coti.getCotizacionVenta());
						ingCobroVO.setImpTotMn((ingCobroVO.getImpTotMo()*ingCobroVO.getTcMov()));
					}
					
				} catch (Exception e) {
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				ingCobroVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
				ingCobroVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
				/*Codigo y serie docum se inicializan en constructor*/
				//ingCobroVO.setNroDocum(Integer.parseInt(nroDocum.getValue()));  VER ESTO CON EL NUMERADOR
				ingCobroVO.setCodEmp(permisos.getCodEmp());
				ingCobroVO.setReferencia(referencia.getValue());
				
				ingCobroVO.setCodCtaInd("egrcobro");
				
				
				ingCobroVO.setCodTitular(codTitular.getValue());
				ingCobroVO.setNomTitular(nomTitular.getValue());
				
				ingCobroVO.setOperacion(operacion);
				
				/*Ver los totales y tc*/
				//ingCobroVO.setImpTotMn(impTotMn);
				//ingCobroVO.setImpTotMo(impTotMn);
				//ingCobroVO.setTcMov(tcMov);
				
				/*Si es nuevo aun no tenemos el nro del cobro*/
				if(this.nroDocum.getValue() != null)
					ingCobroVO.setNroDocum(Integer.parseInt(this.nroDocum.getValue().toString().trim()));
				
				
				/*Si es banco tomamos estos cmapos de lo contrario caja*/
				if(this.comboTipo.getValue().toString().equals("Banco")){
				
					ingCobroVO.setmPago((String)comboMPagos.getValue());
					
					if(ingCobroVO.getmPago().equals("transferencia"))
					{
						ingCobroVO.setCodDocRef("tranemi");
						
						ingCobroVO.setSerieDocRef("0");
					}
					else if(ingCobroVO.getmPago().equals("Cheque"))
					{
						ingCobroVO.setCodDocRef("cheqemi");
						ingCobroVO.setNroDocRef((Integer) nroDocRef.getConvertedValue());
						ingCobroVO.setSerieDocRef(serieDocRef.getValue());
						
					}else
					{
						
						ingCobroVO.setCodDocRef("0");
						ingCobroVO.setNroDocRef(0);
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
						ingCobroVO.setNacionalMonedaCtaBco(auxctaBco.getMonedaVO().isNacional());
					
						
						
					
				}
				else {
					
					if(((String)comboTipo.getValue()).equals("Caja"))
					{
						ingCobroVO.setCodBanco("0");
						ingCobroVO.setNomBanco("0");
						
						ingCobroVO.setCodCtaBco("0");
						ingCobroVO.setNomCtaBco("0");
						
						ingCobroVO.setCodDocRef("0");
						ingCobroVO.setNroDocRef(0);
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
				
				
				/*Si hay detalle nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstDetalleAgregar.size() > 0)
				{
					for (IngresoCobroDetalleVO f : this.lstDetalleAgregar) {
						
						/*Si no esta lo agregamos*/
						if(!this.existeFormularioenLista(f.getNroDocum()))
							this.lstDetalleVO.add(f);
					}
				}
					
				ingCobroVO.setCodCuenta("egrcobro");
				ingCobroVO.setDetalle(this.lstDetalleVO);
				
				if(ingCobroVO.getDetalle().size() <= 0){
					Mensajes.mostrarMensajeError("El egreso no tiene detalle");
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
				
				ingCobroVO.setCodDocum("egrcobro");
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
					this.controlador.insertarIngresoEgreso(ingCobroVO, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Cobro");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*Si es edicion tomamos una copia del ingreso cobro, para detectar
					 * que lineas del cobro se eliminaron*/
					
					
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarIngresoEgreso(ingCobroVO,ingresoCopia, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Cobro");
					main.cerrarVentana();
					
				}
				
			}
			else /*Si los campos no son v�lidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (ModificandoEgresoCobroException| NoExisteEgresoCobroException |InsertandoEgresoCobroException| ExisteEgresoCobroException | InicializandoException| ConexionException | NoTienePermisosException| ObteniendoPermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
		});
	
	
	
	
	
	/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
		try {
			
			//this.codGrupo.setReadOnly(true);
			
			/*Inicializamos el Form en modo Edicion*/
			this.iniFormEditar();
			
			
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
			
		this.btnEliminar.addClickListener(click -> {
			
			UsuarioPermisosVO permisoAux = 
			new UsuarioPermisosVO(this.permisos.getCodEmp(),
					this.permisos.getUsuario(),
					VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
					VariablesPermisos.OPERACION_BORRAR);
			
//			try {
//				
//				GastoVO gastoVO = new GastoVO();
//				
//				gastoVO.setCodDocum(codDocum.getValue());
//				gastoVO.setSerieDocum(serieDocum.getValue());
//				gastoVO.setNroDocum((Integer) nroDocum.getConvertedValue());
//				gastoVO.setCodEmp(permisoAux.getCodEmp());
//				gastoVO.setCodTitular(codTitular.getValue());
//				gastoVO.setNroTrans((long) nroTrans.getConvertedValue());
//				
//				controlador.eliminarGasto(gastoVO, permisoAux);
//				this.mainView.actuilzarGrillaEliminado((long) nroTrans.getConvertedValue());
//				Mensajes.mostrarMensajeOK("Se ha eliminado el gasto");
//				main.cerrarVentana();
//				
//			} catch (ObteniendoPermisosException | ConexionException | InicializandoException | ExisteGastoException |
//					EliminandoGastoException | EliminandoProcesoException | NoTienePermisosException | EliminandoSaldoException e) {
//				// TODO Auto-generated catch block
//				Mensajes.mostrarMensajeError(e.getMessage());
//			}
//			
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
						//sub.setVista(form);
						sub.setVista((Component) form);
						
						sub.center();
						
						String codCliente;/*Codigo del cliente para obtener los gastos a cobrar del mismo*/
						
						/*Obtenemos los formularios que no estan en el grupo
						 * para mostrarlos en la grilla para seleccionar*/
						if(this.operacion.equals(Variables.OPERACION_NUEVO) )
						{
							/*Si la operacion es nuevo, ponemos el  codGrupo vacio
							 * asi nos trae todos los grupos disponibles*/
							codCliente = this.codTitular.getValue().toString().trim();
						}
						else 
						{
							/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
							codCliente = fieldGroup.getItemDataSource().getBean().getCodTitular();
						}
						
						/*Inicializamos VO de permisos para el usuario, formulario y operacion
						 * para confirmar los permisos del usuario*/
						UsuarioPermisosVO permisoAux = 
								new UsuarioPermisosVO(this.permisos.getCodEmp(),
										this.permisos.getUsuario(),
										VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
										VariablesPermisos.OPERACION_NUEVO_EDITAR);
						
	
						//Moneda
						MonedaVO auxMoneda = new MonedaVO();
						if(this.comboMoneda.getValue() != null){
							auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						}
						
						/*Obtenemos los gastos con saldo del cliente*/
						ArrayList<GastoVO> lstGastosConSaldo = this.controlador.getGastosConSaldo(permisoAux, codCliente);
						
						/*Hacemos una lista auxliar para pasarselo al BusquedaViewExtended*/
						ArrayList<Object> lst = new ArrayList<Object>();
						Object obj;
						for (GastoVO i: lstGastosConSaldo) {
							
							/*Verificamos que el gasto ya no esta en la grilla*/
							if(!this.existeFormularioenLista(i.getNroDocum()))
							{
								obj = new Object();
								obj = (Object)i;
								lst.add(obj);
							}
						}
						
						//form.inicializarGrilla(lst);
						
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

					/*Recorremos los gastos del cobro
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstDetalleVO.size() && !esta)
					{
						if(lstDetalleVO.get(i).getLinea()==formSelecccionado.getLinea())
						{
														
							/*Quitamos el formulario seleccionado de la lista*/
							lstDetalleVO.remove(lstDetalleVO.get(i));
						
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
						//lstFormularios.setContainerDataSource(container);
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
			    			BeanItem<IngresoCobroDetalleVO> item = container.getItem(lstGastos.getSelectedRow());
					    	formSelecccionado = item.getBean(); /*Seteamos el formulario
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
			
				/* ESTO LO DEJAMOS COMENTADO POR EL MOMENTO, VER COMO SE EDITA SI SE EDITA EL GTO
			boolean esta = false;	
	
			try {
				
				//Verificamos que haya un formulario seleccionado para eliminar
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_EDITAR);
					
					sub = new MySub("35%", "30%" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else //De lo contrario mostramos mensaje que debe selcionar un formulario
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para editar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			//Listener boton permisos
			this.btnVerPermisos.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				//Verificamos que haya un formulario seleccionado para eliminar
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_LECTURA);
					
					sub = new MySub("53%", "40%" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else //De lo contrario mostramos mensaje que debe selcionar un formulario
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			
			*/
			});
			
			
	///////////////////
	}

	public  void inicializarForm(){
		
		this.controlador = new IngresoEgresoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<IngresoCobroVO>(IngresoCobroVO.class);
		
		/*Mostramos o ocultamos los datos del Banco, dependiendo del combo tipo (banco, caja)*/
		this.mostrarDatosDeBanco();
		
		/*Inicializamos los combos*/
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null);
		this.inicializarComboMoneda(null);
		
		inicializarCampos();
		
	
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
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<IngresoCobroVO> item)
	{
		try{
			
		this.fieldGroup.setItemDataSource(item);
		
		
		IngresoCobroVO ing = new IngresoCobroVO();
		ing = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(ing.getFechaMod());
		
		/*Inicializamos los combos*/
		this.inicializarComboBancos(ing.getCodBanco());
		this.inicializarComboCuentas(ing.getCodCtaBco());
		this.inicializarComboMoneda(ing.getCodMoneda());
		
		
		//Obtenemos bco
		BancoVO auxBco = new BancoVO();
		if(this.comboBancos.getValue() != null){
			
			auxBco = (BancoVO) this.comboBancos.getValue();
			
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
		/*Seteamos el combo de medio de pago*/
		if(item.getBean().getmPago().equals("0"))
		{
			this.comboMPagos.setValue("Sin Asignar");
			
		}else {
			this.comboMPagos.setValue(item.getBean().getmPago());
		}
		
		
		
		auditoria.setDescription(
			
			"Usuario: " + ing.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operaci�n: " + ing.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		/*Copiamos la variable para la modificacion*/
		this.ingresoCopia = new IngresoCobroVO();
		this.ingresoCopia.copiar(ing);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_BORRAR);
		
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
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (IngresoCobroDetalleVO detVO : this.lstDetalleVO) {
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
		
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
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
			
			this.comboTipo.setReadOnly(false);
			
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
		
//		this.nroDocum.setValue("0");
//		this.nroTrans.setValue("0");
		
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
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
		this.lstDetalleAgregar = new ArrayList<IngresoCobroDetalleVO>();
		this.lstDetalleVO = new ArrayList<IngresoCobroDetalleVO>();
		
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		
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
		
		this.impTotMo.setEnabled(false);
		
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
		
		this.comboBancos.setReadOnly(setear);
		
		this.comboCuentas.setReadOnly(setear);
		
		this.fecDoc.setReadOnly(setear);
		
		this.fecValor.setReadOnly(setear);
		
		this.comboMPagos.setReadOnly(setear);
		
		this.serieDocRef.setReadOnly(setear);
		
		this.nroDocRef.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.impTotMo.setReadOnly(setear);
		this.impTotMo.setEnabled(false);
		
		this.referencia.setReadOnly(setear);
		
		this.codTitular.setReadOnly(false);
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
		//nroDocRef.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
		//impTotMo.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
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
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstFormularios(ArrayList<IngresoCobroDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (IngresoCobroDetalleVO det : this.lstDetalleVO) {
				container.addBean(det);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos los formularios seleccionados
	 */
	public void agregarFormulariosSeleccionados(ArrayList<IngresoCobroDetalleVO> lst)
	{

		IngresoCobroDetalleVO bean = new IngresoCobroDetalleVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, IngresoCobroDetalleVO> hForms = new Hashtable<String, IngresoCobroDetalleVO>();
        
		if(lst.size() > 0)
		{
			

			/*Si esta lo eliminamos y lo volvemos a ingresar, para
			 * que queden los ultimos cambios hechos*/
			/*
			for (FormularioVO formVO : lstForms) {
				
				if(hForms.containsKey(formVO.getCodigo())){
					
					hForms.remove(formVO.getCodigo());
					
				}
								
		        hForms.put(formVO.getCodigo(),formVO);
				
			}
			*/
			
			/*Recorremos hash e isertamos en lista de forms a agregar*/
			/*para no duplicar formularios*/
			for (IngresoCobroDetalleVO det : lst) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new IngresoCobroDetalleVO();
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
	public void actulaizarGrilla(IngresoCobroDetalleVO det)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
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
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un grupoVO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarFormularioLista(IngresoCobroDetalleVO det)
	{
		int i =0;
		boolean salir = false;
		
		IngresoCobroDetalleVO detEnLista;
		
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
	private boolean existeFormularioenLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		IngresoCobroDetalleVO aux;
		
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
	
	private void actualizarGrillaContainer(BeanItemContainer<IngresoCobroDetalleVO> container)
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
      		
      		
      		IngresoCobroDetalleVO aux = obtenerGastoEnLista(formSelecccionado.getNroDocum());
	    	gtoSaldo = saldoOriginalGastos.get(formSelecccionado.getNroDocum());
	    	
  		}
    	  
      }

      @Override
      public void postCommit(FieldGroup.CommitEvent commitEvent) throws     FieldGroup.CommitException {
      	
    	  
    	  IngresoCobroDetalleVO aux2 = obtenerGastoEnLista(formSelecccionado.getNroDocum());
    	  
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
	private IngresoCobroDetalleVO obtenerGastoEnLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		IngresoCobroDetalleVO aux = null;
		
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
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
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
	
	public void inicializarComboCuentas(String cod){
		
		BeanItemContainer<CtaBcoVO> ctaObj = new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
		CtaBcoVO cta = new CtaBcoVO();
		ArrayList<CtaBcoVO> lstctas = new ArrayList<CtaBcoVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			/*Si se selacciona un banco buscamos las cuentas, de lo contrario no*/
			if(this.comboBancos.getValue() != null)
				lstctas = this.controlador.getCtaBcos(permisosAux,((BancoVO) this.comboBancos.getValue()).getCodigo());
			
		} catch (ObteniendoCuentasBcoException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (CtaBcoVO ctav : lstctas) {
			
			ctaObj.addBean(ctav);
			
			if(cod != null){
				if(cod.equals(ctav.getCodigo())){
					ctav = ctav;
				}
			}
		}
		
		this.comboCuentas.setContainerDataSource(ctaObj);
		this.comboCuentas.setItemCaptionPropertyId("nombre");
		
		
		if(cod!=null)
		{
			try{
				this.comboCuentas.setReadOnly(false);
				this.comboCuentas.setValue(cta);
				this.comboCuentas.setReadOnly(true);
			}catch(Exception e)
			{}
		}
		
		if(this.operacion.equals(Variables.OPERACION_EDITAR) || this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			this.comboCuentas.setReadOnly(false);
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
							VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			lstBcos = this.controlador.getBcos(permisosAux);
			
		} catch ( InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoBancosException | ObteniendoCuentasBcoException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (BancoVO bco : lstBcos) {
			
			bcoObj.addBean(bco);
			
			if(cod != null){
				if(cod.equals(bco.getCodigo())){
					bcoVO = bco;
					bcoVO.setCodEmp("0");
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
			titularVO = (TitularVO) datos;
			this.codTitular.setValue(String.valueOf(titularVO.getCodigo()));
			this.nomTitular.setValue(titularVO.getNombre());
			this.tipo.setValue(titularVO.getTipo());
		}
		
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
						VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
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
		
		for (IngresoCobroDetalleVO det : lstDetalleVO) {

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
		this.impTotMo.setConvertedValue(impMo);
	}
	
	
	

	
	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstDetalle(ArrayList<IngresoCobroDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (IngresoCobroDetalleVO det : this.lstDetalleVO) {
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
		
		IngresoCobroDetalleVO g;
			
		g = new IngresoCobroDetalleVO();
		g.copiar((DocumDetalleVO)gasto);
		
		/*Seteamos el nro de linea para poder identificarlo 
		 * si se modifica  */
		g.setLinea(lstDetalleVO.size() + 1);
		
		
		this.lstDetalleVO.add(g);
			
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
		IngresoCobroDetalleVO g;
		g = new IngresoCobroDetalleVO();
		g.copiar((DocumDetalleVO)gastoVO);
		g.setLinea(lineaSeleccionada);
		
		int i =0;
		boolean salir = false;
		
		IngresoCobroDetalleVO gastoEnLista;
		
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
		
		IngresoCobroDetalleVO aux;
		
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
						VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
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
