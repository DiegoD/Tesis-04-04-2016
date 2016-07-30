package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.MemberClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Cliente;
import com.logica.Documento;
import com.logica.Formulario;
import com.logica.Grupo;
import com.logica.Usuario;
import com.valueObject.EmpLoginVO;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;

public class DAOClientes implements IDAOClientes{
	
	
	//private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	

	/**
	 * Nos retorna una lista con todos los clientes del sistema
	 */
	public ArrayList<Cliente> getClientesTodos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException {
		
		ArrayList<Cliente> lstClientes = new ArrayList<Cliente>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getClientesTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Cliente aux;
			while(rs.next ()) {
				
							
				aux = new Cliente();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setRazonSocial(rs.getString("razon_social"));
				aux.setTel(rs.getString("tel"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"),rs.getString("nomDoc"), rs.getString("nro_dgi")));
				aux.setDireccion(rs.getString("direccion"));
				aux.setMail(rs.getString("mail"));
				aux.setActivo(rs.getBoolean("activo"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				
				lstClientes.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoClientesException();
			
		}
    	
    	return lstClientes;
	}
	
	/**
	 * Nos retorna una lista con todos los clientes activos del sistema
	 */
	public ArrayList<Cliente> getClientesActivos(Connection con, String codEmp) throws ObteniendoClientesException, ConexionException {
		
		ArrayList<Cliente> lstClientes = new ArrayList<Cliente>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getClientesActivos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	
			rs = pstmt1.executeQuery();
			
			Cliente aux;
			while(rs.next ()) {
				
				Timestamp t = rs.getTimestamp(7);
				
				aux = new Cliente();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setRazonSocial(rs.getString("razon_social"));
				aux.setTel(rs.getString("tel"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"),rs.getString("nomDoc"), rs.getString("nro_dgi")));
				aux.setDireccion(rs.getString("direccion"));
				aux.setMail(rs.getString("mail"));
				aux.setActivo(rs.getBoolean("activo"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				
				
				lstClientes.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoClientesException();
			
		}
    	
    	return lstClientes;
	}

	/**
	 * Dado el codigo del cliente, valida si existe
	 */
	public boolean memberCliente(int codCliente, String codEmp, Connection con) throws ExisteClienteExeption, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.getMemberCliente();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codCliente);
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteClienteExeption();
		}
	}

	/**
	 * Inserta un cliente en la base
	 * 
	 */
	public void insertarCliente(Cliente cliente, String codEmp, Connection con) throws MemberClienteException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.getInsertCliente();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
					
			pstmt1.setInt(1, cliente.getCodigo());
			pstmt1.setString(2, cliente.getNombre());
			pstmt1.setString(3, cliente.getRazonSocial());
			pstmt1.setString(4, cliente.getTel());
			pstmt1.setString(5, cliente.getDocumento().getNumero());
			pstmt1.setString(6, cliente.getDocumento().getCodigo());
			pstmt1.setString(7, cliente.getDireccion());
			pstmt1.setString(8, cliente.getMail());
			pstmt1.setBoolean(9, cliente.isActivo());
			
			pstmt1.setString(10, cliente.getUsuarioMod());
			pstmt1.setString(11, cliente.getOperacion());
			pstmt1.setTimestamp(12, cliente.getFechaMod());
			pstmt1.setString(13, codEmp);
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new MemberClienteException();
		} 
		
	}
	
	
	public void modificarCliente(Cliente cliente, String codEmp, Connection con) throws ModificandoClienteException{
		
		Consultas consultas = new Consultas();
		String update = consultas.getActualizarCliente();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
			pstmt1.setString(1, cliente.getNombre());
			pstmt1.setString(2, cliente.getRazonSocial());
			pstmt1.setString(3, cliente.getTel());
			pstmt1.setString(4, cliente.getDocumento().getNumero());
			pstmt1.setString(5, cliente.getDocumento().getCodigo());
			pstmt1.setString(6, cliente.getDireccion());
			pstmt1.setString(7, cliente.getMail());
			pstmt1.setBoolean(8, cliente.isActivo());
			pstmt1.setString(9, cliente.getUsuarioMod());
			pstmt1.setString(10, cliente.getOperacion());
			pstmt1.setTimestamp(11, cliente.getFechaMod());
			pstmt1.setInt(11, cliente.getCodigo());
			pstmt1.setString(11, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoClienteException();
		}
	}
	
	

}
