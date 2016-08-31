package com.excepciones.Bancos;

public class InsertandoCuentaException extends Exception{

	public InsertandoCuentaException(){
		super("Error ingresando el cuenta al sistema");
	}
}
