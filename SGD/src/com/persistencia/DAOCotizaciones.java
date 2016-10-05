package com.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Cotizaciones.ExisteCotizacionException;
import com.excepciones.Cotizaciones.InsertandoCotizacionException;
import com.excepciones.Cotizaciones.ModificandoCotizacionException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.logica.Cotizacion;
import com.logica.Moneda;

public class DAOCotizaciones implements IDAOCotizaciones{

	@Override
	public ArrayList<Cotizacion> getCotizaciones(String codEmp, Connection con)
			throws ObteniendoCotizacionesException, ConexionException {
		
		ArrayList<Cotizacion> lstCotizaciones = new ArrayList<Cotizacion>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getCotizaciones();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			Cotizacion cotizacion;
			
			while(rs.next ()) {

				cotizacion = new Cotizacion();
				Moneda moneda = new Moneda();
				
				cotizacion.setFecha(rs.getTimestamp(2));
				cotizacion.setCotizacion_compra(rs.getDouble(3));
				cotizacion.setCotizacion_venta(rs.getDouble(4));
				cotizacion.setFechaMod(rs.getTimestamp(5));
				cotizacion.setUsuarioMod(rs.getString(6));
				cotizacion.setOperacion(rs.getString(7));
				moneda.setCod_moneda(rs.getString(8));
				moneda.setDescripcion(rs.getString(9));
				moneda.setSimbolo(rs.getString(10));
				moneda.setAcepta_cotizacion(rs.getBoolean(11));
				moneda.setActivo(rs.getBoolean(12));
				cotizacion.setMoneda(moneda);
				
				lstCotizaciones.add(cotizacion);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoCotizacionesException();
		}
			
		return lstCotizaciones;
	}
	
	public Cotizacion getCotizacion(String codEmp, Date fecha, String codMoneda,Connection con)
			throws ObteniendoCotizacionesException, ConexionException {
		
		Cotizacion cotizacion = null;
		
		try
		{
//			SimpleDateFormat sdf = new SimpleDateFormat(
//				    "mm-dd-yyyy");
			//sdf.format(fecha)
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getCotizacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			pstmt1.setDate(2, fecha);
			pstmt1.setString(3, codMoneda);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			
			
			while(rs.next ()) {

				cotizacion = new Cotizacion();
				Moneda moneda = new Moneda();
				
				cotizacion.setFecha(rs.getTimestamp(2));
				cotizacion.setCotizacion_compra(rs.getDouble(3));
				cotizacion.setCotizacion_venta(rs.getDouble(4));
				cotizacion.setFechaMod(rs.getTimestamp(5));
				cotizacion.setUsuarioMod(rs.getString(6));
				cotizacion.setOperacion(rs.getString(7));
				moneda.setCod_moneda(rs.getString(8));
				moneda.setDescripcion(rs.getString(9));
				moneda.setSimbolo(rs.getString(10));
				moneda.setAcepta_cotizacion(rs.getBoolean(11));
				moneda.setActivo(rs.getBoolean(12));
				cotizacion.setMoneda(moneda);
				
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoCotizacionesException();
		}
			
		return cotizacion;
	}

	
	
	@Override
	public void insertarCotizacion(Cotizacion cotizacion, String codEmp, Connection con)
			throws InsertandoCotizacionException, ConexionException {
		// TODO Auto-generated method stub
		
		
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarCotizacion();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, cotizacion.getMoneda().getCod_moneda());
			pstmt1.setTimestamp(2, cotizacion.getFecha());
			pstmt1.setDouble(3, cotizacion.getCotizacion_compra());
			pstmt1.setDouble(4, cotizacion.getCotizacion_venta());
			pstmt1.setString(5, cotizacion.getUsuarioMod());
			pstmt1.setString(6, cotizacion.getOperacion());
			pstmt1.setString(7, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoCotizacionException();
		} 
		
	}

	@Override
	public boolean memberCotizacion(String cod_moneda, Timestamp fecha, String codEmp, Connection con)
			throws ExisteCotizacionException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberCotizacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_moneda);
			pstmt1.setTimestamp(2, fecha);
			pstmt1.setString(3, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteCotizacionException();
		}
	}

	@Override
	public void actualizarCotizacion(Cotizacion cotizacion, String codEmp, Connection con)
			throws ModificandoCotizacionException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarCotizacion();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info de la cotizacion*/
     		pstmt1 =  con.prepareStatement(update);
    		
    		pstmt1.setDouble(1, cotizacion.getCotizacion_compra());
    		pstmt1.setDouble(2, cotizacion.getCotizacion_venta());
    		pstmt1.setString(3, cotizacion.getUsuarioMod());
    		pstmt1.setString(4, cotizacion.getOperacion());
    		pstmt1.setString(5, cotizacion.getMoneda().getCod_moneda());
    		pstmt1.setTimestamp(6, cotizacion.getFecha());
    		pstmt1.setString(7, codEmp);
    		
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoCotizacionException();
		}
		
	}
	

}
