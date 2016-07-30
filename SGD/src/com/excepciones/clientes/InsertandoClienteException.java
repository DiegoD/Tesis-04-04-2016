package com.excepciones.clientes;

public class InsertandoClienteException extends Exception{

	public InsertandoClienteException(){
		super("Error ingresando el cliente al sistema");
	}
}
