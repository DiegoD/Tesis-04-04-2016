package com.vista.Procesos;

import com.vista.Mensajes;
import com.vista.Variables;

public class ProcesoObservacionesViewExtended extends ProcesoObservacionesView{
	
	
	public ProcesoObservacionesViewExtended(ProcesoViewExtended main, String observaciones, String operacion){
		
		if(operacion == "LECTURA"){
			this.aceptar.setEnabled(false);
			this.aceptar.setVisible(false);
			this.observaciones.setEnabled(false);
		}
		else{
			this.aceptar.setEnabled(true);
			this.aceptar.setVisible(true);
		}
		this.aceptar.addClickListener(click -> {
			
			try {
				main.setObservaciones(this.observaciones.getValue());
				main.cerrarVentana();
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
		
		this.observaciones.setValue(observaciones);
	}
	
	

}
