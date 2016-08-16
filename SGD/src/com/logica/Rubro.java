package com.logica;

import com.valueObject.RubroVO;

public class Rubro extends Auditoria{
	
	private String cod_rubro;
	private String descripcion;
	private boolean activo;
	private Impuesto impuesto;
	private String tipo_rubro;
	private String cod_tipo_rubro;
	
	

	public Rubro(){
		impuesto = new Impuesto();
	}

	public Rubro(String cod_rubro, String descripcion, boolean activo, Impuesto impuesto,
			     String tipo_rubro, String cod_tipo_rubro) {
		super();
		this.cod_rubro = cod_rubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.impuesto = impuesto;
		this.tipo_rubro = tipo_rubro;
		this.cod_tipo_rubro = cod_tipo_rubro;
	}

	public Rubro(RubroVO rubroVO){
		super(rubroVO.getUsuarioMod(), rubroVO.getFechaMod(), rubroVO.getOperacion());
		this.impuesto = new Impuesto();
		this.cod_rubro = rubroVO.getcodRubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = rubroVO.isActivo();
		this.impuesto.setCod_imp(rubroVO.getCodigoImpuesto());
		this.tipo_rubro = rubroVO.getTipoRubro();
		this.cod_tipo_rubro = rubroVO.getCodTipoRubro();
		
		
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

	public String getTipo_rubro() {
		return tipo_rubro;
	}

	public void setTipo_rubro(String tipo_rubro) {
		this.tipo_rubro = tipo_rubro;
	}

	public String getCod_tipo_rubro() {
		return cod_tipo_rubro;
	}

	public void setCod_tipo_rubro(String cod_tipo_rubro) {
		this.cod_tipo_rubro = cod_tipo_rubro;
	}

	
	

}
