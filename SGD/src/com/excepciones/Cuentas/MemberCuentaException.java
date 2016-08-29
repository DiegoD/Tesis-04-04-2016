package com.excepciones.Cuentas;

public class MemberCuentaException extends Exception{

	public MemberCuentaException(){
		super("Ha ocurrido un error verificando cuenta");
	}
}
