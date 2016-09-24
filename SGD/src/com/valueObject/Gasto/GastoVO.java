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
	
	public GastoVO(){
		
	}

	public void copiar (GastoVO gastoVO){
		super.copiar(gastoVO);
	}

}
