package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Recibo.EliminandoReciboException;
import com.excepciones.Recibo.ExisteReciboException;
import com.excepciones.Recibo.InsertandoReciboException;
import com.excepciones.Recibo.ObteniendoReciboException;
import com.logica.Docum.Recibo;

public interface IDAORecibos {

	
	public ArrayList<Recibo> getReciboTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoReciboException, ConexionException;
	public boolean memberRecibos(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteReciboException, ConexionException;
	public void insertarRecibo(Recibo recibo, Connection con) throws InsertandoReciboException, ConexionException;
	public void eliminarRecibo(Recibo recibo, Connection con) throws InsertandoReciboException, ConexionException, EliminandoReciboException;
	public boolean existeReciboFactura(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteReciboException, ConexionException;
		
}
