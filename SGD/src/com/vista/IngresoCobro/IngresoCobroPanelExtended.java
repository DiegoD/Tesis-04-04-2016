package com.vista.IngresoCobro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.BancoControlador;
import com.controladores.IngresoCobroControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.UsuarioPermisosVO;
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
	
	private BancoViewExtended form; 
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
						
							sub = new MySub("72%", "70%");
							form = new BancoViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<BancoVO>(BancoVO.class);
		
		//Obtenemos lista de bancos del sistema
		this.lstIngresoCobro = this.getBancos(); 
		
		for (BancoVO bcoVO : lstIngresoCobro) {
			container.addBean(bcoVO);
		}
		
		gridBancos.setContainerDataSource(container);
		
		//Quitamos las columnas de la grilla de auditoria
		this.ocultarColumnasGrilla();
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		gridBancos.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridBancos.getSelectedRow() != null){
		    			BeanItem<BancoVO> item = container.getItem(gridBancos.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
							
						form = new BancoViewExtended(Variables.OPERACION_LECTURA, BancosPanelExtended.this);
						//form.fieldGroup.setItemDataSource(item);
						sub = new MySub("72%","70%");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item);
						form.setLstCtas(item.getBean().getLstCtas());
						
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
	 * Obtenemos bancos del sistema
	 *
	 */
	private ArrayList<BancoVO> getBancos(){
		
		ArrayList<BancoVO> lstBancos = new ArrayList<BancoVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_BANCOS,
							VariablesPermisos.OPERACION_LEER);

			
			lstBancos = controlador.getBancosTodos(permisoAux);

		} catch (InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoBancosException | ObteniendoCuentasBcoException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstBancos;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un banco
	 * desde BancoViewExtended
	 *
	 */
	public void actulaizarGrilla(BancoVO bancoVO)
	{

		/*Si esta el banco en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeBancoenLista(bancoVO.getCodigo()))
		{
			this.actualizarBancoenLista(bancoVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstIngresoCobro.add(bancoVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstIngresoCobro);
		
		this.gridBancos.setContainerDataSource(container);

	}
	
	
	/**
	 * Modificamos un bancoVO de la lista cuando
	 * se hace una acutalizacion de un Banco
	 *
	 */
	private void actualizarBancoenLista(BancoVO bancoVO)
	{
		int i =0;
		boolean salir = false;
		
		BancoVO bancoEnLista;
		
		while( i < this.lstIngresoCobro.size() && !salir)
		{
			bancoEnLista = this.lstIngresoCobro.get(i);
			if(bancoVO.getCodigo().equals(bancoEnLista.getCodigo()))
			{
				this.lstIngresoCobro.get(i).copiar(bancoVO);

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
	private boolean existeBancoenLista(String codBanco)
	{
		int i =0;
		boolean esta = false;
		
		BancoVO aux;
		
		while( i < this.lstIngresoCobro.size() && !esta)
		{
			aux = this.lstIngresoCobro.get(i);
			if(codBanco.equals(aux.getCodigo()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridBancos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridBancos.getContainerDataSource()
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
		gridBancos.getColumn("fechaMod").setHidden(true);
		gridBancos.getColumn("usuarioMod").setHidden(true);
		gridBancos.getColumn("operacion").setHidden(true);
		gridBancos.getColumn("activo").setHidden(true);
		gridBancos.getColumn("lstCtas").setHidden(true);
	
		gridBancos.getColumn("tel").setHidden(true);
		gridBancos.getColumn("codEmp").setHidden(true);
		gridBancos.getColumn("contacto").setHidden(true);
		gridBancos.getColumn("direccion").setHidden(true);
	}

}
