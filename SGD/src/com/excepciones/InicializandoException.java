package com.excepciones;

public class InicializandoException extends Exception {
	
	public InicializandoException(){
		
		super("Ha ocurrido un error de comunicación con el sistema");
	}
}
