package com.abstractFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AbstractFactoryBuilder 
{
	private static AbstractFactoryBuilder instancia;
	private IAbstractFactory myAbstractFactory;
	
	private AbstractFactoryBuilder() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException 
	{

		/*Carga datos del archivo datos.properties*/
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream stream = classLoader.getResourceAsStream("datos.properties");
	    
	    if (stream == null) {
	        /*Archivo no encontrado*/
	    	throw new FileNotFoundException();
	    	
	    } 
	    else {
	    	
	        Properties p = new Properties();
	        p.load(stream);
	        String nomFab = p.getProperty("abstractFactory");
	        this.myAbstractFactory = (IAbstractFactory) Class.forName(nomFab).newInstance();
	      }
	}

	public static  AbstractFactoryBuilder getInstancia() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException
	{
		if (instancia == null)
			instancia = new AbstractFactoryBuilder();
		return instancia;
	}
	
	public IAbstractFactory getAbstractFactory()
	{
		return this.myAbstractFactory;
	}
}
