package com.excepciones.Cuentas;

public class ModificandoCuentaException extends Exception {

	public ModificandoCuentaException()
	{
		super("Ha ocurrido un error modificando la cuenta");
	}
}
