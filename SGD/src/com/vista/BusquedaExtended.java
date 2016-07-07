package com.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class BusquedaExtended extends Busqueda {
	
	IBusqueda padre;
	
	public BusquedaExtended(IBusqueda padre){
		
		this.padre = padre;
		
		btnBusqueda.addClickListener(new ClickListener() {
	         public void buttonClick(ClickEvent event) {
	             
	             padre.setField(tfBusqueda.getValue());
	             
	             //close(); // Close the sub-window
	            	             
	         }
	     });
		
	}
	
	

}
