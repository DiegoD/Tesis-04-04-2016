package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.*;
import com.logica.Banco;
import com.logica.CtaBco;
import com.logica.Moneda;
import com.mysql.jdbc.Statement;

public class DAOBancos implements IDAOBancos{

	/**
	 * Nos retorna una lista con todos los Bancos del sistema para la emrpesa
	 */
	public ArrayList<Banco> getBancosTodos(Connection con, String codEmp) throws ObteniendoBancosException, ObteniendoCuentasBcoException , ConexionException {
		
		ArrayList<Banco> lstBancos = new ArrayList<Banco>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getBancos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Banco aux;
			while(rs.next ()) {
							
				aux = new Banco();
				
				aux.setCodigo(rs.getString("cod_bco"));
				aux.setNombre(rs.getString("nom_bco"));
				aux.setCodEmp(rs.getString("cod_emp"));
				aux.setTel(rs.getString("tel"));
				aux.setDireccion(rs.getString("direccion"));
				aux.setContacto(rs.getString("contacto"));
				aux.setActivo(rs.getBoolean("activo"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				/*Obtenemos las cuentas para el banco*/
				aux.setLstCtas(this.getCtaBco(con, codEmp, aux.getCodigo()));
				
				lstBancos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch(ObteniendoCuentasBcoException e){
			throw e;
		}
		catch (SQLException e) {
			throw new ObteniendoBancosException();
			
		}
    	
    	return lstBancos;
	}
	
	/**
	 * Nos retorna una lista con todos los Bancos del sistema para la emrpesa que estan activos
	 */
	public ArrayList<Banco> getBancosActivos(Connection con, String codEmp) throws ObteniendoBancosException, ObteniendoCuentasBcoException , ConexionException {
		
		ArrayList<Banco> lstBancos = new ArrayList<Banco>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getBancosActivos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Banco aux;
			while(rs.next ()) {
				
							
				aux = new Banco();
				
				aux.setCodigo(rs.getString("cod_bco"));
				aux.setNombre(rs.getString("nom_bco"));
				aux.setCodEmp(rs.getString("cod_emp"));
				aux.setTel(rs.getString("tel"));
				aux.setDireccion(rs.getString("direccion"));
				aux.setContacto(rs.getString("contacto"));
				aux.setActivo(rs.getBoolean("activo"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				/*Obtenemos las cuentas activas para el banco*/
				aux.setLstCtas(this.getCtaBcoActivos(con, codEmp, aux.getCodigo()));
				
				lstBancos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch(ObteniendoCuentasBcoException e){
			throw e;
		}
		catch (SQLException e) {
			throw new ObteniendoBancosException();
			
		}
    	
    	return lstBancos;
	}

	/**
	 * Dado el codigo del banco, valida si existe
	 */
	public boolean memberBanco(String codBanco, String codEmp, Connection con) throws ExisteBancoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberBanco();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codBanco);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteBancoException();
		}
	}
	

	/**
	 * Inserta un banco en la base
	 * @throws InsertandoCuentaException 
	 * @throws InsertandoBancoException 
	 */
	public void insertarBanco(Banco banco, String codEmp, Connection con) throws InsertandoBancoException, ConexionException, InsertandoCuentaException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarBanco();
    	
    	PreparedStatement pstmt1;

    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, banco.getCodigo());
			pstmt1.setString(2, banco.getNombre());
			pstmt1.setString(3, codEmp);
			pstmt1.setString(4, banco.getTel());
			pstmt1.setString(5, banco.getDireccion());
			pstmt1.setString(6, banco.getContacto());
			pstmt1.setBoolean(7, banco.isActivo());
			pstmt1.setString(8, banco.getUsuarioMod());
			pstmt1.setString(9, banco.getOperacion());
			pstmt1.setTimestamp(10,banco.getFechaMod());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			for (CtaBco cta : banco.getLstCtas()) {
				
				this.insertarCtaBanco(cta, codEmp, banco.getCodigo(), con);
				
			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoBancoException();
		} 
		
    	
	}
	
	/**
	 * MOdificamos banco
	 * @throws ConexionException 
	 * @throws ModificandoCuentaBcoException 
	 * 
	 */
	public void modificarBanco(Banco banco, String codEmp, Connection con) throws ModificandoBancoException, ModificandoCuentaBcoException, ConexionException{
		
		Consultas clts = new Consultas();
    	
    	String insert = clts.actualizarBanco();
    	
    	PreparedStatement pstmt1;

    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, banco.getNombre());
			pstmt1.setString(2, banco.getTel());
			pstmt1.setString(3, banco.getDireccion());
			pstmt1.setString(4, banco.getContacto());
			pstmt1.setBoolean(5, banco.isActivo());
			pstmt1.setString(6, banco.getUsuarioMod());
			pstmt1.setString(7, banco.getOperacion());
			pstmt1.setTimestamp(8,banco.getFechaMod());
			
			pstmt1.setString(9, banco.getCodigo());
			pstmt1.setString(10, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			for (CtaBco cta : banco.getLstCtas()) {
				
				this.actualizarCtaBanco(cta, codEmp, banco.getCodigo(), con);
				
			}
		} 
    	catch (SQLException e) 
    	{
			throw new ModificandoBancoException();
		} 
	}
/////////////////////////////////////CUENTAS/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todos los Bancos del sistema para la emrpesa
	 */
	private ArrayList<CtaBco> getCtaBco(Connection con, String codEmp, String codBco) throws ObteniendoCuentasBcoException, ConexionException {
		
		ArrayList<CtaBco> lstCtaBancos = new ArrayList<CtaBco>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getCtasBancos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codBco);
	    	pstmt1.setString(2, codEmp);
			rs = pstmt1.executeQuery();
			
