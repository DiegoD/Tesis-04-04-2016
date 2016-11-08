package com.controladores;

import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.ExisteNacional;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.NoExisteMonedaException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.InsertandoPeriodoException;
import com.excepciones.Periodo.ModificandoPeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Periodo.ObteniendoPeriodosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Periodo.PeriodoVO;

public class PeriodoControlador {
	
	public PeriodoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los períodos
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<PeriodoVO> getPeriodos(UsuarioPermisosVO permisos) throws ObteniendoPeriodosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getPeriodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo período
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws ExistePeriodoException 
	 */
	public void insertarPeriodo(PeriodoVO periodoVO, UsuarioPermisosVO permisos) throws InsertandoPeriodoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException, ExistePeriodoException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarPeriodo(periodoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Actualiza los datos de un período
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarPeriodo(PeriodoVO periodoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExistePeriodoException, ModificandoPeriodoException, ExistePeriodoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarPeriodo(periodoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}

	public boolean validaPeriodo(String mes, int anio, UsuarioPermisosVO permisos) throws ExistePeriodoException, ConexionException, SQLException, NoExistePeriodoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().validaPeriodo(mes, anio, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
}
