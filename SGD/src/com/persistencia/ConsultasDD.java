package com.persistencia;

public class ConsultasDD {

    protected final static String URL = "jdbc:mysql://localhost:3306/vaadin";
    protected final static String USER = "root";
    protected final static String DRIVER = "com.mysql.jdbc.Driver";
    protected final static String PASS  = "root";
    
    
//    ////////////////////////<COTIZACIONES>/////////////////////////////////////////////////
//    
//    public String getCotizaciones(){
//    	
//     	 StringBuilder sb = new StringBuilder();
//     	 
//     	 sb.append("SELECT fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod ");
//     	 sb.append("FROM ct_cotizaciones ");
//
//     	 return sb.toString();
//     }
//    
//    public String getCotizacion(){
//    	
//      	 StringBuilder sb = new StringBuilder();
//      	 
//      	 sb.append("SELECT fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod ");
//      	 sb.append("FROM ct_cotizaciones WHERE fec_cotizacion = ? AND cod_moneda = ? ");
//
//      	 return sb.toString();
//      }
//    
//    
//    public String insertCotizacion(){
//    	
//    	 StringBuilder sb = new StringBuilder();
//    	 
//    	 sb.append("INSERT INTO ct_cotizaciones (fec_cotizacion, cod_moneda, imp_venta, imp_compra, usuario_mod, fecha_mod) ");
//    	 sb.append("VALUES (?, ?, ?, ?, ?, NOW())");
//    	 
//    	 return sb.toString();
//    }
//
//    public String memberCotizacion(){
//    	
//   	 StringBuilder sb = new StringBuilder();
//   	 
//   	 sb.append("SELECT fec_cotizacion, cod_moneda ");
//   	 sb.append("FROM ct_cotizaciones WHERE fec_cotizacion = ? AND cod_moneda = ? ");
//
//   	 return sb.toString();
//   }
//    
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
    	sb.append("SELECT m_usuarios.usuario, m_usuarios.pass, m_usuarios.nombre, m_usuarios.activo, m_usuarios.usuario_mod, m_usuarios.operacion, m_usuarios.fecha_mod, m_usuarios.mail ");
    	sb.append("FROM m_usuarios, m_usuariosxemp WHERE m_usuarios.usuario = m_usuariosxemp.usuario ");
    	sb.append(" AND m_usuariosxemp.cod_emp = ? ");
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
    	sb.append("FROM m_gruposxusu, m_grupos, m_usuariosxemp  "); 
		sb.append("WHERE m_gruposxusu.usuario = ? ");
		sb.append("AND m_gruposxusu.cod_grupo = m_grupos.cod_grupo ");
		sb.append("AND m_usuariosxemp.usuario = m_gruposxusu.usuario ");
		sb.append("AND m_usuariosxemp.cod_emp = m_gruposxusu.cod_emp ");
		sb.append("AND m_grupos.cod_emp =  m_gruposxusu.cod_emp ");
		
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
		sb.append("FROM m_impuestos WHERE cod_emp = ?");
		
		return sb.toString();
	}
	
	
	public String getImpuestosActivos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_impuestos WHERE cod_emp = ? and activo = 1 ");
		
		return sb.toString();
	}
	
	public String insertarImpuesto()
    {
    	
    	StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO vaadin.m_impuestos (cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion, cod_emp )");
    	 sb.append("VALUES (?, ?, ?, ?, NOW(), ?, ?, ?) ");

    	 return sb.toString();
    	
    }
    
    public String memberImpuesto()
    {
    	
      	 StringBuilder sb = new StringBuilder();
        	 
      	 sb.append("SELECT cod_impuesto ");
      	 sb.append("FROM m_impuestos  ");
      	 sb.append("WHERE cod_impuesto = ? AND cod_emp = ? ");

      	 return sb.toString();
      }
    
    public String eliminarImpuesto()
    {
    	 StringBuilder sb = new StringBuilder();
    	 
      	 sb.append("DELETE ");
      	 sb.append("FROM m_impuesto  ");
      	 sb.append("WHERE cod_impuesto = ? AND cod_emp = ? ");
      	 
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
  		sb.append("WHERE cod_impuesto = ? AND cod_emp = ? ");
      	 
      	return sb.toString();
    }
	
    public String getImpuesto(){
     	 StringBuilder sb = new StringBuilder();
    	 
     	 sb.append("SELECT cod_impuesto, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion ");
     	 sb.append("FROM m_impuestos  ");
     	 sb.append("WHERE cod_impuesto = ? AND cod_emp = ? ");
     	 return sb.toString();
    }
