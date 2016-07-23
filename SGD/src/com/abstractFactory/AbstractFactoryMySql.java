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

}
