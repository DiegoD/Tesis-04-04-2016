package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.valueObject.GrupoVO;
import com.vista.CotizacionesPanelExtended.ItemGrilla;


public class GruposPanelExtended extends GruposPanel {
	
	//private FacturaEjExtended form;
	private GrupoViewExtended form;
	BeanItemContainer<GrupoVO> container;
	GrupoControlador controlador;
	
	public GruposPanelExtended(){
		
		controlador = new GrupoControlador();
		
		try {
			
			this.inicializarGrilla();
			
			this.btnNuevo.addClickListener(click -> {
				

					MySub subGrupoView = new MySub();
					form = new GrupoViewExtended(Variables.OPERACION_NUEVO);
					subGrupoView.setVista(form);
					
					UI.getCurrent().addWindow(subGrupoView);
					
				
			});
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			
			Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
			
		} catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
			
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
			
						
		this.container = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		//Obtenemos lista de grupos del sistema
		ArrayList<GrupoVO> lstGrupos = this.getGrupos();
		
		for (GrupoVO grupoVO : lstGrupos) {
			container.addBean(grupoVO);
		}
		
		
		gridview.setContainerDataSource(container);
		
		//Quitamos las columnas de la grilla de auditoria
		gridview.removeColumn("fechaMod");
		gridview.removeColumn("usuarioMod");
		gridview.removeColumn("operacion");
		
		
		//gridview.setEditorEnabled(true);
		//gridview.setEditorSaveCaption("Save my data, please!");
		
		//this.form = new GrupoViewExtended();
		//panelVerticalGral.addComponent(form);
		gridview.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    	BeanItem<GrupoVO> item = container.getItem(gridview.getSelectedRow());
		
					MySub sub = new MySub();
					form = new GrupoViewExtended(Variables.OPERACION_LECTURA);
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
	
	
	
	/**
	 * Obtenemos grupos del sistema
	 *
	 */
	private ArrayList<GrupoVO> getGrupos(){
		
		ArrayList<GrupoVO> lstGrupos = new ArrayList<GrupoVO>();
		
		ArrayList<JSONObject> lstGruposJ = null;
		
		try {
			lstGruposJ = controlador.getGrupos();

		} catch (ObteniendoGruposException | InicializandoException | ConexionException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		GrupoVO aux;
		for (JSONObject jsonObject : lstGruposJ) {
			
			aux = new GrupoVO(jsonObject);
			
			lstGrupos.add(aux);
		}
		
		return lstGrupos;
	}
	
}
