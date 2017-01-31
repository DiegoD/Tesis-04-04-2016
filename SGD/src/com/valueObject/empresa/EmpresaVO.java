package com.valueObject.empresa;

import com.valueObject.AuditoriaVO;

public class EmpresaVO extends AuditoriaVO {

	private String codEmp;
	private String nomEmp;
	private boolean activo;
	
	
	public EmpresaVO(){
		
	}
	
	public EmpresaVO(String cod_emp, String nom_emp, boolean activo) {
		super();
		this.codEmp = cod_emp;
		this.nomEmp = nom_emp;
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
		
		this.codEmp = empresaVO.getCodEmp();
		this.nomEmp = empresaVO.getNomEmp();
		this.activo = 	empresaVO.isActivo();
	}
	
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
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
