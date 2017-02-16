package com.vista.Cuentas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import com.controladores.CuentaControlador;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cuenta.CuentaVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class CuentaViewExtended extends CuentaView{

	private BeanFieldGroup<CuentaVO> fieldGroup;
	private ArrayList<RubroVO> lstRubrosVO; /*Lista de Formularios del Grupo*/
	private ArrayList<RubroVO> lstRubrosAgregar; /*Lista de Formularios a agregar*/
	private CuentaControlador controlador;
	private String operacion;
	private CuentasPanelExtended mainView;
	BeanItemContainer<RubroVO> container;
	private RubroVO rubroSelecccionado; 
	
	private CuentaRubroPermisos rubroFormPermisos;
	MySub sub;
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	public CuentaViewExtended(String opera, CuentasPanelExtended main){
		
		/*Inicializamos los permisos para el usuario*/
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		
		this.operacion = opera;
		this.mainView = main;
		
		/*Esta lista es utilizada solamente para los formularios nuevos
		 * agregados*/
		this.lstRubrosAgregar = new ArrayList<RubroVO>();
		
		this.inicializarForm();
	
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
				
			try {
				
				/*Validamos los campos antes de invocar al controlador*/
				if(this.fieldsValidos())
				{
					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					UsuarioPermisosVO permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_CUENTAS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);				
					
					
					CuentaVO cuentaVO = new CuentaVO();		
					
					cuentaVO.setCodCuenta(codCuenta.getValue().trim());
					cuentaVO.setDescripcion(descripcion.getValue().trim());
					cuentaVO.setOperacion(operacion);
					cuentaVO.setActivo(activo.getValue());
					cuentaVO.setUsuarioMod(this.permisos.getUsuario());
					
					
					/*Si hay algun rubro nuevo agregado
					 * lo agregamos a la lista de rubro*/
					if(this.lstRubrosAgregar.size() > 0)
					{
						for (RubroVO r : this.lstRubrosAgregar) {
							
							/*Si no esta lo agregamos*/
							if(!this.existeRubroenLista(r.getcodRubro()))
								this.lstRubrosVO.add(r);
						}
					}
						
					cuentaVO.setLstRubros(this.lstRubrosVO);
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO))	
					{	
		
						this.controlador.insertarCuenta(cuentaVO, permisoAux);
						this.mainView.actulaizarGrilla(cuentaVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado la cuenta");
						main.cerrarVentana();
					
					}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
					{

						this.controlador.editarCuenta(cuentaVO, permisoAux);
						this.mainView.actulaizarGrilla(cuentaVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado la cuenta");
						main.cerrarVentana();
						
					}
					
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
			} 
			
			//ver
			catch (Exception e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
					
			}
				
		});
		

		
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
			try {
				
				/*Inicializamos el Form en modo Edicion*/
				this.iniFormEditar();
			}
			catch(Exception e){
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
			
		/*Inicalizamos listener para boton de Agregar Rubro*/
		this.btnAgregar.addClickListener(click -> {
					
			try {
				
				CuentaViewAgregarRubroExtended form = new CuentaViewAgregarRubroExtended(this);
				
				sub = new MySub("400px","850px");
				sub.setModal(true);
				sub.setVista(form);
				//sub.setWidth("50%");
				//sub.setHeight("50%");
				sub.center();
				
				String codCuenta;/*Codigo de la cuenta para obtener los rubros del mismo*/
				
				/*Obtenemos los formularios que no estan en el grupo
				 * para mostrarlos en la grilla para seleccionar*/
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					/*Si la operacion es nuevo, ponemos el  codGrupo vacio
					 * asi nos trae todos los grupos disponibles*/
					codCuenta = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					codCuenta = fieldGroup.getItemDataSource().getBean().getCodCuenta();
				}
				
				ArrayList<RubroVO> lstRubrosB = this.controlador.getRubrosNoCuenta(codCuenta, this.permisos.getCodEmp());
						
				form.setGrillaRubros(lstRubrosB);
				
				UI.getCurrent().addWindow(sub);

			}
			catch(Exception e){
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
				
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
				
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
			
		boolean esta = false;	

		try {
			
			/*Verificamos que haya un formulario seleccionado para
			 * eliminar*/
			if(rubroSelecccionado != null)
			{

				/*Recorremos los rubros de la cuenta
				 * y buscamos el seleccionarlo para quitarlo*/
				int i = 0;
				while(i < lstRubrosVO.size() && !esta)
				{
					if(lstRubrosVO.get(i).getcodRubro().equals(rubroSelecccionado.getcodRubro()))
					{
						/*Quitamos el formulario seleccionado de la lista*/
						lstRubrosVO.remove(lstRubrosVO.get(i));
						
						esta = true;
					}

					i++;
				}
				
				/*Si lo encontro en la grilla*/
				if(esta)
				{
					/*Actualizamos el container y la grilla*/
					container.removeAllItems();
					container.addAll(lstRubrosVO);
					//lstFormularios.setContainerDataSource(container);
					this.actualizarGrillaContainer(container);
					
				}
				
			}
			else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
			{
				Mensajes.mostrarMensajeError("Debe seleccionar un rubro para quitar");
			}
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
				
//		/*Inicializamos el listener de la grilla de formularios*/
		this.lstRubros.addSelectionListener(new SelectionListener() {
			
		    @Override
		    public void select(SelectionEvent event) {
		       
		    	try{
		    		if(lstRubros.getSelectedRow() != null){
		    			BeanItem<RubroVO> item = container.getItem(lstRubros.getSelectedRow());
				    	rubroSelecccionado = item.getBean(); /*Seteamos el formulario
			    	 									seleccionado para poder quitarlo*/
		    		}
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }

		});
				
				
				
		/*Listener boton editar de la grilla de formularios*/
		this.btnEditarForm.addClickListener(click -> {
			
		boolean esta = false;	

		try {
			
			/*Verificamos que haya un formulario seleccionado para
			 * eliminar*/
			if(rubroSelecccionado != null)
			{
				
				this.rubroFormPermisos = new CuentaRubroPermisosExtended(this, rubroSelecccionado, Variables.OPERACION_EDITAR);
				
				sub = new MySub("350px", "400px" );
				sub.setModal(true);
				sub.setVista(this.rubroFormPermisos);
				sub.center();
				
				UI.getCurrent().addWindow(sub);
				
			}
			else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
			{
				Mensajes.mostrarMensajeError("Debe seleccionar un rubro para editar");
			}
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
				
		/*Listener boton permisos*/
		this.btnVerPermisos.addClickListener(click -> {
			
		boolean esta = false;	

		try {
			
			/*Verificamos que haya un formulario seleccionado para
			 * eliminar*/
			if(rubroSelecccionado != null)
			{
				
				this.rubroFormPermisos = new CuentaRubroPermisosExtended(this, rubroSelecccionado, Variables.OPERACION_LECTURA);
				
				sub = new MySub("350px", "400px" );
				sub.setModal(true);
				sub.setVista(this.rubroFormPermisos);
				sub.center();
				
				UI.getCurrent().addWindow(sub);
				
			}
			else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
			{
				Mensajes.mostrarMensajeError("Debe seleccionar un rubro");
			}
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
	}
	
	public  void inicializarForm(){
		
		this.controlador = new CuentaControlador();
					
		this.fieldGroup =  new BeanFieldGroup<CuentaVO>(CuentaVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
	
		}else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
			
			/*Agregamos los filtros a la grilla*/
			this.filtroGrilla();
		} 
		/*LA OPERACION EDITAR ES DESDE EL DE LECTURA*/
	}


	/**
	 * Seteamos las validaciones del Formulario
	 * pasamos un booleano para activarlos y desactivarlos
	 * EN modo LEER: las deshabilitamos (para que no aparezcan los asteriscos, etc)
	 * EN modo NUEVO: las habilitamos
	 * EN modo EDITAR: las habilitamos
	 *
	 */
	private void setearValidaciones(boolean setear){
		
		this.codCuenta.setRequired(setear);
		this.codCuenta.setRequiredError("Es requerido");
		this.descripcion.setRequired(setear);
		this.descripcion.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item CuentaVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<CuentaVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		CuentaVO cuenta = new CuentaVO();
		cuenta = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cuenta.getFechaMod());
		
		
		auditoria.setDescription(
			
			"Usuario: " + cuenta.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + cuenta.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
				
	}
	
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		/*Deshabilitamos botn aceptar*/
		this.disableBotonAceptar();
		this.disableBotonAgregarQuitar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<RubroVO>(RubroVO.class);
		
		
		if(this.lstRubrosVO != null)
		{
			for (RubroVO rubroVO : this.lstRubrosVO) {
				container.addBean(rubroVO);
			}
		}
		
		
		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
						
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar rubros*/
			this.enableBotonAceptar();
			this.disableBotonLectura();
			this.enableBotonAgregarQuitar();
			
			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
		}
		else{
			
			/*Mostramos mensaje Sin permisos para operacion*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CUENTAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.enableBotonAgregarQuitar();
		this.lstRubrosAgregar = new ArrayList<RubroVO>();
		this.lstRubrosVO = new ArrayList<RubroVO>();
		this.activo.setValue(true);
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<RubroVO>(RubroVO.class);
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
	}
	
	/**
	 * Dejamos setear los texFields correspondientes, 
	 *  
	 * Solamente aquellos campos posibles de editar
	 * EJ: el codigo no se deja editar
	 *
	 */
	private void setearFieldsEditar()
	{
		this.descripcion.setReadOnly(false);
	}
	
	/**
	 * Deshabilitamos el boton editar y permisos
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
		
		this.btnVerPermisos.setEnabled(false);
		this.btnVerPermisos.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar y permisos
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
		this.btnVerPermisos.setEnabled(true);
		this.btnVerPermisos.setVisible(true);
		
	}
	
	/**
	 * Habilitamos los botones de agregar y quitar
	 *
	 */
	private void enableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		this.btnQuitar.setEnabled(true);
		this.btnQuitar.setVisible(true);
		
		this.btnEditarForm.setEnabled(true);
		this.btnEditarForm.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnQuitar.setEnabled(false);
		this.btnQuitar.setVisible(false);
		
		this.btnEditarForm.setEnabled(false);
		this.btnEditarForm.setVisible(false);
	}
	
	/**
	 * Deshabilitamos el boton aceptar
	 *
	 */
	private void disableBotonAceptar()
	{
		this.aceptar.setEnabled(false);
		this.aceptar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codCuenta.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codCuenta.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 45, false));
        
	}
	
	
	/**
	 * Nos retorna true si los campos
	 * son válidos, se debe invocar antes
	 * de consumir al controlador
	 *
	 */
	private boolean fieldsValidos()
	{
		boolean valido = false;
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
		try
		{
			if(this.codCuenta.isValid() && this.descripcion.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}

	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstFormularios(ArrayList<RubroVO> lstRubros)
	{
		this.lstRubrosVO = lstRubros;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<RubroVO>(RubroVO.class);
		
		
		if(this.lstRubrosVO != null)
		{
			for (RubroVO rubroVO : this.lstRubrosVO) {
				container.addBean(rubroVO);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos los rubros seleccionados
	 */
	public void agregarRubrosSeleccionados(ArrayList<RubroVO> lstRubros)
	{

        RubroVO bean = new RubroVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, RubroVO> hRubros = new Hashtable<String, RubroVO>();
        
		if(lstRubros.size() > 0)
		{
			

			/*Recorremos hash e isertamos en lista de rubros a agregar*/
			/*para no duplicar rubros*/
			for (RubroVO rubroVO : lstRubros) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new RubroVO();
				bean.setcodRubro(rubroVO.getcodRubro());
				bean.setDescripcion(rubroVO.getDescripcion());
				bean.setOficina(rubroVO.isOficina());
				bean.setProceso(rubroVO.isProceso());
				bean.setPersona(rubroVO.isPersona());
				
		        /*Por ESTO*/
		        boolean saco = this.lstRubrosAgregar.remove(rubroVO);
		        this.lstRubrosVO.add(rubroVO);
		        this.lstRubrosAgregar.add(rubroVO);
				
				this.container.addBean(bean);
			}
			
		}
		
		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	/*Agregamos filtro en la grilla de formularios*/
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = lstRubros.appendHeaderRow();
			
			
	
			// Set up a filter for all columns
			for (Object pid: lstRubros.getContainerDataSource()
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
	
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un rubro y sus permisos
	 * desde RubroViewExtended
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(RubroVO rubroVO)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeRubroenLista(rubroVO.getcodRubro()))
		{
			this.actualizarRubroLista(rubroVO);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstRubrosVO.add(rubroVO);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstRubrosVO);
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un rubroVO de la lista cuando
	 * se hace una acutalizacion de una cuenta
	 *
	 */
	private void actualizarRubroLista(RubroVO rubroVO)
	{
		int i =0;
		boolean salir = false;
		
		RubroVO rubroEnLista;
		
		while( i < this.lstRubrosVO.size() && !salir)
		{
			rubroEnLista = this.lstRubrosVO.get(i);
			if(rubroVO.getcodRubro().equals(rubroEnLista.getcodRubro()))
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstRubrosVO.get(i).copiar(rubroVO);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el rubroVO en la lista
	 * de cuentas de la vista
	 *
	 */
	private boolean existeRubroenLista(String codRubro)
	{
		int i =0;
		boolean esta = false;
		
		RubroVO aux;
		
		while( i < this.lstRubrosVO.size() && !esta)
		{
			aux = this.lstRubrosVO.get(i);
			if(codRubro.equals(aux.getcodRubro()))
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void actualizarGrillaContainer(BeanItemContainer<RubroVO> container)
	{
		lstRubros.setContainerDataSource(container);
		
		//lstFormularios.getColumn("borrar").setHidable(true);
		
		lstRubros.getColumn("proceso").setHidden(true);
		lstRubros.getColumn("persona").setHidden(true);
		lstRubros.getColumn("oficina").setHidden(true);
		lstRubros.getColumn("activo").setHidden(true);
		lstRubros.getColumn("facturable").setHidden(true);
		lstRubros.getColumn("codigoImpuesto").setHidden(true);
		lstRubros.getColumn("descripcionImpuesto").setHidden(true);
		lstRubros.getColumn("porcentajeImpuesto").setHidden(true);
		lstRubros.getColumn("activoImpuesto").setHidden(true);
		lstRubros.getColumn("descripcionTipoRubro").setHidden(true);
		lstRubros.getColumn("codTipoRubro").setHidden(true);
		lstRubros.getColumn("fechaMod").setHidden(true);
		lstRubros.getColumn("usuarioMod").setHidden(true);
		lstRubros.getColumn("operacion").setHidden(true);
		
	}
	
}
