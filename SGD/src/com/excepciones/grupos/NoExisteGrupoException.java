package com.excepciones.grupos;

public class NoExisteGrupoException extends Exception{

	public NoExisteGrupoException(){
		super("No existe c�digo de grupo");
	}
}
