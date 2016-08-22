package com.vista.Cotizaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.CotizacionControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class CotizacionesPanelExtended extends CotizacionesPanel{

	private CotizacionViewExtended form; 
	private ArrayList<CotizacionVO> lstCotizaciones; /*Lista con las cotizaciones*/
	private BeanItemContainer<CotizacionVO> container;
	private CotizacionControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public CotizacionesPanelExtended() {
		
		controlador = new CotizacionControlador();
		this.lstCotizaciones = new ArrayList<CotizacionVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_COTIZACIONES, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevaCotizacion.addClickListener(click -> {
						
						sub = new MySub("65%", "65%");
						form = new CotizacionViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<CotizacionVO>(CotizacionVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstCotizaciones = this.getCotizaciones();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (CotizacionVO cotizacionVO : lstCotizaciones) {
			container.addBean(cotizacionVO);
		}
		
		
		this.gridCotizaciones.setContainerDataSource(container);
		
		gridCotizaciones.removeColumn("activo");
		gridCotizaciones.removeColumn("fechaMod");
		gridCotizaciones.removeColumn("usuarioMod");
		gridCotizaciones.removeColumn("operacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridCotizaciones.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridCotizaciones.getSelectedRow() != null){
		    			BeanItem<CotizacionVO> item = container.getItem(gridCotizaciones.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new CotizacionViewExtended(Variables.OPERACION_LECTURA, CotizacionesPanelExtended.this);
						sub = new MySub("70%","65%");
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
	 * Obtenemos cotizaciones del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<CotizacionVO> getCotizaciones() throws Exception  {
		
		ArrayList<CotizacionVO> lstCotizaciones = new ArrayList<CotizacionVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_COTIZACIONES,
							VariablesPermisos.OPERACION_LEER);

			
			lstCotizaciones = controlador.getCotizaciones(permisoAux);
			
		} 
		catch (ObteniendoCotizacionesException | InicializandoException | ConexionException| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
			
		return lstCotizaciones;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una cotizaciones
	 * desde CotizacionViewExtended
	 *
	 */
	public void actulaizarGrilla(CotizacionVO cotizacionVO)
	{

		/*Si esta la cotizacion en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeCotizacionenLista(cotizacionVO.getCodMoneda(), cotizacionVO.getFecha()))
		{
			this.actualizarCotizacionenLista(cotizacionVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstCotizaciones.add(cotizacionVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstCotizaciones);
		
		this.gridCotizaciones.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos una cotización de la lista cuando
	 * se hace una acutalizacion de una cotización
	 *
	 */
	private void actualizarCotizacionenLista(CotizacionVO cotizacionVO)
	{
		int i =0;
		boolean salir = false;
		
		CotizacionVO cotizacionEnLista;
		
		while( i < this.lstCotizaciones.size() && !salir)
		{
			cotizacionEnLista = this.lstCotizaciones.get(i);
			
			if(cotizacionVO.getCodMoneda().equals(cotizacionEnLista.getCodMoneda()) &&
					cotizacionVO.getFecha().equals(cotizacionEnLista.getFecha())){
				this.lstCotizaciones.get(i).copiar(cotizacionVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta la empresa en la lista
	 * de empresas de la vista
	 *
	 */
	private boolean existeCotizacionenLista(String cod_moneda, Timestamp fecha)
	{
		int i =0;
		boolean esta = false;
		
		CotizacionVO aux;
		
		while( i < this.lstCotizaciones.size() && !esta)
		{
			aux = this.lstCotizaciones.get(i);
			if(cod_moneda.equals(aux.getCodMoneda()) && fecha.equals(aux.getFecha()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridCotizaciones.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridCotizaciones.getContainerDataSource()
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
		this.btnNuevaCotizacion.setVisible(false);
		this.btnNuevaCotizacion.setEnabled(false);
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
