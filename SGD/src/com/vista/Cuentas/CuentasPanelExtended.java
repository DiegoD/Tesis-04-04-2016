package com.vista.Cuentas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.CuentaControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cuenta.CuentaVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class CuentasPanelExtended extends CuentasPanel{
	
	private CuentaViewExtended form; 
	private ArrayList<CuentaVO> lstCuentas; /*Lista con las cuentas*/
	private BeanItemContainer<CuentaVO> container;
	private CuentaControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");

	public CuentasPanelExtended(){
		
		controlador = new CuentaControlador();
		this.lstCuentas = new ArrayList<CuentaVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
			
        /*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				if(permisoNuevoEditar)
				{
				
					this.btnNuevo.addClickListener(click -> {
						
							sub = new MySub("65%", "70%");
							form = new CuentaViewExtended(Variables.OPERACION_NUEVO, this);
							sub.setModal(true);
							sub.setVista(form);
							
							UI.getCurrent().addWindow(sub);
						
					});
				}else{
					/*Si no tiene permisos ocultamos boton de nuevo*/
					this.deshabilitarBotonNuevo();
				}
					
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
				
				Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
				
			} catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		}else {
			
			/*Si no tiene permisos mostramos mensaje*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		
		this.container = 
				new BeanItemContainer<CuentaVO>(CuentaVO.class);
		
		//Obtenemos lista de grupos del sistema
		this.lstCuentas = this.getCuentas();
		
		for (CuentaVO cuentaVO : lstCuentas) {
			container.addBean(cuentaVO);
		}
		
		
		gridCuentas.setContainerDataSource(container);
		
		
		//Quitamos las columnas de la grilla de auditoria
		this.ocultarColumnasGrilla();
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		gridCuentas.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridCuentas.getSelectedRow() != null){
		    			BeanItem<CuentaVO> item = container.getItem(gridCuentas.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						form = new CuentaViewExtended(Variables.OPERACION_LECTURA, CuentasPanelExtended.this);
						//form.fieldGroup.setItemDataSource(item);
						sub = new MySub("65%","70%");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						form.setLstFormularios(item.getBean().getLstRubros());
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
			    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    }
		      
		    }
		});
		
	}
	
	
	
	/**
	 * Obtenemos cuentas del sistema
	 *
	 */
	private ArrayList<CuentaVO> getCuentas(){
		
		ArrayList<CuentaVO> lstCuentas = new ArrayList<CuentaVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_CUENTAS,
							VariablesPermisos.OPERACION_LEER);

			
			lstCuentas = controlador.getCuentas(permisoAux);

		} catch (ObteniendoCuentasException | InicializandoException | ConexionException | ErrorInesperadoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoRubrosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstCuentas;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una cuenta
	 * desde GrupoViewExtended
	 *
	 */
	public void actulaizarGrilla(CuentaVO cuentaVO)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeCuentaenLista(cuentaVO.getCodCuenta()))
		{
			this.actualizarCuentaenLista(cuentaVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstCuentas.add(cuentaVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstCuentas);
		
		this.gridCuentas.setContainerDataSource(container);

	}
	
	
	/**
	 * Modificamos un cuentaVO de la lista cuando
	 * se hace una acutalizacion de una cuenta
	 *
	 */
	private void actualizarCuentaenLista(CuentaVO cuentaVO)
	{
		int i =0;
		boolean salir = false;
		
		CuentaVO cuentaEnLista;
		
		while( i < this.lstCuentas.size() && !salir)
		{
			cuentaEnLista = this.lstCuentas.get(i);
			if(cuentaVO.getCodCuenta().equals(cuentaEnLista.getCodCuenta()))
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstCuentas.get(i).copiar(cuentaVO);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el cuentaVO en la lista
	 * de cuentas de la vista
	 *
	 */
	private boolean existeCuentaenLista(String codCuenta)
	{
		int i =0;
		boolean esta = false;
		
		CuentaVO aux;
		
		while( i < this.lstCuentas.size() && !esta)
		{
			aux = this.lstCuentas.get(i);
			if(codCuenta.equals(aux.getCodCuenta()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridCuentas.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridCuentas.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    /*Agregar field para usar el filtro*/
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				     /*Actualizar el filtro cuando este tenga un cambio en texto*/
				    filterField.addTextChangeListener(change -> {
				        
				    	/*No se pueden modificar los filtros,
				    	 * necesitamos reemplazarlos*/
				    	this.container.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.container.addContainerFilter(
				                new SimpleStringFilter(pid,
				                    change.getText(), true, false));
				    });

				    cell.setComponent(filterField);
				}
			}
			
		}catch(Exception e)
		{
			 System.out.println(e.getStackTrace());
		}
	}
	
	private void deshabilitarBotonNuevo()
	{
		this.btnNuevo.setVisible(false);
		this.btnNuevo.setEnabled(false);
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	public void mostrarMensaje(String msj)
	{
		Mensajes.mostrarMensajeError(msj);
	}
	
	private void ocultarColumnasGrilla()
	{
		gridCuentas.getColumn("fechaMod").setHidden(true);
		gridCuentas.getColumn("usuarioMod").setHidden(true);
		gridCuentas.getColumn("operacion").setHidden(true);
		gridCuentas.getColumn("activo").setHidden(true);
	}
}
