package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.logica.ClienteInfo;
import com.logica.Cuenta;
import com.logica.Gasto;
import com.logica.Impuesto;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.logica.Rubro;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;
import com.mysql.jdbc.Statement;

public class DAOGastos implements IDAOGastos{

	@Override
	public ArrayList<Gasto> getGastos(Connection con, String codEmp)
			throws ObteniendoGastosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Gasto> lstGastos = new ArrayList<Gasto>();
		
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getGastos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	String queryEmpleado = clts.getGastosEmpleados();
	    	PreparedStatement pstmt2 = con.prepareStatement(queryEmpleado);
	    	
	    	String queryOficina = clts.getGastosOficina();
	    	PreparedStatement pstmt3 = con.prepareStatement(queryOficina);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Gasto aux;
			while(rs.next ()) {
				
				aux = new Gasto();
				
				aux.setFecDoc(rs.getTimestamp(1));
				aux.setCodDocum(rs.getString(2));
				aux.setSerieDocum(rs.getString(3));
				aux.setNroDocum(rs.getInt(4));
				aux.setCodEmp(rs.getString(5));
				aux.setReferencia(rs.getString(6));
				aux.setNroTrans(rs.getLong(7));
				aux.setFecValor(rs.getTimestamp(8));
				aux.setCodProceso(rs.getString(9));
				aux.setImpImpuMn(rs.getDouble(11));
				aux.setImpImpuMo(rs.getDouble(12));
				aux.setImpSubMn(rs.getDouble(13));
				aux.setImpSubMo(rs.getDouble(14));
				aux.setImpTotMn(rs.getDouble(15));
				aux.setImpTotMo(rs.getDouble(16));
				aux.setTcMov(rs.getDouble(17));
				aux.setCodCuentaInd(rs.getString(18));
				aux.setFechaMod(rs.getTimestamp(19));
				aux.setUsuarioMod(rs.getString(20));
				aux.setOperacion(rs.getString(21));
				aux.setTitInfo(new TitularInfo(rs.getString(22), rs.getString(23)));
				aux.setMoneda(new MonedaInfo (rs.getString(24), rs.getString(25), rs.getString(26)));
				aux.setCuenta(new CuentaInfo(rs.getString(27), rs.getString(28)));
				aux.setRubroInfo(new RubroInfo(rs.getString(29), rs.getString(30)));
				ImpuestoInfo imp = new ImpuestoInfo();
				imp.setCodImpuesto(rs.getString(33));
				imp.setNomImpuesto(rs.getString(34));
				imp.setPorcentaje(rs.getDouble(35));
				aux.setImpuestoInfo(imp);
				aux.setDescProceso(rs.getString(36));
				
				lstGastos.add(aux);
				
			}
			pstmt1.close ();
			
			pstmt2.setString(1, codEmp);
			rs = pstmt2.executeQuery();
			
			while(rs.next ()) {
				
				aux = new Gasto();
				
				aux.setFecDoc(rs.getTimestamp(1));
				aux.setCodDocum(rs.getString(2));
				aux.setSerieDocum(rs.getString(3));
				aux.setNroDocum(rs.getInt(4));
				aux.setCodEmp(rs.getString(5));
				aux.setReferencia(rs.getString(6));
				aux.setNroTrans(rs.getLong(7));
				aux.setFecValor(rs.getTimestamp(8));
				aux.setCodProceso(rs.getString(9));
				aux.setImpImpuMn(rs.getDouble(11));
				aux.setImpImpuMo(rs.getDouble(12));
				aux.setImpSubMn(rs.getDouble(13));
				aux.setImpSubMo(rs.getDouble(14));
				aux.setImpTotMn(rs.getDouble(15));
				aux.setImpTotMo(rs.getDouble(16));
				aux.setTcMov(rs.getDouble(17));
				aux.setCodCuentaInd(rs.getString(18));
				aux.setFechaMod(rs.getTimestamp(19));
				aux.setUsuarioMod(rs.getString(20));
				aux.setOperacion(rs.getString(21));
				aux.setTitInfo(new TitularInfo(rs.getString(22), rs.getString(23)));
				aux.setMoneda(new MonedaInfo (rs.getString(24), rs.getString(25), rs.getString(26)));
				aux.setCuenta(new CuentaInfo(rs.getString(27), rs.getString(28)));
				aux.setRubroInfo(new RubroInfo(rs.getString(29), rs.getString(30)));
				ImpuestoInfo imp = new ImpuestoInfo();
				imp.setCodImpuesto(rs.getString(33));
				imp.setNomImpuesto(rs.getString(34));
				imp.setPorcentaje(rs.getDouble(35));
				aux.setImpuestoInfo(imp);
				
				lstGastos.add(aux);
				
			}
			
			pstmt3.setString(1, codEmp);
			rs = pstmt3.executeQuery();
			
