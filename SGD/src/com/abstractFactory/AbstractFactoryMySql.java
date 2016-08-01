package com.abstractFactory;

import com.persistencia.*;

public class AbstractFactoryMySql implements IAbstractFactory
{

	@Override
	public IDAOUsuarios crearDAOUsuarios() {
		
		return new DAOUsuarios();
	}

	@Override
	public IDAOGrupos crearDAOGrupos() {
		
		return new DAOGrupos();
	}

	@Override
	public IDAOClientes crearDAOClientes() {
		
		return new DAOClientes();
	}
	

	@Override
	public IDAOImpuestos crearDAOImpuestos() {
		
		return new DAOImpuestos();
	}
	
	@Override
	public IDAOMonedas crearDAOMonedas() {
		
		return new DAOMonedas();
	}
	
	@Override
	public IDAOEmpresas crearDAOEmpresas() {
		
		return new DAOEmpresas();
	}
	
	@Override
	public IDAORubros crearDAORubros() {
		
		return new DAORubros();
	}
	
	@Override
	public IDAODocumentos crearDAODocumentos() {
		
		return new DAODocumentos();
	}
	
	@Override
	public IDAOCodigosGeneralizados crearDAOCodigosGeneralizados () {
		
		return new DAOCodigoGeneralizado();
	}

}
