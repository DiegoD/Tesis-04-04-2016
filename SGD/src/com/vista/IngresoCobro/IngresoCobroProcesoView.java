package com.vista.IngresoCobro;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class IngresoCobroProcesoView extends CssLayout {
	protected CssLayout main_content_wrapper;
	protected FormLayout billing_form;
	protected HorizontalLayout cliente;
	protected TextField codCliente;
	protected TextField nomCliente;
	protected Button btnBuscarCliente;
	protected HorizontalLayout proceso;
	protected TextField codProceso;
	protected TextField descripcion;
	protected Button btnBuscarProceso;
	protected TextField documento;
	protected Label lblCarpeta;
	protected TextField carpeta;
	protected TextField codRubro;
	protected TextField nomRubro;
	protected Button btnBuscarRubro;
	protected TextField codCuenta;
	protected TextField nomCuenta;
	protected TextField comentario;
	protected TextField impMo;
	protected Label lblMoneda;
	protected TextField moneda;
	protected HorizontalLayout botones;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public IngresoCobroProcesoView() {
		Design.read(this);
	}
}
