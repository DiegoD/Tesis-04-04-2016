package com.valueObject.cliente;

import java.io.Serializable;

import com.valueObject.TitularVO;


public class ClienteVO extends TitularVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String razonSocial;
	

	
	public void copiar(ClienteVO c){
		
		super.copiar((TitularVO)c);
		
		this.razonSocial = c.getRazonSocial();
				
	}

	public String getRazonSocial() {
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
}
