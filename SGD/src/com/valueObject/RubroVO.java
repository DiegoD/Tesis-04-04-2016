package com.valueObject;

import com.logica.Auditoria;
import com.logica.Rubro;

public class RubroVO extends Auditoria{
	
	private String codRubro;
	private String descripcion;
	private boolean activo;
	private String codigoImpuesto;
	private String descripcionImpuesto;
	private double porcentajeImpuesto;
	private boolean activoImpuesto;
	private String descripcionTipoRubro;
	private String codTipoRubro;
	private boolean oficina;
	private boolean proceso;
	private boolean persona;
	private boolean facturable;
	
	public RubroVO(){
		
	}
	

	public RubroVO(String codRubro, String descripcion, boolean activo, String cod_impuesto, String descripcionImpuesto,
			double porcentajeImpuesto, boolean activoImpuesto, String tipoRubro, String descripcionTipoRubro) {
		super();
		this.codRubro = codRubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.codigoImpuesto = cod_impuesto;
		this.descripcionImpuesto = descripcionImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.activoImpuesto = activoImpuesto;
		this.descripcionTipoRubro = descripcionTipoRubro;
		this.codTipoRubro = tipoRubro;
	}
	
	public RubroVO(String codRubro, String descripcion, boolean activo, String cod_impuesto, String descripcionImpuesto,
			double porcentajeImpuesto, boolean activoImpuesto, String tipoRubro, String descripcionTipoRubro,
			boolean oficina, boolean proceso, boolean persona, boolean facturable) {
		super();
		this.codRubro = codRubro;
		this.descripcion = descripcion;
		this.activo = activo;
		this.codigoImpuesto = cod_impuesto;
		this.descripcionImpuesto = descripcionImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.activoImpuesto = activoImpuesto;
		this.descripcionTipoRubro = descripcionTipoRubro;
		this.codTipoRubro = tipoRubro;
		this.oficina = oficina;
		this.proceso = proceso;
		this.persona = persona;
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
		this.descripcionTipoRubro = rubroVO.getDescripcionTipoRubro();
		this.codTipoRubro = rubroVO.getCodTipoRubro();
		this.oficina = rubroVO.isOficina();
		this.proceso = rubroVO.isProceso();
		this.persona = rubroVO.isPersona();
		this.facturable = rubroVO.isFacturable();
		
	}

	public RubroVO(Rubro rubro){
		this.codRubro = rubro.getCod_rubro();
		this.descripcion = rubro.getDescripcion();
		this.activo = rubro.isActivo();
		
		if(rubro.getTipoRubro().getCod_tipoRubro() != null){
			this.codTipoRubro = rubro.getTipoRubro().getCod_tipoRubro();
		}
		if(rubro.getImpuesto().getCod_imp() != null){
			this.codigoImpuesto = rubro.getImpuesto().getCod_imp();
		}
		
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


	public double getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}


	public void setPorcentajeImpuesto(double porcentajeImpuesto) {
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


	public String getCodTipoRubro() {
		return codTipoRubro;
	}


	public void setCodTipoRubro(String codTipoRubro) {
		this.codTipoRubro = codTipoRubro;
	}


	public String getDescripcionTipoRubro() {
		return descripcionTipoRubro;
	}


	public void setDescripcionTipoRubro(String descripcionTipoRubro) {
		this.descripcionTipoRubro = descripcionTipoRubro;
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
