package com.abstractFactory;

import com.persistencia.IDAODocumentos;
import com.persistencia.IDAOEmpresas;
import com.persistencia.IDAOGrupos;
import com.persistencia.IDAOImpuestos;
import com.persistencia.IDAOMonedas;
import com.persistencia.IDAORubros;
import com.persistencia.IDAOUsuarios;

public interface IAbstractFactory 
{

	public IDAOUsuarios crearDAOUsuarios();
	
	public IDAOGrupos crearDAOGrupos();
	
	public IDAOImpuestos crearDAOImpuestos();
	
	public IDAOMonedas crearDAOMonedas();
	
	public IDAOEmpresas crearDAOEmpresas() ;
	
	public IDAORubros crearDAORubros();
	
	public IDAODocumentos crearDAODocumentos();
}
