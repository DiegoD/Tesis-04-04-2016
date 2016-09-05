package com.excepciones.Procesos;

public class NoExisteProcesoException extends Exception {
	public NoExisteProcesoException(){
		super("No existe el proceso");
	}
}

