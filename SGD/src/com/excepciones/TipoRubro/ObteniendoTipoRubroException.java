package com.excepciones.TipoRubro;

public class ObteniendoTipoRubroException extends Exception{
	
	public ObteniendoTipoRubroException(){
		
		super("Ha ocurrido un error obteniendo los tipos de rubro del sistema");
	}
}
