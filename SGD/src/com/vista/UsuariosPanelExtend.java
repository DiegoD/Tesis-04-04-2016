package com.vista;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.controladores.UsuarioControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.UI;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;

public class UsuariosPanelExtend extends UsuariosPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioControlador controlador;
	private UsuarioViewExtended form;
	private BeanItemContainer<UsuarioVO> container;
	private ArrayList<UsuarioVO> lstUsuarios; 
	
	public UsuariosPanelExtend() throws ClassNotFoundException, ObteniendoUsuariosException, ErrorInesperadoException, ObteniendoGruposException 
	{
		
		controlador = new UsuarioControlador();
		this.lstUsuarios = new ArrayList<UsuarioVO>();
				
		this.inicializarGrilla();
		this.btnNuevoUsuario.addClickListener(click -> 
		{
			
			MySub subGrupoView = new MySub();
			form = new UsuarioViewExtended(Variables.OPERACION_NUEVO, this);
			subGrupoView.setVista(form);
			
			UI.getCurrent().addWindow(subGrupoView);
				
		});
	}
	
	private void inicializarGrilla() throws ClassNotFoundException, ObteniendoUsuariosException, ErrorInesperadoException, ObteniendoGruposException
	{
		
		this.container = new BeanItemContainer<UsuarioVO>(UsuarioVO.class);
		
		//Obtenemos lista de grupos del sistema
		//ArrayList<UsuarioVO> lstUsuarios = new ArrayList<UsuarioVO>();
		this.lstUsuarios = this.getUsuarios();
		
		for (UsuarioVO usuarioVO : this.lstUsuarios) 
		{
			container.addBean(usuarioVO);
		}
		gridUsuarios.setContainerDataSource(container);
		
		gridUsuarios.removeColumn("activo");
		gridUsuarios.removeColumn("lstGrupos");
		
		gridUsuarios.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		BeanItem<UsuarioVO> item = container.getItem(gridUsuarios.getSelectedRow());
		
					MySub sub = new MySub();
					form = new UsuarioViewExtended(Variables.OPERACION_LECTURA, UsuariosPanelExtend.this);
					
					sub.setVista(form);
					/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
					form.setDataSourceFormulario(item);
					form.setLstGruposUsuario(item.getBean().getLstGrupos());					
					
					 UI.getCurrent().addWindow(sub);
					  
		    	}
		    	catch(Exception e)
		    	{
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
		
		
	}

	private ArrayList<UsuarioVO> getUsuarios() throws ClassNotFoundException, ObteniendoUsuariosException, ErrorInesperadoException, ObteniendoGruposException{
		
		ArrayList<UsuarioVO> lstUsuarios = new ArrayList<UsuarioVO>();
		
		try 
		{
			lstUsuarios = controlador.getUsuarios();

		} 
		catch (InicializandoException|ConexionException e) 
		{
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		return lstUsuarios;
	}
	
	public void actualizarGrilla(UsuarioVO usuarioVo)
	{
		if(this.existeUsuarioLista(usuarioVo.getUsuario()))
		{
			this.actualizarUsuarioenLista(usuarioVo);
		}
		else
		{
			this.lstUsuarios.add(usuarioVo);
		}
		this.container.removeAllItems();
		this.container.addAll(this.lstUsuarios);
		this.gridUsuarios.setContainerDataSource(container);
	}
	
	private void actualizarUsuarioenLista(UsuarioVO usuarioVO)
	{
		int i = 0;
		boolean salir = false;
		UsuarioVO usuarioEnLista;
		
		while( i < this.lstUsuarios.size() && !salir)
		{
			usuarioEnLista = this.lstUsuarios.get(i);
			if(usuarioVO.getUsuario().equals(usuarioEnLista.getUsuario()))
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstUsuarios.get(i).copiar(usuarioVO);

				salir = true;
			}
			
			i++;
		}
	}
	
	private boolean existeUsuarioLista(String usuario)
	{
		int i =0;
		boolean esta = false;
		
		UsuarioVO aux;
		
		while( i < this.lstUsuarios.size() && !esta)
		{
			aux = this.lstUsuarios.get(i);
			if(usuario.equals(aux.getUsuario()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	
	
	public void refreshGrilla(UsuarioVO usuarioVO)
	{
		if(this.existeUsuarioLista(usuarioVO.getUsuario()))
		{
			this.actualizarUsuarioenLista(usuarioVO);
		}
		else
		{
			this.lstUsuarios.add(usuarioVO);
		}
		this.container.removeAllItems();
		this.container.addAll(this.lstUsuarios);
		this.gridUsuarios.setContainerDataSource(container);
	}

}
