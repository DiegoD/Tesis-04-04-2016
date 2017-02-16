package com.persistencia;

public class ConsultasDD {

    protected final static String URL = "jdbc:mysql://localhost:3306/vaadin";
    protected final static String USER = "root";
    protected final static String DRIVER = "com.mysql.jdbc.Driver";
    protected final static String PASS  = "root";
    
    
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
    	sb.append("SELECT m_usuarios.usuario, m_usuarios.pass, m_usuarios.nombre, m_usuarios.activo, m_usuarios.usuario_mod, m_usuarios.operacion, m_usuarios.fecha_mod, m_usuarios.mail, m_usuarios.cod_tit ");
    	sb.append(",(SELECT CASE WHEN m_usuarios.cod_tit = 0 THEN 'No Asignado' ELSE (SELECT m_clientes.nom_tit FROM m_clientes WHERE m_clientes.cod_tit = m_usuarios.cod_tit AND m_usuariosxemp.cod_emp = m_clientes.cod_emp) END) nom_tit  ");
    	sb.append("FROM m_usuarios, m_usuariosxemp WHERE m_usuarios.usuario = m_usuariosxemp.usuario ");
    	sb.append(" AND m_usuariosxemp.cod_emp = ? ");
    	return sb.toString();
    }
    
    public String getUsuarioTitular(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT  m_usuarios.cod_tit ");
    	sb.append(",(SELECT CASE WHEN m_usuarios.cod_tit = 0 THEN 'No Asignado' ELSE (SELECT m_clientes.nom_tit FROM m_clientes WHERE m_clientes.cod_tit = m_usuarios.cod_tit AND m_usuariosxemp.cod_emp = m_clientes.cod_emp) END) nom_tit  ");
    	sb.append("FROM m_usuarios, m_usuariosxemp WHERE m_usuarios.usuario = m_usuariosxemp.usuario ");
    	sb.append(" AND m_usuariosxemp.cod_emp = ? AND m_usuarios.usuario = ?");
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
	   	 
	   	sb.append("INSERT INTO vaadin.m_usuarios (usuario, nombre, pass, usuario_mod, operacion, fecha_mod, activo, mail, cod_tit)");
	  	 	sb.append("VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?) ");
	
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
		sb.append("WHERE cod_grupo NOT IN (SELECT cod_grupo FROM m_gruposxusu WHERE usuario = ? )"
				+ " AND cod_emp = ? ");
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
  		sb.append("mail = ? ,");
  		sb.append("cod_tit = ? ");
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
		
		sb.append("SELECT cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion, "
				+ "nacional ");
		sb.append("FROM m_monedas WHERE cod_emp = ?");
		
		return sb.toString();
	}
	
	public String getMonedasActivas(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, usuario_mod, operacion, "
				+ "nacional ");
		sb.append("FROM m_monedas WHERE cod_emp = ? and activo = 1 ");
		
		return sb.toString();
	}

	public String insertarMoneda()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_monedas (cod_moneda, descripcion, simbolo, acepta_cotizacion, activo, fecha_mod, "
				+ "usuario_mod, operacion, cod_emp, nacional )");
		sb.append("VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ? ) ");
		
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
	
	public String existeNacional()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_moneda ");
		sb.append("FROM m_monedas  ");
		sb.append("WHERE cod_emp = ? and nacional = 1 ");
		
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
		sb.append("operacion = ?, ");
		sb.append("nacional = ? ");
		sb.append("WHERE cod_moneda = ? AND cod_emp = ?");
		return sb.toString();
	}
    
	

