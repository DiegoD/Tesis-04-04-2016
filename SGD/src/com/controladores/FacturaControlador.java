package com.controladores;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Factura.*;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;

public class FacturaControlador {

	/**
	 * Obtiene los cobros del sistema
	 */
	public ArrayList<FacturaVO> getFacturasTodos(UsuarioPermisosVO permisos) throws ObteniendoFacturasException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFacturasTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un cobro 
	 */
	public void insertarFactura(FacturaVO factVO, UsuarioPermisosVO permisos, boolean nuevo) throws InsertandoFacturaException, ConexionException, ExisteFacturaException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarFactura(factVO, permisos.getCodEmp(), nuevo);
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Elimina un cobro 
	 * @throws NoExisteEgresoCobroException 
	 */
	public void eliminarFactura(FacturaVO factVO, UsuarioPermisosVO permisos) throws EliminandoFacturaException, ConexionException, ExisteFacturaException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteFacturaException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().eliminarFactura(factVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de un cobro
	 * @throws NoExisteFacturaException 
	 */
	public void modificarFactura(FacturaVO factVO, FacturaVO copiaVO, UsuarioPermisosVO permisos, boolean nuevo) throws ConexionException, ModificandoFacturaException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteFacturaException, NoExisteFacturaException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().modificarFactura(factVO, copiaVO);
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
	

	public ArrayList<GastoVO> getGastosConSaldo(UsuarioPermisosVO permisos, String cod_tit, int codProceso, String codMoneda) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosFacturablesxProcesoConSaldoxMoneda(permisos.getCodEmp(), cod_tit, codProceso, codMoneda);
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
	
	/**
	 * Trae los titulares activos
	 * @throws ObteniendoTitularesException 
	 */
	public ArrayList<TitularVO> getTitulares(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException, ObteniendoTitularesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getTitularesActivosFuncionarios(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Trae los clientes activos
	 * @throws NoTienePermisosException 
	 * @throws InicializandoException 
	 * @throws ObteniendoPermisosException 
	 */
	public CotizacionVO getCotizacion(UsuarioPermisosVO permisoAux, Date fecha, String codMonedaSeleccionada) throws ObteniendoCotizacionesException, ConexionException, ObteniendoPermisosException, InicializandoException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisoAux))
			return FachadaDD.getInstance().getCotizacion(permisoAux.getCodEmp(), fecha, codMonedaSeleccionada);
		else
			throw new NoTienePermisosException();
	}

	public ArrayList<ProcesoVO> getProcesosCliente(UsuarioPermisosVO permisos, String cod_cliente) throws ObteniendoProcesosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getProcesosCliente(permisos.getCodEmp(), cod_cliente);
		else
			throw new NoTienePermisosException();
	}
	


	
	
}
