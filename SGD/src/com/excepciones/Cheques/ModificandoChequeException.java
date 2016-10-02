package com.excepciones.Cheques;

public class ModificandoChequeException extends Exception{
	public ModificandoChequeException()
	{
		super("Ha ocurrido un error modificando el cheque");
	}
}
