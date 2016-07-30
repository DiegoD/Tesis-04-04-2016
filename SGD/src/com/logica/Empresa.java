package com.logica;

import com.valueObject.EmpresaVO;

public class Empresa extends Auditoria{
	
	private String cod_emp;
	private String nom_emp;
	private boolean activo;
	
	public Empresa(){
		
	}
	
	public Empresa(String cod_emp, String nom_emp, boolean activo) {
		super();
		this.cod_emp = cod_emp;
		this.nom_emp = nom_emp;
		this.activo = activo;
	}
	
	public Empresa(EmpresaVO empresaVO){
		
		super(empresaVO.getUsuarioMod(), empresaVO.getFechaMod(), empresaVO.getOperacion());
		
		this.cod_emp = empresaVO.getCodEmp();
		this.nom_emp = empresaVO.getNomEmp();
		this.activo = empresaVO.isActivo();
	}

	public String getCod_emp() {
		return cod_emp;
	}

	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
	}

	public String getNom_emp() {
		return nom_emp;
	}

	public void setNom_emp(String nom_emp) {
		this.nom_emp = nom_emp;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
