package com.valueObject;

public class CodigoGeneralizadoVO extends AuditoriaVO{
	
	private String codigo;
	private String valor;
	private String descripcion;
	
	
	public CodigoGeneralizadoVO(){
		
	}
	
	public CodigoGeneralizadoVO(String codigo, String valor, String descripcion) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	/**
	 * Copiamos todos los datos del código pasado
	 * por parametro
	 *
	 */
	public void copiar(CodigoGeneralizadoVO codigoGeneralizadoVO){

		this.setUsuarioMod(codigoGeneralizadoVO.getUsuarioMod());
		this.setFechaMod(codigoGeneralizadoVO.getFechaMod());
		this.setOperacion(codigoGeneralizadoVO.getOperacion());
		
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
