package com.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
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
		lstGrupos.removeColumn("activo");
		lstGrupos.removeColumn("fechaMod");
		lstGrupos.removeColumn("operacion");
		lstGrupos.removeColumn("lstFormularios");
		lstGrupos.removeColumn("usuarioMod");
	}
}
