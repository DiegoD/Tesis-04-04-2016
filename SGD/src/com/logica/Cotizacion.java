package com.logica;

import java.sql.Timestamp;

import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.empresa.EmpresaVO;

public class Cotizacion extends Auditoria{
	
	private Moneda moneda;
	private Timestamp fecha;
	private double cotizacion_compra;
	private double cotizacion_venta;
	
	public Cotizacion(){
		
	}
	
	public Cotizacion(Moneda moneda, Timestamp fecha, double cotizacion_compra, double cotizacion_venta) {
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
		
		this.fecha = (Timestamp) cotizacionVO.getFecha();
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

	public double getCotizacion_compra() {
		return cotizacion_compra;
	}

	public void setCotizacion_compra(double cotizacion_compra) {
		this.cotizacion_compra = cotizacion_compra;
	}

	public double getCotizacion_venta() {
		return cotizacion_venta;
	}

	public void setCotizacion_venta(double cotizacion_venta) {
		this.cotizacion_venta = cotizacion_venta;
	}
	
}