////////////////////////INI-EMPRESAS///////////////////////////////////////////////////
    
	public String getEmpresas(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_emp, nom_emp, fecha_mod, usuario_mod, operacion, activo ");
		sb.append("FROM m_empresas where cod_emp <> 01 ");
		
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
	
	public String insMonedaDolares(){
		
		StringBuilder sb = new StringBuilder();
		
	
		sb.append("INSERT INTO vaadin.m_monedas (cod_moneda, descripcion ");
		sb.append(", simbolo, acepta_cotizacion, activo, fecha_mod ");
		sb.append(", usuario_mod, operacion, cod_emp, nacional) ");
		sb.append("VALUES ('2', 'Dolares', 'U$S', 1, 1, NOW(), 'SISTE', 'NUEVO', ?, 0) ");
		
		return sb.toString();
	}
	
	public String insMonedaNacional(){
		
		StringBuilder sb = new StringBuilder();
		
	
		sb.append("INSERT INTO vaadin.m_monedas (cod_moneda, descripcion ");
		sb.append(", simbolo, acepta_cotizacion, activo, fecha_mod ");
		sb.append(", usuario_mod, operacion, cod_emp, nacional) ");
		sb.append("VALUES ('1', 'Pesos', '$', 1, 1, NOW(), 'SISTE', 'NUEVO', ?, 1) ");
		
		return sb.toString();
	}
	
	public String insBcoNoAsignado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_bancos (cod_bco, nom_bco, cod_emp, tel, direccion, contacto, activo, usuario_mod, operacion, fecha_mod) ");
		sb.append("VALUES ('0', 'No asginado', ?, '0', '0', '0', 0, NOW(), 'NUEVO', '2016-09-04 14:45:03') ");
		
		return sb.toString();
	}
	
	public String insImuestoExento(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_impuestos (cod_impuesto, cod_emp, descripcion, porcentaje, activo, fecha_mod, usuario_mod, operacion) ");
		sb.append("VALUES ('0', ?, 'Exento', 0, 1, NOW(), 'SISTE', 'EDITAR')  ");
		
		return sb.toString();
	}
	
	public String insTipoRubroNoAsignado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_tiporubro (cod_tipoRubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp) ");
		sb.append("VALUES ('0', 'No Asignado', NOW(), 'SISTE', 'NUEVO', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insRubroNoAsignado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_rubros (cod_rubro, descripcion, activo, cod_tipo_rubro, tipo_rubro, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_emp, facturable) ");
		sb.append("VALUES ('0', 'No Asignado', 0, '0', NULL, NOW(), 'p', 'EDITAR', '0', ?, 1)  ");
		
		return sb.toString();
	}
	
	public String insTitularOficina(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_titulares (cod_tit, nom_tit, cod_docdgi, nro_dgi, tipo, cod_emp, activo)  ");
		sb.append("VALUES (0, 'Oficina', '01', '0', 'funcionario', ?, 1)  ");
		
		return sb.toString();
	}
	
	public String insCuentaFacturas(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_cuentas (cod_cuenta, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp)  ");
		sb.append("VALUES ('factura', 'Facturas', NOW(), 'SISTE', 'NUEVO', 1, ?)  ");
		
		return sb.toString();
	}
	
	public String insProcesoNoAsig(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.c_procesos (cod_proceso, cod_documento, cod_cliente, cod_moneda, fecha, numero_documento, fecha_documento, numero_mega, carpeta, importe_moneda, importe_moneda_nacional, importe_transaccion, tasa_cambio, peso, fecha_cruce, marca, medio, descripcion, observaciones, cod_emp, fecha_mod, usuario_mod, operacion)  ");
		sb.append("VALUES (0, '0', 1, '0', NOW(), 0, NOW(), 0, '0', 0, 0, 0, 0, 0, NOW(), '0', '0', 'No Asignado', 'No Asignado', ?, NOW(), 'SISTE', 'NUEVO')  ");
		
		return sb.toString();
	}

	public String insClienteNoAsig(){
		
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("INSERT INTO m_clientes (cod_tit, nom_tit, razon_social, cod_emp, tel, nro_dgi, cod_docdgi, direccion, mail, activo, usuario_mod, operacion, fecha_mod)  ");
		sb.append("VALUES (1, 'No asignado', 'No asignado', ?, '0', '0', '02', '0', '0', 1, 'SISTE', 'NUEVO', NOW())  ");

		
	
		return sb.toString();
	}
	
	public String insGrupoAdm(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT m_grupos (cod_grupo, nombre, fecha_mod, usuario_mod, operacion, activo, cod_emp)  ");
		sb.append("SELECT cod_grupo, nombre, fecha_mod, usuario_mod, operacion, activo, ? FROM m_grupos WHERE cod_grupo = 'Adm' AND cod_emp = 'Param'   ");
		
		return sb.toString();
	}
	
	public String insGruposxFormAdm(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_grupoxform (formulario, cod_grupo, leer, nuevo_editar, borrar, cod_emp)  ");
		sb.append("SELECT formulario, cod_grupo, leer, nuevo_editar, borrar, ? FROM m_grupoxform WHERE cod_emp = 'Param' AND cod_grupo = 'Adm'  ");
		
		return sb.toString();
	}
	
	public String insNumProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('01', 'Procesos', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insNumGasto(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('02', 'Gastos', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insNumTitulares(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('04', 'Titulares', 2, ?) ");
		
		return sb.toString();
	}
	
	public String insCtaProc(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('ctaproc', 'Ing Cobro Cta Proc', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insEgrCobro(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('egrcobro', 'Egreso Cobro', 1, ?) ");
		
		return sb.toString();
	}
	
	
	public String insFact(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('factura', 'Factura', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insIngCob(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_numeradores (cod_numerador, nom_numerador, numero, cod_emp) ");
		sb.append("VALUES ('ingcobro', 'Ingreso Cobro', 1, ?)  ");
		
		return sb.toString();
	}
	
	public String insGrupoCliente(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_grupos (cod_grupo, nombre, fecha_mod, usuario_mod, operacion, activo, cod_emp) ");
		sb.append("VALUES ('CLIENTE', 'CLIENTE', NOW(), 'SISTE', 'NUEVO', 1, ?) ");
		
		return sb.toString();
	}
	
	public String insForm1(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_grupoxform (formulario, cod_grupo, leer, nuevo_editar, borrar, cod_emp) ");
		sb.append("VALUES ('RepCheqxCliente', 'CLIENTE', 1, 0, 0, ?) ");
		
		return sb.toString();
	}
	
	public String insForm2(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_grupoxform (formulario, cod_grupo, leer, nuevo_editar, borrar, cod_emp) ");
		sb.append("VALUES ('RepEstadoCuenta', 'CLIENTE', 1, 0, 0, ?) ");
		
		return sb.toString();
	}
	
	public String insForm3(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_grupoxform (formulario, cod_grupo, leer, nuevo_editar, borrar, cod_emp) ");
		sb.append("VALUES ('RepGtosxProceso', 'CLIENTE', 1, 0, 0, ?) ");
		
		return sb.toString();
	}
	
////////////////////////INI-RUBROS///////////////////////////////////////////////////
    
	public String getRubros(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp, facturable ");
		sb.append("FROM m_rubros ");
		sb.append("WHERE cod_emp = ? ");
		return sb.toString();
	}
	
	public String getRubrosActivos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp, facturable ");
		sb.append("FROM m_rubros ");
		sb.append("WHERE cod_emp = ? and activo = 1 ");
		return sb.toString();
	}

	
	public String insertarRubro()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO vaadin.m_rubros (cod_rubro, descripcion, activo, fecha_mod, usuario_mod, operacion, cod_impuesto, cod_tipo_rubro, cod_emp, facturable )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ? ) ");
		
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
		sb.append("cod_tipo_rubro = ?, ");
		sb.append("facturable = ? ");
		sb.append("WHERE cod_rubro = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String rubroCuenta(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_rubros.cod_rubro, m_rubros.descripcion, m_cuentas.cod_cuenta, m_cuentas.descripcion,"
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "m_rubrosxcuenta.oficina, m_rubrosxcuenta.proceso, m_rubrosxcuenta.persona, "
				+ "m_tiporubro.cod_tipoRubro, m_tiporubro.descripcion, m_rubros.facturable ");
		
		sb.append("FROM m_cuentas, m_rubrosxcuenta, m_rubros, m_impuestos, m_tiporubro ");
		
		sb.append("where m_rubrosxcuenta.cod_rubro = m_rubros.cod_rubro AND "
				+ "m_rubrosxcuenta.cod_cuenta = m_cuentas.cod_cuenta and m_rubrosxcuenta.cod_emp = ? "
				+ " and m_rubros.cod_emp = m_rubrosxcuenta.cod_emp and m_rubros.activo = 1 "
				+ " and m_cuentas.cod_emp = m_rubrosxcuenta.cod_emp and m_cuentas.activo = 1 "
				+ " and m_rubros.activo = 1 AND m_cuentas.cod_cuenta = m_rubrosxcuenta.cod_cuenta "
				+ " and m_cuentas.cod_emp = m_rubrosxcuenta.cod_emp "
				+ " and m_impuestos.cod_impuesto = m_rubros.cod_impuesto "
				+ " and m_impuestos.cod_emp = m_rubrosxcuenta.cod_emp "
				+ " and m_rubros.cod_tipo_rubro = m_tiporubro.cod_tipoRubro "
				+ " and m_tiporubro.cod_emp = m_rubrosxcuenta.cod_emp ");

		return sb.toString();
	}

	public String rubroCuentaActivosFacturable(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_rubros.cod_rubro, m_rubros.descripcion, m_cuentas.cod_cuenta, m_cuentas.descripcion,"
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "m_rubrosxcuenta.oficina, m_rubrosxcuenta.proceso, m_rubrosxcuenta.persona, "
				+ "m_tiporubro.cod_tipoRubro, m_tiporubro.descripcion, m_rubros.facturable ");
		
		sb.append("FROM m_cuentas, m_rubrosxcuenta, m_rubros, m_impuestos, m_tiporubro ");
		
		sb.append("where m_rubrosxcuenta.cod_rubro = m_rubros.cod_rubro AND "
				+ "m_rubrosxcuenta.cod_cuenta = m_cuentas.cod_cuenta and m_rubrosxcuenta.cod_emp = ? "
				+ " and m_rubros.cod_emp = m_rubrosxcuenta.cod_emp and m_rubros.activo = 1 "
				+ " and m_cuentas.cod_emp = m_rubrosxcuenta.cod_emp and m_cuentas.activo = 1 "
				+ " and m_rubros.activo = 1 AND m_cuentas.cod_cuenta = m_rubrosxcuenta.cod_cuenta "
				+ " and m_cuentas.cod_emp = m_rubrosxcuenta.cod_emp "
				+ " and m_impuestos.cod_impuesto = m_rubros.cod_impuesto "
				+ " and m_impuestos.cod_emp = m_rubrosxcuenta.cod_emp "
				+ " and m_rubros.cod_tipo_rubro = m_tiporubro.cod_tipoRubro "
				+ " and m_tiporubro.cod_emp = m_rubrosxcuenta.cod_emp "
				+ " and m_rubros.facturable = 1 ");

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
		sb.append("FROM m_cotizaciones, m_monedas WHERE m_cotizaciones.cod_moneda = m_monedas.cod_moneda AND m_cotizaciones.cod_emp = m_monedas.cod_emp ");
		sb.append(" AND m_cotizaciones.cod_emp = ? and fecha between DATE_SUB(?, INTERVAL 1 DAY) and ? ");
		
		return sb.toString();
	}
	
	public String getCotizacion(){
		
		StringBuilder sb = new StringBuilder();
		
				
		sb.append("SELECT m_cotizaciones.cod_moneda, m_cotizaciones.fecha, m_cotizaciones.cotizacion_compra, "
				+ "m_cotizaciones.cotizacion_venta, m_cotizaciones.fecha_mod, m_cotizaciones.usuario_mod, "
				+ "m_cotizaciones.operacion, m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, m_monedas.acepta_cotizacion, m_monedas.activo ");
		sb.append("FROM m_cotizaciones, m_monedas WHERE m_cotizaciones.cod_moneda = m_monedas.cod_moneda AND m_cotizaciones.cod_emp = m_monedas.cod_emp ");
		sb.append(" AND m_cotizaciones.cod_emp = ? AND fecha = ? AND m_cotizaciones.cod_moneda = ? AND m_cotizaciones.cod_emp = m_monedas.cod_emp ");
		
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
		
		sb.append("SELECT cod_tipoRubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp ");
		sb.append("FROM m_tiporubro WHERE cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String getTipoRubrosActivos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_tipoRubro, descripcion, fecha_mod, usuario_mod, operacion, activo, cod_emp ");
		sb.append("FROM m_tiporubro WHERE cod_emp = ? and activo = 1 ");
		
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
		sb.append("FROM m_tiporubro WHERE cod_tipoRubro = ? AND cod_emp = ? ");
		
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
	
	public String getCuentasxRubro()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_cuentas.cod_cuenta, m_cuentas.descripcion, m_cuentas.fecha_mod, m_cuentas.usuario_mod, m_cuentas.operacion, m_cuentas.activo ");
		sb.append("FROM m_cuentas, m_rubrosxcuenta ");
		sb.append("WHERE m_rubrosxcuenta.cod_rubro = ? AND m_cuentas.cod_emp = ?"
				+ "AND m_cuentas.cod_cuenta = m_rubrosxcuenta.cod_cuenta AND m_cuentas.cod_emp = m_rubrosxcuenta.cod_emp ");
		
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

	public String eliminarCuenta()
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
		sb.append("AND m_rubros.cod_rubro = m_rubrosxcuenta.cod_rubro AND m_rubros.cod_emp = m_rubrosxcuenta.cod_emp ");
		
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
				+ "AND m_rubros.activo = 1 AND m_rubros.cod_emp =  ? ");
		
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
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento "
				+ "AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND m_clientes.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_procesos.cod_emp "
				+ " WHERE c_procesos.cod_emp = ? and c_procesos.fecha between DATE_SUB(?, INTERVAL 1 DAY) and ?");  
		
		return sb.toString();
	}
	
	public String getProcesosxTit(){
		
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
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento "
				+ "AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND m_clientes.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_procesos.cod_emp "
				+ " WHERE c_procesos.cod_emp = ? AND c_procesos.cod_cliente = ? ");  
		
		return sb.toString();
	}
	
	public String getProcesosSinFecha(){
		
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
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento "
				+ "AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND m_clientes.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_procesos.cod_emp "
				+ " WHERE c_procesos.cod_emp = ?  ");  
		
		return sb.toString();
	}
	
	public String getProcesosCliente(){
		
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
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento "
				+ "AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND m_clientes.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_procesos.cod_emp "
				+ " WHERE c_procesos.cod_emp = ? AND cod_cliente = ? ");  
		
		return sb.toString();
	}

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
		
	public String eliminarProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_procesos ");
		sb.append("WHERE cod_proceso = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String getProceso(){
		
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
		
		sb.append("FROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento "
				+ "AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND m_clientes.cod_emp = c_procesos.cod_emp "
				+ " LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_procesos.cod_emp "
				+ " WHERE c_procesos.cod_emp = ? AND c_procesos.cod_proceso = ? ");  
		
		return sb.toString();
	}
