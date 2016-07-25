package com.vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.controladores.UsuarioControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
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
import com.valueObject.FormularioVO;
import com.valueObject.GrupoNombreVO;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;

public class UsuarioViewExtended extends UsuarioView{

	private static final long serialVersionUID = 1L;
	private BeanFieldGroup<UsuarioVO> fieldGroup;
	private UsuarioControlador controlador;
	private String operacion;
	private ArrayList<GrupoVO> lstGruposUsuario;
	private ArrayList<GrupoVO> lstGruposAgregar; /*Lista de Grupos a agregar*/
	private UsuariosPanelExtend mainView;
	private GrupoVO grupoSeleccionado; /*Variable utilizada cuando se selecciona
	  									un grupo, para poder quitarlo de la lista*/
	private MySub sub; 
	private BeanFieldGroup<UsuarioVO> fieldGroupAudit;
	
	GrupoControlador controladorGrupo;
	BeanItemContainer<GrupoVO> containerGrupo;
	private PermisosUsuario permisos; /*Permisos del usuario*/
	
	String passOld;
	
	/**
	 * Constructor: recibe operación (nuevo, editar)
	 * También recibe la vista que lo llamó por parametro
	 */
	public UsuarioViewExtended(String opera, UsuariosPanelExtend main)
	{
		/*Inicializamos los permisos para el usuario*/
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		operacion = opera;
		this.mainView = main;
		this.lstGruposAgregar = new ArrayList<GrupoVO>();
		sub = new MySub("70%", "60%" );
		sub.setModal(true);
		sub.center();
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
			try 
			{
				MD5 md5 = new MD5(); /*Para encriptar la contrasena*/
				
				if(this.fieldsValidos())
				{
					UsuarioVO usuarioVO = new UsuarioVO();
					usuarioVO.setUsuario(usuario.getValue().trim());
					usuarioVO.setActivo(activo.getValue());
					usuarioVO.setNombre(nombre.getValue().trim());
					usuarioVO.setPass(md5.getMD5Hash(pass.getValue().trim()));
					usuarioVO.setOperacion(operacion);
					usuarioVO.setActivo(activo.getValue());
					usuarioVO.setUsuarioMod(this.permisos.getUsuario());
					String empresa = this.permisos.getCodEmp();
					
					if(this.lstGruposAgregar.size() > 0)
					{
						for(GrupoVO g: this.lstGruposAgregar)
						{
							this.lstGruposUsuario.add(g);
						}
					}
					
					usuarioVO.setLstGrupos(this.lstGruposUsuario);
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO))	
					{	
						this.controlador.insertarUsuario(usuarioVO, empresa);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se ha guardado el Usuario");
						main.cerrarVentana();
						
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))
					{
						
						if(passOld.trim() == pass.getValue().trim()){
							usuarioVO.setPass(pass.getValue().trim());
						}
						
						this.controlador.modificarUsuario(usuarioVO, empresa);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se guardaron los cambios");
						main.cerrarVentana();
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
				
			} 
			catch(ExisteUsuarioException e){
				Mensajes.mostrarMensajeError(Variables.ERROR_USUARIO_YA_EXISTE);
			}
  			catch (InsertandoUsuarioException| ConexionException| InicializandoException e) {
			
				Mensajes.mostrarMensajeError(e.getMessage());
			
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			
		});
		
		this.btnEditar.addClickListener(click -> {
			
			try
			{
				/*Inicializamos el Form en modo Edicion*/
				passOld = this.pass.getValue().trim();
				this.iniFormEditar();
	
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnCancelar.addClickListener(click ->
		{
			main.cerrarVentana();
		});
		
		this.btnAgregar.addClickListener(click -> {
			try 
			{
				UsuarioViewAgregarGrupoExtend form = new UsuarioViewAgregarGrupoExtend(this);
				
				sub.setModal(true);
				sub.setVista(form);
				sub.center();
				sub.setDraggable(true);
				
				String usuario;
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					usuario = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					usuario = fieldGroup.getItemDataSource().getBean().getUsuario();
				}
				
				ArrayList<GrupoVO> lstGruposNoUsuario = this.controlador.getUsuariosNoGrupo(usuario);
				form.setGrillaGrupos(lstGruposNoUsuario);
				UI.getCurrent().addWindow(sub);
			} 
			catch (Exception e) 
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			
		});
		
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(grupoSeleccionado != null)
				{

					/*Recorremos los formularios del grupo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstGruposUsuario.size() && !esta)
					{
						if(lstGruposUsuario.get(i).getCodGrupo().equals(grupoSeleccionado.getCodGrupo()))
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstGruposUsuario.remove(lstGruposUsuario.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						containerGrupo.removeAllItems();
						containerGrupo.addAll(lstGruposUsuario);
						grillaGrupos.setContainerDataSource(containerGrupo);
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
		
		
		
		grillaGrupos.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		BeanItem<GrupoVO> item = containerGrupo.getItem(grillaGrupos.getSelectedRow());
			    	grupoSeleccionado = item.getBean(); /*Seteamos el formulario
			    	 									seleccionado para poder quitarlo*/

					  
		    	}
		    	catch(Exception e)
		    	{
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});

	}
	
	private void inicializarForm(){
		
		this.controlador = new UsuarioControlador();
		this.controladorGrupo = new GrupoControlador();
		this.fieldGroup =  new BeanFieldGroup<UsuarioVO>(UsuarioVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Chequeamos si tiene permiso de editar*/
			boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
			if(permisoNuevoEditar)
			{
				/*Inicializamos al formulario como nuevo*/
				this.iniFormNuevo();
			}
			else{
			
				mainView.cerrarVentana();
				mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
			
			}
				
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Chequeamos si tiene permiso de editar*/
			boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_LEER);
			
			/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
			if(permisoLectura)
			{
				/*Inicializamos formulario como editar*/
				this.iniFormLectura();
				
			}else{
			
				mainView.cerrarVentana();
				mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
			
			}
					
		}
		
		/*this.container = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		*/
		this.containerGrupo = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
	
		if(this.lstGruposUsuario != null )
		{
			for(GrupoVO grupoVO: this.lstGruposUsuario){
				containerGrupo.addBean(grupoVO);
			}
		}
		
	}
	
	private void setearValidaciones(boolean setear){
		
		this.nombre.setRequired(setear);
		this.nombre.setRequiredError("Es requerido");
		
		this.usuario.setRequired(setear);
		this.usuario.setRequiredError("Es requerido");
		
		this.pass.setRequired(setear);
		this.pass.setRequiredError("Es requerido");
	}
	
	public void setDataSourceFormulario(BeanItem<UsuarioVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		UsuarioVO usu = new UsuarioVO();
		usu = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(usu.getFechaMod());
		
		auditoria.setDescription(
			    "<ul>"+
			    "  <li> Modificado por: " + usu.getUsuarioMod() + "</li>"+
			    "  <li> Fecha: " + fecha + "</li>"+
			    "  <li> Operación: " + usu.getOperacion() + "</li>"+
			    "</ul>");
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
			this.iniFormNuevo();
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		this.enableBotonAceptar();
		this.disableBotonEditar();
		this.lstGruposAgregar = new ArrayList<GrupoVO>();
		this.lstGruposUsuario = new ArrayList<GrupoVO>();
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
		this.activo.setValue(true);	

	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
		
			//setea operación
			operacion = Variables.OPERACION_EDITAR;
			
			/*Oculatamos Editar y mostramos el de guardar*/
			this.enableBotonAceptar();
			this.disableBotonEditar();
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
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonEditar()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.nombre.setReadOnly(setear);
		this.usuario.setReadOnly(setear);
		this.pass.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
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
		this.pass.setReadOnly(false);
		this.activo.setReadOnly(false);
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		
		/*Habilitamos el boton de editar,
		 * deshabilitamos botn aceptar*/
		this.enableBotonEditar();
		this.disableBotonAceptar();
		this.disableBotonAgregarQuitar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
	}
	
	/**
	 * Habilitamos el boton editar
	 *
	 */
	private void enableBotonEditar()
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
	}
	
	private void disableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnQuitar.setEnabled(false);
		this.btnQuitar.setVisible(false);
	}
	
	private void enableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		this.btnQuitar.setEnabled(true);
		this.btnQuitar.setVisible(true);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.nombre.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.usuario.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.pass.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 1, 255, false));
        
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
		
		try
		{
			if(this.nombre.isValid() && this.usuario.isValid() && this.pass.isValid())
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
	public void setLstGruposUsuario(ArrayList<GrupoVO> lstGrupos)
	{
		this.lstGruposUsuario = lstGrupos;
		
		/*Seteamos la grilla con los formularios*/
		this.containerGrupo = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		
		if(this.lstGruposUsuario != null)
		{
			for (GrupoVO grupoVO : this.lstGruposUsuario) {
				containerGrupo.addBean(grupoVO);
			}
		}
		
		
		grillaGrupos.setContainerDataSource(containerGrupo);
		this.filtroGrilla();
		grillaGrupos.removeColumn("activo");
		grillaGrupos.removeColumn("usuarioMod");
		grillaGrupos.removeColumn("fechaMod");
		grillaGrupos.removeColumn("operacion");
		grillaGrupos.removeColumn("lstFormularios");
		
	}
	
	public void agregarGruposSeleccionados(ArrayList<GrupoVO> lstGrupos)
	{

		GrupoVO bean = new GrupoVO();
        
		if(lstGrupos.size() > 0)
		{
			for (GrupoVO grupoVO : lstGrupos) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new GrupoVO();
		        bean.setCodGrupo(grupoVO.getCodGrupo());
				bean.setNomGrupo(grupoVO.getNomGrupo());
		        /*Por ESTO*/
			//	this.lstFormsVO.add(formVO);
				this.lstGruposAgregar.add(grupoVO);
		        this.containerGrupo.addBean(bean);
				
			}
		}
		
		grillaGrupos.setContainerDataSource(containerGrupo);
	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = grillaGrupos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: grillaGrupos.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    // Have an input field to use for filter
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				    // Update filter When the filter input is changed
				    filterField.addTextChangeListener(change -> {
				        // Can't modify filters so need to replace
				    	this.containerGrupo.removeContainerFilters(pid);
		
				        // (Re)create the filter if necessary
				        if (! change.getText().isEmpty())
				        	this.containerGrupo.addContainerFilter(
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
