package com.excepciones.Impuestos;

public class ObteniendoImpuestosException extends Exception{
	
	public ObteniendoImpuestosException(){
		
		super("Ha ocurrido un error obteniendo impuestos del sistema");
	}
}
