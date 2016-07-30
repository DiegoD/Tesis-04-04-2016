package com.excepciones.Rubros;

public class ExisteRubroException extends Exception{
	
	public ExisteRubroException(){
		super("Ya existe el rubro");
	}
}
