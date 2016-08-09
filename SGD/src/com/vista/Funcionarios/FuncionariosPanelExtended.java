package com.vista.Funcionarios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.FuncionarioControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.FuncionarioVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;


public class FuncionariosPanelExtended extends FuncionariosPanel {

	//private ClienteViewExtended form; 
		private ArrayList<FuncionarioVO> lstFuncionarios; /*Lista con los Funcionarios*/
		private BeanItemContainer<FuncionarioVO> container;
		private FuncionarioControlador controlador;
		MySub sub = new MySub("65%", "65%");
		private PermisosUsuario permisos;
		
		
		public FuncionariosPanelExtended() {
			
			controlador = new FuncionarioControlador();
			this.lstFuncionarios = new ArrayList<FuncionarioVO>();
			
			String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
			this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
			
			/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
			boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_LEER);
			
			if(permisoLectura){
		        
				try {
					
					this.inicializarGrilla();
					
					/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
					boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
					
					if(permisoNuevoEditar){
					
						this.btnNuevo.addClickListener(click -> {
							
							sub = new MySub("85%", "65%");
							FuncionarioViewExtended form = new FuncionarioViewExtended(Variables.OPERACION_NUEVO, this);
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
					new BeanItemContainer<FuncionarioVO>(FuncionarioVO.class);
			
			//Obtenemos lista de impuestos del sistema
			
				this.lstFuncionarios = this.getFuncionarios();
		
			
			
			for (FuncionarioVO clienteVO : lstFuncionarios) {
				container.addBean(clienteVO);
			}
			
			
			this.gridFuncionarios.setContainerDataSource(container);
			
			gridFuncionarios.removeColumn("activo");
			gridFuncionarios.removeColumn("fechaMod");
			gridFuncionarios.removeColumn("usuarioMod");
			gridFuncionarios.removeColumn("operacion");
			
			gridFuncionarios.removeColumn("codigoDoc");
			gridFuncionarios.removeColumn("direccion");
			gridFuncionarios.removeColumn("mail");
			gridFuncionarios.removeColumn("nombreDoc");
			gridFuncionarios.removeColumn("numeroDoc");
			gridFuncionarios.removeColumn("tel");
			
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
			
			
			gridFuncionarios.addSelectionListener(new SelectionListener() {
							
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		
			    		if(gridFuncionarios.getSelectedRow() != null){
			    			BeanItem<FuncionarioVO> item = container.getItem(gridFuncionarios.getSelectedRow());
					    	
					    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
					    	if(item.getBean().getFechaMod() == null)
					    	{
					    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
					    	}
								
					    	FuncionarioViewExtended form = new FuncionarioViewExtended(Variables.OPERACION_LECTURA, FuncionariosPanelExtended.this);
							sub = new MySub("85%","65%");
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
		private ArrayList<FuncionarioVO> getFuncionarios() {
			
			ArrayList<FuncionarioVO> lstClientes = new ArrayList<FuncionarioVO>();

			try {
				
					lstClientes = controlador.getFuncionariosTodos(this.permisos.getCodEmp());
				
			} 
			catch ( InicializandoException | ConexionException | ObteniendoFuncionariosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
				
			return lstClientes;
		}
		
		/**
		 * Actualizamos Grilla si se agrega o modigfica un impuesto
		 * desde ImpuestoViewExtended
		 *
		 */
		public void actulaizarGrilla(FuncionarioVO funcionarioVO)
		{

			/*Si esta el impuesto en la lista, es una acutalizacion
			 * y modificamos el objeto en la lista*/
			if(this.existeFuncionarioEnLista(funcionarioVO.getCodigo()))
			{
				this.actualizarFuncionarioEnLista(funcionarioVO);
			}
			else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
			{
				this.lstFuncionarios.add(funcionarioVO);
			}
				
			/*Actualizamos la grilla*/
			this.container.removeAllItems();
			this.container.addAll(this.lstFuncionarios);
			
			this.gridFuncionarios.setContainerDataSource(container);

		}
		
		/**
		 * Modificamos un impuestoVO de la lista cuando
		 * se hace una acutalizacion de un Impuesto
		 *
		 */
		private void actualizarFuncionarioEnLista(FuncionarioVO funcionarioVO)
		{
			int i =0;
			boolean salir = false;
			
			FuncionarioVO funcionarioEnLista;
			
			while( i < this.lstFuncionarios.size() && !salir)
			{
				funcionarioEnLista = this.lstFuncionarios.get(i);
				
				if(funcionarioVO.getCodigo() == funcionarioEnLista.getCodigo()){
					
					this.lstFuncionarios.get(i).copiar(funcionarioVO);
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
		private boolean existeFuncionarioEnLista(int codImpuesto)
		{
			int i =0;
			boolean esta = false;
			
			FuncionarioVO aux;
			
			while( i < this.lstFuncionarios.size() && !esta)
			{
				aux = this.lstFuncionarios.get(i);
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
			
				com.vaadin.ui.Grid.HeaderRow filterRow = gridFuncionarios.appendHeaderRow();
		
				// Set up a filter for all columns
				for (Object pid: gridFuncionarios.getContainerDataSource()
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
