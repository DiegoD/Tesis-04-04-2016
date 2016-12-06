package com.vista.Deposito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.controladores.DepositoControlador;
import com.controladores.MonedaControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Deposito.DepositoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Monedas.MonedaViewExtended;
import com.vista.Monedas.MonedasPanelExtended;

public class DepositoPanelExtended extends DepositoPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DepositoViewExtended form; 
	private ArrayList<DepositoVO> lstDepositos; /*Lista con las empresas*/
	private BeanItemContainer<DepositoVO> container;
	private DepositoControlador controlador;
	MySub sub = new MySub("65%", "65%");
	PermisosUsuario permisos;
	
	public DepositoPanelExtended(){
		
		controlador = new DepositoControlador();
		this.lstDepositos = new ArrayList<DepositoVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_DEPOSITO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevoDeposito.addClickListener(click -> {
						
						sub = new MySub("90%","50%");
						form = new DepositoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<DepositoVO>(DepositoVO.class);
		
		//Obtenemos lista de depositos del sistema
		try {
			this.lstDepositos = this.getDepositos();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (DepositoVO depositoVO: lstDepositos) {
			container.addBean(depositoVO);
		}
		
		
		this.gridDepositos.setContainerDataSource(container);
		
		gridDepositos.removeColumn("operacion");
		gridDepositos.removeColumn("fechaMod");
		gridDepositos.removeColumn("usuarioMod");
		
		gridDepositos.removeColumn("codDocum");
		gridDepositos.removeColumn("serieDocum");
		gridDepositos.removeColumn("fecDoc");
		gridDepositos.removeColumn("codBanco");
		gridDepositos.removeColumn("codCuenta");
		gridDepositos.removeColumn("codMoneda");
		gridDepositos.removeColumn("nacional");
		gridDepositos.removeColumn("funcionario");
		gridDepositos.removeColumn("numComprobante");
		gridDepositos.removeColumn("impTotMn");
		gridDepositos.removeColumn("nroTrans");
		gridDepositos.removeColumn("lstDetalle");
		gridDepositos.removeColumn("tcMov");
		
		gridDepositos.setColumnOrder("fecValor", "nomBanco", "nomCuenta", "impTotMo", "nomMoneda", "nroDocum", "observaciones");
		gridDepositos.getColumn("fecValor").setHeaderCaption("Fecha");
		gridDepositos.getColumn("nomBanco").setHeaderCaption("Banco");
		gridDepositos.getColumn("nomCuenta").setHeaderCaption("Cuenta");
		gridDepositos.getColumn("impTotMo").setHeaderCaption("Importe");
		gridDepositos.getColumn("nroDocum").setHeaderCaption("Comprobante");
		gridDepositos.getColumn("observaciones").setHeaderCaption("Observaciones");
		gridDepositos.getColumn("nomMoneda").setHeaderCaption("Moneda");
		
		gridDepositos.getColumn("fecValor").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridDepositos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridDepositos.getSelectedRow() != null){
		    			BeanItem<DepositoVO> item = container.getItem(gridDepositos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
				    	form = new DepositoViewExtended(Variables.OPERACION_LECTURA, DepositoPanelExtended.this);
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
		
	}
	
	/**
	 * Obtenemos monedas del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<DepositoVO> getDepositos() throws Exception  {
		
		ArrayList<DepositoVO> lstDepositos = new ArrayList<DepositoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_DEPOSITO,
							VariablesPermisos.OPERACION_LEER);
			
			lstDepositos = controlador.getDepositos(permisoAux);
			
		} 
		catch (ObteniendoDepositoException | InicializandoException | ConexionException
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
	public void actulaizarGrilla(DepositoVO depositoVO, String operacion)
	{

		/*Si esta el impuesto en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeDepositoenLista(depositoVO.getNroTrans()))
		{
			
			this.actualizarDepositoenLista(depositoVO, operacion);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstDepositos.add(depositoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstDepositos);
		
		this.gridDepositos.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos un monedaVO de la lista cuando
	 * se hace una acutalizacion de una moneda
	 *
	 */
	private void actualizarDepositoenLista(DepositoVO depositoVO, String operacion)
	{
		int i =0;
		boolean salir = false;
		
		DepositoVO depositoEnLista;
		
		while( i < this.lstDepositos.size() && !salir)
		{
			depositoEnLista = this.lstDepositos.get(i);
			
			if(operacion.equals(Variables.OPERACION_EDITAR)){
				
				if(depositoVO.getNroTrans() == depositoEnLista.getNroTrans()){
					
					this.lstDepositos.get(i).copiar(depositoVO);
					salir = true;
				}
			}
			else{
				if(depositoVO.getNroTrans() == depositoEnLista.getNroTrans()){
					
					this.lstDepositos.remove(i);
					salir = true;
				}
			}
			
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta el monedaVO en la lista
	 * de monedas de la vista
	 *
	 */
	private boolean existeDepositoenLista(Long cod_deposito)
	{
		int i =0;
		boolean esta = false;
		
		DepositoVO aux;
		
		while( i < this.lstDepositos.size() && !esta)
		{
			aux = this.lstDepositos.get(i);
			if(cod_deposito.equals(aux.getNroTrans()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridDepositos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridDepositos.getContainerDataSource()
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
		this.btnNuevoDeposito.setVisible(false);
		this.btnNuevoDeposito.setEnabled(false);
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