			while(rs.next ()) {
				
				aux = new Gasto();
				
				aux.setFecDoc(rs.getTimestamp(1));
				aux.setCodDocum(rs.getString(2));
				aux.setSerieDocum(rs.getString(3));
				aux.setNroDocum(rs.getInt(4));
				aux.setCodEmp(rs.getString(5));
				aux.setReferencia(rs.getString(6));
				aux.setNroTrans(rs.getLong(7));
				aux.setFecValor(rs.getTimestamp(8));
				aux.setCodProceso(rs.getString(9));
				aux.setImpImpuMn(rs.getDouble(11));
				aux.setImpImpuMo(rs.getDouble(12));
				aux.setImpSubMn(rs.getDouble(13));
				aux.setImpSubMo(rs.getDouble(14));
				aux.setImpTotMn(rs.getDouble(15));
				aux.setImpTotMo(rs.getDouble(16));
				aux.setTcMov(rs.getDouble(17));
				aux.setCodCuentaInd(rs.getString(18));
				aux.setFechaMod(rs.getTimestamp(19));
				aux.setUsuarioMod(rs.getString(20));
				aux.setOperacion(rs.getString(21));
				aux.setTitInfo(new TitularInfo("Oficina", "Oficina"));
				aux.setMoneda(new MonedaInfo (rs.getString(24), rs.getString(25), rs.getString(26)));
				aux.setCuenta(new CuentaInfo(rs.getString(27), rs.getString(28)));
				aux.setRubroInfo(new RubroInfo(rs.getString(29), rs.getString(30)));
				ImpuestoInfo imp = new ImpuestoInfo();
				imp.setCodImpuesto(rs.getString(33));
				imp.setNomImpuesto(rs.getString(34));
				imp.setPorcentaje(rs.getDouble(35));
				aux.setImpuestoInfo(imp);
				
				lstGastos.add(aux);
				
			}
			
