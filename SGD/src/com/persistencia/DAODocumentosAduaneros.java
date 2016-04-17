package com.persistencia;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.valueObject.CotizacionVO;
import com.valueObject.DocumentoAuaneroVO;
import com.excepciones.documentosAduaneros.*;

public class DAODocumentosAduaneros {
	
    private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public ArrayList<DocumentoAuaneroVO>  getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException {
		
    	DocumentoAuaneroVO documentoAduaneroVO = null;
    	
    	ArrayList<DocumentoAuaneroVO> lstDocs = new ArrayList<DocumentoAuaneroVO>();


		try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getDocumentosAduanerosActivos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				documentoAduaneroVO = new DocumentoAuaneroVO();
				
				
				documentoAduaneroVO.setCodDocum(rs.getInt(1));
				documentoAduaneroVO.setNomDocum(rs.getString(2));
				documentoAduaneroVO.setActivo(rs.getBoolean(3));
				
				lstDocs.add(documentoAduaneroVO);
		}
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoDocumentoAduaneroException();
			
		}
			
		return lstDocs;
	}

    public ArrayList<DocumentoAuaneroVO>  getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException {
		
    	DocumentoAuaneroVO documentoAduaneroVO = null;
    	
    	ArrayList<DocumentoAuaneroVO> lstDocs = new ArrayList<DocumentoAuaneroVO>();


		try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getDocumentosAduanerosTodos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				documentoAduaneroVO = new DocumentoAuaneroVO();

				documentoAduaneroVO.setCodDocum(rs.getInt(1));
				documentoAduaneroVO.setNomDocum(rs.getString(2));
				documentoAduaneroVO.setActivo(rs.getBoolean(3));
				
				lstDocs.add(documentoAduaneroVO);
		}
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoDocumentoAduaneroException();
			
		}
			
		return lstDocs;
	}
   
    public DocumentoAuaneroVO  getDocumentosAduanerosActivos(int codDocum) throws ObteniendoDocumentoAduaneroException {
		
    	DocumentoAuaneroVO documentoAduaneroVO = null;
    	
    	try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getDocumentosAduanerosActivos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codDocum);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) {
				
				documentoAduaneroVO = new DocumentoAuaneroVO();
				
				
				documentoAduaneroVO.setCodDocum(rs.getInt(1));
				documentoAduaneroVO.setNomDocum(rs.getString(2));
				documentoAduaneroVO.setActivo(rs.getBoolean(3));
				
				
		}
			
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoDocumentoAduaneroException();
			
		}
			
		return documentoAduaneroVO;
	}
    
    public boolean  memberDocumentosAduanerosActivos(int codDocum) throws ObteniendoDocumentoAduaneroException {
		
    	boolean existe = false;
    	
    	DocumentoAuaneroVO documentoAduaneroVO = null;
    	
    	try
		{
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			Consultas consultas = new Consultas ();
			String query = consultas.getDocumentosAduanerosActivos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codDocum);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			con.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoDocumentoAduaneroException();
			
		}
			
		return existe;
	}
}
