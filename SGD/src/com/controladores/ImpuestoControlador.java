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
import com.valueObject.UsuarioPermisosVO;

public class ImpuestoControlador {
	
	public ImpuestoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los impuestos
	 * @throws ObteniendoPermisosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<ImpuestoVO> getImpuestos(UsuarioPermisosVO permisos) throws ObteniendoImpuestosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getImpuestos();
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo impuesto
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarImpuesto(ImpuestoVO impuestoVO, UsuarioPermisosVO permisos) throws InsertandoImpuestoException, ExisteImpuestoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarImpuesto(impuestoVO);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un impuesto
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void actualizarImpuesto(ImpuestoVO impuestoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteImpuestoException, ModificandoImpuestoException, ExisteImpuestoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarImpuesto(impuestoVO);
		else
			throw new NoTienePermisosException();
	}
	
	
}
