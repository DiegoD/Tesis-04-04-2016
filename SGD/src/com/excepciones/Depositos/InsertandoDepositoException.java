package com.excepciones.Depositos;

public class InsertandoDepositoException extends Exception{
	
	public InsertandoDepositoException(){
		super("Ha ocurrido un error ingresando el depósito al sistema");
	}

}