////////////////////////FIN PROCESOS//////////////////////////////////////////////
		
////////////////////////INI NUMERADORES//////////////////////////////////////////////

	public String getNumeroTrans()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT numero ");
		sb.append("FROM g_numeradores WHERE cod_numerador = ? ");
		
		return sb.toString();
	}
		
	public String getNumero()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT numero ");
		sb.append("FROM g_numeradores WHERE cod_numerador = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String actualizarNumeroTrans()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE vaadin.g_numeradores ");
		sb.append("SET numero = ? ");
		sb.append("WHERE cod_numerador = ? ");
		
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
	
////////////////////////GASTOS//////////////////////////////////////////////////

	public String getGastos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, c_gastos.estado, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND m_clientes.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso and c_procesos.cod_emp = c_gastos.cod_emp "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cuenta = 'IngGastoProceso' and c_gastos.fecValor between DATE_SUB(?, INTERVAL 1 DAY) and ? ");  
				
				return sb.toString();
				
//				ROM c_procesos LEFT JOIN  m_documentos_aduaneros ON c_procesos.cod_documento = m_documentos_aduaneros.cod_documento AND m_documentos_aduaneros.cod_emp = c_procesos.cod_emp
//					     LEFT JOIN  m_clientes ON c_procesos.cod_cliente = m_clientes.cod_tit AND c_procesos.cod_emp = m_clientes.cod_emp
//					     LEFT JOIN m_monedas ON c_procesos.cod_moneda = m_monedas.cod_moneda AND c_procesos.cod_emp = m_monedas.cod_emp
//					     AND c_procesos.cod_emp = ?
	}
	
	public String getGastosNoCobrablesxProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, c_gastos.estado, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND m_clientes.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso and c_procesos.cod_emp = c_gastos.cod_emp "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.estado = 'nocobr' AND c_gastos.anulado = 'N' ");
				
				return sb.toString();

	}
	
	public String getGastosFacturablesxProcesoConSaldo(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND c_gastos.cod_emp = m_clientes.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.estado = 'fact' "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ?  "
				+ " AND sa_docum.imp_tot_mo <> 0 AND c_gastos.anulado = 'N' ");
				
				
		return sb.toString();

	}
	
	public String getGastosFacturablesxProcesoConSaldoxMoneda(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND c_gastos.cod_emp = m_clientes.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				
				/*Y QUE NO EXISTAN EN UNA PRE FACTURA*/
				+"AND NOT EXISTS (SELECT * FROM d_facturas df, c_facturas cf WHERE df.nro_trans = cf.nro_trans AND cf.cod_emp = df.cod_emp  AND df.nro_docum = c_gastos.nro_docum AND df.serie_docum = c_gastos.serie_docum AND df.cod_docum = c_gastos.cod_docum AND df.cod_emp = c_gastos.cod_emp AND cf.tipo_factura = 'PreFactura' ) "
				
				
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.estado = 'fact' "); 
				
				
		
		sb.append(" AND c_gastos.cod_tit = ? AND c_gastos.cod_moneda = ? "
				+ " AND sa_docum.imp_tot_mo <> 0 AND c_gastos.anulado = 'N' ");
				
				
		return sb.toString();

	}
	
	public String getGastosCobrablesxProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, c_gastos.estado, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND m_clientes.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso and c_procesos.cod_emp = c_gastos.cod_emp "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.estado = 'cobr' AND c_gastos.anulado = 'N' ");
				
				return sb.toString();

	}
	
	public String getGastosAPagarxProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, c_gastos.estado, sa_docum.imp_tot_mo, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND m_clientes.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso and c_procesos.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum AND sa_docum.serie_docum = c_gastos.serie_docum "
				+ "			   AND sa_docum.nro_docum = c_gastos.nro_docum AND sa_docum.cod_emp = c_gastos.cod_emp and sa_docum.imp_tot_mo > 0 "
				+ " WHERE c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.estado = 'cobr' AND c_gastos.anulado = 'N' ");
				
				return sb.toString();

	}
	

	public String getGastosAnuladosxProceso(){
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
			+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
			+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
			+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
			+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
			+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
			+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
			+ "m_clientes.cod_tit, m_clientes.nom_tit, "
			+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
			+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
			+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
			+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
			+ "c_procesos.descripcion, c_gastos.estado, c_gastos.anulado ");
	
	sb.append("FROM c_gastos"
			+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND m_clientes.cod_emp = c_gastos.cod_emp "
			+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
			+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
			+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
			+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
			+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso and c_procesos.cod_emp = c_gastos.cod_emp "
			+ " AND c_gastos.cod_emp = ? AND c_gastos.cod_proceso = ? AND c_gastos.anulado = 'S' ");
			
			return sb.toString();

}


	public String getGastosEmpleados(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_funcionarios.cod_tit, m_funcionarios.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje,"
				+ "c_gastos.estado, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_funcionarios ON c_gastos.cod_tit = m_funcionarios.cod_tit AND m_funcionarios.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda  AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cuenta = 'IngGastoEmpleado' and c_gastos.fecValor between DATE_SUB(?, INTERVAL 1 DAY) and ? ");  
				
				return sb.toString();
	}
	
	public String getGastosOficina(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, c_gastos.estado, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " LEFT JOIN  m_titulares ON c_gastos.cod_tit = m_titulares.cod_tit AND m_titulares.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND m_cuentas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND m_rubros.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND m_monedas.cod_emp = c_gastos.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND m_impuestos.cod_emp = c_gastos.cod_emp "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.cuenta = 'IngGastoOficina' and c_gastos.fecValor between DATE_SUB(?, INTERVAL 1 DAY) and ? ");  
				
				return sb.toString();
	}
	
		public String getGastosConSaldoxMoneda(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, c_gastos.imp_tot_mn, "
				+ "c_gastos.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_titulares ON c_gastos.cod_tit = m_titulares.cod_tit AND c_gastos.cod_emp = m_titulares.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ? "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ? "
				+ " AND c_gastos.cod_moneda = ? "
				+ " AND sa_docum.imp_tot_mo <> 0 AND c_gastos.anulado = 'N' ");
				
				return sb.toString();
	}

		public String getGastosConSaldo(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_titulares ON c_gastos.cod_tit = m_titulares.cod_tit AND c_gastos.cod_emp = m_titulares.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ? "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ? AND c_gastos.anulado = 'N' "
				+ " AND sa_docum.imp_tot_mo <> 0 ");
				
		return sb.toString();
	}
	
	public String getGastosConSaldoCobrable(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_titulares ON c_gastos.cod_tit = m_titulares.cod_tit AND c_gastos.cod_emp = m_titulares.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.estado = 'cobr' AND c_gastos.anulado = 'N' "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ? "
				+ " AND sa_docum.imp_tot_mo <> 0 ");
				
		return sb.toString();
	}
	
	public String getGastosSinMedioDePago(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_titulares ON c_gastos.cod_tit = m_titulares.cod_tit AND c_gastos.cod_emp = m_titulares.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ?  AND c_gastos.anulado = 'N' "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ? "
				+ " AND sa_docum.imp_tot_mo <> 0 ");
		
		/*Obtenemos los gastos que no esten en un igreso de cobro*/
		sb.append("AND c_gastos.nro_trans IN (SELECT nro_trans FROM c_gastos j WHERE NOT EXISTS (SELECT * FROM d_egrcobro k  ");
		sb.append("WHERE j.cod_emp = k.cod_emp  ");
		sb.append("AND j.cod_docum = k.cod_docum ");
		sb.append("AND j.serie_docum = k.serie_docum  ");
		sb.append("AND j.nro_docum = k.nro_docum)) ");
				
		return sb.toString();
	}
	
	public String getGastosConSaldoCobrableProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_gastos.fecDoc, c_gastos.cod_docum, c_gastos.serie_docum, "
				+ "c_gastos.nro_docum, c_gastos.cod_emp, c_gastos.referencia, "
				+ "c_gastos.nro_trans, c_gastos.fecValor, c_gastos.cod_proceso, "
				+ "c_gastos.referenciaDetalle, c_gastos.imp_impu_mn, c_gastos.imp_impu_mo, "
				+ "c_gastos.imp_sub_mn, c_gastos.imp_sub_mo, sa_docum.imp_tot_mn, "
				+ "sa_docum.imp_tot_mo, c_gastos.tc_mov, c_gastos.cuenta, "
				+ "c_gastos.fecha_mod, c_gastos.usuario_mod, c_gastos.operacion, "
				+ "m_clientes.cod_tit, m_clientes.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_cuentas.cod_cuenta, m_cuentas.descripcion, "
				+ "m_rubros.cod_rubro, m_rubros.descripcion, m_rubros.cod_tipo_rubro, m_rubros.cod_impuesto, "
				+ "m_impuestos.cod_impuesto, m_impuestos.descripcion, m_impuestos.porcentaje, "
				+ "c_procesos.descripcion, m_monedas.nacional, c_gastos.anulado ");
		
		sb.append("FROM c_gastos"
				+ " INNER JOIN  m_clientes ON c_gastos.cod_tit = m_clientes.cod_tit AND c_gastos.cod_emp = m_clientes.cod_emp  "
				+ " INNER JOIN m_cuentas ON c_gastos.cod_cuenta = m_cuentas.cod_cuenta AND c_gastos.cod_emp = m_cuentas.cod_emp "
				+ " INNER JOIN m_rubros ON c_gastos.cod_rubro = m_rubros.cod_rubro AND c_gastos.cod_emp = m_rubros.cod_emp "
				+ " INNER JOIN m_monedas ON c_gastos.cod_moneda = m_monedas.cod_moneda AND c_gastos.cod_emp = m_monedas.cod_emp "
				+ " INNER JOIN m_impuestos ON c_gastos.cod_impuesto = m_impuestos.cod_impuesto AND c_gastos.cod_emp = m_impuestos.cod_emp "
				+ " INNER JOIN c_procesos ON c_gastos.cod_proceso = c_procesos.cod_proceso AND c_gastos.cod_emp = c_procesos.cod_emp "
				+" INNER JOIN sa_docum ON c_gastos.cod_docum = sa_docum.cod_docum  "
				+" AND c_gastos.serie_docum = sa_docum.serie_docum "
				+" AND c_gastos.nro_docum = sa_docum.nro_docum "
				+" AND c_gastos.cod_emp = sa_docum.cod_emp "
				+" AND c_gastos.cod_tit = sa_docum.cod_tit "
				+ " AND c_gastos.cod_emp = ? AND c_gastos.estado = 'cobr' AND c_gastos.anulado = 'N' "); 
		
		
		sb.append(" AND c_gastos.cod_tit = ? "
				+ " AND sa_docum.imp_tot_mo <> 0 "
				+ " AND c_gastos.cod_proceso = ? ");
				
		return sb.toString();
	}
	
	public String insertarGasto()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_gastos ( fecDoc, cod_docum, serie_docum, nro_docum, "
				+ " cod_emp, cod_moneda, referencia, cod_tit, nro_trans, "
				+ " fecValor, cod_proceso, referenciaDetalle, imp_impu_mn, imp_impu_mo, imp_sub_mn, "
				+ " imp_sub_mo, imp_tot_mn, imp_tot_mo, tc_mov, cod_cuenta, cod_rubro, cuenta, "
				+ " fecha_mod, usuario_mod, operacion, estado, cod_impuesto, anulado ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ? ) ");
		
		return sb.toString();
	}
	
	public String memberGasto(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_gastos WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeGastoIngreso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_gastos WHERE serie_docum = ? AND nro_docum = ? AND cod_emp = ? and cod_docum = 'Gasto' ");
		
		return sb.toString();
	}
	
	public String existeGastoAsociadoProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_gastos WHERE cod_proceso = ? AND cod_emp = ? ");
		
		return sb.toString();
	}	
	
	
	public String eliminarGasto(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_gastos ");
		sb.append("WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String eliminarGastoPK(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_gastos ");
		sb.append("WHERE nro_docum = ? AND cod_emp = ? AND serie_docum = ? AND cod_docum = ?");
		
		return sb.toString();
	}
	
	public String anularGasto(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.c_gastos ");
      	sb.append("SET anulado = ? ");
      	sb.append("WHERE nro_docum = ? AND cod_emp = ? AND serie_docum = ? AND cod_docum = ?");
      	 
      	return sb.toString();
    }

