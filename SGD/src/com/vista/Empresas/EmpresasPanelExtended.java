package com.vista.Empresas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.controladores.EmpresaControlador;
import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
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
import com.valueObject.empresa.EmpresaUsuVO;
import com.valueObject.empresa.EmpresaVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class EmpresasPanelExtended extends EmpresasPanel{
	
	private EmpresaViewExtended form; 
	private ArrayList<EmpresaVO> lstEmpresas; /*Lista con los empresas*/
	private BeanItemContainer<EmpresaVO> container;
	private EmpresaControlador controlador;
	PermisosUsuario permisos;
	MySub sub = new MySub("65%", "65%");
	
	public EmpresasPanelExtended(){
		
		controlador = new EmpresaControlador();
		this.lstEmpresas = new ArrayList<EmpresaVO>();
		
		String usuario = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario");
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Verificamos que el usuario tenga permisos de lectura para mostrar la vista*/
		boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_LEER);
		
		if(permisoLectura){
	        
			try {
				
				this.inicializarGrilla();
				
				/*Para el boton de nuevo, verificamos que tenga permisos de nuevoEditar*/
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				if(permisoNuevoEditar){
				
					this.btnNuevaEmpresa.addClickListener(click -> {
						
						sub = new MySub("60%","37%");
						form = new EmpresaViewExtended(Variables.OPERACION_NUEVO, this);
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
				new BeanItemContainer<EmpresaVO>(EmpresaVO.class);
		
		//Obtenemos lista de empresas del sistema
		try {
			this.lstEmpresas = this.getEmpresas();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (EmpresaVO empresaVO : lstEmpresas) {
			container.addBean(empresaVO);
		}
		
		
		this.gridEmpresas.setContainerDataSource(container);
		
		gridEmpresas.removeColumn("activo");
		gridEmpresas.removeColumn("fechaMod");
		gridEmpresas.removeColumn("usuarioMod");
		gridEmpresas.removeColumn("operacion");
		
		/*Agregamos los filtros a la grilla*/
		this.filtroGrilla();
		
		
		gridEmpresas.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		
		    		if(gridEmpresas.getSelectedRow() != null){
		    			BeanItem<EmpresaVO> item = container.getItem(gridEmpresas.getSelectedRow());
				    	
				    	/*Puede ser null si accedemos luego de haberlo agregado, ya que no va a la base*/
				    	if(item.getBean().getFechaMod() == null)
				    	{
				    		item.getBean().setFechaMod(new Timestamp(System.currentTimeMillis()));
				    	}
						
				    	/*Creamos este VO para pasarselo al view ya que es su feildGroup*/
				    	EmpresaUsuVO empUsu = new EmpresaUsuVO(item.getBean().getCodEmp(),
				    											item.getBean().getNomEmp(), 
				    											item.getBean().isActivo());
				    	empUsu.setFechaMod(item.getBean().getFechaMod());
				    	empUsu.setOperacion(item.getBean().getOperacion());
				    	empUsu.setUsuarioMod(item.getBean().getUsuarioMod());
				    	
				    	BeanItem<EmpresaUsuVO> item2 = new BeanItem<EmpresaUsuVO>(empUsu);
				    	
				    	form = new EmpresaViewExtended(Variables.OPERACION_LECTURA, EmpresasPanelExtended.this);
				    	sub = new MySub("50%","32%");
						sub.setModal(true);
						sub.setVista(form);
						/*ACA SETEAMOS EL FORMULARIO EN MODO LEECTURA*/
						form.setDataSourceFormulario(item2);
						
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
	 * Obtenemos empresas del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<EmpresaVO> getEmpresas() throws Exception  {
		
		ArrayList<EmpresaVO> lstEmpresas = new ArrayList<EmpresaVO>();

		try {
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_EMPRESAS,
							VariablesPermisos.OPERACION_LEER);

			
			lstEmpresas = controlador.getEmpresas(permisoAux);
			
		} 
		catch (ObteniendoEmpresasException | InicializandoException | ConexionException| ObteniendoPermisosException| NoTienePermisosException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
			
		return lstEmpresas;
	}
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica una empresa
	 * desde EmpresaViewExtended
	 *
	 */
	public void actulaizarGrilla(EmpresaVO empresaVO)
	{

		/*Si esta la empresa en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeEmpresaenLista(empresaVO.getCodEmp()))
		{
			this.actualizarEmpresaenLista(empresaVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstEmpresas.add(empresaVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(this.lstEmpresas);
		
		this.gridEmpresas.setContainerDataSource(container);

	}
	
	/**
	 * Modificamos una empresa de la lista cuando
	 * se hace una acutalizacion de una empresa
	 *
	 */
	private void actualizarEmpresaenLista(EmpresaVO empresaVO)
	{
		int i =0;
		boolean salir = false;
		
		EmpresaVO empresaEnLista;
		
		while( i < this.lstEmpresas.size() && !salir)
		{
			empresaEnLista = this.lstEmpresas.get(i);
			
			if(empresaVO.getCodEmp().equals(empresaEnLista.getCodEmp())){
				
				this.lstEmpresas.get(i).copiar(empresaVO);
				salir = true;
			}
			
			i++;
		}
	}
	
	/**
	 * Retornanoms true si esta la empresa en la lista
	 * de empresas de la vista
	 *
	 */
	private boolean existeEmpresaenLista(String cod_empresa)
	{
		int i =0;
		boolean esta = false;
		
		EmpresaVO aux;
		
		while( i < this.lstEmpresas.size() && !esta)
		{
			aux = this.lstEmpresas.get(i);
			if(cod_empresa.equals(aux.getCodEmp()))
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = gridEmpresas.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: gridEmpresas.getContainerDataSource()
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
		this.btnNuevaEmpresa.setVisible(false);
		this.btnNuevaEmpresa.setEnabled(false);
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
