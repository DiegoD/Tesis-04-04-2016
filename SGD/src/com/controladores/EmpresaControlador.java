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
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.empresa.EmpresaUsuVO;
import com.valueObject.empresa.EmpresaVO;

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
	 * @throws ExisteUsuarioException 
	 */
	public void insertarEmpresa(EmpresaUsuVO empresaVO, UsuarioPermisosVO permisos) throws InsertandoEmpresaException, ExisteEmpresaException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException, ExisteUsuarioException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos)){
			
			FachadaDD.getInstance().insertarEmprea(empresaVO);
			
		}
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
