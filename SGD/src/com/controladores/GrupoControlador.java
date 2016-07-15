package com.controladores;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.valueObject.FormularioSelVO;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;

public class GrupoControlador {

	public void insertarGrupo(JSONObject grupoJS) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		
		//VEr si retornamos a la vista un objeto con Error = true o false y el mensaje
		//en vez de mandar la exception para arriba
		Fachada.getInstance().insertarGrupo(grupoJS);
	}
		
	public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException, InicializandoException, ConexionException, ErrorInesperadoException 
	{
		
			return Fachada.getInstance().getGrupos();
	
	}
	
	public void editarGrupo(JSONObject grupoJS) throws InsertandoGrupoException, MemberGrupoException, NoExisteGrupoException, ConexionException, ErrorInesperadoException, InicializandoException
	{
		//Fachada.getInstance().editarGrupo(grupoJS);
	}
	
	public ArrayList<FormularioSelVO> getFormulariosNoGrupo(String codGrupo) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException {
	{
		return Fachada.getInstance().getFormulariosNoGrupo(codGrupo);
	}
	

}
	
}
