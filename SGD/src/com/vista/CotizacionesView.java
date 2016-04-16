package com.vista;

import com.valueObject.*;

import java.util.ArrayList;

import com.controladores.CotizacionesController;
import com.excepciones.ObteniendoMonedasException;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CotizacionesView extends Panel implements View{

	private CotizacionesController controlador;
	
	FormLayout  layout1 = new FormLayout ();
	
	final TextField tfFechaCotizacion = new TextField();
	final PopupDateField  pupFecha = new PopupDateField ();
	
	public CotizacionesView(){
			
		this.controlador = new CotizacionesController();
		
		this.pupFecha.setCaption("Fecha");
		
		ComboBox codMoneda = new ComboBox();
		
		
		this.fillComboMonedas();
		
		
		
		layout1.addComponent(pupFecha);
		setContent(layout1);
	}

	
	private void fillComboMonedas(){
		
		try {
			
			ArrayList<MonedaVO> lstMonedas = controlador.getMonedas();
			
		} catch (ObteniendoMonedasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}
