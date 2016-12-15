package com.vista.Conciliaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.controladores.ConciliacionControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
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
import com.valueObject.Conciliaciones.ConciliacionVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class ConciliacionesPanelExtended extends ConciliacionesPanel {
	
	private static final long serialVersionUID = 1L;
	
	private ConciliacionViewExtended form; 
	private ArrayList<ConciliacionVO> lstConciliaciones; /*Lista con las conciliaciones*/
	private BeanItemContainer<ConciliacionVO> container;
	private ConciliacionControlador controlador;
	MySub sub = new MySub("65%", "65%");
	PermisosUsuario permisos;
	boolean actualiza = false;
	
	public ConciliacionesPanelExtended(){
		
		
		controlador = new ConciliacionControlador();
		this.lstConciliaciones = new ArrayList<ConciliacionVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		this.lblTitulo.setValue("Conciliaciones");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				Calendar c = Calendar.getInstance();   // this takes current date
			    c.set(Calendar.DAY_OF_MONTH, 1);
				
			    this.fechaInicio.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
			    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			    this.fechaFin.setValue(new java.sql.Date(c.getTimeInMillis()));
			    
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CONCILIACION, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					
					this.btnNuevo.addClickListener(click -> {
						
						sub = new MySub("90%","50%");
						form = new ConciliacionViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<ConciliacionVO>(ConciliacionVO.class);
		
		//Obtenemos lista de depositos del sistema
		try {
			this.lstConciliaciones = this.getConciliaciones();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (ConciliacionVO conciliacionVO: lstConciliaciones) {
			container.addBean(conciliacionVO);
		}
		
		
		this.gridConciliaciones.setContainerDataSource(container);
		
		
		gridConciliaciones.getColumn("fecValor").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
		
		if(!actualiza){
			
			actualiza = true;
			
			gridConciliaciones.removeColumn("codDocum");
			gridConciliaciones.removeColumn("serieDocum");
			gridConciliaciones.removeColumn("cod_emp");
			gridConciliaciones.removeColumn("impTotMn");
			gridConciliaciones.removeColumn("cuenta");
			gridConciliaciones.removeColumn("nroTrans");
			gridConciliaciones.removeColumn("fecDoc");
			gridConciliaciones.removeColumn("codMoneda");
			gridConciliaciones.removeColumn("simbolo");
			gridConciliaciones.removeColumn("nacional");
			gridConciliaciones.removeColumn("codBanco");
			gridConciliaciones.removeColumn("codCuenta");
			gridConciliaciones.removeColumn("lstDetalle");
			
			gridConciliaciones.removeColumn("fechaMod");
			gridConciliaciones.removeColumn("usuarioMod");
			gridConciliaciones.removeColumn("operacion");
			
			gridConciliaciones.setColumnOrder("fecValor", "nroDocum", "tipo",  "observaciones", "impTotMo",
					"descripcion", "nomBanco", "nomCuenta");
			
			gridConciliaciones.getColumn("fecValor").setHeaderCaption("Fecha");
			gridConciliaciones.getColumn("nroDocum").setHeaderCaption("Número");
			gridConciliaciones.getColumn("tipo").setHeaderCaption("Tipo");
			gridConciliaciones.getColumn("descripcion").setHeaderCaption("Moneda");
			gridConciliaciones.getColumn("impTotMo").setHeaderCaption("Importe");
			gridConciliaciones.getColumn("observaciones").setHeaderCaption("Obsersvaciones");
			gridConciliaciones.getColumn("descripcion").setHeaderCaption("Moneda");
			gridConciliaciones.getColumn("nomBanco").setHeaderCaption("Banco");
			gridConciliaciones.getColumn("nomCuenta").setHeaderCaption("Cuenta");
			
			gridConciliaciones.getColumn("fecValor").setWidth(120);
			gridConciliaciones.getColumn("nroDocum").setWidth(100);
			gridConciliaciones.getColumn("tipo").setWidth(120);
			gridConciliaciones.getColumn("impTotMo").setWidth(120);
			gridConciliaciones.getColumn("observaciones").setWidth(300);
			gridConciliaciones.getColumn("descripcion").setWidth(150);
			gridConciliaciones.getColumn("nomBanco").setWidth(150);
			gridConciliaciones.getColumn("nomCuenta").setWidth(150);
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
			
			gridConciliaciones.addSelectionListener(new SelectionListener() {
				
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		
			    		if(gridConciliaciones.getSelectedRow() != null){
			    			BeanItem<ConciliacionVO> item = container.getItem(gridConciliaciones.getSelectedRow());
					    	
					    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
					    	if(item.getBean().getFechaMod() == null)
					    	{
					    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
					    	}
								
					    	form = new ConciliacionViewExtended(Variables.OPERACION_LECTURA, ConciliacionesPanelExtended.this);
					    	sub = new MySub("90%","50%");
							sub.setModal(true);
							sub.setVista(form);
							/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
							form.setDataSourceFormulario(item);
							form.setLstDetalle(item.getBean().getLstDetalle());
							
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
					this.inicializarGrilla();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
		
		
	}
	
	/**
	 * Obtenemos monedas del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<ConciliacionVO> getConciliaciones() throws Exception  {
		
		ArrayList<ConciliacionVO> lstDepositos = new ArrayList<ConciliacionVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_CONCILIACION,
							VariablesPermisos.OPERACION_LEER);
			
			lstDepositos = controlador.getConciliaciones(permisoAux, new java.sql.Timestamp(fechaInicio.getValue().getTime()), new java.sql.Timestamp(fechaFin.getValue().getTime()));
			
		} 
		catch (ObteniendoConciliacionException | InicializandoException | ConexionException
				| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstDepositos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una moneda
	 * desde ImpuestoViewExtended
	 *
	 */
	public void actulaizarGrilla(ConciliacionVO conciliacionVO, String operacion)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeConciliacionenLista(conciliacionVO.getNroTrans()))
		{
			
			this.actualizarConciliacionenLista(conciliacionVO, operacion);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstConciliaciones.add(conciliacionVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstConciliaciones);
		
		this.gridConciliaciones.setContainerDataSource(container);

	}
	
	private void actualizarConciliacionenLista(ConciliacionVO conciliacionVO, String operacion)
	{
		int i =0;
		boolean salir = false;
		
		ConciliacionVO conciliacionEnLista;
		
		while( i < this.lstConciliaciones.size() && !salir)
		{
			conciliacionEnLista = this.lstConciliaciones.get(i);
			
			if(operacion.equals(Variables.OPERACION_EDITAR)){
				
				if(conciliacionVO.getNroTrans() == conciliacionEnLista.getNroTrans()){
					
					this.lstConciliaciones.get(i).copiar(conciliacionVO);
					salir = true;
				}
			}
			else{
				if(conciliacionVO.getNroTrans() == conciliacionEnLista.getNroTrans()){
					
					this.lstConciliaciones.remove(i);
					salir = true;
				}
			}
			
			
			i++;
		}
	}
	
	private boolean existeConciliacionenLista(Long cod_conciliacion)
	{
		int i =0;
		boolean esta = false;
		
		ConciliacionVO aux;
		
		while( i < this.lstConciliaciones.size() && !esta)
		{
			aux = this.lstConciliaciones.get(i);
			if(cod_conciliacion.equals(aux.getNroTrans()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridConciliaciones.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridConciliaciones.getContainerDataSource()
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
