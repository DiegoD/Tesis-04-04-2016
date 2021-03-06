package com.valueObject;

public class MonedaVO extends AuditoriaVO{
	
	private String codMoneda;
	private String descripcion;
	private String simbolo;
	private boolean aceptaCotizacion;
	private boolean activo;
	private boolean nacional;
	private double cotizacion;
	
	public MonedaVO(){
		
	}

	/**
	 * Copiamos todos los datos de la MonedaVO pasado
	 * por parametro
	 *
	 */
	public void copiar(MonedaVO monedaVO){
		
		this.setCodMoneda(monedaVO.getCodMoneda());
		this.setDescripcion(monedaVO.getDescripcion());
		this.setAceptaCotizacion(monedaVO.isAceptaCotizacion());
		this.setActivo(monedaVO.isActivo());
		
		this.setUsuarioMod(monedaVO.getUsuarioMod());
		this.setFechaMod(monedaVO.getFechaMod());
		this.setOperacion(monedaVO.getOperacion());
		this.setNacional(monedaVO.isNacional());

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

	public boolean isAceptaCotizacion() {
		return aceptaCotizacion;
	}

	public void setAceptaCotizacion(boolean acepta_cotizacion) {
		this.aceptaCotizacion = acepta_cotizacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	public double getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(double cotizacion) {
		this.cotizacion = cotizacion;
	}

	
	
}
