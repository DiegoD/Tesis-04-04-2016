package com.excepciones.grupos;

public class ExisteGrupoException extends Exception{

	public ExisteGrupoException(){
		super("Ya existe código de grupo");
	}
}
