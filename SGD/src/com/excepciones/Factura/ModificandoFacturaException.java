package com.excepciones.Factura;

public class ModificandoFacturaException extends Exception{
	public ModificandoFacturaException()
	{
		super("Ha ocurrido un error modificando factura");
	}
}
