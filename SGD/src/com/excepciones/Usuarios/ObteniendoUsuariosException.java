package com.excepciones.Usuarios;

public class ObteniendoUsuariosException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ObteniendoUsuariosException(){
		super("Ha ocurrido un error obteniendo usuarios del sistema");
	}
}
