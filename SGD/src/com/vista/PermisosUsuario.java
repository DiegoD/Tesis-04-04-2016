package com.vista;

import java.util.ArrayList;
import java.util.Hashtable;

import com.valueObject.FormularioVO;


/**
 *Esta clase nos permite alacenar los permisos del usuaro
 */
public class PermisosUsuario {
	
	private static String codEmp;
	private static String usuario;
			
	
	/*Variable estaticas*/
	


	private static Hashtable<String, FormularioVO> lstFormularios;
	
	
	public static Hashtable<String, FormularioVO> getLstPermisos() {
		return lstFormularios;
	}

	public static void setLstPermisos(Hashtable<String, FormularioVO> pLstPermisos) {
		lstFormularios = pLstPermisos;
	}

	public static String getCodEmp() {
		return codEmp;
	}

	public static void setCodEmp(String pCodEmp) {
		codEmp = pCodEmp;
	}
	
	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String pUsuario) {
		usuario = pUsuario;
	}
	
	
	/**
	 * Dado una operacion y formulario retorna true
	 * si tiene permisos
	 * 
	 */
	public static boolean permisoEnFormulaior(String formulario, String operacion)
	{
		boolean tienePermiso = false;
		
		FormularioVO frm = lstFormularios.get(formulario);
		
		switch(operacion)
		{
			case VariablesPermisos.OPERACION_LEER : tienePermiso = frm.isLeer();
			break;
			case VariablesPermisos.OPERACION_NUEVO_EDITAR : tienePermiso = frm.isNuevoEditar();
			break;
			case VariablesPermisos.OPERACION_BORRAR : tienePermiso = frm.isBorrar();
			break;
		}
		
		return tienePermiso;
	}


}
