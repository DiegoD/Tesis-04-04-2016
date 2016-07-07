package com.controladores;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.InicializandoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.valueObject.GrupoVO;

public class GrupoControlador {

public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, InicializandoException{
	
	//VEr si retornamos a la vista un objeto con Error = true o false y el mensaje
	//en vez de mandar la exception para arriba
	Fachada.getInstance().insertarGrupo(grupoVO);
}
	
public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException, InicializandoException {
    	
    		
		return Fachada.getInstance().getGrupos();

    }

}
