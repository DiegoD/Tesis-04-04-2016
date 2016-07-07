package com.persistencia;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.*;
import com.valueObject.*;



public class DAOMonedas implements IDAOMonedas{
	
	
    private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    
    public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException{
    	
    	ArrayList lstMonedas = new ArrayList<MonedaVO>();
    	Consultas clts = new Consultas();
    	
    	String query = clts.getMonedas();
    	
    	PreparedStatement pstmt1;
    	ResultSet rs;
    	
    	try {
    		
    		Class.forName("com.mysql.jdbc.Driver");
    		
			this.con = DriverManager.getConnection (Consultas.URL,Consultas.USER,Consultas.PASS);
		
			pstmt1 =  con.prepareStatement(query);
	    	
	    	rs = pstmt1.executeQuery ();
	    	 
	    	 MonedaVO monedaVO;
	    	 while (rs.next()){
	    		 
	    		 monedaVO = new MonedaVO();
	    		 monedaVO.setCodMoneda(rs.getInt(1));
	    		 monedaVO.setNomMoneda(rs.getString(2));
	    		 monedaVO.setSimboloMoneda(rs.getString(3));
	    		 
	    		 lstMonedas.add(monedaVO);
	    	 }
	    	
	    	 
	    	 rs.close ();
			 pstmt1.close ();
			 con.close();
    	
    	} catch (SQLException e) {
			throw new ObteniendoMonedasException();
			
		} catch (ClassNotFoundException e) {
			throw new ObteniendoMonedasException();
		}
    	
    	return lstMonedas;
    }
    
    

}
