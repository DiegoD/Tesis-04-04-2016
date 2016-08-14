package com.vista.CodigosGeneralizados;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.CodigoGeneralizadoControlador;
import com.controladores.EmpresaControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.EmpresaVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Empresas.EmpresaViewExtended;
import com.vista.Empresas.EmpresasPanelExtended;

public class CodigosGeneralizadosPanelExtended extends CodigosGeneralizadosPanel{
	
	private CodigoGeneralizadoViewExtended form; 
	private ArrayList<CodigoGeneralizadoVO> lstCodigos; /*Lista con los códigos generalizados*/
	private BeanItemContainer<CodigoGeneralizadoVO> container;
	private CodigoGeneralizadoControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public CodigosGeneralizadosPanelExtended() {
		
		controlador = new CodigoGeneralizadoControlador();
		this.lstCodigos = new ArrayList<CodigoGeneralizadoVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoCodigo.addClickListener(click -> {
						
						sub = new MySub("65%", "65%");
						form = new CodigoGeneralizadoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<CodigoGeneralizadoVO>(CodigoGeneralizadoVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstCodigos = this.getCodigosGeneralizados();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (CodigoGeneralizadoVO codigoGeneralizadoVO : lstCodigos) {
			container.addBean(codigoGeneralizadoVO);
		}
		
		
		this.gridCodigosGeneralizados.setContainerDataSource(container);
		
		gridCodigosGeneralizados.removeColumn("fechaMod");
		gridCodigosGeneralizados.removeColumn("usuarioMod");
		gridCodigosGeneralizados.removeColumn("operacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridCodigosGeneralizados.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridCodigosGeneralizados.getSelectedRow() != null){
		    			BeanItem<CodigoGeneralizadoVO> item = container.getItem(gridCodigosGeneralizados.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new CodigoGeneralizadoViewExtended(Variables.OPERACION_LECTURA, CodigosGeneralizadosPanelExtended.this);
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
	 * Obtenemos codigos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizados() throws Exception  {
		
		ArrayList<CodigoGeneralizadoVO> lstCodigos = new ArrayList<CodigoGeneralizadoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS,
							VariablesPermisos.OPERACION_LEER);

			
			lstCodigos = controlador.getCodigosGeneralizados(permisoAux);
			
		} 
		catch (ObteniendoCodigosException | InicializandoException | ConexionException| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstCodigos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un código
	 * desde CodigoGeneralizadoViewExtended
	 *
	 */
	public void actulaizarGrilla(CodigoGeneralizadoVO codigoGeneralizadoVO)
	{

		/*Si esta la empresa en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeCodigoenLista(codigoGeneralizadoVO.getCodigo(),codigoGeneralizadoVO.getValor()))
		{
			this.actualizarCodigoenLista(codigoGeneralizadoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstCodigos.add(codigoGeneralizadoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstCodigos);
		
		this.gridCodigosGeneralizados.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un codigo de la lista cuando
	 * se hace una acutalizacion de un codigo
	 *
	 */
	private void actualizarCodigoenLista(CodigoGeneralizadoVO codigoGeneralizadoVO)
	{
		int i =0;
		boolean salir = false;
		
		CodigoGeneralizadoVO codigoEnLista;
		
		while( i < this.lstCodigos.size() && !salir)
		{
			codigoEnLista = this.lstCodigos.get(i);
			
			if(codigoGeneralizadoVO.getCodigo().equals(codigoEnLista.getCodigo()) && codigoGeneralizadoVO.getValor().equals(codigoEnLista.getValor())){
				
				this.lstCodigos.get(i).copiar(codigoGeneralizadoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el codigo en la lista
	 * de codigos de la vista
	 *
	 */
	private boolean existeCodigoenLista(String codigo, String valor)
	{
		int i =0;
		boolean esta = false;
		
		CodigoGeneralizadoVO aux;
		
		while( i < this.lstCodigos.size() && !esta)
		{
			aux = this.lstCodigos.get(i);
			if(codigo.equals(aux.getCodigo()) && valor.equals(aux.getValor()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridCodigosGeneralizados.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridCodigosGeneralizados.getContainerDataSource()
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
		this.btnNuevoCodigo.setVisible(false);
		this.btnNuevoCodigo.setEnabled(false);
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
