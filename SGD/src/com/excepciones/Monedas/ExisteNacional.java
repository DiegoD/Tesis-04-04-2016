package com.excepciones.Monedas;

public class ExisteNacional extends Exception{
	
	public ExisteNacional(){
		super("Ya existe una moneda nacional");
	}
}
