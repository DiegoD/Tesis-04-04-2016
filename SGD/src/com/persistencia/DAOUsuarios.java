package com.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Usuario;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;

public class DAOUsuarios implements IDAOUsuarios {

    
	//private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	
	@Override
	public boolean usuarioValido(LoginVO loginVO, Connection con) throws LoginException {
		
	   	boolean usuarioValido = false;
	   	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
		
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
			
			return usuarioValido;
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new LoginException();
			
		}
		
	}

	public ArrayList<Usuario> getUsuarios(Connection con) throws ObteniendoUsuariosException, ConexionException{
		
		ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
	
		try {
			
			System.out.println("estoy en DAO usuarios ");
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getUsuarios();
	    	PreparedStatement pstmt1;
	    	ResultSet rs;
	    	
    	
    		pstmt1 = con.prepareStatement(query);
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				Usuario usr = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
				
				System.out.println("Encontro usuario");
				lstUsuarios.add(usr);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoUsuariosException();
			
		}
    	
    	return lstUsuarios;
	}

	public boolean memberUsuario(String usuario, Connection con) throws ExisteUsuarioException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, usuario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteUsuarioException();
		}
	}

	public void insertarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarUsuario();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, user.getUsuario());
			pstmt1.setString(2, user.getNombre());
			pstmt1.setString(3, user.getPass());
			pstmt1.setString(4, user.getUsuarioMod());
			pstmt1.setString(5, user.getOperacion());
			pstmt1.setBoolean(6, user.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();

			
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoUsuarioException();
		} 
		
	}
	
	public void eliminarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String eliminar = clts.elminarUsuario();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, user.getUsuario());
			
			//pstmt1.setString(6, nuevo);
			System.out.println("voy a liminar " + user.getUsuario());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();

			
		} catch (SQLException e) {
			throw new InsertandoUsuarioException();
			
		} 
		
	}

}
