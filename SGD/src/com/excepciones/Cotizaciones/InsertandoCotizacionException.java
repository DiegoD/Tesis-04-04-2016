package com.excepciones.Cotizaciones;

public class InsertandoCotizacionException extends Exception{
	public InsertandoCotizacionException(){
		super("Ha ocurrido un error ingresando la cotización al sistema");
	}

}
