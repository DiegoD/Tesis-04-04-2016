package com.vista.RepVis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.controladores.GastoControlador;
import com.controladores.ProcesoControlador;
import com.controladores.VistasControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Gastos.ObteniendoGastosException;
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
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Saldos.SaDocumsVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Procesos.ProcesoViewExtended;
import com.vista.Procesos.ProcesosPanelExtended;

public class VistaSaDocumExtended extends VistaSaDocum {

	private ArrayList<SaDocumsVO> lstGastos; 
	private BeanItemContainer<SaDocumsVO> container;
	private VistasControlador controlador;
	MySub sub = new MySub("50%","65%");
	PermisosUsuario permisos;
	boolean actualiza = false;
	
	public VistaSaDocumExtended(){
		
		controlador = new VistasControlador();
		this.lstGastos = new ArrayList<SaDocumsVO>();
		this.lblTitulo.setValue("Vista Saldo Documentos");
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				Calendar c = Calendar.getInstance();   // this takes current date
			    c.set(Calendar.DAY_OF_MONTH, 1);
				
			    this.fechaInicio.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
			    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			    this.fechaFin.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					
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
				new BeanItemContainer<SaDocumsVO>(SaDocumsVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstGastos = this.getGastos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (SaDocumsVO gastoVO: lstGastos) {
			
			container.addBean(gastoVO);
		}
		
		
		this.gridGastos.setContainerDataSource(container);
		
		if(!actualiza){
			
			actualiza = true;
			
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
			
			
		
			
		
			
			this.btnActualizar.addClickListener(click -> {
				try {
					
					if(fechaInicio.getValue()==null || fechaFin.getValue()==null){
						Mensajes.mostrarMensajeError("Debe ingresar las fechas para actualizar la búsqueda");
					}
					else{
						this.inicializarGrilla();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
	}
	
	/**
	 * Obtenemos gastos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<SaDocumsVO> getGastos() throws Exception  {
		
		ArrayList<SaDocumsVO> lstGastos = new ArrayList<SaDocumsVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_LEER);
			
			lstGastos = controlador.getSaldosDocum(permisoAux.getCodEmp());
			
		} 
		catch (InicializandoException | ConexionException
				| ObteniendoGastosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstGastos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un gasto
	 * desde ProcesoViewExtended
	 *
	 */
	public void actulaizarGrilla(SaDocumsVO vo) 
	{


	}
	
	public void actuilzarGrillaEliminado(long codigo){
		
	}

	/**
	 * Modificamos un gastoVO de la lista cuando
	 * se hace una acutalizacion de un gasto
	 *
	 */
	private void actualizarGastoenLista(GastoVO gastoVO)
	{
	}
	
	/**
	 * Retornanoms true si esta el gastoVO en la lista
	 * de gastos de la vista
	 *
	 */
	private boolean existeGastoenLista(Long nro_trans)
	{
		return true;
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridGastos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridGastos.getContainerDataSource()
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
		this.btnNuevoGasto.setVisible(false);
		this.btnNuevoGasto.setEnabled(false);
	}
	
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	public void mostrarMensaje(String msj)
	{
		Mensajes.mostrarMensajeError(msj);
	}
	
	public void setSub(String seleccion){
		if(seleccion.equals("Proceso")){
			sub.setHeight("95%");
			sub.setWidth("50%");
		}
		if(seleccion.equals("Empleado")){
			sub.setHeight("87%");
			sub.setWidth("46%");
		}
		if(seleccion.equals("Oficina")){
			sub.setHeight("80%");
			sub.setWidth("46%");
		}
		
	}




	
}
