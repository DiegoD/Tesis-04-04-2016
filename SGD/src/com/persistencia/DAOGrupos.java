package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Grupo;
import com.valueObject.CotizacionVO;
import com.valueObject.GrupoVO;

public class DAOGrupos implements IDAOGrupos {
	
	private Conexion conexion;
	private java.sql.Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	

	@Override
	public ArrayList<JSONObject> getGrupos(Connection con) throws ObteniendoGruposException, ConexionException {
		
		ArrayList<JSONObject> lstGrupos = new ArrayList<JSONObject>();
	
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getGrupos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery();
			
			GrupoVO grupoVO;
			
			while(rs.next ()) {

				JSONObject obj = new JSONObject();

				obj.put("codGrupo", rs.getString(1));
				obj.put("nomGrupo", rs.getString(2));
				obj.put("fechaMod", rs.getTimestamp(3));
				obj.put("usuarioMod", rs.getString(4));
				obj.put("operacion", rs.getString(5));

				lstGrupos.add(obj);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoGruposException();
		}
			
		return lstGrupos;
	}


	/**
	 * Insertamos grupo dado grupo,
	 * PRECONDICION: El código del codigo no debe existir
	 *
	 */
	public void insertarGrupo(Grupo grupo, Connection con) throws InsertandoGrupoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarGrupo();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, grupo.getCodGrupo());
			pstmt1.setString(2, grupo.getNomGrupo());
			pstmt1.setString(3, grupo.getUsuarioMod());
			pstmt1.setString(4, grupo.getOperacion());

			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} catch (SQLException e) {
			
			throw new InsertandoGrupoException();
			
		} 
		
	}
	
	/**
	 * Nos retorna true si existe el código del grupo
	 *
	 */
	public boolean memberGrupo(String codGrupo, Connection con) throws MemberGrupoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
						
			Consultas consultas = new Consultas ();
			String query = consultas.memberGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
						
			return existe;
			
		}catch(SQLException e){
			
			throw new MemberGrupoException();
		}
	}
	
	/**
	 * Eliminamos grupo dado el codigo,
	 * PRECONDICION: El código del codigo debe existir
	 *
	 */
	public void eliminarGrupo(String codGrupo, Connection con) throws ModificandoGrupoException, ConexionException
	{
		Consultas consultas = new Consultas ();
		String delete = consultas.eliminarGrupo();
		
		PreparedStatement pstmt1;
		
		
		try 
		{
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setString(1, codGrupo);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoGrupoException();
		}
		
		
	}


	
	    	

}
