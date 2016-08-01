package com.excepciones.CodigosGeneralizados;

public class ObteniendoCodigosException extends Exception{
	
	public ObteniendoCodigosException(){
		
		super("Ha ocurrido un error obteniendo códigos del sistema");
	}
}
