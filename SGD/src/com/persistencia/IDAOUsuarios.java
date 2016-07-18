package com.persistencia;

import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Formulario;
import com.logica.Grupo;
import com.logica.Usuario;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;

public interface IDAOUsuarios {
	
	public boolean usuarioValido(LoginVO loginVO, Connection con) throws LoginException;
	
	public ArrayList<Usuario> getUsuarios(Connection con) throws ClassNotFoundException, ObteniendoUsuariosException, ConexionException, ObteniendoGruposException;

	public boolean memberUsuario(String usuario, Connection con) throws ExisteUsuarioException, ConexionException;
	
	public void insertarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException;
	
	public void eliminarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException, ModificandoUsuarioException;
	
	public ArrayList<GrupoVO> getGruposNoUsuario(String nombreUsurio, Connection con) throws ObteniendoUsuariosException, ObteniendoGruposException;
	
	public ArrayList<Formulario> getFormulariosxUsuario(String usuario, Connection con) throws ObteniendoFormulariosException;
}
