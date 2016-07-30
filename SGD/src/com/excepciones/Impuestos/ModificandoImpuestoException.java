package com.excepciones.Impuestos;

public class ModificandoImpuestoException extends Exception{
	public ModificandoImpuestoException()
	{
		super("Ha ocurrido un error modificando el impuesto");
	}
}
