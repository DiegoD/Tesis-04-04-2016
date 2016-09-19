package com.logica.Docum;

import com.valueObject.ImpuestoInfoVO;

public class ImpuestoInfo {

	private String codImpuesto;
	private String nomImpuesto;
	private double porcentaje;
	
	public ImpuestoInfo(){
	
	}
	
	public ImpuestoInfo(ImpuestoInfoVO t){
		
		this.codImpuesto = t.getCodImpuesto();
		this.nomImpuesto = t.getNomImpuesto();
		this.porcentaje = t.getPorcentaje();
	}
	
	public ImpuestoInfo(String cod, String nom, double porc){
		this.codImpuesto = cod;
		this.nomImpuesto = nom;
		this.porcentaje = porc;
	}

	public ImpuestoInfoVO retornarImpuestoInfoVO(){
		
		ImpuestoInfoVO aux = new ImpuestoInfoVO();
		
		aux.setCodImpuesto(this.codImpuesto);
		aux.setNomImpuesto(this.nomImpuesto);
		aux.setPorcentaje(this.porcentaje);
		
		return aux;
		
	}
	
	public String getCodImpuesto() {
		return codImpuesto;
	}

	public void setCodImpuesto(String codImpuesto) {
		this.codImpuesto = codImpuesto;
	}

	public String getNomImpuesto() {
		return nomImpuesto;
	}

	public void setNomImpuesto(String nomImpuesto) {
		this.nomImpuesto = nomImpuesto;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	
}
