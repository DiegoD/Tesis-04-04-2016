package com.excepciones.IngresoCobros;

public class ModificandoIngresoCobroException extends Exception{
	public ModificandoIngresoCobroException()
	{
		super("Ha ocurrido un error modificando el cobro");
	}
}
