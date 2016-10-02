package com.excepciones.SaldoCuentas;

public class ObteniendoSaldoCuentaException extends Exception{
	
	public ObteniendoSaldoCuentaException(){
		
		super("Ha ocurrido un error obteniendo el saldo cuenta");
	}
}
