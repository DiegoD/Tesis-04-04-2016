package com.logica;

import com.valueObject.TipoRubro.TipoRubroVO;

public class TipoRubro extends Auditoria{
	
	private String cod_tipoRubro;
	private String descripcion;
	private boolean activo;
	private String cod_emp;
	
	public TipoRubro(){}
	
	
	public TipoRubro(TipoRubroVO tipoRubroVO){
		
		super(tipoRubroVO.getUsuarioMod(), tipoRubroVO.getFechaMod(), tipoRubroVO.getOperacion());
		
		this.cod_tipoRubro = tipoRubroVO.getCodTipoRubro();
		this.descripcion = tipoRubroVO.getDescripcion();
		this.activo = tipoRubroVO.isActivo();
		this.cod_emp = tipoRubroVO.getCodEmp();
	}

	public String getCod_tipoRubro() {
		return cod_tipoRubro;
	}

	public void setCod_tipoRubro(String cod_tipoRubro) {
		this.cod_tipoRubro = cod_tipoRubro;
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
	
	public String getCod_emp() {
		return cod_emp;
	}

	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
	}


	
}
