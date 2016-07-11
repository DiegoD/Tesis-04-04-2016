package com.logica;

public class Usuario {
	
	private String usuario;
	private String pass;
	private String nombre;
	
	public Usuario(){
		
	}
	public Usuario(String usuario, String pass, String nombre) {
		
		this.usuario = usuario;
		this.pass = pass;
		this.nombre = nombre;
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
