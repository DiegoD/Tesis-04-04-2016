package com.excepciones.Conciliaciones;

public class MovimientoConciliadoException extends Exception{
	public MovimientoConciliadoException(){
		super("El depósito está conciliado");
	}
}