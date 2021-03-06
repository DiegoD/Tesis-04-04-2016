package com.valueObject;

import com.logica.Formulario;

public class FormularioVO {
	

	private String codFormulario;
	private String nomFormulario;
	
	private boolean leer;
	private boolean nuevoEditar;
	private boolean borrar;
	
	public FormularioVO()
	{
		this.borrar = false;
		this.nuevoEditar = false;
		this.borrar = false;
	}
	
	/**
	 * Copiamos todos los datos del FormularioVO pasado
	 * por parametro
	 *
	 */
	public void copiar(FormularioVO frm){
		
		this.codFormulario = frm.getCodigo();
		this.nomFormulario = frm.getNombre();
		this.leer = frm.isLeer();
		this.nuevoEditar = frm.isNuevoEditar();
		this.borrar = frm.isBorrar();
		
	}
	
	public FormularioVO(Formulario f)
	{
		this.codFormulario = f.getCodFormulario();
		this.nomFormulario = f.getNomFormulario();
		this.leer = f.isLeer();
		this.nuevoEditar = f.isNuevoEditar();
		this.borrar = f.isBorrar();
		
	}
	
	public String getCodigo() {
		return codFormulario;
	}
	public void setCodFormulario(String codFormulario) {
		this.codFormulario = codFormulario;
	}
	public String getNombre() {
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
