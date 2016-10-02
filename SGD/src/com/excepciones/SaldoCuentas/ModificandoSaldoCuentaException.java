package com.excepciones.SaldoCuentas;

public class ModificandoSaldoCuentaException extends Exception{
	public ModificandoSaldoCuentaException()
	{
		super("Ha ocurrido un error modificando el saldo cuenta");
	}
}
