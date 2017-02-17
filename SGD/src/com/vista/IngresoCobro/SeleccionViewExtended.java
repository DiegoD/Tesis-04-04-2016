package com.vista.IngresoCobro;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vista.IMensaje;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.Variables;
import com.vista.Egreso.IngresoEgresoViewExtended;
import com.vista.Factura.FacturaViewExtended;
import com.vista.detFactura.DetFacturaViewExtended;

public class SeleccionViewExtended extends SeleccionView{
	
	Object seleccionado;
	IngresoCobroViewExtended main;
	
	public SeleccionViewExtended(IngresoCobroViewExtended main){
		
		this.operacion_Factura.setVisible(false);
		this.operacion_Egreso.setVisible(false);
		
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
		this.operacion_Egreso.setVisible(false);
		
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
	
	public SeleccionViewExtended(IngresoEgresoViewExtended main){
		
		this.operacion.setVisible(false);
		this.operacion_Factura.setVisible(false);
		
		this.aceptar.addClickListener(click -> {
			
			if(operacion_Egreso.getValue() != null){
				if(this.operacion_Egreso.getValue().equals("Agregar Gasto")){
					
					main.agregarNuevoGasto();
				}
				else{
					
					main.asociarGastoSinMedioDePago();
					
					
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
