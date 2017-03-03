package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;

import com.excepciones.NotaCredito.*;
import com.logica.MonedaInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.NotaCredito;
import com.logica.Docum.NotaCreditoDetalle;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;


public class DAONotaCredito implements IDAONotaCredito{
  
	/**
	 * Nos retorna una lista con todos las facturas del sistema para la emrpesa
	 */
	public ArrayList<NotaCredito> getNCTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoNotaCreditoException, ConexionException {
		
		ArrayList<NotaCredito> lst = new ArrayList<NotaCredito>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getNCTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
	    	
			rs = pstmt1.executeQuery();
			
			NotaCredito nc;
			
			while(rs.next ()) {
							
				nc = new NotaCredito();
				
				nc.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				nc.setCuenta(new CuentaInfo(nc.getCodCuentaInd(), "Ingreso Cobro"));
				
				nc.setFecDoc(rs.getTimestamp("fec_doc"));
				nc.setCodDocum(rs.getString("cod_docum"));
				nc.setSerieDocum(rs.getString("serie_docum"));
				nc.setNroDocum(rs.getInt("nro_docum"));
				nc.setCodEmp(rs.getString("cod_emp"));
				
				nc.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
				
				nc.setReferencia(rs.getString("observaciones"));
				nc.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit"), rs.getString("tipo")));
				nc.setNroTrans(rs.getLong("nro_trans"));
			
				nc.setFecValor(rs.getTimestamp("fec_valor"));
				
				nc.setImpTotMn(rs.getDouble("imp_tot_mn"));
				nc.setImpTotMo(rs.getDouble("imp_tot_mo"));
				nc.setTcMov(rs.getDouble("tc_mov"));
				
				nc.setFechaMod(rs.getTimestamp("fecha_mod"));
				nc.setUsuarioMod(rs.getString("usuario_mod"));
				nc.setOperacion(rs.getString("operacion"));
				
				nc.setImpuTotMn(rs.getDouble("impu_tot_mn"));
				nc.setImpuTotMo(rs.getDouble("impu_tot_mo"));
				nc.setImpSubMo(rs.getDouble("imp_sub_mo"));
				nc.setImpSubMn(rs.getDouble("imp_sub_mn"));
				
				
				/*Obtenemos las lineas de la transaccion*/				
				nc.setDetalle(this.getNotaCreditoLineaxTrans(con,nc));
				

				lst.add(nc);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoNotaCreditoException();
			
		}
    	
    	return lst;
	}
	

	
	/**
	 * Dado el nro, serie y codigo Valida si existe
	 */
	public boolean memberNC(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteNotaCreditoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberNC();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, nroDocum);
			pstmt1.setString(2, serie);
			pstmt1.setString(3, codigo);
			pstmt1.setString(4, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteNotaCreditoException();
		}
	}
	
	public boolean existeNCFactura(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteNotaCreditoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.existeNCFactura();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, nroDocum);
			pstmt1.setString(2, serie);
			pstmt1.setString(3, codigo);
			pstmt1.setString(4, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteNotaCreditoException();
		}
	}

	/**
	 * Inserta un cabezal ingreso cobro
	 * 
	 */
	public void insertarNC(NotaCredito nc, Connection con) throws InsertandoNotaCreditoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertNCCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert); 
			
			pstmt1.setString(1, nc.getCodDocum());
			pstmt1.setString(2, nc.getSerieDocum());
			pstmt1.setInt(3, nc.getNroDocum());
			pstmt1.setString(4, nc.getTitInfo().getCodigo());
			pstmt1.setString(5, nc.getCuenta().getCodCuenta());
			pstmt1.setString(6, nc.getCodEmp());
			pstmt1.setTimestamp(7, nc.getFecDoc());
			pstmt1.setTimestamp(8, nc.getFecValor());
			pstmt1.setString(9, nc.getMoneda().getCodMoneda());
			pstmt1.setDouble(10, nc.getImpTotMn());
			pstmt1.setDouble(11, nc.getImpTotMo());
			pstmt1.setDouble(12, nc.getTcMov());
			pstmt1.setString(13, nc.getReferencia());
			pstmt1.setLong(14, nc.getNroTrans());
			pstmt1.setString(15, nc.getUsuarioMod());
			pstmt1.setString(16, nc.getOperacion());
			pstmt1.setDouble(17, nc.getImpuTotMn());
			pstmt1.setDouble(18, nc.getImpuTotMo());
			pstmt1.setDouble(19, nc.getImpSubMo());
			pstmt1.setDouble(20, nc.getImpSubMn());
			
			String s = pstmt1.toString();
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			int linea = 1;
			for (NotaCreditoDetalle lin : nc.getDetalle()) {
				
				/*A cada linea le seteamos el nroTrans*/
				lin.setNroTrans(nc.getNroTrans());
				
				this.insertarLineaNC(lin, linea, con);
				
				linea++;
			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoNotaCreditoException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el cabezal de un cobro
	 * @throws EliminandoFacturaException 
	 *
	 */
	public void eliminarNC(NotaCredito nc, Connection con) throws InsertandoNotaCreditoException, ConexionException, EliminandoNotaCreditoException {

		Consultas clts = new Consultas();
    	String delete = clts.eliminarNCCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, nc.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.eliminarNCDetalle(nc, con);
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoNotaCreditoException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el detalle de un cobro
	 *
	 */
	private void eliminarNCDetalle(NotaCredito nc, Connection con) throws  ConexionException, EliminandoNotaCreditoException {

		Consultas clts = new Consultas();
    	String delete = clts.deleteNCCabDet();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, nc.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoNotaCreditoException();
		} 
	}
	
	/**
	 * Inserta una linea de factura
	 * 
	 */
	private void insertarLineaNC(NotaCreditoDetalle lin, int linea, Connection con) throws InsertandoNotaCreditoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertNCCabDet();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(insert);
    		
    	
    		
			pstmt1.setString(1, lin.getCuenta().getCodCuenta());
			pstmt1.setString(2, lin.getCodEmp());
			pstmt1.setString(3, lin.getCodDocum());
			pstmt1.setString(4, lin.getSerieDocum());
			pstmt1.setInt(5, lin.getNroDocum());
			pstmt1.setString(6, lin.getCodProceso());
			pstmt1.setString(7, lin.getRubroInfo().getCodRubro());
			pstmt1.setString(8, lin.getCuenta().getCodCuenta());
			pstmt1.setTimestamp(9, lin.getFecDoc());
			pstmt1.setTimestamp(10, lin.getFecValor());
			pstmt1.setString(11, lin.getMoneda().getCodMoneda());
			pstmt1.setString(12, lin.getImpuestoInfo().getCodImpuesto());
			pstmt1.setDouble(13, lin.getImpImpuMn());
			pstmt1.setDouble(14, lin.getImpImpuMo());
			pstmt1.setDouble(15, lin.getImpSubMn());
			pstmt1.setDouble(16, lin.getImpSubMo());
			pstmt1.setDouble(17, lin.getImpTotMn());
			pstmt1.setDouble(18, lin.getImpTotMo());
			pstmt1.setDouble(19, lin.getTcMov());
			pstmt1.setString(20, lin.getReferencia());
			pstmt1.setString(21, lin.getReferencia());
			pstmt1.setLong(22, lin.getNroTrans());
			pstmt1.setString(23, lin.getUsuarioMod());
			pstmt1.setString(24, lin.getOperacion());
			pstmt1.setInt(25,linea);
		
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoNotaCreditoException();
		} 
		
	}
	
