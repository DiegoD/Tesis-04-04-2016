package com.vista.Impuestos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.GrupoControlador;
import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class ImpuestoViewExtended extends ImpuestoView{
	
	private BeanFieldGroup<ImpuestoVO> fieldGroup;
	private ImpuestoControlador controlador;
	private String operacion;
	private ImpuestosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 */
	public ImpuestoViewExtended(String opera, ImpuestosPanelExtended main){
		
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
					
					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					UsuarioPermisosVO permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_IMPUESTO,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
									
					ImpuestoVO impuestoVO = new ImpuestoVO();		
					
					impuestoVO.setcodImpuesto(codImpuesto.getValue().trim());
					impuestoVO.setDescripcion(descripcion.getValue().trim());
					
					String aux = porcentaje.getValue().toString().trim().replace(",", ".");
					impuestoVO.setPorcentaje(Float.parseFloat(aux));
					impuestoVO.setActivo(activo.getValue());
					impuestoVO.setUsuarioMod(this.permisos.getUsuario());
					impuestoVO.setOperacion(operacion);
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarImpuesto(impuestoVO, permisoAux);
						
						this.mainView.actulaizarGrilla(impuestoVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el Impuesto");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarImpuesto(impuestoVO, permisoAux);
						
						this.mainView.actulaizarGrilla(impuestoVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado el Grupo");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
				} 
				catch (ConexionException | NoExisteImpuestoException | ModificandoImpuestoException | ExisteImpuestoException | 
						 InicializandoException | InsertandoImpuestoException |
						 ErrorInesperadoException| ObteniendoPermisosException| NoTienePermisosException e) {
					
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
		
		this.controlador = new ImpuestoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<ImpuestoVO>(ImpuestoVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
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
		
		this.codImpuesto.setRequired(setear);
		this.codImpuesto.setRequiredError("Es requerido");
		
		this.descripcion.setRequired(setear);
		this.descripcion.setRequiredError("Es requerido");
		
		this.porcentaje.setRequired(setear);
		this.porcentaje.setRequiredError("Es requerido");
		
		this.activo.setRequired(setear);
		this.activo.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item ImpuestoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<ImpuestoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		ImpuestoVO impuesto = new ImpuestoVO();
		impuesto = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(impuesto.getFechaMod());
		
		
		auditoria.setDescription(
			
				"Usuario: " + impuesto.getUsuarioMod() + "<br>" +
				"Fecha: " + fecha + "<br>" +
				"Operación: " + impuesto.getOperacion());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_IMPUESTO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		/*Deshabilitamos botn aceptar*/
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
		this.operacion = Variables.OPERACION_NUEVO;
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
		this.descripcion.setReadOnly(false);
		this.porcentaje.setReadOnly(false);
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
		this.codImpuesto.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);
		this.porcentaje.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codImpuesto.addValidator(
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
			if(this.codImpuesto.isValid() && this.descripcion.isValid() && this.porcentaje.isValid() && this.validarPorcentaje())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.WARNING_CAMPOS_NO_VALIDOS);
		}
		
		
		
		return valido;
	}
	
	private boolean validarPorcentaje(){
		
		boolean ok = true;
		/*
		float aux = 0;
		
		String s = this.porcentaje.getValue().replace(",", ".");
		
		try{
		aux = Float.parseFloat(s);
		this.porcentaje.setValue(s);
				
		}catch(Exception e){
			ok = false;
		}
		*/
		return ok;
	}
	
	
	
}
