package com.excepciones.Impuestos;

public class ExisteImpuestoException extends Exception{
	
	public ExisteImpuestoException(){
		super("Ya existe el impuesto");
	}
}
