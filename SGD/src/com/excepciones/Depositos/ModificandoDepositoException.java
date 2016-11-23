package com.excepciones.Depositos;

public class ModificandoDepositoException extends Exception{
	public ModificandoDepositoException()
	{
		super("Ha ocurrido un error modificando el depósito");
	}
}
