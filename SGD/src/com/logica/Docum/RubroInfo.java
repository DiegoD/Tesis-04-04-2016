package com.logica.Docum;

public class RubroInfo {

	private String nomRubro;
	private String codRubro;
	
	public RubroInfo(){}
	
	public RubroInfo(String cod, String nom){
		this.nomRubro = nom;
		this.codRubro = cod;
	}

	public String getNomRubro() {
		return nomRubro;
	}

	public void setNomRubro(String nomRubro) {
		this.nomRubro = nomRubro;
	}

	public String getCodRubro() {
		return codRubro;
	}

	public void setCodRubro(String codRubro) {
		this.codRubro = codRubro;
	}
	
	
}
