package com.logica;

import com.valueObject.FormularioVO;

public class Formulario {

	private String codFormulario;
	private String nomFormulario;
	private boolean leer;
	private boolean nuevoEditar;
	private boolean borrar;
	
	public Formulario()
	{
		
	}
	
	public Formulario(FormularioVO form)
	{
		this.codFormulario = form.getCodigo();
		this.nomFormulario = form.getNombre();
		this.leer = form.isLeer();
		this.nuevoEditar = form.isNuevoEditar();
		this.borrar = form.isBorrar();
	}
	
	public String getCodFormulario() {
		return codFormulario;
	}
	public void setCodFormulario(String codFormulario) {
		this.codFormulario = codFormulario;
	}
	public String getNomFormulario() {
		return nomFormulario;
	}
	public void setNomFormulario(String nomFormulario) {
		this.nomFormulario = nomFormulario;
	}
	
	public boolean isLeer() {
		return leer;
	}
	public void setLeer(boolean leer) {
		this.leer = leer;
	}
	public boolean isNuevoEditar() {
		return nuevoEditar;
	}
	public void setNuevoEditar(boolean nuevoEditar) {
		this.nuevoEditar = nuevoEditar;
	}
	public boolean isBorrar() {
		return borrar;
	}
	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
	
}
