package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.TipoRubro.ExisteTipoRubroException;
import com.excepciones.TipoRubro.InsertandoTipoRubroException;
import com.excepciones.TipoRubro.ModificandoTipoRubroException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.logica.TipoRubro;

public class DAOTipoRubro implements IDAOTipoRubro{

	@Override
	public ArrayList<TipoRubro> getTipoRubros(Connection con, String cod_emp) throws ObteniendoTipoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<TipoRubro> lstTipoRubros = new ArrayList<TipoRubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getTipoRubros();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			TipoRubro tipoRubro;
			
			while(rs.next ()) {

				tipoRubro = new TipoRubro();
				tipoRubro.setCod_tipoRubro(rs.getString(1));
				tipoRubro.setDescripcion(rs.getString(2));
				tipoRubro.setFechaMod(rs.getTimestamp(3));
				tipoRubro.setUsuarioMod(rs.getString(4));
				tipoRubro.setOperacion(rs.getString(5));
				tipoRubro.setActivo(rs.getBoolean(6));
				tipoRubro.setCod_emp(rs.getString(7));
				
				lstTipoRubros.add(tipoRubro);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			throw new ObteniendoTipoRubroException();
		}
			
		return lstTipoRubros;
	}
	
	@Override
	public ArrayList<TipoRubro> getTipoRubrosActivos(Connection con, String cod_emp) throws ObteniendoTipoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<TipoRubro> lstTipoRubros = new ArrayList<TipoRubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getTipoRubrosActivos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			TipoRubro tipoRubro;
			
			while(rs.next ()) {

				tipoRubro = new TipoRubro();
				tipoRubro.setCod_tipoRubro(rs.getString(1));
				tipoRubro.setDescripcion(rs.getString(2));
				tipoRubro.setFechaMod(rs.getTimestamp(3));
				tipoRubro.setUsuarioMod(rs.getString(4));
				tipoRubro.setOperacion(rs.getString(5));
				tipoRubro.setActivo(rs.getBoolean(6));
				tipoRubro.setCod_emp(rs.getString(7));
				
				lstTipoRubros.add(tipoRubro);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoTipoRubroException();
		}
			
		return lstTipoRubros;
	}

	@Override
	public void insertarTipoRubro(TipoRubro tipoRubro, String cod_emp, Connection con)
			throws InsertandoTipoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarTipoRubro();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, tipoRubro.getCod_tipoRubro());
			pstmt1.setString(2, tipoRubro.getDescripcion());
			pstmt1.setString(3, tipoRubro.getUsuarioMod());
			pstmt1.setString(4, tipoRubro.getOperacion());
			pstmt1.setBoolean(5, tipoRubro.isActivo());
			pstmt1.setString(6, cod_emp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoTipoRubroException();
		} 
		
	}

	@Override
	public boolean memberTipoRubro(String cod_tipoRubro, String cod_emp, Connection con)
			throws ExisteTipoRubroException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberTipoRubro();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_tipoRubro);
			pstmt1.setString(2, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteTipoRubroException();
		}
	}

	@Override
	public void actualizarTipoRubro(TipoRubro tipoRubro, String cod_emp, Connection con)
			throws ModificandoTipoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarTipoRubro();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, tipoRubro.getDescripcion());
			pstmt1.setBoolean(2, tipoRubro.isActivo());
			pstmt1.setString(3, tipoRubro.getUsuarioMod());
			pstmt1.setString(4, tipoRubro.getOperacion());
			pstmt1.setString(5, tipoRubro.getCod_tipoRubro());
			pstmt1.setString(6, cod_emp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoTipoRubroException();
		}
		
	}

}
