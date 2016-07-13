package com.valueObject;

import org.json.simple.JSONObject;

public class UsuarioVO {
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;


	public UsuarioVO(JSONObject obj){
		
		this.usuario = (String) obj.get("usuario");
		this.pass = (String) obj.get("pass");
		this.nombre = (String) obj.get("nombre");
		this.activo = (Boolean) obj.get("activo");
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
