package com.vista;

import java.util.ArrayList;

import com.valueObject.FormularioVO;

/**
 *Esta clase nos permite alacenar los permisos del usuaro
 */
public class PermisosUsuario {
	
	ArrayList<FormularioVO> lstFormularios;

	public ArrayList<FormularioVO> getLstFormularios() {
		return lstFormularios;
	}

	public void setLstFormularios(ArrayList<FormularioVO> lstFormularios) {
		this.lstFormularios = lstFormularios;
	}

}