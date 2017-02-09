package com.vista.Bancos;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import com.controladores.BancoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.*;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class BancoViewExtended extends BancoView{

	private BeanFieldGroup<BancoVO> fieldBanco;
	private ArrayList<CtaBcoVO> lstCtaBcoVO; /*Lista de cuentas del banco*/
	private ArrayList<CtaBcoVO> lstCtaBcoAgregar; /*Lista de ctas a agregar*/
	private BancoControlador controlador;
	private String operacion;
	private BancosPanelExtended mainView;
	BeanItemContainer<CtaBcoVO> container;
	private CtaBcoVO ctaBcoSelecccionado; /*Variable utilizada cuando se selecciona
	 										  una cuenta, para poder quitarlo de la lista*/
	
	
	private CtaBcoViewExtended frmFCtaBcos;
	MySub sub;
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings({ "unchecked", "serial", "unused" })
	public BancoViewExtended(String opera, BancosPanelExtended main){
	
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	
	this.operacion = opera;
	this.mainView = main;
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstCtaBcoAgregar = new ArrayList<CtaBcoVO>();
	
	this.inicializarForm();

	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
		try {
			
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_BANCOS,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				/*Obtenemos los datos ingresados en el formulario*/
				BancoVO bcoVO =  obtenerDatosBancoFormulario(this.operacion);
				
				
				/*Si hay algun formulario nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstCtaBcoVO.size() > 0)
				{
					for (CtaBcoVO f : this.lstCtaBcoVO) {
						
						f.setCodEmp(bcoVO.getCodEmp());
						f.setFechaMod(bcoVO.getFechaMod());
						f.setOperacion(bcoVO.getOperacion());
						f.setUsuarioMod(bcoVO.getUsuarioMod());
						
						/*Si no esta lo agregamos*/
						if(!this.existeCuentaenLista(f.getCodigo()))
							this.lstCtaBcoVO.add(f);
					}
				}
					
				bcoVO.setLstCtas(this.lstCtaBcoVO);
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
	
					this.controlador.insertarBanco(bcoVO, permisoAux);
					
					this.mainView.actulaizarGrilla(bcoVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Banco");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarBanco(bcoVO, permisoAux);
					
					this.mainView.actulaizarGrilla(bcoVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Banco");
					main.cerrarVentana();
					
				}
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoBancoException| ModificandoCuentaBcoException| ConexionException| ExisteBancoException
					| InicializandoException| ModificandoBancoException| VerificandoBancosException| ObteniendoPermisosException
					| NoTienePermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}
			
		});
	
	

	
	/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
		try {
			
			/*Inicializamos el Form en modo Edicion*/
			this.iniFormEditar();
			
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		/*Inicalizamos listener para boton de Agregar Formulario*/
		this.btnAgregar.addClickListener(click -> {
					
			try {
				
				CtaBcoViewExtended form = new CtaBcoViewExtended(this);
				
				sub = new MySub("350px", "350px");
				sub.setModal(true);
				sub.setVista(form);
				//sub.setWidth("50%");
				//sub.setHeight("50%");
				sub.center();
				
				String codBanco;/*Codigo del banco para obtener los forms del mismo*/
				
				/*Obtenemos los formularios que no estan en el banco
				 * para mostrarlos en la grilla para seleccionar*/
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					/*Si la operacion es nuevo, ponemos el  codBanco vacio
					 * asi nos trae todos los grupos disponibles*/
					codBanco = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codBanco de el fieldGroup*/
					codBanco = fieldBanco.getItemDataSource().getBean().getCodigo();
				}
				
				UI.getCurrent().addWindow(sub);

				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
				if(ctaBcoSelecccionado != null)
				{

					/*Recorremos los formularios del grupo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstCtaBcoVO.size() && !esta)
					{
						if(lstCtaBcoVO.get(i).getCodigo().equals(ctaBcoSelecccionado.getCodigo()))
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstCtaBcoVO.remove(lstCtaBcoVO.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstCtaBcoVO);
						//lstFormularios.setContainerDataSource(container);
						this.actualizarGrillaContainer(container);
						
					}
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para quitar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			/*Inicializamos el listener de la grilla de formularios*/
			lstFormularios.addSelectionListener(new SelectionListener() {
				
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		if(lstFormularios.getSelectedRow() != null){
			    			BeanItem<CtaBcoVO> item = container.getItem(lstFormularios.getSelectedRow());
					    	ctaBcoSelecccionado = item.getBean(); /*Seteamos el formulario
				    	 									seleccionado para poder quitarlo*/
			    		}
			    	}
			    	catch(Exception e){
			    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    	}
			      
			    }
			});
			
			/*Listener boton editar de la grilla de cuentas*/
			this.btnEditarForm.addClickListener(click -> { 
				
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(ctaBcoSelecccionado != null)
				{
					
					this.frmFCtaBcos = new CtaBcoViewExtended(this, ctaBcoSelecccionado, Variables.OPERACION_EDITAR);
					
					sub = new MySub("350px", "350px" );
					sub.setModal(true);
					sub.setVista(this.frmFCtaBcos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para editar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			/*Listener boton permisos*/
			this.btnVerPermisos.addClickListener(click -> {
				
	
			try {
				
				/*Verificamos que haya un formulario seleccionado*/
				if(ctaBcoSelecccionado != null)
				{
					
					this.frmFCtaBcos = new CtaBcoViewExtended(this, ctaBcoSelecccionado, Variables.OPERACION_LECTURA);
					
					sub = new MySub("400px", "350px" );
					sub.setModal(true);
					sub.setVista(this.frmFCtaBcos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			
	///////////////////
	}

	public  void inicializarForm(){
		
		this.controlador = new BancoControlador();
					
		this.fieldBanco =  new BeanFieldGroup<BancoVO>(BancoVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldBanco != null)
			fieldBanco.buildAndBindMemberFields(this);
		
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
		
		
		this.codigo.setRequired(setear);
		this.codigo.setRequiredError("Es requerido");
		
		this.nombre.setRequired(setear);
		this.nombre.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item BancoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<BancoVO> item)
	{
		this.fieldBanco.setItemDataSource(item);
		
		BancoVO bancoVO = new BancoVO();
		bancoVO = fieldBanco.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(bancoVO.getFechaMod());
		
		
		auditoria.setDescription(
		
			"Usuario: " + bancoVO.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + bancoVO.getOperacion());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_BANCOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		/*Seteamos la grilla con las cuentas*/
		this.container = 
				new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
		
		
		if(this.lstCtaBcoVO != null)
		{
			for (CtaBcoVO formVO : this.lstCtaBcoVO) {
				container.addBean(formVO);
			}
		}
		
		
		//lstFormularios.setContainerDataSource(container);
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			this.disableBotonLectura();
			this.enableBotonAgregarQuitar();
			
			/*Deshanilitamos el boton de quitar la cuenta
			 * Solamente se puede quitar cuando es nuevo, cuando 
			 * esta creada solamente se desactiva*/
			this.btnQuitar.setEnabled(false);
			this.btnQuitar.setVisible(false);
			
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
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.enableBotonAgregarQuitar();
		this.lstCtaBcoAgregar = new ArrayList<CtaBcoVO>();
		this.lstCtaBcoVO = new ArrayList<CtaBcoVO>();
		this.activo.setValue(true);
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
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
		this.nombre.setReadOnly(false);
		this.Tel.setReadOnly(false);
		this.direccion.setReadOnly(false);
		this.direccion.setReadOnly(false);
		this.activo.setReadOnly(false);
		this.contacto.setReadOnly(false);
		
		/*Codigo y nombre de documento no los dejamos editar*/
		this.codigo.setReadOnly(true);
		
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
		
		this.btnVerPermisos.setEnabled(false);
		this.btnVerPermisos.setVisible(false);
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
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codigo.setReadOnly(setear); 
		this.contacto.setReadOnly(setear);
		this.nombre.setReadOnly(setear);
		this.Tel.setReadOnly(setear);
		this.direccion.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codigo.addValidator(
                new StringLengthValidator(
                     " 8 caracteres máximo", 1, 8, false));
        
        this.nombre.addValidator(
                new StringLengthValidator(
                     " 45 caracteres máximo", 1, 45, false));
        
        this.Tel.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 0, 20, false));
        
        this.direccion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, false));
        
        this.contacto.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, false));
        
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
			if(this.codigo.isValid() &&
				this.nombre.isValid() &&
				this.Tel.isValid() &&
				this.direccion.isValid() &&
				this.contacto.isValid() 
			){
				valido = true;
			}
			
			
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}

	/**
	 * Seteamos la lista de las cuentas para mostrarlos
	 * en la grilla
	 */
	public void setLstCtas(ArrayList<CtaBcoVO> lstCtas)
	{
		this.lstCtaBcoVO = lstCtas;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
		
		
		if(this.lstCtaBcoVO != null)
		{
			for (CtaBcoVO ctaVO : this.lstCtaBcoVO) {
				container.addBean(ctaVO);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos las cuentas seleccionados
	 */
	@SuppressWarnings("unused")
	public void agregarCtasSeleccionados(CtaBcoVO cta)
	{

        CtaBcoVO bean = new CtaBcoVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, CtaBcoVO> hCtas = new Hashtable<String, CtaBcoVO>();
        
		if(cta != null)
		{
			/*Recorremos hash e isertamos en lista de forms a agregar*/
			/*para no duplicar formularios*/
			
			bean.Copiar(cta);
			
	        boolean saco = this.lstCtaBcoAgregar.remove(cta);
	        //this.lstCtaBcoVO.add(cta);
	        this.lstCtaBcoAgregar.add(cta);
			
			//this.container.addBean(bean); aaa
			
		}
		
		//lstFormularios.setContainerDataSource(container);
		//this.actualizarGrillaContainer(container); aaa
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = lstFormularios.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: lstFormularios.getContainerDataSource()
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
	
	
	/////////////////////////////////////////////////NUEVOOOOOOOOOOOOOO
	
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un formulario y sus permisos
	 * desde CtaBcoViewExtended
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(CtaBcoVO ctaBcoVO)
	{

		/*Si esta la cuenta en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeCuentaenLista(ctaBcoVO.getCodigo()))
		{
			this.actualizarCtaBcoLista(ctaBcoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstCtaBcoVO.add(ctaBcoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstCtaBcoVO);
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un CtaBcoVO de la lista cuando
	 * se hace una acutalizacion de una cuenta
	 *
	 */
	private void actualizarCtaBcoLista(CtaBcoVO ctaBcoVO)
	{
		int i =0;
		boolean salir = false;
		
		CtaBcoVO ctaBcoEnLista;
		
		while( i < this.lstCtaBcoVO.size() && !salir)
		{
			ctaBcoEnLista = this.lstCtaBcoVO.get(i);
			if(ctaBcoVO.getCodigo().equals(ctaBcoEnLista.getCodigo()))
			{
				this.lstCtaBcoVO.get(i).Copiar(ctaBcoVO);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el grupoVO en la lista
	 * de ctas de la vista
	 *
	 */
	private boolean existeCuentaenLista(String codCtaBco)
	{
		int i =0;
		boolean esta = false;
		
		CtaBcoVO aux;
		
		while( i < this.lstCtaBcoVO.size() && !esta)
		{
			aux = this.lstCtaBcoVO.get(i);
			if(codCtaBco.equals(aux.getCodigo()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void actualizarGrillaContainer(BeanItemContainer<CtaBcoVO> container)
	{
		lstFormularios.setContainerDataSource(container);
		
		//lstFormularios.getColumn("borrar").setHidable(true);
		
		lstFormularios.getColumn("operacion").setHidden(true);
		lstFormularios.getColumn("fechaMod").setHidden(true);
		lstFormularios.getColumn("usuarioMod").setHidden(true);
		lstFormularios.getColumn("activo").setHidden(true);
		lstFormularios.getColumn("codBco").setHidden(true);
		lstFormularios.getColumn("codEmp").setHidden(true);
		lstFormularios.getColumn("monedaVO").setHidden(true);
		
		//lstFormularios.removeColumn("borrar");
		//lstFormularios.removeColumn("leer");
		//lstFormularios.removeColumn("nuevoEditar");
	}
	
	
	/**
	 * Nos retorna retorna un BancoVO
	 * nuevo con todos los datos ingresados en el formulario
	 * Le pasamos la operacion por parametro (Nuevo o Editar)
	 */
	private BancoVO obtenerDatosBancoFormulario(String operacion)
	{
		BancoVO banco = new BancoVO();
		String codigo = "";
		
		try
		{
			codigo = this.codigo.getValue().toString().trim();
			
			String nombre = this.nombre.getValue().toString().trim();;
			String tel = this.Tel.getValue().toString().trim();
			String direccion = this.direccion.getValue().toString().trim();
			String contacto = this.contacto.getValue().toString().trim();;
			boolean activo = this.activo.getValue().booleanValue();
			String codEmp = this.permisos.getCodEmp();
			
			
			String usuarioMod = this.permisos.getUsuario(); 
			//String operacion = Variables.OPERACION_NUEVO;
			
			
			banco.setNombre(nombre);
			banco.setTel(tel);
			banco.setDireccion(direccion);
			banco.setActivo(activo);
			banco.setContacto(contacto);
			banco.setCodEmp(codEmp);
			
			banco.setFechaMod(new Timestamp(System.currentTimeMillis()));
			
			banco.setUsuarioMod(usuarioMod);
			banco.setOperacion(operacion);
			
			banco.setCodigo(codigo);
			
			banco.setCodEmp(this.permisos.getCodEmp());
			
		
		}catch(Exception e){
			
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		
		return banco;
	}
	
}



