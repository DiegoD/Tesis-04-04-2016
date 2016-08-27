package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.TipoRubro.ExisteTipoRubroException;
import com.excepciones.TipoRubro.InsertandoTipoRubroException;
import com.excepciones.TipoRubro.ModificandoTipoRubroException;
import com.excepciones.TipoRubro.NoExisteTipoRubroException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.empresa.EmpresaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.TipoRubro.TipoRubroVO;

public class TipoRubroControlador {
	
	public TipoRubroControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los tipos de rubro para la empresa
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<TipoRubroVO> getTipoRubros(UsuarioPermisosVO permisos, String cod_emp) throws ObteniendoTipoRubroException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getTipoRubros(cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo tipo de rubro para la empresa
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarTipoRubro(TipoRubroVO tipoRubroVO, String cod_emp, UsuarioPermisosVO permisos) throws InsertandoTipoRubroException, ExisteTipoRubroException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarTipoRubro(tipoRubroVO, cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un tipo rubro
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarTipoRubro(TipoRubroVO tipoRubroVO, String cod_emp, UsuarioPermisosVO permisos) throws ConexionException, NoExisteTipoRubroException, ModificandoTipoRubroException, ExisteTipoRubroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarTipoRubro(tipoRubroVO, cod_emp);
		else
			throw new NoTienePermisosException();
	}

}
