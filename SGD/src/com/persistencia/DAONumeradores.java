package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excepciones.ConexionException;
import com.excepciones.Procesos.IngresandoProcesoException;

public class DAONumeradores implements IDAONumeradores{

	
	public int getNroTrans(Connection con, String cod_numerador) throws SQLException{
		
		/*TRAE EL NUMERO*/
		PreparedStatement pstmt1;
		ResultSet rs;
		ConsultasDD clts = new ConsultasDD();
		String getNumero = clts.getNumeroTrans();
		String actualizaNumero = clts.actualizarNumeroTrans();
		int codigo = 0;
		
    	pstmt1 = con.prepareStatement(getNumero);
    	pstmt1.setString(1, cod_numerador);
    	rs = pstmt1.executeQuery();
    	while(rs.next ()) {
    		codigo = rs.getInt(1) + 1;
		}
		rs.close ();
		pstmt1.close ();
		
		/*ACTUALIZA TABLA DE NUMERADORES*/
		pstmt1 = con.prepareStatement(actualizaNumero);
    	pstmt1.setInt(1, codigo);
    	pstmt1.setString(2, cod_numerador);
    	pstmt1.executeUpdate();
    	pstmt1.close ();	
    	
    	return codigo;
		
	}
	@Override
	public int getNumero(Connection con, String cod_numerador, String cod_Emp)
			throws IngresandoProcesoException, ConexionException, SQLException {
		
		/*TRAE EL NUMERO*/
		PreparedStatement pstmt1;
		ResultSet rs;
		ConsultasDD clts = new ConsultasDD();
		String getNumero = clts.getNumero();
		String actualizaNumero = clts.actualizarNumero();
		int codigo = 0;
		
    	pstmt1 = con.prepareStatement(getNumero);
    	pstmt1.setString(1, cod_numerador);
    	pstmt1.setString(2, cod_Emp);
    	rs = pstmt1.executeQuery();
    	while(rs.next ()) {
    		codigo = rs.getInt(1) + 1;
		}
		rs.close ();
		pstmt1.close ();
		
		/*ACTUALIZA TABLA DE NUMERADORES*/
		pstmt1 = con.prepareStatement(actualizaNumero);
    	pstmt1.setInt(1, codigo);
    	pstmt1.setString(2, cod_numerador);
    	pstmt1.setString(3, cod_Emp);
    	pstmt1.executeUpdate();
    	pstmt1.close ();	
    	
    	return codigo;
	}
	

}
