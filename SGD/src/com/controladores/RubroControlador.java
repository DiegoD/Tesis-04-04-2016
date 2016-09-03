package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.NoExisteRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.ImpuestoVO;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.TipoRubro.TipoRubroVO;

public class RubroControlador {
	
	public RubroControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas los rubros
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<RubroVO> getRubros(UsuarioPermisosVO permisos, String cod_emp) throws ObteniendoRubrosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))	
			return FachadaDD.getInstance().getRubros(cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtiene array list de VO de todas los rubros ACTIVOS
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<RubroVO> getRubrosActivos(UsuarioPermisosVO permisos, String cod_emp) throws ObteniendoRubrosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))	
			return FachadaDD.getInstance().getRubrosActivos(cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo rubro
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarRubro(RubroVO rubroVO, String cod_emp, UsuarioPermisosVO permisos) throws InsertandoRubroException, ExisteRubroException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))	
			FachadaDD.getInstance().insertarRubro(rubroVO, cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un rubro
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarRubro(RubroVO rubroVO, String cod_emp, UsuarioPermisosVO permisos) throws ConexionException, NoExisteRubroException, ModificandoRubroException, ExisteRubroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarRubro(rubroVO, cod_emp);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtiene array list de VO de todos los impuestos
	 * @throws ObteniendoPermisosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<ImpuestoVO> getImpuestos(String codEmp) throws ObteniendoImpuestosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		return FachadaDD.getInstance().getImpuestos(codEmp);
	}

	public ArrayList<TipoRubroVO> getTipoRubros(String cod_emp) throws ObteniendoTipoRubroException, ConexionException, InicializandoException {
		return FachadaDD.getInstance().getTipoRubros(cod_emp);
	}
	
	/**
	 * Obtiene array list de VO de todos los impuestos
	 * @throws ObteniendoPermisosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<ImpuestoVO> getImpuestosActivos(String codEmp) throws ObteniendoImpuestosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		return FachadaDD.getInstance().getImpuestosActivos(codEmp);
	}

	public ArrayList<TipoRubroVO> getTipoRubrosActivos(String cod_emp) throws ObteniendoTipoRubroException, ConexionException, InicializandoException {
		return FachadaDD.getInstance().getTipoRubrosActivos(cod_emp);
	}
	

}
