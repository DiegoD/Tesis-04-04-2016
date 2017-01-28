package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.IngresoCobros.EliminandoIngresoCobroException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.IngresoCobro.IngresoCobro;

public interface IDAOIngresoCobro {

	public ArrayList<IngresoCobro> getIngresoCobroTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoIngresoCobroException, ConexionException;
	public boolean memberIngresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteIngresoCobroException, ConexionException;
	public void insertarIngresoCobro(IngresoCobro cobro, Connection con) throws InsertandoIngresoCobroException, ConexionException;
	public void eliminarIngresoCobro(IngresoCobro ing, Connection con)throws InsertandoIngresoCobroException, ConexionException, EliminandoIngresoCobroException ;
	public ArrayList<IngresoCobro> getIngresoCobroTodosOtro(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoIngresoCobroException, ConexionException ;
	public boolean existeGastoIngresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteIngresoCobroException, ConexionException;
}
