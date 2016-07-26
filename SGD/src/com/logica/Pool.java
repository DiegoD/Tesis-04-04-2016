package com.logica;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.excepciones.ConexionException;

public class Pool {

	private DataSource dataSource;
	
	private String url;
	private String user;
	private String pass;
	private String driver; 
	private int cantConexiones;
	
	private static volatile Pool INSTANCE = null;
	private static final Object lock = new Object();
	
	public static Pool getInstance() throws InstantiationException
	{
		if(INSTANCE == null)
        {
            synchronized (lock)
            {   
               INSTANCE = new Pool();
				
            }
        }
        
        return INSTANCE;
	}
	
	private Pool() throws InstantiationException 
	{
			/*Inicializamos variables para realizar conexion*/
			this.inicializarVariables();
			
			this.inicializaDataSource();
		
	}
	
	private void inicializarVariables() throws  InstantiationException
	{
		try
		{
		
		/*Carga datos del archivo datos.properties*/
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream stream = classLoader.getResourceAsStream("datos.properties");
	    
	    if (stream == null) {
	        
	    	throw new ConexionException();
	    } 
	    else {
	        Properties p = new Properties();
	        
	        
	        p.load(stream);
	       	        
	        this.driver =  p.getProperty("driver");
			this.user = p.getProperty("user");
			this.pass = p.getProperty("password");
			this.url = p.getProperty("url");
			this.cantConexiones = Integer.parseInt(p.getProperty("cantidad_conexiones"));
			
	      }
		}catch(Exception e)
		{
			throw new InstantiationException();
		}
	}
	
	private void inicializaDataSource() throws InstantiationException
	{
		BasicDataSource basicDataSurce = new BasicDataSource();
		
		try
		{
			basicDataSurce.setDriverClassName(driver);
			
			basicDataSurce.setUsername(user);
			basicDataSurce.setPassword(pass);
			basicDataSurce.setUrl(url);
			basicDataSurce.setMaxTotal(this.cantConexiones);
			
			this.dataSource = basicDataSurce;
		}catch(Exception e)
		{
			throw new InstantiationException();
		}
		
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
			if(con != null)
			{
				if(!con.isClosed())
					con.close();
			}
			
		} catch (SQLException e) { 
			
			throw new ConexionException();
		}
		
	}
	
	public void liberarConeccionRollback(Connection con) throws ConexionException 
	{
		try {
			if(con != null)
			{
				if(!con.isClosed()){
					con.close();
				}
			}
			
		} catch (SQLException e) { 
			
			throw new ConexionException();
		}
		
	}
}
