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
    	 sb.append("FROM m_grupos WHERE cod_emp = ?");

    	 return sb.toString();
    }
    
    public String getGrupo()
    {
    	
    	 StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("SELECT cod_grupo, nombre, fecha_mod, usuario_mod, operacion, activo ");
    	 sb.append("FROM m_grupos WHERE cod_grupo = ? AND cod_emp = ?");

    	 return sb.toString();
    }
    
    public String insertarGrupo()
    {
    	
    	StringBuilder sb = new StringBuilder();
    	 
    	 sb.append("INSERT INTO vaadin.m_grupos (cod_grupo, nombre, usuario_mod, operacion, fecha_mod, activo, cod_emp) ");
    	 sb.append("VALUES (?, ?, ?, ?, NOW(), ?, ?) ");

    	 return sb.toString();
    	
    }
    
    public String memberGrupo()
    {
    	
      	 StringBuilder sb = new StringBuilder();
        	 
      	 sb.append("SELECT cod_grupo ");
      	 sb.append("FROM m_grupos  ");
      	 sb.append("WHERE cod_grupo = ? AND cod_emp = ?");

      	 return sb.toString();
      }
    
    public String eliminarGrupo()
    {
    	 StringBuilder sb = new StringBuilder();
    	 
      	 sb.append("DELETE ");
      	 sb.append("FROM m_grupos  ");
      	 sb.append("WHERE cod_grupo = ? AND cod_emp = ?");
      	 
      	 return sb.toString();
    }
    
    public String getActualizarGrupo(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.m_grupos ");
      	sb.append("SET nombre = ?, ");
  		sb.append("fecha_mod = NOW(), ");
  		sb.append("usuario_mod = ?, ");
  		sb.append("operacion = ?, ");
  		sb.append("activo = ? ");
  		sb.append("WHERE cod_grupo = ? AND cod_emp = ?");
      	 
      	return sb.toString();
    }

    public String getFormulariosxGrupo()
    {

     StringBuilder sb = new StringBuilder();
     
     sb.append("SELECT g_formularios.formulario, g_formularios.nombre,  ");
     sb.append("m_grupoxform.leer, m_grupoxform.nuevo_editar, m_grupoxform.borrar  ");
     sb.append("FROM m_grupoxform, g_formularios "); 
     sb.append("WHERE cod_grupo = ? and cod_emp = ? ");
     sb.append("AND m_grupoxform.formulario = g_formularios.formulario ");
    //+ "AND (g_formularios <> 'MEmpresas' OR usuario = 'AppAdmin')");
     
     return sb.toString();

    }
    
    public String eliminarFormulariosxGrupo()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("DELETE FROM m_grupoxform WHERE cod_grupo = ? AND cod_emp = ?");

    	return sb.toString();
    }
    
    public String insertarFormulariosxGrupo()
    {
    	StringBuilder sb = new StringBuilder();
	
    	sb.append("INSERT INTO vaadin.m_grupoxform (formulario, cod_grupo, leer, nuevo_editar, borrar, cod_emp) ");
    	sb.append("VALUES (?, ?, ?, ?, ?, ?) ");
    	
    	return sb.toString();
    }
    
    public String getFormulariosNOGrupo()
    {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("SELECT formulario, nombre, fecha_mod, usuario_mod, operacion, activo ");
    	sb.append("FROM g_formularios ");
    	sb.append("WHERE formulario NOT IN (SELECT formulario FROM m_grupoxform WHERE cod_grupo = ? AND cod_emp = ?)");

    	return sb.toString();
    }
    
    
////////////////////////FIN-GRUPOS///////////////////////////////////////////////////
    
    /*Consulta permisos para todos los formularios del usuario*/
	public String getFormulariosxUsuario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT formulario, nombre, (CASE WHEN nuevo_editar = 1 THEN 1 ELSE leer END)leer, nuevo_editar, borrar FROM ( ");
		sb.append("SELECT g_formularios.formulario, g_formularios.nombre,  ");
		sb.append("MAX(m_grupoxform.leer) AS leer,  MAX(m_grupoxform.nuevo_editar) AS nuevo_editar,  MAX(m_grupoxform.borrar) AS borrar  ");
		sb.append("FROM g_formularios, m_grupoxform, m_gruposxusu ");
		sb.append("WHERE m_grupoxform.formulario = g_formularios.formulario ");
		sb.append("AND m_grupoxform.cod_grupo = m_gruposxusu.cod_grupo ");
		sb.append("AND m_gruposxusu.usuario = ? AND m_gruposxusu.cod_emp = ? ");
		sb.append("AND (m_grupoxform.formulario <> 'MEmpresas' OR m_gruposxusu.usuario = 'AppAdmin')");
		sb.append("GROUP BY g_formularios.formulario, g_formularios.nombre");
		sb.append(" )Aux ");
		
		return sb.toString();
	}
	
	/*Consulta permisos para un solo formulario*/
	public String getFormularioOperacionxUsuario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT formulario, nombre, (CASE WHEN nuevo_editar = 1 THEN 1 ELSE leer END)leer, nuevo_editar, borrar FROM ( ");
		sb.append("SELECT g_formularios.formulario, g_formularios.nombre,  ");
		sb.append("MAX(m_grupoxform.leer) AS leer,  MAX(m_grupoxform.nuevo_editar) AS nuevo_editar,  MAX(m_grupoxform.borrar) AS borrar  ");
		sb.append("FROM g_formularios, m_grupoxform, m_gruposxusu ");
		sb.append("WHERE m_grupoxform.formulario = g_formularios.formulario ");
		sb.append("AND m_grupoxform.cod_grupo = m_gruposxusu.cod_grupo ");
		sb.append("AND m_gruposxusu.usuario = ? AND m_gruposxusu.cod_emp = ? ");
		sb.append("AND g_formularios.formulario = ? ");
		sb.append("GROUP BY g_formularios.formulario, g_formularios.nombre");
		sb.append(" )Aux ");
		
		return sb.toString();
	}
	
	
	public String getUsuariosxEmp()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_empresas.cod_emp, m_empresas.nom_emp  ");
		sb.append("FROM m_usuariosxemp, m_empresas    ");
		sb.append("WHERE usuario = ?  AND activo = 1 " );
		sb.append("AND m_usuariosxemp.cod_emp = m_empresas.cod_emp ");

		return sb.toString();
	}
	
