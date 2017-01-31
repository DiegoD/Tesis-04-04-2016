package com.valueObject;


public class ImpuestoVO extends AuditoriaVO{
	
	private String codImpuesto;
	private String descripcion;
	private double porcentaje;
	private boolean activo;
	
	public ImpuestoVO(){
		
	}
	

	/**
	 * Copiamos todos los datos del ImpuestoVO pasado
	 * por parametro
	 *
	 */
	public void copiar(ImpuestoVO impuestoVO){

		this.setUsuarioMod(impuestoVO.getUsuarioMod());
		this.setFechaMod(impuestoVO.getFechaMod());
		this.setOperacion(impuestoVO.getOperacion());
		
		this.codImpuesto = impuestoVO.getcodImpuesto();
		this.descripcion = impuestoVO.getDescripcion();
		this.activo = 	impuestoVO.isActivo();
		this.porcentaje = impuestoVO.getPorcentaje();
		
	}
	
	public String getcodImpuesto() {
		return codImpuesto;
	}

	public void setcodImpuesto(String codImpuesto) {
		this.codImpuesto = codImpuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
