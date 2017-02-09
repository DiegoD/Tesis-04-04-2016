package com.vista.Grupos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.commons.digester.substitution.VariableSubstitutor;
import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.UsuarioVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;



public class GrupoViewExtended extends GrupoView {

	private BeanFieldGroup<GrupoVO> fieldGroup;
	private ArrayList<FormularioVO> lstFormsVO; /*Lista de Formularios del Grupo*/
	private ArrayList<FormularioVO> lstFormsAgregar; /*Lista de Formularios a agregar*/
	private GrupoControlador controlador;
	private String operacion;
	private GruposPanelExtended mainView;
	BeanItemContainer<FormularioVO> container;
	private FormularioVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un formulario, para poder quitarlo de la lista*/
	
	private GrupoFormularioPermisos frmFormPermisos;
	MySub sub;
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings("unchecked")
	public GrupoViewExtended(String opera, GruposPanelExtended main){
	
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	
	this.operacion = opera;
	this.mainView = main;
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstFormsAgregar = new ArrayList<FormularioVO>();
	
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
								VariablesPermisos.FORMULARIO_GRUPO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				
				GrupoVO grupoVO = new GrupoVO();				
				grupoVO.setCodGrupo(codGrupo.getValue().trim());
				grupoVO.setNomGrupo(nomGrupo.getValue().trim());
				
				grupoVO.setOperacion(operacion);
				grupoVO.setActivo(activo.getValue());
				
				grupoVO.setUsuarioMod(this.permisos.getUsuario());
				
				
				/*Si hay algun formulario nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstFormsAgregar.size() > 0)
				{
					for (FormularioVO f : this.lstFormsAgregar) {
						
						/*Si no esta lo agregamos*/
						if(!this.existeFormularioenLista(f.getCodigo()))
							this.lstFormsVO.add(f);
					}
				}
					
				grupoVO.setLstFormularios(this.lstFormsVO);
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
	
					this.controlador.insertarGrupo(grupoVO, permisoAux);
					
					this.mainView.actulaizarGrilla(grupoVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Grupo");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.editarGrupo(grupoVO, permisoAux);
					
					this.mainView.actulaizarGrilla(grupoVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Grupo");
					main.cerrarVentana();
					
				}
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoGrupoException| MemberGrupoException| ExisteGrupoException| InicializandoException| ConexionException | ModificandoGrupoException | ErrorInesperadoException | NoExisteGrupoException| NoTienePermisosException| ObteniendoPermisosException e) {
				
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
		
		/*Inicalizamos listener para boton de Agregar Formulario*/
		this.btnAgregar.addClickListener(click -> {
					
			try {
				
				GrupoViewAgregarFormularioExtended form = new GrupoViewAgregarFormularioExtended(this);
				
				sub = new MySub("400px", "800px" );
				sub.setModal(true);
				sub.setVista(form);
				//sub.setWidth("50%");
				//sub.setHeight("50%");
				sub.center();
				
				String codGrupo;/*Codigo del grupo para obtener los forms del mismo*/
				
				/*Obtenemos los formularios que no estan en el grupo
				 * para mostrarlos en la grilla para seleccionar*/
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					/*Si la operacion es nuevo, ponemos el  codGrupo vacio
					 * asi nos trae todos los grupos disponibles*/
					codGrupo = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					codGrupo = fieldGroup.getItemDataSource().getBean().getCodGrupo();
				}
				
				ArrayList<FormularioVO> lstForms = this.controlador.getFormulariosNoGrupo(codGrupo, this.permisos.getCodEmp());
				form.setGrillaForms(lstForms);
				
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
				if(formSelecccionado != null)
				{

					/*Recorremos los formularios del grupo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstFormsVO.size() && !esta)
					{
						if(lstFormsVO.get(i).getCodigo().equals(formSelecccionado.getCodigo()))
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstFormsVO.remove(lstFormsVO.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstFormsVO);
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
			    			BeanItem<FormularioVO> item = container.getItem(lstFormularios.getSelectedRow());
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
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_EDITAR);
					
					sub = new MySub("350px", "350px" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
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
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_LECTURA);
					
					sub = new MySub("350px", "350px" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
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
		
		this.controlador = new GrupoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<GrupoVO>(GrupoVO.class);
		
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
		
		this.codGrupo.setRequired(setear);
		this.codGrupo.setRequiredError("Es requerido");
		
		this.nomGrupo.setRequired(setear);
		this.nomGrupo.setRequiredError("Es requerido");
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<GrupoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		GrupoVO grupo = new GrupoVO();
		grupo = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(grupo.getFechaMod());
		
		
		auditoria.setDescription(
			
			"Usuario: " + grupo.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + grupo.getOperacion());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
		
		if(this.lstFormsVO != null)
		{
			for (FormularioVO formVO : this.lstFormsVO) {
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
		this.lstFormsAgregar = new ArrayList<FormularioVO>();
		this.lstFormsVO = new ArrayList<FormularioVO>();
		this.activo.setValue(true);
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
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
		this.nomGrupo.setReadOnly(false);
	}
	
	/**
	 * Deshabilitamos el boton editar y permisos
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
	 * Habilitamos el boton editar y permisos
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
		this.btnVerPermisos.setEnabled(true);
		this.btnVerPermisos.setVisible(true);
		
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
		this.codGrupo.setReadOnly(setear);
		this.nomGrupo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codGrupo.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.nomGrupo.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 45, false));
        
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
			if(this.codGrupo.isValid() && this.nomGrupo.isValid())
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
	public void setLstFormularios(ArrayList<FormularioVO> lstForms)
	{
		this.lstFormsVO = lstForms;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
		
		if(this.lstFormsVO != null)
		{
			for (FormularioVO formVO : this.lstFormsVO) {
				container.addBean(formVO);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos los formularios seleccionados
	 */
	public void agregarFormulariosSeleccionados(ArrayList<FormularioVO> lstForms)
	{

        FormularioVO bean = new FormularioVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, FormularioVO> hForms = new Hashtable<String, FormularioVO>();
        
		if(lstForms.size() > 0)
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
			for (FormularioVO formVO : lstForms) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new FormularioVO();
		        bean.setCodFormulario(formVO.getCodigo());
		        bean.setNomFormulario(formVO.getNombre());
		        bean.setNuevoEditar(formVO.isNuevoEditar());
		        bean.setLeer(formVO.isLeer());
		        bean.setBorrar(formVO.isBorrar());
				
		        /*Por ESTO*/
			//	this.lstFormsVO.add(formVO);
		        boolean saco = this.lstFormsAgregar.remove(formVO);
		        this.lstFormsVO.add(formVO);
		        this.lstFormsAgregar.add(formVO);
		        
				
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
	 * desde GrupoViewExtended
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(FormularioVO formularioVO)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeFormularioenLista(formularioVO.getCodigo()))
		{
			this.actualizarFormularioLista(formularioVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstFormsVO.add(formularioVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstFormsVO);
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un grupoVO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarFormularioLista(FormularioVO formularioVO)
	{
		int i =0;
		boolean salir = false;
		
		FormularioVO formEnLista;
		
		while( i < this.lstFormsVO.size() && !salir)
		{
			formEnLista = this.lstFormsVO.get(i);
			if(formularioVO.getCodigo().equals(formEnLista.getCodigo()))
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstFormsVO.get(i).copiar(formularioVO);

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
	private boolean existeFormularioenLista(String codForm)
	{
		int i =0;
		boolean esta = false;
		
		FormularioVO aux;
		
		while( i < this.lstFormsVO.size() && !esta)
		{
			aux = this.lstFormsVO.get(i);
			if(codForm.equals(aux.getCodigo()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void actualizarGrillaContainer(BeanItemContainer<FormularioVO> container)
	{
		lstFormularios.setContainerDataSource(container);
		
		//lstFormularios.getColumn("borrar").setHidable(true);
		
		lstFormularios.getColumn("borrar").setHidden(true);
		lstFormularios.getColumn("leer").setHidden(true);
		lstFormularios.getColumn("nuevoEditar").setHidden(true);
		
		//lstFormularios.removeColumn("borrar");
		//lstFormularios.removeColumn("leer");
		//lstFormularios.removeColumn("nuevoEditar");
	}
}



