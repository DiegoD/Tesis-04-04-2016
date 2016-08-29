package com.excepciones.Cuentas;

public class InsertandoCuentaException extends Exception{

	public InsertandoCuentaException(){
		super("Ha ocurrido un error ingresando la cuenta al sistema");
	}
}