			CtaBco aux;
			Moneda monedaAux;
			while(rs.next ()) {
				
							
				aux = new CtaBco();
				
				aux.setCodigo(rs.getString("cod_ctabco"));
				aux.setNombre(rs.getString("nom_cta"));
				aux.setCodBco(rs.getString("cod_bco"));
				aux.setCodEmp(rs.getString("cod_emp"));
				
				aux.setActivo(rs.getBoolean("activo"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
				monedaAux = new Moneda();
				monedaAux.setAcepta_cotizacion(rs.getBoolean("acepta_cotizacion"));
				monedaAux.setActivo(rs.getBoolean("activoMoneda"));
				monedaAux.setCod_moneda(rs.getString("cod_moneda"));
				monedaAux.setDescripcion(rs.getString("descripcion"));
				monedaAux.setFechaMod(rs.getTimestamp("m_fecha_mod"));
				monedaAux.setUsuarioMod(rs.getString("m_usuario_mod"));
				monedaAux.setOperacion(rs.getString("m_operacion"));
				monedaAux.setSimbolo(rs.getString("simbolo"));
				
				aux.setMoneda(monedaAux);
				
				lstCtaBancos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoCuentasBcoException();
			
		}
    	
    	return lstCtaBancos;
	}
	
	
	/**
	 * Nos retorna una lista con todos los Bancos del sistema para la emrpesa
	 */
	private ArrayList<CtaBco> getCtaBcoActivos(Connection con, String codEmp, String codBco) throws ObteniendoCuentasBcoException, ConexionException {
		
		ArrayList<CtaBco> lstCtaBancos = new ArrayList<CtaBco>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getCtasBancosActivos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codBco);
	    	pstmt1.setString(2, codEmp);
			rs = pstmt1.executeQuery();
			
			CtaBco aux;
			while(rs.next ()) {
				
							
				aux = new CtaBco();
				
				aux.setCodigo(rs.getString("cod_ctabco"));
				aux.setNombre(rs.getString("nom_cta"));
				aux.setCodBco(rs.getString("cod_bco"));
				aux.setCodEmp(rs.getString("cod_emp"));
				
				aux.setActivo(rs.getBoolean("activo"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
							
				
				lstCtaBancos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoCuentasBcoException();
			
		}
    	
    	return lstCtaBancos;
	}
	

	/**
	 * Inserta una ctabco en la base
	 * 
	 */
	private void insertarCtaBanco(CtaBco cta, String codEmp, String codBco, Connection con) throws InsertandoCuentaException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarCtaBanco();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(insert);
							
			pstmt1.setString(1, cta.getCodigo());
			pstmt1.setString(2, cta.getNombre());
			pstmt1.setString(3, codBco);
			pstmt1.setString(4, codEmp);
			pstmt1.setBoolean(5, cta.isActivo());

			pstmt1.setString(6, cta.getUsuarioMod());
			pstmt1.setString(7, cta.getOperacion());
			pstmt1.setTimestamp(8,cta.getFechaMod());
			
			
			pstmt1.executeUpdate ();
			
				
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoCuentaException();
		} 
		
	}
	
	/**
	 * Actualizamos cuenta
	 * 
	 */
	private void actualizarCtaBanco(CtaBco cta, String codEmp, String codBco, Connection con) throws ModificandoCuentaBcoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String update = clts.actualizarCtaBanco();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(update);
							
			pstmt1.setString(1, cta.getNombre());
			pstmt1.setBoolean(2, cta.isActivo());
			pstmt1.setString(3, cta.getUsuarioMod());
			pstmt1.setString(4, cta.getOperacion());
			pstmt1.setTimestamp(5,cta.getFechaMod());
			
			pstmt1.setString(6, cta.getCodigo());
			pstmt1.setString(7, codBco);
			pstmt1.setString(8, codEmp);
			
			pstmt1.executeUpdate ();
				
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new ModificandoCuentaBcoException();
		} 
		
	}
}
