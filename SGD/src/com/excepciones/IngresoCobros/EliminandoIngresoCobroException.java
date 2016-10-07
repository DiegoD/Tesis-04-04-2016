package com.excepciones.IngresoCobros;

public class EliminandoIngresoCobroException extends Exception{

	public EliminandoIngresoCobroException(){
		super("Ha ocurrido un error eliminando el cobro al sistema");
	}
	
}
