package com.excepciones.Egresos;

public class EliminandoEgresoCobroException extends Exception{

	public EliminandoEgresoCobroException(){
		super("Ha ocurrido un error eliminando el egreso del sistema");
	}
	
}
