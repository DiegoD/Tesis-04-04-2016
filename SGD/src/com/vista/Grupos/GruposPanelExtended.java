package com.vista.Grupos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;


import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;


public class GruposPanelExtended extends GruposPanel {
	
	
	private GrupoViewExtended form; 
	private ArrayList<GrupoVO> lstGrupos; /*Lista con los grupos*/
	private BeanItemContainer<GrupoVO> container;
	private GrupoControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public GruposPanelExtended(){
		
		controlador = new GrupoControlador();
		this.lstGrupos = new ArrayList<GrupoVO>();
		this.lblTitulo.setValue("Grupos");
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
			
        /*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				if(permisoNuevoEditar)
				{
				
					this.btnNuevo.addClickListener(click -> {
						
							sub = new MySub("450px", "900px");
							form = new GrupoViewExtended(Variables.OPERACION_NUEVO, this);
							sub.setModal(true);
							sub.setVista(form);
							
							UI.getCurrent().addWindow(sub);
						
					});
				}else{
					/*Si no tiene permisos ocultamos boton de nuevo*/
					this.deshabilitarBotonNuevo();
				}
					
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
				
				Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
				
			} catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		}else {
			
			/*Si no tiene permisos mostramos mensaje*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
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
		this.ocultarColumnasGrilla();
		
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
		    		
		    		if(gridview.getSelectedRow() != null){
		    			BeanItem<GrupoVO> item = container.getItem(gridview.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						form = new GrupoViewExtended(Variables.OPERACION_LECTURA, GruposPanelExtended.this);
						//form.fieldGroup.setItemDataSource(item);
						sub = new MySub("450px", "900px");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						form.setLstFormularios(item.getBean().getLstFormularios());
						
						UI.getCurrent().addWindow(sub);
		    		}
			    	
				}
		    	
		    	catch(Exception e){
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
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GRUPO,
							VariablesPermisos.OPERACION_LEER);

			
			lstGrupos = controlador.getGrupos(permisoAux);

		} catch (ObteniendoGruposException | InicializandoException | ConexionException | ErrorInesperadoException | ObteniendoFormulariosException | ObteniendoPermisosException | NoTienePermisosException e) {
			
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
	
	private void deshabilitarBotonNuevo()
	{
		this.btnNuevo.setVisible(false);
		this.btnNuevo.setEnabled(false);
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	public void mostrarMensaje(String msj)
	{
		Mensajes.mostrarMensajeError(msj);
	}
	
	private void ocultarColumnasGrilla()
	{
		gridview.getColumn("fechaMod").setHidden(true);
		gridview.getColumn("usuarioMod").setHidden(true);
		gridview.getColumn("operacion").setHidden(true);
		gridview.getColumn("activo").setHidden(true);
		gridview.getColumn("lstFormularios").setHidden(true);
	}
	
}
