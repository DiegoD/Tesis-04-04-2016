package com.excepciones.DocLog;

public class ModificandoLogException extends Exception{

	public ModificandoLogException(){
		super("Ha ocurrido un error modificando log del documento al sistema");
	}
}
