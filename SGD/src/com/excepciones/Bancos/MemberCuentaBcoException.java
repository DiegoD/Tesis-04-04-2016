package com.excepciones.Bancos;

public class MemberCuentaBcoException extends Exception{

	public MemberCuentaBcoException(){
		super("Existe un cliente con este código");
	}
}
