package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Egresos.*;
import com.logica.IngresoCobro.IngresoCobro;

public interface IDAOEgresoCobro {

	public ArrayList<IngresoCobro> getEgresoCobroTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoEgresoCobroException, ConexionException;
	public ArrayList<IngresoCobro> getEgresoCobroOtroTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoEgresoCobroException, ConexionException;
	public boolean memberEgresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteEgresoCobroException, ConexionException;
	public void insertarEgresoCobro(IngresoCobro cobro, Connection con) throws InsertandoEgresoCobroException, ConexionException;
	//public ArrayList<IngresoCobroLinea> getIngresoCobroLineaxTrans(Connection con, String codEmp, long nroTrans) throws ObteniendoIngresoCobroException, ConexionException;
	public void eliminarEgresoCobro(IngresoCobro ing, Connection con)throws InsertandoEgresoCobroException, ConexionException, EliminandoEgresoCobroException ;
	public boolean existeEgreso(int nroDocum, String codEmp, Connection con) throws ExisteEgresoCobroException, ConexionException;
	public void anularEgresoCobro(IngresoCobro cobro, Connection con, boolean anula) throws InsertandoEgresoCobroException, ConexionException, EliminandoEgresoCobroException;
}
