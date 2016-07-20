package com.vista;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.FormularioVO;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
	PermisosUsuario permisos;
	
	private VerticalLayout tabMantenimientos;
	
	//Accordion acordion;

	
	public MenuExtended(PermisosUsuario permisosUsuario){
		
		this.permisos = permisosUsuario;
	
		
		/*Primero deshabilitamos todas las funcionalidades*/
		this.deshabilitarFuncionalidades();
		
		/*Habilitamos las funcionalidades dados los permisos*/
		this.setearPermisosMenu();
		
		
		// Have it take all space available in the layout.
		Accordion accordion = new Accordion();

		//acordion = new Accordion();
		
		//acordion.setHeight("300px");
		
		//this.menuItems.addComponent(acordion);
		
		// Some components to put in the Accordion.
		Label l1 = new Label("There are no previously saved actions.");
		Label l2 = new Label("There are no saved notes.");
		Label l3 = new Label("There are currently no issues.");

		


		
	
		
		// Add the components as tabs in the Accordion.
		accordion.addTab(l1, "Saved actions", null);
		//accordion.addTab(tab1, "Notes", null);
		//accordion.addTab(inboxButton, "Issues", null);
		
		content.addComponent(accordion);
		
		//this.acordion.setSizeFull();
				
		
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
				
				Mensajes.mostrarMensajeError(e.getMessage());
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
	
	
////////////////////PERMISOS//////////////////////

	/**
	 * Dado los permisos del usuario le damos acceso
	 * a las distintas funcionalidades
	 * 
	 */
	private void setearPermisosMenu()
	{
		/*Permisos del usuario para los mantenimientos*/
		this.setearOpcionesMenuMantenimientos();
	}
	
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * 
	 */
	private void setearOpcionesMenuMantenimientos()
	{
		ArrayList<FormularioVO> lstFormsMenuMant = new ArrayList<FormularioVO>();
		
		int cantidad = 0; /*Contamos la cantidad de botones para el usuario
			para setear el tamaño del layout dentro del accordion*/
		
		int tamIconPx = 15;
		int cantidadEnMantenimiento = 0;
		
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstFormularios()) {
			
			if(formularioVO.getCodigo().equals("MUsuarios")
				|| formularioVO.getCodigo().equals("MGrupos") 
				|| formularioVO.getCodigo().equals("MUsuarios"))
			{
				lstFormsMenuMant.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuMant.size()> 0)
		{

			this.tabMantenimientos = new VerticalLayout();
			
			
			for (FormularioVO formularioVO : lstFormsMenuMant) {
				
				switch(formularioVO.getCodigo())
				{
					case "MUsuarios" : this.habilitarUserButton();
									 
					break;
					
					case "MUsuarios" : this.habilitarUserButton();
					 
					break;
					case "MGrupos" :  this.habilitarGrupoButton();
					break;
				}
				
				cantidadEnMantenimiento ++;
			}
			
			int tam = tamIconPx * cantidadEnMantenimiento;
			this.tabMantenimientos.setHeight(Integer.toString(tam) + "px");
			
			/*Sumamos a la variable de alto del accorion en gral*/
			cantidad =+ cantidadEnMantenimiento;
			
			this.acordion.addTab(tabMantenimientos, "Mantenimientos", null);
			
		}
		
		int tamAccordion = tamIconPx * cantidad *3;
		acordion.setHeight(Integer.toString(tamAccordion) + "px");
		
	}
	
	/**
	 * Deshabiiltamos todas las funcionalidades
	 * 
	 */
	private void deshabilitarFuncionalidades()
	{
		this.userButton.setVisible(false);
		this.userButton.setEnabled(false);
		
		this.gruposButton.setVisible(false);
		this.gruposButton.setEnabled(false);
		
		this.statusButton.setVisible(false);
		this.statusButton.setEnabled(false);
		
		this.inboxButton.setVisible(false);
		this.inboxButton.setEnabled(false);
		
		
	}
	
	
	private void habilitarUserButton()
	{
		this.userButton.setVisible(true);
		this.userButton.setEnabled(true);
		
		this.tabMantenimientos.addComponent(this.userButton);
	}
	
	private void habilitarGrupoButton()
	{
		this.gruposButton.setVisible(true);
		this.gruposButton.setEnabled(true);
		
		this.tabMantenimientos.addComponent(this.gruposButton);
	}
	
	
	
}
