package com.logica;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Grupo extends Auditoria {
	
	private String codGrupo;
	private String nomGrupo;
	private boolean activo;

	private ArrayList<Formulario> lstFormularios;

	public Grupo()
	{
		this.lstFormularios = new ArrayList<Formulario>();
	}
	
	public Grupo(JSONObject obj){
		
		super(((String)obj.get("usuarioMod")),((Timestamp)obj.get("fechaMod")), ((String)obj.get("operacion")));
		
		this.codGrupo = (String) obj.get("codGrupo");
		this.nomGrupo = (String) obj.get("nomGrupo");
		this.activo = (boolean) obj.get("activo");
		this.lstFormularios = new ArrayList<Formulario>();
	
	}
	
	public String getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(String codGrupo) {
		this.codGrupo = codGrupo;
	}
	public String getNomGrupo() {
		return nomGrupo;
	}
	public void setNomGrupo(String nomGrupo) {
		this.nomGrupo = nomGrupo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public ArrayList<Formulario> getLstFormularios() {
		return lstFormularios;
	}

	public void setLstFormularios(ArrayList<Formulario> lstFormularios) {
		this.lstFormularios = lstFormularios;
	}

}
