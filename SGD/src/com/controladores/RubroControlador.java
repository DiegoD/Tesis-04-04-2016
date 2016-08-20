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
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.ImpuestoVO;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;

public class RubroControlador {
	
	public RubroControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas los rubros
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<RubroVO> getRubros(UsuarioPermisosVO permisos) throws ObteniendoRubrosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))	
			return FachadaDD.getInstance().getRubros();
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo rubro
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarRubro(RubroVO rubroVO, UsuarioPermisosVO permisos) throws InsertandoRubroException, ExisteRubroException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))	
			FachadaDD.getInstance().insertarRubro(rubroVO);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un rubro
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarRubro(RubroVO rubroVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteRubroException, ModificandoRubroException, ExisteRubroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarRubro(rubroVO);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtiene array list de VO de todos los impuestos
	 * @throws ObteniendoPermisosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<ImpuestoVO> getImpuestos() throws ObteniendoImpuestosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		return FachadaDD.getInstance().getImpuestos();
	}

}
