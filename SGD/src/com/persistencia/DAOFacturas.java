package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Factura.*;
import com.logica.MonedaInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.Factura;
import com.logica.Docum.FacturaDetalle;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;

public class DAOFacturas implements IDAOFacturas{
  
	/**
	 * Nos retorna una lista con todos las facturas del sistema para la emrpesa
	 */
	public ArrayList<Factura> getFacturaTodos(Connection con, String codEmp, java.sql.Timestamp inicio,	java.sql.Timestamp fin) throws ObteniendoFacturasException, ConexionException  {
		
		ArrayList<Factura> lst = new ArrayList<Factura>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getFacturaCabTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
	    	
			rs = pstmt1.executeQuery();
			
			Factura fact;
			
			while(rs.next ()) {
							
				fact = new Factura();
				
				fact.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				fact.setCuenta(new CuentaInfo(fact.getCodCuentaInd(), "Ingreso Cobro"));
				
				fact.getProcesoInfo().setCodProceso(rs.getInt("cod_proceso"));
				fact.getProcesoInfo().setDescProceso(rs.getString("cod_proceso"));
				fact.setFecDoc(rs.getTimestamp("fec_doc"));
				fact.setCodDocum(rs.getString("cod_docum"));
				fact.setSerieDocum(rs.getString("serie_docum"));
				fact.setNroDocum(rs.getInt("nro_docum"));
				fact.setCodEmp(rs.getString("cod_emp"));
				
				fact.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
				
				fact.setReferencia(rs.getString("observaciones"));
				fact.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit"), rs.getString("tipo")));
				fact.setNroTrans(rs.getLong("nro_trans"));
			
				//aux.setCuentaInfo(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				
				fact.setFecValor(rs.getTimestamp("fec_valor"));
				
				fact.setImpTotMn(rs.getDouble("imp_tot_mn"));
				fact.setImpTotMo(rs.getDouble("imp_tot_mo"));
				fact.setTcMov(rs.getDouble("tc_mov"));
				
				fact.setFechaMod(rs.getTimestamp("fecha_mod"));
				fact.setUsuarioMod(rs.getString("usuario_mod"));
				fact.setOperacion(rs.getString("operacion"));
				
				fact.setImpuTotMn(rs.getDouble("impu_tot_mn"));
				fact.setImpuTotMo(rs.getDouble("impu_tot_mo"));
				fact.setImpSubMo(rs.getDouble("imp_sub_mo"));
				fact.setImpSubMn(rs.getDouble("imp_sub_mn"));
				fact.setTipoFactura(rs.getString("tipo_factura"));
				fact.setTipoContCred(rs.getString("tipoContCred"));
				
				/*Obtenemos las lineas de la transaccion*/				
				fact.setDetalle(this.getEgresoFacturaLineaxTrans(con,fact));
				
				
				lst.add(fact);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoFacturasException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Nos retorna una lista con todos las facturas con saldo para titular, empresa y moneda
	 */
	public ArrayList<Factura> getFacturaConSaldoxMoneda(Connection con, String codEmp, String codMoneda, String codTit) throws ObteniendoFacturasException, ConexionException {
		
		ArrayList<Factura> lst = new ArrayList<Factura>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getFacturaConSaldoxMoneda();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codMoneda);
	    	pstmt1.setString(3, codTit);
			rs = pstmt1.executeQuery();
			
			Factura fact;
			
			while(rs.next ()) {
							
				fact = new Factura();
				
				fact.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				fact.setCuenta(new CuentaInfo(fact.getCodCuentaInd(), "Ingreso Cobro"));
				
				fact.getProcesoInfo().setCodProceso(rs.getInt("cod_proceso"));
				fact.getProcesoInfo().setDescProceso(rs.getString("cod_proceso"));
				fact.setFecDoc(rs.getTimestamp("fec_doc"));
				fact.setCodDocum(rs.getString("cod_docum"));
				fact.setSerieDocum(rs.getString("serie_docum"));
				fact.setNroDocum(rs.getInt("nro_docum"));
				fact.setCodEmp(rs.getString("cod_emp"));
				
				fact.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo"), rs.getBoolean("nacional")));
				
				fact.setReferencia(rs.getString("observaciones"));
				fact.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit"), rs.getString("tipo")));
				fact.setNroTrans(rs.getLong("nro_trans"));
			
				//aux.setCuentaInfo(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				
				fact.setFecValor(rs.getTimestamp("fec_valor"));
				
				fact.setImpTotMn(rs.getDouble("imp_tot_mn"));
				fact.setImpTotMo(rs.getDouble("imp_tot_mo"));
				fact.setTcMov(rs.getDouble("tc_mov"));
				
				fact.setFechaMod(rs.getTimestamp("fecha_mod"));
				fact.setUsuarioMod(rs.getString("usuario_mod"));
				fact.setOperacion(rs.getString("operacion"));
				
				fact.setImpuTotMn(rs.getDouble("impu_tot_mn"));
				fact.setImpuTotMo(rs.getDouble("impu_tot_mo"));
				fact.setImpSubMo(rs.getDouble("imp_sub_mo"));
				fact.setImpSubMn(rs.getDouble("imp_sub_mn"));
				fact.setTipoFactura(rs.getString("tipo_factura"));
				fact.setTipoContCred(rs.getString("tipoContCred"));
				
				/*Obtenemos las lineas de la transaccion*/				
				fact.setDetalle(this.getEgresoFacturaLineaxTrans(con,fact));
				
				
				lst.add(fact);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoFacturasException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Nos retorna una lista con todos las facturas con saldo para titular, empresa y moneda
	 */
	public ArrayList<Factura> getFacturaxProceso(Connection con, String codEmp, Integer codProceso) throws ObteniendoFacturasException, ConexionException {
		
		ArrayList<Factura> lst = new ArrayList<Factura>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getFacturaxProceso();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setInt(2, codProceso);
			rs = pstmt1.executeQuery();
			
			Factura fact;
			
			while(rs.next ()) {
							
				fact = new Factura();
				
				fact.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				fact.setCuenta(new CuentaInfo(fact.getCodCuentaInd(), "Ingreso Cobro"));
				
				fact.getProcesoInfo().setCodProceso(rs.getInt("cod_proceso"));
				fact.getProcesoInfo().setDescProceso(rs.getString("cod_proceso"));
				fact.setFecDoc(rs.getTimestamp("fec_doc"));
				fact.setCodDocum(rs.getString("cod_docum"));
				fact.setSerieDocum(rs.getString("serie_docum"));
				fact.setNroDocum(rs.getInt("nro_docum"));
				fact.setCodEmp(rs.getString("cod_emp"));
				
				fact.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo"), rs.getBoolean("nacional")));
				
				fact.setReferencia(rs.getString("observaciones"));
				fact.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit"), rs.getString("tipo")));
				fact.setNroTrans(rs.getLong("nro_trans"));
			
				//aux.setCuentaInfo(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				
				fact.setFecValor(rs.getTimestamp("fec_valor"));
				
				fact.setImpTotMn(rs.getDouble("imp_tot_mn"));
				fact.setImpTotMo(rs.getDouble("imp_tot_mo"));
				fact.setTcMov(rs.getDouble("tc_mov"));
				
				fact.setFechaMod(rs.getTimestamp("fecha_mod"));
				fact.setUsuarioMod(rs.getString("usuario_mod"));
				fact.setOperacion(rs.getString("operacion"));
				
				fact.setImpuTotMn(rs.getDouble("impu_tot_mn"));
				fact.setImpuTotMo(rs.getDouble("impu_tot_mo"));
				fact.setImpSubMo(rs.getDouble("imp_sub_mo"));
				fact.setImpSubMn(rs.getDouble("imp_sub_mn"));
				fact.setTipoFactura(rs.getString("tipo_factura"));
				fact.setTipoContCred(rs.getString("tipoContCred"));
				
				/*Obtenemos las lineas de la transaccion*/				
				fact.setDetalle(this.getEgresoFacturaLineaxTrans(con,fact));
				
				
				lst.add(fact);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoFacturasException();
			
		}
    	
    	return lst;
	}

	
	/**
	 * Dado el nro, serie y codigo Valida si existe
	 */
	public boolean memberFacturas(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ExisteFacturaException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberFactura();
			
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
			
			throw new ExisteFacturaException();
		}
	}
	
	public boolean existeGasto(int nroDocum, String codEmp, Connection con) throws ExisteFacturaException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.existeGasto();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, nroDocum);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteFacturaException();
		}
	}

	/**
	 * Inserta un cabezal ingreso cobro
	 * 
	 */
	public void insertarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertFacturaCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert); 
			
			pstmt1.setString(1, factura.getCodDocum());
			pstmt1.setString(2, factura.getSerieDocum());
			pstmt1.setInt(3, factura.getNroDocum());
			pstmt1.setString(4, factura.getTitInfo().getCodigo());
			pstmt1.setString(5, factura.getCuenta().getCodCuenta());
			pstmt1.setString(6, factura.getCodEmp());
			pstmt1.setTimestamp(7, factura.getFecDoc());
			pstmt1.setTimestamp(8, factura.getFecValor());
			pstmt1.setString(9, factura.getMoneda().getCodMoneda());
			pstmt1.setDouble(10, factura.getImpTotMn());
			pstmt1.setDouble(11, factura.getImpTotMo());
			pstmt1.setDouble(12, factura.getTcMov());
			pstmt1.setString(13, factura.getReferencia());
			pstmt1.setLong(14, factura.getNroTrans());
			pstmt1.setString(15, factura.getUsuarioMod());
			pstmt1.setString(16, factura.getOperacion());
			pstmt1.setInt(17, factura.getProcesoInfo().getCodProceso());
			
			
			pstmt1.setDouble(18, factura.getImpuTotMn());
			pstmt1.setDouble(19, factura.getImpuTotMo());
			pstmt1.setDouble(20, factura.getImpSubMo());
			pstmt1.setDouble(21, factura.getImpSubMn());
			pstmt1.setString(22, factura.getTipoFactura());
			pstmt1.setString(23, factura.getTipoContCred());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			int linea = 1;
			for (FacturaDetalle lin : factura.getDetalle()) {
				
				/*A cada linea le seteamos el nroTrans*/
				lin.setNroTrans(factura.getNroTrans());
				
				this.insertarLineaFactura(lin, linea, con);
				
				linea++;
			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoFacturaException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el cabezal de un cobro
	 * @throws EliminandoFacturaException 
	 *
	 */
	public void eliminarFactura(Factura factura, Connection con) throws InsertandoFacturaException, ConexionException, EliminandoFacturaException {

		Consultas clts = new Consultas();
    	String delete = clts.eliminarFacturaCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, factura.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.eliminarFacturaDetalle(factura, con);
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoFacturaException();
		} 
		
    	
	}
	
	/**
	 * Eliminamos el detalle de un cobro
	 *
	 */
	private void eliminarFacturaDetalle(Factura factura, Connection con) throws  ConexionException, EliminandoFacturaException {

		Consultas clts = new Consultas();
    	String delete = clts.deleteFacturaCabDet();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, factura.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoFacturaException();
		} 
	}
	
	/**
	 * Inserta una linea de factura
	 * 
	 */
	private void insertarLineaFactura(FacturaDetalle lin, int linea, Connection con) throws InsertandoFacturaException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertFacturaCabDet();
    	
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
			throw new InsertandoFacturaException();
		} 
		
	}
	
