package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.logica.Rubro;

public interface IDAORubros {
	
	public ArrayList<Rubro> getRubros(Connection con) throws ObteniendoRubrosException, ConexionException;
	public void insertarRubro(Rubro rubro, Connection con) throws  InsertandoRubroException, ConexionException ;
	public boolean memberRubro(String cod_rubro, Connection con) throws ExisteRubroException, ConexionException;
	public void actualizarRubro(Rubro rubro, Connection con) throws ModificandoRubroException, ConexionException;

}
