package com.excepciones;


/*La utilizamos cuando se quiere ingresar una cotizacion que ya existe*/
public class ExisteCotizacionException extends Exception {

	 public ExisteCotizacionException(){
		    
	        super("Ya existe cotizaci�n para fecha y moneda");
	 } 
}
