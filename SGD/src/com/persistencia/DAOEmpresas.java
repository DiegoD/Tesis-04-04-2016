package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.logica.Empresa;

public class DAOEmpresas implements IDAOEmpresas{

	@Override
	public ArrayList<Empresa> getEmpreas(Connection con) throws ObteniendoEmpresasException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Empresa> lstEmpresas = new ArrayList<Empresa>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getEmpresas();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Empresa empresa;
			
			while(rs.next ()) {

				empresa = new Empresa();
				
				empresa.setCod_emp(rs.getString(1));
				empresa.setNom_emp(rs.getString(2));
				empresa.setFechaMod(rs.getTimestamp(3));
				empresa.setUsuarioMod(rs.getString(4));
				empresa.setOperacion(rs.getString(5));
				empresa.setActivo(rs.getBoolean(6));
				
				lstEmpresas.add(empresa);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoEmpresasException();
		}
			
		return lstEmpresas;
	}

	@Override
	/**
	 * Inserta una empresa en la base
	 * Pre condición: El código de empresa no debe existir previamente
	 */
	public void insertarEmpresa(Empresa empresa, Connection con) throws InsertandoEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarEmpresa();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, empresa.getCod_emp());
			pstmt1.setString(2, empresa.getNom_emp());
			pstmt1.setString(3, empresa.getUsuarioMod());
			pstmt1.setString(4, empresa.getOperacion());
			pstmt1.setBoolean(5, empresa.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoEmpresaException();
		} 
		
	}

	/**
	 * Dado el codigo de empresa, valida si existe
	 */
	@Override
	public boolean memberEmpresa(String cod_emp, Connection con) throws ExisteEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberEmpresa();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteEmpresaException();
		}
	}

	/**
	 * Actualizamos la empresa dado el codigo,
	 * PRECONDICION: El código de la empresa debe existir
	 */
	@Override
	public void actualizarEmpresa(Empresa empresa, Connection con)
			throws ModificandoEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarEmpresa();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, empresa.getNom_emp());
			pstmt1.setBoolean(2, empresa.isActivo());
			pstmt1.setString(3, empresa.getUsuarioMod());
			pstmt1.setString(4, empresa.getOperacion());
			pstmt1.setString(5, empresa.getCod_emp());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoEmpresaException();
		}
		
	}

	
}