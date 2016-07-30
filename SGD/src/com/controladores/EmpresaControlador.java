package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.logica.FachadaDD;
import com.valueObject.EmpresaVO;
import com.valueObject.ImpuestoVO;

public class EmpresaControlador {
	
	public EmpresaControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas las empresas
	 */
	public ArrayList<EmpresaVO> getEmpresas() throws ObteniendoEmpresasException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getEmpresas();
	}
	
	/**
	 * Inserta una nueva empresa
	 */
	public void insertarEmpresa(EmpresaVO empresaVO) throws InsertandoEmpresaException, ExisteEmpresaException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarEmprea(empresaVO);
	}
	
	
	/**
	 * Actualiza los datos de una empresa
	 */
	public void actualizarEmpresa(EmpresaVO empresaVO) throws ConexionException, NoExisteEmpresaException, ModificandoEmpresaException, ExisteEmpresaException, InicializandoException {
		FachadaDD.getInstance().actualizarEmpresa(empresaVO);
	}
}
