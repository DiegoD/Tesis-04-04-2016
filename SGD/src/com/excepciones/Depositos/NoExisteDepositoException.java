package com.excepciones.Depositos;

public class NoExisteDepositoException extends Exception {
	public NoExisteDepositoException(){
		super("No existe depósito");
	}
}

