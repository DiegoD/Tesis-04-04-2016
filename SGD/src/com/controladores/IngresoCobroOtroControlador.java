package com.controladores;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.Fachada;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;

public class IngresoCobroOtroControlador extends IngresoCobroControlador{

	/**
	 * Obtiene los cobros del sistema
	 */
	@Override
	public ArrayList<IngresoCobroVO> getIngresoCobroTodos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoIngresoCobroException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getIngresoCobroTodosOtro(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
    }
	
}
