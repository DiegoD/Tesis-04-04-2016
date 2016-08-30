package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Cuentas.InsertandoCuentaException;
import com.excepciones.Cuentas.MemberCuentaException;
import com.excepciones.Cuentas.ModificandoCuentaException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Cuenta;
import com.logica.Formulario;
import com.logica.Grupo;
import com.logica.Rubro;

public class DAOCuentas implements IDAOCuentas{

	@Override
	public ArrayList<Cuenta> getCuentas(String codEmp, Connection con)
			throws ObteniendoCuentasException, ConexionException, ObteniendoRubrosException {
		// TODO Auto-generated method stub
		ArrayList<Cuenta> lstCuentas = new ArrayList<Cuenta>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getCuentas();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			Cuenta cuenta;
			
			while(rs.next ()) {

				cuenta = new Cuenta();

				cuenta.setCod_cuenta(rs.getString(1));
				cuenta.setDescripcion(rs.getString(2));
				cuenta.setFechaMod(rs.getTimestamp(3));
				cuenta.setUsuarioMod(rs.getString(4));
				cuenta.setOperacion(rs.getString(5));
				cuenta.setActivo(rs.getBoolean(6));
				
				/*Obtenemos los formularios del grupo*/
				cuenta.setLstRubros(this.getRubrosxCuenta(cuenta.getCod_cuenta(), codEmp, con));

				lstCuentas.add(cuenta);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoCuentasException();
		}
			
		return lstCuentas;
	}

