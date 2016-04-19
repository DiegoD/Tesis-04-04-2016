package com.abstractFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class AbstractFactoryBuilder 
{
	private static AbstractFactoryBuilder instancia;
	private IAbstractFactory myAbstractFactory;
	
	private AbstractFactoryBuilder() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException 
	{
		/*System.out.println(System.getProperty("user.dir"));
		
		Properties p = new Properties();
		String nomArch = "config/datos.properties";
		p.load(new FileInputStream(nomArch));

		String nomFab = p.getProperty("abstractFactory");*/
		
		// Esto es cuando lee desde archivo this.myAbstractFactory = (IAbstractFactory) Class.forName(nomFab).newInstance();
		this.myAbstractFactory = (IAbstractFactory) Class.forName("com.abstractFactory.AbstractFactoryMySql").newInstance();
		
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
