package com.logica;

import com.valueObject.RubroVO;

public class Rubro extends Auditoria{
	
	private String cod_rubro;
	private String descripcion;
	private boolean activo;
	private Impuesto impuesto;
	
	public Rubro(){
		impuesto = new Impuesto();
	}

	public Rubro(String cod_rubro, String descripcion, boolean activo, Impuesto impuesto) {
		super();
		this.cod_rubro = cod_rubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.impuesto = impuesto;
	}

	public Rubro(RubroVO rubroVO){
		super(rubroVO.getUsuarioMod(), rubroVO.getFechaMod(), rubroVO.getOperacion());
		this.impuesto = new Impuesto();
		this.cod_rubro = rubroVO.getcodRubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = rubroVO.isActivo();
		this.impuesto.setCod_imp(rubroVO.getCod_impuesto());
		
		
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
	
	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}



	
	

}
