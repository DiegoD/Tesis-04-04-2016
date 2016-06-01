package com.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PruebaVentana extends Window{
	
	private TextField tfNomCliente = new TextField("Ingresa el nombre");
	
    
    
    public PruebaVentana() {
       super("Devolver valor"); // Set window caption
       center();

       // Some basic content for the window
       VerticalLayout content = new VerticalLayout();
       content.addComponent(new Label("Esta funcionando la Ventana!!!"));
       content.addComponent(tfNomCliente); 
       content.setMargin(true);
       setContent(content);

       // Disable the close button
       setClosable(false);

       // Trivial logic for closing the sub-window
       Button ok = new Button("OK");
       ok.addClickListener(new ClickListener() {
           public void buttonClick(ClickEvent event) {
               
              // padre.setNombreCliente(tfNomCliente.getValue());
               //close(); // Close the sub-window
               
               
           }
       });
       content.addComponent(ok);
   }	

}
