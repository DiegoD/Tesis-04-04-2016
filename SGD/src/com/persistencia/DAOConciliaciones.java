package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.Conciliaciones.EliminandoConcialiacionException;
import com.excepciones.Conciliaciones.ExisteConciliacionException;
import com.excepciones.Conciliaciones.InsertandoConciliacionException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
import com.logica.Cheque;
import com.logica.MonedaInfo;
import com.logica.Conciliacion.Conciliacion;
import com.logica.Conciliacion.ConciliacionDetalle;
import com.logica.Depositos.DepositoDetalle;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.TitularInfo;

public class DAOConciliaciones implements IDAOConciliaciones{
	
	/**
	 * Nos retorna una lista con todos los movimientos del banco a depositar
	 */
	public ArrayList<ConciliacionDetalle> getMovimientosBanco(Connection con, String codEmp, String codBco, String codCta) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		ArrayList<ConciliacionDetalle> lstMovimientos = new ArrayList<ConciliacionDetalle>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getChequesEmitidos();
	    	String query2 = clts.getChequesRecibidos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	PreparedStatement pstmt2 = con.prepareStatement(query2);
	    	
	    	ResultSet rs;
	    	ResultSet rs2;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codBco);
	    	pstmt1.setString(3, codCta);
	    	
	    	pstmt2.setString(1, codEmp);
	    	pstmt2.setString(2, codBco);
	    	pstmt2.setString(3, codCta);
	    	
			rs = pstmt1.executeQuery();
			
			ConciliacionDetalle aux = null;
			
			while(rs.next ()) {
							
				aux = new ConciliacionDetalle();
				
				aux.setCod_docum(rs.getString("cod_docum"));
				aux.setSerie_docum(rs.getString("serie_docum"));
				aux.setNro_docum(rs.getInt("nro_docum"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setCod_emp(rs.getString("cod_emp"));
				aux.setDescripcion(rs.getString("referencia"));
				
				lstMovimientos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
			
			rs2 = pstmt2.executeQuery();
			while(rs2.next ()) {
				
				aux = new ConciliacionDetalle();
				
				aux.setCod_docum(rs2.getString("cod_docum"));
				aux.setSerie_docum(rs2.getString("serie_docum"));
				aux.setNro_docum(rs2.getInt("nro_docum"));
				aux.setFecValor(rs2.getTimestamp("fec_valor"));
				aux.setFecDoc(rs2.getTimestamp("fec_doc"));
				aux.setImpTotMn(rs2.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs2.getDouble("imp_tot_mo"));
				aux.setCod_emp(rs2.getString("cod_emp"));
				aux.setDescripcion(rs2.getString("referencia"));
				
				lstMovimientos.add(aux);
				
			}
			rs2.close ();
			pstmt2.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return lstMovimientos;
	}
	
	public ArrayList<ConciliacionDetalle> getMovimientosCajaMoneda(Connection con, String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		ArrayList<ConciliacionDetalle> lstMovimientos = new ArrayList<ConciliacionDetalle>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getMovimientosCajaMoneda();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codMoneda);
	    	
			rs = pstmt1.executeQuery();
			
			ConciliacionDetalle aux = null;
			
			while(rs.next ()) {
							
				aux = new ConciliacionDetalle();
				
				aux.setCod_docum(rs.getString("cod_docum"));
				aux.setSerie_docum(rs.getString("serie_docum"));
				aux.setNro_docum(rs.getInt("nro_docum"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setCod_emp(rs.getString("cod_emp"));
				aux.setDescripcion(rs.getString("referencia"));
				
				lstMovimientos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
			
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return lstMovimientos;
	}
	
	
	/**
	 * Nos retorna una lista con todos las conciliaciones del sistema para la emrpesa
	 */
	public ArrayList<Conciliacion> getConciliacionesTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoConciliacionException, ConexionException {
		
		ArrayList<Conciliacion> lst = new ArrayList<Conciliacion>();
	
		try {
			//
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getCabezalConciliacion();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
			rs = pstmt1.executeQuery();
			
			Conciliacion aux;
			
			while(rs.next ()) {
							
				aux = new Conciliacion();
				
				aux.setCod_docum(rs.getString("cod_docum"));
				aux.setSerie_docum(rs.getString("serie_docum"));
				aux.setNro_docum(rs.getInt("nro_docum"));
				aux.setCod_emp(rs.getString("cod_emp"));
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setCuenta(rs.getString("cuenta"));
				aux.setNroTrans(rs.getLong("nro_trans"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				aux.setObservaciones(rs.getString("observaciones"));
				
				
				BancoInfo banco = new BancoInfo();
				banco.setCodBanco(rs.getString("cod_bco"));
				banco.setNomBanco(rs.getString("nom_bco"));
				
				CuentaBcoInfo cuentaBanco = new CuentaBcoInfo();
				cuentaBanco.setCodCuenta(rs.getString("cod_ctabco"));
				cuentaBanco.setNomCuenta(rs.getString("nom_cta"));
				cuentaBanco.setCodMoneda(rs.getString("cod_moneda"));
				cuentaBanco.setNacional(rs.getBoolean("nacional"));
				
				
				aux.setBanco(banco);
				aux.setCuentaBanco(cuentaBanco);
				
				MonedaInfo moneda = new MonedaInfo();
				moneda.setCodMoneda(rs.getString("cod_moneda"));
				moneda.setDescripcion(rs.getString("descripcion"));
				moneda.setSimbolo(rs.getString("simbolo"));
				moneda.setNacional(rs.getBoolean("nacional"));
				aux.setMoneda(moneda);
				
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setTipo(rs.getString("tipo"));
				
				
				/*Obtenemos las lineas de la transaccion*/				
				aux.setLstDetalle(this.getConciliacionLineaxTrans(con,aux, codEmp));
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return lst;
	}

	/**
	 * Nos retorna una lista con todas las lineas de la conciliacion, pasandole la conicliacion
	 */
	public ArrayList<ConciliacionDetalle> getConciliacionLineaxTrans(Connection con, Conciliacion conciliacion, String codEmp) throws ObteniendoConciliacionException, ConexionException {
		
		ArrayList<ConciliacionDetalle> lst = new ArrayList<ConciliacionDetalle>();
		Integer codigo;
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getDetalleConiliacion();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setLong(2, conciliacion.getNroTrans());
			rs = pstmt1.executeQuery();
			
			ConciliacionDetalle aux;
			
			while(rs.next ()) {
				
				
				aux = new ConciliacionDetalle();
				
				aux.setCod_docum(rs.getString("cod_docum"));
				aux.setSerie_docum(rs.getString("serie_docum"));
				aux.setNro_docum(rs.getInt("nro_docum"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setNroTrans(rs.getLong("nro_trans"));
				aux.setCod_emp(rs.getString("cod_emp"));
				aux.setDescripcion(rs.getString("referencia"));
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Inserta un cabezal de conciliación
	 * 
	 */
	public void insertarConciliacion(Conciliacion conciliacion, Connection con, String codEmp) throws InsertandoConciliacionException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarCabezalConciliacion();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, conciliacion.getCod_docum());
			pstmt1.setString(2, conciliacion.getSerie_docum());
			pstmt1.setInt(3, conciliacion.getNro_docum());
			pstmt1.setString(4, codEmp);
			pstmt1.setDouble(5, conciliacion.getImpTotMn());
			pstmt1.setDouble(6, conciliacion.getImpTotMo());
			pstmt1.setLong(7, conciliacion.getNroTrans());
			pstmt1.setTimestamp(8, conciliacion.getFecDoc());
			pstmt1.setTimestamp(9, conciliacion.getFecValor());
			pstmt1.setString(10, conciliacion.getBanco().getCodBanco());
			pstmt1.setString(11, conciliacion.getCuentaBanco().getCodCuenta());
			pstmt1.setString(12, conciliacion.getMoneda().getCodMoneda());
			pstmt1.setString(13, conciliacion.getObservaciones());
			pstmt1.setString(14, conciliacion.getUsuarioMod());
			pstmt1.setString(15, conciliacion.getOperacion());
			pstmt1.setString(16, conciliacion.getTipo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoConciliacionException();
		} 
	}

	/**
	 * Inserta detalle de la conciliacion
	 * @throws InsertandoConciliacionException 
	 * 
	 */
	public void insertarConciliacionDetalle(ConciliacionDetalle detalle, int linea, Connection con, String codEmp) throws  ConexionException, InsertandoConciliacionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarDetalleConciliacion();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(insert);
    		
			pstmt1.setString(1, detalle.getCod_docum());
			pstmt1.setString(2, detalle.getSerie_docum());
			pstmt1.setInt(3, detalle.getNro_docum());
			pstmt1.setString(4, codEmp);
			pstmt1.setDouble(5, detalle.getImpTotMn());
			pstmt1.setDouble(6, detalle.getImpTotMo());
			pstmt1.setLong(7, detalle.getNroTrans());
			pstmt1.setTimestamp(8, detalle.getFecDoc());
			pstmt1.setTimestamp(9, detalle.getFecValor());
			pstmt1.setString(10, detalle.getDescripcion());
			pstmt1.setInt(11, linea);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoConciliacionException();
		} 
		
	}
	
	/**
	 * Dado el nro Valida si existe
	 */
	public boolean memberConciliacion(Long nroTrans, String codEmp, Connection con) throws ExisteConciliacionException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.memberConciliacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, nroTrans);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteConciliacionException();
		}
	}
	
	public void eliminarConciliacion(Conciliacion conciliacion, Connection con, String codEmp) throws EliminandoConcialiacionException, ConexionException{
		
		try{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.eliminarConciliacion();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, conciliacion.getNroTrans());
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
			
			//this.eliminarDepositoDetalle(deposito, con, codEmp);
			
		}
		catch(SQLException e){
			
			throw new EliminandoConcialiacionException();
		}
	}
	
	public void eliminarConciliacionDetalle(Conciliacion conciliacion, Connection con, String codEmp) throws EliminandoConcialiacionException, ConexionException{
		
		try{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.eliminarConciliacionDetalle();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, conciliacion.getNroTrans());
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		}
		catch(SQLException e){
			
			throw new EliminandoConcialiacionException();
		}
	}
	
	public void conciliarSaCuentas(ConciliacionDetalle conciliacion, Connection con, String codEmp, boolean conciliar) throws InsertandoConciliacionException, ConexionException{
		
		try{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.actualizarSaCuentasConciliado();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setBoolean(1, conciliar);
			pstmt1.setString(2, conciliacion.getCod_docum());
			pstmt1.setString(3, conciliacion.getSerie_docum());
			pstmt1.setInt(4, conciliacion.getNro_docum());
			pstmt1.setString(5, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		}
		catch(SQLException e){
			
			throw new InsertandoConciliacionException();
		}
	}
	
	public double getSaldoConciliadoMoneda(Connection con, String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		Double saldo_conciliado = (double) 0;
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getSaldoConciliadoMoneda();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codMoneda);
	    	
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				saldo_conciliado = rs.getDouble("sum(imp_tot_mo)");	
			}
			rs.close ();
			pstmt1.close ();
			
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return saldo_conciliado;
	}
	
	public double getSaldoConciliadoCuentaBanco(Connection con, String codBco, String codCta, String codEmp) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		Double saldo_conciliado = (double) 0;
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getSaldoConciliadoCuentaBanco();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codBco);
	    	pstmt1.setString(2, codCta);
	    	pstmt1.setString(3, codEmp);
	    	
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				saldo_conciliado = rs.getDouble("sum(imp_tot_mo)");	
			}
			rs.close ();
			pstmt1.close ();
			
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoConciliacionException();
			
		}
    	
    	return saldo_conciliado;
	}
	
}
