package com.vista.Deposito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.controladores.DepositoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.FuncionarioInfo;
import com.logica.MonedaInfo;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;
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


public class DepositoViewExtended extends DepositoView implements IMensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DepositoControlador controlador;
	private PermisosUsuario permisos;
	private DepositoPanelExtended mainView;
	private String operacion;
	private BeanItemContainer<DepositoDetalleVO> container;
	private ArrayList<DepositoDetalleVO> lstCheques; /*Lista con los cheques*/
	Boolean existe = false;
	private BeanFieldGroup<DepositoVO> fieldGroup;
	UsuarioPermisosVO permisoAux;
	Validaciones val = new Validaciones();
	ChequeVO chequeVO = new ChequeVO();
	DepositoVO depositoVO = new DepositoVO();
	CotizacionVO cotizacion =  new CotizacionVO();
	MySub sub;
	Double importeTotalCalculado;
	
	
	public DepositoViewExtended(String opera, DepositoPanelExtended main){
		
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
	   							VariablesPermisos.FORMULARIO_DEPOSITO,
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
								| ObteniendoPermisosException | NoTienePermisosException | ObteniendoChequeException
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
   							VariablesPermisos.FORMULARIO_DEPOSITO,
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
			
			MensajeExtended form = new MensajeExtended("Elimina el depósito?",this);
			
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
		
		this.depositar.addClickListener(click -> {
			
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
					ArrayList<DepositoDetalleVO> lstSeleccionados = new ArrayList<DepositoDetalleVO>();
					
					/*Obtenemos los formularios seleccionados y se los pasamos a
					 * la View de Grupos para agregarlos*/
					Collection<Object> col= gridCheques.getSelectedRows();
					
					DepositoDetalleVO aux;
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						importeTotalCalculado = (double) 0;
						for (Object object : col) {
							
							aux = (DepositoDetalleVO)object;
							importeTotalCalculado = importeTotalCalculado + aux.getImpTotMo();
							lstSeleccionados.add(aux);
						}
					}
					
					if(lstSeleccionados.size() > 0 || this.operacion.equals(Variables.OPERACION_EDITAR)){
						
						DepositoVO deposito = new DepositoVO();
						
						deposito.setCodDocum("Deposito");
						deposito.setSerieDocum("0");
						deposito.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
						deposito.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
						
						deposito.setNroDocum(nroDocum.getValue());
						
						TitularVO titular = new TitularVO();
						titular = (TitularVO) comboResponsable.getValue();
						FuncionarioInfo funcionario = new FuncionarioInfo();
						funcionario.setCodigo(titular.getCodigo());
						funcionario.setNombre(titular.getNombre());
						deposito.setFuncionario(funcionario);
						
						BancoVO banco = new BancoVO();
						banco = (BancoVO) comboBancos.getValue();
						deposito.setNomBanco(banco.getNombre());
						deposito.setCodBanco(banco.getCodigo());
						
						CtaBcoVO cuentaBanco = new CtaBcoVO();
						cuentaBanco = (CtaBcoVO) comboCuentas.getValue();
						deposito.setCodCuenta(cuentaBanco.getCodigo());
						deposito.setNomCuenta(cuentaBanco.getNombre());
						deposito.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
						
						MonedaInfo moneda = new MonedaInfo();
						moneda.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
						moneda.setNacional(cuentaBanco.getMonedaVO().isNacional());
						moneda.setDescripcion(cuentaBanco.getMonedaVO().getDescripcion());
						moneda.setSimbolo(cuentaBanco.getMonedaVO().getSimbolo());
						deposito.setNomMoneda(moneda.getDescripcion());
						//deposito.setMoneda(moneda);
						
						if(val.validaNumDeposito(permisoAux, deposito.getCodBanco(), deposito.getCodCuenta(), deposito.getNroDocum())){
							Mensajes.mostrarMensajeError("Ya existe el número de depósito para el banco/cuenta");
							return;
						}
						deposito.setImpTotMo((Double)impTotMo.getConvertedValue());
						
						int comp = Double.compare(importeTotalCalculado, (Double)impTotMo.getConvertedValue());
						
						if(comp != 0){
							Mensajes.mostrarMensajeError("El importe total es diferente a la suma del detalle");
							return;
						}
						
						if(moneda.isNacional()) /*Si la moneda seleccionada es nacional*/
						{
							/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
							deposito.setTcMov(1);
							deposito.setImpTotMn(deposito.getImpTotMo());
							
						}else
						{
							Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
							CotizacionVO coti = null;
							coti = this.controlador.getCotizacion(permisoAux, fecha, moneda.getCodMoneda());
							if(coti.getCotizacionVenta() != 0){
								deposito.setTcMov(coti.getCotizacionVenta());
								deposito.setImpTotMn((deposito.getImpTotMo()*deposito.getTcMov()));
							}
							else{
								Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
								return;
							}
							
						}
						
						deposito.setObservaciones(observaciones.getValue());
						
						if(this.operacion.equals(Variables.OPERACION_NUEVO)){
							deposito.setLstDetalle(lstSeleccionados);
						}
						else{
							deposito.setLstDetalle(lstCheques);
						}
						
						
						deposito.setUsuarioMod(this.permisos.getUsuario());
						deposito.setOperacion(this.operacion);
						
						if(this.operacion.equals(Variables.OPERACION_NUEVO)){
							codigo = controlador.depositarCheques(permisoAux, deposito);
							deposito.setNroTrans(codigo);
							this.mainView.actulaizarGrilla(deposito, Variables.OPERACION_NUEVO);
							Mensajes.mostrarMensajeOK("Se ha guardado el depósito");
    						main.cerrarVentana();
						}
						
						
						else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
							
							deposito.setNroTrans((long)this.nroTrans.getConvertedValue());
							
							controlador.modificarDeposito(deposito, permisoAux);
							this.mainView.actulaizarGrilla(deposito, Variables.OPERACION_EDITAR);
							Mensajes.mostrarMensajeOK("Se ha modificado el depósito");
							main.cerrarVentana();
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
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}	
		    
			
		});

	} 
	
	public  void inicializarForm(){
		
		this.controlador = new DepositoControlador();
		
		this.fieldGroup =  new BeanFieldGroup<DepositoVO>(DepositoVO.class);
		
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null, "");
		this.inicializarComboResponsable(null);
		
		this.monedaBanco.setEnabled(false);
		this.cuentaBanco.setEnabled(false);
		
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
	public void setDataSourceFormulario(BeanItem<DepositoVO> item)
	{
		
		this.fieldGroup.setItemDataSource(item);
		
		DepositoVO ing = new DepositoVO();
		ing = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(ing.getFechaMod());
		
		this.importeTotalCalculado = ing.getImpTotMo();
		
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		this.inicializarComboBancos(ing.getCodBanco());
		this.inicializarComboCuentas(ing.getCodCuenta(), "CuentaBanco");
		this.inicializarComboResponsable(ing.getFuncionario().getCodigo());
		
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
	}
	
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_BORRAR);
		
		gridCheques.setSelectionMode(SelectionMode.NONE);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.btnEditar.setVisible(true);
			this.depositar.setVisible(false);
			//this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.btnEditar.setVisible(false);
			this.depositar.setVisible(false);
			//this.disableBotonLectura();
		}
		
		if(permisoEliminar){
			this.btnEliminar.setVisible(true);
		}
		
		this.comboResponsable.setEnabled(false);
		this.comboBancos.setEnabled(false);
		this.comboCuentas.setEnabled(false);
		this.nroDocum.setEnabled(false);
		this.impTotMo.setEnabled(false);
		this.fecDoc.setEnabled(false);
		this.fecValor.setEnabled(false);
		this.observaciones.setEnabled(false);
		
