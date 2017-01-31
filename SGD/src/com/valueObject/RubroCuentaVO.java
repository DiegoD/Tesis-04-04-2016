package com.valueObject;

import com.logica.RubroCuenta;

public class RubroCuentaVO {
	
	private String cod_rubro;
	private String descripcionRubro;
	private String cod_cuenta;
	private String descripcionCuenta;
	private String cod_impuesto;
	private String descripcionImpuesto;
	private double porcentaje;
	private boolean oficina;
	private boolean proceso;
	private boolean persona;
	private String cod_tipoRubro;
	private String descripcionTipoRubro;
	private boolean facturable;
	
	public RubroCuentaVO(){
		
	}


	public RubroCuentaVO(RubroCuenta rubro){
		
		this.cod_rubro = rubro.getCod_rubro();
		this.descripcionRubro = rubro.getDescripcionRubro();
		this.cod_cuenta = rubro.getCod_cuenta();
		this.descripcionCuenta = rubro.getDescripcionCuenta();
		this.cod_impuesto = rubro.getCod_impuesto();
		this.descripcionImpuesto = rubro.getDescripcionImpuesto();
		this.porcentaje = rubro.getPorcentaje();
		this.oficina = rubro.isOficina();
		this.proceso = rubro.isProceso();
		this.persona = rubro.isPersona();
		this.cod_tipoRubro = rubro.getCod_tipoRubro();
		this.descripcionTipoRubro = rubro.getDescripcionTipoRubro();
		this.facturable = rubro.isFacturable();
	}
	
	public String getCod_rubro() {
		return cod_rubro;
	}

	public void setCod_rubro(String cod_rubro) {
		this.cod_rubro = cod_rubro;
	}

	public String getDescripcionRubro() {
		return descripcionRubro;
	}

	public void setDescripcionRubro(String descripcionRubro) {
		this.descripcionRubro = descripcionRubro;
	}

	public String getCod_cuenta() {
		return cod_cuenta;
	}

	public void setCod_cuenta(String cod_cuenta) {
		this.cod_cuenta = cod_cuenta;
	}

	public String getDescripcionCuenta() {
		return descripcionCuenta;
	}

	public void setDescripcionCuenta(String descripcionCuenta) {
		this.descripcionCuenta = descripcionCuenta;
	}

	public String getCod_impuesto() {
		return cod_impuesto;
	}

	public void setCod_impuesto(String cod_impuesto) {
		this.cod_impuesto = cod_impuesto;
	}

	public String getDescripcionImpuesto() {
		return descripcionImpuesto;
	}

	public void setDescripcionImpuesto(String descripcionImpuesto) {
		this.descripcionImpuesto = descripcionImpuesto;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
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

	public String getCod_tipoRubro() {
		return cod_tipoRubro;
	}

	public void setCod_tipoRubro(String cod_tipoRubro) {
		this.cod_tipoRubro = cod_tipoRubro;
	}

	public String getDescripcionTipoRubro() {
		return descripcionTipoRubro;
	}

	public void setDescripcionTipoRubro(String descripcionTipoRubro) {
		this.descripcionTipoRubro = descripcionTipoRubro;
	}

	public boolean isFacturable() {
		return facturable;
	}

	public void setFacturable(boolean facturable) {
		this.facturable = facturable;
	}
	
}
