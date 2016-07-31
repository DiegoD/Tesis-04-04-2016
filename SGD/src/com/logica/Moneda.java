package com.logica;

import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;

public class Moneda extends Auditoria{
	
	private String cod_moneda;
	private String descripcion;
	private String simbolo;
	private boolean acepta_cotizacion;
	private boolean activo;
	
	public Moneda(){
			
	}

	public Moneda(String cod_moneda, String descripcion, String simbolo, boolean acepta_cotizacion, boolean activo) {
		super();
		this.cod_moneda = cod_moneda;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
		this.acepta_cotizacion = acepta_cotizacion;
		this.activo = activo;
	}

	public Moneda(MonedaVO monedaVO){
		
		super(monedaVO.getUsuarioMod(), monedaVO.getFechaMod(), monedaVO.getOperacion());
		
		this.cod_moneda = monedaVO.getCodMoneda();
		this.descripcion = monedaVO.getDescripcion();
		this.simbolo = monedaVO.getSimbolo();
		this.acepta_cotizacion = monedaVO.isAceptaCotizacion();
		this.activo = monedaVO.isActivo();

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
