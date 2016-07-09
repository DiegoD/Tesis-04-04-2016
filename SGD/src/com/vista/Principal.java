package com.vista;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import com.abstractFactory.AbstractFactoryBuilder;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("sgd")
public class Principal extends UI {

	VerticalLayout layout = new VerticalLayout();
	MenuExtended menu;
	
	private LoginExtended loginView;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Principal.class)
	public static class Servlet extends VaadinServlet {
	}

	
	
	@Override
	protected void init(VaadinRequest request) {
		
		//setContent(new MenuExtended());
		//setContent(new LoginExtended());
		//this.loginView = new LoginExtended();
		
		//setContent(layout);
		
		//this.layout.addComponent(new LoginExtended(Principal.this));
		
		//setContent(new LoginExtended(Principal.this));
	}
	
	public Principal(){
		
	
		
		this.menu = new MenuExtended();
		
		setContent(new LoginExtended(Principal.this));
		
		//setContent(menu);
	}
	
	public void setMenu(){
		
		setContent(this.menu);
		
	}
	

}