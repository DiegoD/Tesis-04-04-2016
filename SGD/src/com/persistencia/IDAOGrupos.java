package com.persistencia;
import java.sql.Connection;
import java.util.ArrayList;


import com.excepciones.ConexionException;
import com.excepciones.grupos.*;
import com.logica.Formulario;
import com.logica.Grupo;

public interface IDAOGrupos {

	public ArrayList<Grupo> getGrupos(String codEmp, Connection con) throws ObteniendoGruposException, ConexionException, ObteniendoFormulariosException;
	public void insertarGrupo(Grupo grupo, String codEmp, Connection con) throws  InsertandoGrupoException, ConexionException ;
	public boolean memberGrupo(String codGrupo, String codEmp, Connection con) throws MemberGrupoException, ConexionException;
	public void eliminarGrupo(String codGrupo, String codEmp, Connection con) throws ModificandoGrupoException, ConexionException;
	public ArrayList<Formulario> getFormulariosNoGrupo(String codGrupo, String codEmp, Connection con) throws ObteniendoFormulariosException;
	public void actualizarGrupo(Grupo grupo, String codEmp, Connection con) throws ModificandoGrupoException;
	public Grupo getGrupo(String codEmp, Connection con, String codGrupo) throws ObteniendoGruposException, ObteniendoFormulariosException;
}
