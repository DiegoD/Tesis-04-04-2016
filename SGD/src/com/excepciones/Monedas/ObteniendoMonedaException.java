package com.excepciones.Monedas;

public class ObteniendoMonedaException extends Exception{
	
	public ObteniendoMonedaException(){
		
		super("Ha ocurrido un error obteniendo monedas del sistema");
	}
}
