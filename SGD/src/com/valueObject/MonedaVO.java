package com.valueObject;

public class MonedaVO extends AuditoriaVO{
	
	private String cod_moneda;
	private String descripcion;
	private String simbolo;
	private boolean acepta_cotizacion;
	private boolean activo;
	
	public MonedaVO(){
		
	}

	public MonedaVO(String cod_moneda, String descripcion, String simbolo, boolean acepta_cotizacion, boolean activo) {
		super();
		this.cod_moneda = cod_moneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		this.acepta_cotizacion = acepta_cotizacion;
		this.activo = activo;
	}
	
	/**
	 * Copiamos todos los datos de la MonedaVO pasado
	 * por parametro
	 *
	 */
	public void copiar(MonedaVO monedaVO){
		
		this.setCod_moneda(monedaVO.getCod_moneda());
		this.setDescripcion(monedaVO.getDescripcion());
		this.setAcepta_cotizacion(monedaVO.isAcepta_cotizacion());
		this.setActivo(monedaVO.isActivo());
		
		this.setUsuarioMod(monedaVO.getUsuarioMod());
		this.setFechaMod(monedaVO.getFechaMod());
		this.setOperacion(monedaVO.getOperacion());

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

	public boolean isAcepta_cotizacion() {
		return acepta_cotizacion;
	}

	public void setAcepta_cotizacion(boolean acepta_cotizacion) {
		this.acepta_cotizacion = acepta_cotizacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
}
