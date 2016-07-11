package com.excepciones.Usuarios;

public class ExisteUsuarioException extends Exception{

	public ExisteUsuarioException(){
		super("Ya existe el usuario");
	}
}
