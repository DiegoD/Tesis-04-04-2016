package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.logica.Docum.DatosDocum;

public interface IDAOSaldosProc {

	public boolean memberSaldo(DatosDocum docum, Connection con) throws ExisteSaldoException, ConexionException;
	public void insertarSaldo(DatosDocum documento, Connection con) throws IngresandoSaldoException, ConexionException, SQLException;
	public void eliminarSaldo(DatosDocum documento, Connection con) throws EliminandoSaldoException, ConexionException;
	public void modificarSaldo(DatosDocum documento, int signo, double tc , Connection con)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException;
	
}
