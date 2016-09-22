package com.valueObject.Gasto;

import java.sql.Timestamp;

import com.logica.ClienteInfo;
import com.logica.Cuenta;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.logica.Rubro;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.ImpuestoInfo;
import com.logica.Docum.RubroInfo;
import com.logica.Docum.TitularInfo;
import com.valueObject.AuditoriaVO;
import com.valueObject.Docum.DocumDetalleVO;

public class GastoVO extends DocumDetalleVO{
	
//	private int codGasto;
//	private int codProceso;
//	private String codCliente;
//	private String nomCliente;
//	private String codMoneda;
//	private String descMoneda;
//	private String simboloMoneda;
//	private String codCuenta;
//	private String descripcionCuenta;
//	private String codRubro;
//	private String descripcionRubro;
//	private String tipoRubro;
//	private String codImpuesto;
//	private String descripcionImpuesto;
//	private float porcentajeImpuesto;
//	private Timestamp fecha;
//	private float impMo;
//	private float impMn;
//	private float tcMov;
//	private String descripcion;
//	
	
	public GastoVO(){
		
	}

	public void copiar (GastoVO gastoVO){
		super.copiar(gastoVO);
	}

}
