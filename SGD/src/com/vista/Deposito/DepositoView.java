package com.vista.Deposito;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
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
public class DepositoView extends FormLayout {
	protected ComboBox comboResponsable;
	protected Label lblComprobante;
	protected TextField comprobante;
	protected DateField fecDoc;
	protected Label lblFechaValor;
	protected DateField fecValor;
	protected ComboBox comboBancos;
	protected Label lblCuentaBanco;
	protected ComboBox comboCuentas;
	protected TextField monedaBanco;
	protected TextField cuentaBanco;
	protected TextField importeMo;
	protected TextField observaciones;
	protected Grid gridCheques;
	protected Button depositar;
	protected Button cancelar;

	public DepositoView() {
		Design.read(this);
	}
}
