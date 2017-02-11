package com.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;

public interface IDAOSaldos {
	
	public ArrayList<DocumDetalle> getSaldos(Connection con, String codEmp) throws ObteniendoSaldosException, ConexionException;
	public boolean memberSaldo(DatosDocum docum, Connection con) throws ExisteSaldoException, ConexionException;
	public void insertarSaldo(DatosDocum documento, Connection con) throws IngresandoSaldoException, ConexionException, SQLException;
	public void eliminarSaldo(DatosDocum documento, Connection con) throws EliminandoSaldoException, ConexionException;
	public void modificarSaldo(DatosDocum documento, int signo, double tc , Connection con)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException; 
	public void modificarSaldoSinSA(DatosDocum documento, Connection con)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException;
	
	public void modificarSaldoImporte(DatosDocum documento, Connection con)
			throws ModificandoSaldoException, ConexionException, ExisteSaldoException;
	
	public void anularSaldo(DatosDocum documento, Connection con, boolean anula)
			throws EliminandoSaldoException, ConexionException;
	
}
