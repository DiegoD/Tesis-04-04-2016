package com.excepciones.Documentos;

public class NoExisteDocumentoException extends Exception {
	public NoExisteDocumentoException(){
		super("No existe c�digo de documento");
	}
}

