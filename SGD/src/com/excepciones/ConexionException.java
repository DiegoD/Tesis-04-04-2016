package com.excepciones;

public class ConexionException extends Exception {

	public ConexionException(){
		
		super("Error al conectarse con el sistema");
	}
}
