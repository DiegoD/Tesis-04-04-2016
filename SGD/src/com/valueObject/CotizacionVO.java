package com.valueObject;

import java.sql.Date;

import com.sun.jmx.snmp.Timestamp;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;

public class CotizacionVO extends AuditoriaVO{

	private Date fecha;
	private int codMoneda;
	private float impCompra;
	private float impVenta;
	
	public CotizacionVO(){}
	
	public CotizacionVO(Date fecha,int codMoneda, float impCompra, float impVenta){
		
		super(); //Hay que ver luego para pasarle la fecha GF
		
		this.fecha = fecha;
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
	public int getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(int codMoneda) {
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