////////////////////////CLIENTES////////////////////////////////////////////////////////
	
	/**/
	public String getInsertCliente()
	{
		StringBuilder sb = new StringBuilder();
		
	 	sb.append("INSERT INTO m_clientes ( nom_tit, cod_emp, razon_social, tel, nro_dgi, cod_docdgi ");
	 	sb.append(", direccion, mail, activo, usuario_mod, operacion, fecha_mod, cod_tit ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			
		return sb.toString();
	}
	
	public String getActualizarCliente()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE m_clientes ");
		sb.append("SET  ");
			sb.append("nom_tit = ?,  ");
			sb.append("razon_social = ?, ");
			sb.append("tel = ?, ");
			sb.append("nro_dgi = ?, ");
			sb.append("cod_docdgi = ?, ");
			sb.append("direccion = ?, ");
			sb.append("mail = ?, ");
			sb.append("activo = ?, ");
			sb.append("usuario_mod = ?, ");
			sb.append("operacion = ?, ");
			sb.append("fecha_mod = ? ");
			sb.append("WHERE cod_tit = ? AND cod_emp = ? ");

		return sb.toString();
	}
	
	/**/
	public String getClientesTodos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_clientes.cod_tit, m_clientes.nom_tit, m_clientes.cod_emp, m_clientes.razon_social, m_clientes.tel, m_clientes.nro_dgi, m_documdgi.cod_docdgi, m_documdgi.nombre AS nomDoc , direccion, mail ");
		sb.append(", m_clientes.activo, m_clientes.usuario_mod, m_clientes.operacion, m_clientes.fecha_mod, m_titulares.tipo ");
		sb.append("FROM m_clientes, m_documdgi, m_titulares "
				+ " WHERE m_clientes.cod_docdgi = m_documdgi.cod_docdgi AND m_titulares.cod_tit = m_clientes.cod_tit"
				+ " AND m_titulares.cod_emp = m_clientes.cod_emp AND m_clientes.cod_emp = ? ");

		return sb.toString();
	}
	
	public String getClientesActivos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_clientes.cod_tit, m_clientes.nom_tit, m_clientes.cod_emp, m_clientes.razon_social, m_clientes.tel, m_clientes.nro_dgi, m_documdgi.cod_docdgi, m_documdgi.nombre AS nomDoc , direccion, mail ");
		sb.append(", m_clientes.activo, m_clientes.usuario_mod, m_clientes.operacion, m_clientes.fecha_mod, m_titulares.tipo ");
		sb.append("FROM m_clientes, m_documdgi, m_titulares "
				+ " WHERE m_clientes.cod_docdgi = m_documdgi.cod_docdgi AND m_titulares.cod_tit = m_clientes.cod_tit AND "
				+ " m_titulares.cod_emp = m_clientes.cod_emp AND m_clientes.cod_emp = ? AND m_clientes.activo = 1");


		return sb.toString();
	}
	
	/**/
	public String getMemberCliente()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_titulares WHERE cod_tit = ? AND cod_emp = ?");

		return sb.toString();
	}
	
	/**/
	public String getMemberClienteDocumentoNuevo()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_clientes WHERE cod_emp = ? AND cod_docdgi = ? AND nro_dgi = ?");

		return sb.toString();
	}
	
	/**/
	public String getMemberClienteDocumentoEditar()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_clientes WHERE cod_tit <> ? AND cod_emp = ? AND cod_docdgi = ? AND nro_dgi = ?");

		return sb.toString();
	}
	
////////////////////////FIN-CLIENTES///////////////////////////////////////////////////

////////////////////////FUNCIONARIOS////////////////////////////////////////////////////////
	
/**/
	public String getInsertFuncionario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_funcionarios ( nom_tit, cod_emp, tel, nro_dgi, cod_docdgi ");
		sb.append(", direccion, mail, activo, usuario_mod, operacion, fecha_mod, cod_tit) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		
		return sb.toString();
	}

	public String getActualizarFuncionario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE m_funcionarios ");
		sb.append("SET  ");
		sb.append("nom_tit = ?,  ");
		sb.append("tel = ?, ");
		sb.append("nro_dgi = ?, ");
		sb.append("cod_docdgi = ?, ");
		sb.append("direccion = ?, ");
		sb.append("mail = ?, ");
		sb.append("activo = ?, ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?, ");
		sb.append("fecha_mod = ? ");
		sb.append("WHERE cod_tit = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

/**/
	public String getFuncionariosTodos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit, cod_emp, tel, nro_dgi, m_documdgi.cod_docdgi, m_documdgi.nombre AS nomDoc , direccion, mail ");
		sb.append(", m_funcionarios.activo, m_funcionarios.usuario_mod, m_funcionarios.operacion, m_funcionarios.fecha_mod ");
		sb.append("FROM m_funcionarios, m_documdgi WHERE m_funcionarios.cod_docdgi = m_documdgi.cod_docdgi AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String getFuncionariosActivos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit, cod_emp, tel, nro_dgi, m_documdgi.cod_docdgi, m_documdgi.nombre AS nomDoc , direccion, mail ");
		sb.append(", m_funcionarios.activo, m_funcionarios.usuario_mod, m_funcionarios.operacion, m_funcionarios.fecha_mod ");
		sb.append("FROM m_funcionarios, m_documdgi WHERE m_funcionarios.cod_docdgi = m_documdgi.cod_docdgi AND cod_emp = ? AND m_funcionarios.activo = 1");
		
		
		return sb.toString();
	}

/**/
	public String getMemberFuncionario()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_titulares WHERE cod_tit = ? AND cod_emp = ? AND cod_tit <> 0 ");
		
		return sb.toString();
	}

	public String getMemberFuncionarioDocumentoNuevo()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_funcionarios WHERE  cod_emp = ? AND cod_docdgi = ? AND nro_dgi = ? ");
		
		return sb.toString();
	}
	
	public String getMemberFuncionarioDocumentoEditar()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tit, nom_tit ");
		sb.append("FROM m_funcionarios WHERE cod_tit <> ? AND cod_emp = ? AND cod_docdgi = ? AND nro_dgi = ? ");
		
		return sb.toString();
	}

////////////////////////FIN-FUNCIONARIOS///////////////////////////////////////////////////
	
	
	
////////////////////////DOCUMENTOS-DGI//////////////////////////////////////////////////
	
	public String getDocumentos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docdgi AS cod_documento, nombre AS descripcion, activo, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM m_documdgi ");
		
		return sb.toString();
	}

	public String insertarDocumento()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_documdgi (cod_docdgi, nombre, activo, fecha_mod, usuario_mod, operacion )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberDocumento(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docdgi ");
		sb.append("FROM m_documdgi WHERE cod_documento = ? ");
		
		return sb.toString();
	}


	public String actualizarDocumento(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE m_documdgi ");
		sb.append("SET nombre = ?, ");
		sb.append("activo = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE m_documdgi = ? ");
		
		return sb.toString();
	}
	
////////////////////////FIN-DOCUMENTOS-DGI//////////////////////////////////////////////

////////////////////////PROCESOS//////////////////////////////////////////////////

	public String getProcesos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_proceso, cod_cliente, m_clientes.nom_tit, fec_doc, nro_mega ");
		sb.append(", cod_docum, nro_docum, fec_docum, carpeta ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion AS nomMoneda, m_monedas.simbolo  ");
		sb.append(", imp_mo, tc_mov, imp_mn, imp_tr, kilos, marca, medio ");
		sb.append(", descripcion, observaciones, fecha_mod, usuario_mod ");
		sb.append(", operacion, activo  ");
		sb.append("FROM c_procesos , m_clientes, m_monedas  ");
		sb.append("WHERE c_procesos.cod_cliente = m_clientes.cod_tit   ");
		sb.append("AND c_procesos.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND cod_emp = ? ");
		
		
		return sb.toString();
	}

	public String getProcesosActivos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_proceso, cod_cliente, m_clientes.nom_tit, fec_doc, nro_mega ");
		sb.append(", cod_docum, nro_docum, fec_docum, carpeta ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion AS nomMoneda, m_monedas.simbolo  ");
		sb.append(", imp_mo, tc_mov, imp_mn, imp_tr, kilos, marca, medio ");
		sb.append(", descripcion, observaciones, fecha_mod, usuario_mod ");
		sb.append(", operacion, activo  ");
		sb.append("FROM c_procesos , m_clientes, m_monedas  ");
		sb.append("WHERE c_procesos.cod_cliente = m_clientes.cod_tit   ");
		sb.append("AND c_procesos.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND cod_emp = ? AND activo = 1");
		
		
		return sb.toString();
	}

	public String insertarProceso()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_procesos ( cod_cliente, fec_doc, nro_mega ");
		sb.append(", cod_docum, nro_docum, fec_docum, carpeta, cod_moneda, imp_mo, tc_mov, imp_mn ");
		sb.append(", imp_tr, kilos, marca, medio, descripcion, observaciones, fecha_mod, usuario_mod, operacion, activo) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
	
		
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
		
		sb.append("UPDATE c_procesos");
		sb.append("SET cod_cliente = ?, fec_doc = ?,nro_mega = ?,cod_docum = ?,nro_docum = ?,fec_docum = ?,");
		sb.append("carpeta = ?,cod_moneda = ?,imp_mo = ?,tc_mov = ?,imp_mn = ?,imp_tr = ?,kilos = ?,");
		sb.append("marca = ?,medio = ?,descripcion = ?,observaciones = ?,fecha_mod = NOW(),usuario_mod = ?, ");
		sb.append("operacion = ?,activo = ? ");
		sb.append("WHERE cod_proceso = ? ");
		
		
		return sb.toString();
	}
	
////////////////////////FIN PROCESOS//////////////////////////////////////////////
	
////////////////////////BANCOS///////////////////////////////////////////////////
	public String getBancos(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT cod_bco, nom_bco, cod_emp, tel, direccion, contacto, activo, usuario_mod, operacion, fecha_mod");
		sb.append(" FROM m_bancos WHERE cod_emp = ?");

		return sb.toString();
	}

	public String getBancosActivos(){
	
		StringBuilder sb = new StringBuilder();
	
		sb.append("SELECT cod_bco, nom_bco, cod_emp, tel, direccion, contacto, activo, usuario_mod, operacion, fecha_mod ");
		sb.append("FROM m_bancos WHERE cod_emp = ? AND activo = 1");
		
		
		return sb.toString();
	}

	public String insertarBanco()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_bancos (cod_bco, nom_bco, cod_emp, tel, direccion, contacto, activo, usuario_mod, operacion, fecha_mod) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	
		return sb.toString();
	}

	public String memberBanco(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_bco  ");
		sb.append("FROM m_bancos WHERE cod_bco = ? AND cod_emp = ? ");
		
		return sb.toString();
	}


	public String actualizarBanco(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE m_bancos ");
		sb.append("SET  ");
		sb.append("nom_bco = ?, ");
		sb.append("tel = ?, ");
		sb.append("direccion = ?, ");
		sb.append("contacto = ?, ");
		sb.append("activo = ?, ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?, ");
		sb.append("fecha_mod = ? ");
		sb.append("WHERE cod_bco = ? AND cod_emp = ? ");

		return sb.toString();
	}	
	
	
