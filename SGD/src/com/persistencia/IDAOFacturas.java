package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Factura.*;
import com.logica.Docum.Factura;
import com.valueObject.Docum.FacturaVO;

public interface IDAOFacturas {

	public ArrayList<Factura> getFacturaTodos(Connection con, String codEmp) throws ObteniendoFacturasException, ConexionException;
	public boolean memberFacturas(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteFacturaException, ConexionException;
	public void insertarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException;
	public void eliminarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException, EliminandoFacturaException;
	public ArrayList<Factura> getFacturaConSaldoxMoneda(Connection con, String codEmp, String codMoneda, String codTit) throws ObteniendoFacturasException, ConexionException; 
}
