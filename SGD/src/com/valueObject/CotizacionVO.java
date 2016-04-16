package com.valueObject;

import java.util.Date;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;

public class CotizacionVO {

	private Date fecha;
	private String codMoneda;
	private float impCompra;
	private float impVenta;
	
	public CotizacionVO(String codMoneda, float impCompra, float impVenta){
		
		this.codMoneda = codMoneda;
		this.impCompra = impCompra;
		this.impVenta = impVenta;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}
	public float getImpCompra() {
		return impCompra;
	}
	public void setImpCompra(float impCompra) {
		this.impCompra = impCompra;
	}
	public float getImpVenta() {
		return impVenta;
	}
	public void setImpVenta(float impVenta) {
		this.impVenta = impVenta;
	}


	
}
