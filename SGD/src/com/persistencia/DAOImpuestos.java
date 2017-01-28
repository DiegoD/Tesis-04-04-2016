package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.logica.Impuesto;

public class DAOImpuestos implements IDAOImpuestos{

    
    /**
	 * Obtiene Array de todos lo impuestos existentes
	 */
	public ArrayList<Impuesto> getImpuestos(String codEmp, Connection con) throws ObteniendoImpuestosException, ConexionException{
			
		ArrayList<Impuesto> lstImpuestos = new ArrayList<Impuesto>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getImpuestos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Impuesto impuesto;
			
			while(rs.next ()) {

				impuesto = new Impuesto();
				
				impuesto.setCod_imp(rs.getString(1));
				impuesto.setDescripcion(rs.getString(2));
				impuesto.setPorcentaje(rs.getDouble(3));
				impuesto.setActivo(rs.getBoolean(4));
				impuesto.setFechaMod(rs.getTimestamp(5));
				impuesto.setUsuarioMod(rs.getString(6));
				impuesto.setOperacion(rs.getString(7));
				
				lstImpuestos.add(impuesto);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoImpuestosException();
		}
			
		return lstImpuestos;
	
	}
	
	/**
	 * Obtiene Array de todos lo impuestos existentes
	 */
	public ArrayList<Impuesto> getImpuestosActivos(String codEmp, Connection con) throws ObteniendoImpuestosException, ConexionException{
			
		ArrayList<Impuesto> lstImpuestos = new ArrayList<Impuesto>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getImpuestosActivos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Impuesto impuesto;
			
			while(rs.next ()) {

				impuesto = new Impuesto();
				
				impuesto.setCod_imp(rs.getString(1));
				impuesto.setDescripcion(rs.getString(2));
				impuesto.setPorcentaje(rs.getDouble(3));
				impuesto.setActivo(rs.getBoolean(4));
				impuesto.setFechaMod(rs.getTimestamp(5));
				impuesto.setUsuarioMod(rs.getString(6));
				impuesto.setOperacion(rs.getString(7));
				
				lstImpuestos.add(impuesto);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoImpuestosException();
		}
			
		return lstImpuestos;
	
	}

	/**
	 * Inserta un impuesto en la base
	 * Pre condición: El código de impuesto no debe existir previamente
	 */
	@Override
	public void insertarImpuesto(Impuesto impuesto, String codEmp, Connection con)
			throws InsertandoImpuestoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarImpuesto();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, impuesto.getCod_imp());
			pstmt1.setString(2, impuesto.getDescripcion());
			pstmt1.setDouble(3, impuesto.getPorcentaje());
			pstmt1.setBoolean(4, impuesto.isActivo());
			pstmt1.setString(5, impuesto.getUsuarioMod());
			pstmt1.setString(6, impuesto.getOperacion());
			pstmt1.setString(7, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoImpuestoException();
		} 
		
	}

	/**
	 * Dado el codigo de impuesto, valida si existe
	 */
	@Override
	public boolean memberImpuesto(String cod_impuesto, String codEmp, Connection con)
			throws ExisteImpuestoException, ConexionException {
		// TODO Auto-generated method stub
		
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberImpuesto();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_impuesto);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteImpuestoException();
		}
	}

	@Override
	public void eliminarImpuesto(String cod_impuesto, String codEmp, Connection con)
			throws ModificandoImpuestoException, ConexionException {
		// TODO Auto-generated method stub
	}

	/**
	 * Actualizamos impuesto dado el codigo,
	 * PRECONDICION: El código del codigo debe existir
	 */
	@Override
	public void actualizarImpuesto(Impuesto impuesto, String codEmp, Connection con) throws ModificandoImpuestoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarImpuesto();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, impuesto.getDescripcion());
			pstmt1.setDouble(2, impuesto.getPorcentaje());
			pstmt1.setBoolean(3, impuesto.isActivo());
			pstmt1.setString(4, impuesto.getUsuarioMod());
			pstmt1.setString(5, impuesto.getOperacion());
			pstmt1.setString(6, impuesto.getCod_imp());
			pstmt1.setString(7, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoImpuestoException();
		}
	}
}
