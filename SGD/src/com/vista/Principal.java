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
		
		
		Label  lblSelection = new Label();
		final VerticalLayout layout = new VerticalLayout();
		ActualView = new Panel();
        MenuBar barmenu = new MenuBar();      

        
		layout.setMargin(true);
		setContent(layout);

		/*Comando para Monedas*/
        MenuBar.Command cmdMonedaView = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                monedaView = new MonedaView();
                
                layout.replaceComponent(ActualView, monedaView);
                ActualView = monedaView;
            }
        };
        
        /*Comando para cotizaciones*/
        MenuBar.Command cmdCotizacionesView = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
            	try {
					cotizacionesView = new CotizacionesView();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                layout.replaceComponent(ActualView, cotizacionesView);
                ActualView = cotizacionesView;
            }
        };
        
        /*Comando para Documentos Aduaneros*/
        MenuBar.Command cmdDocumentosAduanerosView = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
            	documentoAduaneroView = new DocumentoAduaneroView();
                
                layout.replaceComponent(ActualView, documentoAduaneroView);
                ActualView = documentoAduaneroView;
            }
        };
        
        /*Comando para cotizaciones*/
        MenuBar.Command cmdImpuestoView = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
            	impuestosView = new ImpuestosView();
                
                layout.replaceComponent(ActualView, impuestosView);
                ActualView = impuestosView;
            }
        };
        
        
        // A top-level menu item that opens a submenu
        MenuItem mnuMantenimientos = barmenu.addItem("Mantenimientos", null, null);

        // Submenu item with a sub-submenu
        MenuItem mnuItemGral = mnuMantenimientos.addItem("General", null, null);
        mnuItemGral.addItem("Moneda",null,    cmdMonedaView);
        mnuItemGral.addItem("Cotizaciones",null,    cmdCotizacionesView);
        mnuItemGral.addItem("Docs. Aduaneros",null,    cmdDocumentosAduanerosView);
        mnuItemGral.addItem("Impuestos",null,    cmdImpuestoView);
        mnuItemGral.addItem("OtroConIcono",
            new ThemeResource("icons/coffee-16px.png"), cmdMonedaView);

        // Another submenu item with a sub-submenu
        MenuItem mnuFacturas = mnuMantenimientos.addItem("Facturas", null, null);
        mnuFacturas.addItem("Factura",      null, cmdMonedaView);
        
        // Another submenu item with a sub-submenu
        MenuItem mnuIngresoCliente = mnuMantenimientos.addItem("Ingreso Cliente", null, null);
        mnuIngresoCliente.addItem("Factura",      null, cmdMonedaView);
        
        
        /*
        // Another top-level item
        MenuItem mnuOtros = barmenu.addItem("Otros", null, null);
        mnuOtros.addItem("Otros1", null, cmdMonedaView);
        mnuOtros.addItem("Otros2",  null, cmdMonedaView);
        mnuOtros.addItem("Otros3", null, cmdMonedaView);
         */
        // Yet another top-level item
        MenuItem mnuPagos = barmenu.addItem("Pagos", null, null);
        mnuPagos.addItem("Solicitud", null, cmdMonedaView);
        
		layout.addComponent(barmenu);
		
		
	}

}