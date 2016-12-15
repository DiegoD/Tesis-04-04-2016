package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Depositos.EliminandoDepositoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.logica.Depositos.Deposito;
import com.logica.Depositos.DepositoDetalle;

public interface IDAODepositos {
	
	public ArrayList<Deposito> getDepositosTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoDepositoException, ConexionException;
	public ArrayList<DepositoDetalle> getDepositoLineaxTrans(Connection con, Deposito deposito, String codEmp) throws ObteniendoDepositoException, ConexionException;
	public void insertarDeposito(Deposito deposito, Connection con, String codEmp) throws InsertandoDepositoException, ConexionException;
	public void insertarDepositoDetalle(DepositoDetalle detalle, int linea, Connection con, String codEmp) throws InsertandoDepositoException, ConexionException;
	public boolean memberDeposito(Long nroTrans, String codEmp, Connection con) throws ExisteDepositoException, ConexionException;
	public void eliminarDeposito(Deposito deposito, Connection con, String codEmp) throws EliminandoDepositoException, ConexionException;
	public void eliminarDepositoDetalle(Deposito deposito, Connection con, String codEmp) throws EliminandoDepositoException, ConexionException;
}
