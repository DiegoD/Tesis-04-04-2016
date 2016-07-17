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
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.vista.GrupoViewExtended;

public class GrupoControlador {

	private GrupoViewExtended vista;
	
	public GrupoControlador(GrupoViewExtended view)
	{
		this.vista = view;
	}
	
	public GrupoControlador()
	{
		
	}
	
	public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		
		//VEr si retornamos a la vista un objeto con Error = true o false y el mensaje
		//en vez de mandar la exception para arriba
		Fachada.getInstance().insertarGrupo(grupoVO);
	}
		
	public ArrayList<GrupoVO> getGrupos() throws ObteniendoGruposException, InicializandoException, ConexionException, ErrorInesperadoException 
	{
		
			return Fachada.getInstance().getGrupos();
	
	}
	
	public void editarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, MemberGrupoException, NoExisteGrupoException, ConexionException, ErrorInesperadoException, InicializandoException, ModificandoGrupoException
	{
		Fachada.getInstance().editarGrupo(grupoVO);
	}
	
	public ArrayList<FormularioVO> getFormulariosNoGrupo(String codGrupo) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException {
	{
		return Fachada.getInstance().getFormulariosNoGrupo(codGrupo);
	}
	

}
	
}