////////////////////////FIN GASTOS//////////////////////////////////////////////

////////////////////////SALDOS//////////////////////////////////////////////////

	public String getSaldos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT sa_docum.cod_docum, sa_docum.serie_docum, "
				+ "sa_docum.nro_docum, sa_docum.cod_emp, sa_docum.cod_moneda, "
				+ "sa_docum.cod_tit, sa_docum.imp_tot_mn, sa_docum.cuenta, "
				+ "sa_docum.fecha_mod, sa_docum.usuario_mod, sa_docum.operacion, "
				+ "m_titulares.cod_tit, m_titulares.nom_tit, "
				+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo ");
		
		sb.append("FROM sa_docum"
				+ " INNER JOIN  m_titulares ON sa_docum.cod_tit = m_titulares.cod_tit AND sa_docum.cod_emp = m_titulares.cod_emp "
				+ " INNER JOIN m_monedas ON sa_docum.cod_moneda = m_monedas.cod_moneda AND sa_docum.cod_emp = m_monedas.cod_emp "
				+ " AND sa_docum.cod_emp = ? "); 
		
		return sb.toString();
	}
	
	public String getSaldoMo(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT imp_tot_mo "
				+ "FROM sa_docum WHERE cod_docum = ? "
				+ "AND serie_docum = ? AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");		
		
		return sb.toString();
	}
	
	public String insertarSaldo()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO sa_docum ( cod_docum, serie_docum, nro_docum, "
				+ " cod_emp, cod_moneda, cod_tit, "
				+ " imp_tot_mn, imp_tot_mo, cuenta, "
				+ " fecha_mod, usuario_mod, operacion, anulado ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, 0) ");
		
		return sb.toString();
	}
	
	public String memberSaldo(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum "); 
		sb.append("FROM sa_docum WHERE cod_docum = ? AND serie_docum = ? "
				+ "AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");
		
		return sb.toString();
	}
	
	
	
	
	public String eliminarSaldo(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM sa_docum ");
		sb.append("WHERE cod_docum = ? AND serie_docum = ? "
				+ "AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");
		
		return sb.toString();
	}		
	
	public String anulaSaldo(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.sa_docum ");
      	sb.append("SET anulado = ? ");
  		sb.append("WHERE cod_docum = ? AND serie_docum = ? AND nro_docum = ? AND cod_emp = ? ");
      	 
      	return sb.toString();
    }

	public String updateSaldoImporte(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.sa_docum ");
      	sb.append("SET imp_tot_mn = ?, ");
      	sb.append("imp_tot_mo = ? ");
  		sb.append("WHERE cod_docum = ? AND serie_docum = ? AND nro_docum = ? AND cod_emp = ? ");
      	 
      	return sb.toString();
    }

