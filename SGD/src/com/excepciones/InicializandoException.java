package com.excepciones;

public class InicializandoException extends Exception {
	
	public InicializandoException(){
		
		super("Ha ocurrido un error de comunicaci�n con el sistema, revise archivo de configuraci�n para la conexi�n con la base de datos");
	}
}
