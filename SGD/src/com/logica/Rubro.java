package com.logica;

import com.valueObject.RubroVO;

public class Rubro extends Auditoria{
	
	private String cod_rubro;
	private String descripcion;
	private boolean activo;
	private Impuesto impuesto;
	private TipoRubro tipoRubro;
	private boolean oficina;
	private boolean proceso;
	private boolean persona;
	private boolean facturable;
	

	public Rubro(){
		impuesto = new Impuesto();
	}


	public Rubro(RubroVO rubroVO){
		super(rubroVO.getUsuarioMod(), rubroVO.getFechaMod(), rubroVO.getOperacion());
		
		this.impuesto = new Impuesto();
		this.tipoRubro = new TipoRubro();
		
		this.cod_rubro = rubroVO.getcodRubro();
		this.descripcion = rubroVO.getDescripcion();
		this.activo = rubroVO.isActivo();
		this.impuesto.setCod_imp(rubroVO.getCodigoImpuesto());
		this.tipoRubro.setCod_tipoRubro(rubroVO.getCodTipoRubro());
		this.oficina = rubroVO.isOficina();
		this.proceso = rubroVO.isProceso();
		this.persona = rubroVO.isPersona();
		this.facturable = rubroVO.isFacturable();
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

	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}

	public boolean isOficina() {
		return oficina;
	}

	public void setOficina(boolean oficina) {
		this.oficina = oficina;
	}

	public boolean isProceso() {
		return proceso;
	}

	public void setProceso(boolean proceso) {
		this.proceso = proceso;
	}

	public boolean isPersona() {
		return persona;
	}

	public void setPersona(boolean persona) {
		this.persona = persona;
	}

	public boolean isFacturable() {
		return facturable;
	}

	public void setFacturable(boolean facturable) {
		this.facturable = facturable;
	}

	
	
}
