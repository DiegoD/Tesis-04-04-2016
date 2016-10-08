package com.logica;

import com.valueObject.MonedaInfoVO;

public class MonedaInfo {
	
	private String codMoneda;
	private String descripcion;
	private String simbolo;
	private boolean nacional;
	
	public MonedaInfo(){}
	
	public MonedaInfo(MonedaInfoVO monedaInfoVO){
		
		this.codMoneda = monedaInfoVO.getCod_moneda();
		this.descripcion = monedaInfoVO.getDescripcion();
		this.simbolo = monedaInfoVO.getSimbolo();
		this.nacional = monedaInfoVO.isNacional();
		
	}
	
	public MonedaInfo(String codMoneda, String descripcion, String simbolo){
		
		this.codMoneda = codMoneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		this.nacional = nacional;
		
	}
	
	public MonedaInfo(String codMoneda, String descripcion, String simbolo, boolean nacional){
		
		this.codMoneda = codMoneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		this.nacional = nacional;
		
	}
	
	public String getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(String cod_moneda) {
		this.codMoneda = cod_moneda;
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
