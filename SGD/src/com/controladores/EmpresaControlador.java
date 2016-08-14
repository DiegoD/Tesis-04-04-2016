package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.EmpresaVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.UsuarioPermisosVO;

public class EmpresaControlador {
	
	public EmpresaControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas las empresas
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<EmpresaVO> getEmpresas(UsuarioPermisosVO permisos) throws ObteniendoEmpresasException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getEmpresas();
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta una nueva empresa
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarEmpresa(EmpresaVO empresaVO, UsuarioPermisosVO permisos) throws InsertandoEmpresaException, ExisteEmpresaException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarEmprea(empresaVO);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de una empresa
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarEmpresa(EmpresaVO empresaVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteEmpresaException, ModificandoEmpresaException, ExisteEmpresaException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarEmpresa(empresaVO);
		else
			throw new NoTienePermisosException();
	}
}
