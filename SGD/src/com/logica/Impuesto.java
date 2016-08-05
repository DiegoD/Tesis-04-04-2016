package com.logica;

import java.util.ArrayList;

import com.valueObject.GrupoVO;
import com.valueObject.ImpuestoVO;

public class Impuesto extends Auditoria{
	
	private String cod_impuesto;
	private String descripcion;
	private float porcentaje;
	private boolean activo;
	
	public Impuesto(){
		
	}
	
	public Impuesto(String cod_imp){
		this.cod_impuesto = cod_imp;
	}
	public Impuesto(String cod_imp, String descripcion, float porcentaje){
		this.cod_impuesto = cod_imp;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
	}
	
	public Impuesto(String cod_imp, String descripcion, float porcentaje, boolean activo) {
		
		super();
		this.cod_impuesto = cod_imp;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
		this.activo = activo;
	}
	
	public Impuesto(ImpuestoVO impuestoVO){
		
		super(impuestoVO.getUsuarioMod(), impuestoVO.getFechaMod(), impuestoVO.getOperacion());
		
		this.cod_impuesto = impuestoVO.getcodImpuesto();
		this.descripcion = impuestoVO.getDescripcion();
		this.activo = impuestoVO.isActivo();
		this.porcentaje = impuestoVO.getPorcentaje();
	}

	public String getCod_imp() {
		return cod_impuesto;
	}

	public void setCod_imp(String cod_imp) {
		this.cod_impuesto = cod_imp;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	
}
