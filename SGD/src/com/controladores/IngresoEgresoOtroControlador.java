package com.controladores;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Egresos.ObteniendoEgresoCobroException;
import com.logica.Fachada;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;

public class IngresoEgresoOtroControlador extends IngresoEgresoControlador {
	
	/**
	 * Obtiene los cobros del sistema
	 */
	@Override
	public ArrayList<IngresoCobroVO> getIngresoEgresoTodos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoEgresoCobroException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getEgresoCobroOtroTodos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
    }

}
