package com.excepciones.Documentos;

public class ModificandoDocumentoException extends Exception{
	public ModificandoDocumentoException()
	{
		super("Ha ocurrido un error modificando el documento");
	}
}
