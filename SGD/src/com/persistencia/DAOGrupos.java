package com.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import com.excepciones.ConexionException;

import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Formulario;
import com.logica.Grupo;


public class DAOGrupos implements IDAOGrupos {
	

	public ArrayList<Grupo> getGrupos(Connection con) throws ObteniendoGruposException, ObteniendoFormulariosException
	{
		ArrayList<Grupo> lstGrupos = new ArrayList<Grupo>();
		
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getGrupos();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Grupo grupo;
			
			while(rs.next ()) {

				grupo = new Grupo();

				grupo.setCodGrupo(rs.getString(1));
				grupo.setNomGrupo(rs.getString(2));
				grupo.setFechaMod(rs.getTimestamp(3));
				grupo.setUsuarioMod(rs.getString(4));
				grupo.setOperacion(rs.getString(5));
				grupo.setActivo(rs.getBoolean(6));
				
				/*Obtenemos los formularios del grupo*/
				grupo.setLstFormularios(this.getFormulariosxGrupo(grupo.getCodGrupo(), con));

				lstGrupos.add(grupo);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoGruposException();
		}
			
		return lstGrupos;
	}

	public Grupo getGrupo(Connection con, String codGrupo) throws ObteniendoGruposException, ObteniendoFormulariosException
	{
		Grupo grupo = null;
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			
			while(rs.next ()) {

				grupo = new Grupo();

				grupo.setCodGrupo(rs.getString(1));
				grupo.setNomGrupo(rs.getString(2));
				grupo.setFechaMod(rs.getTimestamp(3));
				grupo.setUsuarioMod(rs.getString(4));
				grupo.setOperacion(rs.getString(5));
				grupo.setActivo(rs.getBoolean(6));
				
				/*Obtenemos los formularios del grupo*/
				grupo.setLstFormularios(this.getFormulariosxGrupo(grupo.getCodGrupo(), con));

			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoGruposException();
		}
			
		return grupo;
	}
	

