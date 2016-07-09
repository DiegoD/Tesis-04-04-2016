package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Usuario;
import com.valueObject.LoginVO;

public class DAOUsuarios implements IDAOUsuarios {

    
	//private java.sql.Connection con = null;
	private Conexion conexion;
	private java.sql.Connection con;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	
	@Override
	public boolean usuarioValido(LoginVO loginVO) throws LoginException {
		
	   	boolean usuarioValido = false;
	   	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			
		
			Consultas consultas = new Consultas ();
			String query = consultas.getUsuarioValido();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, loginVO.getUsuario());
			pstmt1.setString(2, loginVO.getPass());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ())
				usuarioValido = true;
			
			rs.close ();
			pstmt1.close ();
			con.close ();
			
			return usuarioValido;
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new LoginException();
			
		}
		
	}

	public ArrayList<Usuario> getUsuarios() throws ClassNotFoundException, ObteniendoUsuariosException, ConexionException{
		
		System.out.println("estoy en DAO usuarios ");
		conexion = new Conexion();
    	Consultas clts = new Consultas();
    	String query = clts.getUsuarios();
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
    	
    	try {
    		
    		this.con = conexion.getConnection();
    		pstmt1 = con.prepareStatement(query);
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				Usuario usr = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3));
				
				System.out.println("Encontro usuario");
				//org.json.simple.JSONObject obj = new org.json.simple.JSONObject();

				//obj.put("usuario", rs.getString(1));
				//obj.put("pass", rs.getString(2));
				//obj.put("nombre", rs.getString(3));
				
				
				
				lstUsuarios.add(usr);
				
			}
			rs.close ();
			pstmt1.close ();
			con.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoUsuariosException();
			
		}
    	
    	return lstUsuarios;
	}
}
