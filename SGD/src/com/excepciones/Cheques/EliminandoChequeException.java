package com.excepciones.Cheques;

public class EliminandoChequeException extends Exception{
	public EliminandoChequeException(){
		super("Error eliminando el cheque");
	}
}