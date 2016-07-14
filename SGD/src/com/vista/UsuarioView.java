package com.vista;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
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
public class UsuarioView extends FormLayout {
	protected TextField nombre;
	protected TextField usuario;
	protected PasswordField pass;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Grid grillaGrupos;
	protected Button btnAgregar;

	public UsuarioView() {
		Design.read(this);
	}
}
