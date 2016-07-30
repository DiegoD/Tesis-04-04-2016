package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.ImpuestoVO;

public class ImpuestosPanelExtended extends ImpuestosPanel{
	
	private ImpuestoViewExtended form; 
	private ArrayList<ImpuestoVO> lstImpuestos; /*Lista con los impuestos*/
	private BeanItemContainer<ImpuestoVO> container;
	private ImpuestoControlador controlador;
	MySub sub = new MySub("65%", "65%");
	
	public ImpuestosPanelExtended() {
		
		controlador = new ImpuestoControlador();
		this.lstImpuestos = new ArrayList<ImpuestoVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		PermisosUsuario permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoImpuesto.addClickListener(click -> {
						
						sub = new MySub("65%", "65%");
						form = new ImpuestoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<ImpuestoVO>(ImpuestoVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstImpuestos = this.getImpuestos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (ImpuestoVO impuestoVO : lstImpuestos) {
			container.addBean(impuestoVO);
		}
		
		
		this.gridImpuestos.setContainerDataSource(container);
		
		gridImpuestos.removeColumn("activo");
		gridImpuestos.removeColumn("fechaMod");
		gridImpuestos.removeColumn("usuarioMod");
		gridImpuestos.removeColumn("operacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridImpuestos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridImpuestos.getSelectedRow() != null){
		    			BeanItem<ImpuestoVO> item = container.getItem(gridImpuestos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new ImpuestoViewExtended(Variables.OPERACION_LECTURA, ImpuestosPanelExtended.this);
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
	 * Obtenemos impuestos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<ImpuestoVO> getImpuestos() throws Exception  {
		
		ArrayList<ImpuestoVO> lstImpuestos = new ArrayList<ImpuestoVO>();

		try {
			
				lstImpuestos = controlador.getImpuestos();
			
		} 
		catch (ObteniendoImpuestosException | InicializandoException | ConexionException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstImpuestos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un impuesto
	 * desde ImpuestoViewExtended
	 *
	 */
	public void actulaizarGrilla(ImpuestoVO impuestoVO)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeImpuestoenLista(impuestoVO.getcodImpuesto()))
		{
			this.actualizarImpuestoenLista(impuestoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstImpuestos.add(impuestoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstImpuestos);
		
		this.gridImpuestos.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un impuestoVO de la lista cuando
	 * se hace una acutalizacion de un Impuesto
	 *
	 */
	private void actualizarImpuestoenLista(ImpuestoVO impuestoVO)
	{
		int i =0;
		boolean salir = false;
		
		ImpuestoVO impuestoEnLista;
		
		while( i < this.lstImpuestos.size() && !salir)
		{
			impuestoEnLista = this.lstImpuestos.get(i);
			
			if(impuestoVO.getcodImpuesto().equals(impuestoEnLista.getcodImpuesto())){
				
				this.lstImpuestos.get(i).copiar(impuestoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el impuestoVO en la lista
	 * de impuestos de la vista
	 *
	 */
	private boolean existeImpuestoenLista(String codImpuesto)
	{
		int i =0;
		boolean esta = false;
		
		ImpuestoVO aux;
		
		while( i < this.lstImpuestos.size() && !esta)
		{
			aux = this.lstImpuestos.get(i);
			if(codImpuesto.equals(aux.getcodImpuesto()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridImpuestos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridImpuestos.getContainerDataSource()
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
		this.btnNuevoImpuesto.setVisible(false);
		this.btnNuevoImpuesto.setEnabled(false);
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
