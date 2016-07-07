package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excepciones.Login.LoginException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.valueObject.LoginVO;

public class DAOUsuarios implements IDAOUsuarios {

    
	private java.sql.Connection con = null;
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

}
