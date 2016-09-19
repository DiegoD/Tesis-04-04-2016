package com.excepciones.IngresoCobros;

public class ObteniendoIngresoCobroException extends Exception{
	
	public ObteniendoIngresoCobroException(){
		
		super("Ha ocurrido un error obteniendo cobros del sistema");
	}
}
