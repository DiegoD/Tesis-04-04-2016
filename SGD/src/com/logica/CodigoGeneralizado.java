package com.logica;

import com.valueObject.CodigoGeneralizadoVO;

public class CodigoGeneralizado extends Auditoria{
	
	private String codigo;
	private String valor;
	private String descripcion;
	
	public CodigoGeneralizado(){
		
	}
	
	public CodigoGeneralizado(String codigo, String valor, String descripcion, boolean activo) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.descripcion = descripcion;
	}

	public CodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO){
		
		super(codigoGeneralizadoVO.getUsuarioMod(), codigoGeneralizadoVO.getFechaMod(), codigoGeneralizadoVO.getOperacion());
		this.codigo = codigoGeneralizadoVO.getCodigo();
		this.valor = codigoGeneralizadoVO.getValor();
		this.descripcion = codigoGeneralizadoVO.getDescripcion();
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
