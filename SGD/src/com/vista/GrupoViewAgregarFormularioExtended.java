package com.vista;

import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;
import com.valueObject.FormularioVO;


public class GrupoViewAgregarFormularioExtended extends GrupoViewAgregarFormulario{
	
	BeanItemContainer<FormularioVO> container;
	
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
				
		lstFormularios.setContainerDataSource(container);
	}
}
