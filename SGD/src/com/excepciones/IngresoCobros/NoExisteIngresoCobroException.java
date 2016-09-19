package com.excepciones.IngresoCobros;

public class NoExisteIngresoCobroException extends Exception {
	public NoExisteIngresoCobroException(){
		super("No existe código de cobro");
	}
}

