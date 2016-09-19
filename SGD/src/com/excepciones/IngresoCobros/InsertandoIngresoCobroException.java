package com.excepciones.IngresoCobros;

public class InsertandoIngresoCobroException extends Exception{
	public InsertandoIngresoCobroException(){
		super("Ha ocurrido un error ingresando el cobro al sistema");
	}

}
