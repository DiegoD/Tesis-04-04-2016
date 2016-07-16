package com.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.controladores.GrupoControlador;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioVO;


public class GrupoViewAgregarFormularioExtended extends GrupoViewAgregarFormulario{
	
	
	GrupoControlador controlador;
	BeanItemContainer<FormularioVO> container;
	
	/*Pasamos el controlador por referencia, para poder setearle al padre GrupoViewWxtended
	 * los formularios seleccionados*/
	@SuppressWarnings("unused")
	public GrupoViewAgregarFormularioExtended(GrupoControlador control)
	{
		this.controlador = control;
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

				controlador.agregarFormulariosSeleccionados(lstSeleccionados);
				
				//((Window) this.getParent()).removeFromParent(this);
				
				
			
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}

		});
	}
	
	
	
	/**
	 *Dado una ArrayList de FormularioVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaForms(ArrayList<FormularioVO> lstForms)
	{
		//this.lstFormularios.addAttachListener(listener);
		
			
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
		
		if(lstForms != null)
		{
			for (FormularioVO formVO : lstForms) {
				container.addBean(formVO);
			}
		}
				
		this.lstFormularios.setContainerDataSource(container);
		
		
	}
}
