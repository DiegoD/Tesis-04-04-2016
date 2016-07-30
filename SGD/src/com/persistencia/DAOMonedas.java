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
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.logica.Impuesto;
import com.logica.Moneda;

public class DAOMonedas implements IDAOMonedas{

	@Override
	public ArrayList<Moneda> getMonedas(Connection con) throws ObteniendoMonedaException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Moneda> lstMonedas = new ArrayList<Moneda>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getMonedas();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Moneda moneda;
			
			while(rs.next ()) {

				moneda = new Moneda();
				
				moneda.setCod_moneda(rs.getString(1));
				moneda.setDescripcion(rs.getString(2));
				moneda.setAcepta_cotizacion(rs.getBoolean(3));
				moneda.setActivo(rs.getBoolean(4));
				moneda.setFechaMod(rs.getTimestamp(5));
				moneda.setUsuarioMod(rs.getString(6));
				moneda.setOperacion(rs.getString(7));
				
				lstMonedas.add(moneda);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoMonedaException();
		}
			
		return lstMonedas;
	
	}

	/**
	 * Inserta una moneda en la base
	 * Pre condición: El código de moneda no debe existir previamente
	 */
	@Override
	public void insertarMoneda(Moneda moneda, Connection con) throws InsertandoMonedaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarMoneda();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, moneda.getCod_moneda());
			pstmt1.setString(2, moneda.getDescripcion());
			pstmt1.setBoolean(3, moneda.isAcepta_cotizacion());
			pstmt1.setBoolean(4, moneda.isActivo());
			pstmt1.setString(5, moneda.getUsuarioMod());
			pstmt1.setString(6, moneda.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoMonedaException();
		} 
	}

	@Override
	public boolean memberMoneda(String cod_moneda, Connection con) throws ExisteMonedaException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberMoneda();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_moneda);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteMonedaException();
		}
	}

	@Override
	public void eliminarMoneda(String cod_moneda, Connection con) throws ModificandoMonedaException, ConexionException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Actualizamos moneda dado el codigo,
	 * PRECONDICION: El código de la moneda debe existir
	 */
	@Override
	public void actualizarMoneda(Moneda moneda, Connection con) throws ModificandoMonedaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarMoneda();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
			pstmt1.setString(1, moneda.getDescripcion());
			pstmt1.setBoolean(2, moneda.isAcepta_cotizacion());
			pstmt1.setBoolean(3, moneda.isActivo());
			pstmt1.setString(4, moneda.getUsuarioMod());
			pstmt1.setString(5, moneda.getOperacion());
			pstmt1.setString(6, moneda.getCod_moneda());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoMonedaException();
		}
	}
	
}
