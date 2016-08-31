package com.excepciones.Bancos;

public class ExisteCtaBancoException extends Exception {


	public ExisteCtaBancoException(){
		super("La cuenta ya existe");
	}
}
