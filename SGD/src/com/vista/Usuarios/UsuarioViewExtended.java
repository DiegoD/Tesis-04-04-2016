package com.vista.Usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.GrupoControlador;
import com.controladores.UsuarioControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.GrupoVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.UsuarioVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.MD5;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class UsuarioViewExtended extends UsuarioView implements IBusqueda{

	private static final long serialVersionUID = 1L;
	private BeanFieldGroup<UsuarioVO> fieldGroup;
	private UsuarioControlador controlador;
	private String operacion;
	private ArrayList<GrupoVO> lstGruposUsuario;
	private ArrayList<GrupoVO> lstGruposAgregar; /*Lista de Grupos a agregar*/
	private UsuariosPanelExtend mainView;
	private GrupoVO grupoSeleccionado; /*Variable utilizada cuando se selecciona
	  									un grupo, para poder quitarlo de la lista*/
	private MySub sub; 
	private BeanFieldGroup<UsuarioVO> fieldGroupAudit;
	
	GrupoControlador controladorGrupo;
	BeanItemContainer<GrupoVO> containerGrupo;
	private PermisosUsuario permisos; /*Permisos del usuario*/
	private String msj;
	
	String passOld;
	
	/**
	 * Constructor: recibe operación (nuevo, editar)
	 * También recibe la vista que lo llamó por parametro
	 */
	@SuppressWarnings("deprecation")
	public UsuarioViewExtended(String opera, UsuariosPanelExtend main)
	{
		/*Inicializamos los permisos para el usuario*/
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		operacion = opera;
		this.mainView = main;
		this.lstGruposAgregar = new ArrayList<GrupoVO>();
		sub = new MySub("500px", "550px" );
		sub.setModal(true);
		sub.center();
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
			try 
			{
				/*Inicializamos VO de permisos para el usuario, formulario y operacion
				 * para confirmar los permisos del usuario*/
				UsuarioPermisosVO permisoAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_USUARIO,
								VariablesPermisos.OPERACION_NUEVO_EDITAR);
				
				
				MD5 md5 = new MD5(); /*Para encriptar la contrasena*/
				
				if(this.fieldsValidos())
				{
					UsuarioVO usuarioVO = new UsuarioVO();
					usuarioVO.setUsuario(usuario.getValue().trim());
					usuarioVO.setActivo(activo.getValue());
					usuarioVO.setNombre(nombre.getValue().trim());
					usuarioVO.setPass(md5.getMD5Hash(pass.getValue().trim()));
					usuarioVO.setOperacion(operacion);
					usuarioVO.setActivo(activo.getValue());
					usuarioVO.setUsuarioMod(this.permisos.getUsuario());
					usuarioVO.setMail(mail.getValue());
					usuarioVO.setCodTit(Integer.valueOf(this.codTit.getValue().trim()));
					usuarioVO.setNomTit(this.nomTit.getValue());
					String empresa = this.permisos.getCodEmp();
					
					if(this.lstGruposAgregar.size() > 0)
					{
						for(GrupoVO g: this.lstGruposAgregar)
						{
							this.lstGruposUsuario.add(g);
						}
					}
					
					usuarioVO.setLstGrupos(this.lstGruposUsuario);
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO))	
					{	
						this.controlador.insertarUsuario(usuarioVO, permisoAux);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se ha guardado el Usuario");
						main.cerrarVentana();
						
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))
					{
						
						if(passOld.trim() == pass.getValue().trim()){
							usuarioVO.setPass(pass.getValue().trim());
						}
						
						this.controlador.modificarUsuario(usuarioVO, permisoAux);
						main.refreshGrilla(usuarioVO);
						Mensajes.mostrarMensajeOK("Se guardaron los cambios");
						main.cerrarVentana();
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(this.msj);
				}
				
			} 
			catch (  ExisteUsuarioException | ErrorInesperadoException| InsertandoUsuarioException| ConexionException| InicializandoException| ObteniendoPermisosException | NoTienePermisosException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			
			}
			
			
			
		});
		
		this.btnEditar.addClickListener(click -> {
			
			try
			{
				
				boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
				/*Inicializamos el Form en modo Edicion*/
				passOld = this.pass.getValue().trim();
				this.iniFormEditar(permisoNuevoEditar);		
				if(permisoNuevoEditar)
					this.enableBotonCliente();
	
			}
			catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		this.btnCancelar.addClickListener(click ->
		{
			main.cerrarVentana();
		});
		
		this.btnAgregar.addClickListener(click -> {
			try 
			{
				UsuarioViewAgregarGrupoExtend form = new UsuarioViewAgregarGrupoExtend(this);
				
				sub.setModal(true);
				sub.setVista(form);
				sub.center();
				sub.setDraggable(true);
				
				String usuario;
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					usuario = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					usuario = fieldGroup.getItemDataSource().getBean().getUsuario();
				}
				
				ArrayList<GrupoVO> lstGruposNoUsuario = this.controlador.getUsuariosNoGrupo(usuario, this.permisos.getCodEmp());
				form.setGrillaGrupos(lstGruposNoUsuario);
				UI.getCurrent().addWindow(sub);
			} 
			catch (Exception e) 
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			
		});
		
		/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(grupoSeleccionado != null)
				{

					/*Recorremos los formularios del grupo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstGruposUsuario.size() && !esta)
					{
						if(lstGruposUsuario.get(i).getCodGrupo().equals(grupoSeleccionado.getCodGrupo()))
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstGruposUsuario.remove(lstGruposUsuario.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					//if(esta)
					//{
						/*Actualizamos el container y la grilla*/
						containerGrupo.removeAllItems();
						containerGrupo.addAll(lstGruposUsuario);
						grillaGrupos.setContainerDataSource(containerGrupo);
					//}
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para quitar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
		
		this.btnBuscarCliente.addClickListener(click -> {
			BusquedaViewExtended form;
			
		
			form = new BusquedaViewExtended(this, new ClienteVO());
			
			
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<TitularVO> lstTitulares = new ArrayList<TitularVO>();
			ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
					
					lstClientes = this.controlador.getClientes(permisoAux);
					/*Quitamos el 1 no asignado, ya que esta la logica armada
					 * para el 0 no asignado*/
					int i = 0;
					boolean esta = false;
					ClienteVO cli;
					while(!esta && i < lstClientes.size()  ){
						cli = lstClientes.get(i);
						
						if(cli.getCodigo() == 1){
							
							lstClientes.remove(i);
							
							esta = true;
						}
						
						i++;
					}
				
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
					 ObteniendoClientesException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
			/*Agregamos el 0 no asignado*/
			ClienteVO noAsig = new ClienteVO();
			noAsig.setCodigo(0);
			noAsig.setNombre("No asignado");
			noAsig.setRazonSocial("No asignado");
		    Object ob = new Object();
		    ob = (Object)noAsig;
		    lst.add(ob);
		    
			
				Object obj;
				for (ClienteVO i: lstClientes) {
					obj = new Object();
					obj = (Object)i;
					lst.add(obj);
				}
			
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("550px", "780px" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		 codTit.addListener(new TextChangeListener() {
             @Override
             public void textChange(TextChangeEvent event) {
                
            	 if(!codTit.getValue().equals("0")){
            		 
            		 
            		 for (GrupoVO g : lstGruposUsuario) {
						
            			 
					}
            	 }
                
               
             }
         });
		
		grillaGrupos.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		if(grillaGrupos.getSelectedRow() != null){
		    			BeanItem<GrupoVO> item = containerGrupo.getItem(grillaGrupos.getSelectedRow());
				    	grupoSeleccionado = item.getBean(); /*Seteamos el formulario
				    	 									seleccionado para poder quitarlo*/
		    		}
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});

		/*Si es operacion nuevo habilitamos el boton para seleccionar cliente*/
		if(opera.equals(Variables.OPERACION_NUEVO)){
				this.enableBotonCliente();
		}
		
		this.nomTit.setEnabled(false);
	}
	
	private void inicializarForm(){
		
		this.controlador = new UsuarioControlador();
		this.controladorGrupo = new GrupoControlador();
		this.fieldGroup =  new BeanFieldGroup<UsuarioVO>(UsuarioVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		this.codTit.setEnabled(false);
		this.nomTit.setEnabled(false);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Chequeamos si tiene permiso de editar*/
			boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			this.codTit.setValue("0");
			this.nomTit.setValue("No asignado");
			
			/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
			if(permisoNuevoEditar)
			{
				/*Inicializamos al formulario como nuevo*/
				this.iniFormNuevo();
			}
			else{
			
				mainView.cerrarVentana();
				mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
			
			}
				
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
			/*Chequeamos si tiene permiso de editar*/
			boolean permisoLectura = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_LEER);
			
			/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
			if(permisoLectura)
			{
				/*Inicializamos formulario como editar*/
				this.iniFormLectura();
				
			}else{
			
				mainView.cerrarVentana();
				mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
			
			}
					
		}
		
		/*this.container = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		*/
		this.containerGrupo = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
	
		if(this.lstGruposUsuario != null )
		{
			for(GrupoVO grupoVO: this.lstGruposUsuario){
				containerGrupo.addBean(grupoVO);
			}
		}
		
	}
	
	private void setearValidaciones(boolean setear){
		
		this.nombre.setRequired(setear);
		this.nombre.setRequiredError("Es requerido");
		
		this.usuario.setRequired(setear);
		this.usuario.setRequiredError("Es requerido");
		
		this.pass.setRequired(setear);
		this.pass.setRequiredError("Es requerido");
		
		this.mail.setRequired(setear);
		this.mail.setRequiredError("Es requerido");
	}
	
	public void setDataSourceFormulario(BeanItem<UsuarioVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		UsuarioVO usu = new UsuarioVO();
		usu = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(usu.getFechaMod());
		
		auditoria.setDescription(
			  
		
			"Usuario: " + usu.getUsuarioMod() + "<br>" +
			"Fecha: " + fecha + "<br>" +
			"Operación: " + usu.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
			this.iniFormNuevo();
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		
		
		this.enableBotonAceptar();
		this.enableBotonCliente();
		this.disableBotonEditar(); 
		this.lstGruposAgregar = new ArrayList<GrupoVO>();
		this.lstGruposUsuario = new ArrayList<GrupoVO>();
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
		this.activo.setValue(true);	

	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar(boolean permisoNuevoEditar)
	{
		/*Verificamos que tenga permisos*/
		
		if(permisoNuevoEditar){
		
			//setea operación
			operacion = Variables.OPERACION_EDITAR;
			
			/*Oculatamos Editar y mostramos el de guardar*/
			this.enableBotonAceptar();
			this.disableBotonEditar();
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
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	private void enableBotonCliente()
	{
		this.btnBuscarCliente.setEnabled(true);
		this.btnBuscarCliente.setVisible(true);
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonEditar()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
		this.btnBuscarCliente.setVisible(false);
		this.btnBuscarCliente.setEnabled(false);
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.nombre.setReadOnly(setear);
		this.usuario.setReadOnly(setear);
		this.pass.setReadOnly(setear);
		this.activo.setReadOnly(setear);
		this.mail.setReadOnly(setear);
	
		this.codTit.setEnabled(false);
		this.nomTit.setEnabled(false);
				
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
		this.nombre.setReadOnly(false);
		this.pass.setReadOnly(false);
		this.activo.setReadOnly(false);
		this.mail.setReadOnly(false);
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_USUARIO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonEditar();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonEditar();
		}
		
		/*Deshabilitamos botn aceptar*/
		this.disableBotonAceptar();
		this.disableBotonBuscarCliente();
		this.disableBotonAgregarQuitar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
	}
	
	/**
	 * Habilitamos el boton editar
	 *
	 */
	private void enableBotonEditar()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
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
	
	private void disableBotonBuscarCliente()
	{
		this.btnBuscarCliente.setEnabled(false);
		this.btnBuscarCliente.setVisible(false);
	}
	
	private void disableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnQuitar.setEnabled(false);
		this.btnQuitar.setVisible(false);
	}
	
	private void enableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		this.btnQuitar.setEnabled(true);
		this.btnQuitar.setVisible(true);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.nombre.addValidator(
                new StringLengthValidator(
                     " 45 caracteres máximo", 1, 45, false));
        
        this.usuario.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.pass.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 45, false));
        
        this.mail.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        
        this.mail.addValidator(new EmailValidator("Mail no válido"));
        
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
		
		this.msj = "Verificar grupos no válidos";
		
		try
		{
			if(this.nombre.isValid() && this.usuario.isValid() && this.pass.isValid() && this.mail.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		String aux;
		aux = codTit.getValue().toString();
		
		if(!aux.equals("0")){
			/*Si el usuario es cliente (codTit != 0) solo puede tener este grupo asignado*/
			 for (GrupoVO g : containerGrupo.getItemIds()) {

				 if(!g.getNomGrupo().equals("CLIENTE"))	{
						 
						 valido = false;
						 
						 this.msj = "Solamente Grupo Cliente es admitido";
					 }
				
			}
		}
		return valido;
	}
	
	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstGruposUsuario(ArrayList<GrupoVO> lstGrupos)
	{
		this.lstGruposUsuario = lstGrupos;
		
		/*Seteamos la grilla con los formularios*/
		this.containerGrupo = 
				new BeanItemContainer<GrupoVO>(GrupoVO.class);
		
		
		if(this.lstGruposUsuario != null)
		{
			for (GrupoVO grupoVO : this.lstGruposUsuario) {
				containerGrupo.addBean(grupoVO);
			}
		}
		
		
		grillaGrupos.setContainerDataSource(containerGrupo);
		this.filtroGrilla();
		
		/*Ocultamos columna de la grilla de grupos*/
		this.ocultarColumnasGrillaGrupos();
		
		/*
			grillaGrupos.removeColumn("activo");
			grillaGrupos.removeColumn("usuarioMod");
			grillaGrupos.removeColumn("fechaMod");
			grillaGrupos.removeColumn("operacion");
			grillaGrupos.removeColumn("lstFormularios");
		*/
	}
	
	public void agregarGruposSeleccionados(ArrayList<GrupoVO> lstGrupos)
	{

		GrupoVO bean = new GrupoVO();
        
		if(lstGrupos.size() > 0)
		{
			for (GrupoVO grupoVO : lstGrupos) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new GrupoVO();
		        bean.setCodGrupo(grupoVO.getCodGrupo());
				bean.setNomGrupo(grupoVO.getNomGrupo());

				this.lstGruposAgregar.add(grupoVO);
		        this.containerGrupo.addBean(bean);
				
			}
		}
		
		grillaGrupos.setContainerDataSource(containerGrupo);
		
		/*Ocultamos columna de la grilla de grupos*/
		this.ocultarColumnasGrillaGrupos();
	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	private void filtroGrilla()
	{
		try
		{
		
			com.vaadin.ui.Grid.HeaderRow filterRow = grillaGrupos.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: grillaGrupos.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    // Have an input field to use for filter
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				    // Update filter When the filter input is changed
				    filterField.addTextChangeListener(change -> {
				        // Can't modify filters so need to replace
				    	this.containerGrupo.removeContainerFilters(pid);
		
				        // (Re)create the filter if necessary
				        if (! change.getText().isEmpty())
				        	this.containerGrupo.addContainerFilter(
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
	 * Ocultamos las columnas de auditoria de la grilla de grupos
	 */
	private void ocultarColumnasGrillaGrupos()
	{
	
		try{
			
			grillaGrupos.getColumn("activo").setHidden(true);
			grillaGrupos.getColumn("usuarioMod").setHidden(true);
			grillaGrupos.getColumn("fechaMod").setHidden(true);
			grillaGrupos.getColumn("operacion").setHidden(true);
			grillaGrupos.getColumn("lstFormularios").setHidden(true);
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
	}

	@Override
	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
			
		if(datos instanceof ClienteVO){
			
			ClienteVO clienteVO = (ClienteVO) datos;
			this.codTit.setValue(String.valueOf(clienteVO.getCodigo()));
			this.nomTit.setValue(clienteVO.getNombre());
		}
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
}
