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
import com.vista.Documentos.DocumentosPanelExtended;
//import com.vista.Clientes.ClienteView;
import com.vista.Clientes.ClientesPanelExtended;
import com.vista.CodigosGeneralizados.CodigosGeneralizadosPanelExtended;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Funcionarios.FuncionariosPanelExtended;
import com.vista.Grupos.GruposPanelExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;
import com.vista.Login.LoginExtended;
import com.vista.Monedas.MonedasPanelExtended;
import com.vista.Rubros.RubrosPanelExtended;
import com.vista.Usuarios.UsuariosPanelExtend;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
		
	private VerticalLayout tabMantenimientos;
	private VerticalLayout tabAdministracion;
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
		
		this.documentosButton.addClickListener(click -> {
			
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
		});
		
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
		
		this.funcionariosButton.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				FuncionariosPanelExtended c = new FuncionariosPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.codigosGeneralizados.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				CodigosGeneralizadosPanelExtended c = new CodigosGeneralizadosPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.rubros.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				RubrosPanelExtended u = new RubrosPanelExtended();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		this.setearOpcionesMenuAdministracion();
	}
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Mantenimientos
	 * 
	 */
	private void setearOpcionesMenuMantenimientos()
	{
		ArrayList<FormularioVO> lstFormsMenuMant = new ArrayList<FormularioVO>();
		
	
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_CLIENTES)
				|| formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_FUNCIONARIOS) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_IMPUESTO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_EMPRESAS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_DOCUMENTOS) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_MONEDAS) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RUBROS))
			{
				lstFormsMenuMant.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuMant.size()> 0)
		{

			this.tabMantenimientos = new VerticalLayout();
			//this.tabMantenimientos.setMargin(true);
			
			for (FormularioVO formularioVO : lstFormsMenuMant) {
				
				switch(formularioVO.getCodigo())
				{
										
					case VariablesPermisos.FORMULARIO_IMPUESTO :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_LEER))
							this.habilitarImpuestoButton();
					break;
					
					case VariablesPermisos.FORMULARIO_EMPRESAS :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_LEER))
							this.habilitarEmpresaButton();
					break;
					
					case VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarCodigosGeneralizadosButton();
					break;
					
					case VariablesPermisos.FORMULARIO_DOCUMENTOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DOCUMENTOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarDocumentosButton();
					break;
					
					case VariablesPermisos.FORMULARIO_MONEDAS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_MONEDAS, VariablesPermisos.OPERACION_LEER))
							this.habilitarMonedasButton();
					break;
					
					case VariablesPermisos.FORMULARIO_RUBROS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_LEER))
							this.habilitarRubros();
						
					case VariablesPermisos.FORMULARIO_CLIENTES:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CLIENTES, VariablesPermisos.OPERACION_LEER))
							this.habilitarClientes();
						
					case VariablesPermisos.FORMULARIO_FUNCIONARIOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarFuncionarios();
						
					break;
				}
				
			}
			
			this.acordion.addTab(tabMantenimientos, "Mantenimientos", null);
			
		}
		
		acordion.setHeight("75%"); /*Seteamos alto  del accordion*/
	}
	
	
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Mantenimientos
	 * 
	 */
	private void setearOpcionesMenuAdministracion()
	{
		ArrayList<FormularioVO> lstFormsMenuAdmin = new ArrayList<FormularioVO>();
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_USUARIO)
				|| formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_GRUPO))
			{
				lstFormsMenuAdmin.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuAdmin.size()> 0)
		{

			this.tabAdministracion = new VerticalLayout();
			//this.tabAdministracion.setMargin(true);
			
			
			for (FormularioVO formularioVO : lstFormsMenuAdmin) {
				
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
					
				}
				
			}
			
			this.acordion.addTab(tabAdministracion, "Administración", null);
			
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
		
		this.monedasButton.setVisible(false);
		this.monedasButton.setEnabled(false);
		
		this.rubros.setVisible(false);
		this.rubros.setEnabled(false);
		
		this.codigosGeneralizados.setVisible(false);
		this.codigosGeneralizados.setEnabled(false);
		
		this.documentosButton.setVisible(false);
		this.documentosButton.setEnabled(false);
		
		this.clientesButton.setVisible(false);
		this.clientesButton.setEnabled(false);
		
		this.funcionariosButton.setVisible(false);
		this.funcionariosButton.setEnabled(false);
		
		
	}
	
	
	private void habilitarUserButton()
	{
		this.userButton.setVisible(true);
		this.userButton.setEnabled(true);
		
		this.tabAdministracion.addComponent(this.userButton);
	}
	
	private void habilitarGrupoButton()
	{
		this.gruposButton.setVisible(true);
		this.gruposButton.setEnabled(true);
		
		this.tabAdministracion.addComponent(this.gruposButton);
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
	
	private void habilitarCodigosGeneralizadosButton()
	{
		this.codigosGeneralizados.setVisible(true);
		this.codigosGeneralizados.setEnabled(true);
		
		this.tabMantenimientos.addComponent(this.codigosGeneralizados);
	}
	
	private void habilitarDocumentosButton(){
		this.documentosButton.setVisible(true);
		this.documentosButton.setEnabled(true);
		
		this.tabMantenimientos.addComponent(documentosButton);
	}
	
	private void habilitarMonedasButton(){
		this.monedasButton.setVisible(true);
		this.monedasButton.setEnabled(true);
		this.tabMantenimientos.addComponent(monedasButton);
	}
	
	private void habilitarRubros(){
		this.rubros.setVisible(true);
		this.rubros.setEnabled(true);
		this.tabMantenimientos.addComponent(rubros);
	}
	
	private void habilitarClientes(){
		this.clientesButton.setVisible(true);
		this.clientesButton.setEnabled(true);
		this.tabMantenimientos.addComponent(clientesButton);
	}
	
	private void habilitarFuncionarios(){
		this.funcionariosButton.setVisible(true);
		this.funcionariosButton.setEnabled(true);
		this.tabMantenimientos.addComponent(funcionariosButton);
	}
	
	public PermisosUsuario getPermisosUsuario()
	{
		return this.permisos;
	}
	
	
	
}
