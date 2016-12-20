package com.excepciones.NotaCredito;

public class ModificandoNotaCreditoException extends Exception{
	public ModificandoNotaCreditoException()
	{
		super("Ha ocurrido un error modificando nota de credito");
	}
}
