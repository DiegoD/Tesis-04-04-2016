package com.excepciones.Procesos;

public class ExisteProcesoException extends Exception{

		public ExisteProcesoException(){
			super("El proceso ya existe");
	}
}