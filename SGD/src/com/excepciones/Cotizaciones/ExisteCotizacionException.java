package com.excepciones.Cotizaciones;

public class ExisteCotizacionException extends Exception{
	
	public ExisteCotizacionException(){
		super("Ya existe cotizacion para moneda/fecha");
	}
}
