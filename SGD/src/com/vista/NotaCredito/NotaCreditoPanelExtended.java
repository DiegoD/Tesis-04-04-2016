package com.vista.NotaCredito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.controladores.NotaCreditoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.NotaCredito.*;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Docum.NotaCreditoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class NotaCreditoPanelExtended extends NotaCreditoPanel{
	
	private NotaCreditoViewExtended form; 
	private ArrayList<NotaCreditoVO> lstNc; /*Lista con nc*/
	private BeanItemContainer<NotaCreditoVO> container;
	private NotaCreditoControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("75%", "65%");
	boolean actualiza = false;
	
	public NotaCreditoPanelExtended(){
		
		controlador = new NotaCreditoControlador();
		this.lstNc = new ArrayList<NotaCreditoVO>();
		this.lblTitulo.setValue("Notas de Credito");
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
			
        /*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_NOTA_CREDITO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
        
			try {
				
				Calendar c = Calendar.getInstance();   // this takes current date
			    c.set(Calendar.DAY_OF_MONTH, 1);
				
			    this.fechaInicio.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
			    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			    this.fechaFin.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_NOTA_CREDITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				if(permisoNuevoEditar)
				{
				
					this.btnNuevo.addClickListener(click -> {
						
							sub = new MySub("550px", "1080px");
							form = new NotaCreditoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<NotaCreditoVO>(NotaCreditoVO.class);
		
		//Obtenemos lista de bancos del sistema
		this.lstNc = this.getCobros(); 
		
		for (NotaCreditoVO nc : lstNc) {
			container.addBean(nc);
		}
		
		grid.setContainerDataSource(container);
		
		if(!actualiza){
			
			actualiza = true;
			
			//Quitamos las columnas de la grilla de auditoria
			this.ocultarColumnasGrilla();
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
			
			grid.addSelectionListener(new SelectionListener() {
							
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		
			    		if(grid.getSelectedRow() != null){
			    			BeanItem<NotaCreditoVO> item = container.getItem(grid.getSelectedRow());
					    	
			    			//IngresoCobroVO aux = item.getBean();
					    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
					    	if(item.getBean().getFechaMod() == null)
					    	{
					    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
					    	}
					    	
					    	form = new NotaCreditoViewExtended(Variables.OPERACION_LECTURA, NotaCreditoPanelExtended.this);
							//form.fieldGroup.setItemDataSource(item);
					    	sub = new MySub("550px", "1080px");
							sub.setModal(true);
							sub.setVista(form);
							
					    	form.setDataSourceFormulario(item); 
					    	form.setLstDetalle(item.getBean().getDetalle());
							
							/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
							
							
							
							UI.getCurrent().addWindow(sub);
			    		}
				    	
					}
			    	
			    	catch(Exception e){
				    	Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				    }
			      
			    }
			});
			
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
	 * Obtenemos cobros del sistema
	 *
	 */
	private ArrayList<NotaCreditoVO> getCobros(){
		
		ArrayList<NotaCreditoVO> lst = new ArrayList<NotaCreditoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_NOTA_CREDITO,
							VariablesPermisos.OPERACION_LEER);

			
			lst = controlador.getNotaCreditoTodos(permisoAux,  new java.sql.Timestamp(fechaInicio.getValue().getTime()), new java.sql.Timestamp(fechaFin.getValue().getTime()));

		} catch (InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoNotaCreditoException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lst;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una nc
	 * desde BancoViewExtended
	 *
	 */
	public void actulaizarGrilla(NotaCreditoVO ncVO)
	{

		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		
		//Obtenemos lista de bancos del sistema
		this.lstNc = this.getCobros(); 
		
		
		this.container.addAll(this.lstNc);
		grid.setContainerDataSource(container);

	}
	
	

	
	/**
	 * Retornanoms true si esta el recibo en la lista
	 * de recibos de la vista
	 *
	 */
	private boolean existeEnLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		NotaCreditoVO aux;
		
		while( i < this.lstNc.size() && !esta)
		{
			aux = this.lstNc.get(i);
			if(String.valueOf(nro).equals(aux.getNroDocum()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = grid.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: grid.getContainerDataSource()
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
		grid.getColumn("fechaMod").setHidden(true);
		grid.getColumn("usuarioMod").setHidden(true);
		grid.getColumn("operacion").setHidden(true);
		
		grid.getColumn("codTitular").setHidden(true);
		
		grid.getColumn("codDocum").setHidden(true);
		grid.getColumn("codEmp").setHidden(true);
		grid.getColumn("codMoneda").setHidden(true);
		grid.getColumn("detalle").setHidden(true);
		grid.getColumn("fecDoc").setHidden(true);
		grid.getColumn("impTotMn").setHidden(true);
		
		grid.getColumn("nomMoneda").setHidden(true);
		grid.getColumn("nroTrans").setHidden(true);
		grid.getColumn("tcMov").setHidden(true);
		
		grid.getColumn("codCuenta").setHidden(true);
		grid.getColumn("nomCuenta").setHidden(true);
		
		grid.getColumn("impSubMn").setHidden(true);
		grid.getColumn("impSubMo").setHidden(true);
		grid.getColumn("impuTotMn").setHidden(true);
		grid.getColumn("impuTotMo").setHidden(true);
		grid.getColumn("nacionalMonedaCtaBco").setHidden(true);
		grid.getColumn("serieDocum").setHidden(true);
		grid.getColumn("tipo").setHidden(true);
		
		grid.removeColumn("nacional");
		grid.removeColumn("codCtaInd");
		grid.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		grid.getColumn("impTotMo").setHeaderCaption("Importe");
		
	
		
		grid.getColumn("fecValor").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
		
		grid.getColumn("nroDocum").setWidth(150);
		grid.getColumn("simboloMoneda").setWidth(150);
		grid.getColumn("impTotMo").setWidth(150);
		grid.getColumn("fecDoc").setWidth(150);
		grid.getColumn("referencia").setWidth(300);
		
		grid.setColumnOrder("nomTitular", "referencia", "nroDocum", "simboloMoneda", "impTotMo", "fecValor");
		
	
	}

}
