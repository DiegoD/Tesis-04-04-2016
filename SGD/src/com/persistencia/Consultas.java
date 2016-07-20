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
    
    ////////////////////////<IMPUESTOS>///////////////////////////////////////////////////
    public String insertImpuesto(){
    	
      	 StringBuilder sb = new StringBuilder();
      	 
      	 sb.append("INSERT INTO ct_impuestos (cod_impuesto, desc_impuesto, porcentaje_impuesto) ");
      	 sb.append("VALUES (?, ?, ?)");
      	 
      	 return sb.toString();
    }
    
    public String getImpuestosTodos(){
    	 StringBuilder sb = new StringBuilder();
      	 
      	 sb.append("SELECT cod_impuesto, desc_impuesto, porcentaje_impuesto ");
      	 sb.append("FROM ct_impuestos ");

      	 return sb.toString();
    }
    
    
    ////////////////////////<IMPUESTOS/>///////////////////////////////////////////////////
    
////////////////////////INI-USUARIOS///////////////////////////////////////////////////
    
    public String getUsuarioValido(){
   	 StringBuilder sb = new StringBuilder();
     	 
     	 sb.append("SELECT nombre ");
     	 sb.append("FROM m_usuarios  ");
     	sb.append("WHERE usuario = ? AND pass = ?  ");

     	 return sb.toString();
    }
    
    public String getUsuarios(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT usuario, pass, nombre ");
    	sb.append("FROM m_usuarios ");

    	return sb.toString();
    }
    
    
   
////////////////////////FIN-USUARIOSS///////////////////////////////////////////////////
    
////////////////////////INI-GRUPOS///////////////////////////////////////////////////
    
    public String getGrupos()
    {
    	
    	 StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("SELECT cod_grupo, nombre, fecha_mod, usuario_mod, operacion, activo ");
    	 sb.append("FROM m_grupos ");

    	 return sb.toString();
    }
    
    public String insertarGrupo()
    {
    	
    	StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO vaadin.m_grupos (cod_grupo, nombre, usuario_mod, operacion, fecha_mod, activo)");
    	 sb.append("VALUES (?, ?, ?, ?, NOW(), ?) ");

    	 return sb.toString();
    	
    }
    
    public String memberGrupo()
    {
    	
      	 StringBuilder sb = new StringBuilder();
        	 
      	 sb.append("SELECT cod_grupo ");
      	 sb.append("FROM m_grupos  ");
      	 sb.append("WHERE cod_grupo = ? ");

      	 return sb.toString();
      }
    
    public String eliminarGrupo()
    {
    	 StringBuilder sb = new StringBuilder();
    	 
      	 sb.append("DELETE ");
      	 sb.append("FROM m_grupos  ");
      	 sb.append("WHERE cod_grupo = ? ");
      	 
      	 return sb.toString();
    }
    
    

    public String getFormulariosxGrupo()
    {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("SELECT g_formularios.formulario, g_formularios.nombre ");
    	sb.append("FROM m_grupoxform, g_formularios "); 
		sb.append("WHERE cod_grupo = ? ");
		sb.append("AND m_grupoxform.formulario = g_formularios.formulario ");
    	
    	return sb.toString();
    }
    
    public String eliminarFormulariosxGrupo()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("DELETE FROM m_grupoxform WHERE cod_grupo = ? ");

    	return sb.toString();
    }
    
    public String insertarFormulariosxGrupo()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("INSERT INTO vaadin.m_grupoxform (formulario, cod_grupo) ");
    	sb.append("VALUES (?, ?) ");
    	
    	return sb.toString();
    }
    
    public String getFormulariosNOGrupo()
    {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("SELECT formulario, nombre, fecha_mod, usuario_mod, operacion, activo ");
    	sb.append("FROM g_formularios ");
    	sb.append("WHERE formulario NOT IN (SELECT formulario FROM m_grupoxform WHERE cod_grupo = ? )");

    	return sb.toString();
    }
    
    
////////////////////////FIN-GRUPOS///////////////////////////////////////////////////
    
	public String getFormulariosxUsuario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT g_formularios.formulario, g_formularios.nombre FROM  ");
		sb.append("m_gruposxusu,  m_grupoxform, m_usuarios, g_formularios  ");
		sb.append("WHERE m_usuarios.usuario = m_gruposxusu.usuario  ");
		sb.append("AND m_grupoxform.cod_grupo = m_gruposxusu.cod_grupo  ");
		sb.append("AND g_formularios.formulario = m_grupoxform.formulario  ");
		sb.append("AND m_grupoxform.cod_emp = m_grupoxform.cod_emp  ");
		sb.append("AND m_usuarios.usuario = ? AND cod_emp = ? ");   
	
		
		return sb.toString();
	}
	
	
	public String getUsuariosxEmp()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_empresas.cod_emp, m_empresas.nom_emp  ");
		sb.append("FROM m_usuariosxemp, m_empresas    ");
		sb.append("WHERE usuario = ?  AND activo = 1");

		return sb.toString();
	}

    
}
