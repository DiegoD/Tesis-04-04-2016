package com.excepciones.Procesos;

public class MemberProcesoException extends Exception{

	public MemberProcesoException(){
		super("Existe un proceso con este código");
	}
}
