package com.excepciones.grupos;

public class NoExisteGrupoException extends Exception{

	public NoExisteGrupoException(){
		super("No existe código de grupo");
	}
}
