package com.valueObject;

public class MonedaVO {

	private int codMoneda;
	private String nomMoneda;
	private String simboloMoneda;
	
	@Override
	public String toString() { 
	    return this.nomMoneda;
	} 
	
	public int getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(int codMoneda) {
		this.codMoneda = codMoneda;
	}
	public String getNomMoneda() {
		return nomMoneda;
	}
	public void setNomMoneda(String nomMoneda) {
		this.nomMoneda = nomMoneda;
	}
	public String getSimboloMoneda() {
		return simboloMoneda;
	}
	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}
	
}
