package com.excepciones.CodigosGeneralizados;

public class NoExisteCodigoException extends Exception {
	public NoExisteCodigoException(){
		super("No existe código ");
	}
}

