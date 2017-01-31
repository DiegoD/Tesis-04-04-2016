package com.valueObject.TipoRubro;

import com.valueObject.AuditoriaVO;

public class TipoRubroVO extends AuditoriaVO{

	private String codTipoRubro;
	private String descripcion;
	private boolean activo;
	private String codEmp;
	public TipoRubroVO(){
		
	}
	

	/**
	 * Copiamos todos los datos del tipo rubro pasado
	 * por parametro
	 *
	 */
	public void copiar(TipoRubroVO tipoRubroVO){

		this.setCodTipoRubro(tipoRubroVO.getCodTipoRubro());
		this.setDescripcion(tipoRubroVO.getDescripcion());
		this.setActivo(tipoRubroVO.isActivo());
		
		this.setUsuarioMod(tipoRubroVO.getUsuarioMod());
		this.setFechaMod(tipoRubroVO.getFechaMod());
		this.setOperacion(tipoRubroVO.getOperacion());
		this.setCodEmp(tipoRubroVO.getCodEmp());
		
	}
	
	

	public String getCodTipoRubro() {
		return codTipoRubro;
	}

	public void setCodTipoRubro(String codTipoRubro) {
		this.codTipoRubro = codTipoRubro;
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
	
	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String cod_emp) {
		this.codEmp = cod_emp;
	}
	
}
