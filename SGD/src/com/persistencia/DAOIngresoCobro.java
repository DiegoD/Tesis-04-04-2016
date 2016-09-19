package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.logica.Moneda;
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
	public ArrayList<IngresoCobro> getIngresoCobroTodos(Connection con, String codEmp) throws ObteniendoIngresoCobroException, ConexionException {
		
		ArrayList<IngresoCobro> lst = new ArrayList<IngresoCobro>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getIngresoCobroCabTodos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			IngresoCobro aux;
			
			while(rs.next ()) {
							
				aux = new IngresoCobro();
				
				
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
				
				aux.setCuentaInfo(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				aux.setImpTotMn(rs.getDouble("imp_tot_mn"));
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				aux.setTcMov(rs.getDouble("tc_mov"));
				
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				
				
				/*Obtenemos las lineas de la transaccion*/				
				aux.setDetalle(this.getIngresoCobroLineaxTrans(con, codEmp, aux.getNroTrans()));
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
	

	/**
	 * Inserta un banco en la base
	 * @throws InsertandoCuentaException 
	 * @throws InsertandoBancoException 
	 */
	public void insertarIngresoCobro(IngresoCobro cobro, String codEmp, Connection con) throws InsertandoIngresoCobroException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertIngresoCobroCab();
    	
    	PreparedStatement pstmt1;

    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, cobro.getCodDocum());
			pstmt1.setString(2, cobro.getSerieDocum());
			pstmt1.setInt(3, cobro.getNroDocum());
			pstmt1.setString(4, cobro.getTitInfo().getCodigo());
			pstmt1.setString(5, cobro.getCuentaInfo().getCodCuenta());
			pstmt1.setString(6, codEmp);
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
			pstmt1.setTimestamp(21, cobro.getFechaMod());
			pstmt1.setString(22, cobro.getUsuarioMod());
			pstmt1.setString(23, cobro.getOperacion());
			
			
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			int linea = 1;
			for (IngresoCobroLinea lin : cobro.getDetalle()) {
				
				this.insertarLineaCobro(lin, codEmp,linea, con);
				
				linea++;
			}
			
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoIngresoCobroException();
		} 
		
    	
	}
	
	/**
	 * Inserta una linea del cobro
	 * 
	 */
	private void insertarLineaCobro(IngresoCobroLinea lin, String codEmp, int linea, Connection con) throws InsertandoIngresoCobroException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertIngresoCobroCabDet();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
    		pstmt1 =  con.prepareStatement(insert);
    		
			pstmt1.setString(1, lin.getCuenta().getCodCuenta());
			pstmt1.setString(2, codEmp);
			pstmt1.setString(3, lin.getCodDocum());
			pstmt1.setString(4, lin.getSerieDocum());
			pstmt1.setInt(5, lin.getNroDocum());
			pstmt1.setString(6, lin.getCodProceso());
			pstmt1.setString(7, lin.getRubroInfo().getCodRubro());
			pstmt1.setString(8, lin.getCuenta().getCodCuenta());
			pstmt1.setTimestamp(9, lin.getFecDoc());
			pstmt1.setTimestamp(11, lin.getFecValor());
			pstmt1.setString(12, lin.getMoneda().getCodMoneda());
			pstmt1.setString(13, lin.getImpuestoInfo().getCodImpuesto());
			pstmt1.setDouble(14, lin.getImpImpuMn());
			pstmt1.setDouble(15, lin.getImpImpuMo());
			pstmt1.setDouble(16, lin.getImpSubMn());
			pstmt1.setDouble(17, lin.getImpSubMo());
			pstmt1.setDouble(18, lin.getImpTotMn());
			pstmt1.setDouble(19, lin.getImpTotMo());
			pstmt1.setDouble(20, lin.getTcMov());
			pstmt1.setString(21, lin.getReferencia());
			pstmt1.setString(22, lin.getReferencia());
			pstmt1.setLong(23, lin.getNroTrans());
			pstmt1.setTimestamp(24, lin.getFechaMod());
			pstmt1.setString(25, lin.getUsuarioMod());
			pstmt1.setString(26, lin.getOperacion());
			pstmt1.setInt(27,linea);
		
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoIngresoCobroException();
		} 
		
	}
	
	/**
	 * MOdificamos Ingreso Cobro, hacemos delete y volvemos a ingresar
	 * @throws ConexionException 
	 * @throws ModificandoCuentaBcoException 
	 * 
	 */
	public void modificarIngresoCobro(IngresoCobro cobro, String codEmp, Connection con) throws ModificandoIngresoCobroException, ConexionException{
		
		Consultas clts = new Consultas();
    	
    	String deleteCab = clts.deleteIngresoCobroCab();
    	String deleteDet = clts.deleteIngresoCobroDet();
    	
    	PreparedStatement pstmt1;
    	PreparedStatement pstmt2;

    	try {
    		
			pstmt1 =  con.prepareStatement(deleteCab);
			pstmt2 =  con.prepareStatement(deleteDet);
			
			/*Primero eliminamos la transaccion para luego volverla a insertar*/
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			pstmt2.executeUpdate ();
			pstmt2.close ();
			
			this.insertarIngresoCobro(cobro, codEmp, con);
			
		} 
    	catch (SQLException | InsertandoIngresoCobroException e) 
    	{
			throw new ModificandoIngresoCobroException();
		} 
	}
/////////////////////////////////////CUENTAS/////////////////////////////////////////////////////
	
	/**
	 * Nos retorna una lista con todos los Bancos del sistema para la emrpesa
	 */
	public ArrayList<IngresoCobroLinea> getIngresoCobroLineaxTrans(Connection con, String codEmp, long nroTrans) throws ObteniendoIngresoCobroException, ConexionException {
		
		ArrayList<IngresoCobroLinea> lst = new ArrayList<IngresoCobroLinea>();
	
		try {
			
	    	Consultas clts = new Consultas();
	    	String query = clts.getIngresoCobroDetxTrans();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setLong(1, nroTrans);
			rs = pstmt1.executeQuery();
			
			IngresoCobroLinea aux;
			
			while(rs.next ()) {
				
				
				aux = new IngresoCobroLinea();
				
				aux.setCuenta(new CuentaInfo(rs.getString("cod_cuenta"), rs.getString("nom_cuenta")));
				
				aux.setCodEmp(codEmp);
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setCodProceso(rs.getString("cod_proceso"));
				
				aux.setRubroInfo(new RubroInfo(rs.getString("cod_rubro"), rs.getString("nom_rubro")));
				
				aux.setCodCuentaInd(rs.getString("cuenta"));
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
