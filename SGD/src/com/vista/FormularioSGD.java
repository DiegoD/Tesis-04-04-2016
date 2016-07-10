package com.vista;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.VerticalLayout;
import com.valueObject.AuditoriaVO;

public abstract class FormularioSGD extends VerticalLayout{

	/**
	 * Metodo que inicializa el formulario
	 */
	abstract void inicializarForm();

	/**
	 * Seteamos las validaciones del Formulario
	 * pasamos un booleano para activarlos y desactivarlos
	 * EN modo LEER: las deshabilitamos (para que no aparezcan los asteriscos, etc)
	 * EN modo NUEVO: las habilitamos
	 * EN modo EDITAR: las habilitamos
	 *
	 *Tambien las expreciones regulares
	 * @return 
	 */
	abstract void setearValidaciones(boolean setear);
	
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 * Ponemos AuditoriaVO dado que todos los VO heredan de este
	 * Hay que pasarle el VO correspondiente a la clase
	 * Cuando se selecciona de una grilla, se invoca este metodo
	 * para que inicialize los campos automaticamente
	 *
	 */
	abstract void setDataSourceFormulario(BeanItem<AuditoriaVO> item);
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	abstract void iniFormLectura();
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	abstract void iniFormEditar();
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	abstract void iniFormNuevo();
	
	
	/**
	 * Dejamos setear los texFields correspondientes, 
	 *  
	 * Solamente aquellos campos posibles de editar
	 * EJ: el codigo no se deja editar
	 *
	 */
	abstract void setearFieldsEditar();
	
	
	/**
	 * Deshabilitamos el boton editar:
	 * ENABLED = FALSE
	 * VISIBLE = FALSE
	 *
	 */
	abstract void disableBotonEditar();
	
	/**
	 * Habilitamos el boton editar
	 * ENABLED = TRUE
	 * VISIBLE = TRUE
	 */
	abstract void enableBotonEditar();
	
	/**
	 * Deshabilitamos el boton aceptar
	 * ENABLED = FALSE
	 * VISIBLE = FALSE
	 */
	abstract void disableBotonAceptar();
	
	/**
	 * Habilitamos el boton aceptar
	 * ENABLED = TRUE
	 * VISIBLE = TRUE
	 */
	abstract void enableBotonAceptar();
    
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	abstract void readOnlyFields(boolean setear);
	
}
