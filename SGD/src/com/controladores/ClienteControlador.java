package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Fachada;
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
	 */
	public void insertarCliente(ClienteVO clienteVO, String empresa) throws InsertandoClienteException, ConexionException, ExisteClienteExeption, InicializandoException 
	{
		Fachada.getInstance().insertarCliente(clienteVO, empresa);
	}
	
	/**
	 * Modifica los datos de un usuario dado el VO con las modificaciones
	 * @throws InicializandoException 
	 * @throws ModificandoClienteException 
	 * @throws ConexionException 
	 */
	public void modificarUsuario(ClienteVO clienteVO, String empresa) throws ConexionException, ModificandoClienteException, InicializandoException 
	{
		Fachada.getInstance().editarCliente(clienteVO, empresa);
	}
	

	
}
