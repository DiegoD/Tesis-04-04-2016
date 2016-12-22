package com.excepciones.SaldoCuentas;

public class ExisteNroTransferencia extends Exception{
	public ExisteNroTransferencia(){
		super("Ya existe el número de transferencia para el banco/cuenta");
	}
}