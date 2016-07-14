package com.logica;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Usuario extends Auditoria{
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;
	private ArrayList<GruposUsuario> lstGrupos;
	
	public Usuario(){
		this.lstGrupos = new ArrayList<GruposUsuario>();
	}
	public Usuario(String usuario, String pass, String nombre, Boolean activo) {
		
		this.usuario = usuario;
		this.pass = pass;
		this.nombre = nombre;
		this.activo = activo;
		this.lstGrupos = new ArrayList<GruposUsuario>();
	}
	

	public Usuario(JSONObject jsonUsuario)
	{
		super(((String)jsonUsuario.get("usuarioMod")),((Timestamp)jsonUsuario.get("fechaMod")), ((String)jsonUsuario.get("operacion")));
		this.usuario = jsonUsuario.get("usuario").toString();
		this.pass = jsonUsuario.get("pass").toString();
		this.nombre = jsonUsuario.get("nombre").toString();
		this.activo = (Boolean) jsonUsuario.get("activo");
		this.lstGrupos = new ArrayList<GruposUsuario>();
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
	
	public ArrayList<GruposUsuario> getLstGrupos() {
		return lstGrupos;
	}
	public void setLstGrupos(ArrayList<GruposUsuario> lstGrupos) {
		this.lstGrupos = lstGrupos;
	}

}
