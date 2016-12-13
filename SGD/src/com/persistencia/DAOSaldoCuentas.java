package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.SaldoCuentas.*;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumSaldo;

public class DAOSaldoCuentas implements IDAOSaldosCuentas{

	
	/**
	 * Dado el documento, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberSaldoCta(DocumSaldo docum, Connection con) throws ExisteSaldoCuentaException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberSaldoCuenta();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, docum.getNroTrans());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteSaldoCuentaException();
		}
	}
	


	public void insertarSaldoCuenta(DocumSaldo documento, Connection con)
			throws InsertandoSaldoCuentaException, ConexionException, SQLException {
		
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarSaldoCuenta();
    	
    	
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
			
			pstmt1.setString(15, documento.getCodDocumRef());
			pstmt1.setString(16, documento.getSerieDocumRef());
			pstmt1.setInt(17, documento.getNroDocumRef());
			pstmt1.setString(18, documento.getCodBco());
			pstmt1.setString(19, documento.getCodCtaBco());
			pstmt1.setString(20, documento.getMovimiento());
			pstmt1.setInt(21, documento.getSigno());
			pstmt1.setTimestamp(22, documento.getFecDoc());
			pstmt1.setTimestamp(23, documento.getFecValor());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoSaldoCuentaException();
		} 
		
	}

	public void eliminarSaldoCuenta(DocumSaldo documento, Connection con)
			throws EliminandoSaldoCuetaException, ConexionException {
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarSaldoCuenta();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setLong(1, documento.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoSaldoCuetaException();
		}
	}
	
	public void modificarSaldoCuenta(DocumSaldo saldo, Connection con)
			throws ModificandoSaldoCuentaException, ConexionException, EliminandoSaldoCuetaException, InsertandoSaldoCuentaException, ExisteSaldoCuentaException, NoExisteSaldoCuentaException {
		
		try {
			
			/*Verificamos si existe el documento en la tabla de saldos
			 * si existe eliminamos e insertamos con la nueva info*/
			if(this.memberSaldoCta(saldo, con))
			{
				this.eliminarSaldoCuenta(saldo, con);
				this.insertarSaldoCuenta(saldo, con);
			}
			else /*Si no existe,tiramos exception*/
			{
				throw new NoExisteSaldoCuentaException();
			} 
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoSaldoCuentaException();
		}
	}
}
