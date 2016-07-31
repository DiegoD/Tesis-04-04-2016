package com.vista.Clientes;

import java.text.SimpleDateFormat;

import com.controladores.ClienteControlador;
import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.valueObject.ImpuestoVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Impuestos.ImpuestosPanelExtended;

public class ClienteViewExtended extends ClienteView{
	
	private BeanFieldGroup<ClienteVO> fieldGroup;
	private ClienteControlador controlador;
	private String operacion;
	private ClientesPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 */
	public ClienteViewExtended(String opera, ClientesPanelExtended main){
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		this.operacion = opera;
		this.mainView = main;
		
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
				
			try {
				
				/*Validamos los campos antes de invocar al controlador*/
				if(this.fieldsValidos())
				{
									
					ClienteVO clienteVO; 				
					clienteVO = fieldGroup.getItemDataSource().getBean();
					/*Ver si hay que poner campo a campo...*/
					
					
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarCliente(clienteVO, this.permisos.getCodEmp());
						
						this.mainView.actulaizarGrilla(clienteVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el Cliente");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.modificarCliente(clienteVO, this.permisos.getCodEmp());
						
						this.mainView.actulaizarGrilla(clienteVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado Cliente");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
				} 
				catch (InsertandoClienteException| ConexionException| ExisteClienteExeption| InicializandoException| ModificandoClienteException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
			});
		
			/*Inicalizamos listener para boton de Editar*/
			this.btnEditar.addClickListener(click -> {
					
				try {
				
					/*Inicializamos el Form en modo Edicion*/
					this.iniFormEditar();
				}
				catch(Exception e)	{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			this.cancelar.addClickListener(click -> {
				main.cerrarVentana();
			});
		
	}

	public  void inicializarForm(){
		
		this.controlador = new ClienteControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ClienteVO>(ClienteVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*Seteamos las validaciones de los fields*/
		this.agregarFieldsValidaciones();
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))	{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
		} 
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
		
		
		this.codigo.setRequired(setear);
		this.codigo.setRequiredError("Es requerido");
		
		this.nombre.setRequired(setear);
		this.nombre.setRequiredError("Es requerido");
		
		this.razonSocial.setRequired(setear);
		this.razonSocial.setRequiredError("Es requerido");
		
		this.codigoDoc.setRequired(setear);
		this.codigoDoc.setRequiredError("Es requerido");
		
		this.nombreDoc.setRequired(setear);
		this.nombreDoc.setRequiredError("Es requerido");
		
		this.numeroDoc.setRequired(setear);
		this.numeroDoc.setRequiredError("Es requerido");
				
		
		this.activo.setRequired(setear);
		this.activo.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item ImpuestoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ClienteVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		ClienteVO clienteVO = new ClienteVO();
		clienteVO = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(clienteVO.getFechaMod());
		
		
		auditoria.setDescription(
			    "<ul>"+
			    "  <li> Modificado por: " + clienteVO.getUsuarioMod() + "</li>"+
			    "  <li> Fecha: " + fecha + "</li>"+
			    "  <li> Operación: " + clienteVO.getOperacion() + "</li>"+
			    "</ul>");
		
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
		/*Habilitamos el boton de editar,
		 * deshabilitamos botn aceptar*/
		this.enableBotonesLectura();
		this.disableBotonAceptar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GRUPO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.activo.setValue(true);
		
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
		this.razonSocial.setReadOnly(false);
		this.codigoDoc.setReadOnly(false);
		this.nombreDoc.setReadOnly(false);
		this.numeroDoc.setReadOnly(false);
		this.nombre.setReadOnly(false);
		this.tel.setReadOnly(false);
		this.direccion.setReadOnly(false);
		this.mail.setReadOnly(false);
		this.activo.setReadOnly(false);
	}
	
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar 
	 *
	 */
	private void enableBotonesLectura()
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
		this.codigo.setReadOnly(setear);
		this.razonSocial.setReadOnly(setear);
		this.codigoDoc.setReadOnly(setear);
		this.nombreDoc.setReadOnly(setear);
		this.numeroDoc.setReadOnly(setear);
		this.nombre.setReadOnly(setear);
		this.tel.setReadOnly(setear);
		this.direccion.setReadOnly(setear);
		this.mail.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.razonSocial.addValidator(
                new StringLengthValidator(
                     " 45 caracteres máximo", 1, 45, false));
        
        this.nombre.addValidator(
                new StringLengthValidator(
                     " 45 caracteres máximo", 1, 45, false));
        
        this.numeroDoc.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 1, 20, false));
        
        this.tel.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 1, 20, false));
        
        this.direccion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        
        this.mail.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 1, 100, false));
        
        
        
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
		
		try
		{
			if(this.codigo.isValid() &&
				this.razonSocial.isValid() &&
				this.codigoDoc.isValid() &&
				this.nombreDoc.isValid() &&
				this.numeroDoc.isValid() &&
				this.nombre.isValid() &&
				this.tel.isValid() &&
				this.direccion.isValid() &&
				this.mail.isValid() 
			){
				valido = true;
			}
			
			
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	

}
