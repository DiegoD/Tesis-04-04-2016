package com.logica;

import org.json.simple.JSONObject;

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
	
	public Impuesto (JSONObject jsonImpuesto){
		this.codImpuesto = (int) jsonImpuesto.get("codigo");
		this.descImpuesto = (String) jsonImpuesto.get("descImpuesto");
		this.porcentajeImpuesto = (int) jsonImpuesto.get("porcentaje");
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
