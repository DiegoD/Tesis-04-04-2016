package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.MemberClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Cliente;

public interface IDAOClientes {

	public ArrayList<Cliente> getClientesTodos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException ;
	public ArrayList<Cliente> getClientesActivos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException;
	public boolean memberCliente(int codCliente, String codEmp, Connection con) throws ExisteClienteExeption, ConexionException;
	public void insertarCliente(Cliente cliente, String empresa, Connection con) throws MemberClienteException, ConexionException;
	public void modificarCliente(Cliente cliente, String empresa, Connection con) throws ModificandoClienteException;
}
