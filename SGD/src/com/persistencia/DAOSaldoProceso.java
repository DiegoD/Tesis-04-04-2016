package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.google.gwt.thirdparty.javascript.jscomp.SourceMap.DetailLevel;
import com.logica.MonedaInfo;
import com.logica.SaldoProceso;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.logica.Docum.TitularInfo;
import com.mysql.jdbc.Statement;

public class DAOSaldoProceso implements IDAOSaldosProc {
	
	/**
	 * Dado el codigo de rubro, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberSaldo(DatosDocum docum, Connection con) throws ExisteSaldoException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberSaldoProceso();
			DocumDetalle detalle = (DocumDetalle) docum;
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, detalle.getCodProceso()); /*Es el codigo del proceso*/
			pstmt1.setString(2, docum.getCodEmp());
			pstmt1.setString(3, docum.getTitInfo().getCodigo());
			pstmt1.setString(4, docum.getMoneda().getCodMoneda());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteSaldoException();
		}
	}
	

	@Override
	public void insertarSaldo(DatosDocum documento, Connection con)
			throws IngresandoSaldoException, ConexionException, SQLException {
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarSaldoProceso();
    	
    	DocumDetalle detalle = (DocumDetalle) documento;
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			
			pstmt1.setString(1, detalle.getCodProceso());
			pstmt1.setString(2, documento.getCodDocum());
			pstmt1.setString(3, documento.getSerieDocum());
			pstmt1.setInt(4, documento.getNroDocum());
			pstmt1.setString(5, documento.getCodEmp());
			pstmt1.setString(6, documento.getMoneda().getCodMoneda());
			pstmt1.setString(7, documento.getTitInfo().getCodigo());
			pstmt1.setDouble(8, documento.getImpTotMn());
			pstmt1.setDouble(9, documento.getImpTotMo());
			pstmt1.setString(10, documento.getCodCuentaInd()); /*codigo del proceso*/
			pstmt1.setLong(11, documento.getNroTrans());
			pstmt1.setTimestamp(12, documento.getFecDoc());
			pstmt1.setTimestamp(13, documento.getFecValor());
			pstmt1.setString(14, documento.getUsuarioMod());
			pstmt1.setString(15, documento.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoSaldoException();
		} 
		
	}

	@Override
	public void eliminarSaldo(DatosDocum documento, Connection con)
			throws EliminandoSaldoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarSaldoProceso();
		PreparedStatement pstmt1;
		DocumDetalle detalle = (DocumDetalle) documento;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, detalle.getCodProceso());
			pstmt1.setString(2, documento.getCodEmp());
			pstmt1.setString(3, documento.getTitInfo().getCodigo());
			pstmt1.setString(4, documento.getMoneda().getCodMoneda());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoSaldoException();
		}
	}
	
	@Override
	public void modificarSaldo(DatosDocum documento, int signo, double tc , Connection con)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException {
		
		double saldoAnteriorMO;
		double saldoCalcMO;
		double saldoCalcMN;
		
		try {
			
			/*Verificamos si existe el documento en la tabla de saldos
			 * si existe eliminamos e insertamos con la nueva info*/
			if(this.memberSaldo(documento, con))
			{
				
				
				/*Obtenemos primero el saldo anterior*/
				saldoAnteriorMO = this.getSaldo(documento, con);
				
				/*Resto en la moneda operativa*/
				saldoCalcMO = saldoAnteriorMO +(documento.getImpTotMo()* signo);
				documento.setImpTotMo(saldoCalcMO);
				
				/*Calculamos el saldo resto a la fecha valor pasada por parametro, si es que quedea saldo en MN*/
				if(saldoCalcMO != 0)
				{
					saldoCalcMN = saldoCalcMO * tc;
					documento.setImpTotMn(saldoCalcMN);
				}
				else
				{
					documento.setImpTotMn(0);
				}
				
				this.eliminarSaldo(documento, con);
				this.insertarSaldo(documento, con);
			}
			else /*Si no existe, es nuevo y solamente insertamos*/
			{
				this.insertarSaldo(documento, con);
			} 
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoSaldoException();
		}
	}
	
	/**
	 * Nos retorna el saldo del documento en moneda operativa
	 * @throws ExisteSaldoException 
	 */
	private double getSaldo(DatosDocum docum, Connection con) throws ExisteSaldoException{
		
		ConsultasDD consultas = new ConsultasDD ();
		String query = consultas.getSaldoMnProceso();
		
		Double aux = null;
		
		try {
			PreparedStatement pstmt1 = con.prepareStatement(query);
			DocumDetalle detalle = (DocumDetalle) docum;
			
			pstmt1.setString(1, detalle.getCodProceso());
			pstmt1.setString(2, docum.getCodEmp());
			pstmt1.setString(3, docum.getTitInfo().getCodigo()); 
			pstmt1.setString(4, docum.getMoneda().getCodMoneda()); 
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) {
				aux = (rs.getDouble("imp_tot_mo"));
			}
			
						
			rs.close ();
			pstmt1.close ();
			
			return aux;
		
		}catch(SQLException e){
		
			throw new ExisteSaldoException();
		}
	}
	
	

	/**
	 * Nos retorna los saldos para el proceso abierto por moneda
	 * 
	 */
	public ArrayList<SaldoProceso> getSaldosSinAdjuxProceso(String codEmp, int codProceso, Connection con) throws ObteniendoSaldosException, ConexionException {
		
		ArrayList<SaldoProceso> lst = new ArrayList<SaldoProceso>();
		
			try {
				//
		    	ConsultasDD clts = new ConsultasDD();
		    	String query = clts.getSaldosxProceso();
		    	PreparedStatement pstmt1 = con.prepareStatement(query);
		    	
		    	ResultSet rs;
		    	
		    	pstmt1.setString(1, codEmp);
		    	pstmt1.setInt(2, codProceso);
		    	
				rs = pstmt1.executeQuery();
				
				SaldoProceso aux;
				
				while(rs.next ()) {
								
					aux = new SaldoProceso();
					
					aux.setCodProceso(rs.getString("cod_proceso"));
					aux.setImpTotMN(rs.getDouble("imp_tot_mn"));
					aux.setImpTotMO(rs.getDouble("imp_tot_mo"));
					
					aux.setMoneda(new MonedaInfo(rs.getString("cod_moneda"), rs.getString("descripcion"), rs.getString("simbolo")));
					
					lst.add(aux);
					
				}
				rs.close ();
				pstmt1.close ();
	    	}	
			
			catch (SQLException e) {
				throw new ObteniendoSaldosException();
				
			}
	    	
    	return lst;
	}

}
