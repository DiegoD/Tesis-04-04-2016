package com.excepciones.Factura;

public class ObteniendoSaldoException extends Exception{
	
	public ObteniendoSaldoException(){
		super("Ha ocurrido un error obteniendo el saldo de la factura");
	}
}
