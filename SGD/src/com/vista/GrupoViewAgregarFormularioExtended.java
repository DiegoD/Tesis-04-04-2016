package com.vista;

import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioSelVO;
import com.valueObject.FormularioVO;


public class GrupoViewAgregarFormularioExtended extends GrupoViewAgregarFormulario{
	
	BeanItemContainer<FormularioSelVO> container;
	
	public GrupoViewAgregarFormularioExtended()
	{
		this.lstFormularios.setSelectionMode(SelectionMode.MULTI);
	}
	
	
	
	/**
	 *Dado una ArrayList de FormularioVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaForms(ArrayList<FormularioSelVO> lstForms)
	{
		//this.lstFormularios.addAttachListener(listener);
		
			
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioSelVO>(FormularioSelVO.class);
		
		
		if(lstForms != null)
		{
			for (FormularioSelVO formVO : lstForms) {
				container.addBean(formVO);
			}
		}
				
		this.lstFormularios.setContainerDataSource(container);
		
		this.lstFormularios.removeColumn("sel");
	}
}
