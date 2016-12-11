package com.excepciones.Recibo;

public class NoExisteReciboException extends Exception {
	public NoExisteReciboException(){
		super("No existe recibo");
	}
}

