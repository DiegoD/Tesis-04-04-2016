package com.excepciones.Monedas;

public class NoExisteMonedaException extends Exception {
	public NoExisteMonedaException(){
		super("No existe c�digo de moneda");
	}
}

