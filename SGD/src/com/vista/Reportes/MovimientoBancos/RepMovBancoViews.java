package com.vista.Reportes.MovimientoBancos;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
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
public class RepMovBancoViews extends FormLayout {
	protected FormLayout formLay12;
	protected TextField nroTrans;
	protected DateField fecDesde;
	protected DateField fecHasta;
	protected ComboBox comboBancos;
	protected HorizontalLayout cuenta;
	protected ComboBox comboCuentas;
	protected TextField monedaBanco;
	protected TextField cuentaBanco;
	protected CheckBox chkConciliados;
	protected CheckBox chkSaldos;
	protected HorizontalLayout botones;
	protected Button aceptar;

	public RepMovBancoViews() {
		Design.read(this);
	}
}
