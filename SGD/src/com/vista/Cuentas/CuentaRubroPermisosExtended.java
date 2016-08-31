package com.vista.Cuentas;

import com.valueObject.FormularioVO;
import com.valueObject.RubroVO;
import com.vista.Mensajes;
import com.vista.Variables;
import com.vista.Grupos.GrupoViewExtended;

public class CuentaRubroPermisosExtended extends CuentaRubroPermisos{
	
	CuentaViewExtended mainView;
	private RubroVO rubro;
	private String operacion;
	
	/*Le pasamosel padre CuentaView y el rubro seleccionado para desplegar en pantalla los permisos
	 * y poder editarlos
	 * La operacion puede ser en modo Edicion/Nuevo o lectura*/
	@SuppressWarnings("unused")
	public CuentaRubroPermisosExtended(CuentaViewExtended main, RubroVO rubroA, String operacion){
		
		this.operacion = operacion;
		this.mainView = main;
		this.rubro = rubroA;

		/*Seteamos los cheks pasados del formulario seleccionado*/
		this.chkOficina.setValue(this.rubro.isOficina());
		this.chkProceso.setValue(this.rubro.isProceso());
		this.chkPersona.setValue(this.rubro.isPersona());
		
		/*Dependiendo de la operacion como inicializamos el formulario*/
		if(operacion.equals(Variables.OPERACION_EDITAR))
			this.iniFormEditar();
		else if(operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
		
		this.btnAgregar.addClickListener(click -> {
			
			int i = 0;
	
			try
			{
								
				/*Seteamos la formulario los valores selecionados*/
				this.rubro.setOficina(this.chkOficina.getValue());
				this.rubro.setProceso(this.chkProceso.getValue());
				this.rubro.setPersona(this.chkPersona.getValue());
				
				/*Actualizamos el form en el padre*/
				this.mainView.actulaizarGrilla(rubro);
				mainView.cerrarVentana();

			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}

		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura(){
		
		/*Deshabilitamos boton aceptar*/
		this.disableBotonesEditar();
		
				
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
				
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Deshabilitamos boton aceptar*/
		this.enableBotonesEditar();
		
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.readOnlyFields(false);
		
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
		this.chkOficina.setReadOnly(setear);
		this.chkProceso.setReadOnly(setear);
		this.chkPersona.setReadOnly(setear);
				
	}
}