////////////////////////FIN SALDOS//////////////////////////////////////////////
	
////////////////////////SALDOS PROCESOS////////////////////////////////////////

	public String getSaldosProcesos(){
	
	StringBuilder sb = new StringBuilder();
	
		sb.append("SELECT sa_proceso.cod_proceso , sa_proceso.cod_doca, sa_proceso.serie_doca, "
		+ "sa_proceso.nro_doca, sa_proceso.cod_emp, sa_proceso.cod_moneda, "
		+ "sa_proceso.cod_tit, sa_proceso.imp_tot_mn, "
		+ "sa_proceso.fecha_mod, sa_proceso.usuario_mod, sa_proceso.operacion, "
		+ "m_titulares.cod_tit, m_titulares.nom_tit, "
		+ "m_monedas.cod_moneda, m_monedas.descripcion, m_monedas.simbolo ");
		
		sb.append("FROM sa_proceso"
		+ " INNER JOIN  m_titulares ON sa_proceso.cod_tit = m_titulares.cod_tit AND sa_proceso.cod_emp = m_titulares.cod_emp "
		+ " INNER JOIN m_monedas ON sa_proceso.cod_moneda = m_monedas.cod_moneda AND sa_proceso.cod_emp = m_monedas.cod_emp "
		+ " AND sa_proceso.cod_emp = ? "); 
	
	return sb.toString();
	}

	public String getSaldoMnProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT imp_tot_mo "
		+ "FROM sa_proceso WHERE cod_proceso = ? "
		+ "AND cod_emp = ? AND cod_tit = ? AND cod_moneda = ?");		
		
		return sb.toString();
	}

	public String insertarSaldoProceso()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO sa_proceso ( cod_proceso,cod_doca, serie_doca, nro_doca, "
		+ " cod_emp, cod_moneda, cod_tit, "
		+ " imp_tot_mn, imp_tot_mo, cod_cta, nro_trans, fec_doc, fec_valor, "
		+ " fecha_mod, usuario_mod, operacion ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?) ");
		
		return sb.toString();
	}

	public String memberSaldoProceso(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_proceso ");
		sb.append("FROM sa_proceso WHERE cod_proceso = ? "
		+ "AND cod_emp = ? AND cod_tit = ? AND cod_moneda = ?");
		
		return sb.toString();
	}
	
	public String existeSaldoAsociadoProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_proceso ");
		sb.append("FROM sa_proceso WHERE cod_proceso = ? "
		+ "AND cod_emp = ? ");
		
		return sb.toString();
	}


	public String eliminarSaldoProceso(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM sa_proceso ");
		sb.append("WHERE cod_proceso = ? "
		+ "AND cod_emp = ? AND cod_tit = ? AND cod_moneda = ?");
		
		return sb.toString();
	}		
	
	public String getSaldosxProceso(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT s.cod_proceso, s.imp_tot_mo, s.imp_tot_mn, s.cod_doca, s.serie_doca, s.nro_doca, "
				+ "s.cod_emp, s.cod_tit, s.cod_cta, s.nro_trans, s.fec_doc, s.fec_valor, s.usuario_mod, s.operacion, ");
		sb.append("m.cod_moneda, m.descripcion, m.simbolo, m.nacional ");
		sb.append("FROM sa_proceso s, m_monedas m  ");
		sb.append("WHERE s.cod_moneda = m.cod_moneda  ");
		sb.append("AND s.cod_emp = m.cod_emp  ");
		sb.append("AND s.cod_emp = ? AND s.cod_proceso = ?  ");
		
		return sb.toString();
	}


////////////////////////FIN SALDOS///////////////////////////////////////////////
	
////////////////////////CHEQUES /////////////////////////////////////////////////
	
	public String memberCheque(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum ");
		sb.append("FROM c_cheques WHERE cod_docum = ? AND serie_docum = ? "
				+ "AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");
		
		return sb.toString();
	}

	public String existeCheque(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_docum ");
		sb.append("FROM c_cheques WHERE serie_docum = ? AND nro_docum = ? "
				+ "AND cod_emp = ? AND cod_bco = ? AND cod_ctabco = ? ");
		
		return sb.toString();
	}

	public String insertarCheque()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_cheques ( cod_docum, serie_docum, nro_docum, "
				+ " cod_emp, cod_moneda, cod_tit, "
				+ " imp_tot_mn, imp_tot_mo, cuenta, "
				+ " fecha_mod, usuario_mod, operacion, cod_cta, referencia, nro_trans, cod_bco, cod_ctabco, "
				+ " fec_doc, fec_valor, tc_mov ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		
		return sb.toString();
	}

	public String eliminarCheque(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_cheques ");
		sb.append("WHERE cod_docum = ? AND serie_docum = ? "
				+ "AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");
		
		return sb.toString();
	}	
	
