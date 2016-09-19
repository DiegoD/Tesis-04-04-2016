package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.IngresoCobro.IngresoCobro;
import com.logica.IngresoCobro.IngresoCobroLinea;

public interface IDAOIngresoCobro {

	public ArrayList<IngresoCobro> getIngresoCobroTodos(Connection con, String codEmp) throws ObteniendoIngresoCobroException, ConexionException;
	public boolean memberIngresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteIngresoCobroException, ConexionException;
	public void insertarIngresoCobro(IngresoCobro cobro, String codEmp, Connection con) throws InsertandoIngresoCobroException, ConexionException;
	public void modificarIngresoCobro(IngresoCobro cobro, String codEmp, Connection con) throws ModificandoIngresoCobroException, ConexionException;
	public ArrayList<IngresoCobroLinea> getIngresoCobroLineaxTrans(Connection con, String codEmp, long nroTrans) throws ObteniendoIngresoCobroException, ConexionException;
}
