package com.vista;

import java.sql.Date;
import java.util.Calendar;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
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
	
	//Inicializamos listener de boton aceptar
	this.aceptar.addClickListener(click -> {
			
			try {
				
				JSONObject grupoJS = new JSONObject();
				
				grupoJS.put("codGrupo", codGrupo.getValue().trim());
				grupoJS.put("nomGrupo", nomGrupo.getValue().trim());
				grupoJS.put("usuarioMod", getSession().getAttribute("usuario"));
				grupoJS.put("operacion", operacion);
								
				this.controlador.insertarGrupo(grupoJS);
				
				Mensajes.mostrarMensajeOK("Se ha guardado el Grupo");
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}
		});
	}

	private void inicializarForm(){
		
		this.controlador = new GrupoControlador();
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
			this.aceptar.setEnabled(true);
		
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

}



