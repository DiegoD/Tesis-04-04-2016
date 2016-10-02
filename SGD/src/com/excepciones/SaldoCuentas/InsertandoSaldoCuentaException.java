package com.excepciones.SaldoCuentas;

public class InsertandoSaldoCuentaException extends Exception{
	
	public InsertandoSaldoCuentaException(){
		super("Ha ocurrido un error ingresando el saldo de cuenta");
	}

}
