package com.valueObject;

import com.logica.Auditoria;

public class RubroVO extends Auditoria{
	
	private String codRubro;
	private String descripcion;
	private boolean activo;
	private String cod_impuesto;
	private String descripcionImpuesto;
	private float porcentajeImpuesto;
	private boolean activoImpuesto;
	
	public RubroVO(){
		
	}
	

	public RubroVO(String codRubro, String descripcion, boolean activo, String cod_impuesto, String descripcionImpuesto,
			float porcentajeImpuesto, boolean activoImpuesto) {
		super();
		this.codRubro = codRubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.cod_impuesto = cod_impuesto;
		this.descripcionImpuesto = descripcionImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.activoImpuesto = activoImpuesto;
	}


	/**
	 * Copiamos todos los datos del rubro pasado
	 * por parametro
	 *
	 */
	public void copiar(RubroVO rubroVO){

		this.setUsuarioMod(rubroVO.getUsuarioMod());
		this.setFechaMod(rubroVO.getFechaMod());
		this.setOperacion(rubroVO.getOperacion());
		
		this.codRubro = rubroVO.getcodRubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = 	rubroVO.isActivo();
		this.cod_impuesto = rubroVO.getCod_impuesto();
		this.descripcionImpuesto = rubroVO.getDescripcionImpuesto();
		this.porcentajeImpuesto = rubroVO.getPorcentajeImpuesto();
		this.activoImpuesto = rubroVO.isActivoImpuesto();
		
	}


	public String getcodRubro() {
		return codRubro;
	}


	public void setcodRubro(String codRubro) {
		this.codRubro = codRubro;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public String getCod_impuesto() {
		return cod_impuesto;
	}


	public void setCod_impuesto(String cod_impuesto) {
		this.cod_impuesto = cod_impuesto;
	}


	public String getDescripcionImpuesto() {
		return descripcionImpuesto;
	}


	public void setDescripcionImpuesto(String descripcionImpuesto) {
		this.descripcionImpuesto = descripcionImpuesto;
	}


	public float getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}


	public void setPorcentajeImpuesto(float porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}


	public boolean isActivoImpuesto() {
		return activoImpuesto;
	}


	public void setActivoImpuesto(boolean activoImpuesto) {
		this.activoImpuesto = activoImpuesto;
	}
	
	
}
