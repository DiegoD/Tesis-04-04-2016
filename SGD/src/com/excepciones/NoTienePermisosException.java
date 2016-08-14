package com.excepciones;

public class NoTienePermisosException extends Exception{

	public NoTienePermisosException()
	{
		super("El usuario no tiene permisos para realizar la operación.");
	}
	
}
