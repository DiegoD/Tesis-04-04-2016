package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Numeradores {
	
	private PreparedStatement pstmt1;
	private ResultSet rs1;
	private ConsultasDD clts = new ConsultasDD();
	private Connection con;
	
	
	
	public Numeradores(){
		
	}
	
	public int getNumero(String cod_numerador, String cod_emp){
		
		/*TRAE EL NUMERO DEL PROCESO*/
    	pstmt1 = con.prepareStatement(getNumero);
    	pstmt1.setString(1, "01");
    	pstmt1.setString(2, codEmp);
    	rs2 = pstmt2.executeQuery();
    	while(rs2.next ()) {
    		codigo = rs2.getInt(1) + 1;
		}
		rs2.close ();
		pstmt2.close ();
		
		/*ACTUALIZA TABLA DE NUMERADORES*/
		pstmt2 = con.prepareStatement(actualizarNumero);
    	pstmt2.setInt(1, codigo);
    	pstmt2.setString(2, "01");
    	pstmt2.setString(3, codEmp);
    	pstmt2.executeUpdate();
    	pstmt2.close ();	
	}
	String getNumero = clts.getNumero();
	String actualizarNumero = clts.actualizarNumero();
	

}
