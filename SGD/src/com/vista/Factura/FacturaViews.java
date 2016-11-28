package com.vista.Factura;

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
public class FacturaViews extends FormLayout {
	protected Link auditoria;
	protected FormLayout formLay1;
	protected TextField nroTrans;
	protected HorizontalLayout wrapper_docum;
	protected TextField serieDocum;
	protected TextField nroDocum;
	protected DateField fecDoc;
	protected DateField fecValor;
	protected HorizontalLayout name_wrapper;
	protected TextField codTitular;
	protected TextField nomTitular;
	protected TextField tipo;
	protected Button btnBuscarCliente;
	protected HorizontalLayout proceso;
	protected TextField codProceso;
	protected TextField descProceso;
	protected Button btnBuscarProceso;
	protected TextField referencia;
	protected TextField tcMov;
	protected HorizontalLayout name_wrapper2;
	protected ComboBox comboMoneda;
	protected TextField impTotMo;
	protected HorizontalLayout wrapper_documImpu;
	protected TextField impuTotMo;
	protected TextField impuTotMn;
	protected HorizontalLayout wrapper_documSubtot;
	protected TextField impSubMo;
	protected TextField impSubMn;
	protected HorizontalLayout botones;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;
	protected Button btnEliminar;
	protected FormLayout formLay2;
	protected Grid lstGastos;
	protected Button btnAgregar;
	protected Button btnAgregarGto;
	protected Button btnEditarForm;
	protected Button btnQuitar;

	public FacturaViews() {
		Design.read(this);
	}
}
