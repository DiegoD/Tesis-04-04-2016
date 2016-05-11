package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MySub extends Window{
	
	public MySub() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		//setContent(new CotizacionesView());
		
		center();
	}
	
	public void setVista(Component component){
				
		setContent(component);
		
	}

}
