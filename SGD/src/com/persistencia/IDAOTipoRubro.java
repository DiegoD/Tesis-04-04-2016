package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.TipoRubro.ExisteTipoRubroException;
import com.excepciones.TipoRubro.InsertandoTipoRubroException;
import com.excepciones.TipoRubro.ModificandoTipoRubroException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.logica.TipoRubro;

public interface IDAOTipoRubro {

	public ArrayList<TipoRubro> getTipoRubros(Connection con, String cod_emp) throws ObteniendoTipoRubroException, ConexionException;
	public ArrayList<TipoRubro> getTipoRubrosActivos(Connection con, String cod_emp) throws ObteniendoTipoRubroException, ConexionException;
	public void insertarTipoRubro(TipoRubro tipoRubro, String cod_emp, Connection con) throws  InsertandoTipoRubroException, ConexionException ;
	public boolean memberTipoRubro(String cod_tipoRubro, String cod_emp, Connection con) throws ExisteTipoRubroException, ConexionException;
	public void actualizarTipoRubro(TipoRubro tipoRubro, String cod_emp, Connection con) throws ModificandoTipoRubroException, ConexionException;
}
