package com.vista;

public class MensajeExtended extends Mensaje{
	
	
	Object seleccionado;
	IMensaje main;
	
	public MensajeExtended(String mensaje, IMensaje main){
		
		this.lblMensaje.setValue(mensaje);
		this.main = main;
		
		this.aceptar.addClickListener(click -> {
			
			main.eliminarFact();
			
		});
		
		this.cancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
}
