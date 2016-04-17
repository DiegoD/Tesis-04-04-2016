package com.excepciones.cotizaciones;


/*Error al ingresar una cotización*/
public class IngresandoCotizacionException extends Exception {

	 public IngresandoCotizacionException(){
		    
	        super("Error ingresando cotización");
	    } 
}
