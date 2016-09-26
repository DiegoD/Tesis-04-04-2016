package com.excepciones.Saldos;

public class NoExisteSaldoException extends Exception {
	public NoExisteSaldoException(){
		super("No existe el saldo");
	}
}

