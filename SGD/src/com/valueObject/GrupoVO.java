package com.valueObject;

import org.json.simple.JSONObject;

public class GrupoVO extends AuditoriaVO{
	
	private String codGrupo;
	private String nomGrupo;
	
	public GrupoVO(JSONObject obj){
		
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
