package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.logica.SaldoProceso;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;

public interface IDAOSaldosProc {

	public boolean memberSaldo(DatosDocum docum, Connection con, String codProceso) throws ExisteSaldoException, ConexionException;
	public void insertarSaldo(DatosDocum documento, Connection con, String codProceso) throws IngresandoSaldoException, ConexionException, SQLException;
	public void eliminarSaldo(DatosDocum documento, Connection con, String codProceso) throws EliminandoSaldoException, ConexionException;
	public void modificarSaldo(DatosDocum documento, int signo, double tc , Connection con, String codProceso)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException;
	public ArrayList<SaldoProceso> getSaldosSinAdjuxProceso(String codEmp, int codProceso, Connection con) throws ObteniendoSaldosException, ConexionException;
	public boolean existeSaldoAsociadoProceso(Connection con, Integer codProceso, String codEmp) throws ExisteSaldoException;
}
