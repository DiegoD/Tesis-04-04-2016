package com.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;

public class UsuarioViewAgregarGrupoExtend extends UsuarioViewAgregarGrupo
{
	UsuarioViewExtended mainView;
	BeanItemContainer<GrupoVO> container;
	
	public UsuarioViewAgregarGrupoExtend(UsuarioViewExtended main)
	{
		mainView = main;
		this.lstGrupos.setSelectionMode(SelectionMode.MULTI);
		
		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
			BeanItemContainer<GrupoVO> selec;
			try 
			{
				ArrayList<GrupoVO> lstSeleccionados = new ArrayList<GrupoVO>();
				Collection<Object> col= this.lstGrupos.getSelectedRows();
				GrupoVO aux;
				for (Object object : col) 
				{
					aux = (GrupoVO)object;
					lstSeleccionados.add(aux);
				}
				mainView.agregarGruposSeleccionados(lstSeleccionados);
				mainView.cerrarVentana();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
			}
		});
		
		this.btnCancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
	}
	
	
	
	/**
	 *Dado una ArrayList de FormularioVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaGrupos(ArrayList<GrupoVO> lstGruposUsuario)
	{
			
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		
		if(lstGruposUsuario != null)
		{
			for (GrupoVO grupoVO : lstGruposUsuario) 
			{
				container.addBean(grupoVO);
			}
			
			
		}
			
		lstGrupos.setContainerDataSource(container);
		this.filtroGrilla();
		lstGrupos.removeColumn("activo");
		lstGrupos.removeColumn("fechaMod");
		lstGrupos.removeColumn("operacion");
		lstGrupos.removeColumn("lstFormularios");
		lstGrupos.removeColumn("usuarioMod");
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = lstGrupos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: lstGrupos.getContainerDataSource()
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
				    	this.container.removeContainerFilters(pid);
		
				        // (Re)create the filter if necessary
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
