package com.vista;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.UsuarioControlador;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
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
					form = new UsuarioViewExtended();
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
		
		
	}

	private ArrayList<UsuarioVO> getUsuarios() throws ClassNotFoundException, ObteniendoUsuariosException{
		
		ArrayList<UsuarioVO> lstUsuarios = new ArrayList<UsuarioVO>();
		
		ArrayList<JSONObject> lstUsuariosJ = null;
		
		try {
			lstUsuariosJ = controlador.getUsuarios();

		} catch (InicializandoException e) {
			
			//mostrarMensajeError(e.getMessage());
		}
		
		UsuarioVO aux;
		for (JSONObject jsonObject : lstUsuariosJ) {
			
			aux = new UsuarioVO(jsonObject);
			
			lstUsuarios.add(aux);
		}
		
		return lstUsuarios;
	}

}
