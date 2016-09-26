package com.excepciones.Saldos;

public class ExisteSaldoException extends Exception{

		public ExisteSaldoException(){
			super("El saldo ya existe");
	}
}