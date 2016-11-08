package com.excepciones.Periodo;

public class NoExistePeriodoException extends Exception {
	public NoExistePeriodoException(){
		super("No existe el período");
	}
}

