package com.excepciones.Egresos;

public class ObteniendoEgresoCobroException extends Exception{
	
	public ObteniendoEgresoCobroException(){
		
		super("Ha ocurrido un error obteniendo cobros del sistema");
	}
}
