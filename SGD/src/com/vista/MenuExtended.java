package com.vista;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
	
	//Accordion acordion;

	
	public MenuExtended(){
		
	
		// Have it take all space available in the layout.
		Accordion accordion = new Accordion();

		//acordion = new Accordion();
		
		acordion.setHeight("300px");
		
		//this.menuItems.addComponent(acordion);
		
		// Some components to put in the Accordion.
		Label l1 = new Label("There are no previously saved actions.");
		Label l2 = new Label("There are no saved notes.");
		Label l3 = new Label("There are currently no issues.");


		VerticalLayout tab1 = new VerticalLayout();
		//tab1.setSizeFull();
		
		tab1.addComponent(statusButton);
		tab1.addComponent(inboxButton);
		tab1.addComponent(gruposButton);
		
	
		
		// Add the components as tabs in the Accordion.
		accordion.addTab(l1, "Saved actions", null);
		//accordion.addTab(tab1, "Notes", null);
		//accordion.addTab(inboxButton, "Issues", null);
		
		content.addComponent(accordion);
		
		//this.acordion.setSizeFull();
				
		this.acordion.addTab(tab1, "Mantenimientos", null);
		//this.acordion.addTab(statusButton, "Status", null);
		this.acordion.addTab(l3, "Otros", null);
		
		//this.content.addComponent(acordion);
		
		this.userButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				//CotizacionesPanelExtended c = new CotizacionesPanelExtended();
				//c.setMenu(this);
				
				UsuariosPanelExtend u = new UsuariosPanelExtend();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		

		this.gruposButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				GruposPanelExtended c = new GruposPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				mostrarMensajeError(e.getMessage());
				
			}
		});
		
		this.statusButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new ImpuestosView());
		});
		
		this.inboxButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new FacturaEj());
		});
		
		this.archiveButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new CotizacionViewExtended(true));
			
		});
		
		
	}
	
	public  void setContent(Component comp)
	{
		contentAnterior = this.content;
		this.content.removeAllComponents();
		this.content.addComponent(comp);
	}
	
	
////////////////////MENSAJES//////////////////////
	
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
