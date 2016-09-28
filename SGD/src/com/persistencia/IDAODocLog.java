package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.DocLog.InsertandoLogException;
import com.logica.DocLog.DocLog;

public interface IDAODocLog {
	public void insertarDocLog(DocLog log, String codEmp, Connection con) throws InsertandoLogException, ConexionException, SQLException;
}
