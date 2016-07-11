package com.excepciones.Usuarios;

public class InsertandoUsuarioException extends Exception{

	public InsertandoUsuarioException(){
		super("Ha ocurrido un error ingresando el usuario al sistema");
	}
}
