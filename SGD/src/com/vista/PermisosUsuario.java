package com.vista;

import java.util.Hashtable;

import com.valueObject.FormularioVO;


/**
 *Esta clase nos permite alacenar los permisos del usuaro
 */
public class PermisosUsuario {
	
	private String codEmp;
	private String usuario;
			

	private Hashtable<String, FormularioVO> lstFormularios;
	
	
	public  Hashtable<String, FormularioVO> getLstPermisos() {
		return lstFormularios;
	}

	public void setLstPermisos(Hashtable<String, FormularioVO> pLstPermisos) {
		lstFormularios = pLstPermisos;
	}

	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String pCodEmp) {
		codEmp = pCodEmp;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String pUsuario) {
		usuario = pUsuario;
	}
	
	
	/**
	 * Dado una operacion y formulario retorna true
	 * si tiene permisos
	 * 
	 */
	public boolean permisoEnFormulaior(String formulario, String operacion)
	{
		boolean tienePermiso = false;
		
		FormularioVO frm = lstFormularios.get(formulario);
		
		if(frm != null)
		{
			switch(operacion)
			{
				case VariablesPermisos.OPERACION_LEER : tienePermiso = frm.isLeer();
				break;
				case VariablesPermisos.OPERACION_NUEVO_EDITAR : tienePermiso = frm.isNuevoEditar();
				break;
				case VariablesPermisos.OPERACION_BORRAR : tienePermiso = frm.isBorrar();
				break;
			}
		}
		return tienePermiso;
	}


}
