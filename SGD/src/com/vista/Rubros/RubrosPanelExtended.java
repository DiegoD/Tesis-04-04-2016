package com.vista.Rubros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.EmpresaControlador;
import com.controladores.RubroControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.empresa.EmpresaVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Empresas.EmpresaViewExtended;
import com.vista.Empresas.EmpresasPanelExtended;

public class RubrosPanelExtended extends RubrosPanel{
	
	private RubroViewExtended form; 
	private ArrayList<RubroVO> lstRubros; /*Lista con los rubros*/
	private BeanItemContainer<RubroVO> container;
	private RubroControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public RubrosPanelExtended() throws InicializandoException, ConexionException, ObteniendoImpuestosException{
		controlador = new RubroControlador();
		this.lstRubros = new ArrayList<RubroVO>();
		this.lblTitulo.setValue("Rubros");
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoRubro.addClickListener(click -> {
						
						sub = new MySub("60%", "30%");
						try {
							form = new RubroViewExtended(Variables.OPERACION_NUEVO, this);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
				new BeanItemContainer<RubroVO>(RubroVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstRubros = this.getRubros();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (RubroVO rubroVO : lstRubros) {
			container.addBean(rubroVO);
		}
		
		
		
		this.gridRubros.setContainerDataSource(container);
		
		gridRubros.removeColumn("activo");
		gridRubros.removeColumn("fechaMod");
		gridRubros.removeColumn("usuarioMod");
		gridRubros.removeColumn("operacion");
		gridRubros.removeColumn("activoImpuesto");
		gridRubros.removeColumn("descripcionTipoRubro");
		gridRubros.removeColumn("codigoImpuesto");
		gridRubros.removeColumn("codTipoRubro");
		gridRubros.removeColumn("proceso");
		gridRubros.removeColumn("persona");
		gridRubros.removeColumn("oficina");
		gridRubros.removeColumn("facturable");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridRubros.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridRubros.getSelectedRow() != null){
		    			BeanItem<RubroVO> item = container.getItem(gridRubros.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new RubroViewExtended(Variables.OPERACION_LECTURA, RubrosPanelExtended.this);
						sub = new MySub("60%","30%");
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
	 * Obtenemos rubros del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<RubroVO> getRubros() throws Exception  {
		
		ArrayList<RubroVO> lstRubros = new ArrayList<RubroVO>();

		try {
			 /* para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_RUBROS,
								VariablesPermisos.OPERACION_LEER);
			
			lstRubros = controlador.getRubros(permisoAux, permisoAux.getCodEmp());
			
		} 
		catch (ObteniendoRubrosException | InicializandoException | ConexionException
				| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstRubros;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un rubro
	 * desde RubroViewExtended
	 *
	 */
	public void actulaizarGrilla(RubroVO rubroVO)
	{

		/*Si esta la empresa en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeRubroenLista(rubroVO.getcodRubro()))
		{
			this.actualizarRubroenLista(rubroVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstRubros.add(rubroVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstRubros);
		
		this.gridRubros.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un rubro de la lista cuando
	 * se hace una acutalizacion de un rubro
	 *
	 */
	private void actualizarRubroenLista(RubroVO rubroVO)
	{
		int i =0;
		boolean salir = false;
		
		RubroVO rubroEnLista;
		
		while( i < this.lstRubros.size() && !salir)
		{
			rubroEnLista = this.lstRubros.get(i);
			
			if(rubroVO.getcodRubro().equals(rubroEnLista.getcodRubro())){
				
				this.lstRubros.get(i).copiar(rubroVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el rubro en la lista
	 * de rubros de la vista
	 *
	 */
	private boolean existeRubroenLista(String codRubro)
	{
		int i =0;
		boolean esta = false;
		
		RubroVO aux;
		
		while( i < this.lstRubros.size() && !esta)
		{
			aux = this.lstRubros.get(i);
			if(codRubro.equals(aux.getcodRubro()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridRubros.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridRubros.getContainerDataSource()
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
		this.btnNuevoRubro.setVisible(false);
		this.btnNuevoRubro.setEnabled(false);
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
