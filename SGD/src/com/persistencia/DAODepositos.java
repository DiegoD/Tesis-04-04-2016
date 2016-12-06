package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Depositos.EliminandoDepositoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.logica.Cheque;
import com.logica.FuncionarioInfo;
import com.logica.MonedaInfo;
import com.logica.Depositos.Deposito;
import com.logica.Depositos.DepositoDetalle;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.TitularInfo;

public class DAODepositos implements IDAODepositos{
	
	
	/**
	 * Nos retorna una lista con todos los ingresos de cobro del sistema para la emrpesa
	 */
	public ArrayList<Deposito> getDepositosTodos(Connection con, String codEmp) throws ObteniendoDepositoException, ConexionException {
		
		ArrayList<Deposito> lst = new ArrayList<Deposito>();
	
		try {
			//
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getCabezalDeposito();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Deposito aux;
			
			while(rs.next ()) {
							
				aux = new Deposito();
				
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
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
				
				FuncionarioInfo funcionario = new FuncionarioInfo();
				funcionario.setCodigo(rs.getInt("cod_tit"));
				funcionario.setNombre(rs.getString("nom_tit"));
				aux.setFuncionario(funcionario);
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setObservaciones(rs.getString("observaciones"));
				aux.setNroTrans(rs.getLong("nro_trans"));
				
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				
				
				/*Obtenemos las lineas de la transaccion*/				
				aux.setLstDetalle(this.getDepositoLineaxTrans(con,aux, codEmp));
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoDepositoException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Nos retorna una lista con todas las lineas del cobro, pasandole el cobro
	 */
	public ArrayList<DepositoDetalle> getDepositoLineaxTrans(Connection con, Deposito deposito, String codEmp) throws ObteniendoDepositoException, ConexionException {
		
		ArrayList<DepositoDetalle> lst = new ArrayList<DepositoDetalle>();
		Integer codigo;
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getDetalleDeposito();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setLong(2, deposito.getNroTrans());
			rs = pstmt1.executeQuery();
			
			DepositoDetalle aux;
			
			while(rs.next ()) {
				
				
				aux = new DepositoDetalle();
				
				Cheque cheque = new Cheque();
				
				cheque.setCodDocum(rs.getString("cod_docum"));
				cheque.setSerieDocum(rs.getString("serie_docum"));
				cheque.setNroDocum(rs.getInt("nro_docum"));
				cheque.setFecDoc(rs.getTimestamp("fec_doc"));
				cheque.setFecValor(rs.getTimestamp("fec_valor"));
				
				cheque.setImpTotMn(rs.getDouble("imp_tot_mn"));
				cheque.setImpTotMo(rs.getDouble("imp_tot_mo"));
				cheque.setNroTrans(rs.getLong("nro_trans"));
				
				
				BancoInfo banco = new BancoInfo();
				banco.setCodBanco(rs.getString("cod_bco"));
				banco.setNomBanco(rs.getString("nom_bco"));
				cheque.setBanco(banco);
				
				CuentaBcoInfo cuentaBanco = new CuentaBcoInfo();
				cuentaBanco.setCodCuenta(rs.getString("cod_ctabco"));
				cuentaBanco.setNomCuenta(rs.getString("nom_cta"));
				cuentaBanco.setCodMoneda(rs.getString("cod_moneda"));
				cuentaBanco.setNacional(rs.getBoolean("nacional"));
				cheque.setCuentaBanco(cuentaBanco);
				
				
				MonedaInfo moneda = new MonedaInfo();
				moneda.setCodMoneda(rs.getString("cod_moneda"));
				moneda.setDescripcion(rs.getString("descripcion"));
				moneda.setSimbolo(rs.getString("simbolo"));
				moneda.setNacional(rs.getBoolean("nacional"));
				cheque.setMoneda(moneda);
				
				TitularInfo titInfo = new TitularInfo();
				
				codigo = rs.getInt("cod_tit");
				titInfo.setCodigo(codigo.toString());
				cheque.setTitInfo(titInfo);
				cheque.setTcMov(rs.getDouble("tc_mov"));
				aux.setCheque(cheque);
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoDepositoException();
			
		}
    	
    	return lst;
	}

	/**
	 * Inserta un cabezal de depósito
	 * 
	 */
	public void insertarDeposito(Deposito deposito, Connection con, String codEmp) throws InsertandoDepositoException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarCabezalDeposito();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, deposito.getCodDocum());
			pstmt1.setString(2, deposito.getSerieDocum());
			pstmt1.setInt(3, deposito.getNroDocum());
			pstmt1.setString(4, codEmp);
			pstmt1.setTimestamp(5, deposito.getFecDoc());
			pstmt1.setTimestamp(6, deposito.getFecValor());
			pstmt1.setString(7, deposito.getBanco().getCodBanco());
			pstmt1.setString(8, deposito.getCuentaBanco().getCodCuenta());
			pstmt1.setString(9, deposito.getMoneda().getCodMoneda());
			pstmt1.setDouble(10, deposito.getImpTotMn());
			pstmt1.setDouble(11, deposito.getImpTotMo());
			pstmt1.setDouble(12, deposito.getTcMov());
			pstmt1.setLong(13, deposito.getNroTrans());
			pstmt1.setString(14, deposito.getUsuarioMod());
			pstmt1.setString(15, deposito.getOperacion());
			pstmt1.setString(16, deposito.getObservaciones());
			pstmt1.setInt(17, deposito.getFuncionario().getCodigo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
//			int linea = 1;
//			for (DepositoDetalle lin : deposito.getLstDetalle()) {
//				
//			
//				/*A cada linea le seteamos el nroTrans*/
//				lin.setNroTrans(deposito.getNroTrans());
//				
//				this.insertarDepositoDetalle(lin, linea, con, codEmp);
//				
//				linea++;
//			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoDepositoException();
		} 
	}

	/**
	 * Inserta detalle del depósito
	 * 
	 */
	public void insertarDepositoDetalle(DepositoDetalle detalle, int linea, Connection con, String codEmp) throws InsertandoDepositoException, ConexionException {

		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarDetalleDeposito();
    	String codigo;
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(insert);
    		
			pstmt1.setString(1, detalle.getCheque().getCodDocum());
			pstmt1.setString(2, detalle.getCheque().getSerieDocum());
			pstmt1.setInt(3, detalle.getCheque().getNroDocum());
			pstmt1.setString(4, detalle.getCheque().getCodCuentaInd());
			pstmt1.setTimestamp(5, detalle.getCheque().getFecDoc());
			pstmt1.setTimestamp(6, detalle.getCheque().getFecValor());
			pstmt1.setString(7, detalle.getCheque().getMoneda().getCodMoneda());
			pstmt1.setDouble(8, detalle.getCheque().getImpTotMn());
			pstmt1.setDouble(9, detalle.getCheque().getImpTotMo());
			pstmt1.setLong(10, detalle.getNroTrans());
			pstmt1.setInt(11,linea);
			pstmt1.setString(12,codEmp);
			pstmt1.setString(13, detalle.getCheque().getBanco().getCodBanco());
			pstmt1.setString(14, detalle.getCheque().getCuentaBanco().getCodCuenta());
			codigo = detalle.getCheque().getTitInfo().getCodigo();
			pstmt1.setInt(15, Integer.parseInt(codigo));
			pstmt1.setDouble(16, detalle.getCheque().getTcMov());
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoDepositoException();
		} 
		
	}
	
	/**
	 * Dado el nro Valida si existe
	 */
	public boolean memberDeposito(Long nroTrans, String codEmp, Connection con) throws ExisteDepositoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.memberDeposito();
			
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
			
			throw new ExisteDepositoException();
		}
	}
	
	public void eliminarDeposito(Deposito deposito, Connection con, String codEmp) throws EliminandoDepositoException, ConexionException{
		
		try{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.eliminarDeposito();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, deposito.getNroTrans());
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
			
			//this.eliminarDepositoDetalle(deposito, con, codEmp);
			
		}
		catch(SQLException e){
			
			throw new EliminandoDepositoException();
		}
	}
	
	public void eliminarDepositoDetalle(Deposito deposito, Connection con, String codEmp) throws EliminandoDepositoException, ConexionException{
		
		try{
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.eliminarDepositoDetalle();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, deposito.getNroTrans());
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
		}
		catch(SQLException e){
			
			throw new EliminandoDepositoException();
		}
	}
	
}
