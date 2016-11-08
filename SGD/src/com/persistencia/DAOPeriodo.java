package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.InsertandoPeriodoException;
import com.excepciones.Periodo.ModificandoPeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Periodo.ObteniendoPeriodosException;
import com.logica.Moneda;
import com.logica.Periodo.Periodo;

public class DAOPeriodo implements IDAOPeriodo{
	
	@Override
	public ArrayList<Periodo> getPeriodo(String codEmp, Connection con) throws ObteniendoPeriodosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Periodo> lstPeriodos = new ArrayList<Periodo>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getPeriodos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Periodo periodo;
			
			while(rs.next ()) {

				periodo = new Periodo();
				
				periodo.setMes(rs.getString(1));
				periodo.setAnio(rs.getInt(2));
				periodo.setAbierto(rs.getBoolean(3));
				periodo.setFechaMod(rs.getTimestamp(4));
				periodo.setUsuarioMod(rs.getString(5));
				periodo.setOperacion(rs.getString(6));
				
				lstPeriodos.add(periodo);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoPeriodosException();
		}
			
		return lstPeriodos;
	
	}
	
	

	/**
	 * Inserta una moneda en la base
	 * Pre condición: El código de moneda no debe existir previamente
	 */
	@Override
	public void insertarPeriodo(Periodo periodo, String codEmp, Connection con) throws InsertandoPeriodoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarPeriodo();
    	
    	PreparedStatement pstmt1;
    	    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, periodo.getMes());
			pstmt1.setInt(2, periodo.getAnio());
			pstmt1.setBoolean(3, periodo.getAbierto());
			pstmt1.setString(4, periodo.getUsuarioMod());
			pstmt1.setString(5, periodo.getOperacion());
			pstmt1.setString(6, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoPeriodoException();
		} 
	}

	@Override
	public boolean memberPeriodo(String mes, Integer anio, String codEmp, Connection con) throws ExistePeriodoException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberPeriodo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, mes);
			pstmt1.setInt(2, anio);
			pstmt1.setString(3, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExistePeriodoException();
		}
	}

	
	/**
	 * Actualizamos moneda dado el codigo,
	 * PRECONDICION: El código de la moneda debe existir
	 */
	@Override
	public void actualizarPeriodo(Periodo periodo,String codEmp, Connection con) throws ModificandoPeriodoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarPeriodo();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
			pstmt1.setBoolean(1, periodo.getAbierto());
			pstmt1.setString(2, periodo.getUsuarioMod());
			pstmt1.setString(3, periodo.getOperacion());
			pstmt1.setString(4, periodo.getMes());
			pstmt1.setInt(5, periodo.getAnio());
			pstmt1.setString(6, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoPeriodoException();
		}
	}

	@Override
	public boolean validaPeriodo(String mes, Integer anio, String codEmp, Connection con) throws NoExistePeriodoException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.validaPeriodo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, mes);
			pstmt1.setInt(2, anio);
			pstmt1.setString(3, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new NoExistePeriodoException();
		}
	}
}
