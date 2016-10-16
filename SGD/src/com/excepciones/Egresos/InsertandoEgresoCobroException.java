package com.excepciones.Egresos;

public class InsertandoEgresoCobroException extends Exception{
	public InsertandoEgresoCobroException(){
		super("Ha ocurrido un error ingresando el egreso al sistema");
	}

}
