package com.logica;

public class Documento {
	
	private String codigo;
	private String nombre;
	private String numero;

	public Documento(){}
	
	public Documento(String codigo, String nombre, String numero){
		
		this.codigo =codigo;
		this.nombre = nombre;
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
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
