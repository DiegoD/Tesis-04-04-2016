package com.excepciones.SaldoCuentas;

public class ExisteNroTransferencia extends Exception{
	public ExisteNroTransferencia(){
		super("Ya existe el n�mero de transferencia para el banco/cuenta");
	}
}