package com.vista.IngresoOtro;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
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
public class IngresoOtroPanel extends VerticalLayout {
	protected Label lblTitulo;
	protected DateField fechaInicio;
	protected DateField fechaFin;
	protected Button btnActualizar;
	protected Grid grid;
	protected Button btnNuevo;

	public IngresoOtroPanel() {
		Design.read(this);
	}
}
