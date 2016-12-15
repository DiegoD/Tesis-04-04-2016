package com.controladores;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Depositos.EliminandoDepositoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.excepciones.Depositos.ModificandoDepositoException;
import com.excepciones.Depositos.NoExisteDepositoException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.NoExisteIngresoCobroException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;

public class DepositoControlador {

	
	/**
	 * Obtiene array list de VO de todos los depositos
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoDepositoException 
	 */
	public ArrayList<DepositoVO> getDepositos(UsuarioPermisosVO permisos, Timestamp inicio, Timestamp fin ) throws ObteniendoDepositoException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getDepositos(permisos.getCodEmp(), inicio, fin);
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Elimina un deposito 
	 * @throws NoExisteDepositoException 
	 * @throws ModificandoDepositoException 
	 * @throws EliminandoDepositoException 
	 * @throws ExisteSaldoException 
	 * @throws ModificandoSaldoException 
	 * @throws EliminandoSaldoCuetaException 
	 */
	public void eliminarDeposito(DepositoVO depositoVO, UsuarioPermisosVO permisos) throws InsertandoDepositoException, ConexionException, ExisteDepositoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteDepositoException, EliminandoDepositoException, EliminandoSaldoCuetaException, ModificandoSaldoException, ExisteSaldoException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().eliminarDeposito(depositoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de un deposito
	 * @throws EliminandoDepositoException 
	 * @throws InsertandoDepositoException 
	 * @throws ExisteDepositoException 
	 * @throws ModificandoDepositoException 
	 * @throws NoExisteDepositoException 
	 */
	public void modificarDeposito(DepositoVO depositoVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoIngresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteIngresoCobroException, NoExisteIngresoCobroException, ModificandoDepositoException, ExisteDepositoException, NoExisteDepositoException, InsertandoDepositoException, EliminandoDepositoException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().modificarDeposito(depositoVO, permisos.getCodEmp());
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
	
	public ArrayList<TitularVO> getTitulares(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException, ObteniendoTitularesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getTitularesActivosFuncionarios(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<DepositoDetalleVO> getChequesBanco(UsuarioPermisosVO permisos, String codMoneda) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getChequesBanco(permisos.getCodEmp(), codMoneda);
		else
			throw new NoTienePermisosException();
	}
	
	public Integer depositarCheques(UsuarioPermisosVO permisos, DepositoVO cheques) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException, InsertandoDepositoException, ExisteDepositoException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().insertarDeposito(cheques, permisos.getCodEmp());
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
}
