package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
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
				

				try {
					
					MySub subGrupoView = new MySub();
					form = new GrupoViewExtended();
					subGrupoView.setVista(form);
					
					UI.getCurrent().addWindow(subGrupoView);
					
				} catch (Exception e) {
					
					
				}
			});
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			
			this.mostrarMensajeError("Ha ocurrido un error inesperado");
		}
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
			
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
						
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
		        BeanItem<GrupoVO> item = container.getItem(gridview.getSelectedRow());
		
					MySub sub = new MySub();
					form = new GrupoViewExtended();
					//form.fieldGroup.setItemDataSource(item);
					form.setDataSourceFormulario(item);
					sub.setVista(form);
					
					
					  UI.getCurrent().addWindow(sub);
		      
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

		} catch (ObteniendoGruposException | InicializandoException e) {
			
			mostrarMensajeError(e.getMessage());
		}
		
		GrupoVO aux;
		for (JSONObject jsonObject : lstGruposJ) {
			
			aux = new GrupoVO(jsonObject);
			
			lstGrupos.add(aux);
		}
		
		return lstGrupos;
	}
	
	
////////////////////MENSAJES//////////////////////
	
	private void mostrarMensajeError(String msj){
	
	
		Notification notif = new Notification(
		"Error",
		"<br/>" + msj,
		Notification.Type.ERROR_MESSAGE,
		true); // Contains HTML
		
		
		notif.setDelayMsec(20000);
		notif.setPosition(Position.BOTTOM_RIGHT);
		//notif.setStyleName("mystyle");
		//notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
		
		notif.show(Page.getCurrent());
	
	}
	
	private void mostrarMensajeOK(String msj){
	
	
		Notification notif = new Notification(
		"OK",
		"<br/>" + msj,
		Notification.Type.HUMANIZED_MESSAGE,
		true); // Contains HTML
		
		
		notif.setDelayMsec(20000);
		notif.setPosition(Position.BOTTOM_RIGHT);
		//notif.setStyleName("mystyle");
		//notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
		
		notif.show(Page.getCurrent());
	
	}
	
	private void mostrarMensajeWarning(String msj){
	
	
		Notification notif = new Notification(
		"Atención",
		"<br/>" + msj,
		Notification.Type.WARNING_MESSAGE,
		true); // Contains HTML
		
		
		notif.setDelayMsec(20000);
		notif.setPosition(Position.BOTTOM_RIGHT);
		//notif.setStyleName("mystyle");
		//notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
		
		notif.show(Page.getCurrent());
	
	}
	
}
