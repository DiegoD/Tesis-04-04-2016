package com.vista;

import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;

public class UsuarioViewAgregarGrupoExtend extends UsuarioViewAgregarGrupo
{
	BeanItemContainer<GrupoVO> container;
	
	/**
	 *Dado una ArrayList de FormularioVO inicializamos grilla
	 *de formularios
	 *
	 */
	public void setGrillaGrupos(ArrayList<GrupoVO> lstGruposUsuario)
	{
			
		this.lstGrupos.setSelectionMode(SelectionMode.MULTI);
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		
		if(lstGruposUsuario != null)
		{
			for (GrupoVO grupoVO : lstGruposUsuario) {
				container.addBean(grupoVO);
			}
			
			
		}
			
		lstGrupos.setContainerDataSource(container);
		lstGrupos.removeColumn("activo");
		lstGrupos.removeColumn("fechaMod");
		lstGrupos.removeColumn("operacion");
		lstGrupos.removeColumn("lstFormularios");
		lstGrupos.removeColumn("usuarioMod");
	}
}
