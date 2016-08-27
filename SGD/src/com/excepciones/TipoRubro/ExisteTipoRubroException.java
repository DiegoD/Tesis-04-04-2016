package com.excepciones.TipoRubro;

public class ExisteTipoRubroException extends Exception{
	
	public ExisteTipoRubroException(){
		super("Ya existe el tipo de rubro");
	}
}
