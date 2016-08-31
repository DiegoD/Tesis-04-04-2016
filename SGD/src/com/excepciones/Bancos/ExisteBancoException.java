package com.excepciones.Bancos;

public class ExisteBancoException extends Exception{

	public ExisteBancoException(){
		super("El banco ya existe");
	}
}
