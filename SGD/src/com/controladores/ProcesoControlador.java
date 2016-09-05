package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.proceso.ProcesoVO;

public class ProcesoControlador {
	
	public ProcesoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los proceso
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<ProcesoVO> getProcesos(UsuarioPermisosVO permisos) throws ObteniendoProcesosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getProcesos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo proceso
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarProceso(ProcesoVO procesoVO, UsuarioPermisosVO permisos) throws IngresandoProcesoException, ExisteProcesoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarProceso(procesoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un proceso
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarProceso(ProcesoVO procesoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteProcesoException, ModificandoProcesoException, ExisteProcesoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarProceso(procesoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
}
