package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.logica.Docum.NotaCredito;
import com.excepciones.NotaCredito.*;

public interface IDAONotaCredito {

	
	public ArrayList<NotaCredito> getNCTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoNotaCreditoException, ConexionException;
	public boolean memberNC(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteNotaCreditoException, ConexionException;
	public void insertarNC(NotaCredito recibo, Connection con) throws InsertandoNotaCreditoException, ConexionException;
	public void eliminarNC(NotaCredito recibo, Connection con) throws InsertandoNotaCreditoException, ConexionException, EliminandoNotaCreditoException;

}
