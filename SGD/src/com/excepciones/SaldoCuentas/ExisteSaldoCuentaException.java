package com.excepciones.SaldoCuentas;

public class ExisteSaldoCuentaException extends Exception{
	
	public ExisteSaldoCuentaException(){
		super("Ya existe el saldo en cuenta");
	}
}
