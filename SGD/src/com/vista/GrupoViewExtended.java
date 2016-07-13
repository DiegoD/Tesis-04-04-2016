package com.vista;

import java.sql.Date;
import java.util.Calendar;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.valueObject.CotizacionVO;
import com.valueObject.GrupoVO;



public class GrupoViewExtended extends GrupoView {

	private BeanFieldGroup<GrupoVO> fieldGroup;
	private GrupoControlador controlador;
	private String operacion;
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings("unchecked")
	public GrupoViewExtended(String opera){
		
		 Styles styles = Page.getCurrent().getStyles();
		 
		 styles.add(".v-app .v-textarea.text-label { font-size:" + String.valueOf(4) + "px; }");
		
		 styles.add(".v-caption-inline-label{ display: inline-block;}");
		 
		 
		codGrupo.addStyleName("inline-label");
		
	this.operacion = opera;
	
	this.inicializarForm();
	
	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
		try {
			
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
				JSONObject grupoJS = new JSONObject();
				
				grupoJS.put("codGrupo", codGrupo.getValue().trim());
				grupoJS.put("nomGrupo", nomGrupo.getValue().trim());
				grupoJS.put("usuarioMod", getSession().getAttribute("usuario"));
				grupoJS.put("operacion", operacion);
				grupoJS.put("activo", activo.getValue());
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
	
					this.controlador.insertarGrupo(grupoJS);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Grupo");
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.editarGrupo(grupoJS);
					
				}
				
				/*Mensaje de que se han guardado los cambios 
				 * oculatamos el boton de guardar y mostramos el de editar*/
				Mensajes.mostrarMensajeWarning(Variables.OK_INGRESO);
				
				this.enableBotonEditar();
				this.disableBotonAceptar();
			
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoGrupoException| MemberGrupoException| ExisteGrupoException| InicializandoException| ConexionException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
	
	/*Inicalizamos listener para boton de Editar*/
	//Inicializamos listener de boton aceptar
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
	
	}

	public  void inicializarForm(){
		
		this.controlador = new GrupoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<GrupoVO>(GrupoVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*Seteamos las validaciones de los fields*/
		this.agregarFieldsValidaciones();
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
					
		}
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
		/*Habilitamos el boton de editar,
		 * deshabilitamos botn aceptar*/
		this.enableBotonEditar();
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
		
		/*Oculatamos Editar y mostramos el de guardar*/
		this.enableBotonAceptar();
		this.disableBotonEditar();
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.setearFieldsEditar();
		
		/*Seteamos las validaciones*/
		this.setearValidaciones(true);
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		this.enableBotonAceptar();
		this.disableBotonEditar();
		
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
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonEditar()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
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
                        " 255 caracteres máximo", 1, 255, false));
        
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
			if(this.codGrupo.isValid() && this.nomGrupo.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}

	

	
}



