package com.vista.Clientes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.ClienteControlador;
import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.ImpuestoVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Impuestos.ImpuestoViewExtended;
import com.vista.Impuestos.ImpuestosPanelExtended;

public class ClientesPanelExtended extends ClientesPanel{

	//private ClienteViewExtended form; 
	private ArrayList<ClienteVO> lstClientes; /*Lista con los impuestos*/
	private BeanItemContainer<ClienteVO> container;
	private ClienteControlador controlador;
	MySub sub = new MySub("65%", "35%");
	private PermisosUsuario permisos;
	
	
	public ClientesPanelExtended() {
		
		controlador = new ClienteControlador();
		this.lstClientes = new ArrayList<ClienteVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CLIENTES, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CLIENTES, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevo.addClickListener(click -> {
						
						sub = new MySub("70%","42%");
						ClienteViewExtended form = new ClienteViewExtended(Variables.OPERACION_NUEVO, this);
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
			this.btnNuevo.setEnabled(false);
			this.btnNuevo.setVisible(false);
		}
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		
		this.container = 
				new BeanItemContainer<ClienteVO>(ClienteVO.class);
		
		//Obtenemos lista de impuestos del sistema
		
			this.lstClientes = this.getClientes();
	
		
		
		for (ClienteVO clienteVO : lstClientes) {
			container.addBean(clienteVO);
		}
		
		
		this.gridClientes.setContainerDataSource(container);
		
		gridClientes.removeColumn("activo");
		gridClientes.removeColumn("fechaMod");
		gridClientes.removeColumn("usuarioMod");
		gridClientes.removeColumn("operacion");
		
		gridClientes.removeColumn("codigoDoc");
		gridClientes.removeColumn("direccion");
		gridClientes.removeColumn("mail");
		gridClientes.removeColumn("nombreDoc");
		gridClientes.removeColumn("numeroDoc");
		gridClientes.removeColumn("tel");
		gridClientes.removeColumn("tipo");
		
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridClientes.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridClientes.getSelectedRow() != null){
		    			BeanItem<ClienteVO> item = container.getItem(gridClientes.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	ClienteViewExtended form = new ClienteViewExtended(Variables.OPERACION_LECTURA, ClientesPanelExtended.this);
				    	sub = new MySub("70%","42%");
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
	 * Obtenemos clientes del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<ClienteVO> getClientes() {
		
		ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_CLIENTES,
							VariablesPermisos.OPERACION_LEER);

			
			lstClientes = controlador.getClientesTodos(permisoAux);
			
		} 
		catch ( InicializandoException | ConexionException | ObteniendoClientesException | ObteniendoPermisosException | NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		return lstClientes;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un impuesto
	 * desde ImpuestoViewExtended
	 *
	 */
	public void actulaizarGrilla(ClienteVO clienteVO)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeClienteEnLista(clienteVO.getCodigo()))
		{
			this.actualizarClienteEnLista(clienteVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstClientes.add(clienteVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstClientes);
		
		this.gridClientes.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un impuestoVO de la lista cuando
	 * se hace una acutalizacion de un Impuesto
	 *
	 */
	private void actualizarClienteEnLista(ClienteVO clienteVO)
	{
		int i =0;
		boolean salir = false;
		
		ClienteVO clienteEnLista;
		
		while( i < this.lstClientes.size() && !salir)
		{
			clienteEnLista = this.lstClientes.get(i);
			
			if(clienteVO.getCodigo() == clienteEnLista.getCodigo()){
				
				this.lstClientes.get(i).copiar(clienteVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el impuestoVO en la lista
	 * de impuestos de la vista
	 *
	 */
	private boolean existeClienteEnLista(int codImpuesto)
	{
		int i =0;
		boolean esta = false;
		
		ClienteVO aux;
		
		while( i < this.lstClientes.size() && !esta)
		{
			aux = this.lstClientes.get(i);
			if(codImpuesto == aux.getCodigo())
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridClientes.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridClientes.getContainerDataSource()
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
	
}
