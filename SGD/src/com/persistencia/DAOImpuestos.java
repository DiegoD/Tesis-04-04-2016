package com.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.logica.Impuesto;

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

    @SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getImpuestosTodos() throws ClassNotFoundException{
    	
    	conexion = new Conexion();
    	Consultas clts = new Consultas();
    	String query = clts.getImpuestosTodos();
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	ArrayList<JSONObject> lstImpuesto = new ArrayList<JSONObject>();
    	
    	System.out.println("Estoy en controlador");
    	
    	try {
    		
    		this.con = conexion.getConnection();
    		pstmt1 = con.prepareStatement(query);
			rs = pstmt1.executeQuery();
			
			while(rs.next ()) {
				
				org.json.simple.JSONObject obj = new org.json.simple.JSONObject();

				obj.put("cod_impuesto", rs.getInt(1));
				obj.put("desc_impuesto", rs.getString(2));
				obj.put("porcentaje_impuesto", rs.getInt(3));
				
				System.out.println(rs.getString(2));			
				
				lstImpuesto.add(obj);
				
			}
			rs.close ();
			pstmt1.close ();
			con.close ();
    	}
    	catch (SQLException e) {
			e.printStackTrace();
			
		}
			
		return lstImpuesto;
    }
}
