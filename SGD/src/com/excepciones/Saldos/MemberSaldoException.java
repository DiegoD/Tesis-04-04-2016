package com.excepciones.Saldos;

public class MemberSaldoException extends Exception{

	public MemberSaldoException(){
		super("Existe un saldo con este código");
	}
}
