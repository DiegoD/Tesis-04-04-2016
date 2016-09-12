package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.logica.Gasto;

public interface IDAOGastos {
	
	public ArrayList<Gasto> getGastos(Connection con, String codEmp) throws ObteniendoGastosException, ConexionException;
	public boolean memberGasto(int codGasto, String codEmp, Connection con) throws ExisteGastoException, ConexionException;
	public void insertarGasto(Gasto gasto, String codEmp, Connection con) throws IngresandoGastoException, ConexionException, SQLException;
	public void modificarGasto(Gasto gasto, String codEmp, Connection con) throws ModificandoGastoException;

}
