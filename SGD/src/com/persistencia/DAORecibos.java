package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Factura.*;
import com.excepciones.Recibo.EliminandoReciboException;
import com.excepciones.Recibo.ExisteReciboException;
import com.excepciones.Recibo.InsertandoReciboException;
import com.excepciones.Recibo.ObteniendoReciboException;
import com.logica.MonedaInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.Factura;
import com.logica.Docum.FacturaDetalle;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.Recibo;
import com.logica.Docum.ReciboDetalle;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;
import com.logica.IngresoCobro.IngresoCobro;
import com.logica.IngresoCobro.IngresoCobroLinea;

public class DAORecibos implements IDAORecibos{
  
	/**
	 * Nos retorna una lista con todos las facturas del sistema para la emrpesa
	 */
	public ArrayList<Recibo> getReciboTodos(Connection con, String codEmp) throws ObteniendoReciboException, ConexionException {
		
		ArrayList<Recibo> lst = new ArrayList<Recibo>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getReciboCabTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Recibo recibo;
			
			while(rs.next ()) {
							
				recibo = new Recibo();
				
				recibo.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				recibo.setCuenta(new CuentaInfo(recibo.getCodCuentaInd(), "Ingreso Cobro"));
				
				recibo.getProcesoInfo().setCodProceso(rs.getInt("cod_proceso"));
				recibo.getProcesoInfo().setDescProceso(rs.getString("cod_proceso"));
				recibo.setFecDoc(rs.getTimestamp("fec_doc"));
				recibo.setCodDocum(rs.getString("cod_docum"));
				recibo.setSerieDocum(rs.getString("serie_docum"));
				recibo.setNroDocum(rs.getInt("nro_docum"));
				recibo.setCodEmp(rs.getString("cod_emp"));
				
				recibo.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
				
				recibo.setReferencia(rs.getString("observaciones"));
				recibo.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit"), rs.getString("tipo")));
				recibo.setNroTrans(rs.getLong("nro_trans"));
			
				recibo.setFecValor(rs.getTimestamp("fec_valor"));
				
				recibo.setImpTotMn(rs.getDouble("imp_tot_mn"));
				recibo.setImpTotMo(rs.getDouble("imp_tot_mo"));
				recibo.setTcMov(rs.getDouble("tc_mov"));
				
				recibo.setFechaMod(rs.getTimestamp("fecha_mod"));
				recibo.setUsuarioMod(rs.getString("usuario_mod"));
				recibo.setOperacion(rs.getString("operacion"));
				
				recibo.setImpuTotMn(rs.getDouble("impu_tot_mn"));
				recibo.setImpuTotMo(rs.getDouble("impu_tot_mo"));
				recibo.setImpSubMo(rs.getDouble("imp_sub_mo"));
				recibo.setImpSubMn(rs.getDouble("imp_sub_mn"));
				
				/*Obtenemos las lineas de la transaccion*/				
				recibo.setDetalle(this.getEgresoReciboLineaxTrans(con,recibo));
				
				
				lst.add(recibo);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoReciboException();
			
		}
    	
    	return lst;
	}
	

	
	/**
	 * Dado el nro, serie y codigo Valida si existe
	 */
	public boolean memberRecibos(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteReciboException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberRecibo();
			
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
			
			throw new ExisteReciboException();
		}
	}
	

	/**
	 * Inserta un cabezal ingreso cobro
	 * 
	 */
	public void insertarRecibo(Recibo recibo, Connection con) throws InsertandoReciboException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertFacturaCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert); 
			
			pstmt1.setString(1, recibo.getCodDocum());
			pstmt1.setString(2, recibo.getSerieDocum());
			pstmt1.setInt(3, recibo.getNroDocum());
			pstmt1.setString(4, recibo.getTitInfo().getCodigo());
			pstmt1.setString(5, recibo.getCuenta().getCodCuenta());
			pstmt1.setString(6, recibo.getCodEmp());
			pstmt1.setTimestamp(7, recibo.getFecDoc());
			pstmt1.setTimestamp(8, recibo.getFecValor());
			pstmt1.setString(9, recibo.getMoneda().getCodMoneda());
			pstmt1.setDouble(10, recibo.getImpTotMn());
			pstmt1.setDouble(11, recibo.getImpTotMo());
			pstmt1.setDouble(12, recibo.getTcMov());
			pstmt1.setString(13, recibo.getReferencia());
			pstmt1.setLong(14, recibo.getNroTrans());
			pstmt1.setString(15, recibo.getUsuarioMod());
			pstmt1.setString(16, recibo.getOperacion());
			pstmt1.setInt(17, recibo.getProcesoInfo().getCodProceso());
			
			
			pstmt1.setDouble(18, recibo.getImpuTotMn());
			pstmt1.setDouble(19, recibo.getImpuTotMo());
			pstmt1.setDouble(20, recibo.getImpSubMo());
			pstmt1.setDouble(21, recibo.getImpSubMn());
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			int linea = 1;
			for (ReciboDetalle lin : recibo.getDetalle()) {
				
				/*A cada linea le seteamos el nroTrans*/
				lin.setNroTrans(recibo.getNroTrans());
				
				this.insertarLineaRecibo(lin, linea, con);
				
				linea++;
			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoReciboException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el cabezal de un cobro
	 * @throws EliminandoFacturaException 
	 *
	 */
	public void eliminarRecibo(Recibo recibo, Connection con) throws InsertandoReciboException, ConexionException, EliminandoReciboException {

		Consultas clts = new Consultas();
    	String delete = clts.eliminarFacturaCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, recibo.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.eliminarReciboDetalle(recibo, con);
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoReciboException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el detalle de un cobro
	 *
	 */
	private void eliminarReciboDetalle(Recibo recibo, Connection con) throws  ConexionException, EliminandoReciboException {

		Consultas clts = new Consultas();
    	String delete = clts.deleteReciboCabDet();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, recibo.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoReciboException();
		} 
	}
	
	/**
	 * Inserta una linea de factura
	 * 
	 */
	private void insertarLineaRecibo(ReciboDetalle lin, int linea, Connection con) throws InsertandoReciboException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertReciboCabDet();
    	
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
			throw new InsertandoReciboException();
		} 
		
	}
	
/////////////////////////////////////DETALLE/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todas las lineas de la factura, pasandole la factura
	 */
	private ArrayList<ReciboDetalle> getEgresoReciboLineaxTrans(Connection con, Recibo cab) throws ObteniendoReciboException, ConexionException {
		
		ArrayList<ReciboDetalle> lst = new ArrayList<ReciboDetalle>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getReciboDetxTrans();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setLong(1, cab.getNroTrans());
			rs = pstmt1.executeQuery();
			
			ReciboDetalle aux;
			
			while(rs.next ()) {
				
				
				aux = new ReciboDetalle();
				
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
			throw new ObteniendoReciboException();
			
		}
    	
    	return lst;
	}
	

	
}
