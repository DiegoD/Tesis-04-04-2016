package com.vista;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import com.abstractFactory.AbstractFactoryBuilder;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("sgd")
public class Principal extends UI {

	private Panel ActualView;
	private MonedaView monedaView; 
	private CotizacionesView cotizacionesView;
	private DocumentoAduaneroView documentoAduaneroView;
	private ImpuestosView impuestosView;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Principal.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		
		setContent(new MenuExtended());
	}

}