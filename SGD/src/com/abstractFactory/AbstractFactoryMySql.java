package com.abstractFactory;

import com.persistencia.*;

public class AbstractFactoryMySql implements IAbstractFactory
{

	public IDaoImpuesto crearDaoImpuestos() 
	{
		return new DAOImpuestos();
	}
	
	public IDAOCotizaciones crearDAOCotizaciones() 
	{
		return new DAOCotizaciones();
	}
	
	public IDAOMonedas crearDAOMonedas() 
	{
		return new DAOMonedas();
	}
	
	public IDAODocumentosAduaneros crearDAODocumentosAduaneros() 
	{
		return new DAODocumentosAduaneros();
	}

}
