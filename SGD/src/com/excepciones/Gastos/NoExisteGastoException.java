package com.excepciones.Gastos;

public class NoExisteGastoException extends Exception {
	public NoExisteGastoException(){
		super("No existe el gasto");
	}
}

