package com.vista;

import com.controladores.DocumentoAduaneroController;
import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.valueObject.DocumentoAuaneroVO;
import com.valueObject.Variables;

public class DocumentoAduaneroView extends Panel implements View{


	FormLayout  layout1 = new FormLayout();
	
	private DocumentoAduaneroController controlador;

	final TextField tfNomDocum = new TextField();
	final ComboBox ddlActivo = new ComboBox();
	final Button btnIngresar = new Button("Ingresar");
	
	public DocumentoAduaneroView(){
		
		this.controlador = new DocumentoAduaneroController();
		
		this.tfNomDocum.setCaption("Nombre");
		this.ddlActivo.setCaption("Activo");
		
		this.ddlActivo.setRequired(true);
		this.ddlActivo.setRequiredError("Es requerido");
		
		this.tfNomDocum.setRequired(true);
		this.tfNomDocum.setRequiredError("Es requerido");
		
		this.fillDdlActivo();
		
		this.btnIngresar.addClickListener(new Button.ClickListener() {
	           
	          

			@Override
           public void buttonClick(ClickEvent event) {
               
               String result = "";
               
               /*Verificamos que todos los campos sean v�lidos*/
               if(tfNomDocum.isValid())
               {              
            	   try {
		               /*MonedaVO mon = (MonedaVO)ddlMonedas.getValue();
		               String s =mon.getClass().toString(); */

            		   boolean activo = ddlActivo.getValue().equals("S") ? true : false;
            		   
            		   DocumentoAuaneroVO documentoAuaneroVO = new DocumentoAuaneroVO();
           		   
            		   documentoAuaneroVO.setNomDocum(tfNomDocum.getValue().trim());
            		   documentoAuaneroVO.setActivo(activo);
            		   documentoAuaneroVO.setUsuarioMod("usuario");
	            	   
	            	   
						/*Llamamos al controlador*/
            		   controlador.insertDocumentAduanero(documentoAuaneroVO);
						
						mostrarMensajeOK(Variables.OK_INGRESO);
						
					}
            	    catch(IngresandoDocumentoAduaneroException e) {
            	    	
            	    	mostrarMensajeError(e.getMessage());
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
		layout1.addComponent(ddlActivo);
		layout1.addComponent(btnIngresar);
		
		setContent(layout1);
		
	}
	
	private void fillDdlActivo(){
		
		this.ddlActivo.addItem("S");
		this.ddlActivo.addItem("N");
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
            "Atenci�n",
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
