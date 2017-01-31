package com.valueObject;

public class MonedaInfoVO {
	
	private String cod_moneda;
	private String descripcion;
	private String simbolo;
	private boolean nacional;
	
	public MonedaInfoVO(){}
	
	
	public String getCod_moneda() {
		return cod_moneda;
	}
	public void setCod_moneda(String cod_moneda) {
		this.cod_moneda = cod_moneda;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}
	
	
	
	

}
