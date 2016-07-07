package com.persistencia;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.grupos.*;
import com.valueObject.GrupoVO;

public interface IDAOGrupos {

	public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException;
	public void insertarGrupo(GrupoVO grupoVO) throws  InsertandoGrupoException ;
	public boolean memberGrupo(String codGrupo) throws MemberGrupoException;
}
