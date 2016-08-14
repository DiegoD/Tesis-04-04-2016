package com.valueObject;


/**
 * Clase utilizada para enviar por parametro, para obtener 
 * los permisos de un usuario para una empresa de un formulario y operacion
 * a realizar
 */
public class UsuarioPermisosVO {
	
	String codEmp;
	String formulario;
	String usuario;
	String operacion;
	
	public UsuarioPermisosVO(){}
	
	public UsuarioPermisosVO(String emp, String usu, String form, String op){
		
		this.codEmp = emp;
		this.formulario = form;
		this.usuario = usu;
		this.operacion = op;
	}
	
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	public String getFormulario() {
		return formulario;
	}
	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	

}
