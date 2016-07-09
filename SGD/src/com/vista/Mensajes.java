package com.vista;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class Mensajes {
	
	public static void mostrarMensajeError(String msj){

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
	

public static void mostrarMensajeOK(String msj){

	Notification notif = new Notification(
	"OK",
	"<br/>" + msj,
	Notification.Type.WARNING_MESSAGE,
	true); // Contains HTML
	
	
	notif.setDelayMsec(20000);
	notif.setPosition(Position.BOTTOM_RIGHT);
	//notif.setStyleName("mystyle");
	//notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
	
	notif.show(Page.getCurrent());

}

public static void mostrarMensajeWarning(String msj){

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
