package com.persistencia;

import java.sql.*;
import java.util.ArrayList;


import com.excepciones.ConexionException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.CodTitNomTitAux;
import com.logica.Formulario;
import com.logica.Usuario;
import com.valueObject.EmpLoginVO;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;

public interface IDAOUsuarios {
	
	public boolean usuarioValido(LoginVO loginVO, Connection con) throws LoginException;
	
	public ArrayList<Usuario> getUsuarios(String codEmp, Connection con) throws ClassNotFoundException, ObteniendoUsuariosException, ConexionException, ObteniendoGruposException;

	public boolean memberUsuario(String usuario, Connection con) throws ExisteUsuarioException, ConexionException;
	
	public void insertarUsuario(Usuario user, String empresa, Connection con) throws InsertandoUsuarioException, ConexionException;
	
	public void eliminarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException, ModificandoUsuarioException;
	
	public ArrayList<GrupoVO> getGruposNoUsuario(String nombreUsurio, Connection con) throws ObteniendoUsuariosException, ObteniendoGruposException;
	
	public ArrayList<Formulario> getFormulariosxUsuario(String usuario, String codemp, Connection con) throws ObteniendoFormulariosException;
	
	public EmpLoginVO getEmpresaUsuario(String usuario, Connection con) throws ObteniendoUsuariosxEmpExeption;
	
	public void modificarUsuario(Usuario user, String empresa, Connection con) throws ModificandoUsuarioException;
	
	public Formulario getPermisoFormularioOperacionUsuario(String usuario, String codEmp, String formulario,Connection con)throws ObteniendoPermisosException;
	
	public CodTitNomTitAux getUsuarioCliente(String codEmp, String usuario, Connection con) throws ObteniendoUsuariosException;
}