////////////////////////FIN CHEQUES//////////////////////////////////////////////
	
////////////////////////SALDO CUENTAS////////////////////////////////////////////
	
	public String memberSaldoCuenta(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum ");
		sb.append("FROM sa_cuentas WHERE nro_trans = ? ");
		
		return sb.toString();
	}
	
	public String existeNroTransferencia(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT cod_docum ");
		sb.append("FROM sa_cuentas WHERE nro_doc_ref = ? and cod_bco = ? and cod_ctabco = ? and cod_emp = ? "
				+ "and (cod_doc_ref = 'tranrec' or cod_doc_ref = 'tranemi') ");
		
		return sb.toString();
	}

	public String insertarSaldoCuenta()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO sa_cuentas ( cod_docum, serie_docum, nro_docum, "
				+ " cod_emp, cod_moneda, cod_tit, "
				+ " imp_tot_mn, imp_tot_mo, cuenta, "
				+ " fecha_mod, usuario_mod, operacion, cod_cta, referencia, nro_trans, cod_doc_ref, "
				+ " serie_doc_ref, nro_doc_ref, cod_bco, cod_ctabco, movimiento, signo, fec_doc, fec_valor, anulado ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0) ");
		
		
		return sb.toString();
	}
	
	public String eliminarSaldoCuenta(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM sa_cuentas ");
		sb.append("WHERE nro_trans = ? ");
		
		return sb.toString();
	}	
	
	public String anularSaldoCuenta(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.sa_cuentas ");
      	sb.append("SET anulado = ? ");
      	sb.append("WHERE nro_trans = ? ");
      	 
      	return sb.toString();
    }
	
////////////////////////FIN SALO CUENTAS/////////////////////////////////////////
	
////////////////////////INI LOGS///////////////////////////////////////////////
	public String insertarLog()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO g_log ( cod_docum, serie_docum, nro_docum, "
				+ " cod_doca, serie_doca, nro_doca, "
				+ " cod_doc_ref, serie_doc_ref, nro_doc_ref, "
				+ " cod_emp, cod_tit, nro_trans, cod_moneda, "
				+ " imp_tot_mn, imp_tot_mo, cuenta, "
				+ " fecha_mod, usuario_mod, operacion, linea ) ");
		sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	}
	
	public String eliminarLog()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM g_log ");
		sb.append("WHERE nro_trans = ? AND linea = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
////////////////////////FIN LOGS///////////////////////////////////////////////

////////////////////////INI TITULARES///////////////////////////////////////////////
	public String getTitularesActivos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.cod_docdgi, m_titulares.nro_dgi, m_titulares.tipo, "
				+ "m_titulares.nro_dgi, m_titulares.cod_emp, m_documdgi.nombre ");
		sb.append("FROM m_titulares, m_documdgi WHERE m_titulares.cod_emp = ? and m_titulares.activo = 1 "
				+ "and m_documdgi.cod_docdgi = m_titulares.cod_docdgi ");

		return sb.toString();
	}
	
	public String getTitularesTodos()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.cod_docdgi, m_titulares.nro_dgi, m_titulares.tipo, "
				+ "m_titulares.nro_dgi, m_titulares.cod_emp, m_documdgi.nombre ");
		sb.append("FROM m_titulares, m_documdgi WHERE m_titulares.cod_emp = ? "
				+ "and m_documdgi.cod_docdgi = m_titulares.cod_docdgi ");

		return sb.toString();
	}
	
	public String getTitularesActivosFuncionarios()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT m_titulares.cod_tit, m_titulares.nom_tit, m_titulares.cod_docdgi, m_titulares.nro_dgi, m_titulares.tipo, "
				+ "m_titulares.nro_dgi, m_titulares.cod_emp, m_documdgi.nombre ");
		sb.append("FROM m_titulares, m_documdgi WHERE m_titulares.cod_emp = ? and m_titulares.activo = 1 "
				+ "and m_documdgi.cod_docdgi = m_titulares.cod_docdgi and m_titulares.tipo = 'funcionario' ");

		return sb.toString();
	}
	
////////////////////////FNI TITULARES///////////////////////////////////////////////
	
//////////////////////// INI PERIODOS ///////////////////////////////////////////////

	public String getPeriodos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT mes, anio, abierto, fecha_mod, usuario_mod, operacion, cod_emp ");
		sb.append("FROM m_periodo WHERE cod_emp = ? ");
		
		return sb.toString();
	}
	

	public String insertarPeriodo()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO m_periodo (mes, anio, abierto, fecha_mod, usuario_mod, operacion, cod_emp )");
		sb.append("VALUES (?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String memberPeriodo()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT mes, anio ");
		sb.append("FROM m_periodo  ");
		sb.append("WHERE mes = ? AND anio = ? AND cod_emp = ?");
		
		return sb.toString();
	}
	
	public String actualizarPeriodo(){
	
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE m_periodo ");
		sb.append("SET abierto = ?, ");
		sb.append("fecha_mod = NOW(), ");
		sb.append("usuario_mod = ?, ");
		sb.append("operacion = ? ");
		sb.append("WHERE mes = ? AND anio = ? AND cod_emp = ?");
		return sb.toString();
	}
	
	public String validaPeriodo()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT mes, anio, abierto ");
		sb.append("FROM m_periodo  ");
		sb.append("WHERE mes = ? AND anio = ? AND cod_emp = ? AND abierto = 1 ");
		
		return sb.toString();
	}
	
//////////////////////// FIN PERIODOS ///////////////////////////////////////////////
	
