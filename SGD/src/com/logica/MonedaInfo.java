package com.logica;

import com.valueObject.MonedaInfoVO;

public class MonedaInfo {
	
	private String codMoneda;
	private String descripcion;
	private String simbolo;
	
	public MonedaInfo(){}
	
	public MonedaInfo(MonedaInfoVO monedaInfoVO){
		
		this.codMoneda = monedaInfoVO.getCod_moneda();
		this.descripcion = monedaInfoVO.getDescripcion();
		this.simbolo = monedaInfoVO.getSimbolo();
		
	}
	
	public MonedaInfo(String codMoneda, String descripcion, String simbolo){
		
		this.codMoneda = codMoneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		
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

}
