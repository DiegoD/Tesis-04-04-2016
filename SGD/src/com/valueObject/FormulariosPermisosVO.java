package com.valueObject;

public class FormulariosPermisosVO extends FormularioVO{
	
	private boolean leer;
	private boolean nuevoEditar;
	private boolean borrar;
	
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