/////////////////////// INI DEPOSITOS ///////////////////////////////////////////////////
	
	public String getChequesBanco(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_cheques.cod_docum, c_cheques.serie_docum, c_cheques.nro_docum, "
				+ " c_cheques.referencia, c_cheques.cod_moneda, m_monedas.descripcion, m_monedas.nacional, "
				+ " m_monedas.simbolo, "
				+ "sa_docum.imp_tot_mn, sa_docum.imp_tot_mo, "
				+ "c_cheques.cod_bco, "
				+ "c_cheques.cod_ctabco , c_cheques.fec_valor, c_cheques.fec_doc, c_cheques.tc_mov, "
				+ "m_bancos.nom_bco, m_ctasbcos.nom_cta, c_cheques.cod_tit ");
		
		sb.append("FROM c_cheques, sa_docum, m_bancos, m_ctasbcos, m_monedas ");
		
		sb.append("WHERE c_cheques.cod_docum = 'cheqrec' "
				+ "and c_cheques.cod_docum = sa_docum.cod_docum "
				+ "and c_cheques.serie_docum = sa_docum.serie_docum "
				+ "and c_cheques.nro_docum = sa_docum.nro_docum "
				+ "and c_cheques.cod_emp = sa_docum.cod_emp "
				+ "and c_cheques.cod_emp = ? "
				+ "and c_cheques.cod_moneda = ? "
				+ "and sa_docum.imp_tot_mo > 0 "
				+ "and m_bancos.cod_emp = c_cheques.cod_emp "
				+ "and m_bancos.cod_bco = c_cheques.cod_bco "
				+ "and m_ctasbcos.cod_emp = c_cheques.cod_emp "
				+ "and m_ctasbcos.cod_bco = c_cheques.cod_bco "
				+ "and m_ctasbcos.cod_ctabco = c_cheques.cod_ctabco "
				+ "and m_monedas.cod_moneda = c_cheques.cod_moneda "
				+ "and m_monedas.cod_emp = c_cheques.cod_emp "
				+ "and c_cheques.cod_tit = sa_docum.cod_tit ");
		
		return sb.toString();
	}	
	
	public String depositarChequeSaldo(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE sa_docum ");
		sb.append("imp_tot_mn = ?, ");
		sb.append("imp_tot_mo = ?, ");
		sb.append("WHERE cod_docum = ? AND serie_docum = ? "
				+ "AND nro_docum = ? AND cod_emp = ? AND cod_tit = ? ");
		
		return sb.toString();
		
	}
	
	public String getCabezalDeposito(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_deposito.cod_docum, c_deposito.serie_docum, c_deposito.nro_docum, "
				+ "c_deposito.cod_emp, c_deposito.fec_doc, c_deposito.fec_valor, c_deposito.cod_bco, "
				+ "c_deposito.cod_ctabco, c_deposito.cod_moneda, c_deposito.imp_tot_mn, c_deposito.imp_tot_mo, "
				+ "c_deposito.tc_mov, c_deposito.observaciones, c_deposito.nro_trans, c_deposito.fecha_mod, "
				+ "c_deposito.usuario_mod, c_deposito.operacion, c_deposito.cod_tit, "
				+ "m_bancos.nom_bco, m_ctasbcos.nom_cta, m_monedas.descripcion, m_monedas.simbolo, "
				+ "m_monedas.nacional, m_titulares.nom_tit ");
		
		sb.append("from c_deposito, m_bancos, m_ctasbcos, m_monedas, m_titulares"
				+ " WHERE c_deposito.cod_emp = ? and m_bancos.cod_emp = c_deposito.cod_emp and m_bancos.cod_bco = c_deposito.cod_bco "
				+ " and m_ctasbcos.cod_emp = c_deposito.cod_emp and m_ctasbcos.cod_bco = c_deposito.cod_bco "
				+ " and m_ctasbcos.cod_ctabco = c_deposito.cod_ctabco "
				+ " and m_monedas.cod_emp = c_deposito.cod_emp and m_monedas.cod_moneda = c_deposito.cod_moneda "
				+ " and m_titulares.cod_emp = c_deposito.cod_emp and m_titulares.cod_tit = c_deposito.cod_tit "
				+ " and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ? ");
		
		return sb.toString();
	}
	
	public String getDetalleDeposito(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_deposito.cod_docum, d_deposito.serie_docum, d_deposito.nro_docum, "
				+ "d_deposito.cuenta, d_deposito.fec_doc, d_deposito.fec_valor, d_deposito.cod_moneda, "
				+ "d_deposito.imp_tot_mn, d_deposito.imp_tot_mo, d_deposito.nro_trans, d_deposito.cod_bco, "
				+ "d_deposito.linea, d_deposito.cod_emp, d_deposito.cod_ctabco, d_deposito.cod_tit, d_deposito.tc_mov, "
				+ "m_monedas.descripcion, m_monedas.nacional, m_monedas.simbolo, m_bancos.nom_bco, m_ctasbcos.nom_cta, m_ctasbcos.cod_moneda ");
		
		sb.append("FROM d_deposito, m_monedas, m_bancos, m_ctasbcos "
				+ "WHERE d_deposito.cod_emp = ? AND d_deposito.nro_trans = ? "
				+ "and m_monedas.cod_emp = d_deposito.cod_emp and m_monedas.cod_moneda = d_deposito.cod_moneda "
				+ "and m_bancos.cod_emp = d_deposito.cod_emp and m_bancos.cod_bco = d_deposito.cod_bco "
				+ "and m_ctasbcos.cod_emp = d_deposito.cod_emp and m_ctasbcos.cod_ctabco = d_deposito.cod_ctabco "
				+ "and m_ctasbcos.cod_bco = d_deposito.cod_bco");
		
		return sb.toString();
	}
	
	public String insertarCabezalDeposito()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_deposito (cod_docum, serie_docum, nro_docum, cod_emp, fec_doc, fec_valor, "
				+ " cod_bco, cod_ctabco, cod_moneda, imp_tot_mn, imp_tot_mo, tc_mov, nro_trans, fecha_mod, "
				+ "usuario_mod, operacion, observaciones, cod_tit ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ? ) ");
		
		return sb.toString();
	
	}
	
	public String insertarDetalleDeposito()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_deposito (cod_docum, serie_docum, nro_docum, cuenta, fec_doc, fec_valor, "
				+ " cod_moneda, imp_tot_mn, imp_tot_mo, nro_trans, "
				+ "linea, cod_emp, cod_bco, cod_ctabco, cod_tit, tc_mov ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?	) ");
		
		return sb.toString();
	
	}
	
	public String memberDeposito(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_deposito WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeDeposito(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_deposito WHERE nro_docum = ? AND cod_bco = ? AND cod_ctabco = ? and cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String existeChequeDepositado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM d_deposito WHERE serie_docum = ? AND nro_docum = ? AND cod_emp = ? "
				+ "AND cod_bco = ? and cod_ctabco = ? ");
		
		return sb.toString();
	}
	
	public String eliminarDeposito(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_deposito ");
		sb.append("WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String eliminarDepositoDetalle(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE from d_deposito ");
		sb.append("WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	
	
/////////////////////// FIN DEPOSITOS ///////////////////////////////////////////////////
	
	
/////////////////////// INI CONCILIACIONES ///////////////////////////////////////////////////
	
	public String getChequesRecibidos(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sa_cuentas.cod_docum, sa_cuentas.serie_docum, sa_cuentas.nro_docum, sa_cuentas.cod_doc_ref, "
				+ "sa_cuentas.serie_doc_ref, sa_cuentas.nro_doc_ref, sa_cuentas.fec_doc, sa_cuentas.fec_valor, "
				+ "sa_cuentas.imp_tot_mn, sa_cuentas.imp_tot_mo, sa_cuentas.cod_emp, sa_cuentas.referencia ");

		sb.append(" from sa_cuentas ");
		
		sb.append("WHERE sa_cuentas.cod_doc_ref <> 'cheqemi' "
		+ "and sa_cuentas.cod_emp = ? and sa_cuentas.cod_bco = ? and sa_cuentas.cod_ctabco = ? "
		+ "and sa_cuentas.conciliado = 0  ");
		
		return sb.toString();
	}	
	
	public String getChequesEmitidos(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sa_cuentas.cod_docum, sa_cuentas.serie_docum, sa_cuentas.nro_docum, sa_cuentas.cod_doc_ref, "
				+ "sa_cuentas.serie_doc_ref, sa_cuentas.nro_doc_ref, sa_cuentas.fec_doc, sa_cuentas.fec_valor, "
				+ "sa_cuentas.imp_tot_mn, sa_cuentas.imp_tot_mo, sa_cuentas.cod_emp, sa_cuentas.referencia ");

		sb.append(" from sa_cuentas ");
		
		sb.append("WHERE sa_cuentas.cod_doc_ref = 'cheqemi' "
		+ "and sa_cuentas.cod_emp = ? and sa_cuentas.cod_bco = ? and sa_cuentas.cod_ctabco = ? "
		+ "and sa_cuentas.conciliado = 0  ");
		
		return sb.toString();
	}	
	
	public String getMovimientosCajaMoneda(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sa_cuentas.cod_docum, sa_cuentas.serie_docum, sa_cuentas.nro_docum, sa_cuentas.cod_doc_ref, "
				+ "sa_cuentas.serie_doc_ref, sa_cuentas.nro_doc_ref, sa_cuentas.fec_doc, sa_cuentas.fec_valor, "
				+ "sa_cuentas.imp_tot_mn, sa_cuentas.imp_tot_mo, sa_cuentas.cod_emp, sa_cuentas.referencia ");

		sb.append(" from sa_cuentas ");
		
		sb.append("WHERE sa_cuentas.cod_bco = '0' and sa_cuentas.cod_ctabco = '0' "
		+ "and sa_cuentas.cod_emp = ? and sa_cuentas.cod_moneda = ? "
		+ "and sa_cuentas.conciliado = 0  ");
		
		return sb.toString();
	}	
	
	public String getSaldoConciliadoMoneda(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sum(imp_tot_mo) from sa_cuentas " );

		sb.append("WHERE sa_cuentas.cod_bco = '0' and sa_cuentas.cod_ctabco = '0' "
		+ "and sa_cuentas.cod_emp = ? and sa_cuentas.cod_moneda = ? "
		+ "and sa_cuentas.conciliado = 1  ");
		
		return sb.toString();
	}	
	
	public String getSaldoConciliadoCuentaBanco(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sum(imp_tot_mo) from sa_cuentas " );

		sb.append("WHERE sa_cuentas.cod_bco = ? and sa_cuentas.cod_ctabco = ? "
		+ "and sa_cuentas.cod_emp = ?  "
		+ "and sa_cuentas.conciliado = 1  ");
		
		return sb.toString();
	}


	public String getCabezalConciliacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT c_conciliacion.cod_docum, c_conciliacion.serie_docum, c_conciliacion.nro_docum, "
		+ "c_conciliacion.cod_emp, c_conciliacion.imp_tot_mn, c_conciliacion.imp_tot_mo, "
		+ "c_conciliacion.cuenta, c_conciliacion.nro_trans, c_conciliacion.fec_doc, c_conciliacion.fec_valor, "
		+ "c_conciliacion.cod_bco, c_conciliacion.cod_ctabco, c_conciliacion.cod_moneda, "
		+ "c_conciliacion.fecha_mod, c_conciliacion.usuario_mod, c_conciliacion.operacion, "
		+ "m_bancos.nom_bco, m_ctasbcos.nom_cta, m_monedas.descripcion, m_monedas.simbolo, "
		+ "m_monedas.nacional, c_conciliacion.observaciones, c_conciliacion.tipo ");
		
