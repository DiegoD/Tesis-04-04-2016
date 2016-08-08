package com.persistencia;

public class ConsultasDD {

    protected final static String URL = "jdbc:mysql://localhost:3306/vaadin";
    protected final static String USER = "root";
    protected final static String DRIVER = "com.mysql.jdbc.Driver";
    protected final static String PASS  = "root";
    
    
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
    	sb.append("SELECT usuario, pass, nombre, activo, usuario_mod, operacion, fecha_mod, mail ");
    	sb.append("FROM m_usuarios ");

    	return sb.toString();
    }
    
    
   
////////////////////////FIN-USUARIOSS///////////////////////////////////////////////////
    
////////////////////////INI-GRUPOS///////////////////////////////////////////////////
    
    public String getGrupos(){
    	
    	 StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("SELECT cod_grupo, nombre, fecha_mod, usuario_mod, operacion ");
    	 sb.append("FROM m_grupos ");

    	 return sb.toString();
    }
    
    public String insertarGrupo(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO vaadin.m_grupos (cod_grupo, nombre, usuario_mod, operacion, fecha_mod)");
    	 sb.append("VALUES (?, ?, ?, ?, NOW()) ");

    	 return sb.toString();
    	
    }
    
    public String memberGrupo(){
    	
      	 StringBuilder sb = new StringBuilder();
        	 
      	 sb.append("SELECT cod_grupo ");
      	 sb.append("FROM m_grupos  ");
      	 sb.append("WHERE cod_grupo = ? ");

      	 return sb.toString();
      }

    public String memberUsuario(){
    	
    	 StringBuilder sb = new StringBuilder();
      	 
    	 sb.append("SELECT usuario ");
    	 sb.append("FROM m_usuarios  ");
    	 sb.append("WHERE usuario = ? ");

    	 return sb.toString();
    }
	
	public String insertarUsuario(){
	   	
	   	StringBuilder sb = new StringBuilder();
	   	 
	   	sb.append("INSERT INTO vaadin.m_usuarios (usuario, nombre, pass, usuario_mod, operacion, fecha_mod, activo, mail)");
	  	 	sb.append("VALUES (?, ?, ?, ?, ?, NOW(), ?, ?) ");
	
	   	return sb.toString();
	}
	
	public String elminarUsuario(){
	   	
	   	StringBuilder sb = new StringBuilder();
	   	 
	   	sb.append("DELETE ");
	   	sb.append("FROM m_usuarios  ");
	   	sb.append("WHERE usuario = ? ");
	
	   	return sb.toString();
	}
	
	public String insertGrposxUsuario()
	{
		StringBuilder sb = new StringBuilder();
		
    	sb.append("INSERT INTO vaadin.m_gruposxusu (cod_grupo, usuario) ");
    	sb.append("VALUES (?, ?) ");
    	
    	return sb.toString();
	
	}
	
	public String getGrposxUsuario()
	{
		StringBuilder sb = new StringBuilder();
    	
		sb.append("SELECT m_grupos.cod_grupo, m_grupos.nombre ");
    	sb.append("FROM m_gruposxusu, m_grupos "); 
		sb.append("WHERE m_gruposxusu.usuario = ? ");
		sb.append("AND m_gruposxusu.cod_grupo = m_grupos.cod_grupo ");
    	
    	return sb.toString();
	
	}
////////////////////////FIN-GRUPOS///////////////////////////////////////////////////
    
	public String getGruposNoUsuario()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cod_grupo, nombre ");
		sb.append("FROM m_grupos ");
		sb.append("WHERE cod_grupo NOT IN (SELECT cod_grupo FROM m_gruposxusu WHERE usuario = ? )");
		return sb.toString();
	}
	
	public String insertarGruposxUsuario()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("INSERT INTO vaadin.m_gruposxusu (cod_grupo, usuario, cod_emp) ");
    	sb.append("VALUES (?, ?, ?) ");
    	
    	return sb.toString();
    }

	public String eliminarGruposxUsuario()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("DELETE FROM m_gruposxusu WHERE usuario = ? ");

    	return sb.toString();
    }
	
	public String getActualizarUsuario(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.m_usuarios ");
      	sb.append("SET nombre = ?, ");
      	sb.append("pass = ?, ");
  		sb.append("fecha_mod = NOW(), ");
  		sb.append("usuario_mod = ?, ");
  		sb.append("operacion = ?, ");
  		sb.append("activo = ?, ");
  		sb.append("mail = ?");
  		sb.append("WHERE usuario = ? ");
      	 
      	return sb.toString();
    }
    
	public String insertarUsuarioxEmp()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("INSERT INTO vaadin.m_usuariosxemp (cod_emp, usuario) ");
    	sb.append("VALUES (?, ? ) ");
    	
    	return sb.toString();
    }
	
