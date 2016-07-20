package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.EmpLoginVO;
import com.valueObject.FormularioVO;
import com.valueObject.LoginVO;

public class LoginControlador {

	public LoginControlador(){}
	
	public boolean usuarioValido(LoginVO loginVO) throws LoginException, InicializandoException, ErrorInesperadoException, ConexionException{
		
			return Fachada.getInstance().usuarioValido(loginVO);
						
	}
	
	public ArrayList<FormularioVO> getPermisosUsuario(String usuario, String codEmp) throws ObteniendoFormulariosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getFormulariosxUsuario(usuario, codEmp);
	}
	
	public ArrayList<EmpLoginVO> getUsuariosxEmp(String usuario) throws ConexionException, InicializandoException, ObteniendoUsuariosxEmpExeption
	{
		return FachadaDD.getInstance().getUsuariosxEmp(usuario);
	}
	
}
