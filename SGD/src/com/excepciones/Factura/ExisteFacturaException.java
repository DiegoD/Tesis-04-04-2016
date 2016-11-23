package com.excepciones.Factura;

public class ExisteFacturaException extends Exception{
	
	public ExisteFacturaException(){
		super("Ya existe la factura");
	}
}
