package com.vista.Monedas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.ImpuestoControlador;
import com.controladores.MonedaControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.EmpresaVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class MonedasPanelExtended extends MonedasPanel{
	
	private MonedaViewExtended form; 
	private ArrayList<MonedaVO> lstMonedas; /*Lista con las empresas*/
	private BeanItemContainer<MonedaVO> container;
	private MonedaControlador controlador;
	MySub sub = new MySub("65%", "65%");
	PermisosUsuario permisos;
	
	public MonedasPanelExtended(){
		
		controlador = new MonedaControlador();
		this.lstMonedas = new ArrayList<MonedaVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_MONEDAS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_MONEDAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevaMoneda.addClickListener(click -> {
						
						sub = new MySub("60%","37%");
						form = new MonedaViewExtended(Variables.OPERACION_NUEVO, this);
						sub.setModal(true);
						sub.setVista(form);
						
						UI.getCurrent().addWindow(sub);
						
					});
				}
				else{
					/*Si no tiene permisos ocultamos boton de nuevo*/
					this.deshabilitarBotonNuevo();
				}
					
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
				
				Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
				
			} 
			catch(Exception e){
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		}
		else {
			
			/*Si no tiene permisos mostramos mensaje*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	

	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		
		this.container = 
				new BeanItemContainer<MonedaVO>(MonedaVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstMonedas = this.getMonedas();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (MonedaVO monedaVO: lstMonedas) {
			container.addBean(monedaVO);
		}
		
		
		this.gridMonedas.setContainerDataSource(container);
		
		gridMonedas.removeColumn("activo");
		gridMonedas.removeColumn("fechaMod");
		gridMonedas.removeColumn("usuarioMod");
		gridMonedas.removeColumn("operacion");
		gridMonedas.removeColumn("aceptaCotizacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridMonedas.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridMonedas.getSelectedRow() != null){
		    			BeanItem<MonedaVO> item = container.getItem(gridMonedas.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new MonedaViewExtended(Variables.OPERACION_LECTURA, MonedasPanelExtended.this);
				    	sub = new MySub("60%","37%");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
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
	 * Obtenemos monedas del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<MonedaVO> getMonedas() throws Exception  {
		
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_MONEDAS,
							VariablesPermisos.OPERACION_LEER);
			
			lstMonedas = controlador.getMonedas(permisoAux);
			
		} 
		catch (ObteniendoMonedaException | InicializandoException | ConexionException
				| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstMonedas;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una moneda
	 * desde ImpuestoViewExtended
	 *
	 */
	public void actulaizarGrilla(MonedaVO monedaVO)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeMonedaenLista(monedaVO.getCodMoneda()))
		{
			this.actualizarMonedaenLista(monedaVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstMonedas.add(monedaVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstMonedas);
		
		this.gridMonedas.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un monedaVO de la lista cuando
	 * se hace una acutalizacion de una moneda
	 *
	 */
	private void actualizarMonedaenLista(MonedaVO monedaVO)
	{
		int i =0;
		boolean salir = false;
		
		MonedaVO monedaEnLista;
		
		while( i < this.lstMonedas.size() && !salir)
		{
			monedaEnLista = this.lstMonedas.get(i);
			
			if(monedaVO.getCodMoneda().equals(monedaEnLista.getCodMoneda())){
				
				this.lstMonedas.get(i).copiar(monedaVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el monedaVO en la lista
	 * de monedas de la vista
	 *
	 */
	private boolean existeMonedaenLista(String cod_moneda)
	{
		int i =0;
		boolean esta = false;
		
		MonedaVO aux;
		
		while( i < this.lstMonedas.size() && !esta)
		{
			aux = this.lstMonedas.get(i);
			if(cod_moneda.equals(aux.getCodMoneda()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridMonedas.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridMonedas.getContainerDataSource()
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
		this.btnNuevaMoneda.setVisible(false);
		this.btnNuevaMoneda.setEnabled(false);
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	public void mostrarMensaje(String msj)
	{
		Mensajes.mostrarMensajeError(msj);
	}
}
