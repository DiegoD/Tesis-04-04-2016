package com.excepciones.Monedas;

public class ModificandoMonedaException extends Exception{
	public ModificandoMonedaException()
	{
		super("Ha ocurrido un error modificando la moneda");
	}
}
