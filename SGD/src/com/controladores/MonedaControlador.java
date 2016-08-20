package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.NoExisteMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;

public class MonedaControlador {

	public MonedaControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas las monedas
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<MonedaVO> getMonedas(UsuarioPermisosVO permisos) throws ObteniendoMonedaException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMonedas();
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta una nueva moneda
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarMoneda(MonedaVO monedaVO, UsuarioPermisosVO permisos) throws InsertandoMonedaException, ExisteMonedaException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarMoneda(monedaVO);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Actualiza los datos de una moneda
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarMoneda(MonedaVO monedaVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteMonedaException, ModificandoMonedaException, ExisteMonedaException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarMoneda(monedaVO);
		else
			throw new NoTienePermisosException();
	}
	
}
