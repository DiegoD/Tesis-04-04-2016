package com.excepciones.Empresas;

public class NoExisteEmpresaException extends Exception {
	public NoExisteEmpresaException(){
		super("No existe c�digo de empresa");
	}
}

