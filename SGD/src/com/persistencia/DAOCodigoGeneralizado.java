package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.CodigosGeneralizados.EliminandoCodigoGeneralizadoException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.logica.CodigoGeneralizado;
import com.logica.Empresa;

public class DAOCodigoGeneralizado implements IDAOCodigosGeneralizados{

	@Override
	public ArrayList<CodigoGeneralizado> getCodigosGeneralizados(Connection con)
			throws ObteniendoCodigosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<CodigoGeneralizado> lstCodigos = new ArrayList<CodigoGeneralizado>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getCodigosGeneralizados();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			CodigoGeneralizado codigoGeneralizado;
			
			while(rs.next ()) {

				codigoGeneralizado = new CodigoGeneralizado();
				
				codigoGeneralizado.setCodigo(rs.getString(1));
				codigoGeneralizado.setValor(rs.getString(2));
				codigoGeneralizado.setDescripcion(rs.getString(3));
				codigoGeneralizado.setFechaMod(rs.getTimestamp(4));
				codigoGeneralizado.setUsuarioMod(rs.getString(5));
				codigoGeneralizado.setOperacion(rs.getString(6));
				
				lstCodigos.add(codigoGeneralizado);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoCodigosException();
		}
			
		return lstCodigos;
	}

	/**
	 * Inserta un codigo generalizado en la base
	 * Pre condición: El código y el valor no deben existir previamente
	 */
	@Override
	public void insertarCodigoGeneralizado(CodigoGeneralizado codigoGeneralizado, Connection con)
			throws InsertandoCodigoException, ConexionException {
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarCodigoGeneralizado();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, codigoGeneralizado.getCodigo());
			pstmt1.setString(2, codigoGeneralizado.getValor());
			pstmt1.setString(3, codigoGeneralizado.getDescripcion());
			pstmt1.setString(4, codigoGeneralizado.getUsuarioMod());
			pstmt1.setString(5, codigoGeneralizado.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoCodigoException();
		} 
		
	}

	
	/**
	 * Dado el codigo y el valor del código generalizado, valida si existe
	 */
	@Override
	public boolean memberCodigoGeneralizado(String codigo, String valor, Connection con)
			throws ExisteCodigoException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberCodigoGeneralizado();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codigo);
			pstmt1.setString(2, valor);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteCodigoException();
		}
	}

	/**
	 * Actualizamos el código dado el codigo y el valor,
	 * PRECONDICION: El código y el valor deben existir
	 */
	@Override
	public void actualizarCodigoGeneralizado(CodigoGeneralizado codigoGeneralizado, Connection con)
			throws ModificandoCodigoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarCodigoGeneralizado();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, codigoGeneralizado.getDescripcion());
			pstmt1.setString(2, codigoGeneralizado.getUsuarioMod());
			pstmt1.setString(3, codigoGeneralizado.getOperacion());
			pstmt1.setString(4, codigoGeneralizado.getCodigo());
			pstmt1.setString(5, codigoGeneralizado.getValor());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoCodigoException();
		}
	}
	
	/**
	 * Elimina codigo generalizado dado su codigo y valor
	 */
	public void eliminarCodigoGeneralizado(String codigo, String valor, Connection con) throws EliminandoCodigoGeneralizadoException, ConexionException
	{
		ConsultasDD clts = new ConsultasDD();
		
		String eliminar = clts.eliminarCodigoGeneralizado();
		
		PreparedStatement pstmt1;
		    	
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, codigo);
			pstmt1.setString(2, valor);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			throw new EliminandoCodigoGeneralizadoException();
			
		} 
	}
}
