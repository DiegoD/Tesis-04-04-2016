package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.MemberClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.funcionarios.ExisteFuncionarioException;
import com.excepciones.funcionarios.InsertendoFuncionarioException;
import com.excepciones.funcionarios.MemberFuncionarioException;
import com.excepciones.funcionarios.ModificandoFuncionarioException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.logica.Cliente;
import com.logica.Documento;
import com.logica.Funcionario;
import com.mysql.jdbc.Statement;

public class DAOFuncionarios implements IDAOFuncionarios{

	//private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	

	/**
	 * Nos retorna una lista con todos los funcionarios del sistema
	 */
	public ArrayList<Funcionario> getFuncionariosTodos(Connection con, String codEmp) throws ObteniendoFuncionariosException, ConexionException {
		
		ArrayList<Funcionario> lstFuncionarios = new ArrayList<Funcionario>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getFuncionariosTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Funcionario aux;
			while(rs.next ()) {
				
							
				aux = new Funcionario();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setTel(rs.getString("tel"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"),rs.getString("nomDoc"), rs.getString("nro_dgi")));
				aux.setDireccion(rs.getString("direccion"));
				aux.setMail(rs.getString("mail"));
				aux.setActivo(rs.getBoolean("activo"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				String s = rs.getString("operacion");
				String r = rs.getString("usuario_mod");
				
				lstFuncionarios.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoFuncionariosException();
			
		}
    	
    	return lstFuncionarios;
	}
	
	/**
	 * Nos retorna una lista con todos los funcionarios activos del sistema
	 */
	public ArrayList<Funcionario> getFuncionariosActivos(Connection con, String codEmp) throws ObteniendoFuncionariosException, ConexionException {
		
		ArrayList<Funcionario> lstFuncionarios = new ArrayList<Funcionario>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getFuncionariosActivos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	
			rs = pstmt1.executeQuery();
			
			Funcionario aux;
			while(rs.next ()) {
				
				Timestamp t = rs.getTimestamp(7);
				
				aux = new Funcionario();
				
				aux.setCodigo(rs.getInt("cod_tit"));
				aux.setNombre(rs.getString("nom_tit"));
				aux.setTel(rs.getString("tel"));
				aux.setDocumento(new Documento(rs.getString("cod_docdgi"),rs.getString("nomDoc"), rs.getString("nro_dgi")));
				aux.setDireccion(rs.getString("direccion"));
				aux.setMail(rs.getString("mail"));
				aux.setActivo(rs.getBoolean("activo"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				
				
				lstFuncionarios.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoFuncionariosException();
			
		}
    	
    	return lstFuncionarios;
	}

	/**
	 * Dado el codigo del funcionario, valida si existe
	 */
	public boolean memberFuncionario(int codFuncionario, String codEmp, Connection con) throws ExisteFuncionarioException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			Consultas consultas = new Consultas();
			String query = consultas.getMemberFuncionario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codFuncionario);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteFuncionarioException();
		}
	}
	
	/**
	 * Dado el codigo del funcionario, retorna true si existe
	 * otro funcionario con el mismo documento
	 */
	public boolean memberFuncionarioDocumentoNuevo(Documento doc, String codEmp, Connection con) throws ExisteFuncionarioException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			Consultas consultas = new Consultas();
			String query = consultas.getMemberFuncionarioDocumentoNuevo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codEmp);
			pstmt1.setString(2, doc.getCodigo());
			pstmt1.setString(3, doc.getNumero());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteFuncionarioException();
		}
	}
	
	/**
	 * Dado el codigo del funcionario, retorna true si existe
	 * otro funcionario con el mismo documento
	 */
	public boolean memberFuncionarioDocumentoEditar(Documento doc, int codFuncionario, String codEmp, Connection con) throws ExisteFuncionarioException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			Consultas consultas = new Consultas();
			String query = consultas.getMemberFuncionarioDocumentoEditar();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codFuncionario);
			pstmt1.setString(2, codEmp);
			pstmt1.setString(3, doc.getCodigo());
			pstmt1.setString(4, doc.getNumero());
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteFuncionarioException();
		}
	}

	/**
	 * Inserta un funcionario en la base, nos retrorna un entero que es el codigo del cliente insertado
	 *  PRECONDICION: NO EXISTE OTRO FUNCIONARIO CON EL MISMO DOCUMENTO
	 * 
	 */
	public int insertarFuncionario(Funcionario funcionario, String empresa, Connection con) throws InsertendoFuncionarioException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.getInsertFuncionario();
    	
    	
    	PreparedStatement pstmt1;
    	int codigo =0;	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			//pstmt1.setInt(1, cliente.getCodigo());
			pstmt1.setString(1, funcionario.getNombre());
			pstmt1.setString(2, empresa);
			
			pstmt1.setString(3, funcionario.getTel());
			pstmt1.setString(4, funcionario.getDocumento().getNumero());
			pstmt1.setString(5, funcionario.getDocumento().getCodigo());
			pstmt1.setString(6, funcionario.getDireccion());
			pstmt1.setString(7, funcionario.getMail());
			pstmt1.setBoolean(8, funcionario.isActivo());
			
			pstmt1.setString(9, funcionario.getUsuarioMod());
			pstmt1.setString(10, funcionario.getOperacion());
			pstmt1.setTimestamp(11, funcionario.getFechaMod());
			pstmt1.setInt(12, funcionario.getCodigo());
			
			//codigo = pstmt1.executeUpdate(Statement.RETURN_GENERATED_KEYS);
			
			pstmt1.executeUpdate ();
			
			/*Obtenemos el codigo del cliente insertado*/
			ResultSet rs = pstmt1.getGeneratedKeys();
			if (rs.next()){
			    codigo=funcionario.getCodigo();
			}
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertendoFuncionarioException();
		} 
		
    	return codigo;
	}
	
	
	public void modificarFuncionario(Funcionario funcionario, String empresa, Connection con) throws ModificandoFuncionarioException{
		
		Consultas consultas = new Consultas();
		String update = consultas.getActualizarFuncionario();
		PreparedStatement pstmt1;
		
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
			pstmt1.setString(1, funcionario.getNombre());
			pstmt1.setString(2, funcionario.getTel());
			pstmt1.setString(3, funcionario.getDocumento().getNumero());
			pstmt1.setString(4, funcionario.getDocumento().getCodigo());
			pstmt1.setString(5, funcionario.getDireccion());
			pstmt1.setString(6, funcionario.getMail());
			pstmt1.setBoolean(7, funcionario.isActivo());
			pstmt1.setString(8, funcionario.getUsuarioMod());
			pstmt1.setString(9, funcionario.getOperacion());
			pstmt1.setTimestamp(10, funcionario.getFechaMod());
			pstmt1.setInt(11, funcionario.getCodigo());
			pstmt1.setString(12, empresa);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoFuncionarioException();
		}
	}
	
		
	
}