/////////////////////////////////////DETALLE/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todas las lineas de la factura, pasandole la factura
	 */
	private ArrayList<FacturaDetalle> getEgresoFacturaLineaxTrans(Connection con, Factura cab) throws ObteniendoFacturasException, ConexionException {
		
		ArrayList<FacturaDetalle> lst = new ArrayList<FacturaDetalle>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getFacturaDetxTrans();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setLong(1, cab.getNroTrans());
			rs = pstmt1.executeQuery();
			
			FacturaDetalle aux;
			
			while(rs.next ()) {
				
				
				aux = new FacturaDetalle();
				
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
			throw new ObteniendoFacturasException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Nos retorna una lista con las lineas de gastos asociados a la factura factura
	 */
	public ArrayList<DatosDocum> getGastosFacturaLinea(Connection con, int nroDocum, String serieDocum, String codDocum, String codEmp) throws ObteniendoFacturasException, ConexionException {
		
		ArrayList<DatosDocum> lst = new ArrayList<DatosDocum>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getGastosFactura();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setInt(1, nroDocum);
	    	pstmt1.setString(2, serieDocum);
	    	pstmt1.setString(3, codDocum);
	    	pstmt1.setString(4, codEmp);
	    	
	    	String s = pstmt1.toString();
	    	
			rs = pstmt1.executeQuery();
			
			DatosDocum aux;
			
			while(rs.next ()) {
				
				
				aux = new DatosDocum();
				
				aux.setCodCuentaInd(rs.getString("cod_cuenta"));
				
				/*El titular es el del Cabezal*/
				
				aux.setTitInfo(new TitularInfo()); /*Esta info no interesa para calcular el saldo*/
				
				aux.setCuenta(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				aux.setCodEmp(codEmp);
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("nom_moneda"), rs.getString("simbolo")));
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				
				aux.setTcMov(rs.getDouble("tc_mov"));
				
				aux.setReferencia(rs.getString("referencia"));
				aux.setNroTrans(rs.getInt("nro_trans"));
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setOperacion(rs.getString("operacion"));
				
				aux.setTitInfo(new TitularInfo(rs.getString("cod_tit"),rs.getString("nom_tit")));
				
				lst.add(aux);
				
			}
			
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoFacturasException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Dado el nro, serie , codigo y empresa, retorna su saldo
	 */
	public double getSaldoFactura(int nroDocum, String serie, String codigo, String codEmp, Connection con) throws ObteniendoSaldoException, ConexionException{
		
		double saldo = 0;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.getSaldoFactura();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, nroDocum);
			pstmt1.setString(2, serie);
			pstmt1.setString(3, codigo);
			pstmt1.setString(4, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				saldo =	rs.getDouble("imp_tot_mo");
				
			}
			
			
						
			rs.close ();
			pstmt1.close ();
			
			return saldo;
			
		}catch(SQLException e){
			
			throw new ObteniendoSaldoException();
		}
	}

	

	
}
