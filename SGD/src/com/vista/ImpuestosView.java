package com.vista;

import com.controladores.CotizacionesController;
import com.controladores.ImpuestosController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.ImpuestoVO;
import com.valueObject.Variables;
import com.vaadin.ui.Button.ClickEvent;

public class ImpuestosView extends Panel implements View{
	
	private ImpuestosController controlador;
	
	VerticalLayout layout = new VerticalLayout();

    final TextField codImpuesto = new TextField();
    final TextField descImpuesto = new TextField();
    final TextField porcentaje = new TextField();
    final Button btnIngresar = new Button("Ingresar");
    
    public ImpuestosView()
    {
         this.codImpuesto.setCaption("Codigo");
         this.descImpuesto.setCaption("Descripcion");
         this.porcentaje.setCaption("Porcentaje");
         this.controlador = new ImpuestosController();
         
         System.out.println("Estoy en la vista \n");
         this.btnIngresar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
            	String result = "";
                //layout.addComponent(new Label(result));
            	try {
            		
            		ImpuestoVO impuestoVO = new ImpuestoVO();
            		impuestoVO.setCodImpuesto(Integer.parseInt(codImpuesto.getValue()));
            		impuestoVO.setDescImpuesto(descImpuesto.getValue());
            		impuestoVO.setPorcentaje(Integer.parseInt(porcentaje.getValue()));
            		
            		controlador.insertImpuesto(impuestoVO);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            }
        });
         
        layout.addComponents(codImpuesto, descImpuesto, porcentaje, btnIngresar);
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
