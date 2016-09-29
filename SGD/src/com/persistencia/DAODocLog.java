package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.DocLog.InsertandoLogException;
import com.excepciones.DocLog.ModificandoLogException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.logica.Gasto;
import com.logica.DocLog.DocLog;
import com.mysql.jdbc.Statement;

public class DAODocLog implements IDAODocLog{
	
	public void insertarDocLog(DocLog log, String codEmp, Connection con) throws InsertandoLogException, ConexionException, SQLException{
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarLog();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pstmt1.setString(1, log.getCod_docum());
			pstmt1.setString(2, log.getSerie_docum());
			pstmt1.setInt(3, log.getNro_docum());
			pstmt1.setString(4, log.getCod_doca());
			pstmt1.setString(5, log.getSerie_doca());
			pstmt1.setInt(6, log.getNro_doca());
			pstmt1.setString(7, log.getCod_doc_ref());
			pstmt1.setString(8, log.getSerie_doc_ref());
			pstmt1.setInt(9, log.getNro_doc_ref());
			pstmt1.setString(10, codEmp);
			pstmt1.setString(11, log.getCod_tit());
			pstmt1.setLong(12, log.getNro_trans());
			pstmt1.setString(13, log.getCod_moneda());
			pstmt1.setDouble(14, log.getImp_tot_mn());
			pstmt1.setDouble(15, log.getImp_tot_mo());
			pstmt1.setString(16, log.getCuenta());
			pstmt1.setString(17, log.getUsuarioMod());
			pstmt1.setString(18, log.getOperacion());
			pstmt1.setInt(19, 1);
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoLogException();
		} 
	}
	
	@Override
	public void modificarDocLog(DocLog log, String codEmp, Connection con) throws ModificandoLogException, InsertandoLogException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarLog();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setLong(1, log.getNro_trans());
			pstmt1.setInt(2, log.getLinea());
			pstmt1.setString(3, codEmp);
			
			pstmt1.executeUpdate ();
			this.insertarDocLog(log, codEmp, con);
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoLogException();
		}
	}
	
}
