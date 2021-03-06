package com.controladores;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Factura.ObteniendoFacturasException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;
import com.valueObject.proceso.SaldoProcesoVO;

public class ResumenProcesoControlador {
	
	public ResumenProcesoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los proceso
	 * 
	 */
	public ArrayList<ProcesoVO> getProcesos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin) throws ObteniendoProcesosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getProcesos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo proceso
	 * 
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
	 * 
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
	 *  
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
	
	/**
	 * Obtenemos los gastos no cobrables para el proceso
	 * 
	 */
	public ArrayList<GastoVO> getGastosNoCobrablesxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosNoCobrablesxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtenemos los gastos cobrables para el proceso
	 * 
	 */
	public ArrayList<GastoVO> getGastosCobrablesxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosCobrablesxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtenemos los gastos a pagar para el proceso
	 * 
	 */
	public ArrayList<GastoVO> getGastosAPagarxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosAPagarxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtenemos los gastos anulados para el proceso
	 *
	 */
	public ArrayList<GastoVO> getGastosAnuladosxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosAnuladosxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtenemos los gastos anulados para el proceso
	 * 
	 *
	 */
	public ArrayList<SaldoProcesoVO> getSaldosSinAdjuxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoSaldosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getSaldosSinAdjuxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Obtenemos las facturas para el proceso
	 * 
	 *
	 */
	public ArrayList<FacturaVO> getFacturasxProceso(UsuarioPermisosVO permisos, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoFacturasException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFacturaxProceso(permisos.getCodEmp(), codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<GastoVO> getGastosConSaldoProceso(UsuarioPermisosVO permisos, String cod_tit, int codProceso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoGastosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastosConSaldoCobrableProceso(permisos.getCodEmp(), cod_tit, codProceso);
		else
			throw new NoTienePermisosException();
	}
	
	public void adjudicarSaldo(UsuarioPermisosVO permisos, ArrayList<GastoVO> lstGastos, SaldoProcesoVO saldo) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ModificandoSaldoException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			
			FachadaDD.getInstance().adjudicarSaldoProceoGasto(permisos.getCodEmp(), lstGastos, saldo);
		else
			throw new NoTienePermisosException();
	}
}
