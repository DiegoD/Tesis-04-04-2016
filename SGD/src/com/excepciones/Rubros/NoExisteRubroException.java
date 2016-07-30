package com.excepciones.Rubros;

public class NoExisteRubroException extends Exception {
	public NoExisteRubroException(){
		super("No existe código de rubro");
	}
}

