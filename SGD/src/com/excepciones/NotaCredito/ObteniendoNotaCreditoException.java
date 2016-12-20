package com.excepciones.NotaCredito;

public class ObteniendoNotaCreditoException extends Exception{
	
	public ObteniendoNotaCreditoException(){
		
		super("Ha ocurrido un error obteniendo nota de credito");
	}
}
