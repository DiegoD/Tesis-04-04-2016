package com.vista;

import com.valueObject.*;

import java.util.ArrayList;

import com.controladores.CotizacionesController;
import com.excepciones.ObteniendoMonedasException;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.sun.org.apache.xpath.internal.operations.VariableSafeAbsRef;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.FloatRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification;


public class CotizacionesView extends Panel implements View{

	private CotizacionesController controlador;
	
	FormLayout  layout1 = new FormLayout ();
	
	
	final PopupDateField  pupFecha = new PopupDateField ();
	final ComboBox ddlMonedas = new ComboBox();
	final TextField tfImpVenta = new TextField();
	final TextField tfImpCompra = new TextField();

		
	final Button btnIngresar = new Button("Ingresar");
	
	public CotizacionesView(){
			
		this.controlador = new CotizacionesController();
		
		this.pupFecha.setCaption("Fecha");
		this.ddlMonedas.setCaption("Moneda");
		
		this.tfImpVenta.setCaption("Importe Venta");
		this.tfImpCompra.setCaption("Importe Compra");
		
		////////////////////////////////////////////
		
		Validator numberValidator = new RegexpValidator("^\\d*\\.?\\d*$", "Admite solamente numeros");
		
		this.pupFecha.setRequired(true);
		this.pupFecha.setRequiredError("Es requerido");
		
		this.ddlMonedas.setRequired(true);
		this.ddlMonedas.setRequiredError("Es requerido");
		
		this.tfImpVenta.setRequired(true);
		this.tfImpVenta.setRequiredError("Es requerido");
		this.tfImpVenta.addValidator(numberValidator);
		this.tfImpVenta.setImmediate(true);
		
		this.tfImpCompra.setRequired(true);
		this.tfImpCompra.setRequiredError("Es requerido");
		this.tfImpCompra.addValidator(numberValidator);
		this.tfImpCompra.setImmediate(true);
		
		///////////////////////////////////////////
		
		//region Description

		int asd =0;

		//endregion
		
		
		this.btnIngresar.addClickListener(new Button.ClickListener() {
	           
          

			@Override
           public void buttonClick(ClickEvent event) {
               
               String result = "";
               
               /*Verificamos que todos los campos sean válidos*/
               if(tfImpCompra.isValid() && pupFecha.isValid() && ddlMonedas.isValid() 
            		   && tfImpVenta.isValid() && tfImpCompra.isValid())
               {   
            	 
	              
            	   try {
		               /*MonedaVO mon = (MonedaVO)ddlMonedas.getValue();
		               String s =mon.getClass().toString(); */
  
		               CotizacionVO cotizacionVO = new CotizacionVO();
	            	   
	            	   cotizacionVO.setFecha(new java.sql.Date(pupFecha.getValue().getTime()));
	            	   cotizacionVO.setCodMoneda(((MonedaVO)ddlMonedas.getValue()).getCodMoneda());
	            	   cotizacionVO.setImpVenta(Float.parseFloat(tfImpVenta.getValue()));
	            	   cotizacionVO.setImpCompra(Float.parseFloat(tfImpCompra.getValue()));
	            	   cotizacionVO.setUsuarioMod("usuario");
	            	   
						controlador.insertCotizacion(cotizacionVO);
						
						mostrarMensajeOK(Variables.OK_INGRESO);
						
					} catch (IngresandoCotizacionException | MemberCotizacionException e) {
						
						mostrarMensajeError(e.getMessage());
					}
            	   	catch(ExisteCotizacionException e){
            		   mostrarMensajeWarning(e.getMessage());
            		   
            	   	}
            	    catch(Exception e) {
            	    	
            	    	mostrarMensajeError(Variables.ERROR_INESPERADO);
            	    }
		           
               }
               else
               {
            	   mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
               }
                          
           }
       });
		
		
		
		this.fillComboMonedas();
		
		
		layout1.addComponent(pupFecha);
		layout1.addComponent(ddlMonedas);
		layout1.addComponent(tfImpVenta);
		layout1.addComponent(tfImpCompra);
		layout1.addComponent(btnIngresar);
		
		setContent(layout1);
	}

	
	
	private void mostrarMensajeError(String msj){
		
		
        Notification notif = new Notification(
            "Error",
            "<br/>" + msj,
            Notification.Type.ERROR_MESSAGE,
            true); // Contains HTML

       
        notif.setDelayMsec(20000);
        notif.setPosition(Position.BOTTOM_RIGHT);
        //notif.setStyleName("mystyle");
        //notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
        
        notif.show(Page.getCurrent());
      		
	}
	
	private void mostrarMensajeOK(String msj){
		
		
        Notification notif = new Notification(
            "OK",
            "<br/>" + msj,
            Notification.Type.HUMANIZED_MESSAGE,
            true); // Contains HTML

       
        notif.setDelayMsec(20000);
        notif.setPosition(Position.BOTTOM_RIGHT);
        //notif.setStyleName("mystyle");
        //notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
        
        notif.show(Page.getCurrent());
      		
	}
	
	private void mostrarMensajeWarning(String msj){
		
		
        Notification notif = new Notification(
            "Atención",
            "<br/>" + msj,
            Notification.Type.WARNING_MESSAGE,
            true); // Contains HTML

       
        notif.setDelayMsec(20000);
        notif.setPosition(Position.BOTTOM_RIGHT);
        //notif.setStyleName("mystyle");
        //notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
        
        notif.show(Page.getCurrent());
      		
	}
	
	
	private void fillComboMonedas(){
		
		try {
			
			ArrayList<MonedaVO> lstMonedas = controlador.getMonedas();
			
			for (MonedaVO monedaVO : lstMonedas) {
				
				this.ddlMonedas.addItem(monedaVO);
			}
			
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
