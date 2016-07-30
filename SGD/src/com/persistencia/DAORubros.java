package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.logica.Empresa;
import com.logica.Rubro;

public class DAORubros implements IDAORubros{

	@Override
	public ArrayList<Rubro> getRubros(Connection con) throws ObteniendoRubrosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Rubro> lstEmpresas = new ArrayList<Rubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getRubros();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Rubro rubro;
			
			while(rs.next ()) {

				rubro = new Rubro();
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcion(rs.getString(2));
				rubro.setActivo(rs.getBoolean(3));
				rubro.setFechaMod(rs.getTimestamp(4));
				rubro.setUsuarioMod(rs.getString(5));
				rubro.setOperacion(rs.getString(6));
				
				lstEmpresas.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstEmpresas;
	}

	@Override
	/**
	 * Inserta un rubro en la base
	 * Pre condición: El código de rubro no debe existir previamente
	 */
	public void insertarRubro(Rubro rubro, Connection con) throws InsertandoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarRubro();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, rubro.getCod_rubro());
			pstmt1.setString(2, rubro.getDescripcion());
			pstmt1.setBoolean(3, rubro.isActivo());
			pstmt1.setString(4, rubro.getOperacion());
			pstmt1.setBoolean(5, rubro.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoRubroException();
		} 
		
	}

	@Override
	/**
	 * Dado el codigo de rubro, valida si existe
	 */
	public boolean memberRubro(String cod_rubro, Connection con) throws ExisteRubroException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberRubro();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_rubro);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteRubroException();
		}
	}
	
	/**
	 * Actualizamos el rubro dado el codigo,
	 * PRECONDICION: El código del rubro debe existir
	 */
	@Override
	public void actualizarRubro(Rubro rubro, Connection con) throws ModificandoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarRubro();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, rubro.getDescripcion());
			pstmt1.setBoolean(2, rubro.isActivo());
			pstmt1.setString(3, rubro.getUsuarioMod());
			pstmt1.setString(4, rubro.getOperacion());
			pstmt1.setString(5, rubro.getCod_rubro());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoRubroException();
		}
		
	}
	

}
