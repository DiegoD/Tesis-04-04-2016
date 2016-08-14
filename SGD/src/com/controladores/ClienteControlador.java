package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.ExisteDocumentoClienteException;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.clientes.VerificandoClienteException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
import com.valueObject.DocumDGIVO;
import com.valueObject.GrupoVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.UsuarioVO;
import com.valueObject.cliente.ClienteVO;

public class ClienteControlador {

	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoClientesException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<ClienteVO> getClientesTodos(UsuarioPermisosVO permisos) throws ObteniendoClientesException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getClientesTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoClientesException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<ClienteVO> getClientesAvtivos(UsuarioPermisosVO permisos) throws ObteniendoClientesException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getClientesActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un usuario dado su VO
	 * @throws InicializandoException 
	 * @throws ExisteClienteExeption 
	 * @throws ConexionException 
	 * @throws InsertandoClienteException 
	 * @throws ExisteDocumentoClienteException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public int insertarCliente(ClienteVO clienteVO, UsuarioPermisosVO permisos) throws InsertandoClienteException, ConexionException, ExisteClienteExeption, InicializandoException, ExisteDocumentoClienteException, ObteniendoPermisosException, NoTienePermisosException 
	{
		int codigo = 0;
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			codigo = Fachada.getInstance().insertarCliente(clienteVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
		return codigo;
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 * @throws InicializandoException 
	 * @throws ModificandoClienteException 
	 * @throws ConexionException 
	 * @throws ExisteDocumentoClienteException 
	 * @throws VerificandoClienteException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void modificarCliente(ClienteVO clienteVO, UsuarioPermisosVO permisos) throws ConexionException, ModificandoClienteException, InicializandoException, VerificandoClienteException, ExisteDocumentoClienteException, ObteniendoPermisosException, NoTienePermisosException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().editarCliente(clienteVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<DocumDGIVO> obtnerDocumentosDgi() throws ObteniendoDocumentosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getDocumentosDGI();
	}
	
}
