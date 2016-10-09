package com.controladores;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.cliente.ClienteVO;
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
	public int insertarProceso(ProcesoVO procesoVO, UsuarioPermisosVO permisos) throws IngresandoProcesoException, ExisteProcesoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().insertarProceso(procesoVO, permisos.getCodEmp());
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
	
	/**
	 * Elimina un proceso
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws EliminandoProcesoException 
	 */
	public void eliminarProceso(int codigo, UsuarioPermisosVO permisos) throws ConexionException, NoExisteProcesoException, ExisteProcesoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, EliminandoProcesoException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().eliminarProceso(codigo, permisos.getCodEmp());
			
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Trae los clientes activos
	 */
	public ArrayList<ClienteVO> getClientes(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getClientesActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<MonedaVO> getMonedas(UsuarioPermisosVO permisos) throws ObteniendoMonedaException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMonedasActivas(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<DocumentoAduaneroVO> getDocumentos(UsuarioPermisosVO permisos) throws ObteniendoDocumentosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getDocumentos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public CotizacionVO getCotizacion(UsuarioPermisosVO permisos, Date fecha, String codMoneda) throws ObteniendoCotizacionesException, ConexionException, ObteniendoPermisosException, InicializandoException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos)){
			return FachadaDD.getInstance().getCotizacion(permisos.getCodEmp(), fecha, codMoneda);
		}
		else
			throw new NoTienePermisosException();
	}
}
