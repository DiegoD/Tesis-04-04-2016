package com.vista;


import com.controladores.CotizacionesController;
import com.controladores.DocumentoAduaneroController;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.valueObject.CotizacionVO;
import com.valueObject.MonedaVO;
import com.valueObject.Variables;

public class DocumentoAduaneroView extends Panel implements View{

	FormLayout  layout1 = new FormLayout();
	
	private DocumentoAduaneroController controlador;

	final TextField tfNomDocum = new TextField();
	final Button btnIngresar = new Button("Ingresar");
	
	public DocumentoAduaneroView(){
		
		this.controlador = new DocumentoAduaneroController();
		this.tfNomDocum.setCaption("Nombre");
		
		this.tfNomDocum.setRequired(true);
		this.tfNomDocum.setRequiredError("Es requerido");
		
		
		this.btnIngresar.addClickListener(new Button.ClickListener() {
	           
	          

			@Override
           public void buttonClick(ClickEvent event) {
               
               String result = "";
               
               /*Verificamos que todos los campos sean válidos*/
               if(tfNomDocum.isValid())
               {              
            	   try {
		               /*MonedaVO mon = (MonedaVO)ddlMonedas.getValue();
		               String s =mon.getClass().toString(); */
  
            		   //DocumentoAuaneroVO documentoAuaneroVO = new DocumentoAuaneroVO();
	            	   
	            	  
	            	   
	            	   
						/*Llamamos al controladoe*/
						
						mostrarMensajeOK(Variables.OK_INGRESO);
						
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
		
		
		
		layout1.addComponent(tfNomDocum);
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
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
