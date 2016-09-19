package com.valueObject;

public class ImpuestoInfoVO {

	private String codImpuesto;
	private String nomImpuesto;
	private double porcentaje;
	
	public ImpuestoInfoVO(){
	
	}
	
	public ImpuestoInfoVO(String cod, String nom, int porc){
		this.codImpuesto = cod;
		this.nomImpuesto = nom;
		this.porcentaje = porc;
	}
	
	public void copiar(ImpuestoInfoVO t){
		
		this.codImpuesto = t.codImpuesto;
		this.nomImpuesto = t.nomImpuesto;
		this.porcentaje = t.porcentaje;
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
