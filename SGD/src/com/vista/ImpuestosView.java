package com.vista;

import com.controladores.CotizacionesController;

import com.controladores.ImpuestosController;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.ImpuestoVO;
import com.valueObject.Variables;
import org.json.*;
import org.json.simple.JSONValue;

import com.vaadin.ui.Button.ClickEvent;

public class ImpuestosView extends Panel implements View{
	
	private ImpuestosController controlador;
	
	VerticalLayout layout = new VerticalLayout();

    final TextField codImpuesto = new TextField();
    final TextField descImpuesto = new TextField();
    final TextField porcentaje = new TextField();
    final Button btnIngresar = new Button("Ingresar");
    
    @SuppressWarnings("unchecked")
	public ImpuestosView()
    {
         this.codImpuesto.setCaption("Codigo");
         this.descImpuesto.setCaption("Descripcion");
         this.porcentaje.setCaption("Porcentaje");
         this.controlador = new ImpuestosController();
         
         final Table table = new Table("The Brightest Stars");
         
	     // Define two columns for the built-in container
	     table.addContainerProperty("Name", String.class, null);
	     table.addContainerProperty("Mag",  Integer.class, null);

	     // Add a row the hard way
	     Object newItemId = table.addItem();
	     Item row1 = table.getItem(newItemId);
	     row1.getItemProperty("Name").setValue("TEST");			
	     row1.getItemProperty("Mag").setValue(1);
	     table.setPageLength(5);
	     table.setEditable(true);
	     
         
         System.out.println("Estoy en la vista \n");
         this.btnIngresar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
            	String result = "";
                //layout.addComponent(new Label(result));
            	try {
            		
            		org.json.simple.JSONObject obj = new org.json.simple.JSONObject();

            		
            		obj.put("codigo", codImpuesto.getValue());
            		obj.put("descImpuesto", descImpuesto.getValue());
            		
            		
            		ImpuestoVO impuestoVO = new ImpuestoVO();
            		impuestoVO.setCodImpuesto(Integer.parseInt(codImpuesto.getValue()));
            		impuestoVO.setDescImpuesto(descImpuesto.getValue());
            		impuestoVO.setPorcentaje(Integer.parseInt(porcentaje.getValue()));
            		
            		controlador.insertImpuesto(impuestoVO, obj);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            }
        });
         
        layout.addComponents(codImpuesto, descImpuesto, porcentaje, btnIngresar, table);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        
        setContent(layout);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
    
}
