package com.valueObject;

public class TipoInCobVO {

	private String codigo;
	private String nombre;
	
	public TipoInCobVO(){
		
	
		
	}
	
	public TipoInCobVO(String cod, String nom){
		
		this.codigo = cod;
		this.nombre = nom;
		
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
