package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.NoExisteCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.EmpresaVO;
import com.valueObject.UsuarioPermisosVO;

public class CodigoGeneralizadoControlador {
	
	public CodigoGeneralizadoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los códigos generalizados
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizados(UsuarioPermisosVO permisos) throws ObteniendoCodigosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getCodigosGeneralizados();
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtiene array list de VO de todos los códigos generalizados dado un código
	 */
	public ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizadosxCodigo(String codigo) throws ObteniendoCodigosException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getCodigosGeneralizadosxCodigo(codigo);
	}
	
	/**
	 * Inserta un nuevo codigo generalizado
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO, UsuarioPermisosVO permisos) throws InsertandoCodigoException, ExisteCodigoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarCodigoGeneralizado(codigoGeneralizadoVO);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un código generalizado
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteCodigoException, ModificandoCodigoException, ExisteCodigoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarCodigoGeneralizado(codigoGeneralizadoVO);
		else
			throw new NoTienePermisosException();
		
	}

}