	/**
	 * Insertamos grupo dado grupo,
	 * PRECONDICION: El código del codigo no debe existir
	 *
	 */
	public void insertarGrupo(Grupo grupo, Connection con) throws InsertandoGrupoException, ConexionException 
	{

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarGrupo();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, grupo.getCodGrupo());
			pstmt1.setString(2, grupo.getNomGrupo());
			pstmt1.setString(3, grupo.getUsuarioMod());
			pstmt1.setString(4, grupo.getOperacion());
			pstmt1.setBoolean(5, grupo.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.insertarFormulariosxGrupo(grupo.getCodGrupo(), grupo.getLstFormularios(), con);
	
		} catch (SQLException e) {
			
			throw new InsertandoGrupoException();
		} 
	}
	
	/**
	 * Nos retorna true si existe el código del grupo
	 *
	 */
	public boolean memberGrupo(String codGrupo, Connection con) throws MemberGrupoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
						
			Consultas consultas = new Consultas ();
			String query = consultas.memberGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
						
			return existe;
			
		}catch(SQLException e){
			
			throw new MemberGrupoException();
		}
	}
	
	/**
	 * Eliminamos grupo dado el codigo,
	 * PRECONDICION: El código del codigo debe existir
	 * PRECONDICION: Invocar dentro de una transaction
	 *
	 */
	public void eliminarGrupo(String codGrupo, Connection con) throws ModificandoGrupoException, ConexionException
	{
		Consultas consultas = new Consultas ();
		String delete = consultas.eliminarGrupo();
		
		PreparedStatement pstmt1;
		
		
		try 
		{
			this.eliminarFormulariosxGrupo(codGrupo, con);
			
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setString(1, codGrupo);
			
			pstmt1.executeUpdate ();
			
			
			
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoGrupoException();
		}
		
		
	}

	/**
	 * Actualizamos grupo dado el codigo,
	 * PRECONDICION: El código del codigo debe existir
	 * PRECONDICION: Invocar dentro de una transaction
	 * @throws ModificandoGrupoException 
	 *
	 */
	public void actualizarGrupo(Grupo grupo, Connection con) throws ModificandoGrupoException
	{
		Consultas consultas = new Consultas ();
		String update = consultas.getActualizarGrupo();
		
		PreparedStatement pstmt1;
		
		
		try 
		{
			/*Primero eliminamos los formularios para luego volver a insertarlos*/
			this.eliminarFormulariosxGrupo(grupo.getCodGrupo(), con);
			
			/*Volvemos a insertar los formularios modificados*/
			this.insertarFormulariosxGrupo(grupo.getCodGrupo(), grupo.getLstFormularios(), con);
			
			/*Updateamos la info del grupo*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, grupo.getNomGrupo());
			pstmt1.setString(2, grupo.getUsuarioMod());
			pstmt1.setString(3, grupo.getOperacion());
			pstmt1.setBoolean(4, grupo.isActivo());
			pstmt1.setString(5, grupo.getCodGrupo());
			
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
			
		} catch (SQLException | ModificandoGrupoException | ConexionException | InsertandoGrupoException e) {
			
			throw new ModificandoGrupoException();
		}
		
		
	}
	
	/**
	 * Nos retorna los formularios activos para el grupo
	 *
	 */
	private ArrayList<Formulario> getFormulariosxGrupo(String codGrupo, Connection con) throws ObteniendoFormulariosException
	{
		ArrayList<Formulario> lstFormulario = new ArrayList<Formulario>();
		
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getFormulariosxGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Formulario formulario;
			
			while(rs.next ()) {

				formulario = new Formulario();

				formulario.setCodFormulario(rs.getString(1));
				formulario.setNomFormulario(rs.getString(2));
				
				formulario.setLeer(rs.getBoolean(3));
				formulario.setNuevoEditar(rs.getBoolean(4));
				formulario.setBorrar(rs.getBoolean(5));
				
				lstFormulario.add(formulario);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoFormulariosException();
		}
			
		return lstFormulario;
	}
	
	/**
	 * Insertamos grupo dado grupo,
	 * PRECONDICION: El código del codigo no debe existir
	 *
	 */
	private void insertarFormulariosxGrupo(String codGrupo, ArrayList<Formulario> lstFormularios, Connection con) throws InsertandoGrupoException, ConexionException 
	{

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarFormulariosxGrupo();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
    		
    		for (Formulario formulario : lstFormularios) {
				
    			pstmt1.setString(1, formulario.getCodFormulario());
    			pstmt1.setString(2, codGrupo);
    			
    			pstmt1.setBoolean(3, formulario.isLeer());
    			pstmt1.setBoolean(4, formulario.isNuevoEditar());
    			pstmt1.setBoolean(5, formulario.isBorrar());

    			pstmt1.executeUpdate ();
			}
    		
			pstmt1.close ();
	
		} catch (SQLException e) {
			
			throw new InsertandoGrupoException();
		} 
	}

	
	/**
	 * Eliminamos formularios del grupo dado el codigo del grupo,
	 * PRECONDICION: El código del codigo debe existir
	 *
	 */
	private void eliminarFormulariosxGrupo(String codGrupo, Connection con) throws ModificandoGrupoException, ConexionException
	{
		Consultas consultas = new Consultas ();
		String delete = consultas.eliminarFormulariosxGrupo();
		
		PreparedStatement pstmt1;
		
		try 
		{
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setString(1, codGrupo);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoGrupoException();
		}
	}
	
	
	/**
	 * Nos retorna los formualrios que no pertenecen
	 * al grupo para que los pueda agregar
	 *
	 */
	public ArrayList<Formulario> getFormulariosNoGrupo(String codGrupo, Connection con) throws ObteniendoFormulariosException
	{
		ArrayList<Formulario> lstFormulario = new ArrayList<Formulario>();
		
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getFormulariosNOGrupo();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codGrupo);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Formulario formulario;
			
			while(rs.next ()) {

				formulario = new Formulario();

				formulario.setCodFormulario(rs.getString(1));
				formulario.setNomFormulario(rs.getString(2));
				
				lstFormulario.add(formulario);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoFormulariosException();
		}
			
		return lstFormulario;
	}
	

}
