package com.excepciones.Impuestos;

public class NoExisteImpuestoException extends Exception {
	public NoExisteImpuestoException(){
		super("No existe c�digo de impuesto");
	}
}

