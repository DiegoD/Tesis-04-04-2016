package com.excepciones;


/*Si no existe la cotizacion para la fecha y moneda*/
public class NoExisteCotizacionException extends Exception{
	
	 public NoExisteCotizacionException(){
		    
	        super("No existe cotizaci�n para fecha y moneda");
	 } 
	

}
