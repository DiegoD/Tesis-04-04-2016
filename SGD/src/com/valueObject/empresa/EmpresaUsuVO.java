package com.valueObject.empresa;

public class EmpresaUsuVO extends EmpresaVO{

	private String usuario;
	private String pass;
		
	public	EmpresaUsuVO(){
		super();
		
	}
	
	public	EmpresaUsuVO(String codEmp, String nomEmp, boolean activo){
		super(codEmp, nomEmp, activo);
		
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	
	
	
}
