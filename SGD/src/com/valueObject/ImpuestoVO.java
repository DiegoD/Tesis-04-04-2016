package com.valueObject;

public class ImpuestoVO {
	private int codImpuesto;
	private String descImpuesto;
	private int porcentaje;
	
	public ImpuestoVO(){
		
	}
	
	public ImpuestoVO(int codImpuesto, String descImpuesto, int porcentaje) {
		super();
		this.codImpuesto = codImpuesto;
		this.descImpuesto = descImpuesto;
		this.porcentaje = porcentaje;
	}
	public int getCodImpuesto() {
		return codImpuesto;
	}
	public void setCodImpuesto(int codImpuesto) {
		this.codImpuesto = codImpuesto;
	}
	public String getDescImpuesto() {
		return descImpuesto;
	}
	public void setDescImpuesto(String descImpuesto) {
		this.descImpuesto = descImpuesto;
	}
	public int getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}
