package com.logica;

public class ProcesoInfo {

	private int codProceso;
	private String descProceso;
	
	public ProcesoInfo(){}
	
	public ProcesoInfo(int cod, String desc){
		
		this.codProceso = cod;
		this.descProceso = desc;
	}

	public int getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(int codProceso) {
		this.codProceso = codProceso;
	}

	public String getDescProceso() {
		return descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}
	
	
	
}
