package com.vista.Conciliaciones;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
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
public class ConciliacionView extends FormLayout {
	protected ComboBox comboCajaBanco;
	protected Label lblComprobante;
	protected TextField nroDocum;
	protected DateField fecDoc;
	protected Label lblFechaValor;
	protected DateField fecValor;
	protected TextField nroTrans;
	protected HorizontalLayout horizontalMoneda;
	protected ComboBox comboMoneda;
	protected HorizontalLayout horizontalBanco;
	protected ComboBox comboBancos;
	protected Label lblCuentaBanco;
	protected ComboBox comboCuentas;
	protected TextField monedaBanco;
	protected TextField cuentaBanco;
	protected HorizontalLayout horizontalImportes;
	protected TextField impTotMo;
	protected Label lblConciliado;
	protected TextField importeConciliado;
	protected TextField observaciones;
	protected Grid gridDetalle;
	protected HorizontalLayout botones;
	protected Button conciliar;
	protected Button btnEditar;
	protected Button cancelar;
	protected Button btnEliminar;

	public ConciliacionView() {
		Design.read(this);
	}
}
