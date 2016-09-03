package com.vista.Clientes;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
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
public class ClienteView extends CssLayout {
	protected CssLayout main_content_wrapper;
	protected FormLayout billing_form;
	protected Link auditoria;
	protected HorizontalLayout name_wrapper;
	protected TextField codigo;
	protected TextField nombre;
	protected TextField razonSocial;
	protected HorizontalLayout documento_wrapper;
	protected ComboBox comboDocumento;
	protected TextField codigoDoc;
	protected TextField nombreDoc;
	protected TextField numeroDoc;
	protected Button btnBuscarDoc;
	protected TextField direccion;
	protected TextField mail;
	protected TextField tel;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public ClienteView() {
		Design.read(this);
	}
}
