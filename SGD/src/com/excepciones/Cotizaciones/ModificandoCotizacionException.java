package com.excepciones.Cotizaciones;

public class ModificandoCotizacionException extends Exception{
	public ModificandoCotizacionException()
	{
		super("Ha ocurrido un error modificando la cotización");
	}
}
