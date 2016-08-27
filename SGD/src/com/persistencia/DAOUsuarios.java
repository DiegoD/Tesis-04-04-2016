package com.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.excepciones.ConexionException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Formulario;
import com.logica.Grupo;
import com.logica.GruposUsuario;
import com.logica.Usuario;
import com.sun.jna.platform.win32.Sspi.TimeStamp;
import com.valueObject.EmpLoginVO;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;
import com.valueObject.LoginVO;
import com.valueObject.UsuarioVO;
import com.valueObject.empresa.EmpresaVO;

public class DAOUsuarios implements IDAOUsuarios {

    
	//private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
	
	@Override
	/**
	 * Valida un usuario en la base de datos
	 *
	 */
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

	/**
	 * Obtiene Array de todos lo usuarios existentes
	 */
	public ArrayList<Usuario> getUsuarios(Connection con) throws ObteniendoUsuariosException, ConexionException, ObteniendoGruposException{
		
		ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getUsuarios();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				Timestamp t = rs.getTimestamp(7);
				
				Usuario usr = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5), rs.getTimestamp(7), rs.getString(6), rs.getString(8));
				
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

	/**
	 * Dado el usuario, valida si existe
	 */
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

	/**
	 * Inserta un usuario en la base
	 * Pre condición: El nombre de usuario no debe existir previamente
	 */
	public void insertarUsuario(Usuario user, String empresa, Connection con) throws InsertandoUsuarioException, ConexionException {

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
			pstmt1.setString(7, user.getMail());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.insertarGruposxUsuario(user.getUsuario(), user.getLstGrupos(), empresa, con);
			
			this.insertarUsuarioxEmp(user.getUsuario(), empresa, con);
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoUsuarioException();
		} 
		
	}
	
	/**
	 * Elimina un usuario de la base dado el usuario
	 */
	public void eliminarUsuario(Usuario user, Connection con) throws InsertandoUsuarioException, ConexionException, ModificandoUsuarioException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String eliminar = clts.elminarUsuario();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
    		this.eliminarGruposxUsuario(user.getUsuario(), con);
    		
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, user.getUsuario());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();

			
		} catch (SQLException e) {
			throw new InsertandoUsuarioException();
			
		} 
		
	}

	/**
	 * Obtiene los grupos asignados a un usuario
	 */
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

	/**
	 * OBtiene los grupos que un usuario no tiene asignados
	 */
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
	 * Insertamos un grupo para el usuario
	 */
	private void insertarGruposxUsuario(String usuario, ArrayList<Grupo> lstGrupos, String empresa, Connection con) throws InsertandoUsuarioException, ConexionException 
	{

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarGruposxUsuario();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
    		
    		for (Grupo grupo: lstGrupos) {
				
    			pstmt1.setString(1, grupo.getCodGrupo());
    			pstmt1.setString(2, usuario);
    			pstmt1.setString(3, empresa);

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
	
	
	public void modificarUsuario(Usuario user, String empresa, Connection con) throws ModificandoUsuarioException{
		
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.getActualizarUsuario();
		PreparedStatement pstmt1;
		
		try {
			this.eliminarGruposxUsuario(user.getUsuario(), con);
			this.insertarGruposxUsuario(user.getUsuario(), user.getLstGrupos(), empresa, con);
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, user.getNombre());
			pstmt1.setString(2, user.getPass());
			pstmt1.setString(3, user.getUsuarioMod());
			pstmt1.setString(4, user.getOperacion());
			pstmt1.setBoolean(5, user.isActivo());
			pstmt1.setString(6, user.getMail());
			pstmt1.setString(7, user.getUsuario());
			
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException | ModificandoUsuarioException | ConexionException | InsertandoUsuarioException e) {
			
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
				form.setLeer(rs.getBoolean(3));
				form.setNuevoEditar(rs.getBoolean(4));
				form.setBorrar(rs.getBoolean(5));
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
	 * Nos retorna los permisos para el formulario usuario empresa
	 */
	public Formulario getPermisoFormularioOperacionUsuario(String usuario, String codEmp, String formulario, Connection con) throws ObteniendoPermisosException 
	{
		Formulario form = null;
		
		try 
		{
			Consultas consultas = new Consultas();
			String query = consultas.getFormularioOperacionxUsuario();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, usuario);
			pstmt1.setString(2, codEmp);
			pstmt1.setString(3, formulario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			while(rs.next ()) 
			{
				form = new Formulario();
				form.setCodFormulario(rs.getString(1));
				form.setNomFormulario(rs.getString(2));
				form.setLeer(rs.getBoolean(3));
				form.setNuevoEditar(rs.getBoolean(4));
				form.setBorrar(rs.getBoolean(5));
				
			}
			rs.close();
			pstmt1.close();
		} 
		catch (Exception e) 
		{
			throw new ObteniendoPermisosException();
		}
		return form;
		
	}
	
	/**
	 * Obtenemos las empresas para el usuario
	 * 
	 */
	public EmpLoginVO getEmpresaUsuario(String usuario, Connection con) throws ObteniendoUsuariosxEmpExeption 
	{
		EmpLoginVO empVO = null;
		
		try 
		{
			Consultas consultas = new Consultas();
			String query = consultas.getUsuariosxEmp();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, usuario);
			
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next ()) 
			{
				empVO = new EmpLoginVO();
				empVO.setCodEmp(rs.getString(1));
				empVO.setNomEmp(rs.getString(2));
				
				
			}
			rs.close();
			pstmt1.close();
		} 
		catch (Exception e) 
		{
			throw new ObteniendoUsuariosxEmpExeption();
		}
		return empVO;
		
	}
	
	private void insertarUsuarioxEmp(String usuario, String empresa, Connection con) throws InsertandoUsuarioException, ConexionException 
	{

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarUsuarioxEmp();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
    		
			pstmt1.setString(1, empresa);
			pstmt1.setString(2, usuario);

			pstmt1.executeUpdate ();
    		
			pstmt1.close ();
	
		} catch (SQLException e) {
			
			throw new InsertandoUsuarioException();
		} 
	}
	
}
