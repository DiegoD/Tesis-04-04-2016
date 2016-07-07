package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.controladores.CotizacionesController;
import com.excepciones.InicializandoException;
import com.excepciones.ObteniendoMonedasException;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.valueObject.CotizacionVO;
import com.valueObject.MonedaVO;
import com.valueObject.Variables;

public class CotizacionViewExtended extends CotizacionView{

	private CotizacionesController controlador;
	
	public BeanFieldGroup<CotizacionVO> fieldGroup;
	
	public CotizacionViewExtended(boolean editar){
		
		this.inicializarFormulario();
		
	}
	
	private void inicializarFormulario(){
		
		this.controlador = new CotizacionesController();
		
		this.fieldGroup =  new BeanFieldGroup<CotizacionVO>(CotizacionVO.class);
		
		
		///////////////////////////////////////////
		
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
		
		this.fillComboMonedas();
		
		this.agregarListenerBoton();
		

		if(true)
			fieldGroup.buildAndBindMemberFields(this);
	}
	
	private void fillComboMonedas() {
		
		try {
			
			ArrayList<MonedaVO> lstMonedas = controlador.getMonedas();
			
			for (MonedaVO monedaVO : lstMonedas) {
				
				this.codMoneda.addItem(monedaVO);
			}
			
		} catch (ObteniendoMonedasException | InicializandoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//TENEMOS QUE MOSTRAR EL ERROR
		}
	}
	
	private void agregarListenerBoton(){
		
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
		
	}
	
	
	
	
	////////////////////MENSAJES DE ERROR//////////////////////
	
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
}
