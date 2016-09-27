package com.vista.IngresoCobro;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
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
public class IngresoCobroViews extends FormLayout {
	protected Link auditoria;
	protected FormLayout formLay1;
	protected TextField nroDocum;
	protected ComboBox comboTipo;
	protected DateField fecDoc;
	protected DateField fecValor;
	protected HorizontalLayout name_wrapper;
	protected TextField codTitular;
	protected TextField nomTitular;
	protected Button btnBuscarCliente;
	protected TextField referencia;
	protected ComboBox comboBancos;
	protected ComboBox comboCuentas;
	protected HorizontalLayout name_wrappeer;
	protected ComboBox comboMPagos;
	protected TextField serieDocRef;
	protected TextField nroDocRef;
	protected HorizontalLayout name_wrapper2;
	protected ComboBox comboMoneda;
	protected TextField importeMO;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;
	protected FormLayout formLay2;
	protected Grid lstGastos;
	protected Button btnAgregar;
	protected Button btnEditarForm;
	protected Button btnQuitar;

	public IngresoCobroViews() {
		Design.read(this);
	}
}
