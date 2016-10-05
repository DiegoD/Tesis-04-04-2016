package com.vista.TipoRubro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.EmpresaControlador;
import com.controladores.TipoRubroControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.empresa.EmpresaVO;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.TipoRubro.TipoRubroVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Empresas.EmpresaViewExtended;
import com.vista.Empresas.EmpresasPanelExtended;

public class TipoRubrosPanelExtended extends TipoRubrosPanel{
	
	private TipoRubroViewExtended form; 
	private ArrayList<TipoRubroVO> lstTipoRubros; /*Lista con los tipos rubros*/
	private BeanItemContainer<TipoRubroVO> container;
	private TipoRubroControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public TipoRubrosPanelExtended(){
		
		controlador = new TipoRubroControlador();
		this.lstTipoRubros = new ArrayList<TipoRubroVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_TIPORUBROS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_TIPORUBROS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoTipoRubro.addClickListener(click -> {
						
						sub = new MySub("40%","28%");
						form = new TipoRubroViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<TipoRubroVO>(TipoRubroVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstTipoRubros = this.getTipoRubros();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (TipoRubroVO tipoRubroVO : lstTipoRubros) {
			container.addBean(tipoRubroVO);
		}
		
		
		this.gridTipoRubros.setContainerDataSource(container);
		
		gridTipoRubros.removeColumn("activo");
		gridTipoRubros.removeColumn("fechaMod");
		gridTipoRubros.removeColumn("usuarioMod");
		gridTipoRubros.removeColumn("operacion");
		gridTipoRubros.removeColumn("codEmp");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridTipoRubros.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridTipoRubros.getSelectedRow() != null){
		    			BeanItem<TipoRubroVO> item = container.getItem(gridTipoRubros.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new TipoRubroViewExtended(Variables.OPERACION_LECTURA, TipoRubrosPanelExtended.this);
				    	sub = new MySub("40%","28%");
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
	 * Obtenemos empresas del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<TipoRubroVO> getTipoRubros() throws Exception  {
		
		ArrayList<TipoRubroVO> lstTipoRubros = new ArrayList<TipoRubroVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_TIPORUBROS,
							VariablesPermisos.OPERACION_LEER);

			
			lstTipoRubros = controlador.getTipoRubros(permisoAux, permisos.getCodEmp());
			
		} 
		catch (ObteniendoTipoRubroException | InicializandoException | ConexionException| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
			
		return lstTipoRubros;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un tipo rubro
	 * desde TipoRubroViewExtended
	 *
	 */
	public void actulaizarGrilla(TipoRubroVO tipoRubroVO)
	{

		/*Si esta el tipo rubro en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeTipoRubroenLista(tipoRubroVO.getCodTipoRubro()))
		{
			this.actualizarTipoRubroenLista(tipoRubroVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstTipoRubros.add(tipoRubroVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstTipoRubros);
		
		this.gridTipoRubros.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un tipo rubro de la lista cuando
	 * se hace una acutalizacion de un tipo rubro
	 *
	 */
	private void actualizarTipoRubroenLista(TipoRubroVO tipoRubroVO)
	{
		int i =0;
		boolean salir = false;
		
		TipoRubroVO tipoRubroEnLista;
		
		while( i < this.lstTipoRubros.size() && !salir)
		{
			tipoRubroEnLista = this.lstTipoRubros.get(i);
			
			if(tipoRubroVO.getCodTipoRubro().equals(tipoRubroEnLista.getCodTipoRubro())){
				
				this.lstTipoRubros.get(i).copiar(tipoRubroVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el tipo rubro en la lista
	 * de tipo rubros de la vista
	 *
	 */
	private boolean existeTipoRubroenLista(String cod_tipoRubro)
	{
		int i =0;
		boolean esta = false;
		
		TipoRubroVO aux;
		
		while( i < this.lstTipoRubros.size() && !esta)
		{
			aux = this.lstTipoRubros.get(i);
			if(cod_tipoRubro.equals(aux.getCodTipoRubro()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridTipoRubros.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridTipoRubros.getContainerDataSource()
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
		this.btnNuevoTipoRubro.setVisible(false);
		this.btnNuevoTipoRubro.setEnabled(false);
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
