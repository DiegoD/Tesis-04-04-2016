package com.controladores;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioPermisosVO;

public class GrupoControlador {

	
	public GrupoControlador()
	{
		
	}
	
	public void insertarGrupo(GrupoVO grupoVO, UsuarioPermisosVO permisos) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, InicializandoException, ConexionException, ErrorInesperadoException, NoTienePermisosException, ObteniendoPermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarGrupo(grupoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
		
	public ArrayList<GrupoVO> getGrupos(UsuarioPermisosVO permisos) throws ObteniendoGruposException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoFormulariosException, ObteniendoPermisosException, NoTienePermisosException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getGrupos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	
	}
	
	public void editarGrupo(GrupoVO grupoVO, UsuarioPermisosVO permisos) throws InsertandoGrupoException, MemberGrupoException, NoExisteGrupoException, ConexionException, ErrorInesperadoException, InicializandoException, ModificandoGrupoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().editarGrupo(grupoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<FormularioVO> getFormulariosNoGrupo(String codGrupo, String codEmp) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException, InicializandoException {
	{
		return Fachada.getInstance().getFormulariosNoGrupo(codGrupo, codEmp);
	}
	

}
	
}
