package com.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Factura.ObteniendoSaldoException;
import com.excepciones.Gastos.EliminandoGastoException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.logica.Gasto;
import com.logica.Docum.DocumDetalle;

public interface IDAOGastos {
	
	public ArrayList<Gasto> getGastos(Connection con, String codEmp, Timestamp inicio, Timestamp fin)
			throws ObteniendoGastosException, ConexionException;
	public boolean memberGasto(long codGasto, String codEmp, Connection con) throws ExisteGastoException, ConexionException;
	public void insertarGasto(DocumDetalle gasto, String codEmp, Connection con) throws IngresandoGastoException, ConexionException, SQLException;
	public void modificarGasto(DocumDetalle gasto, String codEmp, Connection con) throws ModificandoGastoException, IngresandoGastoException, ConexionException;
	public ArrayList<Gasto> getGastosConSaldo(Connection con, String codEmp, String codTit) throws ObteniendoGastosException, ConexionException;
	public ArrayList<Gasto> getGastosConSaldoxMoneda(Connection con, String codEmp, String codTit, String codMoneda) throws ObteniendoGastosException, ConexionException;
	public void eliminarGasto(long  transaccion, String codEmp, Connection con) throws EliminandoGastoException, ConexionException;
	public void eliminarGastoPK(int nroDocum, String serieDocum, String codDocum, String codEmp, Connection con) throws EliminandoGastoException, ConexionException;
	public ArrayList<DocumDetalle> getGastosNoCobrablesxProceso(Connection con, String codEmp, int codProceso)
			throws ObteniendoGastosException, ConexionException;
	public ArrayList<DocumDetalle> getGastosCobrablesxProceso(Connection con, String codEmp, int codProceso)
			throws ObteniendoGastosException, ConexionException;
	public ArrayList<DocumDetalle> getGastosAPagarxProceso(Connection con, String codEmp, int codProceso)
			throws ObteniendoGastosException, ConexionException;
	public ArrayList<DocumDetalle> getGastosAnuladosxProceso(Connection con, String codEmp, int codProceso)
			throws ObteniendoGastosException, ConexionException;
	public ArrayList<Gasto> getGastosFacturablesxProcesoConSaldo(Connection con, String codEmp, String codTit, int codProceso) throws ObteniendoGastosException, ConexionException;
	public ArrayList<Gasto> getGastosFacturablesxProcesoConSaldoxMoneda(Connection con, String codEmp, String codTit, int codProceso, String codMoneda) throws ObteniendoGastosException, ConexionException;
	public ArrayList<Gasto> getGastosConSaldoCobrable(Connection con, String codEmp, String codTit)
			throws ObteniendoGastosException, ConexionException;
	
	public boolean existeGastoIngreso(String serieDocum, Integer nroDocum, String codEmp, Connection con)
			throws ExisteGastoException, ConexionException;
	
	public boolean existeGastoAsociadoProceso(Integer nroProceso, String codEmp, Connection con)
			throws ExisteGastoException, ConexionException;
	public double getSaldoGasto(int nroDocum, String codEmp, Connection con) throws ObteniendoSaldoException, ConexionException;
	
	public ArrayList<Gasto> getGastosConSaldoCobrableProceso(Connection con, String codEmp, String codTit, Integer codProceso)
			throws ObteniendoGastosException, ConexionException;
	
	public void anularGastoPK(int nroDocum, String serieDocum, String codDocum, String codEmp, Connection con, boolean anula) throws EliminandoGastoException, ConexionException;
	
	public ArrayList<Gasto> getGastosSinMedioDePago(Connection con, String codEmp, String codTit) throws ObteniendoGastosException, ConexionException;
}
