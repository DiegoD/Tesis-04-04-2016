package com.vista;

import com.controladores.GrupoControlador;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.valueObject.CotizacionVO;
import com.valueObject.GrupoVO;



public class GrupoViewExtended extends GrupoView {

	private BeanFieldGroup<GrupoVO> fieldGroup;
	private GrupoControlador controlador;
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	public GrupoViewExtended(){
	
	this.inicializarForm();
	
	//Inicializamos listener de boton aceptar
	this.aceptar.addClickListener(click -> {
			
			try {
				
				
				
				
				
			} catch (Exception e) {
				
				
			}
		});
	}

	private void inicializarForm(){
		
		this.controlador = new GrupoControlador();
		
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
