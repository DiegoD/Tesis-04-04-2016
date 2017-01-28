package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.DocumDGI;

public class DAODocumDGI implements IDAODocumDgi{

	@Override
	public ArrayList<DocumDGI> getDocumentos(Connection con) throws ObteniendoDocumentosException, ConexionException {
		
		ArrayList<DocumDGI> lstDocumentos = new ArrayList<DocumDGI>();
		
		try
		{
			Consultas consultas = new Consultas();
			String query = consultas.getDocumentos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			DocumDGI documento;
			
			while(rs.next ()) {

				documento = new DocumDGI();
				
				documento.setCod_docucmento(rs.getString(1));
				documento.setDescirpcion(rs.getString(2));
				documento.setActivo(rs.getBoolean(3));
				documento.setFechaMod(rs.getTimestamp(4));
				documento.setUsuarioMod(rs.getString(5));
				documento.setOperacion(rs.getString(6));
				
				
				lstDocumentos.add(documento);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoDocumentosException();
		}
			
		return lstDocumentos;

	}

	/**
	 * Inserta un documento en la base
	 * Pre condición: El código de documento no debe existir previamente
	 */
	@Override
	public void insertarDocumento(DocumDGI documento, Connection con)
			throws InsertandoDocumentoException, ConexionException {
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarDocumento();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, documento.getCod_docucmento());
			pstmt1.setString(2, documento.getDescirpcion());
			pstmt1.setBoolean(3, documento.isActivo());
			pstmt1.setString(4, documento.getUsuarioMod());
			pstmt1.setString(5, documento.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoDocumentoException();
		} 
		
	}

	/**
	 * Dado el codigo de documento, valida si existe
	 */
	@Override
	public boolean memberDocumento(String cod_documento, Connection con)
			throws ExisteDocumentoException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberDocumento();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_documento);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteDocumentoException();
		}
	}

	@Override
	public void actualizarDocumento(DocumDGI documento, Connection con)
			throws ModificandoDocumentoException, ConexionException {
		
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarDocumento();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, documento.getDescirpcion());
			pstmt1.setBoolean(2, documento.isActivo());
			pstmt1.setString(3, documento.getUsuarioMod());
			pstmt1.setString(4, documento.getOperacion());
			pstmt1.setString(5, documento.getCod_docucmento());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoDocumentoException();
		}
		
	}
	
}
