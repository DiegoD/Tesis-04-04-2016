package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.MemberClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.clientes.VerificandoClienteException;
import com.logica.Cliente;
import com.logica.Documento;

public interface IDAOClientes {

	public ArrayList<Cliente> getClientesTodos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException ;
	public ArrayList<Cliente> getClientesActivos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException;
	public boolean memberCliente(int codCliente, String codEmp, Connection con) throws ExisteClienteExeption, ConexionException;
	public int insertarCliente(Cliente cliente, String empresa, Connection con) throws MemberClienteException, ConexionException;
	public void modificarCliente(Cliente cliente, String empresa, Connection con) throws ModificandoClienteException;
	public boolean memberClienteDocumentoEditar(Documento doc, int codCliente, String codEmp, Connection con) throws VerificandoClienteException, ConexionException;
	public boolean memberClienteDocumentoNuevo(Documento doc, String codEmp, Connection con) throws VerificandoClienteException, ConexionException;
}
