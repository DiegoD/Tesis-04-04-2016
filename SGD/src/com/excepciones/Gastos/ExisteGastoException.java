package com.excepciones.Gastos;

public class ExisteGastoException extends Exception{

		public ExisteGastoException(){
			super("El gasto ya existe");
	}
}