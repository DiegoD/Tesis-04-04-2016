package com.excepciones.Factura;

public class ObteniendoFacturasException extends Exception{
	
	public ObteniendoFacturasException(){
		
		super("Ha ocurrido un error obteniendo facturas");
	}
}
