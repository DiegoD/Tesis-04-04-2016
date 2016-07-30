package com.excepciones.Documentos;

public class ObteniendoDocumentosException extends Exception{
	
	public ObteniendoDocumentosException(){
		
		super("Ha ocurrido un error obteniendo documentos del sistema");
	}
}
