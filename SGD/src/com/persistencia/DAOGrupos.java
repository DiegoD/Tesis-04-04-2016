package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.valueObject.CotizacionVO;
import com.valueObject.GrupoVO;

public class DAOGrupos implements IDAOGrupos {
	
	private Conexion conexion;
	private java.sql.Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	

	@Override
	public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException, ConexionException {
		
	
		//ArrayList<GrupoVO> lstGrupos = new ArrayList<GrupoVO>();
		ArrayList<JSONObject> lstGrupos = new ArrayList<JSONObject>();
	
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
			this.conexion = new Conexion();
			this.con = conexion.getConnection();
			
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
			con.close ();
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new ObteniendoGruposException();
			
		}
			
		return lstGrupos;
	}


	@Override
	public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarGrupo();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			this.conexion = new Conexion();
			this.con = conexion.getConnection();
			
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, grupoVO.getCodGrupo());
			pstmt1.setString(2, grupoVO.getNomGrupo());
			pstmt1.setString(3, grupoVO.getUsuarioMod());
			pstmt1.setString(4, grupoVO.getOperacion());
			//pstmt1.setDate(5, grupoVO.getFechaMod());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			con.close ();

			
		} catch (ClassNotFoundException | SQLException e) {
			throw new InsertandoGrupoException();
			
		} 
		
	}
	
	public boolean memberGrupo(String codGrupo) throws MemberGrupoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			this.conexion = new Conexion();
			this.con = conexion.getConnection();
			
			
			
			Consultas consultas = new Consultas ();
			String query = consultas.memberGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			con.close ();
			
			return existe;
			
		}catch(SQLException | ClassNotFoundException e){
			
			throw new MemberGrupoException();
		}
	}
	    	

}
