package com.excepciones.Cheques;

public class NoExisteChequeException extends Exception {
	public NoExisteChequeException(){
		super("No existe cheque");
	}
}

