package com.excepciones.DocLog;

public class InsertandoLogException extends Exception{

	public InsertandoLogException(){
		super("Ha ocurrido un error ingresando log del documento al sistema");
	}
}
