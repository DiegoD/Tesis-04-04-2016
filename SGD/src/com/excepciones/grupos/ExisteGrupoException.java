package com.excepciones.grupos;

public class ExisteGrupoException extends Exception{

	public ExisteGrupoException(){
		super("Ya existe c�digo de grupo");
	}
}
