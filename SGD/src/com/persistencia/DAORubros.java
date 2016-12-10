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
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.logica.Empresa;
import com.logica.Impuesto;
import com.logica.Rubro;
import com.logica.RubroCuenta;
import com.logica.TipoRubro;

public class DAORubros implements IDAORubros{

	DAOImpuestos impuestos = new DAOImpuestos();
	
	@Override
	public ArrayList<Rubro> getRubros(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Rubro> lstRubros = new ArrayList<Rubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getRubros();
			String queryImpuesto = consultas.getImpuesto();
			String queryTipoRubro = consultas.getTipoRubro();
			String codImpuesto;
			String codTipoRubro;
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			PreparedStatement pstmt2 = con.prepareStatement(queryImpuesto);
			PreparedStatement pstmt3 = con.prepareStatement(queryTipoRubro);
			
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Rubro rubro;
			Impuesto impuesto;
			TipoRubro tipoRubro;
			while(rs.next ()) {

				rubro = new Rubro();
				
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcion(rs.getString(2));
				rubro.setActivo(rs.getBoolean(3));
				rubro.setFechaMod(rs.getTimestamp(4));
				rubro.setUsuarioMod(rs.getString(5));
				rubro.setOperacion(rs.getString(6));
				codImpuesto = rs.getString(7);
				codTipoRubro = rs.getString(8);
				rubro.setFacturable(rs.getBoolean(10));
				
				
				pstmt2.setString(1, codImpuesto);
				pstmt2.setString(2, codEmp);
				
				ResultSet rs2 = pstmt2.executeQuery();
				
				while(rs2.next()){
					
					impuesto = new Impuesto();
					impuesto.setCod_imp(rs2.getString(1));
					impuesto.setDescripcion(rs2.getString(2));
					impuesto.setPorcentaje(rs2.getDouble(3));
					impuesto.setActivo(rs2.getBoolean(4));
					impuesto.setFechaMod(rs2.getTimestamp(5));
					impuesto.setUsuarioMod(rs2.getString(6));
					impuesto.setOperacion(rs2.getString(7));
					
					
					rubro.setImpuesto(impuesto);
				}
				
				pstmt3.setString(1, codTipoRubro);
				pstmt3.setString(2, codEmp);
				
				ResultSet rs3 = pstmt3.executeQuery();
				
				while(rs3.next()){
					
					tipoRubro = new TipoRubro();
					tipoRubro.setCod_tipoRubro(rs3.getString(1));
					tipoRubro.setDescripcion(rs3.getString(2));
					tipoRubro.setFechaMod(rs3.getTimestamp(3));
					tipoRubro.setUsuarioMod(rs3.getString(4));
					tipoRubro.setOperacion(rs3.getString(5));
					tipoRubro.setActivo(rs3.getBoolean(6));
					tipoRubro.setCod_emp(rs3.getString(7));
					
					rubro.setTipoRubro(tipoRubro);
				}
				
				lstRubros.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubros;
	}

	@Override
	public ArrayList<Rubro> getRubrosActivos(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Rubro> lstRubros = new ArrayList<Rubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getRubrosActivos();
			String queryImpuesto = consultas.getImpuesto();
			String queryTipoRubro = consultas.getTipoRubro();
			String codImpuesto;
			String codTipoRubro;
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			PreparedStatement pstmt2 = con.prepareStatement(queryImpuesto);
			PreparedStatement pstmt3 = con.prepareStatement(queryTipoRubro);
			
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Rubro rubro;
			Impuesto impuesto;
			TipoRubro tipoRubro;
			while(rs.next ()) {

				rubro = new Rubro();
				
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcion(rs.getString(2));
				rubro.setActivo(rs.getBoolean(3));
				rubro.setFechaMod(rs.getTimestamp(4));
				rubro.setUsuarioMod(rs.getString(5));
				rubro.setOperacion(rs.getString(6));
				codImpuesto = rs.getString(7);
				codTipoRubro = rs.getString(8);
				rubro.setFacturable(rs.getBoolean(9));
				
				pstmt2.setString(1, codImpuesto);
				pstmt2.setString(2, codEmp);
				
				ResultSet rs2 = pstmt2.executeQuery();
				
				while(rs2.next()){
					
					impuesto = new Impuesto();
					impuesto.setCod_imp(rs2.getString(1));
					impuesto.setDescripcion(rs2.getString(2));
					impuesto.setPorcentaje(rs2.getDouble(3));
					impuesto.setActivo(rs2.getBoolean(4));
					impuesto.setFechaMod(rs2.getTimestamp(5));
					impuesto.setUsuarioMod(rs2.getString(6));
					impuesto.setOperacion(rs2.getString(7));
					
					
					rubro.setImpuesto(impuesto);
				}
				
				pstmt3.setString(1, codTipoRubro);
				pstmt3.setString(2, codEmp);
				
				ResultSet rs3 = pstmt3.executeQuery();
				
				while(rs3.next()){
					
					tipoRubro = new TipoRubro();
					tipoRubro.setCod_tipoRubro(rs3.getString(1));
					tipoRubro.setDescripcion(rs3.getString(2));
					tipoRubro.setFechaMod(rs3.getTimestamp(3));
					tipoRubro.setUsuarioMod(rs3.getString(4));
					tipoRubro.setOperacion(rs3.getString(5));
					tipoRubro.setActivo(rs3.getBoolean(6));
					tipoRubro.setCod_emp(rs3.getString(7));
					
					rubro.setTipoRubro(tipoRubro);
				}
				
				lstRubros.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubros;
	}
	
	
	@Override
	public ArrayList<RubroCuenta> getRubrosCuentasActivos(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<RubroCuenta> lstRubros = new ArrayList<RubroCuenta>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.rubroCuenta();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			RubroCuenta rubro;
			
			while(rs.next ()) {

				rubro = new RubroCuenta();
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcionRubro(rs.getString(2));
				rubro.setCod_cuenta(rs.getString(3));
				rubro.setDescripcionCuenta(rs.getString(4));
				rubro.setCod_impuesto(rs.getString(5));
				rubro.setDescripcionImpuesto(rs.getString(6));
				rubro.setPorcentaje(rs.getDouble(7));
				rubro.setOficina(rs.getBoolean(8));
				rubro.setProceso(rs.getBoolean(9));
				rubro.setPersona(rs.getBoolean(10));
				rubro.setCod_tipoRubro(rs.getString(11));
				rubro.setDescripcionTipoRubro(rs.getString(12));
				rubro.setFacturable(rs.getBoolean(13));
				
				lstRubros.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubros;
	}
	
	@Override
	public ArrayList<RubroCuenta> getRubrosCuentasActivosFacturable(String codEmp, Connection con) throws ObteniendoRubrosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<RubroCuenta> lstRubros = new ArrayList<RubroCuenta>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.rubroCuentaActivosFacturable();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			RubroCuenta rubro;
			
			while(rs.next ()) {

				rubro = new RubroCuenta();
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcionRubro(rs.getString(2));
				rubro.setCod_cuenta(rs.getString(3));
				rubro.setDescripcionCuenta(rs.getString(4));
				rubro.setCod_impuesto(rs.getString(5));
				rubro.setDescripcionImpuesto(rs.getString(6));
				rubro.setPorcentaje(rs.getDouble(7));
				rubro.setOficina(rs.getBoolean(8));
				rubro.setProceso(rs.getBoolean(9));
				rubro.setPersona(rs.getBoolean(10));
				rubro.setCod_tipoRubro(rs.getString(11));
				rubro.setDescripcionTipoRubro(rs.getString(12));
				rubro.setFacturable(rs.getBoolean(13));
				
				lstRubros.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubros;
	}
	
	
	@Override
	/**
	 * Inserta un rubro en la base
	 * Pre condición: El código de rubro no debe existir previamente
	 */
	public void insertarRubro(Rubro rubro, String cod_emp, Connection con) throws InsertandoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarRubro();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, rubro.getCod_rubro());
			pstmt1.setString(2, rubro.getDescripcion());
			pstmt1.setBoolean(3, rubro.isActivo());
			pstmt1.setString(4, rubro.getUsuarioMod());
			pstmt1.setString(5, rubro.getOperacion());
			pstmt1.setString(6, rubro.getImpuesto().getCod_imp());
			pstmt1.setString(7,  rubro.getTipoRubro().getCod_tipoRubro());
			pstmt1.setString(8,  cod_emp);
			pstmt1.setBoolean(9, rubro.isFacturable());
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoRubroException();
		} 
		
	}

	@Override
	/**
	 * Dado el codigo de rubro, valida si existe
	 */
	public boolean memberRubro(String cod_rubro, String cod_emp, Connection con) throws ExisteRubroException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberRubro();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_rubro);
			pstmt1.setString(2, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteRubroException();
		}
	}
	
	/**
	 * Actualizamos el rubro dado el codigo,
	 * PRECONDICION: El código del rubro debe existir
	 */
	@Override
	public void actualizarRubro(Rubro rubro, String cod_emp, Connection con) throws ModificandoRubroException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarRubro();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, rubro.getDescripcion());
			pstmt1.setBoolean(2, rubro.isActivo());
			pstmt1.setString(3, rubro.getUsuarioMod());
			pstmt1.setString(4, rubro.getOperacion());
			pstmt1.setString(5, rubro.getImpuesto().getCod_imp());
			pstmt1.setString(6, rubro.getTipoRubro().getCod_tipoRubro());
			pstmt1.setBoolean(7, rubro.isFacturable());
			pstmt1.setString(8, rubro.getCod_rubro());
			pstmt1.setString(9, cod_emp);
			
			
			
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoRubroException();
		}
		
	}
	

}
