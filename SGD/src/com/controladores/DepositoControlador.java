package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
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
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
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
	public ArrayList<DepositoVO> getDepositos(UsuarioPermisosVO permisos) throws ObteniendoDepositoException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getDepositos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo deposito
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarDeposito(DepositoVO depositoVO, UsuarioPermisosVO permisos) throws InsertandoDepositoException, ExisteDepositoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarDeposito(depositoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Elimina un deposito 
	 * @throws NoExisteDepositoException 
	 * @throws ModificandoDepositoException 
	 * @throws EliminandoDepositoException 
	 */
	public void eliminarDeposito(DepositoVO depositoVO, UsuarioPermisosVO permisos) throws InsertandoDepositoException, ConexionException, ExisteDepositoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NoExisteDepositoException, EliminandoDepositoException 
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
	
	public ArrayList<DepositoDetalleVO> getChequesBanco(UsuarioPermisosVO permisos, String codBco, String codCtaBco) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getChequesBanco(permisos.getCodEmp(), codBco, codCtaBco);
		else
			throw new NoTienePermisosException();
	}
	
	public void depositarCheques(UsuarioPermisosVO permisos, ArrayList<DepositoDetalleVO> cheques) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().depositarCheques(permisos.getCodEmp(), cheques);
		else
			throw new NoTienePermisosException();
	}
}
