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
import com.excepciones.Factura.ObteniendoFacturasException;
import com.excepciones.NotaCredito.*;
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
import com.valueObject.Docum.NotaCreditoVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;

public class NotaCreditoControlador {

	/**
	 * Obtiene las nc del sistema
	 */
	public ArrayList<NotaCreditoVO> getNotaCreditoTodos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoNotaCreditoException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getNotaCreditoTodos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta una nc 
	 */
	public void insertarNotaCredito(NotaCreditoVO ncVO, UsuarioPermisosVO permisos) throws InsertandoNotaCreditoException, ConexionException, ExisteNotaCreditoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarNotaCredito(ncVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Elimina NC
	 */
	public void eliminarNotaCredito(NotaCreditoVO ncVO, UsuarioPermisosVO permisos) throws EliminandoNotaCreditoException, ConexionException, ExisteNotaCreditoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteNotaCreditoException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().eliminarNotaCredito(ncVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de NC
	 * 
	 */
	public void modificarNotaCredito(NotaCreditoVO ncVO, NotaCreditoVO copiaVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoNotaCreditoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteNotaCreditoException, NoExisteNotaCreditoException, SQLException, InsertandoNotaCreditoException, EliminandoNotaCreditoException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().modificarNotaCredito(ncVO, copiaVO);
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


	
	
}
