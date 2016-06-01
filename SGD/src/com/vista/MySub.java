package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MySub extends Window{
	
	public MySub(){
		
		//setContent(new CotizacionesView());
		
		center();
		setClosable(true);
		//setDraggable(true);
		//setModal(true);
		setResizable(true);
		//setScrollLeft(true);
		
		setHeight("70%");
		setWidth("80%");
		
	}
	
	public void setVista(Component component){
				
		setContent(component);
		
	}

}
