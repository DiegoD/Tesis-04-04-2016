package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.logica.Moneda;

public interface IDAOMonedas {
	
	public ArrayList<Moneda> getMonedas(String codEmp, Connection con) throws ObteniendoMonedaException, ConexionException;
	public void insertarMoneda(Moneda moneda, String codEmp, Connection con) throws  InsertandoMonedaException, ConexionException ;
	public boolean memberMoneda(String codMoneda, String codEmp, Connection con) throws ExisteMonedaException, ConexionException;
	public void eliminarMoneda(String codMoneda, String codEmp, Connection con) throws ModificandoMonedaException, ConexionException;
	public void actualizarMoneda(Moneda moneda, String codEmp, Connection con) throws ModificandoMonedaException, ConexionException;
}
