package com.controladores;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ExisteBancoException;
import com.excepciones.Bancos.InsertandoBancoException;
import com.excepciones.Bancos.ModificandoBancoException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Egresos.*;
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
import com.valueObject.Gasto.GastoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;

public class IngresoEgresoControlador {

	/**
	 * Obtiene los cobros del sistema
	 */
	public ArrayList<IngresoCobroVO> getIngresoEgresoTodos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoEgresoCobroException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getEgresoCobroTodos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un cobro 
	 */
	public void insertarIngresoEgreso(IngresoCobroVO ingVO, UsuarioPermisosVO permisos) throws InsertandoEgresoCobroException, ConexionException, ExisteEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarEgresoCobro(ingVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Elimina un cobro 
	 * @throws NoExisteEgresoCobroException 
	 */
	public void eliminarIngresoEgreso(IngresoCobroVO ingVO, UsuarioPermisosVO permisos) throws InsertandoEgresoCobroException, ConexionException, ExisteEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteEgresoCobroException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().eliminarEgresoCobro(ingVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Anula un cobro 
	 * @throws NoExisteEgresoCobroException 
	 */
	public void anularIngresoEgreso(IngresoCobroVO ingVO, UsuarioPermisosVO permisos, boolean anula) throws InsertandoEgresoCobroException, ConexionException, ExisteEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteEgresoCobroException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().anularEgresoCobro(ingVO, permisos.getCodEmp(), anula);
		else
			throw new NoTienePermisosException();
		
	}
	/**
	 * Modifica los datos de un cobro
	 * @throws NoExisteNotaCreditoException 
	 */
	public void modificarIngresoEgreso(IngresoCobroVO ingVO, IngresoCobroVO copiaVO, UsuarioPermisosVO permisos, ArrayList<IngresoCobroDetalleVO> lstDetalleSinMedioDePago) throws ConexionException, ModificandoEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteEgresoCobroException, NoExisteEgresoCobroException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().modificarEgresoCobro(ingVO, copiaVO, lstDetalleSinMedioDePago);
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
	
	/* NO VAMOS A OBTENER LOS GASTOS CON SALDO, LUEGO ELIMINAR*/
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
	
	
	public ArrayList<GastoVO> getGastosSinMedioDePago(UsuarioPermisosVO permisos, String cod_tit) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosSinMedioDePago(permisos.getCodEmp(), cod_tit);
		else
			throw new NoTienePermisosException();
	}


	
	
}
