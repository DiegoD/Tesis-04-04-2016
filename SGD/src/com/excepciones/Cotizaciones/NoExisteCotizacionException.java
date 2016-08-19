package com.excepciones.Cotizaciones;

public class NoExisteCotizacionException extends Exception {
	public NoExisteCotizacionException(){
		super("No existe la cotización ");
	}
}

