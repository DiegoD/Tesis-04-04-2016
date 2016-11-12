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
import com.logica.Depositos.Deposito;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.mysql.jdbc.Statement;
import com.valueObject.Deposito.DepositoVO;

public class DAOCheques implements IDAOCheques{

	
	/**
	 * Dado el documento, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberCheque(DatosDocum docum, Connection con) throws ExisteChequeException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberCheque();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, docum.getCodDocum());
			pstmt1.setString(2, docum.getSerieDocum());
			pstmt1.setInt(3, docum.getNroDocum());
			pstmt1.setString(4, docum.getCodEmp());
			pstmt1.setString(5, docum.getTitInfo().getCodigo());
			
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
	


	public void insertarCheque(DatosDocum documento, Connection con)
			throws InsertandoChequeException, ConexionException, SQLException {
		
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarCheque();
    	
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, documento.getCodDocum());
			pstmt1.setString(2, documento.getSerieDocum());
			pstmt1.setInt(3, documento.getNroDocum());
			pstmt1.setString(4, documento.getCodEmp());
			pstmt1.setString(5, documento.getMoneda().getCodMoneda());
			pstmt1.setString(6, documento.getTitInfo().getCodigo());
			pstmt1.setDouble(7, documento.getImpTotMn());
			pstmt1.setDouble(8, documento.getImpTotMo());
			pstmt1.setString(9, documento.getCodCuentaInd());
			pstmt1.setString(10, documento.getUsuarioMod());
			pstmt1.setString(11, documento.getOperacion());
			pstmt1.setString(12, documento.getCodCuentaInd());
			pstmt1.setString(13, documento.getReferencia());
			pstmt1.setLong(14, documento.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoChequeException();
		} 
		
	}

	public void eliminarCheque(DatosDocum documento, Connection con)
			throws EliminandoChequeException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarCheque();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, documento.getCodDocum());
			pstmt1.setString(2, documento.getSerieDocum());
			pstmt1.setInt(3, documento.getNroDocum());
			pstmt1.setString(4, documento.getCodEmp());
			pstmt1.setString(5, documento.getTitInfo().getCodigo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoChequeException();
		}
	}
	
	public void modificarCheque(DatosDocum cheque, int signo, double tc   , Connection con)
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
	public ArrayList<Deposito> getChequesBanco(Connection con, String codEmp, String codBanco, String codCtaBco) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		ArrayList<Deposito> lstDepositos = new ArrayList<Deposito>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getChequesBanco();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codBanco);
	    	pstmt1.setString(3, codCtaBco);
	    	
			rs = pstmt1.executeQuery();
			
			Deposito aux;
			BancoInfo b;
			CuentaBcoInfo c;
			
			while(rs.next ()) {
							
				aux = new Deposito();
				
				aux.setCodDocum(rs.getString("cod_docum"));
				aux.setSerieDocum(rs.getString("serie_docum"));
				aux.setNroDocum(rs.getInt("nro_docum"));
				aux.setFecValor(rs.getTimestamp("fec_valor"));
				
				b = new BancoInfo();
				b.setCodBanco(rs.getString("cod_bco"));
				b.setNomBanco(rs.getString("nom_bco"));
				aux.setBanco(b);
				
				c = new CuentaBcoInfo();
				c.setCodCuenta(rs.getString("cod_ctabco"));
				c.setNomCuenta(rs.getString("nom_cta"));
				aux.setCuentaBanco(c);
				
				aux.setFuncionario(null);
				aux.setNumComprobante(0);
				aux.setObservaciones(null);
				aux.setImpTotMo(rs.getDouble("imp_tot_mo"));
				
				
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
	
}
