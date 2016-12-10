package com.vista.Cuentas;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.TextField;
import com.valueObject.RubroVO;
import com.vaadin.ui.Grid.SelectionMode;
import com.vista.Mensajes;
import com.vista.Variables;

public class CuentaViewAgregarRubroExtended extends CuentaViewAgregarRubro{
	
	CuentaViewExtended mainView;
	BeanItemContainer<RubroVO> container;
	
	/*Pasamos el controlador por referencia, para poder setearle al padre CuentaViewWxtended
	 * los formularios seleccionados*/
	@SuppressWarnings("unused")
	public CuentaViewAgregarRubroExtended(CuentaViewExtended main)
	{
		this.mainView = main;
		this.lstRubros.setSelectionMode(SelectionMode.MULTI);
		

		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
	
			BeanItemContainer<RubroVO> selec;
			
			try
			{
				ArrayList<RubroVO> lstSeleccionados = new ArrayList<RubroVO>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Cuentas para agregarlos*/
				Collection<Object> col= this.lstRubros.getSelectedRows();
				
				RubroVO aux;
				for (Object object : col) {
					aux = (RubroVO)object;
					
										
					lstSeleccionados.add(aux);
					
				}

				mainView.agregarRubrosSeleccionados(lstSeleccionados);
				mainView.cerrarVentana();

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
	 *Dado una ArrayList de RubroVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaRubros(ArrayList<RubroVO> lstRubros)
	{
			
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<RubroVO>(RubroVO.class);
		
				
		if(lstRubros != null)
		{
			for (RubroVO rubroVO : lstRubros) {
				container.addBean(rubroVO);
			}
		}
				
		this.lstRubros.setContainerDataSource(container);
		this.lstRubros.removeColumn("activo");
		this.lstRubros.removeColumn("codigoImpuesto");
		this.lstRubros.removeColumn("descripcionImpuesto");
		this.lstRubros.removeColumn("porcentajeImpuesto");
		this.lstRubros.removeColumn("activoImpuesto");
		this.lstRubros.removeColumn("descripcionTipoRubro");
		this.lstRubros.removeColumn("codTipoRubro");
		this.lstRubros.removeColumn("oficina");
		this.lstRubros.removeColumn("proceso");
		this.lstRubros.removeColumn("persona");
		this.lstRubros.removeColumn("fechaMod");
		this.lstRubros.removeColumn("usuarioMod");
		this.lstRubros.removeColumn("operacion");
		this.lstRubros.removeColumn("facturable");
		
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = this.lstRubros.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: this.lstRubros.getContainerDataSource()
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