/////////////////////////////////////DETALLE/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todas las lineas de la nc, pasandole la nc
	 */
	private ArrayList<NotaCreditoDetalle> getNotaCreditoLineaxTrans(Connection con, NotaCredito cab) throws ObteniendoNotaCreditoException, ConexionException {
		
		ArrayList<NotaCreditoDetalle> lst = new ArrayList<NotaCreditoDetalle>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getNCDetxTrans();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setLong(1, cab.getNroTrans());
			rs = pstmt1.executeQuery();
			
			NotaCreditoDetalle aux;
			
			while(rs.next ()) {
				
				
				aux = new NotaCreditoDetalle();
				
				/*Cuenta ind es el del cabezal (en la linea solo tenemos la cuenta)*/
				aux.setCodCuentaInd(cab.getCuenta().getCodCuenta());
				
				/*El titular es el del Cabezal*/
				
				aux.setTitInfo(new TitularInfo(cab.getTitInfo().getCodigo(), cab.getTitInfo().getNombre()) );
				
				
				aux.setCuenta(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				aux.setCodEmp(cab.getCodEmp());
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setCodProceso(rs.getString("cod_proceso"));
				aux.setDescProceso(rs.getString("nom_proceso"));
				
				aux.setRubroInfo(new RubroInfo(rs.getString("cod_rubro"), rs.getString("nom_rubro")));
				
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("nom_moneda"), rs.getString("simbolo")));
				aux.getMoneda().setNacional(rs.getBoolean("nacional"));
				
				aux.setImpuestoInfo(new ImpuestoInfo(rs.getString("cod_impuesto"), rs.getString("nom_impuesto"), rs.getDouble("porcentaje")));
				
				aux.setImpImpuMn(rs.getDouble("imp_impu_mn"));
				aux.setImpImpuMo(rs.getDouble("imp_impu_mo"));
				
				aux.setImpSubMn(rs.getDouble("imp_sub_mn"));
				aux.setImpSubMo(rs.getDouble("imp_sub_mo"));
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				
				aux.setTcMov(rs.getDouble("tc_mov"));
				
				aux.setReferencia(rs.getString("referencia"));
				aux.setNroTrans(rs.getInt("nro_trans"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setLinea(rs.getInt("linea"));
				aux.setEstadoGasto("0");
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoNotaCreditoException();
			
		}
    	
    	return lst;
	}
	

	
}
