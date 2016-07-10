package com.persistencia;
import java.sql.Connection;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.grupos.*;
import com.valueObject.GrupoVO;

public interface IDAOGrupos {

	public ArrayList<JSONObject> getGrupos(Connection con) throws ObteniendoGruposException, ConexionException;
	public void insertarGrupo(GrupoVO grupoVO, Connection con) throws  InsertandoGrupoException, ConexionException ;
	public boolean memberGrupo(String codGrupo, Connection con) throws MemberGrupoException, ConexionException;
	public void eliminarGrupo(String codGrupo, Connection con) throws ModificandoGrupoException, ConexionException;
}
