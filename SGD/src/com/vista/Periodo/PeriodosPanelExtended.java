package com.vista.Periodo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.PeriodoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Periodo.ObteniendoPeriodosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Periodo.PeriodoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class PeriodosPanelExtended extends PeriodosPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PeriodoViewExtended form; 
	private ArrayList<PeriodoVO> lstPeriodos; /*Lista con las empresas*/
	private BeanItemContainer<PeriodoVO> container;
	private PeriodoControlador controlador;
	MySub sub = new MySub("65%", "65%");
	PermisosUsuario permisos;
	
	public PeriodosPanelExtended(){
		
		controlador = new PeriodoControlador();
		this.lstPeriodos = new ArrayList<PeriodoVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PERIODO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PERIODO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoPeriodo.addClickListener(click -> {
						
						sub = new MySub("45%","30%");
						form = new PeriodoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<PeriodoVO>(PeriodoVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstPeriodos = this.getPeriodos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (PeriodoVO periodoVO: lstPeriodos) {
			container.addBean(periodoVO);
		}
		
		
		this.gridPeriodos.setContainerDataSource(container);
		
		gridPeriodos.removeColumn("fechaMod");
		gridPeriodos.removeColumn("usuarioMod");
		gridPeriodos.removeColumn("operacion");
		gridPeriodos.getColumn("anio").setHeaderCaption("Año");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridPeriodos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridPeriodos.getSelectedRow() != null){
		    			BeanItem<PeriodoVO> item = container.getItem(gridPeriodos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new PeriodoViewExtended(Variables.OPERACION_LECTURA, PeriodosPanelExtended.this);
				    	sub = new MySub("45%","30%");
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
	 * Obtenemos períodos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<PeriodoVO> getPeriodos() throws Exception  {
		
		ArrayList<PeriodoVO> lstPeriodos = new ArrayList<PeriodoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PERIODO,
							VariablesPermisos.OPERACION_LEER);
			
			lstPeriodos = controlador.getPeriodos(permisoAux);
			
		} 
		catch (ObteniendoPeriodosException | InicializandoException | ConexionException
				| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstPeriodos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una moneda
	 * desde ImpuestoViewExtended
	 *
	 */
	public void actulaizarGrilla(PeriodoVO periodoVO)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existePeriodoenLista(periodoVO.getMes()))
		{
			this.actualizarPeriodoenLista(periodoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstPeriodos.add(periodoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstPeriodos);
		
		this.gridPeriodos.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un periodoVO de la lista cuando
	 * se hace una acutalizacion de un periodo
	 *
	 */
	private void actualizarPeriodoenLista(PeriodoVO periodoVO)
	{
		int i =0;
		boolean salir = false;
		
		PeriodoVO periodoEnLista;
		
		while( i < this.lstPeriodos.size() && !salir)
		{
			periodoEnLista = this.lstPeriodos.get(i);
			
			if(periodoVO.getMes().equals(periodoEnLista.getMes())){
				
				this.lstPeriodos.get(i).copiar(periodoVO);
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
	private boolean existePeriodoenLista(String mes)
	{
		int i =0;
		boolean esta = false;
		
		PeriodoVO aux;
		
		while( i < this.lstPeriodos.size() && !esta)
		{
			aux = this.lstPeriodos.get(i);
			if(mes.equals(aux.getMes()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridPeriodos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridPeriodos.getContainerDataSource()
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
		this.btnNuevoPeriodo.setVisible(false);
		this.btnNuevoPeriodo.setEnabled(false);
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
