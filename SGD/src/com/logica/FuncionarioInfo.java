package com.logica;

public class FuncionarioInfo {
	
	private int codigo;
	private String nombre;
	
	public FuncionarioInfo(){
		
	}

	public FuncionarioInfo(int codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
