package com.vista;

public class MensajeExtended extends Mensaje{
	
	
	Object seleccionado;
	IMensaje main;
	
	public MensajeExtended(String mensaje, IMensaje main, String operacion){
		
		this.lblMensaje.setValue(mensaje);
		this.main = main;
		
		this.aceptar.addClickListener(click -> {
			if(operacion.equals("Eliminar")){
				main.eliminarFact();
			}
			else if (operacion.equals("Anular")){
				main.anularFact();
			}
			
		});
		
		this.cancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
}
