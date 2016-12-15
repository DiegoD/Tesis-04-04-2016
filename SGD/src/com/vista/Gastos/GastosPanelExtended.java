package com.vista.Gastos;

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
import com.valueObject.proceso.ProcesoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Procesos.ProcesoViewExtended;
import com.vista.Procesos.ProcesosPanelExtended;

public class GastosPanelExtended extends GastosPanel implements IGastosMain{

	private GastoViewExtended form; 
	private ArrayList<GastoVO> lstGastos; /*Lista con las empresas*/
	private BeanItemContainer<GastoVO> container;
	private GastoControlador controlador;
	MySub sub = new MySub("50%","65%");
	PermisosUsuario permisos;
	boolean actualiza = false;
	
	public GastosPanelExtended(){
		
		controlador = new GastoControlador();
		this.lstGastos = new ArrayList<GastoVO>();
		this.lblTitulo.setValue("Gastos");
		
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
				
					this.btnNuevoGasto.addClickListener(click -> {
						
						sub = new MySub("50%","45%");
						form = new GastoViewExtended(Variables.OPERACION_NUEVO, this, null, "Gasto");
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
				new BeanItemContainer<GastoVO>(GastoVO.class);
		
		//Obtenemos lista de impuestos del sistema
		try {
			this.lstGastos = this.getGastos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (GastoVO gastoVO: lstGastos) {
			if(gastoVO.getNomTitular().equals("0")){
				gastoVO.setNomTitular("Oficina");
			}
			container.addBean(gastoVO);
		}
		
		
		this.gridGastos.setContainerDataSource(container);
		
		if(!actualiza){
			
			actualiza = true;
			
			gridGastos.removeColumn("fechaMod");
			gridGastos.removeColumn("usuarioMod");
			gridGastos.removeColumn("operacion");
			
			gridGastos.removeColumn("codDocum");
			gridGastos.removeColumn("serieDocum");
			gridGastos.removeColumn("codEmp");
			
			gridGastos.removeColumn("codMoneda");
			gridGastos.removeColumn("nomMoneda");
			gridGastos.removeColumn("simboloMoneda");
			
			gridGastos.removeColumn("codTitular");
			gridGastos.removeColumn("nroTrans");
			//gridGastos.removeColumn("impTotMn");
			
			gridGastos.removeColumn("tcMov");
			gridGastos.removeColumn("fecDoc");
			
			gridGastos.removeColumn("codImpuesto");
			gridGastos.removeColumn("nomImpuesto");
			gridGastos.removeColumn("porcentajeImpuesto");
			
			gridGastos.removeColumn("impImpuMn");
			gridGastos.removeColumn("impImpuMo");
			gridGastos.removeColumn("impSubMn");
			
			gridGastos.removeColumn("impSubMo");
			gridGastos.removeColumn("impTotMn");
			gridGastos.removeColumn("impTotMo");
			
			//gridGastos.removeColumn("tcMov");
			gridGastos.removeColumn("nomCuenta");
			gridGastos.removeColumn("codProceso");
			
			gridGastos.removeColumn("nomRubro");
			gridGastos.removeColumn("codRubro");
			gridGastos.removeColumn("codCtaInd");
			gridGastos.removeColumn("linea");
			gridGastos.removeColumn("codCuenta");
			gridGastos.removeColumn("nacional");
			
			gridGastos.setColumnOrder("fecValor", "nomTitular", "nroDocum", "referencia", "descProceso");
		
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
			
			
			gridGastos.addSelectionListener(new SelectionListener() {
							
			    /**
				 * 
				 */
	
				@Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		
			    		if(gridGastos.getSelectedRow() != null){
			    			BeanItem<GastoVO> item = container.getItem(gridGastos.getSelectedRow());
					    	
					    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
					    	if(item.getBean().getFechaMod() == null)
					    	{
					    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
					    	}
								
					    	form = new GastoViewExtended(Variables.OPERACION_LECTURA, GastosPanelExtended.this, null, "Gasto");
					    	sub = new MySub("50%","45%");
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
			gridGastos.getColumn("fecValor").setConverter(new StringToDateConverter(){
				/**
				 * 
				 */
	
				@Override
	
				public DateFormat getFormat(Locale locale){
	
					return new SimpleDateFormat("dd/MM/yyyy");
	
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
	 * Obtenemos gastos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<GastoVO> getGastos() throws Exception  {
		
		ArrayList<GastoVO> lstGastos = new ArrayList<GastoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_LEER);
			
			lstGastos = controlador.getGastos(permisoAux, new java.sql.Timestamp(fechaInicio.getValue().getTime()), new java.sql.Timestamp(fechaFin.getValue().getTime()));
			
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
	public void actulaizarGrilla(GastoVO gastoVO) 
	{

		/*Si esta el proceso en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeGastoenLista(gastoVO.getNroTrans()))
		{
			this.actualizarGastoenLista(gastoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstGastos.add(gastoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstGastos);
		
		this.gridGastos.setContainerDataSource(container);

	}
	
	public void actuilzarGrillaEliminado(long codigo){
		
		int i =0;
		boolean salir = false;
		int index = 0;
		
		GastoVO gastoEnLista;
		
		while( i < this.lstGastos.size() && !salir)
		{
			gastoEnLista = this.lstGastos.get(i);
			
			if(codigo == gastoEnLista.getNroTrans()){
				
				index = i;
				salir = true;
			}
			
			i++;
		}
		
		this.lstGastos.remove(index);
		this.container.removeAllItems();
		this.container.addAll(this.lstGastos);
		
		this.gridGastos.setContainerDataSource(container);
	}

	/**
	 * Modificamos un gastoVO de la lista cuando
	 * se hace una acutalizacion de un gasto
	 *
	 */
	private void actualizarGastoenLista(GastoVO gastoVO)
	{
		int i =0;
		boolean salir = false;
		
		GastoVO gastoEnLista;
		
		while( i < this.lstGastos.size() && !salir)
		{
			gastoEnLista = this.lstGastos.get(i);
			
			if(gastoVO.getNroTrans() == gastoEnLista.getNroTrans()){
				
				this.lstGastos.get(i).copiar(gastoVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el gastoVO en la lista
	 * de gastos de la vista
	 *
	 */
	private boolean existeGastoenLista(Long nro_trans)
	{
		int i =0;
		boolean esta = false;
		
		GastoVO aux;
		
		while( i < this.lstGastos.size() && !esta)
		{
			aux = this.lstGastos.get(i);
			if(nro_trans.equals(aux.getNroTrans()))
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

	@Override
	public void setInfoLst(GastoVO gasto) {
		// TODO Auto-generated method stub
		/*No lo implementamos en este form*/
	}

	@Override
	public String nomForm() {
		
		return "Panel";
	}
	
}
