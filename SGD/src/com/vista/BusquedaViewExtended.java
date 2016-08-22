package com.vista;

import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.DocumDGIVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.vista.Rubros.RubroViewExtended;

public class BusquedaViewExtended extends BusquedaView{
	
	private ArrayList<Object> lst;
	BeanItemContainer<ImpuestoVO> containerImpuesto;
	BeanItemContainer<DocumDGIVO> containerDocumentosDgi;
	BeanItemContainer<CodigoGeneralizadoVO> containerCodigoGeneralizado;
	BeanItemContainer<MonedaVO> containerMoneda;
	Object seleccionado;
	IBusqueda main;
	
	public BusquedaViewExtended(IBusqueda main, Object obj){
		
		this.main = main;
		if(obj instanceof ImpuestoVO){
			this.containerImpuesto = new BeanItemContainer<ImpuestoVO>(ImpuestoVO.class);
			this.lblNombre.setValue("Impuestos");
			this.seleccionado = new ImpuestoVO();
			
		}
		else if(obj instanceof DocumDGIVO){
			
			this.containerDocumentosDgi = new BeanItemContainer<DocumDGIVO>(DocumDGIVO.class);
			this.lblNombre.setValue("Documentos");
			this.seleccionado = new DocumDGIVO();
		}
		else if(obj instanceof CodigoGeneralizadoVO){
	
			this.containerCodigoGeneralizado = new BeanItemContainer<CodigoGeneralizadoVO>(CodigoGeneralizadoVO.class);
			this.lblNombre.setValue("Tipo Rubro");
			this.seleccionado = new CodigoGeneralizadoVO();
		}
		
		else if(obj instanceof MonedaVO){
			
			this.containerMoneda = new BeanItemContainer<MonedaVO>(MonedaVO.class);
			this.lblNombre.setValue("Monedas");
			this.seleccionado = new MonedaVO();
		}
		
		grid.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		if(seleccionado instanceof ImpuestoVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<ImpuestoVO> item = containerImpuesto.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		} 
		    		else if(seleccionado instanceof DocumDGIVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<DocumDGIVO> item = containerDocumentosDgi.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof CodigoGeneralizadoVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<CodigoGeneralizadoVO> item = containerCodigoGeneralizado.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof MonedaVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<MonedaVO> item = containerMoneda.getItem(grid.getSelectedRow());
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
			this.grid.setContainerDataSource(containerImpuesto);
			
			grid.removeColumn("activo");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
			this.arreglarGrilla();
		}
		
		if(seleccionado instanceof DocumDGIVO){
			
			ArrayList<DocumDGIVO> lstDoc = new ArrayList<>();
			
			DocumDGIVO i;
			for (Object o : lst) {
				
				i = (DocumDGIVO) o;
				lstDoc.add(i);
			}
			
			this.containerDocumentosDgi.addAll(lstDoc);
			this.grid.setContainerDataSource(containerDocumentosDgi);
			
			grid.removeColumn("activo");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		if(seleccionado instanceof CodigoGeneralizadoVO){
			
			ArrayList<CodigoGeneralizadoVO> lstDoc = new ArrayList<>();
			
			CodigoGeneralizadoVO i;
			for (Object o : lst) {
				
				i = (CodigoGeneralizadoVO) o;
				lstDoc.add(i);
			}
			
			this.containerCodigoGeneralizado.addAll(lstDoc);
			this.grid.setContainerDataSource(containerCodigoGeneralizado);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		if(seleccionado instanceof MonedaVO){
			
			ArrayList<MonedaVO> lstDoc = new ArrayList<>();
			
			MonedaVO i;
			for (Object o : lst) {
				
				i = (MonedaVO) o;
				lstDoc.add(i);
			}
			
			this.containerMoneda.addAll(lstDoc);
			this.grid.setContainerDataSource(containerMoneda);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		
		
	}
	
	private void arreglarGrilla()
	{
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(200);
		lstColumn.get(0).setHeaderCaption("Código");
		lstColumn.get(1).setHeaderCaption("Nombre");
	}
	
	private void filtroGrilla()
	{
		try
		{
			 
			/*if(seleccionado instanceof ImpuestoVO)
				containerImpuesto.addContainerProperty(ImpuestoVO.class, ImpuestoVO.class, ImpuestoVO.class);
			VER CON DIEGO */
			
			com.vaadin.ui.Grid.HeaderRow filterRow = grid.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: grid.getContainerDataSource()
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
				        
				    	if(seleccionado instanceof ImpuestoVO) /*PARA IMPUESTOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerImpuesto.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerImpuesto.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
					        
				    	}
				    	else if(seleccionado instanceof DocumDGIVO) /*PARA DOCUMENTOS GDI*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerDocumentosDgi.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerDocumentosDgi.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof CodigoGeneralizadoVO) /*PARA DOCUMENTOS GDI*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerCodigoGeneralizado.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerCodigoGeneralizado.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof MonedaVO) /*PARA MONEDAS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerMoneda.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerMoneda.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
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
