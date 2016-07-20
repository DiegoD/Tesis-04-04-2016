package com.controladores;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;

public class UsuarioControlador {

	
	/**
	 * Obtiene los usuarios existentes en base
	 */
	public ArrayList<UsuarioVO> getUsuarios() throws ObteniendoUsuariosException, InicializandoException, ClassNotFoundException, ConexionException, ErrorInesperadoException, ObteniendoGruposException 
	{
		return FachadaDD.getInstance().getUsuarios();
    }
	
	/**
	 * Inserta un usuario dado su VO
	 */
	public void insertarUsuario(UsuarioVO usuarioVO) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, InicializandoException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarUsuario(usuarioVO);
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 */
	public void modificarUsuario(UsuarioVO usuarioVO) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException, InicializandoException
	{
		FachadaDD.getInstance().modificarUsuario(usuarioVO);
	}
	
	/**
	 * Obtiene un array de los grupos que un usuario no tiene asignado dado el usuario
	 */
	public ArrayList<GrupoVO> getUsuariosNoGrupo(String nombreUsuario) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException 
	{
		return FachadaDD.getInstance().getGruposNoUsuario(nombreUsuario);
	}

}
