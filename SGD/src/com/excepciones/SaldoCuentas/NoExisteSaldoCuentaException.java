package com.excepciones.SaldoCuentas;

public class NoExisteSaldoCuentaException extends Exception {
	public NoExisteSaldoCuentaException(){
		super("No existe saldo cuenta");
	}
}

