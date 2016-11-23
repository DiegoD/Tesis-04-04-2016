package com.excepciones.Factura;

public class EliminandoFacturaException extends Exception{

	public EliminandoFacturaException(){
		super("Ha ocurrido un error eliminando factura");
	}
	
}
