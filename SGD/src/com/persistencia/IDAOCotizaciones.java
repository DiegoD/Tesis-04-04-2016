package com.persistencia;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Cotizaciones.ExisteCotizacionException;
import com.excepciones.Cotizaciones.InsertandoCotizacionException;
import com.excepciones.Cotizaciones.ModificandoCotizacionException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.logica.Cotizacion;
import com.logica.Moneda;

public interface IDAOCotizaciones {
	
	public ArrayList<Cotizacion> getCotizaciones(Connection con) throws ObteniendoCotizacionesException, ConexionException;
	public void insertarCotizacion(Cotizacion cotizacion, Connection con) throws  InsertandoCotizacionException, ConexionException ;
	public boolean memberCotizacion(String cod_moneda, Timestamp fecha, Connection con) throws ExisteCotizacionException, ConexionException;
	public void actualizarCotizacion(Cotizacion cotizacion, Connection con) throws ModificandoCotizacionException, ConexionException;

}
