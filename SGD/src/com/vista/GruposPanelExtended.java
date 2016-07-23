package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;


public class GruposPanelExtended extends GruposPanel {
	
	
	private GrupoViewExtended form; 
	private ArrayList<GrupoVO> lstGrupos; /*Lista con los grupos*/
	private BeanItemContainer<GrupoVO> container;
	private GrupoControlador controlador;
	//private MySub subGrupoView = new MySub("60%", "55%");
	MySub sub;// = new MySub("60%", "55%");
	
	public GruposPanelExtended(){
		
		controlador = new GrupoControlador();
		this.lstGrupos = new ArrayList<GrupoVO>();
		
        //layout.setSizeFull();
        
		
		try {
			
			this.inicializarGrilla();
			
			this.btnNuevo.addClickListener(click -> {
				
				    sub = new MySub("60%","65%");
					form = new GrupoViewExtended(Variables.OPERACION_NUEVO, this);
					sub.setModal(true);
					sub.setVista(form);
					
					UI.getCurrent().addWindow(sub);
				
			});
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			
			Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
			
		} catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
									
		this.container = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		//Obtenemos lista de grupos del sistema
		this.lstGrupos = this.getGrupos();
		
		for (GrupoVO grupoVO : lstGrupos) {
			container.addBean(grupoVO);
		}
		
		
		gridview.setContainerDataSource(container);
		
		
		//Quitamos las columnas de la grilla de auditoria
		gridview.removeColumn("fechaMod");
		gridview.removeColumn("usuarioMod");
		gridview.removeColumn("operacion");
		gridview.removeColumn("activo");
		gridview.removeColumn("lstFormularios");
		
		
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		//gridview.setEditorEnabled(true);
		//gridview.setEditorSaveCaption("Save my data, please!");
		
		//this.form = new GrupoViewExtended();
		//panelVerticalGral.addComponent(form);
		gridview.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    	BeanItem<GrupoVO> item = container.getItem(gridview.getSelectedRow());
		
					
					form = new GrupoViewExtended(Variables.OPERACION_LECTURA, GruposPanelExtended.this);
					//form.fieldGroup.setItemDataSource(item);
					sub = new MySub("70%","65%");
					sub.setModal(true);
					sub.setVista(form);
					/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
					form.setDataSourceFormulario(item);
					form.setLstFormularios(item.getBean().getLstFormularios());
					
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

		try {
			
			lstGrupos = controlador.getGrupos();

		} catch (ObteniendoGruposException | InicializandoException | ConexionException | ErrorInesperadoException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstGrupos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un grupo
	 * desde GrupoViewExtended
	 *
	 */
	public void actulaizarGrilla(GrupoVO grupoVO)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeGrupoenLista(grupoVO.getCodGrupo()))
		{
			this.actualizarGrupoenLista(grupoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstGrupos.add(grupoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstGrupos);
		
		this.gridview.setContainerDataSource(container);

	}
	
	
	/**
	 * Modificamos un grupoVO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarGrupoenLista(GrupoVO grupoVO)
	{
		int i =0;
		boolean salir = false;
		
		GrupoVO grupoEnLista;
		
		while( i < this.lstGrupos.size() && !salir)
		{
			grupoEnLista = this.lstGrupos.get(i);
			if(grupoVO.getCodGrupo().equals(grupoEnLista.getCodGrupo()))
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstGrupos.get(i).copiar(grupoVO);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el grupoVO en la lista
	 * de grupos de la vista
	 *
	 */
	private boolean existeGrupoenLista(String codGrupo)
	{
		int i =0;
		boolean esta = false;
		
		GrupoVO aux;
		
		while( i < this.lstGrupos.size() && !esta)
		{
			aux = this.lstGrupos.get(i);
			if(codGrupo.equals(aux.getCodGrupo()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridview.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridview.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    /*Agregar field para usar el filtro*/
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				     /*Actualizar el filtro cuando este tenga un cambio en texto*/
				    filterField.addTextChangeListener(change -> {
				        
				    	/*No se pueden modificar los filtros,
				    	 * necesitamos reemplazarlos*/
				    	this.container.removeContainerFilters(pid);
		
				    	/*Hacemos nuevamente el filtro si es necesario*/
				        if (! change.getText().isEmpty())
				        	this.container.addContainerFilter(
				                new SimpleStringFilter(pid,
				                    change.getText(), true, false));
				    });

				    cell.setComponent(filterField);
				}
			}
			
		}catch(Exception e)
		{
			 System.out.println(e.getStackTrace());
		}
	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
}
