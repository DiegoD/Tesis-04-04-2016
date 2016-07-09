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
import com.vaadin.server.Page;
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
	
	this.operacion = opera;
	
	this.inicializarForm();
	
	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
			try {
				
				JSONObject grupoJS = new JSONObject();
				
				grupoJS.put("codGrupo", codGrupo.getValue().trim());
				grupoJS.put("nomGrupo", nomGrupo.getValue().trim());
				grupoJS.put("usuarioMod", getSession().getAttribute("usuario"));
				grupoJS.put("operacion", operacion);
								
				this.controlador.insertarGrupo(grupoJS);
				
				Mensajes.mostrarMensajeOK("Se ha guardado el Grupo");
				
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
			
			/*Ocultamos el boton de editar y mostramos el nuevo*/
			this.disableBotonEditar();
			this.enableBotonAceptar();
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
	
	
	
	}

	private void inicializarForm(){
		
		this.controlador = new GrupoControlador();
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Boton de nuevo lo fejamos enable y visible*/
			this.aceptar.setEnabled(true);
			this.aceptar.setVisible(true);
			
			/*En nuevo ocultamos editar*/
			this.btnEditar.setEnabled(false);
			this.btnEditar.setVisible(false);
			
		}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
		{
			/*Deshabilitamos boton aceptar*/
			this.disableBotonAceptar();
			
			/*En nuevo mostramos editar*/
			this.enableBotonEditar();
		}
		
	
		this.setearValidaciones();
		
		this.fieldGroup =  new BeanFieldGroup<GrupoVO>(GrupoVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
	}
	

	/**
	 * Seteamos las validaciones del Formulario
	 *
	 */
	private void setearValidaciones(){
		
		this.codGrupo.setRequired(true);
		this.codGrupo.setRequiredError("Es requerido");
		
		this.nomGrupo.setRequired(true);
		this.nomGrupo.setRequiredError("Es requerido");
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<GrupoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
	}

	private void disableBotonEditar()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	private void enableBotonEditar()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
	}
	
	private void disableBotonAceptar()
	{
		this.aceptar.setEnabled(false);
		this.aceptar.setVisible(false);
	}
	
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	/**
	 * Dejamos todos los Fields readonly
	 *
	 */
	private void readOnlyFields()
	{
		this.codGrupo.setReadOnly(true);
		this.nomGrupo.setReadOnly(true);
	}
	
	/**
	 * Dejamos todos los correspondientes para poder editar
	 *
	 */
	private void editarFields()
	{
		this.nomGrupo.setReadOnly(true);
	}
	
}



