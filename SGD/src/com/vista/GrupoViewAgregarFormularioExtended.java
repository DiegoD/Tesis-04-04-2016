package com.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.controladores.GrupoControlador;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioSelVO;
import com.valueObject.FormularioVO;


public class GrupoViewAgregarFormularioExtended extends GrupoViewAgregarFormulario{
	
	
	GrupoControlador controlador;
	BeanItemContainer<FormularioSelVO> container;
	
	/*Pasamos el controlador por referencia, para poder setearle al padre GrupoViewWxtended
	 * los formularios seleccionados*/
	@SuppressWarnings("unused")
	public GrupoViewAgregarFormularioExtended(GrupoControlador control)
	{
		this.controlador = control;
		this.lstFormularios.setSelectionMode(SelectionMode.MULTI);
		
		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
			
			
			BeanItemContainer<FormularioSelVO> selec;
			
			try
			{
				ArrayList<FormularioSelVO> lstSeleccionados = new ArrayList<FormularioSelVO>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Grupos para agregarlos*/
				Collection<Object> col= this.lstFormularios.getSelectedRows();
				
				FormularioSelVO aux;
				for (Object object : col) {
					aux = (FormularioSelVO)object;
					
					lstSeleccionados.add(aux);
					
				}

				controlador.agregarFormulariosSeleccionados(lstSeleccionados);
				
				((Window) this.getParent()).removeFromParent(this);
				
				
			
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
