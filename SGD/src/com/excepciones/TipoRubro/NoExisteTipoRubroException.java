package com.excepciones.TipoRubro;

public class NoExisteTipoRubroException extends Exception {
	public NoExisteTipoRubroException(){
		super("No existe el tipo de rubro");
	}
}

