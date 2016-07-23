package com.abstractFactory;

import com.persistencia.IDAOGrupos;
import com.persistencia.IDAOUsuarios;

public interface IAbstractFactory 
{

	public IDAOUsuarios crearDAOUsuarios();
	
	public IDAOGrupos crearDAOGrupos();
}
