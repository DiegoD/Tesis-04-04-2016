package com.excepciones.CodigosGeneralizados;

public class ModificandoCodigoException extends Exception{
	public ModificandoCodigoException()
	{
		super("Ha ocurrido un error modificando el código");
	}
}