////////////////////////INI-MONEDAS///////////////////////////////////////////////////
    
	public String getMonedas(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_monedas WHERE cod_emp = ?");
		
		return sb.toString();
	}
	
	public String getMonedasActivas(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_monedas WHERE cod_emp = ? and activo = 1 ");
		
		return sb.toString();
	}

	public String insertarMoneda()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_monedas (cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion, cod_emp )");
		sb.append("VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	
	}

	public String memberMoneda()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda ");
		sb.append("FROM m_monedas  ");
		sb.append("WHERE cod_moneda = ? AND cod_emp = ?");
		
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
		sb.append("WHERE cod_moneda = ? AND cod_emp = ?");
		
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
		
		sb.append("SELECT cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp ");
		sb.append("FROM m_rubros ");
		sb.append("WHERE cod_emp = ? ");
		return sb.toString();
	}
	
	public String getRubrosActivos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp ");
		sb.append("FROM m_rubros ");
		sb.append("WHERE cod_emp = ? and activo = 1 ");
		return sb.toString();
	}

	
	public String insertarRubro()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_rubros (cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberRubro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro ");
		sb.append("FROM m_rubros ");
		sb.append("WHERE cod_rubro = ? AND cod_emp = ? ");
		
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
		sb.append("cod_impuesto = ?, ");
		sb.append("cod_tipo_rubro = ? ");
		sb.append("WHERE cod_rubro = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

	

////////////////////////INI-DOCUEMNTOS///////////////////////////////////////////////////

	public String getDocumentos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_documento, descripcion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_documentos_aduaneros WHERE cod_emp = ? ");
		
		return sb.toString();
	}

	public String insertarDocumento()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_documentos_aduaneros (cod_documento, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_emp )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberDocumento(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_documento ");
		sb.append("FROM m_documentos_aduaneros WHERE cod_documento = ? AND cod_emp = ? ");
		
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
		sb.append("WHERE cod_documento = ? AND cod_emp = ?");
		
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
	
////////////////////////INI-COTIZACIONES///////////////////////////////////////////////////

	public String getCotizaciones(){
	
		StringBuilder sb = new StringBuilder();
		
				
		sb.append("SELECT m_cotizaciones.cod_moneda, m_cotizaciones.fecha, m_cotizaciones.cotizacion_compra, "
				+ "m_cotizaciones.cotizacion_venta, m_cotizaciones.fecha_mod, m_cotizaciones.usuario_mod, "
				+ "m_cotizaciones.operacion, m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, m_monedas.acepta_cotizacion, m_monedas.activo ");
		sb.append("FROM m_cotizaciones, m_monedas WHERE m_cotizaciones.cod_moneda = m_monedas.cod_moneda ");
		sb.append(" AND m_cotizaciones.cod_emp = ? ");
		
		return sb.toString();
	}

	public String insertarCotizacion()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_cotizaciones (cod_moneda, fecha, cotizacion_compra, cotizacion_venta, fecha_mod, usuario_mod, operacion, cod_emp )");
		sb.append("VALUES (?, ?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberCotizacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda ");
		sb.append("FROM m_cotizaciones WHERE cod_moneda = ? and fecha = ? AND cod_emp = ?");
		
		return sb.toString();
	}


	public String actualizarCotizacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_cotizaciones ");
		sb.append("SET cotizacion_compra = ?, ");
		sb.append("cotizacion_venta = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE cod_moneda = ? and fecha = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

////////////////////////FIN-COTIZACIONES///////////////////////////////////////////////////
	
	
////////////////////////INI-TIPO RUBRO///////////////////////////////////////////////////
    
	public String getTipoRubros(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tiporubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp ");
		sb.append("FROM m_tipoRubro WHERE cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String getTipoRubrosActivos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tiporubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp ");
		sb.append("FROM m_tipoRubro WHERE cod_emp = ? and activo = 1 ");
		
		return sb.toString();
	}

	public String insertarTipoRubro()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_tiporubro (cod_tipoRubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp )");
		sb.append("VALUES (?, ?, NOW(), ?, ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberTipoRubro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tipoRubro, descripcion, activo, usuario_mod, fecha_mod ");
		sb.append("FROM m_tiporubro WHERE cod_tipoRubro = ? AND cod_emp = ? ");
		
		return sb.toString();
	}


	public String actualizarTipoRubro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_tiporubro ");
		sb.append("SET descripcion = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE cod_tipoRubro = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String getTipoRubro(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tiporubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp ");
		sb.append("FROM m_tipoRubro WHERE cod_tipoRubro = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
////////////////////////INI-CUENTAS///////////////////////////////////////////////////
    
	public String getCuentas()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_cuenta, descripcion, fecha_mod, usuario_mod, operacion, activo ");
		sb.append("FROM m_cuentas WHERE cod_emp = ? ");
		
		return sb.toString();
	}

	public String getCuenta()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_cuenta, descripcion, fecha_mod, usuario_mod, operacion, activo ");
		sb.append("FROM m_cuentas WHERE cod_cuenta = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String insertarCuenta()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_cuentas (cod_cuenta, descripcion, usuario_mod, operacion, fecha_mod, activo, cod_emp) ");
		sb.append("VALUES (?, ?, ?, ?, NOW(), ?, ?) ");
		
		return sb.toString();
	
	}

	public String memberCuenta()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_cuenta ");
		sb.append("FROM m_cuentas  ");
		sb.append("WHERE cod_cuenta = ? AND cod_emp = ?");
		
		return sb.toString();
	}

	public String eliminarGrupo()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE ");
		sb.append("FROM m_cuentas  ");
		sb.append("WHERE cod_cuenta = ? AND cod_emp = ?");
		
		return sb.toString();
	}

	public String getActualizarCuenta(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.m_cuentas ");
		sb.append("SET descripcion = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?, ");
		sb.append("activo = ? ");
		sb.append("WHERE cod_cuenta = ? AND cod_emp = ?");
		
		return sb.toString();
	}

	public String getRubrosxCuenta()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_rubros.cod_rubro, m_rubros.descripcion, ");
		sb.append("m_rubrosxcuenta.oficina, m_rubrosxcuenta.proceso, m_rubrosxcuenta.persona ");
		sb.append("FROM m_rubros, m_rubrosxcuenta ");
		sb.append("WHERE m_rubrosxcuenta.cod_cuenta = ? and m_rubros.cod_emp = ? ");
		sb.append("AND m_rubros.cod_rubro = m_rubrosxcuenta.cod_rubro ");
		
		return sb.toString();
	}

	public String eliminarRubrosxCuenta()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM m_rubrosxcuenta WHERE cod_cuenta = ? AND cod_emp = ?");
		
		return sb.toString();
	}

	public String insertarRubrosxCuenta()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_rubrosxcuenta (cod_cuenta, cod_rubro, oficina, proceso, persona, cod_emp) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?) ");
		
		return sb.toString();
	}

	public String getRubrosNOCuenta()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.fecha_mod, "
				+ "m_rubros.usuario_mod, m_rubros.operacion, m_rubros.activo, m_rubros.cod_impuesto, "
				+ "m_tiporubro.cod_tipoRubro, m_tiporubro.descripcion, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion ");
		sb.append("FROM m_rubros, m_tiporubro, m_impuestos ");
		sb.append("WHERE cod_rubro NOT IN (SELECT cod_rubro FROM m_rubrosxcuenta WHERE cod_cuenta = ? AND cod_emp = ?) ");
		sb.append("AND m_rubros.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = m_rubros.cod_emp ");
		sb.append("AND m_rubros.cod_tipo_rubro = m_tiporubro.cod_tipoRubro AND m_tiporubro.cod_emp = m_rubros.cod_emp "
				+ "AND m_rubros.activo = 1 ");
		
		return sb.toString();
	}


