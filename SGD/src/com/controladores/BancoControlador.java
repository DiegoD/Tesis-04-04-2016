package com.controladores;

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
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.banco.BancoVO;

public class BancoControlador {

	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoBancosException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws ObteniendoCuentasBcoException 
	 */
	public ArrayList<BancoVO> getBancosTodos(UsuarioPermisosVO permisos) throws ObteniendoBancosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getBancosTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoBancosException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws ObteniendoCuentasBcoException 
	 */
	public ArrayList<BancoVO> getBancosAvtivos(UsuarioPermisosVO permisos) throws ObteniendoBancosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoBancosException, ObteniendoCuentasBcoException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getBancosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un usuario dado su VO
	 * @throws InicializandoException 
	 * @throws ExisteBancoExeption 
	 * @throws ConexionException 
	 * @throws InsertandoBancoException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarBanco(BancoVO bancoVO, UsuarioPermisosVO permisos) throws InsertandoBancoException, ConexionException, ExisteBancoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().insertarBanco(bancoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 * @throws InicializandoException 
	 * @throws ModificandoBancoException 
	 * @throws ConexionException 
	 * @throws VerificandoBancoException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws ModificandoCuentaBcoException 
	 * @throws ExisteBancoException 
	 */
	public void modificarBanco(BancoVO bancoVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoBancoException, InicializandoException, VerificandoBancosException, ObteniendoPermisosException, NoTienePermisosException, ExisteBancoException, ModificandoCuentaBcoException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().editarBanco(bancoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	
	public ArrayList<MonedaVO> getMonedas(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoMonedaException {
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMonedas(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
}
