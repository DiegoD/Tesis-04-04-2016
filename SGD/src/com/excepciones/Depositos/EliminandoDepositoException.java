package com.excepciones.Depositos;

public class EliminandoDepositoException extends Exception{
	public EliminandoDepositoException(){
		super("Error eliminando el depósito");
	}
}