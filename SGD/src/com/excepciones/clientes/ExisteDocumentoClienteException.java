package com.excepciones.clientes;

public class ExisteDocumentoClienteException extends Exception {

	public ExisteDocumentoClienteException(){
		super("Ya existe un cliente con este documento");
	}
}
