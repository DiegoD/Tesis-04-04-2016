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
	        // File not nound
	    	System.out.println("no lo encontro");
	    } 
	    else {
	    	System.out.println("encontro");
	        Properties p = new Properties();
	        p.load(stream);
	        String nomFab = p.getProperty("abstractFactory");
	        this.myAbstractFactory = (IAbstractFactory) Class.forName(nomFab).newInstance();
	      }
		/*
		Properties p = new Properties();
		String nomArch = "SGD/WEB-INF/classes/config/datos.properties";
		//p.load(new FileInputStream(nomArch));
		p.load(getClass().getResourceAsStream("resources/common/configure/commonData.properties"));
		System.out.println(p.getProperty("abstractFactory"));

		
		String nomFab = p.getProperty("abstractFactory");
		
		this.myAbstractFactory = (IAbstractFactory) Class.forName(nomFab).newInstance();
		
		// Esto es cuando lee desde archivo this.myAbstractFactory = (IAbstractFactory) Class.forName(nomFab).newInstance();
		//this.myAbstractFactory = (IAbstractFactory) Class.forName("com.abstractFactory.AbstractFactoryMySql").newInstance();
		*/
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
