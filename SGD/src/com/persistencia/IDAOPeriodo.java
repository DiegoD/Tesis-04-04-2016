package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.InsertandoPeriodoException;
import com.excepciones.Periodo.ModificandoPeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Periodo.ObteniendoPeriodosException;
import com.logica.Periodo.Periodo;

public interface IDAOPeriodo {
	
	public ArrayList<Periodo> getPeriodo(String codEmp, Connection con) throws ObteniendoPeriodosException, ConexionException;
	public void insertarPeriodo(Periodo periodo, String codEmp, Connection con) throws InsertandoPeriodoException, ConexionException;
	public boolean memberPeriodo(String mes, Integer anio, String codEmp, Connection con) throws ExistePeriodoException, ConexionException;
	public void actualizarPeriodo(Periodo periodo,String codEmp, Connection con) throws ModificandoPeriodoException, ConexionException;
	public boolean validaPeriodo(String mes, Integer anio, String codEmp, Connection con) throws NoExistePeriodoException;
}
