package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.SaldoCuentas.ExisteNroTransferencia;
import com.excepciones.SaldoCuentas.ExisteSaldoCuentaException;
import com.excepciones.SaldoCuentas.InsertandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.ModificandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.NoExisteSaldoCuentaException;
import com.logica.Docum.DocumSaldo;
import com.valueObject.Saldos.*;


public interface IDAOSaldosCuentas {

	public boolean memberSaldoCta(DocumSaldo docum, Connection con) throws ExisteSaldoCuentaException;
	public void insertarSaldoCuenta(DocumSaldo documento, Connection con)
			throws InsertandoSaldoCuentaException, ConexionException, SQLException;
	public void eliminarSaldoCuenta(DocumSaldo documento, Connection con)
			throws EliminandoSaldoCuetaException, ConexionException;
	public void modificarSaldoCuenta(DocumSaldo saldo, Connection con)
			throws ModificandoSaldoCuentaException, ConexionException, EliminandoSaldoCuetaException, InsertandoSaldoCuentaException, ExisteSaldoCuentaException, NoExisteSaldoCuentaException;
	public boolean existeNroTransferencia(Integer nro, String codBco, String codCta, String codEmp, Connection con) throws ExisteNroTransferencia;
	
	public ArrayList<SaCuentasVO> getSaCuentas(String codEmp, Connection con) throws Exception;
	public ArrayList<SaDocumsVO> getSaldosDocum(String codEmp, Connection con) throws Exception;
}
