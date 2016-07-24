package com.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.controladores.GrupoControlador;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioVO;


public class GrupoViewAgregarFormularioExtended extends GrupoViewAgregarFormulario{
	
	
	GrupoViewExtended mainView;
	BeanItemContainer<FormularioVO> container;
	
	/*Pasamos el controlador por referencia, para poder setearle al padre GrupoViewWxtended
	 * los formularios seleccionados*/
	@SuppressWarnings("unused")
	public GrupoViewAgregarFormularioExtended(GrupoViewExtended main)
	{
		this.mainView = main;
		this.lstFormularios.setSelectionMode(SelectionMode.MULTI);
		

		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
	
			BeanItemContainer<FormularioVO> selec;
			
			try
			{
				ArrayList<FormularioVO> lstSeleccionados = new ArrayList<FormularioVO>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Grupos para agregarlos*/
				Collection<Object> col= this.lstFormularios.getSelectedRows();
				
				FormularioVO aux;
				for (Object object : col) {
					aux = (FormularioVO)object;
					
										
					lstSeleccionados.add(aux);
					
				}

				mainView.agregarFormulariosSeleccionados(lstSeleccionados);
				mainView.cerrarVentana();
				
				//((Window) this.getParent()).removeFromParent(this);

			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}

		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
	}
	
	
	
	/**
	 *Dado una ArrayList de FormularioVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaForms(ArrayList<FormularioVO> lstFrms)
	{
		//this.lstFormularios.addAttachListener(listener);
		
			
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
				
		if(lstFrms != null)
		{
			for (FormularioVO formVO : lstFrms) {
				container.addBean(formVO);
			}
		}
				
		this.lstFormularios.setContainerDataSource(container);
		/*Agregamos los filtros de la grilla*/
		this.filtroGrilla();
		
	}
	
	
	/**
	 *Seteamos los filtros de la grilla
	 *
	 */
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = this.lstFormularios.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: this.lstFormularios.getContainerDataSource()
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
}
