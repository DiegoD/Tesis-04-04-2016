package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.IngresoCobros.EliminandoIngresoCobroException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.MonedaInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;
import com.logica.IngresoCobro.IngresoCobro;
import com.logica.IngresoCobro.IngresoCobroLinea;

public class DAOIngresoCobro implements IDAOIngresoCobro{
  
	/**
	 * Nos retorna una lista con todos los ingresos de cobro del sistema para la emrpesa
	 */
	public ArrayList<IngresoCobro> getIngresoCobroTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoIngresoCobroException, ConexionException {
		
		ArrayList<IngresoCobro> lst = new ArrayList<IngresoCobro>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getIngresoCobroCabTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
			rs = pstmt1.executeQuery();
			
			IngresoCobro aux;
			
			while(rs.next ()) {
							
				aux = new IngresoCobro();
				
				aux.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				aux.setCuenta(new CuentaInfo(aux.getCodCuentaInd(), "Ingreso Cobro"));
				
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setCodEmp(rs.getString("cod_emp"));
				
				aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
				
				aux.setReferencia(rs.getString("observaciones"));
				aux.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit")));
				aux.setNroTrans(rs.getLong("nro_trans"));
				aux.setmPago(rs.getString("cod_mpago"));
				aux.setCodDocRef(rs.getString("cod_doc_ref"));
				aux.setSerieDocRef(rs.getString("serie_doc_ref"));
				aux.setNroDocRef(rs.getInt("nro_doc_ref"));
				
				aux.setBancoInfo(new BancoInfo(rs.getString("cod_bco"), rs.getString("nom_bco")));
				
				aux.setCuentaBcoInfo(new CuentaBcoInfo(rs.getString("cod_ctabco"), rs.getString("nom_cta")));
				
				//aux.setCuentaInfo(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setTcMov(rs.getDouble("tc_mov"));
				
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				
				
				
				/*Obtenemos las lineas de la transaccion*/				
				aux.setDetalle(this.getIngresoCobroLineaxTrans(con,aux));
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoIngresoCobroException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Nos retorna una lista con todos los ingresos de cobro del sistema para la emrpesa
	 */
	public ArrayList<IngresoCobro> getIngresoCobroTodosOtro(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoIngresoCobroException, ConexionException {
		
		ArrayList<IngresoCobro> lst = new ArrayList<IngresoCobro>();
	
		try {
			//
	    	Consultas clts = new Consultas();
	    	String query = clts.getIngresoCobroCabTodosOtros(); 
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
	    	
			rs = pstmt1.executeQuery();
			
			IngresoCobro aux;
			
			while(rs.next ()) {
							
				aux = new IngresoCobro();
				
				aux.setCodCuentaInd(rs.getString("cod_cuenta")); /*El cabezal de ingreso cobro solamente tiene la cuenta interna*/
				
				aux.setCuenta(new CuentaInfo(aux.getCodCuentaInd(), "Ingreso Cobro"));
				
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setCodEmp(rs.getString("cod_emp"));
				
				aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
				
				aux.setReferencia(rs.getString("observaciones"));
				aux.setTitInfo(new TitularInfo(rs.getString("cod_tit"), rs.getString("nom_tit")));
				aux.setNroTrans(rs.getLong("nro_trans"));
				aux.setmPago(rs.getString("cod_mpago"));
				aux.setCodDocRef(rs.getString("cod_doc_ref"));
				aux.setSerieDocRef(rs.getString("serie_doc_ref"));
				aux.setNroDocRef(rs.getInt("nro_doc_ref"));
				
				aux.setBancoInfo(new BancoInfo(rs.getString("cod_bco"), rs.getString("nom_bco")));
				
				aux.setCuentaBcoInfo(new CuentaBcoInfo(rs.getString("cod_ctabco"), rs.getString("nom_cta")));
				
				
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setTcMov(rs.getDouble("tc_mov"));
				
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				
				
				/*Obtenemos las lineas de la transaccion*/				
				aux.setDetalle(this.getIngresoCobroLineaxTrans(con,aux));
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		
		catch (SQLException e) {
			throw new ObteniendoIngresoCobroException();
			
		}
    	
    	return lst;
	}
	
	/**
	 * Dado el nro Valida si existe
	 */
	public boolean memberIngresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteIngresoCobroException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberIngresoCobro();
			
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
			
			throw new ExisteIngresoCobroException();
		}
	}
	

	public boolean existeGastoIngresoCobro(int nroDocum, String codEmp, Connection con) throws ExisteIngresoCobroException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.existeGastoIngresoCobro();
			
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
			
			throw new ExisteIngresoCobroException();
		}
	}


	/**
	 * Inserta un cabezal ingreso cobro
	 * 
	 */
	public void insertarIngresoCobro(IngresoCobro cobro, Connection con) throws InsertandoIngresoCobroException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertIngresoCobroCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, cobro.getCodDocum());
			pstmt1.setString(2, cobro.getSerieDocum());
			pstmt1.setInt(3, cobro.getNroDocum());
			pstmt1.setString(4, cobro.getTitInfo().getCodigo());
			pstmt1.setString(5, cobro.getCuenta().getCodCuenta());
			pstmt1.setString(6, cobro.getCodEmp());
			pstmt1.setTimestamp(7, cobro.getFecDoc());
			pstmt1.setTimestamp(8, cobro.getFecValor());
			pstmt1.setString(9, cobro.getBancoInfo().getCodBanco());
			pstmt1.setString(10, cobro.getCuentaBcoInfo().getCodCuenta());
			pstmt1.setString(11, cobro.getmPago());
			pstmt1.setString(12, cobro.getCodDocRef());
			pstmt1.setString(13, cobro.getSerieDocRef());
			pstmt1.setInt(14, cobro.getNroDocRef());
			pstmt1.setString(15, cobro.getMoneda().getCodMoneda());
			pstmt1.setDouble(16, cobro.getImpTotMn());
			pstmt1.setDouble(17, cobro.getImpTotMo());
			pstmt1.setDouble(18, cobro.getTcMov());
			pstmt1.setString(19, cobro.getReferencia());
			pstmt1.setLong(20, cobro.getNroTrans());
			//pstmt1.setTimestamp(21, cobro.getFechaMod());
			pstmt1.setString(21, cobro.getUsuarioMod());
			pstmt1.setString(22, cobro.getOperacion());
			
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			int linea = 1;
			for (IngresoCobroLinea lin : cobro.getDetalle()) {
				
				/*A cada linea le seteamos el nroTrans*/
				lin.setNroTrans(cobro.getNroTrans());
				
				this.insertarLineaCobro(lin, linea, con);
				
				linea++;
			}
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoIngresoCobroException();
		} 
	}
	
	/**
	 * Eliminamos el cabezal de un cobro
	 * @throws EliminandoIngresoCobroException 
	 *
	 */
	public void eliminarIngresoCobro(IngresoCobro cobro, Connection con) throws InsertandoIngresoCobroException, ConexionException, EliminandoIngresoCobroException {

		Consultas clts = new Consultas();
    	String delete = clts.eliminarIngresoCobroCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, cobro.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.eliminarIngresoCobroDetalle(cobro, con);
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoIngresoCobroException();
		} 
    	
	}
	
	/**
	 * Eliminamos el detalle de un cobro
	 *
	 */
	private void eliminarIngresoCobroDetalle(IngresoCobro cobro, Connection con) throws  ConexionException, EliminandoIngresoCobroException {

		Consultas clts = new Consultas();
    	String delete = clts.deleteIngresoCobroCabDet();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(delete);
			pstmt1.setLong(1, cobro.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new EliminandoIngresoCobroException();
		} 
	}
	
	/**
	 * Inserta una linea del cobro
	 * 
	 */
	private void insertarLineaCobro(IngresoCobroLinea lin, int linea, Connection con) throws InsertandoIngresoCobroException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertIngresoCobroCabDet();
    	
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
			//pstmt1.setTimestamp(23, lin.getFechaMod());
			pstmt1.setString(23, lin.getUsuarioMod());
			pstmt1.setString(24, lin.getOperacion());
			pstmt1.setInt(25,linea);
		
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoIngresoCobroException();
		} 
		
	}
	
/////////////////////////////////////DETALLE/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todas las lineas del cobro, pasandole el cobro
	 */
	private ArrayList<IngresoCobroLinea> getIngresoCobroLineaxTrans(Connection con, IngresoCobro cab) throws ObteniendoIngresoCobroException, ConexionException {
		
		ArrayList<IngresoCobroLinea> lst = new ArrayList<IngresoCobroLinea>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getIngresoCobroDetxTrans();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setLong(1, cab.getNroTrans());
			rs = pstmt1.executeQuery();
			
			IngresoCobroLinea aux;
			
			while(rs.next ()) {
				
				
				aux = new IngresoCobroLinea();
				
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
				
				//aux.setCodCuentaInd(rs.getString("cuenta"));
				aux.setFecDoc(rs.getTimestamp("fec_doc"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("nom_moneda"), rs.getString("simbolo"), rs.getBoolean("nacional")));
				
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
				
				
				
				lst.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoIngresoCobroException();
			
		}
    	
    	return lst;
	}
	

	
}
