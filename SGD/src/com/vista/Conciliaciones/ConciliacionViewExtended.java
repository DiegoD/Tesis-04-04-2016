package com.vista.Conciliaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.controladores.ConciliacionControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.MonedaInfo;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Conciliaciones.ConciliacionDetalleVO;
import com.valueObject.Conciliaciones.ConciliacionVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.vista.IMensaje;
import com.vista.MensajeExtended;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Validaciones.Validaciones;

public class ConciliacionViewExtended extends ConciliacionView implements IMensaje{

private static final long serialVersionUID = 1L;
	
	private ConciliacionControlador controlador;
	private PermisosUsuario permisos;
	private ConciliacionesPanelExtended mainView;
	private String operacion;
	private BeanItemContainer<ConciliacionDetalleVO> container;
	private ArrayList<ConciliacionDetalleVO> lstDetalle; /*Lista con los cheques*/
	Boolean existe = false;
	private BeanFieldGroup<ConciliacionVO> fieldGroup;
	private Double tcMov;
	UsuarioPermisosVO permisoAux;
	Validaciones val = new Validaciones();
	CotizacionVO cotizacion =  new CotizacionVO();
	MySub sub;
	Double importeTotalCalculado;
	NumeradoresVO codigos;
	MonedaVO moneda;
	double saldoConciliadoMoneda = 0;
	
	public ConciliacionViewExtended(String opera, ConciliacionesPanelExtended main){
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		operacion = opera;
		this.mainView = main;
		this.inicializarForm();
		
		
		
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
	
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
				
		comboMoneda.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(comboMoneda.getValue() != null){
					
					try {
						inicializarGrilla();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException
							| ConexionException | InicializandoException | ObteniendoPermisosException
							| NoTienePermisosException | ObteniendoConciliacionException | ObteniendoCuentasBcoException
							| ObteniendoBancosException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}		
			}
	    });
		
