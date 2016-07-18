package com.excepciones.Usuarios;

public class ModificandoUsuarioException extends Exception {

	public ModificandoUsuarioException()
	{
		super("Ha ocurrido un error modificando el Usuario");
	}
}
