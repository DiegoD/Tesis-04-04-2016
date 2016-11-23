package com.excepciones.Factura;

public class NoExisteFacturaException extends Exception {
	public NoExisteFacturaException(){
		super("No existe factura");
	}
}

