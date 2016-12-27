package com.excepciones.Conciliaciones;

public class ObteniendoConciliacionException extends Exception{
	
	public ObteniendoConciliacionException(){
		
		super("Ha ocurrido un error obteniendo las conciliaciones del sistema");
	}
}
