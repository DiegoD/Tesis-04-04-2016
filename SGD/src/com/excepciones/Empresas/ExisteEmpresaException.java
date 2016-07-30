package com.excepciones.Empresas;

public class ExisteEmpresaException extends Exception{
	
	public ExisteEmpresaException(){
		super("Ya existe la empresa");
	}
}
