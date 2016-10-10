package com.excepciones.Gastos;

public class EliminandoGastoException extends Exception{
	
	public EliminandoGastoException(){
			super("No se puede eliminar el gasto");
	}

}
