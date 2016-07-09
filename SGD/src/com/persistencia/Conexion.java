package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.logica.Fachada;
import com.mysql.jdbc.Connection;

public class Conexion {
	
	
	private java.sql.Connection con = null;
	private String URL;
	private String user;
	private String pass;
	
	public Conexion() throws ClassNotFoundException, ConexionException{
		
		Consultas clts = new Consultas();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			
			this.con = (java.sql.Connection) DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
		} 
		catch (SQLException e) {
			
			throw new ConexionException();
		}
	}
	
	public java.sql.Connection getConnection() {
		return con;
	}
	
	public void cerrarConnection(){
		con = null;
	}
}
