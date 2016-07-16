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
	
	public String getCodFOrmulario() {
		return codFormulario;
	}
	public void setCodFormulario(String codFOrmulario) {
		this.codFormulario = codFOrmulario;
	}
	public String getNomFormulario() {
		return nomFormulario;
	}
	public void setNomFormulario(String nomFormulario) {
		this.nomFormulario = nomFormulario;
	}

}
