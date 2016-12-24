package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Factura.*;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.Factura;
import com.logica.Docum.FacturaDetalle;
import com.valueObject.Docum.FacturaVO;

public interface IDAOFacturas {

	public ArrayList<Factura> getFacturaTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoFacturasException, ConexionException;
	public boolean memberFacturas(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteFacturaException, ConexionException;
	public void insertarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException;
	public void eliminarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException, EliminandoFacturaException;
	public ArrayList<DatosDocum> getGastosFacturaLinea(Connection con, int nroDocum, String serieDocum, String codDocum, String codEmp) throws ObteniendoFacturasException, ConexionException;
	public ArrayList<Factura> getFacturaConSaldoxMoneda(Connection con, String codEmp, String codMoneda, String codTit) throws ObteniendoFacturasException, ConexionException;
	public boolean existeGasto(int nroDocum, String codEmp, Connection con) throws ExisteFacturaException, ConexionException;
	public double getSaldoFactura(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ObteniendoSaldoException, ConexionException;
}
