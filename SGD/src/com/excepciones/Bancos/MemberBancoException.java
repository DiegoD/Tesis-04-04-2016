package com.excepciones.Bancos;

public class MemberBancoException extends Exception{

	public MemberBancoException(){
		super("Existe un cliente con este código");
	}
	
}
