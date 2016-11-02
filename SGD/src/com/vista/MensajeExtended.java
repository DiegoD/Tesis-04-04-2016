package com.vista;

public class MensajeExtended extends Mensaje{
	
	
	Object seleccionado;
	IBusqueda main;
	
	public MensajeExtended(String mensaje, IBusqueda main){
		
		this.lblMensaje.setCaption(mensaje);
		this.lblMensaje.setStyleName("center");
		
		this.aceptar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
		
		this.cancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
}
