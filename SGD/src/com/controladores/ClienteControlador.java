package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
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
import com.valueObject.UsuarioVO;
import com.valueObject.cliente.ClienteVO;

public class ClienteControlador {

	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoClientesException 
	 */
	public ArrayList<ClienteVO> getClientesTodos(String codEmp) throws ObteniendoClientesException, ConexionException, InicializandoException  
	{
		return Fachada.getInstance().getClientesTodos(codEmp);
    }
	
	/**
	 * Obtiene todos los clientes del sistema
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoClientesException 
	 */
	public ArrayList<ClienteVO> getClientesAvtivos(String codEmp) throws ObteniendoClientesException, ConexionException, InicializandoException  
	{
		return Fachada.getInstance().getClientesActivos(codEmp);
    }
	
	
	/**
	 * Inserta un usuario dado su VO
	 * @throws InicializandoException 
	 * @throws ExisteClienteExeption 
	 * @throws ConexionException 
	 * @throws InsertandoClienteException 
	 * @throws ExisteDocumentoClienteException 
	 */
	public int insertarCliente(ClienteVO clienteVO, String empresa) throws InsertandoClienteException, ConexionException, ExisteClienteExeption, InicializandoException, ExisteDocumentoClienteException 
	{
		int codigo = 0;
		
		codigo = Fachada.getInstance().insertarCliente(clienteVO, empresa);
		
		return codigo;
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 * @throws InicializandoException 
	 * @throws ModificandoClienteException 
	 * @throws ConexionException 
	 * @throws ExisteDocumentoClienteException 
	 * @throws VerificandoClienteException 
	 */
	public void modificarCliente(ClienteVO clienteVO, String empresa) throws ConexionException, ModificandoClienteException, InicializandoException, VerificandoClienteException, ExisteDocumentoClienteException 
	{
		Fachada.getInstance().editarCliente(clienteVO, empresa);
	}
	
	public ArrayList<DocumDGIVO> obtnerDocumentosDgi() throws ObteniendoDocumentosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getDocumentosDGI();
	}
	
}
