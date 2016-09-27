package com.excepciones.Saldos;

public class EliminandoSaldoException extends Exception{
	public EliminandoSaldoException(){
		super("Error eliminando el saldo");
	}
}