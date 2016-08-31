package com.excepciones.Bancos;

public class InsertandoBancoException extends Exception{

	public InsertandoBancoException(){
		super("Error ingresando el banco al sistema");
	}
}
