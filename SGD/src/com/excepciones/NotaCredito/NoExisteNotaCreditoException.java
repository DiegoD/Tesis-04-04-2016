package com.excepciones.NotaCredito;

public class NoExisteNotaCreditoException extends Exception {
	public NoExisteNotaCreditoException(){
		super("No existe nota de credito");
	}
}

