package com.excepciones.Conciliaciones;

public class NoExisteConciliacionException extends Exception {
	public NoExisteConciliacionException(){
		super("No existe depósito");
	}
}

