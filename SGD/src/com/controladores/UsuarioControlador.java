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

public class UsuarioControlador {

	
	public ArrayList<JSONObject> getUsuarios() throws ObteniendoUsuariosException, InicializandoException, ClassNotFoundException, ConexionException, ErrorInesperadoException 
	{
		System.out.println("estoy en el controlador de usuarios ");
		return FachadaDD.getInstance().getUsuarios();
    }
	
	public void insertarUsuario(JSONObject jsonUsuario) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, InicializandoException, ErrorInesperadoException
	{
		System.out.println("llamo a fachada");
		FachadaDD.getInstance().insertarUsuario(jsonUsuario);
	}
	
	public void modificarUsuario(JSONObject jsonUsuario) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException, InicializandoException
	{
		System.out.println("llamo a fachada");
		FachadaDD.getInstance().modificarUsuario(jsonUsuario);
	}
	
	public ArrayList<GrupoVO> getFormulariosNoGrupo(String nombreUsuario) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException 
	{
		return FachadaDD.getInstance().getGruposNoUsuario(nombreUsuario);
	}

}
