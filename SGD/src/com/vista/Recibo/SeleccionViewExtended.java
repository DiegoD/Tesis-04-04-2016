package com.vista.Recibo;

import com.vista.Mensajes;
import com.vista.Factura.FacturaViewExtended;

public class SeleccionViewExtended extends SeleccionView{
	
	Object seleccionado;
	ReciboViewExtended main;
	
	public SeleccionViewExtended(ReciboViewExtended main){
		
		this.operacion_Factura.setVisible(false);
		
		this.aceptar.addClickListener(click -> {
			
			if(operacion.getValue() != null){
				if(this.operacion.getValue().equals("Agregar Gasto")){
					main.agregarGasto();
				}
				else if(this.operacion.getValue().equals("A cuenta de Proceso")){
					main.agregarProceso();
				}
			}
			
			else{
				
				Mensajes.mostrarMensajeError("Debe seleccionar una opción");
			}
			
		});
		
		this.cancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
	
	public SeleccionViewExtended(FacturaViewExtended main){
		
		this.operacion.setVisible(false);
		
		this.aceptar.addClickListener(click -> {
			
			if(operacion_Factura.getValue() != null){
				if(this.operacion_Factura.getValue().equals("Agregar Gasto")){
					main.agregarGasto();
				}
				else{
					
					main.agregarDetalle();
					
				}
				
			}
			
			else{
				
				Mensajes.mostrarMensajeError("Debe seleccionar una opción");
			}
			
		});
		
		this.cancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
	

}
