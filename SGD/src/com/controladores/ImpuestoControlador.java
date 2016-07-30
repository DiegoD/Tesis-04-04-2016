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
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.GrupoVO;
import com.valueObject.ImpuestoVO;

public class ImpuestoControlador {
	
	public ImpuestoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los impuestos
	 */
	public ArrayList<ImpuestoVO> getImpuestos() throws ObteniendoImpuestosException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getImpuestos();
	}
	
	/**
	 * Inserta un nuevo impuesto
	 */
	public void insertarImpuesto(ImpuestoVO impuestoVO) throws InsertandoImpuestoException, ExisteImpuestoException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarImpuesto(impuestoVO);
	}
	
	
	/**
	 * Actualiza los datos de un impuesto
	 */
	public void actualizarImpuesto(ImpuestoVO impuestoVO) throws ConexionException, NoExisteImpuestoException, ModificandoImpuestoException, ExisteImpuestoException, InicializandoException {
		FachadaDD.getInstance().actualizarImpuesto(impuestoVO);
	}
	
	
}
