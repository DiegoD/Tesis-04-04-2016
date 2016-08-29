package com.excepciones.Cuentas;

public class ExisteCuentaException extends Exception{

	public ExisteCuentaException(){
		super("Ya existe cuenta");
	}
}
