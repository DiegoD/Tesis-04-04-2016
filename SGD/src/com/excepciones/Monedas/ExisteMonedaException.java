package com.excepciones.Monedas;

public class ExisteMonedaException extends Exception{
	
	public ExisteMonedaException(){
		super("Ya existe la moneda");
	}
}