////////////////////////FIN BANCOS///////////////////////////////////////////////

////////////////////////CTAS BANCOS//////////////////////////////////////////////
	
	public String getCtasBancos(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT b.cod_ctabco, b.nom_cta, b.cod_bco, b.cod_emp, b.activo, b.cod_moneda, b.usuario_mod, b.operacion, b.fecha_mod, ");
		sb.append("m.descripcion, m.simbolo, m.acepta_cotizacion, m.activo activoMoneda, m.fecha_mod m_fecha_mod, m.usuario_mod m_usuario_mod, m.operacion m_operacion, m.cod_emp " );
		sb.append("FROM m_ctasbcos b, m_monedas m WHERE cod_bco = ? AND b.cod_emp = ? ");
		sb.append(" AND b.cod_moneda = m.cod_moneda AND b.cod_emp = m.cod_emp");

		return sb.toString();
	}

	public String getCtasBancosActivos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT b.cod_ctabco, b.nom_cta, b.cod_bco, b.cod_emp, b.activo, b.cod_moneda, b.usuario_mod, "
				+ " b.operacion, b.fecha_mod, m.descripcion, m.simbolo, m.acepta_cotizacion, m.activo activoMoneda, "
				+ " m.fecha_mod m_fecha_mod, m.usuario_mod m_usuario_mod, m.operacion m_operacion, m.cod_emp, nacional ");
		sb.append("FROM m_ctasbcos b, m_monedas m "
				+ " WHERE cod_bco = ? AND b.cod_emp = ? AND b.activo = 1 "
				+ " AND b.cod_moneda = m.cod_moneda AND b.cod_emp = m.cod_emp ");
		
		
		return sb.toString();
	}

	public String insertarCtaBanco()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_ctasbcos (cod_ctabco, nom_cta, cod_bco, cod_emp, activo, usuario_mod, operacion, fecha_mod, cod_moneda) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	
		return sb.toString();
	}

	public String memberCtasBanco(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_ctabco ");
		sb.append("FROM m_ctasbcos WHREE cod_bco = ? AND cod_emp = ? AND activo = 1 ");
		
		return sb.toString();
	}


	public String actualizarCtaBanco(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE m_ctasbcos ");
		sb.append("SET  ");
		sb.append("nom_cta = ?, ");
		sb.append("activo = ?, ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ?, ");
		sb.append("fecha_mod = ? ");
		sb.append("WHERE cod_ctabco = ? AND cod_bco = ? AND cod_emp = ? ");
		
		return sb.toString();
	}	
	
	public String memberCtaBanco(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_ctabco  ");
		sb.append("FROM m_ctasbcos WHERE cod_ctabco = ? AND cod_bco = ? AND cod_emp = ?  ");
		
		return sb.toString();
	}
////////////////////////FIN CTAS BANCOS///////////////////////////////////////////////
	