//		gridDetalle.addEventListener(AdvancedDataGridEvent.ITEM_FOCUS_IN, clickedRow);
//		
//		console.log("Selected: " + gridDetalle.addSelectionListener(listener);.selection.selected());
		
		gridDetalle.addSelectionListener(selection -> { // Java 8
			
			Collection<Object> col= gridDetalle.getSelectedRows();
			
			ConciliacionDetalleVO aux;
			
			if(this.operacion.equals(Variables.OPERACION_NUEVO)){
				importeTotalCalculado = (double) 0;
				for (Object object : col) {
					
					aux = (ConciliacionDetalleVO)object;
					importeTotalCalculado = importeTotalCalculado + aux.getImpTotMo();
				}
			}
			this.impTotMo.setConvertedValue(importeTotalCalculado);
		});
		
		comboCajaBanco.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(comboCajaBanco.getValue() != null){
					if(comboCajaBanco.getValue().equals("Caja")){
						horizontalBanco.setVisible(false);
						horizontalMoneda.setVisible(true);
						container.removeAllItems();
					}
					else{
						horizontalMoneda.setVisible(false);
						horizontalBanco.setVisible(true);
						container.removeAllItems();
					}
				}		
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
	   			permisoAux = 
	   					new UsuarioPermisosVO(permisos.getCodEmp(),
	   							permisos.getUsuario(),
	   							VariablesPermisos.FORMULARIO_CONCILIACION,
	   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
	   			
	   			CtaBcoVO ctaBcoAux;
	   			ctaBcoAux = new CtaBcoVO();
	   			if(comboCuentas.getValue() != null){
	   				ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
	   			
		   			MonedaVO auxMoneda = new MonedaVO();
		   			auxMoneda = (MonedaVO) ctaBcoAux.getMonedaVO();
		   			
					
		   			if(ctaBcoAux != null && !comboCuentas.getValue().equals("")){
		   				monedaBanco.setValue(ctaBcoAux.getMonedaVO().getSimbolo());
		   				cuentaBanco.setValue(ctaBcoAux.getCodigo());
		   				
		   				try {
							inicializarGrilla();
							
						} 
		   				catch (InstantiationException | IllegalAccessException | ClassNotFoundException
								| IOException | ConexionException | InicializandoException
								| ObteniendoPermisosException | NoTienePermisosException | ObteniendoConciliacionException
								| ObteniendoCuentasBcoException | ObteniendoBancosException e) {
							// TODO Auto-generated catch block
							Mensajes.mostrarMensajeError("Error inesperado");
						}
		   			}
	   			}
			}
	    });
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
		
		this.btnEliminar.addClickListener(click -> {

			permisoAux = 
   					new UsuarioPermisosVO(permisos.getCodEmp(),
   							permisos.getUsuario(),
   							VariablesPermisos.FORMULARIO_CONCILIACION,
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
			
			MensajeExtended form = new MensajeExtended("Elimina la conciliación?",this);
			
			this.operacion = Variables.OPERACION_ELIMINAR;
			
			
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
		
		
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
			
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
		
		this.conciliar.addClickListener(click -> {
			
			int codigo;
			
    		try {
			
			
				this.setearValidaciones(true);
    			/*Validamos los campos antes de invocar al controlador*/
    			if(this.fieldsValidos())
    			{
	    		
    				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
    					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
    					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
    					return;
    				}
    				/*Inicializamos VO de permisos para el usuario, formulario y operacion
    	   			 * para confirmar los permisos del usuario*/
    	   			permisoAux = 
    	   					new UsuarioPermisosVO(permisos.getCodEmp(),
    	   							permisos.getUsuario(),
    	   							VariablesPermisos.FORMULARIO_DEPOSITO,
    	   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
    	   			
					/*ACA*/
					ArrayList<ConciliacionDetalleVO> lstSeleccionados = new ArrayList<ConciliacionDetalleVO>();
					
					
					/*Obtenemos los formularios seleccionados y se los pasamos a
					 * la View de Grupos para agregarlos*/
					Collection<Object> col= gridDetalle.getSelectedRows();
					
					ConciliacionDetalleVO aux;
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						importeTotalCalculado = (double) 0;
						for (Object object : col) {
							
							aux = (ConciliacionDetalleVO)object;
							importeTotalCalculado = importeTotalCalculado + aux.getImpTotMo();
							lstSeleccionados.add(aux);
						}
					}
					
					if(lstSeleccionados.size() > 0 || this.operacion.equals(Variables.OPERACION_EDITAR)){
						
						ConciliacionVO conciliacion = new ConciliacionVO();
						
						conciliacion.setCodDocum("Conc");
						conciliacion.setSerieDocum("0");
						conciliacion.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
						conciliacion.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
						
						conciliacion.setTipo(comboCajaBanco.getValue().toString());
						
						if(comboCajaBanco.getValue().equals("Banco")){
							BancoVO banco = new BancoVO();
							banco = (BancoVO) comboBancos.getValue();
							conciliacion.setNomBanco(banco.getNombre());
							conciliacion.setCodBanco(banco.getCodigo());
							
							CtaBcoVO cuentaBanco = new CtaBcoVO();
							cuentaBanco = (CtaBcoVO) comboCuentas.getValue();
							conciliacion.setCodCuenta(cuentaBanco.getCodigo());
							conciliacion.setNomCuenta(cuentaBanco.getNombre());
							conciliacion.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
							
							moneda = new MonedaVO();
							moneda.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
							moneda.setNacional(cuentaBanco.getMonedaVO().isNacional());
							moneda.setDescripcion(cuentaBanco.getMonedaVO().getDescripcion());
							moneda.setSimbolo(cuentaBanco.getMonedaVO().getSimbolo());
							conciliacion.setCodMoneda(moneda.getCodMoneda());
							conciliacion.setDescripcion(moneda.getDescripcion());
						}
						
						else{
							moneda = new MonedaVO();
							moneda = (MonedaVO) comboMoneda.getValue();
							conciliacion.setCodMoneda(moneda.getCodMoneda());
							conciliacion.setDescripcion(moneda.getDescripcion());
						}
						
						
						conciliacion.setImpTotMo((Double)impTotMo.getConvertedValue());
						
						conciliacion.setUsuarioMod(this.permisos.getUsuario());
						conciliacion.setOperacion(this.operacion);
						
						int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
						
						if(comp != 0){
							Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
							return;
						}
						
						
						conciliacion.setObservaciones(observaciones.getValue());
						
						if(this.operacion.equals(Variables.OPERACION_NUEVO)){
							conciliacion.setLstDetalle(lstSeleccionados);
						}
						else{
							conciliacion.setLstDetalle(lstDetalle);
						}
						
						
						conciliacion.setUsuarioMod(this.permisos.getUsuario());
						conciliacion.setOperacion(this.operacion);
						
						if(this.operacion.equals(Variables.OPERACION_NUEVO)){
							codigos = controlador.insertarConciliacion(permisoAux, conciliacion);
							conciliacion.setNroTrans(codigos.getNumeroTrans());
							conciliacion.setNroDocum(String.valueOf(codigos.getCodigo()));
							this.mainView.actulaizarGrilla(conciliacion, Variables.OPERACION_NUEVO);
    						main.cerrarVentana();
						}
						
						
						else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
							
							conciliacion.setNroTrans((long)this.nroTrans.getConvertedValue());
							conciliacion.setNroDocum(this.nroDocum.getValue());
							
							controlador.modificarConciliacion(conciliacion, permisoAux);
							this.mainView.actulaizarGrilla(conciliacion, Variables.OPERACION_EDITAR);
							main.cerrarVentana();
						}
					}
					else{
						Mensajes.mostrarMensajeError("Debe seleccionar movimientos a conciliar");
					}
					
    			}
    			else /*Si los campos no son válidos mostramos warning*/
    			{
    				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
    			}
			} 
    		catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}	
		    
			
		});
		
		

	} 
	
	public  void inicializarForm(){
		
		this.controlador = new ConciliacionControlador();
		
		this.fieldGroup =  new BeanFieldGroup<ConciliacionVO>(ConciliacionVO.class);
		
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null, "");
		this.inicializarComboMoneda(null);
		
		this.monedaBanco.setEnabled(false);
		this.cuentaBanco.setEnabled(false);
		this.importeConciliado.setEnabled(false);
		this.impTotMo.setEnabled(false);
		
		inicializarCampos();
		
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		this.monedaBanco.setEnabled(false);
		this.cuentaBanco.setEnabled(false);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
			//this.filtroGrilla();
		} 
	
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ConciliacionVO> item)
	{
		
		this.fieldGroup.setItemDataSource(item);
		
		ConciliacionVO ing = new ConciliacionVO();
		ing = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(ing.getFechaMod());
		
		this.importeTotalCalculado = ing.getImpTotMo();
		
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		this.inicializarComboBancos(ing.getCodBanco());
		this.inicializarComboCuentas(ing.getCodCuenta(), "CuentaBanco");
		this.inicializarComboCajaBanco(ing.getTipo());
		this.inicializarComboMoneda(ing.getCodMoneda());
		
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
	}
	
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_BORRAR);
		
		gridDetalle.setSelectionMode(SelectionMode.NONE);
		this.importeConciliado.setVisible(false);
		this.lblConciliado.setVisible(false);
		this.horizontalImportes.setCaption("Importe");
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.btnEditar.setVisible(true);
			this.conciliar.setVisible(false);
			//this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.btnEditar.setVisible(false);
			this.conciliar.setVisible(false);
			//this.disableBotonLectura();
		}
		
		if(permisoEliminar){
			this.btnEliminar.setVisible(true);
		}
		
		this.comboBancos.setEnabled(false);
		this.comboCuentas.setEnabled(false);
		this.nroDocum.setEnabled(false);
		this.impTotMo.setEnabled(false);
		this.fecDoc.setEnabled(false);
		this.fecValor.setEnabled(false);
		this.observaciones.setEnabled(false);
		this.nroDocum.setVisible(true);
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ConciliacionDetalleVO>(ConciliacionDetalleVO.class);
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		//this.readOnlyFields(true);
		
		if(this.lstDetalle != null)
		{
			for (ConciliacionDetalleVO detVO : this.lstDetalle) {
				container.addBean(detVO);
			}
		}
		
		//this.actualizarGrillaContainer(container);
		
						
	}
	
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			this.btnEditar.setVisible(false);
			this.conciliar.setVisible(true);
			this.btnEliminar.setVisible(false);
			this.conciliar.setCaption("Guardar");
			
			this.nroDocum.setEnabled(false);
			this.fecDoc.setEnabled(true);
			this.observaciones.setEnabled(true);
			
			this.botones.setWidth("187");
		}
		else{
			
			/*Mostramos mensaje Sin permisos para operacion*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	private void iniFormNuevo()
	{
		this.operacion = Variables.OPERACION_NUEVO;
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		this.nroDocum.setVisible(false);
		
		importeTotalCalculado = (double) 0;
		
		this.horizontalBanco.setVisible(false);
		this.horizontalMoneda.setVisible(false);
		
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null, "");
		this.inicializarComboCajaBanco(null);
		this.inicializarCampos();
		this.lstDetalle = new ArrayList<ConciliacionDetalleVO>();
		
		this.container = 
				new BeanItemContainer<ConciliacionDetalleVO>(ConciliacionDetalleVO.class);
		
		this.gridDetalle.setContainerDataSource(container);
		
		actualizarGrillaContainer(container);
		
		this.btnEliminar.setVisible(false);
		this.btnEditar.setVisible(false);
		this.botones.setWidth("187");
		
	}

	private void setearFieldsEditar(){
		
	}
	
	private void disableBotonLectura(){
		
	}
	
	private void disableBotonAceptar()
	{
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		
	}
	
	private void enableBotonEliminar()
	{
		if(operacion != Variables.OPERACION_NUEVO){
		}
		else{
			disableBotonEliminar();
		}
	}
	
	private void disableBotonEliminar()
	{
		
		
	}
	
	private void readOnlyFields(boolean setear){
		
	}
	
	public void setLstFormularios(ArrayList<ConciliacionDetalleVO> lst)
	{
		this.lstDetalle = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ConciliacionDetalleVO>(ConciliacionDetalleVO.class);
		
		
		if(this.lstDetalle != null)
		{
			for (ConciliacionDetalleVO det : this.lstDetalle) {
				container.addBean(det);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
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
							VariablesPermisos.FORMULARIO_CONCILIACION,
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
	
	public void inicializarComboCajaBanco(String cod){
		
		BeanItemContainer<BancoVO> bcoObj = new BeanItemContainer<BancoVO>(BancoVO.class);
		BancoVO bcoVO = new BancoVO();
		ArrayList<BancoVO> lstBcos = new ArrayList<BancoVO>();
		UsuarioPermisosVO permisosAux;
		
		if(cod!=null){
			if(cod.equals("Caja")){
				this.comboCajaBanco.setValue("Caja");
				this.comboCajaBanco.setEnabled(false);
				this.horizontalBanco.setVisible(false);
				this.horizontalMoneda.setVisible(true);
			}
			else{
				this.comboCajaBanco.setValue("Banco");
				this.comboCajaBanco.setEnabled(false);
				this.horizontalBanco.setVisible(true);
				this.horizontalMoneda.setVisible(false);
			}
			
				
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
								VariablesPermisos.FORMULARIO_CONCILIACION,
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
				
				if(llamador.equals("Banco")){
					this.monedaBanco.setValue("");
					this.cuentaBanco.setValue("");
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
					this.comboCuentas.setEnabled(true);
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
	
	
	public void inicializarCampos(){
		
	
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de número");
		
		importeConciliado.setConverter(Double.class);
		importeConciliado.setConversionError("Error en formato de número");
		
		nroTrans.setConverter(Long.class);
		nroTrans.setConversionError("Error en formato de número");
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoConciliacionException, ObteniendoCuentasBcoException, ObteniendoBancosException{
		
		
		permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_CONCILIACION,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(this.comboCajaBanco.getValue()!=null){
			if(this.comboCajaBanco.getValue().equals("Banco")){
				BancoVO bcoAux = null;
				if(comboBancos.getValue() != null){
					bcoAux = new BancoVO();
					bcoAux = (BancoVO) comboBancos.getValue();
				}		
				
				CtaBcoVO ctaBcoAux;
				ctaBcoAux = new CtaBcoVO();
				if(comboCuentas.getValue() != null){
					ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
					//Obtenemos lista de movimientos sin conciliar para el banco/cuenta 
					this.lstDetalle = this.controlador.getMovimientosBanco(permisoAux, ctaBcoAux.getCodBco(), ctaBcoAux.getCodigo());
					this.saldoConciliadoMoneda = this.controlador.getSaldoConciliadoCuentaBAnco(permisoAux, ctaBcoAux.getCodBco(), ctaBcoAux.getCodigo());
					this.importeConciliado.setConvertedValue(saldoConciliadoMoneda);
					
				}
				
				
				
			}
			
			else{
				MonedaVO moneda = null;
				if(comboMoneda.getValue()!= null){
					moneda = new MonedaVO();
					moneda = (MonedaVO) comboMoneda.getValue();
					this.lstDetalle = this.controlador.getMovimientosCaja(permisoAux, moneda.getCodMoneda());
					this.saldoConciliadoMoneda = this.controlador.getSaldoConciliadoMoneda(permisoAux, moneda.getCodMoneda());
					this.importeConciliado.setConvertedValue(saldoConciliadoMoneda);
				}
			}
		}
		
		container.removeAllItems();
		
		for (ConciliacionDetalleVO conciliacionDetalleVO : lstDetalle) {
			container.addBean(conciliacionDetalleVO);
		}
		
		
		gridDetalle.setContainerDataSource(container);
		gridDetalle.setSelectionMode(SelectionMode.MULTI);
		
		
	}
	
	private void filtroGrilla()
	{
		try
		{
			com.vaadin.ui.Grid.HeaderRow filterRow = gridDetalle.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridDetalle.getContainerDataSource()
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
	
	
	private void setearValidaciones(boolean setear){
		
		if(comboCajaBanco.getValue()!=null){
			if(comboCajaBanco.getValue().equals("Banco")){
				this.comboBancos.setRequired(setear);
				this.comboBancos.setRequiredError("Es requerido");
				
				this.comboCuentas.setRequired(setear);
				this.comboCuentas.setRequiredError("Es requerido");
			}
			else{
				this.comboMoneda.setRequired(setear);
				this.comboMoneda.setRequiredError("Es requerido");
			}
		}
		
		
		this.impTotMo.setRequired(setear);
		this.impTotMo.setRequiredError("Es requerido");
		
		this.observaciones.setRequired(setear);
		this.observaciones.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
		this.fecDoc.setRequired(setear);
		this.fecDoc.setRequiredError("Es requerido");
		
	}
	
	
	
	private boolean fieldsValidos()
	{
		boolean valido = false;
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
		try
		{
			if(this.nroDocum.isValid() && this.fecValor.isValid() && this.fecDoc.isValid()
					&& this.nroDocum.isValid() && this.comboBancos.isValid() 
					&& this.comboCuentas.isValid()
					&& this.impTotMo.isValid() && this.observaciones.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	private void agregarFieldsValidaciones()
	{
		
		this.observaciones.addValidator(
				new StringLengthValidator(
						"50 caracteres máximo", 0, 50, true));
		
	}
	
	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	
	private void actualizarGrillaContainer(BeanItemContainer<ConciliacionDetalleVO> container)
	{
		gridDetalle.setContainerDataSource(container);
		
		
		//Modifica el formato de fecha en la grilla 
		gridDetalle.getColumn("fecValor").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
		
		gridDetalle.removeColumn("cod_docum");
		gridDetalle.removeColumn("serie_docum");
		gridDetalle.removeColumn("fecDoc");
		gridDetalle.removeColumn("nroTrans");
		gridDetalle.removeColumn("nroTransDoc");
		gridDetalle.removeColumn("impTotMn");
		gridDetalle.removeColumn("codEmp");
		
		gridDetalle.getColumn("nro_docum").setHeaderCaption("Número");
		gridDetalle.getColumn("fecValor").setHeaderCaption("Fecha");
		gridDetalle.getColumn("impTotMo").setHeaderCaption("Importe");
		gridDetalle.getColumn("descripcion").setHeaderCaption("Descripción");
		
		gridDetalle.setColumnOrder("fecValor", "nro_docum", "impTotMo");
		gridDetalle.getColumn("fecValor").setWidth(120);
		gridDetalle.getColumn("nro_docum").setWidth(100);
		gridDetalle.getColumn("impTotMo").setWidth(100);
		gridDetalle.getColumn("descripcion").setWidth(283);
		
		this.filtroGrilla();
	}
	
	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstDetalle(ArrayList<ConciliacionDetalleVO> lst)
	{
		
		this.lstDetalle = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<ConciliacionDetalleVO>(ConciliacionDetalleVO.class);
		
		
		if(this.lstDetalle != null)
		{
			for (ConciliacionDetalleVO det : this.lstDetalle) {
				container.addBean(det); /*Lo agregamos a la grilla*/
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	
	public void actulaizarGrilla(ConciliacionDetalleVO det)
	{

			
		this.lstDetalle.add(det);
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstDetalle);
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}

	@Override
	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cerrarVentana() {
		// TODO Auto-generated method stub
		UI.getCurrent().removeWindow(sub);
	}

	@Override
	public void eliminarFact() {
	
		try {
			
			
			this.setearValidaciones(true);
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
    		
				if(!val.validaPeriodo(fecValor.getValue(), permisoAux)){
					String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecValor.getValue());
					Mensajes.mostrarMensajeError("El período está cerrado para la fecha " + fecha);
					return;
				}
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
	   			 * para confirmar los permisos del usuario*/
	   			permisoAux = 
	   					new UsuarioPermisosVO(permisos.getCodEmp(),
	   							permisos.getUsuario(),
	   							VariablesPermisos.FORMULARIO_CONCILIACION,
	   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
	   			
				/*ACA*/
				ArrayList<ConciliacionDetalleVO> lstSeleccionados = new ArrayList<ConciliacionDetalleVO>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Grupos para agregarlos*/
				Collection<Object> col= gridDetalle.getSelectedRows();
				
				
				if(lstSeleccionados.size() > 0 || this.operacion.equals(Variables.OPERACION_ELIMINAR)){
					
					ConciliacionVO conciliacion = new ConciliacionVO();
					
					conciliacion.setCodDocum("Conciliacion");
					conciliacion.setSerieDocum("0");
					conciliacion.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
					conciliacion.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
					
					conciliacion.setNroDocum(nroDocum.getValue());
					
					BancoVO banco = new BancoVO();
					banco = (BancoVO) comboBancos.getValue();
					conciliacion.setNomBanco(banco.getNombre());
					conciliacion.setCodBanco(banco.getCodigo());
					
					CtaBcoVO cuentaBanco = new CtaBcoVO();
					cuentaBanco = (CtaBcoVO) comboCuentas.getValue();
					conciliacion.setCodCuenta(cuentaBanco.getCodigo());
					conciliacion.setNomCuenta(cuentaBanco.getNombre());
					conciliacion.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
					
					MonedaInfo moneda = new MonedaInfo();
					moneda.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
					moneda.setNacional(cuentaBanco.getMonedaVO().isNacional());
					moneda.setDescripcion(cuentaBanco.getMonedaVO().getDescripcion());
					moneda.setSimbolo(cuentaBanco.getMonedaVO().getSimbolo());
					conciliacion.setDescripcion(moneda.getDescripcion());
					conciliacion.setCodMoneda(moneda.getDescripcion());
					
					conciliacion.setImpTotMo((Double)impTotMo.getConvertedValue());
					
					if(moneda.isNacional()) /*Si la moneda seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						conciliacion.setImpTotMn(conciliacion.getImpTotMo());
						
					}else
					{
						Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
						CotizacionVO coti = null;
						coti = this.controlador.getCotizacion(permisoAux, fecha, moneda.getCodMoneda());
						if(coti.getCotizacionVenta() != 0){
							tcMov = coti.getCotizacionVenta();
							conciliacion.setImpTotMn((conciliacion.getImpTotMo()*tcMov));
						}
						else{
							Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
							return;
						}
						
					}
					
					conciliacion.setObservaciones(observaciones.getValue());
					
					if(this.operacion.equals(Variables.OPERACION_ELIMINAR)){
						conciliacion.setLstDetalle(lstDetalle);
					}
					
					
					conciliacion.setUsuarioMod(this.permisos.getUsuario());
					conciliacion.setOperacion(this.operacion);
					
					if(this.operacion.equals(Variables.OPERACION_ELIMINAR)){
						conciliacion.setNroTrans((long)this.nroTrans.getConvertedValue());
						controlador.eliminarConciliacion(conciliacion, permisoAux);
						this.mainView.actulaizarGrilla(conciliacion, Variables.OPERACION_ELIMINAR);
						UI.getCurrent().removeWindow(sub);
						mainView.cerrarVentana();
					}
					
					
				}
				else{
					Mensajes.mostrarMensajeError("Debe seleccionar cheques a depositar");
				}
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
		} 
		catch (Exception e) {
			Mensajes.mostrarMensajeError(e.getMessage());
		}	
		
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
}
