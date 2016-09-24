package com.vista.Gastos;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
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
public class GastoView extends FormLayout {
	protected Link auditoria;
	protected TextField nroDocum;
	protected ComboBox comboSeleccion;
	protected TextField nroTrans;
	protected TextField codDocum;
	protected TextField serieDocum;
	protected DateField fecDoc;
	protected DateField fecValor;
	protected HorizontalLayout proceso;
	protected TextField codProceso;
	protected TextField descProceso;
	protected Button btnBuscarProceso;
	protected HorizontalLayout cliente;
	protected TextField codTitular;
	protected TextField nomTitular;
	protected Button btnBuscarEmpleado;
	protected TextField codRubro;
	protected TextField nomRubro;
	protected Button btnBuscarRubro;
	protected TextField codCuenta;
	protected TextField nomCuenta;
	protected Button btnBuscarCuenta;
	protected TextField codImpuesto;
	protected TextField nomImpuesto;
	protected TextField porcentajeImpuesto;
	protected Button btnBuscarImpuesto;
	protected ComboBox comboMoneda;
	protected TextField tcMov;
	protected TextField impTtotMo;
	protected TextField impTotMn;
	protected TextField impImpuMo;
	protected TextField impImpuMn;
	protected TextField referencia;
	protected Button aceptar;
	protected Button btnEditar;
	protected Button cancelar;

	public GastoView() {
		Design.read(this);
	}
}
