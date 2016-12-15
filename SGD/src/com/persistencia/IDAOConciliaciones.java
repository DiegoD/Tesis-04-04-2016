package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Conciliaciones.EliminandoConcialiacionException;
import com.excepciones.Conciliaciones.ExisteConciliacionException;
import com.excepciones.Conciliaciones.InsertandoConciliacionException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.logica.Conciliacion.Conciliacion;
import com.logica.Conciliacion.ConciliacionDetalle;

public interface IDAOConciliaciones {
	
	public ArrayList<Conciliacion> getConciliacionesTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoConciliacionException, ConexionException;
	public ArrayList<ConciliacionDetalle> getConciliacionLineaxTrans(Connection con, Conciliacion conciliacion, String codEmp) throws ObteniendoConciliacionException, ConexionException;
	public void insertarConciliacion(Conciliacion conciliacion, Connection con, String codEmp) throws InsertandoConciliacionException, ConexionException;
	public void insertarConciliacionDetalle(ConciliacionDetalle detalle, int linea, Connection con, String codEmp) throws InsertandoDepositoException, ConexionException, InsertandoConciliacionException;
	public boolean memberConciliacion(Long nroTrans, String codEmp, Connection con) throws ExisteConciliacionException, ConexionException;
	public void eliminarConciliacion(Conciliacion conciliacion, Connection con, String codEmp) throws EliminandoConcialiacionException, ConexionException;
	public void eliminarConciliacionDetalle(Conciliacion conciliacion, Connection con, String codEmp) throws EliminandoConcialiacionException, ConexionException;
	public ArrayList<ConciliacionDetalle> getMovimientosBanco(Connection con, String codEmp, String codBco, String codCta) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
	public void conciliarSaCuentas(ConciliacionDetalle conciliacion, Connection con, String codEmp, boolean conciliar) throws InsertandoConciliacionException, ConexionException;
	public ArrayList<ConciliacionDetalle> getMovimientosCajaMoneda(Connection con, String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
	public double getSaldoConciliadoMoneda(Connection con, String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
	public double getSaldoConciliadoCuentaBanco(Connection con, String codBco, String codCta, String codEmp) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException;
}
