package com.valueObject;

import com.logica.Auditoria;

public class RubroVO extends Auditoria{
	
	private String cod_rubro;
	private String descripcion;
	private boolean activo;
	
	public RubroVO(){
	}
	
	public RubroVO(String cod_rubro, String descripcion, boolean activo) {
		super();
		this.cod_rubro = cod_rubro;
		this.descripcion = descripcion;
		this.activo = activo;
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
		
		this.cod_rubro = rubroVO.getCod_rubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = 	rubroVO.isActivo();
	}
	
	public String getCod_rubro() {
		return cod_rubro;
	}
	public void setCod_rubro(String cod_rubro) {
		this.cod_rubro = cod_rubro;
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
	
	

}
