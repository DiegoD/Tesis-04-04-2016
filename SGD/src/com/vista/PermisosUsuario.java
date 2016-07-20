package com.vista;

import java.util.ArrayList;

import com.valueObject.FormularioVO;

/**
 *Esta clase nos permite alacenar los permisos del usuaro
 */
public class PermisosUsuario {
	
	private String codEmp;
	
	ArrayList<FormularioVO> lstFormularios;
	
	
	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}

	public ArrayList<FormularioVO> getLstFormularios() {
		return lstFormularios;
	}

	public void setLstFormularios(ArrayList<FormularioVO> lstFormularios) {
		this.lstFormularios = lstFormularios;
	}

}
