package com.controladores;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Factura.ExisteFacturaException;
import com.excepciones.Factura.NoExisteFacturaException;
import com.excepciones.Factura.ObteniendoFacturasException;
import com.excepciones.Factura.ObteniendoSaldoException;
import com.excepciones.Recibo.*;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Docum.ReciboVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;

public class ReciboControlador {

	/**
	 * Obtiene los recibos del sistema
	 */
	public ArrayList<ReciboVO> getReciboTodos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoReciboException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getRecibosTodos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un recibo 
	 */
	public void insertarRecibo(ReciboVO recVO, UsuarioPermisosVO permisos) throws InsertandoReciboException, ConexionException, ExisteReciboException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarRecibo(recVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Elimina un recibo 
	 */
	public void eliminarRecibo(ReciboVO recVO, UsuarioPermisosVO permisos) throws EliminandoReciboException, ConexionException, ExisteReciboException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteReciboException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().eliminarRecibo(recVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de un recibo
	 * 
	 */
	public void modificarRecibo(ReciboVO recVO, ReciboVO copiaVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoReciboException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteReciboException, NoExisteReciboException, SQLException, InsertandoReciboException, EliminandoReciboException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().modificarRecibo(recVO, copiaVO);
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
	

	public ArrayList<FacturaVO> getFacturasConSaldo(UsuarioPermisosVO permisos, String codMoneda, String codTit) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoFacturasException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFacturaConSaldoxMoneda(permisos.getCodEmp(), codMoneda, codTit);
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
	 */
	public CotizacionVO getCotizacion(UsuarioPermisosVO permisoAux, Date fecha, String codMonedaSeleccionada) throws ObteniendoCotizacionesException, ConexionException, ObteniendoPermisosException, InicializandoException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisoAux))
			return FachadaDD.getInstance().getCotizacion(permisoAux.getCodEmp(), fecha, codMonedaSeleccionada);
		else
			throw new NoTienePermisosException();
	}


	public ArrayList<CtaBcoVO> getCtaBcos(UsuarioPermisosVO permisos, String codBco) throws  ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getCtaBcoActivosxBco(permisos.getCodEmp(), codBco);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<BancoVO> getBcos(UsuarioPermisosVO permisos) throws  ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoBancosException, ObteniendoCuentasBcoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getBancosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}

	
	/***
	 *  Nos retorna el saldo de factura seleccionada
	 *  para poder controlar el saldo de la misma a la hora
	 *  de modificar el importe en la grilla, para no permitir
	 *  ingresar un importe mayor al saldo
	 */
	public double getSaldoFactura(UsuarioPermisosVO permisos, int nroDocum, String serie, String codigo) throws  ObteniendoSaldoException, ExisteFacturaException, NoExisteFacturaException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getSaldoFactura(nroDocum, serie, codigo, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	
}
