package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.documentosAduaneros.*;
import com.valueObject.DocumentoAuaneroVO;


public class DAODocumentosAduaneros {
	
    private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    
    /*Nos retorna los documentos que esten activos*/
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
				documentoAduaneroVO.setUsuarioMod(rs.getString(4));
				documentoAduaneroVO.setFechaMod(rs.getDate(5));
				
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

    
    /*Nos retorna todos los documentos*/
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
				documentoAduaneroVO.setUsuarioMod(rs.getString(4));
				documentoAduaneroVO.setFechaMod(rs.getDate(5));
				
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
   
    /*Dado el codigo del documento nos lo retorna*/
    public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException {
		
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
				documentoAduaneroVO.setUsuarioMod(rs.getString(4));
				documentoAduaneroVO.setFechaMod(rs.getDate(5));
				
				
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
    
    /*Dadp el codigo del documento, retornamos true si existe*/
    public boolean  memberDocumentosAduanerosActivos(int codDocum) throws MemberDocumentosAduanerosException {
		
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
			throw new MemberDocumentosAduanerosException();
			
		}
			
		return existe;
	}
    
    public void insertCotizacion(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException{
    	
    	Consultas clts = new Consultas();
    	
    	String insert = clts.insertDocumentoAduanero();
    	
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	
    	
    	try {
    		
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, documentoAuaneroVO.getNomDocum());
			pstmt1.setBoolean(2, documentoAuaneroVO.isActivo());
			pstmt1.setString(3, documentoAuaneroVO.getUsuarioMod());
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			con.close ();

			
		} catch (ClassNotFoundException e) {
			throw new IngresandoDocumentoAduaneroException();
			
		} catch (SQLException e) {
			throw new IngresandoDocumentoAduaneroException();
			
		}
    }
}
