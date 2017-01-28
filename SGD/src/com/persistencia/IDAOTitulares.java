package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.logica.Cliente;
import com.logica.Titular;

public interface IDAOTitulares {
	
	 public ArrayList<Titular> getTitularesActivos(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException;
	 public ArrayList<Titular> getTitularesActivosFuncioanrios(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException;
	 public ArrayList<Titular> getTitularesTodos(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException;
}
