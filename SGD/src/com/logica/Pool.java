package com.logica;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.excepciones.ConexionException;

public class Pool {

	private DataSource dataSource;
	
	String db;
	String url;
	String user;
	String pass;
	
	
	public Pool()
	{
		this.inicializaDataSource();
	}
	
	private void inicializaDataSource()
	{
		BasicDataSource basicDataSurce = new BasicDataSource();
		
		basicDataSurce.setDriverClassName("com.mysql.jdbc.Driver");
		
		basicDataSurce.setUsername("root");
		basicDataSurce.setPassword("root");
		basicDataSurce.setUrl("jdbc:mysql://localhost:3306/vaadin");
		basicDataSurce.setMaxTotal(50);
		
		this.dataSource = basicDataSurce;
		
		
	}
	
	public Connection obtenerConeccion() throws ConexionException 
	{
		try {
			
			return this.dataSource.getConnection();
			
		} catch (SQLException e) {
			
			throw new ConexionException();
		}
				
	}
	
	public void liberarConeccion(Connection con) throws ConexionException 
	{
		try {
			con.close();
			
		} catch (SQLException e) { 
			
			throw new ConexionException();
		}
		
	}
}
