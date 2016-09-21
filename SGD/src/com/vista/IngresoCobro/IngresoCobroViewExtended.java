package com.vista.IngresoCobro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import com.controladores.GrupoControlador;
import com.controladores.IngresoCobroControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Grupos.GrupoFormularioPermisos;
import com.vista.Grupos.GrupoFormularioPermisosExtended;
import com.vista.Grupos.GrupoViewAgregarFormularioExtended;
import com.vista.Grupos.GruposPanelExtended;

public class IngresoCobroViewExtended extends IngresoCobroViews implements IBusqueda{

	private BeanFieldGroup<IngresoCobroVO> fieldGroup;
	private ArrayList<IngresoCobroDetalleVO> lstDetalleVO; /*Lista de detalle del Cobro*/
	private ArrayList<IngresoCobroDetalleVO> lstDetalleAgregar; /*Lista de detalle a agregar*/
	private IngresoCobroControlador controlador;
	private String operacion;
	private IngresoCobroPanelExtended mainView;
	BeanItemContainer<IngresoCobroDetalleVO> container;
	private IngresoCobroDetalleVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
	
	private GrupoFormularioPermisos frmFormPermisos;
	MySub sub;
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings("unchecked")
	public IngresoCobroViewExtended(String opera, IngresoCobroPanelExtended main){
	
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	
	this.operacion = opera;
	this.mainView = main;
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstDetalleAgregar = new ArrayList<IngresoCobroDetalleVO>();
	
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
								VariablesPermisos.FORMULARIO_INGRESO_COBRO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
				
				IngresoCobroVO ingCobroVO = new IngresoCobroVO();				
				ingCobroVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
				ingCobroVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
				/*Codigo y serie docum se inicializan en constructor*/
				ingCobroVO.setNroDocum(Integer.parseInt(nroDocum.getValue()));
				ingCobroVO.setCodEmp(permisos.getCodEmp());
				ingCobroVO.setReferencia(referencia.getValue());
				
				//Moneda
				if(this.comboMoneda.getValue() != null){
					MonedaVO auxMoneda = new MonedaVO();
					auxMoneda = (MonedaVO) this.comboMoneda.getValue();
					ingCobroVO.setCodMoneda(auxMoneda.getCodMoneda());
					ingCobroVO.setNomMoneda(auxMoneda.getDescripcion());
					ingCobroVO.setSimboloMoneda(auxMoneda.getSimbolo());
				}
				
				ingCobroVO.setCodTitular(codTitular.getValue());
				ingCobroVO.setNomTitular(nomTitular.getValue());
				
				ingCobroVO.setOperacion(operacion);
				
				/*Ver los totales y tc*/
				//ingCobroVO.setImpTotMn(impTotMn);
				//ingCobroVO.setImpTotMo(impTotMn);
				//ingCobroVO.setTcMov(tcMov);
				
				ingCobroVO.setmPago((String)comboMPagos.getValue());
				
				if(ingCobroVO.getmPago().equals("transferencia"))
				{
					ingCobroVO.setCodDocRef("tranrec");
					ingCobroVO.setNroDocRef(Integer.parseInt(nroDocRef.getValue()));
					ingCobroVO.setSerieDocRef("0");
				}
				else if(ingCobroVO.getmPago().equals("cheque"))
				{
					ingCobroVO.setCodDocRef("cheqrec");
					ingCobroVO.setNroDocRef(Integer.parseInt(nroDocRef.getValue()));
					ingCobroVO.setSerieDocRef(serieDocRef.getValue());
					
				}else
				{
					ingCobroVO.setCodDocRef("0");
					ingCobroVO.setNroDocRef(0);
					ingCobroVO.setSerieDocRef("0");
				}
				
				if(((String)comboTipo.getValue()).equals("Caja"))
				{
					ingCobroVO.setCodCtaBco("0");
					ingCobroVO.setNomCtaBco("0");
				}
				else
				{
					ingCobroVO.setCodCtaBco(comboBancos.getValue().toString());
					/*Falta poner el nombre de la cuenta*/
				}
				
				
				ingCobroVO.setUsuarioMod(this.permisos.getUsuario());
				
				
				/*Si hay detalle nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstDetalleAgregar.size() > 0)
				{
					for (IngresoCobroDetalleVO f : this.lstDetalleAgregar) {
						
						/*Si no esta lo agregamos*/
						if(!this.existeFormularioenLista(f.getNroDocum()))
							this.lstDetalleVO.add(f);
					}
				}
					
								
				ingCobroVO.setDetalle(this.lstDetalleVO);
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
	
					this.controlador.insertarIngresoCobro(ingCobroVO, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Cobro");
					main.cerrarVentana();
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.modificarIngresoCobro(ingCobroVO, permisoAux);
					
					this.mainView.actulaizarGrilla(ingCobroVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Cobro");
					main.cerrarVentana();
					
				}
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoGrupoException| MemberGrupoException| ExisteGrupoException| InicializandoException| ConexionException | ModificandoGrupoException | ErrorInesperadoException | NoExisteGrupoException| NoTienePermisosException| ObteniendoPermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}
			
		});
	

	
	/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
		try {
			
			//this.codGrupo.setReadOnly(true);
			
			/*Inicializamos el Form en modo Edicion*/
			this.iniFormEditar();
			
			
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		/*Inicalizamos listener para boton de Agregar gastos a cobrar*/
		this.btnAgregar.addClickListener(click -> {
					
			try {
				
								
				BusquedaViewExtended form = new BusquedaViewExtended(this, new GastoVO());
				
				sub = new MySub("70%", "60%" );
				sub.setModal(true);
				sub.setVista(form);
				//sub.setWidth("50%");
				//sub.setHeight("50%");
				sub.center();
				
				String codCliente;/*Codigo del cliente para obtener los gastos a cobrar del mismo*/
				
				/*Obtenemos los formularios que no estan en el grupo
				 * para mostrarlos en la grilla para seleccionar*/
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					/*Si la operacion es nuevo, ponemos el  codGrupo vacio
					 * asi nos trae todos los grupos disponibles*/
					codCliente = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					codCliente = fieldGroup.getItemDataSource().getBean().getCodTitular());
				}
				/*Aca obtener los gastos con saldo para el cliente*/
				ArrayList<FormularioVO> lstForms = this.controlador.getFormulariosNoGrupo(codCliente, this.permisos.getCodEmp());
				form.setGrillaForms(lstForms);
				
				UI.getCurrent().addWindow(sub);

				}catch(Exception e)
				{
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
				if(formSelecccionado != null)
				{

					/*Recorremos los gastos del cobro
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstDetalleVO.size() && !esta)
					{
						if(lstDetalleVO.get(i).getNroDocum()==formSelecccionado.getNroDocum())
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstDetalleVO.remove(lstDetalleVO.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstDetalleVO);
						//lstFormularios.setContainerDataSource(container);
						this.actualizarGrillaContainer(container);
						
					}
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un gasto*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un gasto para quitar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			/*Inicializamos el listener de la grilla de gastos*/
			lstGastos.addSelectionListener(new SelectionListener() {
				
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    		if(lstGastos.getSelectedRow() != null){
			    			BeanItem<IngresoCobroDetalleVO> item = container.getItem(lstGastos.getSelectedRow());
					    	formSelecccionado = item.getBean(); /*Seteamos el formulario
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
			
				/* ESTO LO DEJAMOS COMENTADO POR EL MOMENTO, VER COMO SE EDITA SI SE EDITA EL GTO
			boolean esta = false;	
	
			try {
				
				//Verificamos que haya un formulario seleccionado para eliminar
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_EDITAR);
					
					sub = new MySub("35%", "30%" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else //De lo contrario mostramos mensaje que debe selcionar un formulario
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para editar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			//Listener boton permisos
			this.btnVerPermisos.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				//Verificamos que haya un formulario seleccionado para eliminar
				if(formSelecccionado != null)
				{
					
					this.frmFormPermisos = new GrupoFormularioPermisosExtended(this, formSelecccionado, Variables.OPERACION_LECTURA);
					
					sub = new MySub("53%", "40%" );
					sub.setModal(true);
					sub.setVista(this.frmFormPermisos);
					sub.center();
					
					UI.getCurrent().addWindow(sub);
					
				}
				else //De lo contrario mostramos mensaje que debe selcionar un formulario
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			
			*/
			});
			
			
	///////////////////
	}

	public  void inicializarForm(){
		
		this.controlador = new IngresoCobroControlador();
					
		this.fieldGroup =  new BeanFieldGroup<IngresoCobroVO>(IngresoCobroVO.class);
		
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
		
		this.comboTipo.setRequired(setear);
		this.comboTipo.setRequiredError("Es requerido");
		
		this.comboBancos.setRequired(setear);
		this.comboBancos.setRequiredError("Es requerido");
		
		this.comboCuentas.setRequired(setear);
		this.comboCuentas.setRequiredError("Es requerido");
		
		this.fecDoc.setRequired(setear);
		this.fecDoc.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
		this.comboMPagos.setRequired(setear);
		this.comboMPagos.setRequiredError("Es requerido");
		
		this.serieDocRef.setRequired(setear);
		this.serieDocRef.setRequiredError("Es requerido");
		
		this.nroDocRef.setRequired(setear);
		this.nroDocRef.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.importeMO.setRequired(setear);
		this.importeMO.setRequiredError("Es requerido");
		
		this.referencia.setRequired(setear);
		this.referencia.setRequiredError("Es requerido");
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<IngresoCobroVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		IngresoCobroVO ing = new IngresoCobroVO();
		ing = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(ing.getFechaMod());
		
		
		auditoria.setDescription(
			
			"Usuario: " + ing.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + ing.getOperacion());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (IngresoCobroDetalleVO detVO : this.lstDetalleVO) {
				container.addBean(detVO);
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.enableBotonAgregarQuitar();
		this.lstDetalleAgregar = new ArrayList<IngresoCobroDetalleVO>();
		this.lstDetalleVO = new ArrayList<IngresoCobroDetalleVO>();
		
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
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
		/*Agregar control si se puede editar si el mes esta abierto*/
		//TODO
		this.comboTipo.setReadOnly(false);
		
		this.comboBancos.setReadOnly(false);
		
		this.comboCuentas.setReadOnly(false);
		
		this.fecDoc.setReadOnly(false);
		
		this.fecValor.setReadOnly(false);
		
		this.comboMPagos.setReadOnly(false);
		
		this.serieDocRef.setReadOnly(false);
		
		this.nroDocRef.setReadOnly(false);
		
		this.comboMoneda.setReadOnly(false);
		
		this.importeMO.setReadOnly(false);
		
		this.referencia.setReadOnly(false);
	}
	
	/**
	 * Deshabilitamos el boton editar y permisos
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar y permisos
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
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
		this.comboTipo.setReadOnly(setear);
		
		this.comboBancos.setReadOnly(setear);
		
		this.comboCuentas.setReadOnly(setear);
		
		this.fecDoc.setReadOnly(setear);
		
		this.fecValor.setReadOnly(setear);
		
		this.comboMPagos.setReadOnly(setear);
		
		this.serieDocRef.setReadOnly(setear);
		
		this.nroDocRef.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.importeMO.setReadOnly(setear);
		
		this.referencia.setReadOnly(setear);
		
		this.codTitular.setReadOnly(setear);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
		nroDocRef.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
		importeMO.addValidator(new RegexpValidator("^[0-9]*(\\.[0-9]+)?$", true, "Dato numerico"));
		
        this.serieDocRef.addValidator(
                new StringLengthValidator(
                     " 4 caracteres máximo", 1, 4, false));
        
        this.referencia.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 255, false));
        
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
			if(this.nroDocRef.isValid() && this.importeMO.isValid()
					&& this.serieDocRef.isValid()
					&& this.referencia.isValid())
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
	public void setLstFormularios(ArrayList<IngresoCobroDetalleVO> lst)
	{
		this.lstDetalleVO = lst;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<IngresoCobroDetalleVO>(IngresoCobroDetalleVO.class);
		
		
		if(this.lstDetalleVO != null)
		{
			for (IngresoCobroDetalleVO det : this.lstDetalleVO) {
				container.addBean(det);
			}
		}

		//lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);
		
	}
	

	/**
	 *Agregamos los formularios seleccionados
	 */
	public void agregarFormulariosSeleccionados(ArrayList<IngresoCobroDetalleVO> lst)
	{

		IngresoCobroDetalleVO bean = new IngresoCobroDetalleVO();
        
        /*Hacemos un hash auxiliar por si se agrega mas de una vez
         * dejamos el ultimo agregado*/
        Hashtable<String, IngresoCobroDetalleVO> hForms = new Hashtable<String, IngresoCobroDetalleVO>();
        
		if(lst.size() > 0)
		{
			

			/*Si esta lo eliminamos y lo volvemos a ingresar, para
			 * que queden los ultimos cambios hechos*/
			/*
			for (FormularioVO formVO : lstForms) {
				
				if(hForms.containsKey(formVO.getCodigo())){
					
					hForms.remove(formVO.getCodigo());
					
				}
								
		        hForms.put(formVO.getCodigo(),formVO);
				
			}
			*/
			
			/*Recorremos hash e isertamos en lista de forms a agregar*/
			/*para no duplicar formularios*/
			for (IngresoCobroDetalleVO det : lst) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new IngresoCobroDetalleVO();
		        bean.copiar(det);
				
		        boolean saco = this.lstDetalleAgregar.remove(det);
		        this.lstDetalleVO.add(det);
		        this.lstDetalleAgregar.add(det);
		        
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
		
			com.vaadin.ui.Grid.HeaderRow filterRow = lstGastos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: lstGastos.getContainerDataSource()
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
	
	
	/////////////////////////////////////////////////NUEVOOOOOOOOOOOOOO
	
	
	/**
	 * Actualizamos Grilla si se agrega o modigfica un formulario y sus permisos
	 * desde GrupoViewExtended
	 * Es invocado desde las ventnas hijas
	 *
	 */
	public void actulaizarGrilla(IngresoCobroDetalleVO det)
	{

		/*Si esta el grupo en la lista, es una acutalizacion
		 * y modificamos el objeto en la lista*/
		if(this.existeFormularioenLista(det.getNroDocum()))
		{
			this.actualizarFormularioLista(det);
		}
		else  /*De lo contrario es uno nuevo y lo agregamos a la lista*/
		{
			this.lstDetalleVO.add(det);
		}
			
		/*Actualizamos la grilla*/
		this.container.removeAllItems();
		this.container.addAll(lstDetalleVO);
		
		//this.lstFormularios.setContainerDataSource(container);
		this.actualizarGrillaContainer(container);

	}
	
	
	/**
	 * Modificamos un grupoVO de la lista cuando
	 * se hace una acutalizacion de un Grupo
	 *
	 */
	private void actualizarFormularioLista(IngresoCobroDetalleVO det)
	{
		int i =0;
		boolean salir = false;
		
		IngresoCobroDetalleVO detEnLista;
		
		while( i < this.lstDetalleVO.size() && !salir)
		{
			detEnLista = this.lstDetalleVO.get(i);
			if(det.getNroDocum()== detEnLista.getNroDocum())
			{
				//this.lstGrupos.get(i).setNomGrupo(grupoVO.getNomGrupo());
				
				this.lstDetalleVO.get(i).copiar(det);

				salir = true;
			}
			
			i++;
		}
		
	}
	
	/**
	 * Retornanoms true si esta el grupoVO en la lista
	 * de grupos de la vista
	 *
	 */
	private boolean existeFormularioenLista(int nro)
	{
		int i =0;
		boolean esta = false;
		
		IngresoCobroDetalleVO aux;
		
		while( i < this.lstDetalleVO.size() && !esta)
		{
			aux = this.lstDetalleVO.get(i);
			if(nro==aux.getNroDocum())
			{
				esta = true;
			}
			
			i++;
		}
		
		return esta;
	}
	
	private void actualizarGrillaContainer(BeanItemContainer<IngresoCobroDetalleVO> container)
	{
		lstGastos.setContainerDataSource(container);
		
		//lstFormularios.getColumn("borrar").setHidable(true);
		
		lstGastos.getColumn("operacion").setHidden(true);
		lstGastos.getColumn("fechaMod").setHidden(true);
		lstGastos.getColumn("usuaioMod").setHidden(true);
		
		//lstFormularios.removeColumn("borrar");
		//lstFormularios.removeColumn("leer");
		//lstFormularios.removeColumn("nuevoEditar");
	}
	
	public void inicializarComboMoneda(String cod){
		
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			lstMonedas = this.controlador.getMonedas(permisosAux);
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedasObj.addBean(monedaVO);
			
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		this.comboMoneda.setValue(moneda);
	}
	
	
}
