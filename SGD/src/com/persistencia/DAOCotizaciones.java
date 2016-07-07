package com.persistencia;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.*;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.valueObject.*;

public class DAOCotizaciones implements IDAOCotizaciones {

    private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException {
		
    	CotizacionVO cotizacionVO = null;
    	
		try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getCotizacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setDate(1, fecha);
			pstmt1.setInt(2, codMoneda);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) {
				
				cotizacionVO = new CotizacionVO();

				cotizacionVO.setFecha(rs.getDate(1));
				cotizacionVO.setCodMoneda(rs.getInt(2));
				cotizacionVO.setImpVenta(rs.getFloat(3));
				cotizacionVO.setImpCompra(rs.getFloat(4));
				cotizacionVO.setUsuarioMod(rs.getString(5));
				cotizacionVO.setFechaMod(rs.getDate(6));
				
			}
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoCotizacionException();
			
		}
			
		return cotizacionVO;
	}
    
    public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException{
    	
    	ArrayList<CotizacionVO> lstCotizaciones = new ArrayList<CotizacionVO>();
    	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getCotizaciones();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery();
			
			CotizacionVO cotizacionVO;
			
			while(rs.next ()) {
				
				cotizacionVO = new CotizacionVO();

				cotizacionVO.setFecha(rs.getDate(1));
				cotizacionVO.setCodMoneda(rs.getInt(2));
				cotizacionVO.setImpVenta(rs.getFloat(3));
				cotizacionVO.setImpCompra(rs.getFloat(4));
				cotizacionVO.setUsuarioMod(rs.getString(5));
				cotizacionVO.setFechaMod(rs.getDate(6));
				
				lstCotizaciones.add(cotizacionVO);
				
			}
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new ObteniendoCotizacionException();
			
		} 
			
		return lstCotizaciones;
    	
    }
    

    public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException{
    	
    	Consultas clts = new Consultas();
    	
    	String insert = clts.insertCotizacion();
    	
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	
    	
    	try {
    		
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setDate(1, cotizacionVO.getFecha());
			pstmt1.setInt(2, cotizacionVO.getCodMoneda());
			pstmt1.setFloat(3, cotizacionVO.getImpVenta());
			pstmt1.setFloat(4, cotizacionVO.getImpCompra());
			pstmt1.setString(5, cotizacionVO.getUsuarioMod());
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			con.close ();

			
		} catch (ClassNotFoundException e) {
			throw new IngresandoCotizacionException();
			
		} catch (SQLException e) {
			throw new IngresandoCotizacionException();
			
		}
    }
    	
	public boolean memberCotizacion(Date fecha, int codMoneda) throws MemberCotizacionException	{
		
		boolean existeCotizacion = false;
		try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.memberCotizacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setDate(1, fecha);
			pstmt1.setInt(2, codMoneda);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ())
				existeCotizacion = true;
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new MemberCotizacionException();
			
		}
			
		return existeCotizacion;
	}
	
	
	
	
}
