package com.vista.Grupos;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Grid.SelectionMode;
import com.valueObject.FormularioVO;
import com.vista.Mensajes;
import com.vista.Variables;

public class GrupoFormularioPermisosExtended extends GrupoFormularioPermisos{

	GrupoViewExtended mainView;
	private FormularioVO frm;
	private String operacion;
	
	
	/*Le pasamosel padre GrupoView y el formulario seleccionado para desplegar en pantalla los permisos
	 * y poder editarlos
	 * La operacion puede ser en modo Edicion/Nuevo o lectura*/
	@SuppressWarnings("unused")
	public GrupoFormularioPermisosExtended(GrupoViewExtended main, FormularioVO form, String operacion)
	{
		this.operacion = operacion;
		this.mainView = main;
		this.frm = form;

		/*Seteamos los cheks pasados del formulario seleccionado*/
		this.chkLeer.setValue(this.frm.isLeer());
		this.chkNuevoEditar.setValue(this.frm.isNuevoEditar());
		this.chkEliminar.setValue(this.frm.isBorrar());
		
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
				this.frm.setLeer(this.chkLeer.getValue());
				this.frm.setNuevoEditar(this.chkNuevoEditar.getValue());
				this.frm.setBorrar(this.chkEliminar.getValue());
					
				/*Actualizamos el form en el padre*/
				this.mainView.actulaizarGrilla(frm);
				mainView.cerrarVentana();
				
				//((Window) this.getParent()).removeFromParent(this);

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
		this.chkNuevoEditar.setReadOnly(setear);
		this.chkLeer.setReadOnly(setear);
		this.chkEliminar.setReadOnly(setear);
				
	}
	
}
