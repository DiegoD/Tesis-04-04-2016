package com.vista.NotaCredito;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vista.IMensaje;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.Variables;
import com.vista.Factura.FacturaViewExtended;
import com.vista.detFactura.DetFacturaViewExtended;

public class SeleccionViewExtended extends SeleccionView{
	
	Object seleccionado;
	NotaCreditoViewExtended main;
	
	public SeleccionViewExtended(NotaCreditoViewExtended main){
		
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
