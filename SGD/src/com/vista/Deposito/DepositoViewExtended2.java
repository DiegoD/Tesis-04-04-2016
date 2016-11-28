package com.vista.Deposito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

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
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.IngresoCobro.IngresoCobroPanelExtended;


public class DepositoViewExtended2 extends DepositoView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DepositoControlador controlador;
	private PermisosUsuario permisos;
	private String operacion;
	private BeanItemContainer<DepositoDetalleVO> container;
	private ArrayList<DepositoVO> lstCheques; /*Lista con los cobros*/
	Boolean existe = false;
	private BeanFieldGroup<DepositoVO> fieldGroup;
	
	public DepositoViewExtended2(String opera, DepositoPanelExtended main){
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		operacion = VariablesPermisos.OPERACION_NUEVO_EDITAR;
		
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
	   			UsuarioPermisosVO permisoAux = 
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
		
		this.depositar.addClickListener(click -> {
			
    		try {
			
    			
	   			
    			this.setearValidaciones(true);
    			/*Validamos los campos antes de invocar al controlador*/
    			if(this.fieldsValidos())
    			{
	    		
    				/*Inicializamos VO de permisos para el usuario, formulario y operacion
    	   			 * para confirmar los permisos del usuario*/
    	   			UsuarioPermisosVO permisoAux = 
    	   					new UsuarioPermisosVO(permisos.getCodEmp(),
    	   							permisos.getUsuario(),
    	   							VariablesPermisos.FORMULARIO_DEPOSITO,
    	   							VariablesPermisos.OPERACION_NUEVO_EDITAR);
    	   			
					/*ACA*/
					ArrayList<DepositoVO> lstSeleccionados = new ArrayList<DepositoVO>();
					
					/*Obtenemos los formularios seleccionados y se los pasamos a
					 * la View de Grupos para agregarlos*/
					Collection<Object> col= gridCheques.getSelectedRows();
					
					DepositoVO aux;
					for (Object object : col) {
						
						aux = (DepositoVO)object;
						lstSeleccionados.add(aux);
					}
					
					controlador.depositarCheques(permisoAux, lstSeleccionados);
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
					
		this.iniFormNuevo();
	
	}
	
	
	private void iniFormNuevo()
	{
		this.operacion = Variables.OPERACION_NUEVO;
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		this.inicializarComboBancos();
		this.inicializarComboCuentas(null, "");
		this.inicializarComboResponsable();
		this.inicializarCampos();
		
		this.container = 
				new BeanItemContainer<DepositoDetalleVO>(DepositoDetalleVO.class);
		this.gridCheques.setContainerDataSource(container);
		this.arreglarGrilla();
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		
//		this.enableBotonAceptar();
//		this.disableBotonLectura();
//		this.abierto.setValue(true);
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		//this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		//this.readOnlyFields(false);
	}

	public void inicializarComboBancos(){
		
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
			
		}
		
		this.comboBancos.setContainerDataSource(bcoObj);
		this.comboBancos.setItemCaptionPropertyId("nombre");
		
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
								VariablesPermisos.FORMULARIO_INGRESO_COBRO,
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
	
	public void inicializarComboResponsable(){
		
		BeanItemContainer<TitularVO> titObj = new BeanItemContainer<TitularVO>(TitularVO.class);
		TitularVO titularVO = new TitularVO();
		ArrayList<TitularVO> lstTitulares = new ArrayList<TitularVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_EGRESO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			lstTitulares = this.controlador.getTitulares(permisosAux);
			
		} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoClientesException | ObteniendoTitularesException  e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (TitularVO tit : lstTitulares) {
			
			titObj.addBean(tit);
			
		}
		
		this.comboResponsable.setContainerDataSource(titObj);
		this.comboResponsable.setItemCaptionPropertyId("nombre");
		
	}
	
	public void inicializarCampos(){
		
	
		importeMo.setConverter(Double.class);
		importeMo.setConversionError("Error en formato de número");
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException{
		
		
//		this.container = 
//				new BeanItemContainer<DepositoVO>(DepositoVO.class);
		
		UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(permisos.getCodEmp(),
							permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
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
		this.lstCheques = this.controlador.getChequesBanco(permisoAux, bcoAux.getCodigo(), ctaBcoAux.getCodigo());
		
		for (DepositoDetalleVO depositoDetalleVO : lstCheques) {
			container.addBean(depositoDetalleVO);
		}
		
		gridCheques.setContainerDataSource(container);
		gridCheques.setSelectionMode(SelectionMode.MULTI);
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
	}
	
	private void filtroGrilla()
	{
		if(!existe){
			try
			{
				existe = true;
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
		
	}
	
	private void arreglarGrilla(){
		
		this.gridCheques.getColumn("codDocum").setHidden(true);
		this.gridCheques.getColumn("serieDocum").setHidden(true);
		this.gridCheques.getColumn("nroDocum").setHidden(true);
		this.gridCheques.getColumn("codigoBanco").setHidden(true);
		this.gridCheques.getColumn("codigoCuentaBanco").setHidden(true);
		this.gridCheques.getColumn("nombreCuentaBanco").setHidden(true);
		this.gridCheques.getColumn("funcionario").setHidden(true);
		this.gridCheques.getColumn("numComprobante").setHidden(true);
		this.gridCheques.getColumn("observaciones").setHidden(true);
	}
	
	private void setearValidaciones(boolean setear){
		
		this.comboResponsable.setRequired(setear);
		this.comboResponsable.setRequiredError("Es requerido");
		
		this.comboBancos.setRequired(setear);
		this.comboBancos.setRequiredError("Es requerido");
		
		this.comboCuentas.setRequired(setear);
		this.comboCuentas.setRequiredError("Es requerido");
		
		this.comprobante.setRequired(setear);
		this.comprobante.setRequiredError("Es requerido");
		
		this.importeMo.setRequired(setear);
		this.importeMo.setRequiredError("Es requerido");
		
		this.observaciones.setRequired(setear);
		this.observaciones.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
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
		
//		try{
//		/*Inicializamos los combos*/
//		this.inicializarComboBancos(ing.getBanco().getCodBanco());
//		this.inicializarComboCuentas(ing.getCodCtaBco(), "CuentaBanco");
//		this.inicializarComboMoneda(ing.getCodMoneda());
		
		
//		//Obtenemos bco
//		BancoVO auxBco = new BancoVO();
//		if(this.comboBancos.getValue() != null){
//			
//			auxBco = (BancoVO) this.comboBancos.getValue();
//			
//		}
//		else{
//			auxBco.setCodigo("0");
//		}
//		
//		/*Seteamos el tipo*/
//		//this.comboTipo = new ComboBox();
//		if(auxBco.getCodigo().equals("0"))
//			this.comboTipo.setValue("Caja");
//		else
//			this.comboTipo.setValue("Banco");
//		
//		this.nroDocum.setReadOnly(true);
//		this.serieDocRef.setReadOnly(false);
//		this.serieDocRef.setValue(item.getBean().getSerieDocRef());
//		//this.comboMPagos = new ComboBox();
//		
//		this.comboMPagos.setImmediate(true);
//		this.comboMPagos.setNullSelectionAllowed(false);
//		this.comboMPagos.setReadOnly(false);
//		this.comboMPagos.addItem("Sin Asignar");
//		this.comboMPagos.addItem("Cheque");
//		this.comboMPagos.addItem("Transferencia");
//		/*Seteamos el combo de medio de pago*/
//		if(item.getBean().getmPago().equals("0"))
//		{
//			this.comboMPagos.setValue("Sin Asignar");
//			
//		}else {
//			this.comboMPagos.setValue(item.getBean().getmPago());
//		}
//		
//		
//		
//		auditoria.setDescription(
//			
//			"Usuario: " + ing.getUsuarioMod() + "<br>" +
//		    "Fecha: " + fecha + "<br>" +
//		    "Operación: " + ing.getOperacion());
//		
//		/*SETEAMOS LA OPERACION EN MODO LECUTA
//		 * ES CUANDO LLAMAMOS ESTE METODO*/
//		if(this.operacion.equals(Variables.OPERACION_LECTURA))
//			this.iniFormLectura();
//		
//		/*Copiamos la variable para la modificacion*/
//		this.ingresoCopia = new IngresoCobroVO();
//		this.ingresoCopia.copiar(ing);
//		
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
//		}
			
		
		
	}
	
	private boolean fieldsValidos()
	{
		boolean valido = false;
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
		try
		{
			if(this.comprobante.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	private void agregarFieldsValidaciones()
	{
		//nroDocRef.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
		//impTotMo.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
        this.comprobante.addValidator(
                new StringLengthValidator(
                     " 4 caracteres máximo", 1, 4, false));
//        
//        this.referencia.addValidator(
//                new StringLengthValidator(
//                        " 45 caracteres máximo", 1, 255, false));
        
	}
}
