package com.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Cotizaciones.ExisteCotizacionException;
import com.excepciones.Cotizaciones.InsertandoCotizacionException;
import com.excepciones.Cotizaciones.ModificandoCotizacionException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.logica.Cotizacion;

public interface IDAOCotizaciones {
	
	public ArrayList<Cotizacion> getCotizaciones(String codEmp, Connection con, Timestamp inicio, Timestamp fin) throws ObteniendoCotizacionesException, ConexionException;
	public void insertarCotizacion(Cotizacion cotizacion, String codEmp, Connection con) throws  InsertandoCotizacionException, ConexionException ;
	public boolean memberCotizacion(String cod_moneda, Timestamp fecha, String codEmp, Connection con) throws ExisteCotizacionException, ConexionException;
	public void actualizarCotizacion(Cotizacion cotizacion, String codEmp, Connection con) throws ModificandoCotizacionException, ConexionException;
	public Cotizacion getCotizacion(String codEmp, Date fecha, String codMoneda,Connection con) throws ObteniendoCotizacionesException, ConexionException;
}
