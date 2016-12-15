package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.logica.Proceso;

public interface IDAOProcesos {
	
	public ArrayList<Proceso> getProcesosTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoProcesosException, ConexionException;
	public boolean memberProceso(int codProceso, String codEmp, Connection con) throws ExisteProcesoException, ConexionException;
	public void insertarProceso(Proceso proceso, String codEmp, Connection con) throws IngresandoProcesoException, ConexionException, SQLException;
	public void modificarProceso(Proceso proceso, String codEmp, Connection con) throws ModificandoProcesoException;
	public void eliminarProceso(int codProceso, String codEmp, Connection con) throws EliminandoProcesoException, ConexionException;
	public ArrayList<Proceso> getProcesosCliente(Connection con, String codEmp, String cod_cliente) throws ObteniendoProcesosException, ConexionException;
	public Proceso getProceso(Connection con, String codEmp, Integer codProceso) throws ObteniendoProcesosException, ConexionException;
	public ArrayList<Proceso> getProcesosTodosSinFecha(Connection con, String codEmp) throws ObteniendoProcesosException, ConexionException;
}
