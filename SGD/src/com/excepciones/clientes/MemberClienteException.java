package com.excepciones.clientes;

public class MemberClienteException extends Exception{

	public MemberClienteException(){
		super("Existe un cliente con este código");
	}
}