////////////////////////INI-IMPUESTOS///////////////////////////////////////////////////
    
	public String getImpuestos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_impuestos ");
		
		return sb.toString();
	}
	
	public String insertarImpuesto()
    {
    	
    	StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO vaadin.m_impuestos (cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion )");
    	 sb.append("VALUES (?, ?, ?, ?, NOW(), ?, ?) ");

    	 return sb.toString();
    	
    }
    
    public String memberImpuesto()
    {
    	
      	 StringBuilder sb = new StringBuilder();
        	 
      	 sb.append("SELECT cod_impuesto ");
      	 sb.append("FROM m_impuestos  ");
      	 sb.append("WHERE cod_impuesto = ? ");

      	 return sb.toString();
      }
    
    public String eliminarImpuesto()
    {
    	 StringBuilder sb = new StringBuilder();
    	 
      	 sb.append("DELETE ");
      	 sb.append("FROM m_impuesto  ");
      	 sb.append("WHERE cod_impuesto = ? ");
      	 
      	 return sb.toString();
    }
    
    public String actualizarImpuesto(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.m_impuestos ");
      	sb.append("SET descripcion = ?, ");
      	sb.append("porcentaje = ?, ");
      	sb.append("activo = ?, ");
  		sb.append("fecha_mod = NOW(), ");
  		sb.append("usuario_mod = ?, ");
  		sb.append("operacion = ? ");
  		sb.append("WHERE cod_impuesto = ? ");
      	 
      	return sb.toString();
    }
	
    public String getImpuesto(){
     	 StringBuilder sb = new StringBuilder();
    	 
     	 sb.append("SELECT cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion ");
     	 sb.append("FROM m_impuestos  ");
     	 sb.append("WHERE cod_impuesto = ? ");
     	 return sb.toString();
    }
////////////////////////INI-MONEDAS///////////////////////////////////////////////////
    
	public String getMonedas(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_monedas ");
		
		return sb.toString();
	}

	public String insertarMoneda()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_monedas (cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion )");
		sb.append("VALUES (?, ?, ?, ?, ?, NOW(), ?, ?) ");
		
		return sb.toString();
	
	}

	public String memberMoneda()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda ");
		sb.append("FROM m_monedas  ");
		sb.append("WHERE cod_moneda = ? ");
		
		return sb.toString();
	}

	public String eliminarMoneda()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE ");
		sb.append("FROM m_moneda  ");
		sb.append("WHERE cod_moneda = ? ");
		
		return sb.toString();
	}

	public String actualizarMoneda(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_monedas ");
		sb.append("SET descripcion = ?, ");
		sb.append("simbolo = ?, ");
		sb.append("acepta_cotizacion = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?");
		sb.append("WHERE cod_moneda = ? ");
		
		return sb.toString();
	}
    
	

////////////////////////INI-EMPRESAS///////////////////////////////////////////////////
    
	public String getEmpresas(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_emp, nom_emp, fecha_mod, usuario_mod, operacion, activo ");
		sb.append("FROM m_empresas ");
		
		return sb.toString();
	}

	public String insertarEmpresa()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_empresas (cod_emp, nom_emp, fecha_mod, usuario_mod, operacion, activo )");
		sb.append("VALUES (?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

    public String memberEmpresa(){
    	
    	 StringBuilder sb = new StringBuilder();
    	      	 
    	 sb.append("SELECT cod_emp, nom_emp, activo, usuario_mod, fecha_mod ");
    	 sb.append("FROM m_empresas WHERE cod_emp = ? ");

    	 return sb.toString();
    }


	public String actualizarEmpresa(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_empresas ");
		sb.append("SET nom_emp = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE cod_emp = ? ");
		
		return sb.toString();
	}

////////////////////////INI-RUBROS///////////////////////////////////////////////////
    
	public String getRubros(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto ");
		sb.append("FROM m_rubros ");
		
		return sb.toString();
	}

	public String insertarRubro()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_rubros (cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberRubro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro ");
		sb.append("FROM m_rubros WHERE cod_rubro = ? ");
		
		return sb.toString();
	}


	public String actualizarRubro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_rubros ");
		sb.append("SET descripcion = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?, ");
		sb.append("cod_impuesto = ? ");
		sb.append("WHERE cod_rubro = ? ");
		
		return sb.toString();
	}

	

////////////////////////INI-DOCUEMNTOS///////////////////////////////////////////////////

	public String getDocumentos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_documento, descripcion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_documentos_aduaneros ");
		
		return sb.toString();
	}

	public String insertarDocumento()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_documentos_aduaneros (cod_documento, descripcion, activo, fecha_mod, usuario_mod, operacion )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberDocumento(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_documento ");
		sb.append("FROM m_documentos_aduaneros WHERE cod_documento = ? ");
		
		return sb.toString();
	}


	public String actualizarDocumento(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_documentos_aduaneros ");
		sb.append("SET descripcion = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE cod_documento = ? ");
		
		return sb.toString();
	}
    
////////////////////////INI-CÓDIGOS GENERALIZADOS///////////////////////////////////////////////////

	public String getCodigosGeneralizados(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT codigo, valor, descripcion, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_codigos_generalizados ");
		
		return sb.toString();
	}

	public String insertarCodigoGeneralizado()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_codigos_generalizados (codigo, valor, descripcion, fecha_mod, usuario_mod, operacion )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberCodigoGeneralizado(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT codigo, valor ");
		sb.append("FROM m_codigos_generalizados WHERE codigo = ? AND valor = ? ");
		
		return sb.toString();
	}


	public String actualizarCodigoGeneralizado(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_codigos_generalizados ");
		sb.append("SET descripcion = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE codigo = ? AND valor = ? ");
		
		return sb.toString();
	}

	public String eliminarCodigoGeneralizado()
    {
    	 StringBuilder sb = new StringBuilder();
    	 
      	 sb.append("DELETE ");
      	 sb.append("FROM m_codigos_generalizados  ");
      	 sb.append("WHERE codigo = ? AND valor = ? ");
      	 
      	 return sb.toString();
    }
	  
	public String getCodigosGeneralizadosxCodigo(){
		 
	  StringBuilder sb = new StringBuilder();
	  
	  sb.append("SELECT codigo, valor, descripcion, fecha_mod, usuario_mod, operacion ");
	  sb.append("FROM m_codigos_generalizados WHERE codigo = ? ");
	  
	  return sb.toString();
	}
}
