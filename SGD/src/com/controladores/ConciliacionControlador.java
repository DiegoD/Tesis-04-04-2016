package com.controladores;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.Conciliaciones.EliminandoConcialiacionException;
import com.excepciones.Conciliaciones.ExisteConciliacionException;
import com.excepciones.Conciliaciones.InsertandoConciliacionException;
import com.excepciones.Conciliaciones.ModificandoConciliacionException;
import com.excepciones.Conciliaciones.NoExisteConciliacionException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Depositos.EliminandoDepositoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.excepciones.Depositos.ModificandoDepositoException;
import com.excepciones.Depositos.NoExisteDepositoException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.NoExisteIngresoCobroException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Conciliaciones.ConciliacionDetalleVO;
import com.valueObject.Conciliaciones.ConciliacionVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;

public class ConciliacionControlador {
	
	/**
	 * Obtiene array list de VO de todos las concliaciones
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<ConciliacionVO> getConciliaciones(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoConciliacionException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getConciliacionesTodos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<ConciliacionDetalleVO> getMovimientosBanco(UsuarioPermisosVO permisos, String codBco, String codCtaBco) throws ObteniendoConciliacionException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMovimientosBanco(permisos.getCodEmp(), codBco, codCtaBco);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<ConciliacionDetalleVO> getMovimientosCaja(UsuarioPermisosVO permisos, String codMoneda) throws ObteniendoConciliacionException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMovimientosCajaMoneda(permisos.getCodEmp(), codMoneda);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Elimina una conciliación 
	 */
	public void eliminarConciliacion(ConciliacionVO conciliacionVO, UsuarioPermisosVO permisos) throws InsertandoConciliacionException, ConexionException, ExisteConciliacionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteConciliacionException, EliminandoConcialiacionException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().eliminarConciliacion(conciliacionVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de una conciliacion
	 */
	public void modificarConciliacion(ConciliacionVO conciliacionVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoConciliacionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteConciliacionException, NoExisteConciliacionException, ModificandoConciliacionException, ExisteConciliacionException, NoExisteConciliacionException, InsertandoConciliacionException, EliminandoConcialiacionException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().modificarConciliacion(conciliacionVO, permisos.getCodEmp());
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

	public ArrayList<CtaBcoVO> getCtaBcos(UsuarioPermisosVO permisos, String codBco) throws  ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getCtaBcoActivosxBco(permisos.getCodEmp(), codBco);
		else
			throw new NoTienePermisosException();
	}
	
	
//	public ArrayList<DepositoDetalleVO> getChequesBanco(UsuarioPermisosVO permisos, String codMoneda) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
//		
//		/*Primero se verifican los permisos*/
//		if(Fachada.getInstance().permisoEnFormulario(permisos))
//			return FachadaDD.getInstance().getChequesBanco(permisos.getCodEmp(), codMoneda);
//		else
//			throw new NoTienePermisosException();
//	}
//	
	public NumeradoresVO insertarConciliacion(UsuarioPermisosVO permisos, ConciliacionVO conciliacion) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoConciliacionException, ObteniendoCuentasBcoException, ObteniendoBancosException, InsertandoConciliacionException, ExisteConciliacionException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().insertarConciliacion(conciliacion, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public CotizacionVO getCotizacion(UsuarioPermisosVO permisoAux, Date fecha, String codMonedaSeleccionada) throws ObteniendoCotizacionesException, ConexionException, ObteniendoPermisosException, InicializandoException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisoAux))
			return FachadaDD.getInstance().getCotizacion(permisoAux.getCodEmp(), fecha, codMonedaSeleccionada);
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
	
	public double getSaldoConciliadoMoneda(UsuarioPermisosVO permisos, String codMoneda) throws ObteniendoConciliacionException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getSaldoConciliadoMoneda(permisos.getCodEmp(), codMoneda);
		else
			throw new NoTienePermisosException();
	}
	
	public double getSaldoConciliadoCuentaBAnco(UsuarioPermisosVO permisos, String codBanco, String codCuenta) throws ObteniendoConciliacionException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getSaldoConciliadoCuentaBanco(permisos.getCodEmp(), codBanco, codCuenta);
		else
			throw new NoTienePermisosException();
	}
}
