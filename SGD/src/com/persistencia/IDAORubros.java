package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.logica.Rubro;
import com.logica.RubroCuenta;

public interface IDAORubros {
	
	public ArrayList<Rubro> getRubros(String cod_emp, Connection con) throws ObteniendoRubrosException, ConexionException;
	public ArrayList<Rubro> getRubrosActivos(String cod_emp, Connection con) throws ObteniendoRubrosException, ConexionException;
	public ArrayList<RubroCuenta> getRubrosCuentasActivos(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException;
	public ArrayList<RubroCuenta> getRubrosCuentasActivosFacturable(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException;
	public void insertarRubro(Rubro rubro, String cod_emp, Connection con) throws  InsertandoRubroException, ConexionException ;
	public boolean memberRubro(String cod_rubro, String cod_emp, Connection con) throws ExisteRubroException, ConexionException;
	public void actualizarRubro(Rubro rubro, String cod_emp, Connection con) throws ModificandoRubroException, ConexionException;

}
