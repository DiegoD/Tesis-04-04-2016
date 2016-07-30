package com.excepciones.Documentos;

public class ExisteDocumentoException extends Exception{
	
	public ExisteDocumentoException(){
		super("Ya existe el documento");
	}
}
