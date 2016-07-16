package com.persistencia;
import java.sql.Connection;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.grupos.*;
import com.logica.Formulario;
import com.logica.Grupo;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;

public interface IDAOGrupos {

	public ArrayList<Grupo> getGrupos(Connection con) throws ObteniendoGruposException, ConexionException, ObteniendoFormulariosException;
	public void insertarGrupo(Grupo grupo, Connection con) throws  InsertandoGrupoException, ConexionException ;
	public boolean memberGrupo(String codGrupo, Connection con) throws MemberGrupoException, ConexionException;
	public void eliminarGrupo(String codGrupo, Connection con) throws ModificandoGrupoException, ConexionException;
	public ArrayList<Formulario> getFormulariosNoGrupo(String codGrupo, Connection con) throws ObteniendoFormulariosException;
}
