package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.DocumentoControlador;
import com.controladores.EmpresaControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.EmpresaVO;

public class DocumentosPanelExtended extends DocumentosPanel{

	private DocumentoViewExtended form; 
	private ArrayList<DocumentoAduaneroVO> lstDocumentos; /*Lista con los documentos*/
	private BeanItemContainer<DocumentoAduaneroVO> container;
	private DocumentoControlador controlador;
	MySub sub = new MySub("65%", "65%");
	
	public DocumentosPanelExtended(){
		
		controlador = new DocumentoControlador();
		this.lstDocumentos = new ArrayList<DocumentoAduaneroVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		PermisosUsuario permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DOCUMENTOS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DOCUMENTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoDocumento.addClickListener(click -> {
						
						sub = new MySub("65%", "65%");
						form = new DocumentoViewExtended(Variables.OPERACION_NUEVO, this);
						sub.setModal(true);
						sub.setVista(form);
						
						UI.getCurrent().addWindow(sub);
						
					});
				}
				else{
					/*Si no tiene permisos ocultamos boton de nuevo*/
					this.deshabilitarBotonNuevo();
				}
					
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
				
				Mensajes.mostrarMensajeError("Ha ocurrido un error inesperado");
				
			} 
			catch(Exception e){
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		}
		else {
			
			/*Si no tiene permisos mostramos mensaje*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		
		this.container = 
				new BeanItemContainer<DocumentoAduaneroVO>(DocumentoAduaneroVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstDocumentos = this.getDocumentos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (DocumentoAduaneroVO documentoVO : lstDocumentos) {
			container.addBean(documentoVO);
		}
		
		
		this.gridDocumentos.setContainerDataSource(container);
		
		gridDocumentos.removeColumn("activo");
		gridDocumentos.removeColumn("fechaMod");
		gridDocumentos.removeColumn("usuarioMod");
		gridDocumentos.removeColumn("operacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridDocumentos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridDocumentos.getSelectedRow() != null){
		    			BeanItem<DocumentoAduaneroVO> item = container.getItem(gridDocumentos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new DocumentoViewExtended(Variables.OPERACION_LECTURA, DocumentosPanelExtended.this);
						sub = new MySub("70%","65%");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						
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
	 * Obtenemos documentos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<DocumentoAduaneroVO> getDocumentos() throws Exception  {
		
		ArrayList<DocumentoAduaneroVO> lstDocumentos = new ArrayList<DocumentoAduaneroVO>();

		try {
			
			lstDocumentos = controlador.getDocumentos();
			
		} 
		catch (ObteniendoDocumentosException | InicializandoException | ConexionException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstDocumentos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un documento
	 * desde DocumentoViewExtended
	 *
	 */
	public void actulaizarGrilla(DocumentoAduaneroVO documentoVO)
	{

		/*Si esta el documento en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeDocumentoenLista(documentoVO.getCod_docucmento()))
		{
			this.actualizarDocumentoenLista(documentoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstDocumentos.add(documentoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstDocumentos);
		
		this.gridDocumentos.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un documento de la lista cuando
	 * se hace una acutalizacion de un documento
	 *
	 */
	private void actualizarDocumentoenLista(DocumentoAduaneroVO documentoVO)
	{
		int i =0;
		boolean salir = false;
		
		DocumentoAduaneroVO documentoEnLista;
		
		while( i < this.lstDocumentos.size() && !salir)
		{
			documentoEnLista = this.lstDocumentos.get(i);
			
			if(documentoVO.getCod_docucmento().equals(documentoEnLista.getCod_docucmento())){
				
				this.lstDocumentos.get(i).copiar(documentoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el documento en la lista
	 * de documentos de la vista
	 *
	 */
	private boolean existeDocumentoenLista(String cod_documento)
	{
		int i =0;
		boolean esta = false;
		
		DocumentoAduaneroVO aux;
		
		while( i < this.lstDocumentos.size() && !esta)
		{
			aux = this.lstDocumentos.get(i);
			if(cod_documento.equals(aux.getCod_docucmento()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridDocumentos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridDocumentos.getContainerDataSource()
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
		this.btnNuevoDocumento.setVisible(false);
		this.btnNuevoDocumento.setEnabled(false);
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	public void mostrarMensaje(String msj)
	{
		Mensajes.mostrarMensajeError(msj);
	}
}
