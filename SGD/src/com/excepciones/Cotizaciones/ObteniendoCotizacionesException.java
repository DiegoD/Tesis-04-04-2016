package com.excepciones.Cotizaciones;

public class ObteniendoCotizacionesException extends Exception{
	
	public ObteniendoCotizacionesException(){
		
		super("Ha ocurrido un error obteniendo cotizaciones del sistema");
	}
}
