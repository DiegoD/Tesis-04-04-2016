package com.vista;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.FormularioVO;
import com.vista.Documentos.DocumentosPanelExtended;
//import com.vista.Clientes.ClienteView;
import com.vista.Bancos.BancosPanelExtended;
import com.vista.Clientes.ClientesPanelExtended;
import com.vista.CodigosGeneralizados.CodigosGeneralizadosPanelExtended;
import com.vista.Cotizaciones.CotizacionesPanelExtended;
import com.vista.Cuentas.CuentasPanelExtended;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Funcionarios.FuncionariosPanelExtended;
import com.vista.Gastos.GastosPanelExtended;
import com.vista.Grupos.GruposPanelExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;
import com.vista.IngresoCobro.IngresoCobroPanelExtended;
import com.vista.Login.LoginExtended;
import com.vista.Monedas.MonedasPanelExtended;
import com.vista.Procesos.ProcesosPanelExtended;
import com.vista.Rubros.RubrosPanelExtended;
import com.vista.TipoRubro.TipoRubrosPanelExtended;
import com.vista.Usuarios.UsuariosPanelExtend;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
		
	private VerticalLayout tabMantenimientos;
	private VerticalLayout tabAdministracion;
	private PermisosUsuario permisos;
	private Principal mainPrincipal; /*Variable para poder desloguearse*/
	MenuBar barmenu;
	
	private void inicializarMenu(){
		
		this.barmenu = new MenuBar();
		this.barmenu.setStyleName("valo-menu-responsive");
		
		// A top-level menu item that opens a submenu
		MenuItem administracion = barmenu.addItem("Administración", null, null);
		MenuItem mantenimientos = barmenu.addItem("Mantenimientos", null, null);
		
		
		// Submenu item with a sub-submenu
		MenuItem usuarios = administracion.addItem("Usuarios", cmdUsuario);
		MenuItem grupos = administracion.addItem("Grupos", cmdGrupos);
		
		MenuItem impuestos = mantenimientos.addItem("Impuestos", cmdImpuestos);
		
		this.content.addComponent(barmenu);
		
	}
	
	MenuBar.Command cmdUsuario = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	        
	    	setSizeFull();
			
			content.removeAllComponents();
			try {
				
				UsuariosPanelExtend u = new UsuariosPanelExtend();
				content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
	    }
	};
	
	MenuBar.Command cmdGrupos = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	
	    	setSizeFull();
			
			content.removeAllComponents();
			try {
				
				GruposPanelExtended c = new GruposPanelExtended();
				c.setSizeFull();
				content.setSizeFull();
				
				content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
	    }
	};
	
	MenuBar.Command cmdImpuestos = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	
    		setSizeFull();
			
			content.removeAllComponents();
			try {
				
				ImpuestosPanelExtended c = new ImpuestosPanelExtended();
				c.setSizeFull();
				content.setSizeFull();
				
				content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
	    }
	};
	
	public MenuExtended(Principal principalView){
		
		this.inicializarMenu();
		
		this.mainPrincipal = principalView;
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");;
		
		menuTitleLabel.setValue(
				
				this.permisos.getUsuario()
				
				);
		this.logoutButton.setDescription("Logout");
		
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
		
		this.cotizaciones.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				CotizacionesPanelExtended u = new CotizacionesPanelExtended();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		
		this.tipoRubros.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				TipoRubrosPanelExtended u = new TipoRubrosPanelExtended();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.cuentas.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				CuentasPanelExtended u = new CuentasPanelExtended();
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.bancos.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				BancosPanelExtended u = new BancosPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.procesos.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ProcesosPanelExtended u = new ProcesosPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.gastos.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				GastosPanelExtended u = new GastosPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.ingCobro.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				IngresoCobroPanelExtended u = new IngresoCobroPanelExtended(); 
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
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_DOCUMENTOS_DGI) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_MONEDAS) || 
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_COTIZACIONES) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_TIPORUBROS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_CUENTAS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_BANCOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_PROCESOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_GASTOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_COBRO) ||
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
					
					case VariablesPermisos.FORMULARIO_COTIZACIONES:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_LEER))
							this.habilitarCotizaciones();
						
					break;
					
					case VariablesPermisos.FORMULARIO_TIPORUBROS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_TIPORUBROS, VariablesPermisos.OPERACION_LEER))
							this.habilitarTipoRubros();
						
					break;
					
					case VariablesPermisos.FORMULARIO_CUENTAS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_LEER))
							this.habilitarCuentas();
						
					break;
					
					case VariablesPermisos.FORMULARIO_BANCOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_BANCOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarBancos();
						
					break;
					
					case VariablesPermisos.FORMULARIO_PROCESOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarProcesos();
						
					break;
					
					case VariablesPermisos.FORMULARIO_GASTOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_LEER))
							this.habilitarGastos();
						
					break;
					
					case VariablesPermisos.FORMULARIO_INGRESO_COBRO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_LEER))
							this.habilitarIngresoCobro();
						
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
		
		this.cotizaciones.setVisible(false);
		this.cotizaciones.setEnabled(false);
		
		this.tipoRubros.setVisible(false);
		this.tipoRubros.setEnabled(false);
		
		this.cuentas.setVisible(false);
		this.cuentas.setEnabled(false);
		
		this.bancos.setVisible(false);
		this.bancos.setEnabled(false);
		
		this.procesos.setVisible(false);
		this.procesos.setEnabled(false);
		
		this.gastos.setVisible(false);
		this.gastos.setEnabled(false);
		
		this.ingCobro.setVisible(false);
		this.ingCobro.setEnabled(false);
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
	
	private void habilitarCotizaciones(){
		this.cotizaciones.setVisible(true);
		this.cotizaciones.setEnabled(true);
		this.tabMantenimientos.addComponent(cotizaciones);
	}
	
	private void habilitarTipoRubros(){
		this.tipoRubros.setVisible(true);
		this.tipoRubros.setEnabled(true);
		this.tabMantenimientos.addComponent(tipoRubros);
	}
	
	private void habilitarCuentas(){
		this.cuentas.setVisible(true);
		this.cuentas.setEnabled(true);
		this.tabMantenimientos.addComponent(cuentas);
	}
	
	private void habilitarBancos(){
		this.bancos.setVisible(true);
		this.bancos.setEnabled(true);
		this.tabMantenimientos.addComponent(bancos);
	}
	
	private void habilitarProcesos(){
		this.procesos.setVisible(true);
		this.procesos.setEnabled(true);
		this.tabMantenimientos.addComponent(procesos);
	}
	
	private void habilitarGastos(){
		this.gastos.setVisible(true);
		this.gastos.setEnabled(true);
		this.tabMantenimientos.addComponent(gastos);
	}
	
	private void habilitarIngresoCobro(){
		this.ingCobro.setVisible(true);
		this.ingCobro.setEnabled(true);
		this.tabMantenimientos.addComponent(ingCobro);
	}
	
	public PermisosUsuario getPermisosUsuario()
	{
		return this.permisos;
	}
	
	
	
}
