package com.excepciones.Documentos;

public class NoExisteDocumentoException extends Exception {
	public NoExisteDocumentoException(){
		super("No existe código de documento");
	}
}

