package com.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Formulario;
import com.logica.Grupo;
import com.logica.GruposUsuario;
import com.logica.Usuario;
import com.valueObject.EmpLoginVO;
import com.valueObject.EmpresaVO;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;
import com.valueObject.UsuarioVO;

public class DAOUsuarios implements IDAOUsuarios {

    
	//private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	
	@Override
	public boolean usuarioValido(LoginVO loginVO, Connection con) throws LoginException {
		
	   	boolean usuarioValido = false;
	   	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
		
			Consultas consultas = new Consultas ();
			String query = consultas.getUsuarioValido();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, loginVO.getUsuario());
			pstmt1.setString(2, loginVO.getPass());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ())
				usuarioValido = true;
			
			rs.close ();
			pstmt1.close ();
			
			return usuarioValido;
		}
		catch (SQLException | ClassNotFoundException e) {
			throw new LoginException();
			
		}
		
	}

	public ArrayList<Usuario> getUsuarios(Connection con) throws ObteniendoUsuariosException, ConexionException, ObteniendoGruposException{
		
		ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
	
		try {
			
			System.out.println("estoy en DAO usuarios ");
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getUsuarios();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				Usuario usr = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
				
				System.out.println("Encontro usuario");
				
				usr.setLstGrupos(this.getGruposxUsuario(usr.getUsuario(), con));
				
				lstUsuarios.add(usr);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoUsuariosException();
			
		}
    	
    	return lstUsuarios;
	}

	public boolean memberUsuario(String usuario, Connection con) throws ExisteUsuarioException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, usuario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteUsuarioException();
		}
	}

	public void insertarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarUsuario();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, user.getUsuario());
			pstmt1.setString(2, user.getNombre());
			pstmt1.setString(3, user.getPass());
			pstmt1.setString(4, user.getUsuarioMod());
			pstmt1.setString(5, user.getOperacion());
			pstmt1.setBoolean(6, user.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.insertarGruposxUsuario(user.getUsuario(), user.getLstGrupos(), con);
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoUsuarioException();
		} 
		
	}
	
	public void eliminarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException, ModificandoUsuarioException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String eliminar = clts.elminarUsuario();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
    		this.eliminarGruposxUsuario(user.getUsuario(), con);
    		
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, user.getUsuario());
			
			//pstmt1.setString(6, nuevo);
			System.out.println("voy a eliminar " + user.getUsuario());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();

			
		} catch (SQLException e) {
			throw new InsertandoUsuarioException();
			
		} 
		
	}

	public ArrayList<Grupo> getGruposxUsuario(String usuario, Connection con) throws ObteniendoGruposException
	{
		ArrayList<Grupo> lstGrupos = new ArrayList<Grupo>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getGrposxUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, usuario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Grupo grupoUsuario;
			
			while(rs.next ()) {

				grupoUsuario = new Grupo();

				grupoUsuario.setCodGrupo(rs.getString(1));
				grupoUsuario.setNomGrupo(rs.getString(2));
				
				lstGrupos.add(grupoUsuario);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoGruposException();
		}
			
		return lstGrupos;
	}

	public ArrayList<GrupoVO> getGruposNoUsuario(String nombreUsurio, Connection con) throws ObteniendoUsuariosException, ObteniendoGruposException
	{
		ArrayList<GrupoVO> lstGrupo = new ArrayList<GrupoVO>();
		try 
		{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.getGruposNoUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, nombreUsurio);
			
			ResultSet rs = pstmt1.executeQuery();
			
			GrupoVO grupo;
			
			while(rs.next ()) 
			{
				grupo = new GrupoVO();
				grupo.setCodGrupo(rs.getString(1));
				grupo.setNomGrupo(rs.getString(2));
				lstGrupo.add(grupo);
			}
			rs.close();
			pstmt1.close();
		} 
		catch (Exception e) 
		{
			throw new ObteniendoGruposException();
		}
		return lstGrupo;
		
	}
	
	/**
	 * Insertamos grupo dado grupo,
	 * PRECONDICION: El c�digo del codigo no debe existir
	 *
	 */
	private void insertarGruposxUsuario(String usuario, ArrayList<Grupo> lstGrupos, Connection con) throws InsertandoUsuarioException, ConexionException 
	{

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarGruposxUsuario();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
    		
    		for (Grupo grupo: lstGrupos) {
				
    			pstmt1.setString(1, grupo.getCodGrupo());
    			pstmt1.setString(2, usuario);

    			pstmt1.executeUpdate ();
			}
    		
			pstmt1.close ();
	
		} catch (SQLException e) {
			
			throw new InsertandoUsuarioException();
		} 
	}
	
	
	/**
	 * Dado el usuario eliminamos los grupos del mismo
	 */
	private void eliminarGruposxUsuario(String usuario, Connection con) throws ModificandoUsuarioException, ConexionException
	{
		ConsultasDD consultas = new ConsultasDD ();
		String delete = consultas.eliminarGruposxUsuario();
		
		PreparedStatement pstmt1;
		
		try 
		{
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setString(1, usuario);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoUsuarioException();
		}
	}
	
	
	/**
	 * Nos retorna los formularios habilitados para el usuario
	 */
	public ArrayList<Formulario> getFormulariosxUsuario(String usuario, String codEmp, Connection con) throws ObteniendoFormulariosException 
	{
		ArrayList<Formulario> lstFormularios = new ArrayList<Formulario>();
		try 
		{
			Consultas consultas = new Consultas();
			String query = consultas.getFormulariosxUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, usuario);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Formulario form;
			
			while(rs.next ()) 
			{
				form = new Formulario();
				form.setCodFormulario(rs.getString(1));
				form.setNomFormulario(rs.getString(2));
				lstFormularios.add(form);
			}
			rs.close();
			pstmt1.close();
		} 
		catch (Exception e) 
		{
			throw new ObteniendoFormulariosException();
		}
		return lstFormularios;
		
	}
	
	/**
	 * Obtenemos las empresas para el usuario
	 * 
	 */
	public ArrayList<EmpLoginVO> getUsuariosxEmp(String usuario, Connection con) throws ObteniendoUsuariosxEmpExeption 
	{
		ArrayList<EmpLoginVO> lstEmpresas = new ArrayList<EmpLoginVO>();
		try 
		{
			Consultas consultas = new Consultas();
			String query = consultas.getUsuariosxEmp();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, usuario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			EmpLoginVO empVO;
			
			while(rs.next ()) 
			{
				empVO = new EmpLoginVO();
				empVO.setCodEmp(rs.getString(1));
				empVO.setNomEmp(rs.getString(2));
				
				lstEmpresas.add(empVO);
			}
			rs.close();
			pstmt1.close();
		} 
		catch (Exception e) 
		{
			throw new ObteniendoUsuariosxEmpExeption();
		}
		return lstEmpresas;
		
	}
	
}
