package com.excepciones.Empresas;

public class ModificandoEmpresaException extends Exception{
	public ModificandoEmpresaException()
	{
		super("Ha ocurrido un error modificando la empresa");
	}
}
