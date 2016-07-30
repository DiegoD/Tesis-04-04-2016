package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
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
import com.logica.FachadaDD;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;

public class MonedaControlador {

	public MonedaControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas las monedas
	 */
	public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedaException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getMonedas();
	}
	
	/**
	 * Inserta una nueva moneda
	 */
	public void insertarMoneda(MonedaVO monedaVO) throws InsertandoMonedaException, ExisteMonedaException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarMoneda(monedaVO);
	}
	
	/**
	 * Actualiza los datos de una moneda
	 */
	public void actualizarMoneda(MonedaVO monedaVO) throws ConexionException, NoExisteMonedaException, ModificandoMonedaException, ExisteMonedaException, InicializandoException {
		FachadaDD.getInstance().actualizarMoneda(monedaVO);
	}
	
}
