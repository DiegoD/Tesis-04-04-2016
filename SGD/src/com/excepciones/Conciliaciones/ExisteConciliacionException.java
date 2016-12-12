package com.excepciones.Conciliaciones;

public class ExisteConciliacionException extends Exception{
	
	public ExisteConciliacionException(){
		super("Ya existe la conciliación");
	}
}
