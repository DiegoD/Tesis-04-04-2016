package com.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MySearch extends Window{

	VerticalLayout content;
	private Button ok;
	private TextField tfBusqueda;
	private Grid grilla;
	IBusqueda padre;
	
	 public MySearch(IBusqueda padre) {
		 
		 this.padre = padre;
		 
		 content = new VerticalLayout();
		 
		 this.inicializar();
		 
		 center();
		 //setSizeFull();
		 
	 }
	 
	 	public MySearch() {
		 
	 		content = new VerticalLayout();
		 
	 		 tfBusqueda = new TextField();
			 tfBusqueda.setCaption("Busqueda");
			 Button ok = new Button("OK");
			 
			 content.addComponent(ok);
			 content.addComponent(tfBusqueda);
		 
			 setSizeFull();
		 
	 }
	 
	 private void inicializar(){
		 
		 setContent(content);
		 
		 tfBusqueda = new TextField();
		 tfBusqueda.setCaption("Busqueda");
		 
		 
		 Button ok = new Button("OK");
	        ok.addClickListener(new ClickListener() {
	            public void buttonClick(ClickEvent event) {
	                
	                padre.setField(tfBusqueda.getValue());
	                close(); // Close the sub-window
	                
	                
	            }
	        });
	       
	        content.addComponent(tfBusqueda);
	        content.addComponent(ok);
		 
	 }
	 
}