//		sb.append("from c_conciliacion, m_bancos, m_ctasbcos, m_monedas "
//		+ " WHERE c_conciliacion.cod_emp = ? and m_bancos.cod_emp = c_conciliacion.cod_emp and m_bancos.cod_bco = c_conciliacion.cod_bco "
//		+ " and m_ctasbcos.cod_emp = c_conciliacion.cod_emp and m_ctasbcos.cod_bco = c_conciliacion.cod_bco "
//		+ " and m_ctasbcos.cod_ctabco = c_conciliacion.cod_ctabco "
//		+ " and m_monedas.cod_emp = c_conciliacion.cod_emp and m_monedas.cod_moneda = c_conciliacion.cod_moneda ");
		
		sb.append("from c_conciliacion "
				+ "LEFT JOIN m_bancos ON c_conciliacion.cod_bco = m_bancos.cod_bco and "
				+ "c_conciliacion.cod_emp = m_bancos.cod_emp "
				+ "LEFT JOIN m_ctasbcos ON c_conciliacion.cod_emp = m_ctasbcos.cod_emp and "
				+ "m_ctasbcos.cod_bco = c_conciliacion.cod_bco and m_ctasbcos.cod_ctabco = c_conciliacion.cod_ctabco "
				+ "LEFT JOIN  m_monedas ON c_conciliacion.cod_emp = m_monedas.cod_emp and "
				+ "m_monedas.cod_moneda = c_conciliacion.cod_moneda "
				+ " WHERE c_conciliacion.cod_emp = ? and fec_valor between DATE_SUB(?, INTERVAL 1 DAY) and ? " );
		
		return sb.toString();
	}

	public String getDetalleConiliacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_conciliacion.cod_docum, d_conciliacion.serie_docum, d_conciliacion.nro_docum, "
		+ "d_conciliacion.fec_doc, d_conciliacion.fec_valor, "
		+ "d_conciliacion.imp_tot_mn, d_conciliacion.imp_tot_mo, d_conciliacion.nro_trans, "
		+ "d_conciliacion.cod_emp, referencia, cod_doc_ref, serie_doc_ref, nro_doc_ref ");
		
		sb.append("FROM d_conciliacion "
		+ "WHERE d_conciliacion.cod_emp = ? AND d_conciliacion.nro_trans = ? ");
		
		return sb.toString();
	}

	public String insertarCabezalConciliacion()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO c_conciliacion (cod_docum, serie_docum, nro_docum, cod_emp, imp_tot_mn, imp_tot_mo, "
		+ " nro_trans, fec_doc, fec_valor, cod_bco, cod_ctabco, cod_moneda, observaciones, "
		+ "fecha_mod, usuario_mod, operacion, tipo ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ? ) ");
		
		return sb.toString();
	
	}

	public String insertarDetalleConciliacion()
	{
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO d_conciliacion (cod_docum, serie_docum, nro_docum, cod_emp, imp_tot_mn, imp_tot_mo, "
		+ " nro_trans, fec_doc, fec_valor, referencia, linea, cod_doc_ref, serie_doc_ref, nro_doc_ref ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?	) ");
		
		return sb.toString();
	
	}

	public String memberConciliacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT nro_trans ");
		sb.append("FROM c_conciliacion WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String eliminarConciliacion(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM c_conciliacion ");
		sb.append("WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}

	public String eliminarConciliacionDetalle(){
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE from d_conciliacion ");
		sb.append("WHERE nro_trans = ? AND cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String actualizarSaCuentasConciliado(){
    	
    	StringBuilder sb = new StringBuilder();
    	 
       	sb.append("UPDATE vaadin.sa_cuentas ");
      	sb.append("SET conciliado = ? ");
      	sb.append("WHERE cod_docum = ? AND serie_docum = ? AND nro_docum = ? AND cod_emp = ? ");
      	 
      	return sb.toString();
    }

	public String chequeConciliado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_conciliacion.cod_docum, c_conciliacion.nro_trans ");
		sb.append("FROM d_conciliacion, c_conciliacion WHERE d_conciliacion.serie_doc_ref = ? "
				+ "AND d_conciliacion.nro_doc_ref = ? AND d_conciliacion.cod_emp = ? AND c_conciliacion.cod_bco = ? "
				+ "AND c_conciliacion.cod_ctabco = ? AND d_conciliacion.nro_trans = c_conciliacion.nro_trans "
				+ "and d_conciliacion.cod_emp = c_conciliacion.cod_emp ");
		
		return sb.toString();
	}
	
	public String depositoConciliado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_conciliacion.cod_docum, c_conciliacion.nro_trans ");
		sb.append("FROM d_conciliacion, c_conciliacion WHERE d_conciliacion.serie_docum = ? "
				+ "AND d_conciliacion.nro_docum = ? AND d_conciliacion.cod_emp = ? AND c_conciliacion.cod_bco = ? "
				+ "AND c_conciliacion.cod_ctabco = ? AND d_conciliacion.nro_trans = c_conciliacion.nro_trans "
				+ "and d_conciliacion.cod_emp = c_conciliacion.cod_emp and d_conciliacion.cod_docum = 'Deposito'");
		
		return sb.toString();
	}
	
	public String egresoConciliado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_conciliacion.nro_trans ");
		sb.append("FROM d_conciliacion WHERE (d_conciliacion.cod_docum = 'egrcobro' or d_conciliacion.cod_docum = 'otroegr') "
				+ "AND d_conciliacion.nro_docum = ? AND d_conciliacion.cod_emp = ? ");
		
		return sb.toString();
	}
	
	public String ingresoConciliado(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT d_conciliacion.nro_trans ");
		sb.append("FROM d_conciliacion WHERE (d_conciliacion.cod_docum = 'ingcobro' or d_conciliacion.cod_docum = 'otrcobro') "
				+ "AND d_conciliacion.nro_docum = ? AND d_conciliacion.cod_emp = ? ");
		
		return sb.toString();
	}


/////////////////////// FIN DEPOSITOS ///////////////////////////////////////////////////
}
