package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Cliente;
import com.logica.Documento;
import com.logica.Titular;

public class DAOTitulares implements IDAOTitulares{
	
	private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public ArrayList<Titular> getTitularesActivos(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException {
    	
    	ArrayList<Titular> lstTitulares = new ArrayList<Titular>();
    	
    	try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getTitularesTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Titular aux;
			Documento doc;
			while(rs.next ()) {
				
				aux = new Titular();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"), rs.getString("m_documdgi.nombre"), rs.getString("nro_dgi")));
				aux.setTipo(rs.getString("tipo").toUpperCase());
				
							
				lstTitulares.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoTitularesException();
			
		}

    	return lstTitulares;
    }
    

public ArrayList<Titular> getTitularesTodos(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException {
    	
    	ArrayList<Titular> lstTitulares = new ArrayList<Titular>();
    	
    	try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getTitularesActivos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Titular aux;
			Documento doc;
			while(rs.next ()) {
				
				aux = new Titular();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"), rs.getString("m_documdgi.nombre"), rs.getString("nro_dgi")));
				aux.setTipo(rs.getString("tipo").toUpperCase());
				
							
				lstTitulares.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoTitularesException();
			
		}

    	return lstTitulares;
    }
    
    
    public ArrayList<Titular> getTitularesActivosFuncioanrios(Connection con, String codEmp) throws ObteniendoTitularesException, ConexionException {
    	
    	ArrayList<Titular> lstTitulares = new ArrayList<Titular>();
    	
    	try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getTitularesActivosFuncionarios();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Titular aux;
			Documento doc;
			while(rs.next ()) {
				
				aux = new Titular();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"), rs.getString("m_documdgi.nombre"), rs.getString("nro_dgi")));
				aux.setTipo(rs.getString("tipo").toUpperCase());
				
							
				lstTitulares.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoTitularesException();
			
		}

    	return lstTitulares;
    }
}
