package com.logica;

import com.valueObject.MonedaInfoVO;

public class MonedaInfo {
	
	private String cod_moneda;
	private String descripcion;
	private String simbolo;
	
	public MonedaInfo(){}
	
	public MonedaInfo(MonedaInfoVO monedaInfoVO){
		
		this.cod_moneda = monedaInfoVO.getCod_moneda();
		this.descripcion = monedaInfoVO.getDescripcion();
		this.simbolo = monedaInfoVO.getSimbolo();
		
	}
	
	public MonedaInfo(String codMoneda, String descripcion, String simbolo){
		
		this.cod_moneda = codMoneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		
	}
	
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

}
