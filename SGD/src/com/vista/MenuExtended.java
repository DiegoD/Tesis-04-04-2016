package com.vista;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.FormularioVO;
import com.vista.Clientes.ClienteView;
import com.vista.Clientes.ClientesPanelExtended;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Grupos.GruposPanelExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;
import com.vista.Login.LoginExtended;
import com.vista.Monedas.MonedasPanelExtended;
import com.vista.Usuarios.UsuariosPanelExtend;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
		
	private VerticalLayout tabMantenimientos;
	private PermisosUsuario permisos;
	private Principal mainPrincipal; /*Variable para poder desloguearse*/
	
	public MenuExtended(Principal principalView){
		
		this.mainPrincipal = principalView;
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");;
		
		/*Primero deshabilitamos todas las funcionalidades*/
		this.deshabilitarFuncionalidades();
		
		/*Habilitamos las funcionalidades dados los permisos*/
		this.setearPermisosMenu();
		
		
		
		this.userButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				UsuariosPanelExtend u = new UsuariosPanelExtend();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		this.impuestoButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ImpuestosPanelExtended c = new ImpuestosPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.logoutButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				/*Para desloguearnos matamos la session y vamos a login*/
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("usuario", null);
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("permisos", null);
				
				LoginExtended c = new LoginExtended(this.mainPrincipal);
				this.mainPrincipal.setContent(c);
				
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.empresaButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				EmpresasPanelExtended c = new EmpresasPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		/*this.documentosButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				DocumentosPanelExtended c = new DocumentosPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});*/
		
		this.monedasButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				MonedasPanelExtended c = new MonedasPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.clientesButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ClientesPanelExtended c = new ClientesPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
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
		
	
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_USUARIO)
				|| formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_GRUPO) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_IMPUESTO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_EMPRESAS))
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
					case VariablesPermisos.FORMULARIO_USUARIO : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_LEER))
							this.habilitarUserButton();
					break;
										
					case VariablesPermisos.FORMULARIO_GRUPO :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_LEER))
							this.habilitarGrupoButton();
					break;
					
					case VariablesPermisos.FORMULARIO_IMPUESTO :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_LEER))
							this.habilitarImpuestoButton();
					break;
					
					case VariablesPermisos.FORMULARIO_EMPRESAS :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_LEER))
							this.habilitarEmpresaButton();
					break;
				}
				
			}
			
			this.acordion.addTab(tabMantenimientos, "Mantenimientos", null);
			
		}
		
		acordion.setHeight("75%"); /*Seteamos alto  del accordion*/
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
		
		this.impuestoButton.setVisible(false);
		this.impuestoButton.setEnabled(false);
		
		this.empresaButton.setVisible(false);
		this.empresaButton.setEnabled(false);
				
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
	
	private void habilitarImpuestoButton()
	{
		this.impuestoButton.setVisible(true);
		this.impuestoButton.setEnabled(true);
		
		this.tabMantenimientos.addComponent(this.impuestoButton);
	}
	
	private void habilitarEmpresaButton()
	{
		this.empresaButton.setVisible(true);
		this.empresaButton.setEnabled(true);
		
		this.tabMantenimientos.addComponent(this.empresaButton);
	}
	
	public PermisosUsuario getPermisosUsuario()
	{
		return this.permisos;
	}
	
	
	
}
