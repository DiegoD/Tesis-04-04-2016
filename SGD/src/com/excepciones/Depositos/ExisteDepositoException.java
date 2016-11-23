package com.excepciones.Depositos;

public class ExisteDepositoException extends Exception{
	
	public ExisteDepositoException(){
		super("Ya existe el depósito");
	}
}
