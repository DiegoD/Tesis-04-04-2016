package com.vista.Bancos;

import java.util.ArrayList;

import com.controladores.BancoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.banco.CtaBcoVO;
import com.vista.Mensajes;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class CtaBcoViewExtended extends CtaBcoView{
	
	
	BancoViewExtended mainView;
	private CtaBcoVO ctaBcoVO;
	private String operacion;
	private PermisosUsuario permisos;
	private BancoControlador controlador;
	
	public CtaBcoViewExtended(BancoViewExtended main){
		
		this.mainView = main;
		
		this.controlador = new BancoControlador();
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		
		/*Habilitamos al formulario para la operacion de nuevo*/
		this.iniFormNuevo();
		
		/*Inicializamos listener*/
		this.inicializarBotonAgregar();
		
		/*Inicializamos listener*/
		this.inicializarBotonCancelar();
		
		this.setearValidaciones(true);
		
		
	}
	
	
	/*Le pasamosel padre GrupoView y el formulario seleccionado para desplegar en pantalla los permisos
	 * y poder editarlos
	 * La operacion puede ser en modo Edicion/Nuevo o lectura*/
	@SuppressWarnings("unused")
	public CtaBcoViewExtended(BancoViewExtended main, CtaBcoVO ctaBco, String operacion){
	
		this.controlador = new BancoControlador();
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
			
		this.operacion = operacion;
		this.mainView = main;
		this.ctaBcoVO = ctaBco;

		/*Seteamos los campos de la cuenta seleccionada*/
		/*Primero dejamos los campor que no sean readonly (de lo contrario falla)*/	
		this.readOnlyFields(false);
		this.codigo.setValue(ctaBco.getCodigo());
		this.nombre.setValue(ctaBco.getNombre());
		this.activo.setValue(ctaBco.isActivo());
		this.comboMoneda.setValue(ctaBco.getMonedaVO());
		
		/*Dependiendo de la operacion como inicializamos el formulario*/
		if(operacion.equals(Variables.OPERACION_EDITAR)){
			this.iniFormEditar();
			this.setearAuditoria(ctaBco);
		}
		else if(operacion.equals(Variables.OPERACION_LECTURA)){
			this.iniFormLectura();
			this.setearAuditoria(ctaBco);
		}
		

		/*Inicializamos listener*/
		this.inicializarBotonAgregar();
		
		/*Inicializamos listener*/
		this.inicializarBotonCancelar();
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura(){
		
		/*Deshabilitamos boton aceptar*/
		this.disableBotonesEditar();
		
				
		/*Inicializamos el combo de monedas*/
		this.inicializarComboMoneda(ctaBcoVO.getMonedaVO().getCodMoneda());
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
				
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Habilitamos boton aceptar*/
		this.enableBotonesEditar();
		
				
		/*Inicializamos el combo de monedas*/
		this.inicializarComboMoneda(ctaBcoVO.getMonedaVO().getCodMoneda());
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.readOnlyFields(false);
		
		/*Seteamos las validaciones*/
		this.setearValidaciones(true);
		
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormNuevo()
	{
		/*Habilitamos boton aceptar*/
		this.enableBotonesEditar();
		
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.readOnlyFields(false);
		
		/*Inicializamos el combo de monedas*/
		this.inicializarComboMoneda(null);
		
		/*Deshabilitamos el boton de editar*/
		//this.desHabilitarBotonEditar();
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
	}
	
	private void setearAuditoria(CtaBcoVO ctaBco)
	{
		auditoria.setDescription(
				
				"Usuario: " + ctaBco.getUsuarioMod() + "<br>" +
			    "Fecha: " + ctaBco.getFechaMod() + "<br>" +
			    "Operación: " + ctaBco.getOperacion());
	}
	
	private void disableBotonesEditar()	{
		
				
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
	}
		
	private void enableBotonesEditar()	{
		
		
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		
	}
	
	
	

	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codigo.setReadOnly(setear);
		this.nombre.setReadOnly(setear);
		this.activo.setReadOnly(setear);
		this.comboMoneda.setReadOnly(setear);
				
	}

	public void inicializarComboMoneda(String cod){
		
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		UsuarioPermisosVO permisoAux;
		
		try {
			permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_BANCOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			lstMonedas = this.controlador.getMonedas(permisoAux);
			
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
			if(this.codigo.isValid() &&
				this.nombre.isValid() &&
				this.comboMoneda.isValid() 
			){
				valido = true;
			}
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
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
	                 " 45 caracteres máximo", 1, 45, false));
	    
	    this.nombre.addValidator(
	            new StringLengthValidator(
	                 " 45 caracteres máximo", 1, 45, false));
	    
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
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
	}
	
	
	private void inicializarBotonAgregar(){
		
		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
	
			try
			{
				/*Validamos los campos antes de invocar al controlador*/
				if(this.fieldsValidos())
				{
					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					UsuarioPermisosVO permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_BANCOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);	
								
					this.ctaBcoVO = new CtaBcoVO();
					
					/*Seteamos la formulario los valores selecionados*/
					this.ctaBcoVO.setCodigo(this.codigo.getValue().trim());
					this.ctaBcoVO.setNombre(this.nombre.getValue().trim());
					this.ctaBcoVO.setActivo(this.activo.getValue());
					this.ctaBcoVO.setMonedaVO((MonedaVO)this.comboMoneda.getValue());
						
					/*Actualizamos el form en el padre*/
					this.mainView.actulaizarGrilla(ctaBcoVO);
					//mainView.agregarCtasSeleccionados(this.ctaBcoVO);
					
					mainView.cerrarVentana();
				
				}

			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}

		});
		
		
	}
	
	
	private void inicializarBotonCancelar(){
		
		this.cancelar.addClickListener(click -> {
			this.mainView.cerrarVentana();
		});
	}
}
