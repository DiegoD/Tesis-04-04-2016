package com.excepciones.Cuentas;

public class NoExisteCuentaException extends Exception{

	public NoExisteCuentaException(){
		super("No existe c�digo de cuenta");
	}
}
