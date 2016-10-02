package com.excepciones.Cheques;

public class ExisteChequeException extends Exception{
	
	public ExisteChequeException(){
		super("Ya existe el cheque");
	}
}
