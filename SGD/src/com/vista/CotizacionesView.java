package com.vista;

import com.valueObject.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.controladores.CotizacionesController;
import com.excepciones.ObteniendoMonedasException;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.sun.org.apache.xpath.internal.operations.VariableSafeAbsRef;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
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
	
	public BeanFieldGroup<CotizacionVO> fieldGroup;
	
	FormLayout  layout1 = new FormLayout ();
	
	
	final PopupDateField  fecha = new PopupDateField ();
	final ComboBox codMoneda = new ComboBox();
	final TextField impVenta = new TextField();
	final TextField impCompra = new TextField();

		
	final Button btnIngresar = new Button("Ingresar");
	
	public CotizacionesView(boolean editar) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		
		this.inicializarForm();
				
	}

	public CotizacionesView(){
		boolean editar = false;
		if(editar){
			
			fieldGroup.buildAndBindMemberFields(this);
		}
	}
	
	
	private void inicializarForm() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		this.controlador = new CotizacionesController();
		
		this.fieldGroup =  new BeanFieldGroup<CotizacionVO>(CotizacionVO.class);
		
		this.fecha.setCaption("Fecha");
		this.codMoneda.setCaption("Moneda");
		
		this.impVenta.setCaption("Importe Venta");
		this.impCompra.setCaption("Importe Compra");
		
		////////////////////////////////////////////
		
		Validator numberValidator = new RegexpValidator("^\\d*\\.?\\d*$", "Admite solamente numeros");
		
		this.fecha.setRequired(true);
		this.fecha.setRequiredError("Es requerido");
		
		this.codMoneda.setRequired(true);
		this.codMoneda.setRequiredError("Es requerido");
		
		this.impVenta.setRequired(true);
		this.impVenta.setRequiredError("Es requerido");
		this.impVenta.addValidator(numberValidator);
		this.impVenta.setImmediate(true);
		
		this.impCompra.setRequired(true);
		this.impCompra.setRequiredError("Es requerido");
		this.impCompra.addValidator(numberValidator);
		this.impCompra.setImmediate(true);
		
		///////////////////////////////////////////
		
		//region Description

		int asd =0;

		//endregion
		
		
		this.btnIngresar.addClickListener(new Button.ClickListener() {
	           
          

			@Override
           public void buttonClick(ClickEvent event) {
               
               String result = "";
               
               /*Verificamos que todos los campos sean válidos*/
               if(impCompra.isValid() && fecha.isValid() && codMoneda.isValid() 
            		   && impVenta.isValid() && impCompra.isValid())
               {   
            	 
	              
            	   try {
		               /*MonedaVO mon = (MonedaVO)ddlMonedas.getValue();
		               String s =mon.getClass().toString(); */
  
		               CotizacionVO cotizacionVO = new CotizacionVO();
	            	   
	            	   cotizacionVO.setFecha(new java.sql.Date(fecha.getValue().getTime()));
	            	   cotizacionVO.setCodMoneda(((MonedaVO)codMoneda.getValue()).getCodMoneda());
	            	   cotizacionVO.setImpVenta(Float.parseFloat(impVenta.getValue()));
	            	   cotizacionVO.setImpCompra(Float.parseFloat(impCompra.getValue()));
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
		
		
		layout1.addComponent(fecha);
		layout1.addComponent(codMoneda);
		layout1.addComponent(impVenta);
		layout1.addComponent(impCompra);
		layout1.addComponent(btnIngresar);
		
		setContent(layout1);
		
		if(true)
			fieldGroup.buildAndBindMemberFields(this);
		
	}	//Fin inicializar FORM
	
	
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
	
	
	private void fillComboMonedas() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		try {
			
			ArrayList<MonedaVO> lstMonedas = controlador.getMonedas();
			
			for (MonedaVO monedaVO : lstMonedas) {
				
				this.codMoneda.addItem(monedaVO);
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
