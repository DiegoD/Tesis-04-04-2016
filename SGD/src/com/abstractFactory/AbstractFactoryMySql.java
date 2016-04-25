package com.abstractFactory;

import com.persistencia.DAOImpuestos;
import com.persistencia.IDaoImpuesto;

public class AbstractFactoryMySql implements IAbstractFactory
{

	public IDaoImpuesto crearDaoImpuestos() 
	{
		return new DAOImpuestos();
	}
	
	

}
