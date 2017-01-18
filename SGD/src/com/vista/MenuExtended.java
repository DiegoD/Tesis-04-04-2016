package com.vista;

import java.util.ArrayList;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.FormularioVO;
import com.vista.Documentos.DocumentosPanelExtended;
import com.vista.Egreso.IngresoEgresoPanelExtended;
import com.vista.EgresoOtro.IngresoEgresoOtroPanelExtended;
import com.vista.Bancos.BancosPanelExtended;
import com.vista.Clientes.ClientesPanelExtended;
import com.vista.CodigosGeneralizados.CodigosGeneralizadosPanelExtended;
import com.vista.Conciliaciones.ConciliacionesPanelExtended;
import com.vista.Cotizaciones.CotizacionesPanelExtended;
import com.vista.Cuentas.CuentasPanelExtended;
import com.vista.Deposito.DepositoPanelExtended;
import com.vista.Deposito.DepositoViewExtended;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Factura.FacturaPanelExtended;
import com.vista.Funcionarios.FuncionariosPanelExtended;
import com.vista.Gastos.GastosPanelExtended;
import com.vista.Grupos.GruposPanelExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;
import com.vista.IngresoCobro.IngresoCobroPanelExtended;
import com.vista.IngresoOtro.IngresoOtroPanelExtended;
import com.vista.Login.LoginExtended;
import com.vista.Monedas.MonedasPanelExtended;
import com.vista.NotaCredito.NotaCreditoPanelExtended;
import com.vista.Periodo.PeriodosPanelExtended;
import com.vista.Procesos.ProcesosPanelExtended;
import com.vista.Recibo.ReciboPanelExtended;
import com.vista.RepVis.VistaSaDocum;
import com.vista.RepVis.VistaSaDocumExtended;
import com.vista.RepVis2.VistaSaCuentasExtended;
import com.vista.Reportes.ChequesPendDepositar.ChequesDepositarViewExtended;
import com.vista.Reportes.ChequesxCliente.RepChequesxClienteViewExtended;
import com.vista.Reportes.ChequesxCliente.ReportePanelChequeExtended;
import com.vista.Reportes.EstadoCuenta.RepEstadoCuentaViewExtended;
import com.vista.Reportes.EstadoCuentaTotales.RepEstadoCuentaTotalesViewExtended;
import com.vista.Reportes.GastosPendCobro.GtosPendCobroxClienteViewExtended;
import com.vista.Reportes.IVA.RepIvaViewExtended;
import com.vista.Reportes.RepCuentaRubro.RepMovCtaRubroExtended;
import com.vista.ResumenProceso.ResProcesosPanelExtended;
import com.vista.Rubros.RubrosPanelExtended;
import com.vista.TipoRubro.TipoRubrosPanelExtended;
import com.vista.Usuarios.UsuarioViewExtended;
import com.vista.Usuarios.UsuariosPanelExtend;

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
	
	//bground.addStyleName("backColorGrey");
	
	private void inicializarMenu(){
		
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
		
		this.saldoDocumentos.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				VistaSaDocumExtended c = new VistaSaDocumExtended();
				c.setSizeFull();
				this.content.setSizeFull();
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
		});
		
		this.saldoCuentas.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				VistaSaCuentasExtended c = new VistaSaCuentasExtended();
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
		
		this.recibo.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ReciboPanelExtended u = new ReciboPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.notaCredito.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				NotaCreditoPanelExtended u = new NotaCreditoPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.conciliacion.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				ConciliacionesPanelExtended u = new ConciliacionesPanelExtended(); 
				this.content.addComponent(u);
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		
		this.btnRepCheque.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
				RepChequesxClienteViewExtended form = new RepChequesxClienteViewExtended();
				
				sub = new MySub("50%","50%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnRepGastosPendCobro.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			try {
				
		
				GtosPendCobroxClienteViewExtended form = new GtosPendCobroxClienteViewExtended();

				
				sub = new MySub("40%","50%");
				

				sub.setModal(true);


				

				sub.setVista(form);

			
				
				UI.getCurrent().addWindow(sub);
				
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnCheqADepositar.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			
			try {
				
				
				ChequesDepositarViewExtended form = new ChequesDepositarViewExtended();
				
				sub = new MySub("40%","50%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnRepIva.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			
			try {
				
				
				RepIvaViewExtended form = new RepIvaViewExtended();
				
				sub = new MySub("34%","45%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnEstadoCuenta.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			
			try {
				
				
				RepEstadoCuentaViewExtended form = new RepEstadoCuentaViewExtended();
				
				sub = new MySub("50%","50%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnEstadoCuentaTotales.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			
			try {
				
				
				RepEstadoCuentaTotalesViewExtended form = new RepEstadoCuentaTotalesViewExtended();
				
				sub = new MySub("34%","45%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnMovPorCuenta.addClickListener(click -> {
			
			setSizeFull();
			
			this.content.removeAllComponents();
			
			try {
				
				
				RepMovCtaRubroExtended form = new RepMovCtaRubroExtended();
				
				sub = new MySub("50%","45%");
				
				sub.setModal(true);
				
				sub.setVista(form);
				
				UI.getCurrent().addWindow(sub);
				
				
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
		this.setearOpcionesMenuFacturacion();
		this.setearOpcionesMenuCobros();
		this.setearOpcionesMenuProcesos();
		this.setearOpcionesReportes();
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
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RESUMEN_PROCESO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_PERIODO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_CONCILIACION) ||
				formularioVO.getCodigo().equals("SaldoDocumentos") ||
				formularioVO.getCodigo().equals("SaldoCuentas") ||
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
					break;
						
					case VariablesPermisos.FORMULARIO_CLIENTES:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CLIENTES, VariablesPermisos.OPERACION_LEER)){
							this.habilitarClientes();
							this.layoutMenu.addComponent(this.clientesButton);
						}
					break;
						
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
					
					case VariablesPermisos.FORMULARIO_CONCILIACION:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_LEER)){
							this.habilitarConciliacion();
							this.layoutMenu.addComponent(this.conciliacion);
						}
					break;
					
					case "SaldoDocumentos" :
						
							this.habilitarSaldoDocumentosButton(); 
							
							this.layoutMenu.addComponent(this.saldoDocumentos);
							
							
					break;
					
					case "SaldoCuentas" :
							this.habilitarSaldoDocumentosButton(); 
							
							this.layoutMenu.addComponent(this.saldoCuentas);
							
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
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Mantenimientos
	 * 
	 */
	private void setearOpcionesReportes()
	{
		ArrayList<FormularioVO> lstFormsReportes = new ArrayList<FormularioVO>();
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REPORTES) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_CHEQUE_CLIENTES)||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_GTOS_PENDIENTES_CLIENTES) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_IVA) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA_TOTALES) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_POR_CUENTA) ||
			   formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_REP_CHEQUES_PENDIENTES_DEPOSITAR))
				
			{
				lstFormsReportes.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsReportes.size()> 0)
		{
			//TODO
			//this.tabAdministracion = new VerticalLayout();
			//this.tabAdministracion.setMargin(true);
			
			this.lbReportes.setVisible(true);
			this.layoutMenu.addComponent(this.lbReportes);
			
			for (FormularioVO formularioVO : lstFormsReportes) {
				
				switch(formularioVO.getCodigo())
				{
					case VariablesPermisos.FORMULARIO_REP_CHEQUE_CLIENTES : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_CHEQUE_CLIENTES, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteChequeCliente();
							this.layoutMenu.addComponent(this.btnRepCheque);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_GTOS_PENDIENTES_CLIENTES : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_GTOS_PENDIENTES_CLIENTES, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteGtosPendCliente();
							this.layoutMenu.addComponent(this.btnRepGastosPendCobro);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_CHEQUES_PENDIENTES_DEPOSITAR : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_CHEQUES_PENDIENTES_DEPOSITAR, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteChequesPendDeposito();
							this.layoutMenu.addComponent(this.btnCheqADepositar);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_IVA : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_IVA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteRepIva();
							this.layoutMenu.addComponent(this.btnRepIva);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteEstadoCuenta();
							this.layoutMenu.addComponent(this.btnEstadoCuenta);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA_TOTALES : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_ESTADO_CUENTA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteEstadoCuentaTotales(); 
							this.layoutMenu.addComponent(this.btnEstadoCuentaTotales); 
						}
					break;
					
					case VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_POR_CUENTA : 
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_POR_CUENTA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarReporteMovimientosxCuenta(); 
							this.layoutMenu.addComponent(this.btnMovPorCuenta); 
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
		
		this.recibo.setVisible(false);
		this.recibo.setEnabled(false);
		
		this.notaCredito.setVisible(false);
		this.notaCredito.setEnabled(false);
		
		this.conciliacion.setVisible(false);
		this.conciliacion.setEnabled(false);
		
		this.btnRepGastosPendCobro.setVisible(false);
		this.btnRepGastosPendCobro.setEnabled(false);
		
		this.btnRepCheque.setVisible(false);
		this.btnRepCheque.setEnabled(false);
		
		this.btnCheqADepositar.setVisible(false);
		this.btnCheqADepositar.setEnabled(false);
		
		this.btnRepIva.setVisible(false);
		this.btnRepIva.setEnabled(false);
		
		this.btnEstadoCuenta.setVisible(false);
		this.btnEstadoCuenta.setEnabled(false);
		
		this.btnEstadoCuentaTotales.setVisible(false);
		this.btnEstadoCuentaTotales.setEnabled(false);
		
		this.btnMovPorCuenta.setVisible(false);
		this.btnMovPorCuenta.setEnabled(false);
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
	
	private void habilitarNotaCredito(){
		this.notaCredito.setVisible(true);
		this.notaCredito.setEnabled(true);
		this.layoutMenu.addComponent(notaCredito);
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
	
	private void habilitarRecibo(){
		this.recibo.setVisible(true);
		this.recibo.setEnabled(true);
		this.layoutMenu.addComponent(recibo);
	}
	
	private void habilitarConciliacion(){
		this.conciliacion.setVisible(true);
		this.conciliacion.setEnabled(true);
		this.layoutMenu.addComponent(conciliacion);
	}
	
	public PermisosUsuario getPermisosUsuario()
	{
		return this.permisos;
	}
	
	private void habilitarSaldoCuentaButton()
	{
		this.saldoCuentas.setVisible(true);
		this.saldoCuentas.setEnabled(true);
		
		this.layoutMenu.addComponent(this.impuestoButton);
	}
	
	private void habilitarSaldoDocumentosButton()
	{
		this.saldoDocumentos.setVisible(true);
		this.saldoDocumentos.setEnabled(true);
		
		this.layoutMenu.addComponent(this.impuestoButton);
	}
	
	private void habilitarReporteChequeCliente()
	{
		this.btnRepCheque.setVisible(true);
		this.btnRepCheque.setEnabled(true);
	}
	
	private void habilitarReporteGtosPendCliente()
	{
		this.btnRepGastosPendCobro.setVisible(true);
		this.btnRepGastosPendCobro.setEnabled(true);
	}
	
	private void habilitarReporteChequesPendDeposito()
	{
		this.btnCheqADepositar.setVisible(true);
		this.btnCheqADepositar.setEnabled(true);
	}
	
	private void habilitarReporteRepIva()
	{
		this.btnRepIva.setVisible(true);
		this.btnRepIva.setEnabled(true);
	}
	
	private void habilitarReporteEstadoCuenta()
	{
		this.btnEstadoCuenta.setVisible(true);
		this.btnEstadoCuenta.setEnabled(true);
	}
	
	private void habilitarReporteEstadoCuentaTotales()
	{
		this.btnEstadoCuentaTotales.setVisible(true);
		this.btnEstadoCuentaTotales.setEnabled(true);
	}
	
	private void habilitarReporteMovimientosxCuenta()
	{
		this.btnMovPorCuenta.setVisible(true);
		this.btnMovPorCuenta.setEnabled(true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Cobros
	 * 
	 */
	private void setearOpcionesMenuCobros()
	{
		ArrayList<FormularioVO> lstFormsMenuCobros = new ArrayList<FormularioVO>();
		
	
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(	formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_COBRO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_COBRO_OTRO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_EGRESO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_GASTOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_DEPOSITO) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_INGRESO_EGRESO_OTRO))
				
			{
				
				lstFormsMenuCobros.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuCobros.size()> 0)
		{

			this.layoutMenu = new VerticalLayout();
			//this.tabMantenimientos.setMargin(true);
			
			this.lbCobros.setVisible(true);
			this.layoutMenu.addComponent(this.lbCobros);
			
			
			for (FormularioVO formularioVO : lstFormsMenuCobros) {
				
				switch(formularioVO.getCodigo())
				{
										
			
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
					


					case VariablesPermisos.FORMULARIO_GASTOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarGastos();
							this.layoutMenu.addComponent(this.gastos);
						}
						
					break;


					case VariablesPermisos.FORMULARIO_DEPOSITO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarDeposito();
							this.layoutMenu.addComponent(this.deposito);
						}
					break;
					
					
				}
				
			}
			
			this.menuItems.addComponent(this.layoutMenu);
			
		}
	}
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Cobros
	 * 
	 */
	private void setearOpcionesMenuFacturacion()
	{
		ArrayList<FormularioVO> lstFormsMenuFacturacion = new ArrayList<FormularioVO>();
		
	
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_FACTURA) ||
					formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RECIBO) ||
					formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_NOTA_CREDITO))
				
			{
				
				lstFormsMenuFacturacion.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuFacturacion.size()> 0)
		{

			this.layoutMenu = new VerticalLayout();
			//this.tabMantenimientos.setMargin(true);
			
			this.lbFacturacion.setVisible(true);
			this.layoutMenu.addComponent(this.lbFacturacion);
			
			
			for (FormularioVO formularioVO : lstFormsMenuFacturacion) {
				
				switch(formularioVO.getCodigo())
				{
										
					case VariablesPermisos.FORMULARIO_NOTA_CREDITO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_NOTA_CREDITO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarNotaCredito();
							this.layoutMenu.addComponent(this.ingCobro);
						}
					break;
	
	
					
					case VariablesPermisos.FORMULARIO_FACTURA:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FACTURA, VariablesPermisos.OPERACION_LEER)){
							this.habilitarFactura();
							this.layoutMenu.addComponent(this.factura);
						}
					break;
					
					case VariablesPermisos.FORMULARIO_RECIBO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RECIBO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarRecibo();
							this.layoutMenu.addComponent(this.recibo);
						}
					break;
					
				}
				
			}
			
			this.menuItems.addComponent(this.layoutMenu);
			
		}
	}
	
	
	/**
	 * Vemos los permisos del usuario para los mantenimientos
	 * y lo agregamos al TAB de Mantenimientos
	 * 
	 */
	private void setearOpcionesMenuProcesos()
	{
		ArrayList<FormularioVO> lstFormsMenuProceso = new ArrayList<FormularioVO>();
		
	
		
		/*Buscamos los Formulairos correspondientes a este TAB*/
		for (FormularioVO formularioVO : this.permisos.getLstPermisos().values()) {
			
			if(formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_PROCESOS) ||
				formularioVO.getCodigo().equals(VariablesPermisos.FORMULARIO_RESUMEN_PROCESO))
			{
				lstFormsMenuProceso.add(formularioVO);
			}
			
		}
		
		/*Si hay formularios para el tab*/
		if(lstFormsMenuProceso.size()> 0)
		{

			this.layoutMenu = new VerticalLayout();
			//this.tabMantenimientos.setMargin(true);
			
			this.lbProcesos.setVisible(true);
			this.layoutMenu.addComponent(this.lbProcesos);
			
			
			for (FormularioVO formularioVO : lstFormsMenuProceso) {
				
				switch(formularioVO.getCodigo())
				{
										
					case VariablesPermisos.FORMULARIO_PROCESOS:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_LEER)){
							this.habilitarProcesos();
							this.layoutMenu.addComponent(this.procesos);
						}
						
					break;
					
					
					case VariablesPermisos.FORMULARIO_RESUMEN_PROCESO:
						if(this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RESUMEN_PROCESO, VariablesPermisos.OPERACION_LEER)){
							this.habilitarResumenProceso();
							this.layoutMenu.addComponent(this.resumenProc);
						}
						
					break;
					
				}
				
			}
			
			this.menuItems.addComponent(this.layoutMenu);
			
		}
	}
	
	
}
