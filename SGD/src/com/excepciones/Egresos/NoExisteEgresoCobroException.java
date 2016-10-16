package com.excepciones.Egresos;

public class NoExisteEgresoCobroException extends Exception {
	public NoExisteEgresoCobroException(){
		super("No existe código de egreso");
	}
}

