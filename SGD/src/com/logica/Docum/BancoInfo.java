package com.logica.Docum;

public class BancoInfo {

	private String codBanco;
	private String nomBanco;
	
	public BancoInfo(String cod, String nom){
		this.codBanco = cod;
		this.nomBanco = nom;
	}
	
	public BancoInfo(){
	
	}

	public String getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public String getNomBanco() {
		return nomBanco;
	}

	public void setNomBanco(String nomBanco) {
		this.nomBanco = nomBanco;
	}
	
	
}
