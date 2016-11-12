package com.logica;

import com.valueObject.proceso.SaldoProcesoVO;

public class SaldoProceso {
	
	
	//SELECT cod_proceso, cod_emp, cod_tit, cod_moneda, imp_tot_mo, imp_tot_mn FROM sa_proceso 
	private String codProceso;
	private MonedaInfo moneda;
	private double impTotMO;
	private double impTotMN;
	
	public SaldoProceso(){
		
		this.moneda = new MonedaInfo();
	}
	
	public SaldoProceso(SaldoProcesoVO vo){
		
		this.codProceso = vo.getCodProceso();
		this.impTotMO = vo.getImpTotMO();
		this.impTotMN = vo.getImpTotMN();
		
		this.moneda = new MonedaInfo(vo.getCodMoneda(), vo.getDescMoneda(), vo.getSimboloMoneda(), vo.isNacional());
	}
	
	public SaldoProcesoVO retronarSaldoProcesoVO(){
		
		SaldoProcesoVO vo = new SaldoProcesoVO();
		
		vo.setCodProceso(this.codProceso);
		vo.setImpTotMN(this.impTotMN);
		vo.setImpTotMO(this.impTotMO);
		
		vo.setCodMoneda(this.moneda.getCodMoneda());
		vo.setSimboloMoneda(this.moneda.getSimbolo());
		vo.setDescMoneda(this.moneda.getDescripcion());
		vo.setNacional(this.moneda.isNacional());
		
		return vo;
	}
	
	public String getCodProceso() {
		return codProceso;
	}
	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}
	public MonedaInfo getMoneda() {
		return moneda;
	}
	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
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

	

}
