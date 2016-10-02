package com.excepciones.SaldoCuentas;

public class EliminandoSaldoCuetaException extends Exception{
	public EliminandoSaldoCuetaException(){
		super("Error eliminando el saldo de cuenta");
	}
}