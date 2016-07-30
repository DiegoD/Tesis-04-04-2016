package com.valueObject;

public class EmpresaVO extends AuditoriaVO {

	private String cod_emp;
	private String nom_emp;
	private boolean activo;
	
	
	public EmpresaVO(){
		
	}
	public EmpresaVO(String cod_emp, String nom_emp, boolean activo) {
		super();
		this.cod_emp = cod_emp;
		this.nom_emp = nom_emp;
		this.activo = activo;
	}
	
	/**
	 * Copiamos todos los datos de la empresa pasado
	 * por parametro
	 *
	 */
	public void copiar(EmpresaVO empresaVO){

		this.setUsuarioMod(empresaVO.getUsuarioMod());
		this.setFechaMod(empresaVO.getFechaMod());
		this.setOperacion(empresaVO.getOperacion());
		
		this.cod_emp = empresaVO.getCodEmp();
		this.nom_emp = empresaVO.getNomEmp();
		this.activo = 	empresaVO.isActivo();
	}
	
	public String getCodEmp() {
		return cod_emp;
	}
	public void setCodEmp(String codEmp) {
		this.cod_emp = codEmp;
	}
	public String getNomEmp() {
		return nom_emp;
	}
	public void setNomEmp(String nomEmp) {
		this.nom_emp = nomEmp;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
