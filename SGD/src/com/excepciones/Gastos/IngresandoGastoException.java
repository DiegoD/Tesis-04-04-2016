package com.excepciones.Gastos;

public class IngresandoGastoException extends Exception{

	public IngresandoGastoException(){
		super("Error ingresando el gasto al sistema.");
	}
}
