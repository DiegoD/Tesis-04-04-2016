package com.persistencia;

import java.sql.*;

import com.excepciones.Login.LoginException;
import com.valueObject.LoginVO;

public interface IDAOUsuarios {
	
	public boolean usuarioValido(LoginVO loginVO) throws LoginException;

}
