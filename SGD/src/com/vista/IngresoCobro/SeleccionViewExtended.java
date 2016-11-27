package com.vista.IngresoCobro;

import com.vista.IMensaje;
import com.vista.Mensajes;
import com.vista.Factura.FacturaViewExtended;

public class SeleccionViewExtended extends SeleccionView{
	
	Object seleccionado;
	IngresoCobroViewExtended main;
	
	public SeleccionViewExtended(IngresoCobroViewExtended main){
		
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
		
		this.aceptar.addClickListener(click -> {
			
			if(operacion.getValue() != null){
				if(this.operacion.getValue().equals("Agregar Gasto")){
					main.agregarGasto();
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
