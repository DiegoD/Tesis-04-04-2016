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
		setDraggable(false);
		//setModal(true);
		setResizable(true);
		//setScrollLeft(true);
		
		//setHeight("70%");
		//setWidth("55%");
		
	}
	
	public MySub(String height, String width){
		
		center();
		setClosable(true);
		setDraggable(true);
		setResizable(true);
		
		setHeight(height);
		setWidth(width);
		
	}
	
	public void setVista(Component component){
				
		setContent(component);
		
	}
	
	public void setTam(String height, String width){
		
		setHeight(height);
		setWidth(width);
		
	}

}
