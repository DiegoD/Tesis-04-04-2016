package com.persistencia;

public class Consultas {

    protected final static String URL = "jdbc:mysql://localhost:3306/vaadin";
    protected final static String USER = "root";
    protected final static String DRIVER = "com.mysql.jdbc.Driver";
    protected final static String PASS  = "root";
    
    
    ////////////////////////<MONEDAS>/////////////////////////////////////////////////////
    
    public String getMonedas(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT cod_moneda, nom_moneda, simbolo_moneda FROM ct_monedas ");
                
        return sb.toString();
    }
	
   
    ////////////////////////<MONEDAS/>/////////////////////////////////////////////////////
    
    
    ////////////////////////<COTIZACIONES>/////////////////////////////////////////////////
    
    public String getCotizaciones(){
    	
     	 StringBuilder sb = new StringBuilder();
     	 
     	 sb.append("SELECT fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod ");
     	 sb.append("FROM ct_cotizaciones ");

     	 return sb.toString();
     }
    
    public String getCotizacion(){
    	
      	 StringBuilder sb = new StringBuilder();
      	 
      	 sb.append("SELECT fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod ");
      	 sb.append("FROM ct_cotizaciones WHERE fec_cotizacion = ? AND cod_moneda = ? ");

      	 return sb.toString();
      }
    
    
    public String insertCotizacion(){
    	
    	 StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO ct_cotizaciones (fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod) ");
    	 sb.append("VALUES (?, ?, ?, ?, ?, NOW())");
    	 
    	 return sb.toString();
    }
    
    public String insertImpuesto(){
    	
   	 StringBuilder sb = new StringBuilder();
   	 
   	 sb.append("INSERT INTO ct_impuestos (cod_impuesto, desc_impuesto, porcentaje_impuesto) ");
   	 sb.append("VALUES (?, ?, ?)");
   	 
   	 return sb.toString();
   }
	
    public String memberCotizacion(){
    	
   	 StringBuilder sb = new StringBuilder();
   	 
   	 sb.append("SELECT fec_cotizacion, cod_moneda ");
   	 sb.append("FROM ct_cotizaciones WHERE fec_cotizacion = ? AND cod_moneda = ? ");

   	 return sb.toString();
   }
    
   ////////////////////////<COTIZACIONES/>/////////////////////////////////////////////////
    
    
   ////////////////////////<DOCUMENTO ADUANERO>///////////////////////////////////////////
    
    
    public String memberDocumentoAduanero(){
    	
      	 StringBuilder sb = new StringBuilder();
      	      	 
      	 sb.append("SELECT cod_docum, nom_docum, activo, usuario_mod, fecha_mod ");
      	 sb.append("FROM ct_documaduan WHERE nom_docum = ? ");

      	 return sb.toString();
      }
    
    public String insertDocumentoAduanero(){
    	
   	 StringBuilder sb = new StringBuilder();
   	 
   	 sb.append("INSERT INTO ct_documaduan (nom_docum, activo, usuario_mod, fecha_mod) ");
   	 sb.append("VALUES (?, ?, ?, NOW()) ");
   	 
   	 return sb.toString();
   }
    
    public String getDocumentosAduanerosActivos(){
    	
    	 StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("SELECT cod_docum, nom_docum, activo, usuario_mod, fecha_mod ");
    	 sb.append("FROM ct_documaduan WHERE activo = 1 ");

    	 return sb.toString();
    }
   
    public String getDocumentosAduanerosTodos(){
    	
   	 StringBuilder sb = new StringBuilder();
   	 
   	 sb.append("SELECT cod_docum, nom_docum, activo, usuario_mod, fecha_mod ");
   	 sb.append("FROM ct_documaduan ");

   	 return sb.toString();
   }
    
    public String getDocumentoAduanero(){
   	
     	 StringBuilder sb = new StringBuilder();
     	 
     	 sb.append("SELECT cod_docum, nom_docum, activo, usuario_mod, fecha_mod ");
     	 sb.append("FROM ct_documaduan WHERE cod_docum = ?  ");

     	 return sb.toString();
     }
    
   ////////////////////////<DOCUMENTO ADUANERO/>//////////////////////////////////////////
    
    
    
    ////////////////////////<EMPRESAS>////////////////////////////////////////////////////
    
    
    public String memberEmpresa(){
    	
     	 StringBuilder sb = new StringBuilder();
     	      	 
     	 sb.append("SELECT cod_emp, nom_emp, activo, usuario_mod, fecha_mod ");
     	 sb.append("FROM ct_empresas WHERE nom_emp = ? ");

     	 return sb.toString();
     }
   
   
    
    
    ////////////////////////<EMPRESAS/>///////////////////////////////////////////////////
    
}
