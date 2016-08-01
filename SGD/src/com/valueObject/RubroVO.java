package com.valueObject;

import com.logica.Auditoria;

public class RubroVO extends Auditoria{
	
	private String codRubro;
	private String descripcion;
	private boolean activo;
	
	public RubroVO(){
	}
	
	public RubroVO(String cod_rubro, String descripcion, boolean activo) {
		super();
		this.codRubro = cod_rubro;
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
		
		this.codRubro = rubroVO.getcodRubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = 	rubroVO.isActivo();
	}
	
	public String getcodRubro() {
		return codRubro;
	}
	public void setcodRubro(String cod_rubro) {
		this.codRubro = cod_rubro;
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
