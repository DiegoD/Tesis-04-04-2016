package com.vista;

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
import com.vista.Rubros.RubroViewExtended;

public class BusquedaViewExtended extends BusquedaView{
	
	private ArrayList<Object> lst;
	BeanItemContainer<ImpuestoVO> containerImpuesto;
	Object seleccionado;
	IBusqueda main;
	
	public BusquedaViewExtended(IBusqueda main, Object obj){
		
		this.main = main;
		if(obj instanceof ImpuestoVO){
			this.containerImpuesto = new BeanItemContainer<ImpuestoVO>(ImpuestoVO.class);
			this.lblNombre.setValue("Impuestos");
			this.seleccionado = new ImpuestoVO();
			
		}
		
		gridImpuestos.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		if(seleccionado instanceof ImpuestoVO){
			    		if(gridImpuestos.getSelectedRow() != null){
			    			
			    			BeanItem<ImpuestoVO> item = containerImpuesto.getItem(gridImpuestos.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		
		    		
		    		
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
	}

	public void inicializarGrilla(ArrayList<Object> lst) throws ObteniendoImpuestosException, ConexionException, InicializandoException{
	
		if(seleccionado instanceof ImpuestoVO){
			
			ArrayList<ImpuestoVO> lstImp = new ArrayList<>();
			
			ImpuestoVO i;
			for (Object o : lst) {
				
				i = (ImpuestoVO) o;
				lstImp.add(i);
			}
			
			this.containerImpuesto.addAll(lstImp);
			this.gridImpuestos.setContainerDataSource(containerImpuesto);
			
			gridImpuestos.removeColumn("activo");
			gridImpuestos.removeColumn("fechaMod");
			gridImpuestos.removeColumn("usuarioMod");
			gridImpuestos.removeColumn("operacion");
		}
		
		
		
		
	}
	
	private void filtroGrilla()
	{
		try
		{
			 
			if(seleccionado instanceof ImpuestoVO)
				containerImpuesto.addContainerProperty(ImpuestoVO.class, ImpuestoVO.class, ImpuestoVO.class);
			
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
