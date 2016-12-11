package com.excepciones.Recibo;

public class ExisteReciboException extends Exception{
	
	public ExisteReciboException(){
		super("Ya existe el recibo");
	}
}
