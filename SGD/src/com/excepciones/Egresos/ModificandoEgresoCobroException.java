package com.excepciones.Egresos;

public class ModificandoEgresoCobroException extends Exception{
	public ModificandoEgresoCobroException()
	{
		super("Ha ocurrido un error modificando el egreso");
	}
}
