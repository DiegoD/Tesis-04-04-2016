package com.valueObject.banco;

import com.valueObject.AuditoriaVO;

public class CtaBcoVO extends AuditoriaVO{

	private String codigo;
	private String nombre;
	private String codBco;
	private String codEmp;
	private boolean activo;
	
	public CtaBcoVO(){}
	
	public void Copiar(CtaBcoVO vo){
		
		this.codigo = vo.codigo;
		this.nombre = vo.nombre;
		this.codBco = vo.codBco;
		this.codEmp = vo.codEmp;
		this.activo = vo.activo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodBco() {
		return codBco;
	}
	public void setCodBco(String codBco) {
		this.codBco = codBco;
	}
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}
