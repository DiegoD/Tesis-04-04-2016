package com.valueObject.proceso;


public class SaldoProcesoVO {

	
	private String codProceso;
	private double impTotMO;
	private double impTotMN;
	
	private String codMoneda;
	private String descMoneda;
	private String simboloMoneda;
	private boolean nacional;
	
	
	public void copiar(SaldoProcesoVO saVO){
		
		this.codProceso = saVO.codProceso;
		this.impTotMO = saVO.impTotMO;
		this.impTotMN = saVO.impTotMN;
		
		this.codMoneda = saVO.codMoneda;
		this.descMoneda = saVO.descMoneda;
		this.simboloMoneda = saVO.simboloMoneda;
		this.nacional = saVO.nacional;
		
	}
	
	
	public String getCodProceso() {
		return codProceso;
	}
	
	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}
	
	public double getImpTotMO() {
		return impTotMO;
	}
	
	public void setImpTotMO(double impTotMO) {
		this.impTotMO = impTotMO;
	}
	
	public double getImpTotMN() {
		return impTotMN;
	}
	
	public void setImpTotMN(double impTotMN) {
		this.impTotMN = impTotMN;
	}
	
	public String getCodMoneda() {
		return codMoneda;
	}
	
	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}
	
	public boolean isNacional() {
		return nacional;
	}
	
	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	public String getDescMoneda() {
		return descMoneda;
	}

	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}

	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}
	
	
	
}
