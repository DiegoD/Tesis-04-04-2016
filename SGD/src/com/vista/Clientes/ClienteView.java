package com.vista.Clientes;

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
public class ClienteView extends FormLayout {
	protected Link auditoria;
	protected TextField codigo;
	protected TextField nombre;
	protected TextField razonSocial;
	protected TextField codigoDoc;
	protected TextField nombreDoc;
	protected TextField numeroDoc;
	protected TextField tel;
	protected TextField direccion;
	protected TextField mail;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public ClienteView() {
		Design.read(this);
	}
}
