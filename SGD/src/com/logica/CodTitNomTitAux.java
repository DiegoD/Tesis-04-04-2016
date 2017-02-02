package com.logica;

import com.valueObject.CodTitNomTitAuxVO;

public class CodTitNomTitAux {

	private String codTit;
	private String nomTit;
	
	public CodTitNomTitAux(){}
	
	public CodTitNomTitAux(String cod, String nom){
		
		this.codTit = cod;
		this.nomTit = nom;
	}

	public String getCodTit() {
		return codTit;
	}

	public void setCodTit(String codTit) {
		this.codTit = codTit;
	}

	public String getNomTit() {
		return nomTit;
	}

	public void setNomTit(String nomTit) {
		this.nomTit = nomTit;
	}
	
	public CodTitNomTitAuxVO retornarVO(){
		
		CodTitNomTitAuxVO aux = new CodTitNomTitAuxVO();
		
		aux.setCodTit(this.codTit);
		aux.setNomTit(this.nomTit);
		
		return aux;
		
	}
	
}
