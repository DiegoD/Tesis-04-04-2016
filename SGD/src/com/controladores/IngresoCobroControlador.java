package com.controladores;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ExisteBancoException;
import com.excepciones.Bancos.InsertandoBancoException;
import com.excepciones.Bancos.ModificandoBancoException;
import com.excepciones.Bancos.ModificandoCuentaBcoException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Bancos.VerificandoBancosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.NoExisteIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;

public class IngresoCobroControlador {

	/**
	 * Obtiene los cobros del sistema
	 */
	public ArrayList<IngresoCobroVO> getIngresoCobroTodos(UsuarioPermisosVO permisos) throws ObteniendoIngresoCobroException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getIngresoCobroTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un cobro 
	 */
	public void insertarIngresoCobro(IngresoCobroVO ingVO, UsuarioPermisosVO permisos) throws InsertandoIngresoCobroException, ConexionException, ExisteIngresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarIngresoCobro(ingVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de un cobro
	 * @throws NoExisteIngresoCobroException 
	 */
	public void modificarIngresoCobro(IngresoCobroVO ingVO, IngresoCobroVO copiaVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoIngresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteIngresoCobroException, NoExisteIngresoCobroException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().modificarIngresoCobro(ingVO, copiaVO);
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
	
	
	public ArrayList<GastoVO> getGastosConSaldo(UsuarioPermisosVO permisos, String cod_tit) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosConSaldo(permisos.getCodEmp(), cod_tit);
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
