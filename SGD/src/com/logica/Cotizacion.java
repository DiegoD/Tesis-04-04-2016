package com.logica;

import java.sql.Timestamp;

import com.valueObject.EmpresaVO;
import com.valueObject.Cotizacion.CotizacionVO;

public class Cotizacion extends Auditoria{
	
	private Moneda moneda;
	private Timestamp fecha;
	private float cotizacion;
	
	public Cotizacion(Moneda moneda, Timestamp fecha, float cotizacion) {
		super();
		this.moneda = moneda;
		this.fecha = fecha;
		this.cotizacion = cotizacion;
	}
	
	public Cotizacion (CotizacionVO cotizacionVO){
		
		super(cotizacionVO.getUsuarioMod(), cotizacionVO.getFechaMod(), cotizacionVO.getOperacion());
		
		this.moneda = new Moneda();
		this.moneda.setCod_moneda(cotizacionVO.getCodMoneda());
		this.moneda.setSimbolo(cotizacionVO.getSimboloMoneda());
		this.moneda.setDescripcion(cotizacionVO.getDescripcionMoneda());
		this.moneda.setSimbolo(cotizacionVO.getSimboloMoneda());
		this.moneda.setAcepta_cotizacion(cotizacionVO.isAceptaCotizacionMoneda());
		this.moneda.setActivo(cotizacionVO.isActivoMoneda());
		
		this.fecha = cotizacionVO.getFecha();
		this.cotizacion = cotizacionVO.getCotizacion();
	}
	
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public float getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(float cotizacion) {
		this.cotizacion = cotizacion;
	}
	
}
