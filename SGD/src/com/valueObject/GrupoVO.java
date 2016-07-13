package com.valueObject;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class GrupoVO extends AuditoriaVO{
	
	private String codGrupo;
	private String nomGrupo;
	private boolean activo;

	public GrupoVO(JSONObject obj){
		
		super(((String)obj.get("usuarioMod")),((Timestamp)obj.get("fechaMod")), ((String)obj.get("operacion")));
		
		this.codGrupo = (String) obj.get("codGrupo");
		this.nomGrupo = (String) obj.get("nomGrupo");
		this.activo = (boolean) obj.get("activo");
		
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

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
