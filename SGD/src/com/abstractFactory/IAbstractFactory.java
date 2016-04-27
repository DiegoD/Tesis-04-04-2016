package com.abstractFactory;

import com.persistencia.IDAOCotizaciones;
import com.persistencia.IDAODocumentosAduaneros;
import com.persistencia.IDAOMonedas;
import com.persistencia.IDaoImpuesto;

public interface IAbstractFactory 
{
	public IDaoImpuesto crearDaoImpuestos();
	
	public IDAOCotizaciones crearDAOCotizaciones();
	
	public IDAOMonedas crearDAOMonedas();
	
	public IDAODocumentosAduaneros crearDAODocumentosAduaneros();
}
