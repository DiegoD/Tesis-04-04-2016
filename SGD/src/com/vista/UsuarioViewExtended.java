package com.vista;

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
import com.vaadin.data.validator.StringLengthValidator;
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
	private ArrayList<GrupoVO> lstGruposAgregar; /*Lista de Formularios a agregar*/
	private UsuariosPanelExtend mainView;
	
	BeanItemContainer<GrupoVO> container;
	GrupoControlador controladorGrupo;
	BeanItemContainer<GrupoVO> containerGrupo;
	
	public UsuarioViewExtended(String opera, UsuariosPanelExtend main)
	{
		
		operacion = opera;
		this.mainView = main;
		this.lstGruposAgregar = new ArrayList<GrupoVO>();
		
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
			try 
			{
				if(this.fieldsValidos())
				{
					UsuarioVO usuarioVO = new UsuarioVO();
					usuarioVO.setUsuario(usuario.getValue().trim());
					usuarioVO.setActivo(activo.getValue());
					usuarioVO.setNombre(nombre.getValue().trim());
					usuarioVO.setPass(pass.getValue().trim());
					
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
						this.controlador.insertarUsuario(usuarioVO);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se ha guardado el Usuario");
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))
					{
										
						this.controlador.modificarUsuario(usuarioVO);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se guardaron los cambios");
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
				
			} 
			catch (InsertandoUsuarioException| ConexionException| ExisteUsuarioException| InicializandoException e) {
			
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
				//this.codGrupo.setReadOnly(true);
				/*Inicializamos el Form en modo Edicion*/
				
				this.iniFormEditar();
	
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnAgregar.addClickListener(click -> {
			try 
			{
				UsuarioViewAgregarGrupoExtend form = new UsuarioViewAgregarGrupoExtend(this);
				MySub sub = new MySub();
				sub.setVista(form);
				sub.setWidth("50%");
				sub.setHeight("50%");
				sub.center();
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
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
					
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
		//setea operación
		operacion = Variables.OPERACION_EDITAR;
		
		/*Oculatamos Editar y mostramos el de guardar*/
		this.enableBotonAceptar();
		this.disableBotonEditar();
		this.enableBotonAgregar();
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.setearFieldsEditar();
		
		/*Seteamos las validaciones*/
		this.setearValidaciones(true);
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
		this.disableBotonAgregar();
		
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
	
	private void disableBotonAgregar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
	}
	
	private void enableBotonAgregar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
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
		grillaGrupos.removeColumn("activo");
		grillaGrupos.removeColumn("usuarioMod");
		grillaGrupos.removeColumn("fechaMod");
		grillaGrupos.removeColumn("operacion");
		grillaGrupos.removeColumn("lstFormularios");

	}

}