	@Override
	public void insertarCuenta(Cuenta cuenta, String codEmp, Connection con)
			throws InsertandoCuentaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarCuenta();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, cuenta.getCod_cuenta());
			pstmt1.setString(2, cuenta.getDescripcion());
			pstmt1.setString(3, cuenta.getUsuarioMod());
			pstmt1.setString(4, cuenta.getOperacion());
			pstmt1.setBoolean(5, cuenta.isActivo());
			pstmt1.setString(6, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.insertarRubrosxCuenta(cuenta.getCod_cuenta(), codEmp, cuenta.getLstRubros(), con);
	
		} catch (SQLException e) {
			
			throw new InsertandoCuentaException();
		} 
		
	}

	@Override
	public boolean memberCuenta(String codCuenta, String codEmp, Connection con)
			throws MemberCuentaException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
						
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberCuenta();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codCuenta);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
						
			return existe;
			
		}catch(SQLException e){
			
			throw new MemberCuentaException();
		}
	}

	@Override
	public void eliminarCuenta(String codCuenta, String codEmp, Connection con)
			throws ModificandoCuentaException, ConexionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCuenta(Cuenta cuenta, String codEmp, Connection con) throws ModificandoCuentaException, ConexionException, InsertandoCuentaException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.getActualizarCuenta();
		
		PreparedStatement pstmt1;
		
		
		try 
		{
			/*Primero eliminamos los rubros para luego volver a insertarlos*/
			this.eliminarRubrosxCuenta(cuenta.getCod_cuenta(), codEmp, con);
			
			/*Volvemos a insertar los rubros modificados*/
			this.insertarRubrosxCuenta(cuenta.getCod_cuenta(), codEmp, cuenta.getLstRubros(), con);
			
			/*Updateamos la info del grupo*/
     		pstmt1 =  con.prepareStatement(update);
     		
     		
			pstmt1.setString(1, cuenta.getDescripcion());
			pstmt1.setString(2, cuenta.getUsuarioMod());
			pstmt1.setString(3, cuenta.getOperacion());
			pstmt1.setBoolean(4, cuenta.isActivo());
			pstmt1.setString(5, cuenta.getCod_cuenta());
			pstmt1.setString(6, codEmp);
			
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoCuentaException();
		}
		
	}

	@Override
	public Cuenta getCuenta(String codEmp, Connection con, String codCuenta)
			throws ObteniendoCuentasException, ObteniendoRubrosException {
		// TODO Auto-generated method stub
		Cuenta cuenta = null;
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getCuenta();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codCuenta);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			
			
			while(rs.next ()) {

				cuenta = new Cuenta();

				cuenta.setCod_cuenta(rs.getString(1));
				cuenta.setDescripcion(rs.getString(2));
				cuenta.setFechaMod(rs.getTimestamp(3));
				cuenta.setUsuarioMod(rs.getString(4));
				cuenta.setOperacion(rs.getString(5));
				cuenta.setActivo(rs.getBoolean(6));
				
				/*Obtenemos los rubros de la cuenta*/
				cuenta.setLstRubros(this.getRubrosxCuenta(cuenta.getCod_cuenta(), codEmp, con));
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoCuentasException();
		}
			
		return cuenta;
	}
	
	/**
	 * Nos retorna los rubros activos para la cuenta
	 *
	 */
	private ArrayList<Rubro> getRubrosxCuenta(String codCuenta, String cod_emp, Connection con) throws ObteniendoRubrosException
	{
		ArrayList<Rubro> lstRubro = new ArrayList<Rubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query =	consultas.getRubrosxCuenta();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codCuenta);
			pstmt1.setString(2, cod_emp);
			ResultSet rs = pstmt1.executeQuery();
			
			Rubro rubro;
			
			while(rs.next ()) {

				rubro = new Rubro();
				
				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcion(rs.getString(2));
				rubro.setOficina(rs.getBoolean(3));
				rubro.setProceso(rs.getBoolean(4));
				rubro.setPersona(rs.getBoolean(5));
				
				lstRubro.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubro;
	}
	
	/**
	 * Insertamos rubro dada cuenta,
	 * PRECONDICION: El código del rubro no debe existir
	 *
	 */
	private void insertarRubrosxCuenta(String codCuenta, String codEmp, ArrayList<Rubro> lstRubros, Connection con) throws InsertandoCuentaException, ConexionException 
	{

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarRubrosxCuenta();
    	
    	PreparedStatement pstmt1;
  	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
    		
    		for (Rubro rubro : lstRubros) {
				
    			pstmt1.setString(1, codCuenta);
    			pstmt1.setString(2, rubro.getCod_rubro());
    			
    			pstmt1.setBoolean(3, rubro.isOficina());
    			pstmt1.setBoolean(4, rubro.isProceso());
    			pstmt1.setBoolean(5, rubro.isPersona());
    			pstmt1.setString(6, codEmp);

    			pstmt1.executeUpdate ();
			}
    		
			pstmt1.close ();
	
		} catch (SQLException e) {
			
			throw new InsertandoCuentaException();
		} 
	}

	
	/**
	 * Eliminamos rubros de la cuenta dado el codigo del rubro,
	 * PRECONDICION: El código del  rubro debe existir
	 *
	 */
	private void eliminarRubrosxCuenta(String codRubro, String codEmp, Connection con) throws ModificandoCuentaException, ConexionException
	{
		ConsultasDD consultas = new ConsultasDD ();
		String delete = consultas.eliminarRubrosxCuenta();
		
		PreparedStatement pstmt1;
		
		try 
		{
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setString(1, codRubro);
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
			
		} catch (SQLException e) {
			
			throw new ModificandoCuentaException();
		}
	}
	
	
	/**
	 * Nos retorna los rubros que no pertenecen
	 * a la cuenta para que los pueda agregar
	 *
	 */
	public ArrayList<Rubro> getRubrosNoCuenta(String codCuenta, String codEmp, Connection con) throws ObteniendoRubrosException
	{
		ArrayList<Rubro> lstRubro = new ArrayList<Rubro>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.getRubrosNOCuenta();			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codCuenta);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			Rubro rubro;
			
			while(rs.next ()) {

				rubro = new Rubro();

				rubro.setCod_rubro(rs.getString(1));
				rubro.setDescripcion(rs.getString(2));
				
				lstRubro.add(rubro);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lstRubro;
	}

}
