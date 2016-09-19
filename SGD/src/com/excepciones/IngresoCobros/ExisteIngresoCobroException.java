package com.excepciones.IngresoCobros;

public class ExisteIngresoCobroException extends Exception{
	
	public ExisteIngresoCobroException(){
		super("Ya existe el cobro");
	}
}
