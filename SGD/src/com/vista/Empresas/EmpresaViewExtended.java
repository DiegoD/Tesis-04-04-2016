package com.vista.Empresas;

import java.text.SimpleDateFormat;

import com.controladores.EmpresaControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.empresa.EmpresaUsuVO;
import com.vista.MD5;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class EmpresaViewExtended extends EmpresaView{

	private BeanFieldGroup<EmpresaUsuVO> fieldGroup;
	private EmpresaControlador controlador;
	private String operacion;
	private EmpresasPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 */
	public EmpresaViewExtended(String opera, EmpresasPanelExtended main){
		
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
									VariablesPermisos.FORMULARIO_EMPRESAS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
					
					
					EmpresaUsuVO empresaUsuVO = new EmpresaUsuVO();		
					
					empresaUsuVO.setCodEmp(codEmp.getValue().trim());
					empresaUsuVO.setNomEmp(nomEmp.getValue().trim());
					empresaUsuVO.setActivo(activo.getValue());
					empresaUsuVO.setUsuarioMod(this.permisos.getUsuario());
					empresaUsuVO.setOperacion(operacion);
					
					/*Datos del usuario administrador*/
					MD5 md5 = new MD5(); /*Para encriptar la contrasena*/
					empresaUsuVO.setUsuario(campo2.getValue().trim());
					empresaUsuVO.setPass(md5.getMD5Hash(campo1.getValue().trim()));
					
					
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarEmpresa(empresaUsuVO, permisoAux);
						
						this.mainView.actulaizarGrilla(empresaUsuVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado la empresa");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarEmpresa(empresaUsuVO, permisoAux);
						
						this.mainView.actulaizarGrilla(empresaUsuVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado la empresa");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
				} 
				catch (ConexionException | NoExisteEmpresaException | ModificandoEmpresaException | 
						ExisteEmpresaException | InicializandoException | InsertandoEmpresaException |
						 ErrorInesperadoException| ObteniendoPermisosException|  NoTienePermisosException| ExisteUsuarioException e) {
					
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
		
		this.controlador = new EmpresaControlador();
					
		this.fieldGroup =  new BeanFieldGroup<EmpresaUsuVO>(EmpresaUsuVO.class);
		
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
		
		this.codEmp.setRequired(setear);
		this.codEmp.setRequiredError("Es requerido");
		
		this.nomEmp.setRequired(setear);
		this.nomEmp.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Seteamos las validaciones del Formulario en modo Nuevo
	 * Este requiere tambien los campos del usuario
	 * pasamos un booleano para activarlos y desactivarlos
	 * EN modo LEER: las deshabilitamos (para que no aparezcan los asteriscos, etc)
	 * EN modo NUEVO: las habilitamos
	 * EN modo EDITAR: las habilitamos
	 *
	 */
	private void setearValidacionesNuevo(boolean setear){
		
		this.codEmp.setRequired(setear);
		this.codEmp.setRequiredError("Es requerido");
		
		this.nomEmp.setRequired(setear);
		this.nomEmp.setRequiredError("Es requerido");
		
		this.campo2.setRequired(true);
		this.campo2.setRequiredError("Es requerido");
		
		this.campo1.setRequired(true);
		this.campo1.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item ImpuestoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<EmpresaUsuVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		EmpresaUsuVO empresa = new EmpresaUsuVO();
		empresa = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(empresa.getFechaMod());
		
		
		auditoria.setDescription(
				"Usuario: " + empresa.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operación: " + empresa.getOperacion());
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		
		/*Deshabilitamos fields de usuario*/
		this.disableFieldsUsuario();
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			this.disableBotonLectura();

			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
			
			/*Deshabilitamos fields de usuario*/
			this.disableFieldsUsuario();
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_EMPRESAS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.activo.setValue(true);
		
		/*Habilitamos los campos del usuario administrador*/
		this.enableFieldsUsuario();
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidacionesNuevo(true);
		
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
		this.nomEmp.setReadOnly(false);
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
		this.codEmp.setReadOnly(setear);
		this.nomEmp.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codEmp.addValidator(
                new StringLengthValidator(
                     " 15 caracteres máximo", 1, 15, false));
        
        this.nomEmp.addValidator(
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
			if(this.codEmp.isValid() && this.nomEmp.isValid() )
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	private void disableFieldsUsuario()
	{
		this.campo2.setEnabled(false);
		this.campo2.setVisible(false);
		
		this.campo1.setEnabled(false);
		this.campo1.setVisible(false);
	}
	
	private void enableFieldsUsuario()
	{
		this.campo2.setEnabled(true);
		this.campo2.setVisible(true);
		
		this.campo1.setEnabled(true);
		this.campo1.setVisible(true);
	}
}
