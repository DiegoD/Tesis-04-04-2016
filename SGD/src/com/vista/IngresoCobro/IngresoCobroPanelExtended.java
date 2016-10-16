package com.vista.IngresoCobro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.controladores.BancoControlador;
import com.controladores.IngresoCobroControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.IngresoCobro.IngresoCobro;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Bancos.BancoViewExtended;
import com.vista.Bancos.BancosPanelExtended;

public class IngresoCobroPanelExtended extends IngresoCobroPanel{
	
	private IngresoCobroViewExtended form; 
	private ArrayList<IngresoCobroVO> lstIngresoCobro; /*Lista con los cobros*/
	private BeanItemContainer<IngresoCobroVO> container;
	private IngresoCobroControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("75%", "65%");
	
	public IngresoCobroPanelExtended(){
		
		controlador = new IngresoCobroControlador();
		this.lstIngresoCobro = new ArrayList<IngresoCobroVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
			
        /*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				if(permisoNuevoEditar)
				{
				
					this.btnNuevo.addClickListener(click -> {
						
							sub = new MySub("83%", "80%");
							form = new IngresoCobroViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<IngresoCobroVO>(IngresoCobroVO.class);
		
		//Obtenemos lista de bancos del sistema
		this.lstIngresoCobro = this.getCobros(); 
		
		for (IngresoCobroVO ingVO : lstIngresoCobro) {
			container.addBean(ingVO);
		}
		
		grid.setContainerDataSource(container);
		
		//Quitamos las columnas de la grilla de auditoria
		this.ocultarColumnasGrilla();
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		grid.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(grid.getSelectedRow() != null){
		    			BeanItem<IngresoCobroVO> item = container.getItem(grid.getSelectedRow());
				    	
		    			//IngresoCobroVO aux = item.getBean();
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
				    	
				    	form = new IngresoCobroViewExtended(Variables.OPERACION_LECTURA, IngresoCobroPanelExtended.this);
						//form.fieldGroup.setItemDataSource(item);
						sub = new MySub("90%","90%");
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
		
	}
	
	/**
	 * Obtenemos cobros del sistema
	 *
	 */
	private ArrayList<IngresoCobroVO> getCobros(){
		
		ArrayList<IngresoCobroVO> lst = new ArrayList<IngresoCobroVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_LEER);

			
			lst = controlador.getIngresoCobroTodos(permisoAux);

		} catch (InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoIngresoCobroException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lst;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un banco
	 * desde BancoViewExtended
	 *
	 */
	public void actulaizarGrilla(IngresoCobroVO ingVO)
	{

		/*Si esta el banco en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
//		if(this.existeEnLista(ingVO.getNroDocum()))
//		{
//			this.actualizarBancoenLista(ingVO);
//		}
//		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
//		{
//			this.lstIngresoCobro.add(ingVO);
//		}
//			
//		/*Actualizamos la grilla*/
//		this.container.removeAllItems();
//		this.container.addAll(this.lstIngresoCobro);
//		
//		this.grid.setContainerDataSource(container);
		
		
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		
		//Obtenemos lista de bancos del sistema
		this.lstIngresoCobro = this.getCobros(); 
		
		
		this.container.addAll(this.lstIngresoCobro);
		grid.setContainerDataSource(container);

	}
	
	
	/**
	 * Modificamos un bancoVO de la lista cuando
	 * se hace una acutalizacion de un Banco
	 *
	 */
	private void actualizarBancoenLista(IngresoCobroVO ingVO)
	{
		int i =0;
		boolean salir = false;
		
		IngresoCobroVO ingEnLista;
		
		while( i < this.lstIngresoCobro.size() && !salir)
		{
			ingEnLista = this.lstIngresoCobro.get(i);
			if(ingVO.getNroDocum()==ingEnLista.getNroDocum())
			{
				this.lstIngresoCobro.get(i).copiar(ingVO);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el bancoVO en la lista
	 * de bancoss de la vista
	 *
	 */
	private boolean existeEnLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		IngresoCobroVO aux;
		
		while( i < this.lstIngresoCobro.size() && !esta)
		{
			aux = this.lstIngresoCobro.get(i);
			if(nro==aux.getNroDocum())
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
		//grid.sort("nomTitular", SortDirection.ASCENDING);
		
		grid.getColumn("fechaMod").setHidden(true);
		grid.getColumn("usuarioMod").setHidden(true);
		grid.getColumn("operacion").setHidden(true);
		
		grid.getColumn("codTitular").setHidden(true);
		grid.getColumn("codBanco").setHidden(true);
		
		grid.getColumn("codCtaBco").setHidden(true);
		grid.getColumn("codDocRef").setHidden(true);
		grid.getColumn("codDocum").setHidden(true);
		grid.getColumn("codEmp").setHidden(true);
		grid.getColumn("codMoneda").setHidden(true);
		grid.getColumn("detalle").setHidden(true);
		grid.getColumn("fecValor").setHidden(true);
		grid.getColumn("impTotMn").setHidden(true);
		grid.getColumn("mPago").setHidden(true);
		grid.getColumn("nomBanco").setHidden(true);
		
		grid.getColumn("nomCtaBco").setHidden(true);
		grid.getColumn("nomMoneda").setHidden(true);
		grid.getColumn("nroDocRef").setHidden(true);
		grid.getColumn("nroTrans").setHidden(true);
		//grid.getColumn("referencia").setHidden(true);
		grid.getColumn("serieDocRef").setHidden(true);
		grid.getColumn("serieDocum").setHidden(true);
		grid.getColumn("tcMov").setHidden(true);
		
		grid.getColumn("codCuenta").setHidden(true);
		grid.getColumn("nomCuenta").setHidden(true);
		
		grid.removeColumn("nacional");
		grid.removeColumn("codCtaInd");
		grid.getColumn("simboloMoneda").setHeaderCaption("Moneda");
		
		grid.getColumn("fecDoc").setConverter(new StringToDateConverter(){
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
		
		grid.setColumnOrder("nomTitular", "referencia", "nroDocum", "simboloMoneda", "impTotMo", "fecDoc");
		
	
	}

}
