package com.controladores;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.FachadaDD;

public class UsuarioControlador {

	
	public ArrayList<JSONObject> getUsuarios() throws ObteniendoUsuariosException, InicializandoException, ClassNotFoundException, ConexionException {
    	
		
		System.out.println("estoy en el controlador de usuarios ");
		return FachadaDD.getInstance().getUsuarios();

    }

}
