package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.SaldoCuentas.ExisteSaldoCuentaException;
import com.excepciones.SaldoCuentas.InsertandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.ModificandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.NoExisteSaldoCuentaException;
import com.logica.Docum.DocumSaldo;

public interface IDAOSaldosCuentas {

	public boolean memberSaldoCta(DocumSaldo docum, Connection con) throws ExisteSaldoCuentaException;
	public void insertarSaldoCuenta(DocumSaldo documento, Connection con)
			throws InsertandoSaldoCuentaException, ConexionException, SQLException;
	public void eliminarSaldoCuenta(DocumSaldo documento, Connection con)
			throws EliminandoSaldoCuetaException, ConexionException;
	public void modificarSaldoCuenta(DocumSaldo saldo, Connection con)
			throws ModificandoSaldoCuentaException, ConexionException, EliminandoSaldoCuetaException, InsertandoSaldoCuentaException, ExisteSaldoCuentaException, NoExisteSaldoCuentaException;
	
}
