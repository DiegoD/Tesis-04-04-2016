package com.excepciones.Egresos;

public class NoExisteEgresoCobroException extends Exception {
	public NoExisteEgresoCobroException(){
		super("No existe c�digo de egreso");
	}
}