			rs.close ();
			pstmt2.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoGastosException();
			
		}
    	
    	return lstGastos;
	}

	@Override
	public boolean memberGasto(long nroTrans, String codEmp, Connection con)
			throws ExisteGastoException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.memberGasto();
			
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
			
			throw new ExisteGastoException();
		}
	}

	@Override
	public void insertarGasto(Gasto gasto, String codEmp, Connection con)
			throws IngresandoGastoException, ConexionException, SQLException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarGasto();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
					
			pstmt1.setTimestamp(1, gasto.getFecDoc());
			pstmt1.setString(2, gasto.getCodDocum());
			pstmt1.setString(3, gasto.getSerieDocum());
			pstmt1.setInt(4, gasto.getNroDocum());
			pstmt1.setString(5, gasto.getCodEmp());
			pstmt1.setString(6, gasto.getMoneda().getCodMoneda());
			pstmt1.setString(7, gasto.getReferencia());
			pstmt1.setString(8, gasto.getTitInfo().getCodigo());
			pstmt1.setLong(9, gasto.getNroTrans());
			pstmt1.setTimestamp(10, gasto.getFecValor());
			pstmt1.setString(11, gasto.getCodProceso());
			pstmt1.setString(12, gasto.getReferencia());
			pstmt1.setDouble(13, gasto.getImpImpuMn());
			pstmt1.setDouble(14, gasto.getImpImpuMo());
			pstmt1.setDouble(15, gasto.getImpSubMn());
			pstmt1.setDouble(16, gasto.getImpSubMo());
			pstmt1.setDouble(17, gasto.getImpTotMn());
			pstmt1.setDouble(18, gasto.getImpTotMo());
			pstmt1.setDouble(19, gasto.getTcMov());
			pstmt1.setString(20, gasto.getCuenta().getCodCuenta());
			pstmt1.setString(21, gasto.getRubroInfo().getCodRubro());
			pstmt1.setString(22, gasto.getCodCuentaInd());
			pstmt1.setString(23, gasto.getUsuarioMod());
			pstmt1.setString(24, gasto.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoGastoException();
		} 
		
	}

	@Override
	public void modificarGasto(Gasto gasto, String codEmp, Connection con) throws ModificandoGastoException, IngresandoGastoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarGasto();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setLong(1, gasto.getNroTrans());
			pstmt1.setString(2, gasto.getCodEmp());
			
			pstmt1.executeUpdate ();
			this.insertarGasto(gasto, codEmp, con);
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoGastoException();
		}
	}
	
	//Nos retorna los gastos con saldo para el titular por moneda
		@Override
		public ArrayList<Gasto> getGastosConSaldoxMoneda(Connection con, String codEmp, String codTit, String codMoneda)
				throws ObteniendoGastosException, ConexionException {
			// TODO Auto-generated method stub
			ArrayList<Gasto> lstGastos = new ArrayList<Gasto>();
			
			try {
				
		    	ConsultasDD clts = new ConsultasDD();
		    	String query = clts.getGastosConSaldoxMoneda();
		    	PreparedStatement pstmt1 = con.prepareStatement(query);
		    	
		    	ResultSet rs;
		    	
		    	pstmt1.setString(1, codEmp);
		    	pstmt1.setString(2, codTit);
		    	pstmt1.setString(3, codMoneda);
				rs = pstmt1.executeQuery();
				
				Gasto aux;
				while(rs.next ()) {
					
					aux = new Gasto();
					
					aux.setFecDoc(rs.getTimestamp(1));
					aux.setCodDocum(rs.getString(2));
					aux.setSerieDocum(rs.getString(3));
					aux.setNroDocum(rs.getInt(4));
					aux.setCodEmp(rs.getString(5));
					aux.setReferencia(rs.getString(6));
					aux.setNroTrans(rs.getLong(7));
					aux.setFecValor(rs.getTimestamp(8));
					aux.setCodProceso(rs.getString(9));
					aux.setImpImpuMn(rs.getDouble(11));
					aux.setImpImpuMo(rs.getDouble(12));
					aux.setImpSubMn(rs.getDouble(13));
					aux.setImpSubMo(rs.getDouble(14));
					aux.setImpTotMn(rs.getDouble(15));
					aux.setImpTotMo(rs.getDouble(16));
					aux.setTcMov(rs.getDouble(17));
					aux.setCodCuentaInd(rs.getString(18));
					aux.setFechaMod(rs.getTimestamp(19));
					aux.setUsuarioMod(rs.getString(20));
					aux.setOperacion(rs.getString(21));
					aux.setTitInfo(new TitularInfo(rs.getString(22), rs.getString(23)));
					aux.setMoneda(new MonedaInfo (rs.getString(24), rs.getString(25), rs.getString(26)));
					aux.setCuenta(new CuentaInfo(rs.getString(27), rs.getString(28)));
					aux.setRubroInfo(new RubroInfo(rs.getString(29), rs.getString(30)));
					ImpuestoInfo imp = new ImpuestoInfo();
					imp.setCodImpuesto(rs.getString(33));
					imp.setNomImpuesto(rs.getString(34));
					imp.setPorcentaje(rs.getDouble(35));
					aux.setImpuestoInfo(imp);
					aux.setDescProceso(rs.getString(36));
					
					lstGastos.add(aux);
					
				}
				rs.close ();
				pstmt1.close ();
	    	}	
	    	
			catch (SQLException e) {
				throw new ObteniendoGastosException();
				
			}
	    	
	    	return lstGastos;
		}
		
		//Nos retorna los gastos con saldo para el titular por moneda
				@Override
				public ArrayList<Gasto> getGastosConSaldo(Connection con, String codEmp, String codTit)
						throws ObteniendoGastosException, ConexionException {
					// TODO Auto-generated method stub
					ArrayList<Gasto> lstGastos = new ArrayList<Gasto>();
					
					try {
						
				    	ConsultasDD clts = new ConsultasDD();
				    	String query = clts.getGastosConSaldo();
				    	PreparedStatement pstmt1 = con.prepareStatement(query);
				    	
				    	ResultSet rs;
				    	
				    	pstmt1.setString(1, codEmp);
				    	pstmt1.setString(2, codTit);
						rs = pstmt1.executeQuery();
						
						Gasto aux;
						while(rs.next ()) {
							
							aux = new Gasto();
							
							aux.setFecDoc(rs.getTimestamp(1));
							aux.setCodDocum(rs.getString(2));
							aux.setSerieDocum(rs.getString(3));
							aux.setNroDocum(rs.getInt(4));
							aux.setCodEmp(rs.getString(5));
							aux.setReferencia(rs.getString(6));
							aux.setNroTrans(rs.getLong(7));
							aux.setFecValor(rs.getTimestamp(8));
							aux.setCodProceso(rs.getString(9));
							aux.setImpImpuMn(rs.getDouble(11));
							aux.setImpImpuMo(rs.getDouble(12));
							aux.setImpSubMn(rs.getDouble(13));
							aux.setImpSubMo(rs.getDouble(14));
							aux.setImpTotMn(rs.getDouble(15));
							aux.setImpTotMo(rs.getDouble(16));
							aux.setTcMov(rs.getDouble(17));
							aux.setCodCuentaInd(rs.getString(18));
							aux.setFechaMod(rs.getTimestamp(19));
							aux.setUsuarioMod(rs.getString(20));
							aux.setOperacion(rs.getString(21));
							aux.setTitInfo(new TitularInfo(rs.getString(22), rs.getString(23)));
							aux.setMoneda(new MonedaInfo (rs.getString(24), rs.getString(25), rs.getString(26)));
							aux.setCuenta(new CuentaInfo(rs.getString(27), rs.getString(28)));
							aux.setRubroInfo(new RubroInfo(rs.getString(29), rs.getString(30)));
							ImpuestoInfo imp = new ImpuestoInfo();
							imp.setCodImpuesto(rs.getString(33));
							imp.setNomImpuesto(rs.getString(34));
							imp.setPorcentaje(rs.getDouble(35));
							aux.setImpuestoInfo(imp);
							aux.setDescProceso(rs.getString(36));
							
							lstGastos.add(aux);
							
						}
						rs.close ();
						pstmt1.close ();
			    	}	
			    	
					catch (SQLException e) {
						throw new ObteniendoGastosException();
						
					}
			    	
			    	return lstGastos;
				}
}
