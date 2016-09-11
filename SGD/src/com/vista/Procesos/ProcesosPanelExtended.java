package com.vista.Procesos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.controladores.ProcesoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Monedas.MonedaViewExtended;
import com.vista.Monedas.MonedasPanelExtended;

public class ProcesosPanelExtended extends ProcesosPanel{
	
	private ProcesoViewExtended form; 
	private ArrayList<ProcesoVO> lstProcesos; /*Lista con las empresas*/
	private BeanItemContainer<ProcesoVO> container;
	private ProcesoControlador controlador;
	MySub sub = new MySub("100%","70%");
	PermisosUsuario permisos;

	public ProcesosPanelExtended(){
		controlador = new ProcesoControlador();
		this.lstProcesos = new ArrayList<ProcesoVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoProceso.addClickListener(click -> {
						
						sub = new MySub("100%","70%");
						form = new ProcesoViewExtended(Variables.OPERACION_NUEVO, this);
						sub.setModal(true);
						sub.setVista((Component) form);
						
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
				new BeanItemContainer<ProcesoVO>(ProcesoVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstProcesos = this.getProcesos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (ProcesoVO procesoVO: lstProcesos) {
			container.addBean(procesoVO);
		}
		
		
		this.gridProcesos.setContainerDataSource(container);
		
		gridProcesos.removeColumn("tcMov");
		gridProcesos.removeColumn("simboloMoneda");
		gridProcesos.removeColumn("codCliente");
		gridProcesos.removeColumn("codMoneda");
		gridProcesos.removeColumn("fechaMod");
		gridProcesos.removeColumn("usuarioMod");
		gridProcesos.removeColumn("operacion");
		gridProcesos.removeColumn("nroMega");
		gridProcesos.removeColumn("codDocum");
		gridProcesos.removeColumn("nomDocum");
		gridProcesos.removeColumn("nroDocum");
		gridProcesos.removeColumn("fecDocum");
		gridProcesos.removeColumn("carpeta");
		gridProcesos.removeColumn("impMo");
		gridProcesos.removeColumn("impMn");
		gridProcesos.removeColumn("impTr");
		gridProcesos.removeColumn("kilos");
		gridProcesos.removeColumn("fecCruce");
		gridProcesos.removeColumn("marca");
		gridProcesos.removeColumn("medio");
		gridProcesos.removeColumn("descripcion");
		gridProcesos.removeColumn("observaciones");
		gridProcesos.removeColumn("descMoneda");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridProcesos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridProcesos.getSelectedRow() != null){
		    			BeanItem<ProcesoVO> item = container.getItem(gridProcesos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new ProcesoViewExtended(Variables.OPERACION_LECTURA, ProcesosPanelExtended.this);
				    	sub = new MySub("100%","70%");
						sub.setModal(true);
						sub.setVista((Component) form);
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
		
		//Modifica el formato de fecha en la grilla 
		gridProcesos.getColumn("fecha").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
		
	}
	
	/**
	 * Obtenemos Procesos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<ProcesoVO> getProcesos() throws Exception  {
		
		ArrayList<ProcesoVO> lstProcesos = new ArrayList<ProcesoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_PROCESOS,
							VariablesPermisos.OPERACION_LEER);
			
			lstProcesos = controlador.getProcesos(permisoAux);
			
		} 
		catch (ObteniendoProcesosException | InicializandoException | ConexionException
				| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstProcesos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un proceso
	 * desde ProcesoViewExtended
	 *
	 */
	public void actulaizarGrilla(ProcesoVO procesoVO)
	{

		/*Si esta el proceso en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeProcesoenLista(procesoVO.getCodigo()))
		{
			this.actualizarProcesoenLista(procesoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstProcesos.add(procesoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstProcesos);
		
		this.gridProcesos.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un procesoVO de la lista cuando
	 * se hace una acutalizacion de un proceso
	 *
	 */
	private void actualizarProcesoenLista(ProcesoVO procesoVO)
	{
		int i =0;
		boolean salir = false;
		
		ProcesoVO procesoEnLista;
		
		while( i < this.lstProcesos.size() && !salir)
		{
			procesoEnLista = this.lstProcesos.get(i);
			
			if(procesoVO.getCodigo() == procesoEnLista.getCodigo()){
				
				this.lstProcesos.get(i).copiar(procesoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el procesoVO en la lista
	 * de procesos de la vista
	 *
	 */
	private boolean existeProcesoenLista(Integer cod_proceso)
	{
		int i =0;
		boolean esta = false;
		
		ProcesoVO aux;
		
		while( i < this.lstProcesos.size() && !esta)
		{
			aux = this.lstProcesos.get(i);
			if(cod_proceso.equals(aux.getCodigo()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridProcesos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridProcesos.getContainerDataSource()
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
		this.btnNuevoProceso.setVisible(false);
		this.btnNuevoProceso.setEnabled(false);
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
