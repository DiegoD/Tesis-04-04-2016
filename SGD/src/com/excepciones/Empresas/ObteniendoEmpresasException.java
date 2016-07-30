package com.excepciones.Empresas;

public class ObteniendoEmpresasException extends Exception{
	
	public ObteniendoEmpresasException(){
		
		super("Ha ocurrido un error obteniendo empresas del sistema");
	}
}
