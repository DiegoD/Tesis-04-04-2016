package com.excepciones.Login;

public class LoginException extends Exception{

	public LoginException(){
		super("Ha ocurrido un error al intentar ingresar al sistema");
	}
}
