package com.excepciones.grupos;

public class ObteniendoGruposException extends Exception{

	public ObteniendoGruposException(){
		super("Ha ocurrido un error obteniendo grupos del sistema");
	}
}
