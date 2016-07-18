package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.logica.Fachada;
import com.valueObject.FormularioVO;
import com.valueObject.LoginVO;

public class LoginControlador {

	public LoginControlador(){}
	
	public boolean usuarioValido(LoginVO loginVO) throws LoginException, InicializandoException, ErrorInesperadoException, ConexionException{
		
			return Fachada.getInstance().usuarioValido(loginVO);
						
	}
	
	public ArrayList<FormularioVO> getPermisosUsuario(String usuario) throws ObteniendoFormulariosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getFormulariosxUsuario(usuario);
	}
	
}
