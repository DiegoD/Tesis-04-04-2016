package com.persistencia;

import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.logica.Usuario;
import com.valueObject.LoginVO;

public interface IDAOUsuarios {
	
	public boolean usuarioValido(LoginVO loginVO) throws LoginException;
	
	public ArrayList<Usuario> getUsuarios() throws ClassNotFoundException, ObteniendoUsuariosException, ConexionException;

	public boolean memberUsuario(String usuario) throws ExisteUsuarioException, ConexionException;
	
	public void insertarUsuario(Usuario user) throws InsertandoUsuarioException, ConexionException;
}
