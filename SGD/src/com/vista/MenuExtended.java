package com.vista;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.FormularioVO;
import com.vista.Documentos.DocumentosPanelExtended;
import com.vista.Egreso.IngresoEgresoPanelExtended;
import com.vista.Egreso.IngresoEgresoViewExtended;
import com.vista.EgresoOtro.IngresoEgresoOtroPanelExtended;
//import com.vista.Clientes.ClienteView;
import com.vista.Bancos.BancosPanelExtended;
import com.vista.Clientes.ClientesPanelExtended;
import com.vista.CodigosGeneralizados.CodigosGeneralizadosPanelExtended;
import com.vista.Cotizaciones.CotizacionesPanelExtended;
import com.vista.Cuentas.CuentasPanelExtended;
import com.vista.Deposito.DepositoPanelExtended;
import com.vista.Deposito.DepositoViewExtended;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Factura.FacturaPanelExtended;
import com.vista.Factura.FacturaViewExtended;
import com.vista.Funcionarios.FuncionariosPanelExtended;
import com.vista.Gastos.GastosPanelExtended;
import com.vista.Grupos.GruposPanelExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;
import com.vista.IngresoCobro.IngresoCobroPanelExtended;
import com.vista.IngresoOtro.IngresoOtroPanelExtended;
import com.vista.Login.LoginExtended;
import com.vista.Monedas.MonedasPanelExtended;
import com.vista.Periodo.PeriodosPanelExtended;
import com.vista.Procesos.ProcesosPanelExtended;
import com.vista.ResumenProceso.ResProcesosPanelExtended;
//import com.vista.ResumenProceso.ResProcesosPanelExtended;
import com.vista.Rubros.RubrosPanelExtended;
import com.vista.TipoRubro.TipoRubrosPanelExtended;
import com.vista.Usuarios.UsuariosPanelExtend;
import com.vaadin.ui.TabSheet.Tab;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	public static String nombre = "Menu";
		
	private VerticalLayout layoutMenu;
	//private VerticalLayout tabAdministracion;
	private PermisosUsuario permisos;
	private Principal mainPrincipal; /*Variable para poder desloguearse*/
	MenuBar barmenu;
	private DepositoViewExtended form;
	MySub sub = new MySub("75%", "65%");
	
	private void inicializarMenu(){
		
		/*
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
		*/
		
		
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
				
				HorizontalLayout h = new HorizontalLayout();
				h.setHeight("10px");
				h.setWidth("100%");
				h.addComponent(gruposButton);
				
				this.content.addComponent(h);
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
		
		this.ingEgreso.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				IngresoEgresoPanelExtended u = new IngresoEgresoPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.otroCobro.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				IngresoOtroPanelExtended u = new IngresoOtroPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.otroEgreso.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				IngresoEgresoOtroPanelExtended u = new IngresoEgresoOtroPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.resumenProc.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ResProcesosPanelExtended u = new ResProcesosPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});

		this.periodo.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				PeriodosPanelExtended c = new PeriodosPanelExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.deposito.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				DepositoPanelExtended u = new DepositoPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.factura.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				FacturaPanelExtended u = new FacturaPanelExtended(); 
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
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_COBRO_OTRO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_EGRESO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_EGRESO_OTRO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RESUMEN_PROCESO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_PERIODO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_DEPOSITO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_FACTURA) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RUBROS))
			{
				lstFormsMenuMant.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuMant.size()> 0)
		{

			this.layoutMenu = new VerticalLayout();
			//this.tabMantenimientos.setMargin(true);
			
			this.lbMantenimientos.setVisible(true);
			this.layoutMenu.addComponent(this.lbMantenimientos);
			
			
			for (FormularioVO formularioVO : lstFormsMenuMant) {
				
				switch(formularioVO.getCodigo())
				{
										
					case VariablesPermisos.FORMULARIO_IMPUESTO :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarImpuestoButton(); 
							
							this.layoutMenu.addComponent(this.impuestoButton);
							
							}
					break;
					
					case VariablesPermisos.FORMULARIO_EMPRESAS :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarEmpresaButton();
							this.layoutMenu.addComponent(this.empresaButton);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarCodigosGeneralizadosButton();
							this.layoutMenu.addComponent(this.codigosGeneralizados);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_DOCUMENTOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DOCUMENTOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarDocumentosButton();
							this.layoutMenu.addComponent(this.documentosButton);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_MONEDAS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_MONEDAS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarMonedasButton();
							this.layoutMenu.addComponent(this.monedasButton);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_RUBROS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarRubros();
							this.layoutMenu.addComponent(this.rubros);
						}
						
					case VariablesPermisos.FORMULARIO_CLIENTES:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CLIENTES, VariablesPermisos.OPERACION_LEER)){
							this.habilitarClientes();
							this.layoutMenu.addComponent(this.clientesButton);
						}
						
					case VariablesPermisos.FORMULARIO_FUNCIONARIOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarFuncionarios();
							this.layoutMenu.addComponent(this.funcionariosButton);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_COTIZACIONES:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_LEER)){
							this.habilitarCotizaciones();
							this.layoutMenu.addComponent(this.cotizaciones);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_TIPORUBROS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_TIPORUBROS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarTipoRubros();
							this.layoutMenu.addComponent(this.tipoRubros);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_CUENTAS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarCuentas();
							this.layoutMenu.addComponent(this.cuentas);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_BANCOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_BANCOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarBancos();
							this.layoutMenu.addComponent(this.bancos);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_PROCESOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarProcesos();
							this.layoutMenu.addComponent(this.procesos);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_GASTOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarGastos();
							this.layoutMenu.addComponent(this.gastos);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_INGRESO_COBRO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarIngresoCobro();
							this.layoutMenu.addComponent(this.ingCobro);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_INGRESO_COBRO_OTRO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO_OTRO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarOtroCobro();
							this.layoutMenu.addComponent(this.otroCobro);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_INGRESO_EGRESO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarIngresoEgreso();
							this.layoutMenu.addComponent(this.ingEgreso);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_INGRESO_EGRESO_OTRO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO_OTRO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarOtroEgreso();
							this.layoutMenu.addComponent(this.otroEgreso);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_RESUMEN_PROCESO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RESUMEN_PROCESO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarResumenProceso();
							this.layoutMenu.addComponent(this.resumenProc);
						}
						
					break;
					
					case VariablesPermisos.FORMULARIO_PERIODO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PERIODO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarPeriodo();
							this.layoutMenu.addComponent(this.periodo);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_DEPOSITO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarDeposito();
							this.layoutMenu.addComponent(this.deposito);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_FACTURA:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarFactura();
							this.layoutMenu.addComponent(this.factura);
						}
					break;
				}
				
			}
			
			//TODO
			//this.acordion.addTab(tabMantenimientos, "Mantenimientos", null);
			
			this.menuItems.addComponent(this.layoutMenu);
			
		}
		
		//acordion.setHeight("75%"); /*Seteamos alto  del accordion*/
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
			//TODO
			//this.tabAdministracion = new VerticalLayout();
			//this.tabAdministracion.setMargin(true);
			
			this.lbAdministracion.setVisible(true);
			this.layoutMenu.addComponent(this.lbAdministracion);
			
			for (FormularioVO formularioVO : lstFormsMenuAdmin) {
				
				switch(formularioVO.getCodigo())
				{
					case VariablesPermisos.FORMULARIO_USUARIO : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarUserButton();
							this.layoutMenu.addComponent(this.userButton);
						}
					break;
										
					case VariablesPermisos.FORMULARIO_GRUPO :
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarGrupoButton();
							this.layoutMenu.addComponent(this.gruposButton);
						}
					break;
					
				}
				
			}
			
			//TODO
			//this.acordion.addTab(tabAdministracion, "Administración", null);
			
		}
		//TODO
		//acordion.setHeight("75%"); /*Seteamos alto  del accordion*/
	}
	
	/**
	 * Deshabiiltamos todas las funcionalidades
	 * 
	 */
	private void deshabilitarFuncionalidades()
	{
		
		this.lbMantenimientos.setVisible(false);
		
		
		this.lbAdministracion.setVisible(false);
		
		
		
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
		
		this.ingEgreso.setVisible(false);
		this.ingEgreso.setEnabled(false);
		
		this.otroCobro.setVisible(false);
		this.otroCobro.setEnabled(false);
		
		this.otroEgreso.setVisible(false);
		this.otroEgreso.setEnabled(false);
		
		this.resumenProc.setVisible(false);
		this.resumenProc.setEnabled(false);
		
		this.periodo.setVisible(false);
		this.periodo.setEnabled(false);
		
		this.deposito.setVisible(false);
		this.deposito.setEnabled(false);
		
		this.factura.setVisible(false);
		this.factura.setEnabled(false);
	}
	
	
	private void habilitarUserButton()
	{
		this.userButton.setVisible(true);
		this.userButton.setEnabled(true);
		
		//TODO
		//this.tabAdministracion.addComponent(this.userButton);
	}
	
	private void habilitarGrupoButton()
	{
		this.gruposButton.setVisible(true);
		this.gruposButton.setEnabled(true);
		
		//TODO
		//this.tabAdministracion.addComponent(this.gruposButton);
	}
	
	private void habilitarImpuestoButton()
	{
		this.impuestoButton.setVisible(true);
		this.impuestoButton.setEnabled(true);
		
		this.layoutMenu.addComponent(this.impuestoButton);
	}
	
	private void habilitarEmpresaButton()
	{
		this.empresaButton.setVisible(true);
		this.empresaButton.setEnabled(true);
		
		this.layoutMenu.addComponent(this.empresaButton);
	}
	
	private void habilitarCodigosGeneralizadosButton()
	{
		this.codigosGeneralizados.setVisible(true);
		this.codigosGeneralizados.setEnabled(true);
		
		this.layoutMenu.addComponent(this.codigosGeneralizados);
	}
	
	private void habilitarDocumentosButton(){
		this.documentosButton.setVisible(true);
		this.documentosButton.setEnabled(true);
		
		this.layoutMenu.addComponent(documentosButton);
	}
	
	private void habilitarMonedasButton(){
		this.monedasButton.setVisible(true);
		this.monedasButton.setEnabled(true);
		this.layoutMenu.addComponent(monedasButton);
	}
	
	private void habilitarRubros(){
		this.rubros.setVisible(true);
		this.rubros.setEnabled(true);
		this.layoutMenu.addComponent(rubros);
	}
	
	private void habilitarClientes(){
		this.clientesButton.setVisible(true);
		this.clientesButton.setEnabled(true);
		this.layoutMenu.addComponent(clientesButton);
	}
	
	private void habilitarFuncionarios(){
		this.funcionariosButton.setVisible(true);
		this.funcionariosButton.setEnabled(true);
		this.layoutMenu.addComponent(funcionariosButton);
	}
	
	private void habilitarCotizaciones(){
		this.cotizaciones.setVisible(true);
		this.cotizaciones.setEnabled(true);
		this.layoutMenu.addComponent(cotizaciones);
	}
	
	private void habilitarTipoRubros(){
		this.tipoRubros.setVisible(true);
		this.tipoRubros.setEnabled(true);
		this.layoutMenu.addComponent(tipoRubros);
	}
	
	private void habilitarCuentas(){
		this.cuentas.setVisible(true);
		this.cuentas.setEnabled(true);
		this.layoutMenu.addComponent(cuentas);
	}
	
	private void habilitarBancos(){
		this.bancos.setVisible(true);
		this.bancos.setEnabled(true);
		this.layoutMenu.addComponent(bancos);
	}
	
	private void habilitarProcesos(){
		this.procesos.setVisible(true);
		this.procesos.setEnabled(true);
		this.layoutMenu.addComponent(procesos);
	}
	
	private void habilitarGastos(){
		this.gastos.setVisible(true);
		this.gastos.setEnabled(true);
		this.layoutMenu.addComponent(gastos);
	}
	
	private void habilitarIngresoCobro(){
		this.ingCobro.setVisible(true);
		this.ingCobro.setEnabled(true);
		this.layoutMenu.addComponent(ingCobro);
	}
	
	private void habilitarIngresoEgreso(){
		this.ingEgreso.setVisible(true);
		this.ingEgreso.setEnabled(true);
		this.layoutMenu.addComponent(ingEgreso);
	}
	
	private void habilitarOtroCobro(){
		this.otroCobro.setVisible(true);
		this.otroCobro.setEnabled(true);
		this.layoutMenu.addComponent(otroCobro);
	}
	
	private void habilitarOtroEgreso(){
		this.otroEgreso.setVisible(true);
		this.otroEgreso.setEnabled(true);
		this.layoutMenu.addComponent(otroEgreso);
	}
	
	private void habilitarResumenProceso(){
		this.resumenProc.setVisible(true);
		this.resumenProc.setEnabled(true);
		this.layoutMenu.addComponent(resumenProc);
	}
	
	private void habilitarPeriodo(){
		this.periodo.setVisible(true);
		this.periodo.setEnabled(true);
		this.layoutMenu.addComponent(periodo);
	}
	
	private void habilitarDeposito(){
		this.deposito.setVisible(true);
		this.deposito.setEnabled(true);
		this.layoutMenu.addComponent(deposito);
	}
	
	private void habilitarFactura(){
		this.factura.setVisible(true);
		this.factura.setEnabled(true);
		this.layoutMenu.addComponent(factura);
	}
	
	public PermisosUsuario getPermisosUsuario()
	{
		return this.permisos;
	}
	
	
	
}
