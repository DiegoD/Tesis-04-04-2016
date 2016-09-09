package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.Procesos.IngresandoProcesoException;

public interface IDAONumeradores {
	
	public int getNumero(Connection con, String cod_numerador, String cod_Emp) throws IngresandoProcesoException, ConexionException, SQLException;

}
