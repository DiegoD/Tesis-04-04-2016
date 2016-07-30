package com.excepciones.clientes;

public class ExisteClienteExeption extends Exception {

	
	public ExisteClienteExeption(){
		super("El ciente ya existe");
	}
}