//		/*Deshabilitamos botn aceptar*/
//		this.disableBotonAceptar();
//		this.disableBotonAgregarQuitar();
//		
//		if(permisoEliminar)
//			this.enableBotonEliminar();
		
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<DepositoDetalleVO>(DepositoDetalleVO.class);
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		//this.readOnlyFields(true);
		
		if(this.lstCheques != null)
		{
			for (DepositoDetalleVO detVO : this.lstCheques) {
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			this.btnEditar.setVisible(false);
			this.depositar.setVisible(true);
			this.btnEliminar.setVisible(false);
			this.depositar.setCaption("Guardar");
			
			this.comboResponsable.setEnabled(true);
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		importeTotalCalculado = (double) 0;
		
		this.inicializarComboBancos(null);
		this.inicializarComboCuentas(null, "");
		this.inicializarComboResponsable(null);
		this.inicializarCampos();
		this.lstCheques = new ArrayList<DepositoDetalleVO>();
		
		this.container = 
				new BeanItemContainer<DepositoDetalleVO>(DepositoDetalleVO.class);
		
		this.gridCheques.setContainerDataSource(container);
		
		actualizarGrillaContainer(container);
		
		this.btnEliminar.setVisible(false);
		this.btnEditar.setVisible(false);
		this.botones.setWidth("187");
		
//		this.enableBotonAceptar();
//		this.disableBotonLectura();
//		this.abierto.setValue(true);
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		//this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		//this.readOnlyFields(false);
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
	
	public void setLstFormularios(ArrayList<DepositoDetalleVO> lst)
	{
		this.lstCheques = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<DepositoDetalleVO>(DepositoDetalleVO.class);
		
		
		if(this.lstCheques != null)
		{
			for (DepositoDetalleVO det : this.lstCheques) {
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
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
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
								VariablesPermisos.FORMULARIO_DEPOSITO,
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
	
	public void inicializarComboResponsable(Integer cod){
		
		BeanItemContainer<TitularVO> titObj = new BeanItemContainer<TitularVO>(TitularVO.class);
		TitularVO titularVO = new TitularVO();
		ArrayList<TitularVO> lstTitulares = new ArrayList<TitularVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_DEPOSITO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			lstTitulares = this.controlador.getTitulares(permisosAux);
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoClientesException | ObteniendoTitularesException  e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (TitularVO tit : lstTitulares) {
			
			titObj.addBean(tit);
			
			if(cod != null){
				if(cod.equals(tit.getCodigo())){
					titularVO = tit;
				}
			}
		}
		

		this.comboResponsable.setContainerDataSource(titObj);
		this.comboResponsable.setItemCaptionPropertyId("nombre");
		this.comboResponsable.setValue(titularVO);
		
	}
	
	public void inicializarCampos(){
		
	
		impTotMo.setConverter(Double.class);
		impTotMo.setConversionError("Error en formato de número");
		
		nroTrans.setConverter(Long.class);
		nroTrans.setConversionError("Error en formato de número");
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException{
		
		
		permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_DEPOSITO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		BancoVO bcoAux = null;
		if(comboBancos.getValue() != null){
			bcoAux = new BancoVO();
			bcoAux = (BancoVO) comboBancos.getValue();
		}		
		
		CtaBcoVO ctaBcoAux;
		ctaBcoAux = new CtaBcoVO();
		if(comboCuentas.getValue() != null){
			ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
			
		}
		
		//Obtenemos lista de bancos del sistema
		this.lstCheques = this.controlador.getChequesBanco(permisoAux, ctaBcoAux.getMonedaVO().getCodMoneda());
		
		container.removeAllItems();
		
		for (DepositoDetalleVO depositoDetalleVO : lstCheques) {
			container.addBean(depositoDetalleVO);
		}
		
		
		gridCheques.setContainerDataSource(container);
		gridCheques.setSelectionMode(SelectionMode.MULTI);
		
		
	}
	
	private void filtroGrilla()
	{
		try
		{
			com.vaadin.ui.Grid.HeaderRow filterRow = gridCheques.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridCheques.getContainerDataSource()
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
		
		this.comboResponsable.setRequired(setear);
		this.comboResponsable.setRequiredError("Es requerido");
		
		this.comboBancos.setRequired(setear);
		this.comboBancos.setRequiredError("Es requerido");
		
		this.comboCuentas.setRequired(setear);
		this.comboCuentas.setRequiredError("Es requerido");
		
		this.nroDocum.setRequired(setear);
		this.nroDocum.setRequiredError("Es requerido");
		
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
					&& this.comboCuentas.isValid() && this.comboResponsable.isValid()
					&& this.impTotMo.isValid() && this.observaciones.isValid())
				valido = true;
			
			if(!this.tryParseInt(nroDocum.getValue())){
				nroDocum.setComponentError(new UserError("Debe ingresar un número entero"));
				valido = false;
			}
			
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
		
		
		//nroDocRef.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
		//impTotMo.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
//        this.nroDocum.addValidator(
//                new StringLengthValidator(
//                     " 4 caracteres máximo", 1, 10, false));
//        
//        this.referencia.addValidator(
//                new StringLengthValidator(
//                        " 45 caracteres máximo", 1, 255, false));
        
	}
	
	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	
	
	private void actualizarGrillaContainer(BeanItemContainer<DepositoDetalleVO> container)
	{
		gridCheques.setContainerDataSource(container);
		
		gridCheques.removeColumn("fecDoc");
		gridCheques.removeColumn("codDocum");
		//gridCheques.removeColumn("serieDocum");
		//gridCheques.removeColumn("nroDocum");
		gridCheques.removeColumn("codEmp");
		gridCheques.removeColumn("nacional");
		gridCheques.removeColumn("referencia");
		gridCheques.removeColumn("codMoneda");
		gridCheques.removeColumn("nomMoneda");
		gridCheques.removeColumn("simboloMoneda");
		gridCheques.removeColumn("nomTitular");
		gridCheques.removeColumn("codTitular");
		gridCheques.removeColumn("impTotMn");
		gridCheques.removeColumn("tcMov");
		gridCheques.removeColumn("codCuenta");
		gridCheques.removeColumn("nomCuenta");
		gridCheques.removeColumn("codCtaInd");
		gridCheques.removeColumn("codBanco");
		gridCheques.removeColumn("nroTrans");
		gridCheques.removeColumn("tipo");
		gridCheques.removeColumn("linea");
		
		//Modifica el formato de fecha en la grilla 
		gridCheques.getColumn("fecValor").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
				
		
		
		
		gridCheques.getColumn("fecValor").setHeaderCaption("Fecha");
		gridCheques.getColumn("nomBanco").setHeaderCaption("Banco");
		gridCheques.getColumn("impTotMo").setHeaderCaption("Importe");
		gridCheques.getColumn("nroDocum").setHeaderCaption("Cheque");
		gridCheques.getColumn("serieDocum").setHeaderCaption("Serie");
		
		gridCheques.getColumn("fecValor").setWidth(100);
		gridCheques.getColumn("impTotMo").setWidth(100);
		gridCheques.getColumn("nomBanco").setWidth(150);
		gridCheques.getColumn("nroDocum").setWidth(130);
		gridCheques.getColumn("serieDocum").setWidth(100);
		
		this.filtroGrilla();
	}
	
	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstDetalle(ArrayList<DepositoDetalleVO> lst)
	{
		
		this.lstCheques = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<DepositoDetalleVO>(DepositoDetalleVO.class);
		
		
		if(this.lstCheques != null)
		{
			for (DepositoDetalleVO det : this.lstCheques) {
				container.addBean(det); /*Lo agregamos a la grilla*/
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	
	public void actulaizarGrilla(DepositoDetalleVO det)
	{

			
		this.lstCheques.add(det);
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstCheques);
		
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
	   							VariablesPermisos.FORMULARIO_DEPOSITO,
	   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
	   			
				/*ACA*/
				ArrayList<DepositoDetalleVO> lstSeleccionados = new ArrayList<DepositoDetalleVO>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Grupos para agregarlos*/
				Collection<Object> col= gridCheques.getSelectedRows();
				
				DepositoDetalleVO aux;
				for (Object object : col) {
					
					aux = (DepositoDetalleVO)object;
					lstSeleccionados.add(aux);
					
				}
				if(lstSeleccionados.size() > 0 || this.operacion.equals(Variables.OPERACION_ELIMINAR)){
					
					DepositoVO deposito = new DepositoVO();
					
					deposito.setCodDocum("Deposito");
					deposito.setSerieDocum("0");
					deposito.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
					deposito.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
					
					deposito.setNroDocum(nroDocum.getValue());
					
					TitularVO titular = new TitularVO();
					titular = (TitularVO) comboResponsable.getValue();
					FuncionarioInfo funcionario = new FuncionarioInfo();
					funcionario.setCodigo(titular.getCodigo());
					funcionario.setNombre(titular.getNombre());
					deposito.setFuncionario(funcionario);
					
					BancoVO banco = new BancoVO();
					banco = (BancoVO) comboBancos.getValue();
					deposito.setNomBanco(banco.getNombre());
					deposito.setCodBanco(banco.getCodigo());
					
					CtaBcoVO cuentaBanco = new CtaBcoVO();
					cuentaBanco = (CtaBcoVO) comboCuentas.getValue();
					deposito.setCodCuenta(cuentaBanco.getCodigo());
					deposito.setNomCuenta(cuentaBanco.getNombre());
					deposito.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
					
					MonedaInfo moneda = new MonedaInfo();
					moneda.setCodMoneda(cuentaBanco.getMonedaVO().getCodMoneda());
					moneda.setNacional(cuentaBanco.getMonedaVO().isNacional());
					moneda.setDescripcion(cuentaBanco.getMonedaVO().getDescripcion());
					moneda.setSimbolo(cuentaBanco.getMonedaVO().getSimbolo());
					deposito.setNomMoneda(moneda.getDescripcion());
					//deposito.setMoneda(moneda);
					
					deposito.setImpTotMo((Double)impTotMo.getConvertedValue());
					
					if(val.depositoConciliado(permisoAux, deposito)){
						UI.getCurrent().removeWindow(sub);
						Mensajes.mostrarMensajeError("El depósito está conciliado");
						return;
					}
					
					if(moneda.isNacional()) /*Si la moneda seleccionada es nacional*/
					{
						/*Si la moneda es la nacional, el TC es 1 y el importe MN es el mismo*/
						deposito.setTcMov(1);
						deposito.setImpTotMn(deposito.getImpTotMo());
						
					}else
					{
						Date fecha = convertFromJAVADateToSQLDate(fecValor.getValue());
						CotizacionVO coti = null;
						coti = this.controlador.getCotizacion(permisoAux, fecha, moneda.getCodMoneda());
						if(coti.getCotizacionVenta() != 0){
							deposito.setTcMov(coti.getCotizacionVenta());
							deposito.setImpTotMn((deposito.getImpTotMo()*deposito.getTcMov()));
						}
						else{
							Mensajes.mostrarMensajeError("Debe cargar la cotización para la moneda");
							return;
						}
						
					}
					
					deposito.setObservaciones(observaciones.getValue());
					
					
					if(this.operacion.equals(Variables.OPERACION_ELIMINAR)){
						deposito.setLstDetalle(lstCheques);
						
						
					}
					
					
					deposito.setUsuarioMod(this.permisos.getUsuario());
					deposito.setOperacion(this.operacion);
					
					if(this.operacion.equals(Variables.OPERACION_ELIMINAR)){
						deposito.setNroTrans((long)this.nroTrans.getConvertedValue());
						controlador.eliminarDeposito(deposito, permisoAux);
						this.mainView.actulaizarGrilla(deposito, Variables.OPERACION_ELIMINAR);
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
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
		
	
}
