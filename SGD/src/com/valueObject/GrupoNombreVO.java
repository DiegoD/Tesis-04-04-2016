package com.valueObject;


public class GrupoNombreVO {
	
	private String codGrupo;
	private String nomGrupo;
	
	public GrupoNombreVO()
	{
		
	}
	
	public GrupoNombreVO(String codigo, String nombre)
	{
		this.codGrupo = codigo;
		this.nomGrupo = nombre;
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
