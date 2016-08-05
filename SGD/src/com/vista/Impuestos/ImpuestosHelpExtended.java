package com.vista.Impuestos;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.TextField;
import com.valueObject.ImpuestoVO;
import com.vista.Mensajes;
import com.vista.Variables;
import com.vista.Rubros.RubroViewExtended;

public class ImpuestosHelpExtended extends ImpuestosHelp{
	
	private BeanFieldGroup<ImpuestoVO> fieldGroup;
	private ImpuestoControlador controlador;
	private ArrayList<ImpuestoVO> lstImpuestos;
	BeanItemContainer<ImpuestoVO> containerImpuesto;
	ImpuestoVO impuestoSeleccionado;
	
	public ImpuestosHelpExtended(RubroViewExtended main){
		
		impuestoSeleccionado = new ImpuestoVO();
		
		try {
			this.inicializarForm();
			
			gridImpuestos.addSelectionListener(new SelectionListener() 
			{
				
			    @Override
			    public void select(SelectionEvent event) 
			    {
			       
			    	try
			    	{
			    		if(gridImpuestos.getSelectedRow() != null){
			    			
			    			BeanItem<ImpuestoVO> item = containerImpuesto.getItem(gridImpuestos.getSelectedRow());
					    	impuestoSeleccionado = item.getBean(); 
					    	main.setImpuesto(impuestoSeleccionado);		
					    	main.cerrarVentana();
					    }
			    	}
			    	catch(Exception e){
			    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    	}
			      
			    }
			});
		} 
		catch (ObteniendoImpuestosException | ConexionException | InicializandoException e) {
			Mensajes.mostrarMensajeError(e.getMessage());
		}
	}

	private void inicializarForm() throws ObteniendoImpuestosException, ConexionException, InicializandoException{
		
		this.controlador = new ImpuestoControlador();
		this.fieldGroup =  new BeanFieldGroup<ImpuestoVO>(ImpuestoVO.class);
		
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		this.containerImpuesto = 
				new BeanItemContainer<ImpuestoVO>(ImpuestoVO.class);
	
		this.lstImpuestos = controlador.getImpuestos();
		
		if(this.lstImpuestos != null )
		{
			for(ImpuestoVO impuestoVO: this.lstImpuestos){
				containerImpuesto.addBean(impuestoVO);
			}
		}
		
		this.gridImpuestos.setContainerDataSource(containerImpuesto);
		gridImpuestos.removeColumn("activo");
		gridImpuestos.removeColumn("fechaMod");
		gridImpuestos.removeColumn("usuarioMod");
		gridImpuestos.removeColumn("operacion");
		
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
				    // Have an input field to use for filter
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				    // Update filter When the filter input is changed
				    filterField.addTextChangeListener(change -> {
				        // Can't modify filters so need to replace
				    	this.containerImpuesto.removeContainerFilters(pid);
		
				        // (Re)create the filter if necessary
				        if (! change.getText().isEmpty())
				        	this.containerImpuesto.addContainerFilter(
				                new SimpleStringFilter(pid,
				                    change.getText(), true, false));
				    });

				    cell.setComponent(filterField);
				}
			}
			
		}
		catch(Exception e)	{
			 System.out.println(e.getStackTrace());
		}
	}
}
