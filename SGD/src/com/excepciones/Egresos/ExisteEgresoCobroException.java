package com.excepciones.Egresos;

public class ExisteEgresoCobroException extends Exception{
	
	public ExisteEgresoCobroException(){
		super("Ya existe el egreso");
	}
}
