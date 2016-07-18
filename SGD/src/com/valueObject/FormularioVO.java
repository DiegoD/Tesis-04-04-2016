package com.valueObject;

import com.logica.Formulario;

public class FormularioVO {
	

	private String codFormulario;
	private String nomFormulario;
	
	public FormularioVO(){}
	
	public FormularioVO(Formulario f)
	{
		this.codFormulario = f.getCodFormulario();
		this.nomFormulario = f.getNomFormulario();
		
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

}
