package com.excepciones;

public class InicializandoException extends Exception {
	
	public InicializandoException(){
		
		super("Ha ocurrido un error de comunicación con el sistema, revise archivo de configuración para la conexión con la base de datos");
	}
}
