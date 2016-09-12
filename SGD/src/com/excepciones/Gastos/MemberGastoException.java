package com.excepciones.Gastos;

public class MemberGastoException extends Exception{

	public MemberGastoException(){
		super("Existe un gasto con este código");
	}
}
