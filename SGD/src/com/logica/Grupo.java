package com.logica;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class Grupo extends Auditoria {
	
	private String codGrupo;
	private String nomGrupo;

	
	public Grupo(JSONObject obj){
		
		super(((String)obj.get("usuarioMod")),((Timestamp)obj.get("fechaMod")), ((String)obj.get("operacion")));
		
		this.codGrupo = (String) obj.get("codGrupo");
		this.nomGrupo = (String) obj.get("nomGrupo");
		
		
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



}