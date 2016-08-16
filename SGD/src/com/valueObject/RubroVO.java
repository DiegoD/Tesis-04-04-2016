package com.valueObject;

import com.logica.Auditoria;

public class RubroVO extends Auditoria{
	
	private String codRubro;
	private String descripcion;
	private boolean activo;
	private String codigoImpuesto;
	private String descripcionImpuesto;
	private float porcentajeImpuesto;
	private boolean activoImpuesto;
	private String tipoRubro;
	private String codTipoRubro;
	
	public RubroVO(){
		
	}
	

	public RubroVO(String codRubro, String descripcion, boolean activo, String cod_impuesto, String descripcionImpuesto,
			float porcentajeImpuesto, boolean activoImpuesto, String tipoRubro) {
		super();
		this.codRubro = codRubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.codigoImpuesto = cod_impuesto;
		this.descripcionImpuesto = descripcionImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.activoImpuesto = activoImpuesto;
		this.tipoRubro = tipoRubro;
		this.codTipoRubro = tipoRubro;
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
		this.codigoImpuesto = rubroVO.getCodigoImpuesto();
		this.descripcionImpuesto = rubroVO.getDescripcionImpuesto();
		this.porcentajeImpuesto = rubroVO.getPorcentajeImpuesto();
		this.activoImpuesto = rubroVO.isActivoImpuesto();
		this.tipoRubro = rubroVO.getTipoRubro();
		this.codTipoRubro = rubroVO.getCodTipoRubro();
		
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


	public String getCodigoImpuesto() {
		return codigoImpuesto;
	}


	public void setCodigoImpuesto(String cod_impuesto) {
		this.codigoImpuesto = cod_impuesto;
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
	
	public String getCodRubro() {
		return codRubro;
	}


	public void setCodRubro(String codRubro) {
		this.codRubro = codRubro;
	}


	public String getTipoRubro() {
		return tipoRubro;
	}


	public void setTipoRubro(String tipoRubro) {
		this.tipoRubro = tipoRubro;
	}


	public String getCodTipoRubro() {
		return codTipoRubro;
	}


	public void setCodTipoRubro(String codTipoRubro) {
		this.codTipoRubro = codTipoRubro;
	}
	
	
}
