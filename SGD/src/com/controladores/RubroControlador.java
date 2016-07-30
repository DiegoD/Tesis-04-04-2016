package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.NoExisteRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.logica.FachadaDD;
import com.valueObject.RubroVO;

public class RubroControlador {
	
	public RubroControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas los rubros
	 */
	public ArrayList<RubroVO> getRubros() throws ObteniendoRubrosException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getRubros();
	}
	
	/**
	 * Inserta un nuevo rubro
	 */
	public void insertarRubro(RubroVO rubroVO) throws InsertandoRubroException, ExisteRubroException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarRubro(rubroVO);
	}
	
	
	/**
	 * Actualiza los datos de un rubro
	 */
	public void actualizarRubro(RubroVO rubroVO) throws ConexionException, NoExisteRubroException, ModificandoRubroException, ExisteRubroException, InicializandoException {
		FachadaDD.getInstance().actualizarRubro(rubroVO);
	}

}
