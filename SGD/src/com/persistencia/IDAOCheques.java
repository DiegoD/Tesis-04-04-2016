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
import com.logica.Depositos.Deposito;
import com.logica.Docum.DatosDocum;

public interface IDAOCheques {
	
	public boolean memberCheque(DatosDocum docum, Connection con) throws ExisteChequeException;
	
	public void insertarCheque(DatosDocum documento, Connection con)
			throws InsertandoChequeException, ConexionException, SQLException;
	
	public void eliminarCheque(DatosDocum documento, Connection con)
			throws EliminandoChequeException, ConexionException;
	
	public void modificarCheque(DatosDocum cheque, int signo, double tc   , Connection con)
			throws ModificandoChequeException, ConexionException, EliminandoChequeException, InsertandoChequeException, ExisteChequeException, NoExisteChequeException;
	
	public ArrayList<Deposito> getChequesBanco(Connection con, String codEmp, String codBanco, String codCtaBco) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
	
}
