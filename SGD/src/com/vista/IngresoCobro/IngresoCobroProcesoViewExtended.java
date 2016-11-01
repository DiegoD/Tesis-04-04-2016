package com.vista.IngresoCobro;

import java.sql.Date;
import java.util.ArrayList;

import com.controladores.IngresoCobroControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.RubroCuentaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class IngresoCobroProcesoViewExtended extends IngresoCobroProcesoView implements IBusqueda{

	private IngresoCobroControlador controlador;
	private String operacion;
	private IngresoCobroViewExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	UsuarioPermisosVO permisoAux;
	ProcesoVO procesoParametro = new ProcesoVO();
	
	public IngresoCobroProcesoViewExtended(String opera, IngresoCobroViewExtended main, ProcesoVO proceso){
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		this.operacion = opera;
		this.mainView = main;
		
		procesoParametro = proceso;
		
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
				
				
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
			
				ProcesoVO procesoNuevo = new ProcesoVO();
				
				procesoNuevo.setCodCliente(codCliente.getValue());
				procesoNuevo.setNomCliente(nomCliente.getValue());
				
				if(codProceso.getValue()!= null){
					if(codProceso.getConvertedValue().equals(0)){
						Mensajes.mostrarMensajeError("Debe seleccionar un proceso");
						return;
					}
					else{
						procesoNuevo.setCodigo(((Integer)codProceso.getConvertedValue()));
					}
				}
				else{
					Mensajes.mostrarMensajeError("Debe seleccionar un proceso");
					return;
				}
				
				
				procesoNuevo.setDescripcion(descripcion.getValue());
				procesoNuevo.setNomDocum(documento.getValue());
				procesoNuevo.setCarpeta(carpeta.getValue());
				
				if(impMo.getValue() !=null){
					if((double)impMo.getConvertedValue() <= 0){
						Mensajes.mostrarMensajeError("El importe no es correcto");
						return;
					}
					else{
						procesoNuevo.setImpMo((double)impMo.getConvertedValue());
					}
				}
				else{
					Mensajes.mostrarMensajeError("El importe no es correcto");
					return;
				}
				
				
				procesoNuevo.setCodRubro(codRubro.getValue());
				procesoNuevo.setNomRubro(nomRubro.getValue());
				procesoNuevo.setCodCuenta(codCuenta.getValue());
				procesoNuevo.setNomCuenta(nomCuenta.getValue());
				
				main.setInfo(procesoNuevo);
				main.cerrarVentana();
				
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
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
		
		this.btnBuscarProceso.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ProcesoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ProcesoVO> lstClientes = new ArrayList<ProcesoVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				
				lstClientes = this.controlador.getProcesosCliente(permisoAux, this.codCliente.getValue());
				//lstClientes = this.controlador.getClientes(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException |
					 ObteniendoProcesosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (ProcesoVO i: lstClientes) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("65%", "65%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
		});
		
		this.btnBuscarRubro.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new RubroCuentaVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<RubroCuentaVO> lstRubros = new ArrayList<RubroCuentaVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstRubros = this.controlador.getRubrosCuentasActivos(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoRubrosException | com.excepciones.Rubros.ObteniendoRubrosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (RubroCuentaVO i: lstRubros) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
			sub = new MySub("65%", "65%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		
	}
	
	public  void inicializarForm(){
		
		this.controlador = new IngresoCobroControlador();
		this.inicializarCampos();
		
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
		
		
	}
	
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoBorrar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_BORRAR);
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		if(permisoBorrar)
			this.enableBotonEliminar();
		
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
		this.disableBotonEliminar();
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_PROCESOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		this.codCliente.setValue(procesoParametro.getCodCliente());
		this.nomCliente.setValue(procesoParametro.getNomCliente());
		this.moneda.setValue(procesoParametro.getSimboloMoneda());
		
		this.enableBotonAceptar();
		this.enableBotonEliminar();
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
		
		this.btnBuscarCliente.setEnabled(false);
		this.btnBuscarCliente.setVisible(false);
	}
	
	private void disableBotonEliminar()
	{
		this.botones.setWidth("187px");
		
		
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
		this.btnBuscarCliente.setEnabled(true);
		this.btnBuscarCliente.setVisible(true);
	}
	
	private void enableBotonEliminar()
	{
		if(operacion != Variables.OPERACION_NUEVO){
			this.botones.setWidth("270px");
		}
		else{
			disableBotonEliminar();
		}
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.btnBuscarCliente.setVisible(false);
		
		this.codCliente.setReadOnly(false);
		this.nomCliente.setReadOnly(false);
		this.codProceso.setReadOnly(false);
		this.descripcion.setReadOnly(false);
		this.documento.setReadOnly(false);
		this.carpeta.setReadOnly(false);
		this.codRubro.setReadOnly(false);
		this.nomRubro.setReadOnly(false);
		this.codCuenta.setReadOnly(false);
		this.nomCuenta.setReadOnly(false);
		this.comentario.setReadOnly(false);
		this.impMo.setReadOnly(false);
		this.moneda.setReadOnly(false);
		
		this.codCliente.setEnabled(false);
		this.nomCliente.setEnabled(false);
		this.codProceso.setEnabled(false);
		this.descripcion.setEnabled(false);
		this.documento.setEnabled(false);
		this.carpeta.setEnabled(false);
		this.codRubro.setEnabled(false);
		this.nomRubro.setEnabled(false);
		this.codCuenta.setEnabled(false);
		this.nomCuenta.setEnabled(false);
		this.moneda.setEnabled(false);
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
		
        this.carpeta.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 0, 20, true));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, true));
        
        this.codRubro.addValidator(
                new StringLengthValidator(
                        " 15 caracteres máximo", 1, 15, true));
        
        this.codCuenta.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 1, 20, true));
        
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
			if(this.codCliente.isValid() && this.codProceso.isValid() && this.codRubro.isValid()
					&& this.codCuenta.isValid() && this.impMo.isValid())
				valido = true;
			
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	public void cerrarVentana()	{
		UI.getCurrent().removeWindow(sub);
	}

	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		if(datos instanceof ProcesoVO){
			ProcesoVO procesoVO = (ProcesoVO) datos;
			this.codProceso.setValue(String.valueOf(procesoVO.getCodigo()));
			this.descripcion.setValue(procesoVO.getDescripcion());
			this.documento.setValue(procesoVO.getNomDocum());
			this.carpeta.setValue(procesoVO.getCarpeta());
			
		}
		
		if(datos instanceof RubroCuentaVO){
			RubroCuentaVO rubroCuenta = (RubroCuentaVO) datos;
			this.codRubro.setValue(rubroCuenta.getCod_rubro());
			this.nomRubro.setValue(rubroCuenta.getDescripcionRubro());
			this.codCuenta.setValue(rubroCuenta.getCod_cuenta());
			this.nomCuenta.setValue(rubroCuenta.getDescripcionCuenta());
			
		}
		
	}
	
	

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
	
	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	
	public void inicializarCampos(){
		
		impMo.setConverter(Double.class);
		impMo.setConversionError("Error en formato de número");
		codProceso.setConverter(Integer.class);
		codProceso.setConversionError("Error en formato de número");
		
	}


}
