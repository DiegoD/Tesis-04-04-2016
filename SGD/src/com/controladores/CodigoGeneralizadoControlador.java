package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.NoExisteCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.logica.FachadaDD;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.EmpresaVO;

public class CodigoGeneralizadoControlador {
	
	public CodigoGeneralizadoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los códigos generalizados
	 */
	public ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizados() throws ObteniendoCodigosException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getCodigosGeneralizados();
	}
	
	/**
	 * Inserta un nuevo codigo generalizado
	 */
	public void insertarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO) throws InsertandoCodigoException, ExisteCodigoException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarCodigoGeneralizado(codigoGeneralizadoVO);
	}
	
	
	/**
	 * Actualiza los datos de un código generalizado
	 */
	public void actualizarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO) throws ConexionException, NoExisteCodigoException, ModificandoCodigoException, ExisteCodigoException, InicializandoException {
		FachadaDD.getInstance().actualizarCodigoGeneralizado(codigoGeneralizadoVO);
	}

}
