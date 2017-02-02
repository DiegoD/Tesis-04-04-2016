package com.controladores;

import java.util.ArrayList;


import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.UsuarioVO;
import com.valueObject.cliente.ClienteVO;

public class UsuarioControlador {

	
	/**
	 * Obtiene los usuarios existentes en base
	 * @throws ObteniendoPermisosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<UsuarioVO> getUsuarios(UsuarioPermisosVO permisos) throws ObteniendoUsuariosException, InicializandoException, ClassNotFoundException, ConexionException, ErrorInesperadoException, ObteniendoGruposException, ObteniendoPermisosException, NoTienePermisosException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getUsuarios(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	/**
	 * Inserta un usuario dado su VO
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarUsuario(UsuarioVO usuarioVO, UsuarioPermisosVO permisos) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, InicializandoException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarUsuario(usuarioVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void modificarUsuario(UsuarioVO usuarioVO, UsuarioPermisosVO permisos) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().modificarUsuario(usuarioVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtiene un array de los grupos que un usuario no tiene asignado dado el usuario
	 */
	public ArrayList<GrupoVO> getUsuariosNoGrupo(String nombreUsuario) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException 
	{
		return FachadaDD.getInstance().getGruposNoUsuario(nombreUsuario);
	}

	
	/**
	 * Trae los clientes activos
	 */
	public ArrayList<ClienteVO> getClientes(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getClientesActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
}
