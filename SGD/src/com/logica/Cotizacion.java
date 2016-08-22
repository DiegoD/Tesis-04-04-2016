package com.logica;

import java.sql.Timestamp;

import com.valueObject.EmpresaVO;
import com.valueObject.Cotizacion.CotizacionVO;

public class Cotizacion extends Auditoria{
	
	private Moneda moneda;
	private Timestamp fecha;
	private float cotizacion_compra;
	private float cotizacion_venta;
	
	public Cotizacion(){
		
	}
	
	public Cotizacion(Moneda moneda, Timestamp fecha, float cotizacion_compra, float cotizacion_venta) {
		super();
		this.moneda = moneda;
		this.fecha = fecha;
		this.cotizacion_compra = cotizacion_compra;
		this.cotizacion_venta = cotizacion_venta;
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
		this.cotizacion_compra = cotizacionVO.getCotizacionCompra();
		this.cotizacion_venta = cotizacionVO.getCotizacionVenta();
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

	public float getCotizacion_compra() {
		return cotizacion_compra;
	}

	public void setCotizacion_compra(float cotizacion_compra) {
		this.cotizacion_compra = cotizacion_compra;
	}

	public float getCotizacion_venta() {
		return cotizacion_venta;
	}

	public void setCotizacion_venta(float cotizacion_venta) {
		this.cotizacion_venta = cotizacion_venta;
	}
	
}
