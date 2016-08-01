package com.excepciones.CodigosGeneralizados;

public class ExisteCodigoException extends Exception{
	
	public ExisteCodigoException(){
		super("Ya existe el codigo");
	}
}