///////////////////////INGRESO COBRO/////////////////////////////////////////////////

	public String insertIngresoCobroCab(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_ingcobro (cod_docum, serie_docum, nro_docum, cod_tit, cod_cuenta ");
		sb.append(", cod_emp, fec_doc, fec_valor, cod_bco, cod_ctabco, cod_mpago, cod_doc_ref ");
		sb.append(", serie_doc_ref, nro_doc_ref, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov ");
		sb.append(", observaciones, nro_trans, fecha_mod, usuario_mod, operacion) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?) ");
		
		return sb.toString();
	}
	
	public String insertIngresoCobroCabDet(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_ingcobro (cod_cuenta, cod_emp, cod_docum, serie_docum, nro_docum, cod_proceso,  ");
		sb.append("cod_rubro, cuenta, fec_doc, fec_valor, cod_moneda, cod_impuesto, imp_impu_mn, imp_impu_mo,  ");
		sb.append("imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia, referencia2, ");
		sb.append("nro_trans, fecha_mod, usuario_mod, operacion, linea) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	}
	
	public String eliminarIngresoCobroCab(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_ingcobro WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String deleteIngresoCobroCabDet(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_ingcobro WHERE nro_trans = ?");
	
		
		return sb.toString();
	}
	
	

	public String getIngresoCobroCabTodos(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit ");
		sb.append(",c_ingcobro.cod_emp, fec_doc, fec_valor, COALESCE(m_bancos.cod_bco,'0') cod_bco ");
		sb.append(", COALESCE(m_bancos.nom_bco,'0') nom_bco, COALESCE(m_ctasbcos.cod_ctabco,'0') cod_ctabco");
		sb.append(", COALESCE(m_ctasbcos.nom_cta,'0') nom_cta, COALESCE(cod_mpago,'0') cod_mpago, cod_doc_ref, serie_doc_ref ");
		sb.append(", nro_doc_ref,	m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn ");
		sb.append(", imp_tot_mo, tc_mov, observaciones, nro_trans, c_ingcobro.fecha_mod, c_ingcobro.usuario_mod ");
		sb.append(", c_ingcobro.operacion, m_monedas.descripcion, m_monedas.simbolo, c_ingcobro.cod_cuenta   "); 
		sb.append("	FROM c_ingcobro ");
	
		sb.append("INNER JOIN m_monedas "); 
	 	sb.append("ON c_ingcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_ingcobro.cod_emp = m_monedas.cod_emp  ");
	
		sb.append("INNER JOIN m_titulares ");
	
		sb.append("ON c_ingcobro.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_ingcobro.cod_tit = m_titulares.cod_tit  ");
	
  		
		sb.append("LEFT JOIN  m_ctasbcos "); 
		sb.append("ON c_ingcobro.cod_emp = m_ctasbcos.cod_emp ");  
		sb.append("AND c_ingcobro.cod_ctabco = m_ctasbcos.cod_ctabco ");  
		sb.append("AND c_ingcobro.cod_bco = m_ctasbcos.cod_bco ");  
	
		sb.append("LEFT JOIN m_bancos ");
	
		sb.append("ON c_ingcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_ingcobro.cod_emp = m_bancos.cod_emp  "); 
		 
		sb.append("WHERE c_ingcobro.cod_emp = ?  AND c_ingcobro.cod_docum = 'ingcobro' and c_ingcobro.fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ?"); 
		
		return sb.toString();
	}

public String getIngresoCobroCabTodosOtros(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit ");
		sb.append(",c_ingcobro.cod_emp, fec_doc, fec_valor, COALESCE(m_bancos.cod_bco,'0') cod_bco ");
		sb.append(", COALESCE(m_bancos.nom_bco,'0') nom_bco, COALESCE(m_ctasbcos.cod_ctabco,'0') cod_ctabco");
		sb.append(", COALESCE(m_ctasbcos.nom_cta,'0') nom_cta, COALESCE(cod_mpago,'0') cod_mpago, cod_doc_ref, serie_doc_ref ");
		sb.append(", nro_doc_ref,	m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn ");
		sb.append(", imp_tot_mo, tc_mov, observaciones, nro_trans, c_ingcobro.fecha_mod, c_ingcobro.usuario_mod ");
		sb.append(", c_ingcobro.operacion, m_monedas.descripcion, m_monedas.simbolo, c_ingcobro.cod_cuenta   "); 
		sb.append("	FROM c_ingcobro ");
	
		sb.append("INNER JOIN m_monedas "); 
	 	sb.append("ON c_ingcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_ingcobro.cod_emp = m_monedas.cod_emp  ");
	
		sb.append("INNER JOIN m_titulares ");
	
		sb.append("ON c_ingcobro.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_ingcobro.cod_tit = m_titulares.cod_tit  ");
	
  		
		sb.append("LEFT JOIN  m_ctasbcos "); 
		sb.append("ON c_ingcobro.cod_emp = m_ctasbcos.cod_emp ");  
		sb.append("AND c_ingcobro.cod_ctabco = m_ctasbcos.cod_ctabco ");  
		sb.append("AND c_ingcobro.cod_bco = m_ctasbcos.cod_bco ");  
	
		sb.append("LEFT JOIN m_bancos ");
	
		sb.append("ON c_ingcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_ingcobro.cod_emp = m_bancos.cod_emp  "); 
		 
		sb.append("WHERE c_ingcobro.cod_emp = ? AND c_ingcobro.cod_docum = 'otrcobro' and c_ingcobro.fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ?"); 
		
		return sb.toString();
	}
	
	public String getIngresoCobroDetxTrans(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_ingcobro.cod_emp, cod_docum, serie_docum, nro_docum, ");
		sb.append("COALESCE(cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE cod_proceso =d_ingcobro.cod_proceso AND cod_emp = d_ingcobro.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, cuenta, fec_doc, fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, imp_impu_mn,  ");
		sb.append("imp_impu_mo, imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia,  ");
		sb.append("referencia2, nro_trans, d_ingcobro.fecha_mod, d_ingcobro.usuario_mod, d_ingcobro.operacion, d_ingcobro.linea, m_monedas.nacional ");
		
		
		sb.append("FROM d_ingcobro, m_monedas, m_impuestos, m_rubros, m_cuentas  ");

		sb.append("WHERE d_ingcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_ingcobro.cod_emp = m_monedas.cod_emp "); 
			
		sb.append(" AND d_ingcobro.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_ingcobro.cod_emp = m_impuestos.cod_emp "); 
			
		sb.append(" AND d_ingcobro.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_ingcobro.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND d_ingcobro.nro_trans  = ? ");
		
		sb.append(" AND d_ingcobro.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_ingcobro.cod_emp = m_cuentas.cod_emp "); 
		
		return sb.toString();
	}
	
	public String getIngresoCobroCabInd(){
		
		StringBuilder sb = new StringBuilder();
		 
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_clientes.cod_tit, m_clientes.nom_tit, m_cuentas.cod_cuenta, m_cuentas.descripcion,  c_ingcobro.cod_emp, fec_doc,  ");
		sb.append("fec_valor, m_bancos.cod_bco, m_bancos.nom_bco, m_ctasbcos.cod_ctabco, m_ctasbcos.nom_cta, cod_mpago, cod_doc_ref, serie_doc_ref, nro_doc_ref,  ");
		sb.append("m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn, imp_tot_mo, tc_mov, observaciones, nro_trans,  ");
		sb.append("fecha_mod, usuario_mod, operacion, m_monedas.descripcion, m_monedas.simbolo ");
		sb.append("FROM c_ingcobro, m_monedas, m_clientes, m_ctasbcos, m_cuentas WHERE cod_emp = ?   ");
		sb.append(" AND cod_docum = ? AND nro_docum = ? ");
		
		sb.append(" AND c_ingcobro.cod_moneda = m_monedas.cod_moneda ");
		sb.append("AND c_ingcobro.cod_emp = m_monedas.cod_emp ");
		
		sb.append("AND c_ingcobro.cod_emp = m_clientes.cod_emp  ");
		sb.append("AND c_ingcobro.cod_tit = m_clientes.cod_tit  ");
		
		sb.append("AND c_ingcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_ingcobro.cod_emp = m_bancos.cod_emp  ");
		
		sb.append(" AND c_ingcobro.cod_emp = m_ctasbcos.cod_emp  ");
		sb.append(" AND c_ingcobro.cod_ctabco = m_ctasbcos.cod_ctabco  ");
		sb.append(" AND c_ingcobro.cod_bco = m_ctasbcos.cod_bco  ");
		
		sb.append(" AND c_ingcobro.cod_emp = m_cuentas.cod_emp  ");
		sb.append(" AND c_ingcobro.cod_cuenta = m_cuentas.cod_cuenta  ");
		
		return sb.toString();
	}

		
	public String memberIngresoCobro(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_ingcobro WHERE nro_docum = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeGastoIngresoCobro(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM d_ingcobro WHERE nro_docum = ? AND cod_emp = ? AND cod_docum = 'Gasto' ");
		
		return sb.toString();
	}
	
	public String deleteIngresoCobroCab(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_ingcobro WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String deleteIngresoCobroDet(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_ingcobro WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	
//////////////////////FIN-INGRESO COBRO/////////////////////////////////////////////
 
///////////////////////EGRESO COBRO/////////////////////////////////////////////////

	public String insertEgresoCobroCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_egrcobro (cod_docum, serie_docum, nro_docum, cod_tit, cod_cuenta ");
		sb.append(", cod_emp, fec_doc, fec_valor, cod_bco, cod_ctabco, cod_mpago, cod_doc_ref ");
		sb.append(", serie_doc_ref, nro_doc_ref, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov ");
		sb.append(", observaciones, nro_trans, fecha_mod, usuario_mod, operacion) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?) ");
		
		return sb.toString();
	}
	
	public String insertEgresoCobroCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_egrcobro (cod_cuenta, cod_emp, cod_docum, serie_docum, nro_docum, cod_proceso,  ");
		sb.append("cod_rubro, cuenta, fec_doc, fec_valor, cod_moneda, cod_impuesto, imp_impu_mn, imp_impu_mo,  ");
		sb.append("imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia, referencia2, ");
		sb.append("nro_trans, fecha_mod, usuario_mod, operacion, linea) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	}
	
	public String eliminarEgresoCobroCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_egrcobro WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String deleteEgresoCobroCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_egrcobro WHERE nro_trans = ?");
		
		
		return sb.toString();
	}
	
	
	
	public String getEgresoCobroCabTodos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_egrcobro.cod_emp, fec_doc, fec_valor, COALESCE(m_bancos.cod_bco,'0') cod_bco ");
		sb.append(", COALESCE(m_bancos.nom_bco,'0') nom_bco, COALESCE(m_ctasbcos.cod_ctabco,'0') cod_ctabco");
		sb.append(", COALESCE(m_ctasbcos.nom_cta,'0') nom_cta, COALESCE(cod_mpago,'0') cod_mpago, cod_doc_ref, serie_doc_ref ");
		sb.append(", nro_doc_ref,	m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn ");
		sb.append(", imp_tot_mo, tc_mov, observaciones, nro_trans, c_egrcobro.fecha_mod, c_egrcobro.usuario_mod ");
		sb.append(", c_egrcobro.operacion, m_monedas.descripcion, m_monedas.simbolo, c_egrcobro.cod_cuenta   "); 
		sb.append("	FROM c_egrcobro ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_egrcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_egrcobro.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_egrcobro.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_egrcobro.cod_tit = m_titulares.cod_tit  ");
		
		
		sb.append("LEFT JOIN  m_ctasbcos "); 
		sb.append("ON c_egrcobro.cod_emp = m_ctasbcos.cod_emp ");  
		sb.append("AND c_egrcobro.cod_ctabco = m_ctasbcos.cod_ctabco ");  
		sb.append("AND c_egrcobro.cod_bco = m_ctasbcos.cod_bco ");  
		
		sb.append("LEFT JOIN m_bancos ");
		
		sb.append("ON c_egrcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_egrcobro.cod_emp = m_bancos.cod_emp  "); 
		
		sb.append("WHERE c_egrcobro.cod_emp = ? AND c_egrcobro.cod_docum = 'egrcobro' "
				+ "and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ? "); 
		
		return sb.toString();
	}
	
	public String getEgresoOtroCobroCabTodos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, tipo ");
		sb.append(",c_egrcobro.cod_emp, fec_doc, fec_valor, COALESCE(m_bancos.cod_bco,'0') cod_bco ");
		sb.append(", COALESCE(m_bancos.nom_bco,'0') nom_bco, COALESCE(m_ctasbcos.cod_ctabco,'0') cod_ctabco");
		sb.append(", COALESCE(m_ctasbcos.nom_cta,'0') nom_cta, COALESCE(cod_mpago,'0') cod_mpago, cod_doc_ref, serie_doc_ref ");
		sb.append(", nro_doc_ref,	m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn ");
		sb.append(", imp_tot_mo, tc_mov, observaciones, nro_trans, c_egrcobro.fecha_mod, c_egrcobro.usuario_mod ");
		sb.append(", c_egrcobro.operacion, m_monedas.descripcion, m_monedas.simbolo, c_egrcobro.cod_cuenta   "); 
		sb.append("	FROM c_egrcobro ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_egrcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_egrcobro.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_egrcobro.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_egrcobro.cod_tit = m_titulares.cod_tit  ");
		
		
		sb.append("LEFT JOIN  m_ctasbcos "); 
		sb.append("ON c_egrcobro.cod_emp = m_ctasbcos.cod_emp ");  
		sb.append("AND c_egrcobro.cod_ctabco = m_ctasbcos.cod_ctabco ");  
		sb.append("AND c_egrcobro.cod_bco = m_ctasbcos.cod_bco ");  
		
		sb.append("LEFT JOIN m_bancos ");
		
		sb.append("ON c_egrcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_egrcobro.cod_emp = m_bancos.cod_emp  "); 
		
		sb.append("WHERE c_egrcobro.cod_emp = ? AND c_egrcobro.cod_docum = 'otroegr' "
				+ "and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ? "); 
		
		return sb.toString();
	}
	
	public String getEgresoCobroDetxTrans(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_egrcobro.cod_emp, d_egrcobro.cod_docum, d_egrcobro.serie_docum, d_egrcobro.nro_docum, ");
		sb.append("COALESCE(d_egrcobro.cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE c_procesos.cod_proceso = d_egrcobro.cod_proceso AND c_procesos.cod_emp = d_egrcobro.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, d_egrcobro.cuenta, fec_doc, fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, d_egrcobro.imp_impu_mn,  ");
		sb.append("d_egrcobro.imp_impu_mo, d_egrcobro.imp_sub_mn, d_egrcobro.imp_sub_mo, d_egrcobro.imp_tot_mn, d_egrcobro.imp_tot_mo, d_egrcobro.tc_mov, d_egrcobro.referencia,  ");
		sb.append("d_egrcobro.referencia2, d_egrcobro.nro_trans, d_egrcobro.fecha_mod, d_egrcobro.usuario_mod, d_egrcobro.operacion, d_egrcobro.linea, c_gastos.estado ");
		
		
		sb.append("FROM d_egrcobro, m_monedas, m_impuestos, m_rubros, m_cuentas, c_gastos  ");
		
		sb.append("WHERE d_egrcobro.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_egrcobro.cod_emp = m_monedas.cod_emp "); 
		
		sb.append(" AND d_egrcobro.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_egrcobro.cod_emp = m_impuestos.cod_emp "); 
		
		sb.append(" AND d_egrcobro.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_egrcobro.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND d_egrcobro.nro_trans  = ? ");
		
		sb.append(" AND d_egrcobro.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_egrcobro.cod_emp = m_cuentas.cod_emp "
				+ " AND d_egrcobro.nro_docum = c_gastos.nro_docum AND d_egrcobro.cod_emp = c_gastos.cod_emp "); 
		
		return sb.toString();
	}
	
	public String getEgresoCobroCabInd(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_clientes.cod_tit, m_clientes.nom_tit, m_cuentas.cod_cuenta, m_cuentas.descripcion,  c_egrcobro.cod_emp, fec_doc,  ");
		sb.append("fec_valor, m_bancos.cod_bco, m_bancos.nom_bco, m_ctasbcos.cod_ctabco, m_ctasbcos.nom_cta, cod_mpago, cod_doc_ref, serie_doc_ref, nro_doc_ref,  ");
		sb.append("m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, imp_tot_mn, imp_tot_mo, tc_mov, observaciones, nro_trans,  ");
		sb.append("fecha_mod, usuario_mod, operacion, m_monedas.descripcion, m_monedas.simbolo ");
		sb.append("FROM c_egrcobro, m_monedas, m_clientes, m_ctasbcos, m_cuentas WHERE cod_emp = ?   ");
		sb.append(" AND cod_docum = ? AND nro_docum = ? ");
		
		sb.append(" AND c_egrcobro.cod_moneda = m_monedas.cod_moneda ");
		sb.append("AND c_egrcobro.cod_emp = m_monedas.cod_emp ");
		
		sb.append("AND c_egrcobro.cod_emp = m_clientes.cod_emp  ");
		sb.append("AND c_egrcobro.cod_tit = m_clientes.cod_tit  ");
		
		sb.append("AND c_egrcobro.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_egrcobro.cod_emp = m_bancos.cod_emp  ");
		
		sb.append(" AND c_egrcobro.cod_emp = m_ctasbcos.cod_emp  ");
		sb.append(" AND c_egrcobro.cod_ctabco = m_ctasbcos.cod_ctabco  ");
		sb.append(" AND c_egrcobro.cod_bco = m_ctasbcos.cod_bco  ");
		
		sb.append(" AND c_egrcobro.cod_emp = m_cuentas.cod_emp  ");
		sb.append(" AND c_egrcobro.cod_cuenta = m_cuentas.cod_cuenta  ");
		
		
		
		return sb.toString();
	}
	
	
	public String memberEgresoCobro(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_egrcobro WHERE nro_docum = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeEgresoCobro(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM d_egrcobro WHERE nro_docum = ? AND cod_emp = ? AND cod_docum = 'Gasto' ");
		
		return sb.toString();
	}
	
	public String deleteEgresoCobroCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_egrcobro WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String deleteEgresoCobroDet(){
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("DELETE FROM d_egrcobro WHERE nro_trans = ? ");
	
	return sb.toString();
	}


//////////////////////FIN-EGRESO COBRO/////////////////////////////////////////////	
	
///////////////////////FACTURA/////////////////////////////////////////////////

	public String insertFacturaCab(){
	
		StringBuilder sb = new StringBuilder();
		
			sb.append("INSERT INTO c_facturas (cod_docum, serie_docum, nro_docum, cod_tit, cod_cuenta ");
			sb.append(", cod_emp, fec_doc, fec_valor, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov ");
			sb.append(", observaciones, nro_trans, fecha_mod, usuario_mod, operacion, cod_proceso, impu_tot_mn, impu_tot_mo, imp_sub_mo, imp_sub_mn, tipo_factura ) ");
			sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ? ) ");
			
			return sb.toString();
	}

	public String insertFacturaCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_facturas (cod_cuenta, cod_emp, cod_docum, serie_docum, nro_docum, cod_proceso,  ");
		sb.append("cod_rubro, cuenta, fec_doc, fec_valor, cod_moneda, cod_impuesto, imp_impu_mn, imp_impu_mo,  ");
		sb.append("imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia, referencia2, ");
		sb.append("nro_trans, fecha_mod, usuario_mod, operacion, linea) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	}

	public String eliminarFacturaCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_facturas WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteFacturaCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_facturas WHERE nro_trans = ?");
		
		
		return sb.toString();
	}



	public String getFacturaCabTodos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_facturas.cod_emp, c_facturas.fec_doc, c_facturas.fec_valor, c_facturas.cod_proceso,  c_procesos.descripcion  ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, c_facturas.imp_tot_mn ");
		sb.append(", c_facturas.imp_tot_mo, c_facturas.tc_mov, c_facturas.observaciones, nro_trans, c_facturas.fecha_mod, c_facturas.usuario_mod ");
		sb.append(", c_facturas.operacion, m_monedas.descripcion, m_monedas.simbolo, c_facturas.cod_cuenta,   "); 
		sb.append("c_facturas.impu_tot_mn , c_facturas.impu_tot_mo, c_facturas.imp_sub_mo , c_facturas.imp_sub_mn, "
				+ "c_facturas.tipo_factura ");
		sb.append("	FROM c_facturas ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_facturas.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_facturas.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_facturas.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_facturas.cod_tit = m_titulares.cod_tit  ");
		
		sb.append("INNER JOIN c_procesos ");
		
		sb.append("ON c_facturas.cod_emp = c_procesos.cod_emp ");  
		sb.append("AND c_facturas.cod_proceso = c_procesos.cod_proceso  ");
		
		sb.append("WHERE c_facturas.cod_emp = ? and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ? "); 
		
		return sb.toString();
	}
	
	public String getFacturaConSaldoxMoneda(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_facturas.cod_docum, c_facturas.serie_docum, c_facturas.nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_facturas.cod_emp, c_facturas.fec_doc, c_facturas.fec_valor, c_facturas.cod_proceso,  c_procesos.descripcion  ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, sa_docum.imp_tot_mn ");
		sb.append(", sa_docum.imp_tot_mo, c_facturas.tc_mov, c_facturas.observaciones, nro_trans, c_facturas.fecha_mod, c_facturas.usuario_mod ");
		sb.append(", c_facturas.operacion, m_monedas.descripcion, m_monedas.simbolo, c_facturas.cod_cuenta,   "); 
		sb.append("c_facturas.impu_tot_mn , c_facturas.impu_tot_mo, c_facturas.imp_sub_mo , c_facturas.imp_sub_mn,  m_monedas.nacional, c_facturas.tipo_factura ");
		sb.append("	FROM c_facturas ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_facturas.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_facturas.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_facturas.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_facturas.cod_tit = m_titulares.cod_tit  ");
		
		sb.append("INNER JOIN c_procesos ");
		
		sb.append("ON c_facturas.cod_emp = c_procesos.cod_emp ");  
		sb.append("AND c_facturas.cod_proceso = c_procesos.cod_proceso  ");
		
		
		sb.append(" INNER JOIN sa_docum ON c_facturas.cod_docum = sa_docum.cod_docum  "); 
		sb.append(" AND c_facturas.serie_docum = sa_docum.serie_docum  "); 
		sb.append(" AND c_facturas.nro_docum = sa_docum.nro_docum  "); 
		sb.append(" AND c_facturas.cod_emp = sa_docum.cod_emp  "); 
		sb.append(" AND c_facturas.cod_tit = sa_docum.cod_tit "); 
		
		sb.append("WHERE c_facturas.cod_emp = ?  "); 
		sb.append("AND c_facturas.cod_moneda = ?  "); 
		sb.append("AND c_facturas.cod_tit = ?  "); 
		
		sb.append("AND sa_docum.imp_tot_mo <> 0 ");
		
		
		return sb.toString();
	}

	public String getFacturaxProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_facturas.cod_docum, c_facturas.serie_docum, c_facturas.nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_facturas.cod_emp, c_facturas.fec_doc, c_facturas.fec_valor, c_facturas.cod_proceso,  c_procesos.descripcion  ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, sa_docum.imp_tot_mn ");
		sb.append(", sa_docum.imp_tot_mo, c_facturas.tc_mov, c_facturas.observaciones, nro_trans, c_facturas.fecha_mod, c_facturas.usuario_mod ");
		sb.append(", c_facturas.operacion, m_monedas.descripcion, m_monedas.simbolo, c_facturas.cod_cuenta,   "); 
		sb.append("c_facturas.impu_tot_mn , c_facturas.impu_tot_mo, c_facturas.imp_sub_mo , c_facturas.imp_sub_mn,  m_monedas.nacional, c_facturas.tipo_factura ");
		sb.append("	FROM c_facturas ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_facturas.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_facturas.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_facturas.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_facturas.cod_tit = m_titulares.cod_tit  ");
		
		sb.append("INNER JOIN c_procesos ");
		
		sb.append("ON c_facturas.cod_emp = c_procesos.cod_emp ");  
		sb.append("AND c_facturas.cod_proceso = c_procesos.cod_proceso  ");
		
		
		sb.append(" INNER JOIN sa_docum ON c_facturas.cod_docum = sa_docum.cod_docum  "); 
		sb.append(" AND c_facturas.serie_docum = sa_docum.serie_docum  "); 
		sb.append(" AND c_facturas.nro_docum = sa_docum.nro_docum  "); 
		sb.append(" AND c_facturas.cod_emp = sa_docum.cod_emp  "); 
		sb.append(" AND c_facturas.cod_tit = sa_docum.cod_tit "); 
		
		sb.append("WHERE c_facturas.cod_emp = ?  "); 
		sb.append("AND c_facturas.cod_proceso = ?  "); 
		
		
		
		return sb.toString();
	}

	public String getFacturaDetxTrans(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_facturas.cod_emp, d_facturas.cod_docum, d_facturas.serie_docum, d_facturas.nro_docum, ");
		sb.append("COALESCE(d_facturas.cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE c_procesos.cod_proceso = d_facturas.cod_proceso AND c_procesos.cod_emp = d_facturas.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, d_facturas.cuenta, fec_doc, fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, d_facturas.imp_impu_mn,  ");
		sb.append("d_facturas.imp_impu_mo, d_facturas.imp_sub_mn, d_facturas.imp_sub_mo, d_facturas.imp_tot_mn, d_facturas.imp_tot_mo, d_facturas.tc_mov, d_facturas.referencia,  ");
		sb.append("d_facturas.referencia2, d_facturas.nro_trans, d_facturas.fecha_mod, d_facturas.usuario_mod, d_facturas.operacion, d_facturas.linea ");
		
		
		sb.append("FROM d_facturas, m_monedas, m_impuestos, m_rubros, m_cuentas  ");
		
		sb.append("WHERE d_facturas.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_facturas.cod_emp = m_monedas.cod_emp "); 
		
		sb.append(" AND d_facturas.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_facturas.cod_emp = m_impuestos.cod_emp "); 
		
		sb.append(" AND d_facturas.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_facturas.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND d_facturas.nro_trans  = ? ");
		
		sb.append(" AND d_facturas.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_facturas.cod_emp = m_cuentas.cod_emp  "); 
		
		return sb.toString();
	}
	
	public String getGastosFactura(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_facturas.cod_emp, d_facturas.cod_docum, d_facturas.serie_docum, d_facturas.nro_docum, ");
		sb.append("COALESCE(d_facturas.cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE c_procesos.cod_proceso = d_facturas.cod_proceso AND c_procesos.cod_emp = d_facturas.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, d_facturas.cuenta, d_facturas.fec_doc, d_facturas.fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, d_facturas.imp_impu_mn,  ");
		sb.append("d_facturas.imp_impu_mo, d_facturas.imp_sub_mn, d_facturas.imp_sub_mo, d_facturas.imp_tot_mn, d_facturas.imp_tot_mo, d_facturas.tc_mov, d_facturas.referencia,  ");
		sb.append("d_facturas.referencia2, d_facturas.nro_trans, d_facturas.fecha_mod, d_facturas.usuario_mod, d_facturas.operacion, d_facturas.linea, ");
		sb.append(" c_facturas.cod_tit, (SELECT nom_tit FROM m_titulares tit WHERE tit.cod_tit = c_facturas.cod_tit AND tit.cod_emp = c_facturas.cod_emp ) nom_tit ");
		
		sb.append("FROM c_facturas, d_facturas, m_monedas, m_impuestos, m_rubros, m_cuentas  ");
		
		sb.append("WHERE c_facturas.nro_trans = d_facturas.nro_trans  ");
		sb.append("AND d_facturas.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_facturas.cod_emp = m_monedas.cod_emp "); 
		
		sb.append(" AND d_facturas.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_facturas.cod_emp = m_impuestos.cod_emp "); 
		
		sb.append(" AND d_facturas.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_facturas.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND c_facturas.nro_docum  = ? AND c_facturas.serie_docum = ? ");
		sb.append("AND c_facturas.cod_docum = ? AND c_facturas.cod_emp = ?");
		
		sb.append(" AND d_facturas.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_facturas.cod_emp = m_cuentas.cod_emp  "); 
		sb.append("AND d_facturas.cod_docum = 'Gasto'");
		sb.append(" AND c_facturas.tipo_factura = 'Factura'");
		
		return sb.toString();
	}


	public String memberFactura(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_facturas WHERE nro_docum = ? AND serie_docum = ? AND cod_docum = ?"
				+ " AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeGasto(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM d_facturas WHERE nro_docum = ? "
				+ " AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String deleteFacturaCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_facturas WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteFacturaDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_facturas WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String getSaldoFactura(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT imp_tot_mo FROM sa_docum WHERE  nro_docum = ? AND serie_docum = ? AND cod_docum = ?  AND cod_emp = ? ");
		
		return sb.toString();
	}


//////////////////////FIN-FACTURA /////////////////////////////////////////////	

///////////////////////RECIBO/////////////////////////////////////////////////

	public String insertReciboCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_recibos (cod_docum, serie_docum, nro_docum, cod_tit, cod_cuenta ");
		sb.append(", cod_emp, fec_doc, fec_valor, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov ");
		sb.append(", observaciones, nro_trans, fecha_mod, usuario_mod, operacion, cod_proceso, impu_tot_mn, impu_tot_mo, imp_sub_mo, imp_sub_mn, ");
		sb.append("cod_bco, cod_ctabco,cod_mpago , cod_doc_ref, serie_doc_ref, nro_doc_ref)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?,  ");
		sb.append(" ?, ?, ?, ?, ?, ? ) ");
		
		return sb.toString();
	}

	public String insertReciboCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_recibos (cod_cuenta, cod_emp, cod_docum, serie_docum, nro_docum, cod_proceso,  ");
		sb.append("cod_rubro, cuenta, fec_doc, fec_valor, cod_moneda, cod_impuesto, imp_impu_mn, imp_impu_mo,  ");
		sb.append("imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia, referencia2, ");
		sb.append("nro_trans, fecha_mod, usuario_mod, operacion, linea) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	}

	public String eliminarReciboCab(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_recibos WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteReciboCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_recibos WHERE nro_trans = ?");
		
		
		return sb.toString();
	}

	public String getReciboCabTodos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_recibos.cod_emp, c_recibos.fec_doc, c_recibos.fec_valor, c_recibos.cod_proceso,  c_procesos.descripcion  ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, c_recibos.imp_tot_mn ");
		sb.append(", c_recibos.imp_tot_mo, c_recibos.tc_mov, c_recibos.observaciones, nro_trans, c_recibos.fecha_mod, c_recibos.usuario_mod ");
		sb.append(", c_recibos.operacion, m_monedas.descripcion, m_monedas.simbolo, c_recibos.cod_cuenta,   "); 
		sb.append("c_recibos.impu_tot_mn , c_recibos.impu_tot_mo, c_recibos.imp_sub_mo , c_recibos.imp_sub_mn, ");
		sb.append("COALESCE(m_bancos.cod_bco,'0') cod_bco, COALESCE(m_bancos.nom_bco,'0') nom_bco, ");
		sb.append("COALESCE(m_ctasbcos.cod_ctabco,'0') cod_ctabco, COALESCE(m_ctasbcos.nom_cta,'0') nom_cta, ");
		sb.append("COALESCE(cod_mpago,'0') cod_mpago, ");
		sb.append("cod_doc_ref, serie_doc_ref, nro_doc_ref "); 
		
		sb.append("	FROM c_recibos ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_recibos.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_recibos.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		
		sb.append("ON c_recibos.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_recibos.cod_tit = m_titulares.cod_tit  ");
		
		sb.append("INNER JOIN c_procesos ");
		
		sb.append("ON c_recibos.cod_emp = c_procesos.cod_emp ");  
		sb.append("AND c_recibos.cod_proceso = c_procesos.cod_proceso  ");
		
		sb.append("LEFT JOIN m_bancos ");
		sb.append("ON c_recibos.cod_bco = m_bancos.cod_bco  ");
		sb.append("AND c_recibos.cod_emp = m_bancos.cod_emp  "); 

		sb.append("LEFT JOIN  m_ctasbcos "); 
		sb.append("ON c_recibos.cod_emp = m_ctasbcos.cod_emp ");  
		sb.append("AND c_recibos.cod_ctabco = m_ctasbcos.cod_ctabco ");  
		sb.append("AND c_recibos.cod_bco = m_ctasbcos.cod_bco ");
		
		sb.append("WHERE c_recibos.cod_emp = ? and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ?  "); 
		
		return sb.toString();
	}

	
	
	public String getReciboDetxTrans(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_recibos.cod_emp, d_recibos.cod_docum, d_recibos.serie_docum, d_recibos.nro_docum, ");
		sb.append("COALESCE(d_recibos.cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE c_procesos.cod_proceso = d_recibos.cod_proceso AND c_procesos.cod_emp = d_recibos.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, d_recibos.cuenta, fec_doc, fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, d_recibos.imp_impu_mn,  ");
		sb.append("d_recibos.imp_impu_mo, d_recibos.imp_sub_mn, d_recibos.imp_sub_mo, d_recibos.imp_tot_mn, d_recibos.imp_tot_mo, d_recibos.tc_mov, d_recibos.referencia,  ");
		sb.append("d_recibos.referencia2, d_recibos.nro_trans, d_recibos.fecha_mod, d_recibos.usuario_mod, d_recibos.operacion, d_recibos.linea ");
		
		
		sb.append("FROM d_recibos, m_monedas, m_impuestos, m_rubros, m_cuentas  ");
		
		sb.append("WHERE d_recibos.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_recibos.cod_emp = m_monedas.cod_emp "); 
		
		sb.append(" AND d_recibos.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_recibos.cod_emp = m_impuestos.cod_emp "); 
		
		sb.append(" AND d_recibos.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_recibos.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND d_recibos.nro_trans  = ? ");
		
		sb.append(" AND d_recibos.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_recibos.cod_emp = m_cuentas.cod_emp  "); 
		
		return sb.toString();
	}


	public String memberRecibo(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_recibos WHERE nro_docum = ? AND serie_docum = ? AND cod_docum = ? ");
		sb.append( "AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String deleteReciboCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_recibos WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteReciboDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_recibos WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String existeReciboFactura(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM d_recibos WHERE nro_docum = ? AND serie_docum = ? AND cod_docum = ? ");
		sb.append( "AND cod_emp = ? ");
		
		return sb.toString();
	}


//////////////////////FIN-RECIBO /////////////////////////////////////////////	

///////////////////////NOTA DE CREDITO/////////////////////////////////////////////////

	public String insertNCCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_notacred (cod_docum, serie_docum, nro_docum, cod_tit, cod_cuenta ");
		sb.append(", cod_emp, fec_doc, fec_valor, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov ");
		sb.append(", observaciones, nro_trans, fecha_mod, usuario_mod, operacion, impu_tot_mn, impu_tot_mo, imp_sub_mo, imp_sub_mn) ");
		
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ? )  ");
	
		
		return sb.toString();
	}

	public String insertNCCabDet(){
	
	StringBuilder sb = new StringBuilder();
	
		sb.append("INSERT INTO d_notacred (cod_cuenta, cod_emp, cod_docum, serie_docum, nro_docum, cod_proceso,  ");
		sb.append("cod_rubro, cuenta, fec_doc, fec_valor, cod_moneda, cod_impuesto, imp_impu_mn, imp_impu_mo,  ");
		sb.append("imp_sub_mn, imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, referencia, referencia2, ");
		sb.append("nro_trans, fecha_mod, usuario_mod, operacion, linea) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
		
		return sb.toString();
	}

	public String eliminarNCCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_notacred WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteNCCabDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_notacred WHERE nro_trans = ?");
		
		
		return sb.toString();
	}

	public String getNCTodos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.tipo ");
		sb.append(",c_notacred.cod_emp, c_notacred.fec_doc, c_notacred.fec_valor  ");
		sb.append(", m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, c_notacred.imp_tot_mn ");
		sb.append(", c_notacred.imp_tot_mo, c_notacred.tc_mov, c_notacred.observaciones, nro_trans, c_notacred.fecha_mod, c_notacred.usuario_mod ");
		sb.append(", c_notacred.operacion, m_monedas.descripcion, m_monedas.simbolo, c_notacred.cod_cuenta,   "); 
		sb.append("c_notacred.impu_tot_mn , c_notacred.impu_tot_mo, c_notacred.imp_sub_mo , c_notacred.imp_sub_mn ");
		 
		sb.append("	FROM c_notacred ");
		
		sb.append("INNER JOIN m_monedas "); 
		sb.append("ON c_notacred.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND c_notacred.cod_emp = m_monedas.cod_emp  ");
		
		sb.append("INNER JOIN m_titulares ");
		sb.append("ON c_notacred.cod_emp = m_titulares.cod_emp ");  
		sb.append("AND c_notacred.cod_tit = m_titulares.cod_tit  ");
		
		
		sb.append("WHERE c_notacred.cod_emp = ? and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ?  "); 
		
		return sb.toString();
	}



	public String getNCDetxTrans(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion nom_cuenta, d_notacred.cod_emp, d_notacred.cod_docum, d_notacred.serie_docum, d_notacred.nro_docum, ");
		sb.append("COALESCE(d_notacred.cod_proceso,0) cod_proceso, (SELECT COALESCE(descripcion,'Sin-Asignar') FROM c_procesos WHERE c_procesos.cod_proceso = d_notacred.cod_proceso AND c_procesos.cod_emp = d_notacred.cod_emp ) nom_proceso,  ");
		sb.append("m_rubros.cod_rubro, m_rubros.descripcion nom_rubro, d_notacred.cuenta, fec_doc, fec_valor, m_monedas.cod_moneda, m_monedas.simbolo, m_monedas.descripcion nom_moneda ");
		sb.append(", m_impuestos.cod_impuesto, m_impuestos.descripcion nom_impuesto, m_impuestos.porcentaje, d_notacred.imp_impu_mn,  ");
		sb.append("d_notacred.imp_impu_mo, d_notacred.imp_sub_mn, d_notacred.imp_sub_mo, d_notacred.imp_tot_mn, d_notacred.imp_tot_mo, d_notacred.tc_mov, d_notacred.referencia,  ");
		sb.append("d_notacred.referencia2, d_notacred.nro_trans, d_notacred.fecha_mod, d_notacred.usuario_mod, d_notacred.operacion, d_notacred.linea ");
		
		
		sb.append("FROM d_notacred, m_monedas, m_impuestos, m_rubros, m_cuentas  ");
		
		sb.append("WHERE d_notacred.cod_moneda = m_monedas.cod_moneda  ");
		sb.append("AND d_notacred.cod_emp = m_monedas.cod_emp "); 
		
		sb.append(" AND d_notacred.cod_impuesto = m_impuestos.cod_impuesto "); 
		sb.append("AND d_notacred.cod_emp = m_impuestos.cod_emp "); 
		
		sb.append(" AND d_notacred.cod_rubro = m_rubros.cod_rubro  ");
		sb.append("AND d_notacred.cod_emp = m_rubros.cod_emp "); 
		sb.append("AND d_notacred.nro_trans  = ? ");
		
		sb.append(" AND d_notacred.cod_cuenta = m_cuentas.cod_cuenta  ");
		sb.append("AND d_notacred.cod_emp = m_cuentas.cod_emp  "); 
		
		return sb.toString();
	}


	public String memberNC(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_notacred WHERE nro_docum = ? AND serie_docum = ? AND cod_docum = ? ");
		sb.append( "AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeNCFactura(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM d_notacred WHERE nro_docum = ? AND serie_docum = ? AND cod_docum = ? ");
		sb.append( "AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String deleteNCCab(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_notacred WHERE nro_trans = ? ");
		
		return sb.toString();
	}

	public String deleteNCDet(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM d_notacred WHERE nro_trans = ? ");
		
		return sb.toString();
	}


//////////////////////FIN-NOTA DE CREDITO /////////////////////////////////////////////	
	
	public String getSaldoGasto(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT imp_tot_mo FROM sa_docum WHERE  nro_docum = ? AND cod_docum = 'Gasto'  AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	
	////////////////////////////////////////VISTAS//////////////////////////////////////
	
	public String getSaCuentas(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, cod_doc_ref, serie_doc_ref, nro_doc_ref, cod_bco, cod_ctabco, movimiento, cod_emp, cod_moneda, cod_tit, imp_tot_mn, imp_tot_mo, signo, cuenta, cod_cta, referencia, nro_trans, fec_valor, fec_doc, conciliado ");
		sb.append("FROM sa_cuentas WHERE cod_emp = ? ");
			
		
		return sb.toString();
	}
	
	
	public String getSaDocum(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum, serie_docum, nro_docum, cod_emp, cod_moneda, cod_tit, imp_tot_mn, imp_tot_mo, cuenta, fecha_mod, usuario_mod, operacion ");
		sb.append("FROM sa_docum WHERE cod_emp = ? ");
			
		
		return sb.toString();
	}
	
}
