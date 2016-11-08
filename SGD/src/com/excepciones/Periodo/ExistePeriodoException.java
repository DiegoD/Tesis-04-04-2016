package com.excepciones.Periodo;

public class ExistePeriodoException extends Exception{
	
	public ExistePeriodoException(){
		super("Ya existe el período");
	}
}
