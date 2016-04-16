package com.persistencia;

public class Consultas {

    protected final static String URL = "jdbc:mysql://localhost:3306/vaadin";
    protected final static String USER = "root";
    protected final static String DRIVER = "com.mysql.jdbc.Driver";
    protected final static String PASS  = "rootfuerte15";
    
    
    public String getMonedas(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT cod_moneda, nom_moneda, simbolo_moneda FROM ct_monedas");
                
        return sb.toString();
    }
	
	
}
