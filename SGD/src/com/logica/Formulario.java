package com.logica;

import com.valueObject.FormularioVO;

public class Formulario {

	private String codFormulario;
	private String nomFormulario;
	
	public Formulario()
	{
		
	}
	
	public Formulario(FormularioVO form)
	{
		this.codFormulario = form.getCodigo();
		this.nomFormulario = form.getNombre();
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
	
}
