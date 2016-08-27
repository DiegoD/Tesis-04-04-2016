package com.vista.Rubros;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class RubroView extends FormLayout {
	protected Link auditoria;
	protected TextField codRubro;
	protected TextField descripcion;
	protected TextField codigoImpuesto;
	protected TextField descripcionImpuesto;
	protected Button btnBuscarImpuesto;
	protected TextField porcentajeImpuesto;
	protected TextField codTipoRubro;
	protected TextField descripcionTipoRubro;
	protected Button btnBuscarTipoRubro;
	protected ComboBox impuestos;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public RubroView() {
		Design.read(this);
	}
}