////////////////////////FIN-CUENTAS///////////////////////////////////////////////////
	
////////////////////////PROCESOS//////////////////////////////////////////////////

	public String getProcesos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_procesos.cod_proceso, c_procesos.fecha, c_procesos.numero_documento, "
				+ "c_procesos.fecha_documento, c_procesos.numero_mega, c_procesos.carpeta, "
				+ "c_procesos.importe_moneda, c_procesos.importe_moneda_nacional, c_procesos.importe_transaccion, "
				+ "c_procesos.tasa_cambio, c_procesos.peso, c_procesos.fecha_cruce, c_procesos.marca, "
				+ "c_procesos.medio, c_procesos.descripcion, c_procesos.observaciones, "
				+ "c_procesos.fecha_mod, c_procesos.usuario_mod, c_procesos.operacion, "
				+ "m_documentos_aduaneros.cod_documento, m_documentos_aduaneros.descripcion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo ");
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento"
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit"
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda "
				+ " AND c_procesos.cod_emp = ? ");  
		
		return sb.toString();
	}

//	public String getProcesosActivos(){
//	
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("SELECT cod_proceso, cod_cliente, m_clientes.nom_tit, fec_doc, nro_mega ");
//		sb.append(", m_documentos_aduaneros.cod_documento, m_documentos_aduaneros.descripcion, nro_docum, fec_docum, carpeta ");
//		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion AS nomMoneda, m_monedas.simbolo  ");
//		sb.append(", imp_mo, tc_mov, imp_mn, imp_tr, kilos, marca, medio ");
//		sb.append(", descripcion, observaciones, fecha_mod, usuario_mod ");
//		sb.append(", operacion, activo  ");
//		sb.append("FROM c_procesos , m_clientes, m_monedas  ");
//		sb.append("WHERE c_procesos.cod_cliente = m_clientes.cod_tit   ");
//		sb.append("AND c_procesos.cod_moneda = m_monedas.cod_moneda  ");
//		sb.append("AND m_documentos_aduaneros.cod_documento = c_procesos.cod_documento");
//		sb.append("AND cod_emp = ? AND activo = 1");
//		
//		
//		return sb.toString();
//	}

	public String insertarProceso()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_procesos ( fecha, numero_documento, fecha_documento, numero_mega, "
				+ "carpeta, importe_moneda, importe_moneda_nacional, importe_transaccion, "
				+ "tasa_cambio, peso, fecha_cruce, marca, medio, descripcion, observaciones, "
				+ "fecha_mod, usuario_mod, operacion, "
				+ "cod_documento, cod_cliente, cod_moneda, cod_emp, cod_proceso ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?) ");
		
		return sb.toString();
	}

	public String memberProceso(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_proceso ");
		sb.append("FROM c_procesos WHERE cod_proceso = ? AND cod_emp = ? ");
		
		return sb.toString();
	}


		public String actualizarProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE c_procesos ");
		sb.append("SET fecha = ?, numero_documento = ?, fecha_documento = ?, numero_mega = ?, "
				+ "carpeta = ?, importe_moneda = ?, importe_moneda_nacional = ?, importe_transaccion = ?, "
				+ "tasa_cambio = ?, peso = ?, fecha_cruce = ?, marca = ?, medio = ?, descripcion = ?, "
				+ "observaciones = ?, fecha_mod = NOW(), usuario_mod = ?, operacion = ?, "
				+ "cod_documento = ?, cod_cliente = ?, cod_moneda = ? ");
		sb.append("WHERE cod_proceso = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

////////////////////////FIN PROCESOS//////////////////////////////////////////////
		
////////////////////////INI NUMERADORES//////////////////////////////////////////////

		
	public String getNumero()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT numero ");
		sb.append("FROM g_numeradores WHERE cod_numerador = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String actualizarNumero()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.g_numeradores ");
		sb.append("SET numero = ? ");
		sb.append("WHERE cod_numerador = ? AND cod_emp = ?");
		
		return sb.toString();
	}
		
////////////////////////FIN NUMERADORES//////////////////////////////////////////////
}
