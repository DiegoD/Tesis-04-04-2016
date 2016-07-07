package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.logica.Fachada;
import com.valueObject.LoginVO;

public class LoginControlador {

	public LoginControlador(){}
	
	public boolean usuarioValido(LoginVO loginVO) throws LoginException, InicializandoException{
		
			return Fachada.getInstance().usuarioValido(loginVO);
						
	}
	
}
