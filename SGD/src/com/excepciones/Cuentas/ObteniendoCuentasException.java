package com.excepciones.Cuentas;

public class ObteniendoCuentasException extends Exception{

	public ObteniendoCuentasException(){
		super("Ha ocurrido un error obteniendo cuentas del sistema");
	}
}
