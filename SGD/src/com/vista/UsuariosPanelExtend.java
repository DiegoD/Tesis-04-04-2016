package com.vista;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.UsuarioControlador;
import com.excepciones.ConexionException;
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
	BeanItemContainer<UsuarioVO> container;
	UsuarioControlador controlador;
	private UsuarioViewExtended form;
	
	public UsuariosPanelExtend() {
		
		controlador = new UsuarioControlador();
		
		try {
			
			this.inicializarGrilla();
			this.btnNuevoUsuario.addClickListener(click -> {
				

				try {
					
					MySub subGrupoView = new MySub();
					form = new UsuarioViewExtended(Variables.OPERACION_NUEVO);
					subGrupoView.setVista(form);
					
					UI.getCurrent().addWindow(subGrupoView);
					
				} catch (Exception e) {
					
					
				}
			});
			
		} 
		
		catch (Exception e) {
			//e.fillInStackTrace(e);
		}
		
	}
	
	private void inicializarGrilla() throws ClassNotFoundException, ObteniendoUsuariosException{
		
		this.container = new BeanItemContainer<UsuarioVO>(UsuarioVO.class);
		
		//Obtenemos lista de grupos del sistema
		ArrayList<UsuarioVO> lstUsuarios = this.getUsuarios();
		
		for (UsuarioVO usuarioVO : lstUsuarios) {
			container.addBean(usuarioVO);
		}
		gridUsuarios.setContainerDataSource(container);
		
		gridUsuarios.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    	BeanItem<UsuarioVO> item = container.getItem(gridUsuarios.getSelectedRow());
		
					MySub sub = new MySub();
					form = new UsuarioViewExtended(Variables.OPERACION_LECTURA);
					//form.fieldGroup.setItemDataSource(item);
					
					sub.setVista(form);
					/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
					form.setDataSourceFormulario(item);
					
					
					 UI.getCurrent().addWindow(sub);
					  
		    	}catch(Exception e)
		    	{
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
		
		
	}

	private ArrayList<UsuarioVO> getUsuarios() throws ClassNotFoundException, ObteniendoUsuariosException{
		
		ArrayList<UsuarioVO> lstUsuarios = new ArrayList<UsuarioVO>();
		
		ArrayList<JSONObject> lstUsuariosJ = null;
		
		try {
			lstUsuariosJ = controlador.getUsuarios();

		} catch (InicializandoException|ConexionException e) {
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		UsuarioVO aux;
		for (JSONObject jsonObject : lstUsuariosJ) {
			
			aux = new UsuarioVO(jsonObject);
			
			lstUsuarios.add(aux);
		}
		
		return lstUsuarios;
	}

}
