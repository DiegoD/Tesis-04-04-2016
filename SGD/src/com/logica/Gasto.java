package com.logica;

import java.sql.Timestamp;

import com.logica.Docum.DocumDetalle;
import com.valueObject.Gasto.GastoVO;

public class Gasto extends DocumDetalle{
	
	
	public Gasto(){
		super();
	}
	
	public Gasto(GastoVO gastoVO){
		super(gastoVO);
	}
	
	
}
