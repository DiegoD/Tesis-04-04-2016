package com.vista;

import com.controladores.GrupoControlador;
import com.controladores.UsuarioControlador;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;

public class UsuarioViewExtended extends UsuarioView{

	private static final long serialVersionUID = 1L;
	private BeanFieldGroup<UsuarioVO> fieldGroup;
	private UsuarioControlador controlador;

	public UsuarioViewExtended(){
		
		this.inicializarForm();
		
	}
	
	private void inicializarForm(){
		
		this.controlador = new UsuarioControlador();
		
		this.setearValidaciones();
		
		this.fieldGroup =  new BeanFieldGroup<UsuarioVO>(UsuarioVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
	}
	
	private void setearValidaciones(){
		
		this.nombre.setRequired(true);
		this.nombre.setRequiredError("Es requerido");
		
		this.usuario.setRequired(true);
		this.usuario.setRequiredError("Es requerido");
		
		this.pass.setRequired(true);
		this.pass.setRequiredError("Es requerido");
	}
	
	public void setDataSourceFormulario(BeanItem<UsuarioVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
	}
}
