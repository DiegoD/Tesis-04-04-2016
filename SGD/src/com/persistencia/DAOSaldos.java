package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.logica.Docum.DocumDetalle;

public class DAOSaldos implements IDAOSaldos{

	@Override
	public ArrayList<DocumDetalle> getSaldos(Connection con, String codEmp)
			throws ObteniendoSaldosException, ConexionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean memberSaldo(String cod_docum, String serie_docum, Integer nro_docum, Integer cod_tit, String codEmp,
			Connection con) throws ExisteSaldoException, ConexionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insertarSaldo(DocumDetalle documento, String codEmp, Connection con)
			throws IngresandoSaldoException, ConexionException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarSaldo(DocumDetalle documento, String codEmp, Connection con)
			throws ModificandoSaldoException, IngresandoSaldoException, ConexionException {
		// TODO Auto-generated method stub
		
	}
	
	

}
