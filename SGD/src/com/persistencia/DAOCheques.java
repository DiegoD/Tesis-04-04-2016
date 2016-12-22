package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.*;
import com.logica.Banco;
import com.logica.Cheque;
import com.logica.MonedaInfo;
import com.logica.Depositos.Deposito;
import com.logica.Depositos.DepositoDetalle;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.logica.Docum.TitularInfo;
import com.mysql.jdbc.Statement;
import com.valueObject.Deposito.DepositoVO;

public class DAOCheques implements IDAOCheques{

	
	/**
	 * Dado el documento, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberCheque(Cheque cheque, Connection con) throws ExisteChequeException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberCheque();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cheque.getCodDocum());
			pstmt1.setString(2, cheque.getSerieDocum());
			pstmt1.setInt(3, cheque.getNroDocum());
			pstmt1.setString(4, cheque.getCodEmp());
			pstmt1.setString(5, cheque.getTitInfo().getCodigo());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteChequeException();
		}
	}
	


	public void insertarCheque(Cheque cheque, Connection con)
			throws InsertandoChequeException, ConexionException, SQLException {
		
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarCheque();
    	
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, cheque.getCodDocum());
			pstmt1.setString(2, cheque.getSerieDocum());
			pstmt1.setInt(3, cheque.getNroDocum());
			pstmt1.setString(4, cheque.getCodEmp());
			pstmt1.setString(5, cheque.getMoneda().getCodMoneda());
			pstmt1.setString(6, cheque.getTitInfo().getCodigo());
			pstmt1.setDouble(7, cheque.getImpTotMn());
			pstmt1.setDouble(8, cheque.getImpTotMo());
			pstmt1.setString(9, cheque.getCodCuentaInd());
			pstmt1.setString(10, cheque.getUsuarioMod());
			pstmt1.setString(11, cheque.getOperacion());
			pstmt1.setString(12, cheque.getCodCuentaInd());
			pstmt1.setString(13, cheque.getReferencia());
			pstmt1.setLong(14, cheque.getNroTrans());
			pstmt1.setString(15, cheque.getBanco().getCodBanco());
			pstmt1.setString(16, cheque.getCuentaBanco().getCodCuenta());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoChequeException();
		} 
		
	}

	public void eliminarCheque(Cheque cheque, Connection con)
			throws EliminandoChequeException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarCheque();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, cheque.getCodDocum());
			pstmt1.setString(2, cheque.getSerieDocum());
			pstmt1.setInt(3, cheque.getNroDocum());
			pstmt1.setString(4, cheque.getCodEmp());
			pstmt1.setString(5, cheque.getTitInfo().getCodigo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoChequeException();
		}
	}
	
	public void modificarCheque(Cheque cheque, int signo, double tc   , Connection con)
			throws ModificandoChequeException, ConexionException, EliminandoChequeException, InsertandoChequeException, ExisteChequeException, NoExisteChequeException {
		
		try {
			
			/*Verificamos si existe el documento en la tabla de saldos
			 * si existe eliminamos e insertamos con la nueva info*/
			if(this.memberCheque(cheque, con))
			{
				this.eliminarCheque(cheque, con);
				this.insertarCheque(cheque, con);
			}
			else /*Si no existe,tiramos exception*/
			{
				throw new NoExisteChequeException();
			} 
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoChequeException();
		}
	}
	
	/**
	 * Nos retorna una lista con todos los cheques a depositar
	 * @throws ObteniendoBancosException 
	 */
	public ArrayList<DepositoDetalle> getChequesBanco(Connection con, String codEmp, String codMoneda) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		ArrayList<DepositoDetalle> lstDepositos = new ArrayList<DepositoDetalle>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getChequesBanco();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codMoneda);
	    	
			rs = pstmt1.executeQuery();
			
			DepositoDetalle aux;
			BancoInfo b;
			CuentaBcoInfo c;
			Integer codigo;
			
			while(rs.next ()) {
							
				aux = new DepositoDetalle();
				Cheque cheque = new Cheque();
				cheque.setCodDocum(rs.getString("cod_docum"));
				cheque.setSerieDocum(rs.getString("serie_docum"));
				cheque.setNroDocum(rs.getInt("nro_docum"));
				cheque.setImpTotMn(rs.getDouble("imp_tot_mn"));
				cheque.setImpTotMo(rs.getDouble("imp_tot_mo"));
				
				BancoInfo banco = new BancoInfo();
				banco.setCodBanco(rs.getString("cod_bco"));
				banco.setNomBanco(rs.getString("nom_bco"));
				cheque.setBanco(banco);

				CuentaBcoInfo cuentaBanco = new CuentaBcoInfo();
				cuentaBanco.setCodCuenta(rs.getString("cod_ctabco"));
				cuentaBanco.setNomCuenta(rs.getString("nom_cta"));
				cheque.setCuentaBanco(cuentaBanco);
				
				MonedaInfo moneda = new MonedaInfo();
				moneda.setCodMoneda(rs.getString("cod_moneda"));
				moneda.setDescripcion(rs.getString("descripcion"));
				moneda.setNacional(rs.getBoolean("nacional"));
				moneda.setSimbolo(rs.getString("simbolo"));
				cheque.setMoneda(moneda);
				
				cheque.setFecValor(rs.getTimestamp("fec_valor"));
				cheque.setFecDoc(rs.getTimestamp("fec_doc"));
				cheque.setReferencia(rs.getString("referencia"));
				cheque.setTcMov(rs.getDouble("tc_mov"));
				
				TitularInfo tit = new TitularInfo();
				codigo = rs.getInt("cod_tit");
				tit.setCodigo(codigo.toString());
				cheque.setTitInfo(tit);
				
				aux.setCheque(cheque);
				aux.setNroTrans(0);
				lstDepositos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoChequeException();
			
		}
    	
    	return lstDepositos;
	}

	public void depositarCheques(String codEmp, ArrayList<DepositoVO> cheques){
		
	}

	public boolean exixsteCheque(String serie, Integer nro, String codEmp, String codBco, String codCta, Connection con) throws ExisteChequeException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.existeCheque();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, serie);
			pstmt1.setInt(2, nro);
			pstmt1.setString(3, codEmp);
			pstmt1.setString(4, codBco);
			pstmt1.setString(5, codCta);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteChequeException();
		}
	}
	
	
}
