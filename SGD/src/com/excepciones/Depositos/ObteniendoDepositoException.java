package com.excepciones.Depositos;

public class ObteniendoDepositoException extends Exception{
	
	public ObteniendoDepositoException(){
		
		super("Ha ocurrido un error obteniendo los depůsitos del sistema");
	}
}
