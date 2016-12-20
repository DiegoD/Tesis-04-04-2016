package com.excepciones.NotaCredito;

public class ExisteNotaCreditoException extends Exception{
	
	public ExisteNotaCreditoException(){
		super("Ya existe la nota de credito");
	}
}
