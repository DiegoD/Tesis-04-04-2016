package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.EliminandoChequeException;
import com.excepciones.Cheques.ExisteChequeException;
import com.excepciones.Cheques.InsertandoChequeException;
import com.excepciones.Cheques.ModificandoChequeException;
import com.excepciones.Cheques.NoExisteChequeException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.logica.Cheque;
import com.logica.Depositos.Deposito;
import com.logica.Depositos.DepositoDetalle;
import com.logica.Docum.DatosDocum;

public interface IDAOCheques {
	
	public boolean memberCheque(Cheque cheque, Connection con) throws ExisteChequeException;
	
	public void insertarCheque(Cheque cheque, Connection con)
			throws InsertandoChequeException, ConexionException, SQLException;
	
	public void eliminarCheque(Cheque cheque, Connection con)
			throws EliminandoChequeException, ConexionException;
	
	public void modificarCheque(Cheque cheque, int signo, double tc   , Connection con)
			throws ModificandoChequeException, ConexionException, EliminandoChequeException, InsertandoChequeException, ExisteChequeException, NoExisteChequeException;
	
	public ArrayList<DepositoDetalle> getChequesBanco(Connection con, String codEmp, String codMoneda) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
	
}
