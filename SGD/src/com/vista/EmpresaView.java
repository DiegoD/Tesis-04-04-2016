package com.vista;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
public class EmpresaView extends FormLayout {
	protected Link auditoria;
	protected TextField cod_emp;
	protected TextField nom_emp;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public EmpresaView() {
		Design.read(this);
	}
}
