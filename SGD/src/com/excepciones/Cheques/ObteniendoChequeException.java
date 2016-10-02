package com.excepciones.Cheques;

public class ObteniendoChequeException extends Exception{
	
	public ObteniendoChequeException(){
		
		super("Ha ocurrido un error obteniendo los cheques del sistema");
	}
}
