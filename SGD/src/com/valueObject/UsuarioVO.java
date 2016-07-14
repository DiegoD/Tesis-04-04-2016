package com.valueObject;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.logica.Grupo;

public class UsuarioVO {
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;
	private ArrayList<GrupoNombreVO> lstGrupos;

	public UsuarioVO(JSONObject obj){
		
		this.usuario = (String) obj.get("usuario");
		this.pass = (String) obj.get("pass");
		this.nombre = (String) obj.get("nombre");
		this.activo = (Boolean) obj.get("activo");
		this.lstGrupos = new ArrayList<GrupoNombreVO>();
		
		JSONArray jLstGrupos = (JSONArray) obj.get("lstGruposUsuario");
		GrupoNombreVO grupoNombre;
		for(int i = 0; i < jLstGrupos.size(); i++)
		{
			JSONObject jGrupoUsuario = (JSONObject) jLstGrupos.get(i);
			grupoNombre = new GrupoNombreVO();
			grupoNombre.setCodGrupo((String) jGrupoUsuario.get("codigo"));
			grupoNombre.setNomGrupo((String) jGrupoUsuario.get("nombre"));
			
			this.lstGrupos.add(grupoNombre);
		}
		
	}
	
	public ArrayList<GrupoNombreVO> getLstGrupos() {
		return lstGrupos;
	}

	public void setLstGrupos(ArrayList<GrupoNombreVO> lstGrupos) {
		this.lstGrupos = lstGrupos;
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
