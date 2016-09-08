package com.vista.Procesos;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class ProcesoView extends CssLayout {
	protected CssLayout main_content_wrapper;
	protected FormLayout billing_form;
	protected Link auditoria;
	protected TextField codigo;
	protected Label lblMega;
	protected TextField nroMega;
	protected Label lblFecha;
	protected DateField fecha;
	protected HorizontalLayout cliente;
	protected TextField codCliente;
	protected TextField nomCliente;
	protected TextField carpeta;
	protected Button btnBuscarCliente;
	protected ComboBox comboDocumento;
	protected TextField nroDocum;
	protected DateField fecDocum;
	protected ComboBox comboMoneda;
	protected TextField impMo;
	protected TextField tcMov;
	protected TextField impMn;
	protected TextField Kilos;
	protected DateField fecCruce;
	protected TextField impTr;
	protected TextField marca;
	protected TextField medio;
	protected TextField descripcion;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public ProcesoView() {
		Design.read(this);
	}
}
