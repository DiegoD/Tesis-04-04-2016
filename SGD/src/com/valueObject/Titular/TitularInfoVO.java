package com.valueObject.Titular;


public class TitularInfoVO {

public TitularInfoVO(){}
	
	public TitularInfoVO(String codigo, String nombre){
		
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	private String codigo;
	private String nombre;
	
	public void copiar(TitularInfoVO t){
		
		this.codigo = t.codigo;
		this.nombre = t.nombre;
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
	
}
