package com.valueObject;

public class EmpresaVO extends AuditoriaVO {

	private int codEmp;
	private String nomEmp;
	private boolean activo;
	
	public int getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(int codEmp) {
		this.codEmp = codEmp;
	}
	public String getNomEmp() {
		return nomEmp;
	}
	public void setNomEmp(String nomEmp) {
		this.nomEmp = nomEmp;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
