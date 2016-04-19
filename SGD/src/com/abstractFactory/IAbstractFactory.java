package com.abstractFactory;

import com.persistencia.IDaoImpuesto;

public interface IAbstractFactory 
{
	public IDaoImpuesto crearDaoImpuestos();
}
