package com.logica;

public class Impuesto {
	
	private int codImpuesto;
	private String descImpuesto;
	private int porcentajeImpuesto;
	
	public Impuesto() {
	}
	
	public Impuesto(int codImpuesto, String descImpuesto, int porcentajeImpuesto) {
		super();
		this.codImpuesto = codImpuesto;
		this.descImpuesto = descImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
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

	public int getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public void setPorcentajeImpuesto(int porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}
	
}
