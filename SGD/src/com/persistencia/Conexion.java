package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.logica.Fachada;
import com.mysql.jdbc.Connection;

public class Conexion {
	
	public static Conexion instance;
	private Conexion cnn = null;
	
	public Conexion() throws ClassNotFoundException{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.cnn = (Conexion) DriverManager.getConnection ("jdbc:mysql://localhost:3306/vaadin","root","root");
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Conexion getInstanceConexion() throws ClassNotFoundException{
        
        if(instance == null)
        {
        	instance = new Conexion();
        }
        
        return instance;
    }
	
	public Conexion getConn(){
		return cnn;
	}
	
	public void cerrarConexion(){
		instance = null;
	}

}
