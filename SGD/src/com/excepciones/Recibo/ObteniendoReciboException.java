package com.excepciones.Recibo;

public class ObteniendoReciboException extends Exception{
	
	public ObteniendoReciboException(){
		
		super("Ha ocurrido un error obteniendo recibos");
	}
}
