package com.vista.Bancos;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
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
public class BancoView extends FormLayout {
	protected Link auditoria;
	protected FormLayout formLay1;
	protected TextField codigo;
	protected TextField nombre;
	protected TextField Tel;
	protected TextField direccion;
	protected TextField contacto;
	protected TextField codEmp;
	protected CheckBox activo;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;
	protected FormLayout formLay2;
	protected Grid lstFormularios;
	protected Button btnAgregar;
	protected Button btnQuitar;
	protected Button btnEditarForm;
	protected Button btnVerPermisos;

	public BancoView() {
		Design.read(this);
	}
}
