package com.persistencia;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.logica.Impuesto;
import com.valueObject.ImpuestoVO;

public class DAOImpuestos implements IDaoImpuesto{
	
	//private java.sql.Connection con = null;
	private Conexion conexion;
	private java.sql.Connection con;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public void insertImpuesto (Impuesto impuesto) throws ClassNotFoundException{
    	
    	conexion = new Conexion();
    	Consultas clts = new Consultas();
    	String insert = clts.insertImpuesto();
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	
    	try {
    		
    		//Class.forName("com.mysql.jdbc.Driver");
			//this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
			this.con = conexion.getConnection();
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setInt(1, impuesto.getCodImpuesto());
			pstmt1.setString(2, impuesto.getDescImpuesto());
			pstmt1.setInt(3, impuesto.getPorcentajeImpuesto());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			con.close ();

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }

}
