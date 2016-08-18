package com.vista.CodigosGeneralizados;

import java.text.SimpleDateFormat;

import com.controladores.CodigoGeneralizadoControlador;
import com.controladores.EmpresaControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.NoExisteCodigoException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.EmpresaVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Empresas.EmpresasPanelExtended;

public class CodigoGeneralizadoViewExtended extends CodigoGeneralizadoView{
	
	private BeanFieldGroup<CodigoGeneralizadoVO> fieldGroup;
	private CodigoGeneralizadoControlador controlador;
	private String operacion;
	private CodigosGeneralizadosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 */
	public CodigoGeneralizadoViewExtended(String opera, CodigosGeneralizadosPanelExtended main){
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
									VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);

					
					
					CodigoGeneralizadoVO codigoGeneralizadoVO = new CodigoGeneralizadoVO();		
					
					codigoGeneralizadoVO.setCodigo(codigo.getValue().trim());
					codigoGeneralizadoVO.setValor(valor.getValue().trim());
					codigoGeneralizadoVO.setDescripcion(descripcion.getValue().trim());
					codigoGeneralizadoVO.setUsuarioMod(this.permisos.getUsuario());
					codigoGeneralizadoVO.setOperacion(operacion);
					
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarCodigoGeneralizado(codigoGeneralizadoVO, permisoAux);
						
						this.mainView.actulaizarGrilla(codigoGeneralizadoVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el código generalizado");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarCodigoGeneralizado(codigoGeneralizadoVO, permisoAux);
						
						this.mainView.actulaizarGrilla(codigoGeneralizadoVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado el código generalizado");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
				
			} 
			catch (ConexionException | NoExisteCodigoException | ModificandoCodigoException | 
					ExisteCodigoException | InicializandoException | InsertandoCodigoException |
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
		
		this.controlador = new CodigoGeneralizadoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<CodigoGeneralizadoVO>(CodigoGeneralizadoVO.class);
		
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
		
		this.codigo.setRequired(setear);
		this.codigo.setRequiredError("Es requerido");
		
		this.valor.setRequired(setear);
		this.valor.setRequiredError("Es requerido");
		
		this.descripcion.setRequired(setear);
		this.descripcion.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item CodigoGeneralizadoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<CodigoGeneralizadoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		CodigoGeneralizadoVO codigoGeneralizado = new CodigoGeneralizadoVO();
		codigoGeneralizado = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(codigoGeneralizado.getFechaMod());
		
		
		auditoria.setDescription(
			    "<ul>"+
			    "  <li> Modificado por: " + codigoGeneralizado.getUsuarioMod() + "</li>"+
			    "  <li> Fecha: " + fecha + "</li>"+
			    "  <li> Operación: " + codigoGeneralizado.getOperacion() + "</li>"+
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_CODIGOS_GENERALIZADOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		
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
		this.valor.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codigo.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.valor.addValidator(
                new StringLengthValidator(
                        " 255 caracteres máximo", 1, 255, false));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 255 caracteres máximo", 1, 255, false));
        
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
			if(this.codigo.isValid() && this.valor.isValid() && this.descripcion.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
}
