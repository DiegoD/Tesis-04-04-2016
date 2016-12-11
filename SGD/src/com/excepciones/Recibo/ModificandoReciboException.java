package com.excepciones.Recibo;

public class ModificandoReciboException extends Exception{
	public ModificandoReciboException()
	{
		super("Ha ocurrido un error modificando recibo");
	}
}
