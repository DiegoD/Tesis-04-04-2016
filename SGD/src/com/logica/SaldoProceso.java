package com.logica;

import java.sql.Timestamp;

import com.valueObject.proceso.SaldoProcesoVO;

public class SaldoProceso {
	
	
	//SELECT cod_proceso, cod_emp, cod_tit, cod_moneda, imp_tot_mo, imp_tot_mn FROM sa_proceso 
	private String codProceso;
	private MonedaInfo moneda;
	private double impTotMO;
	private double impTotMN;
	private String codDoca;
	private String serieDoca;
	private int nroDoca;
	private String codEmp;
	private String codTit;
	private String codCta;
	private long nroTrans;
	private Timestamp fecDoc;
	private Timestamp fecValor;
	private String usuarioMod;
	private String operacion;
	private double tcMov;
	
	public SaldoProceso(){
		
		this.moneda = new MonedaInfo();
	}
	
	public SaldoProceso(SaldoProcesoVO vo){
		
		this.codProceso = vo.getCodProceso();
		this.impTotMO = vo.getImpTotMO();
		this.impTotMN = vo.getImpTotMN();
		
		this.moneda = new MonedaInfo(vo.getCodMoneda(), vo.getDescMoneda(), vo.getSimboloMoneda(), vo.isNacional());
		this.codDoca = vo.getCodDoca();
		this.serieDoca = vo.getSerieDoca();
		this.nroDoca = vo.getNroDoca();
		this.codEmp = vo.getCodEmp();
		this.codTit = vo.getCodTit();
		this.codCta = vo.getCodCta();
		this.nroTrans = vo.getNroTrans();
		this.fecDoc = vo.getFecDoc();
		this.fecValor = vo.getFecValor();
		this.usuarioMod = vo.getUsuarioMod();
		this.operacion = vo.getOperacion();
		this.tcMov = vo.getTcMov();
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
		vo.setCodDoca(this.getCodDoca());
		vo.setSerieDoca(this.getSerieDoca());
		vo.setNroDoca(this.getNroDoca());
		vo.setCodEmp(this.getCodEmp());
		vo.setCodTit(this.getCodTit());
		vo.setCodCta(this.getCodCta());
		vo.setNroTrans(this.getNroTrans());
		vo.setFecDoc(this.getFecDoc());
		vo.setFecValor(this.getFecValor());
		vo.setUsuarioMod(this.getUsuarioMod());
		vo.setOperacion(this.getOperacion());
		vo.setTcMov(this.getTcMov());

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

	public String getCodDoca() {
		return codDoca;
	}

	public void setCodDoca(String codDoca) {
		this.codDoca = codDoca;
	}

	public String getSerieDoca() {
		return serieDoca;
	}

	public void setSerieDoca(String serieDoca) {
		this.serieDoca = serieDoca;
	}

	public int getNroDoca() {
		return nroDoca;
	}

	public void setNroDoca(int nroDoca) {
		this.nroDoca = nroDoca;
	}

	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}

	public String getCodTit() {
		return codTit;
	}

	public void setCodTit(String codTit) {
		this.codTit = codTit;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public Timestamp getFecDoc() {
		return fecDoc;
	}

	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}

	public Timestamp getFecValor() {
		return fecValor;
	}

	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
	}

	public String getUsuarioMod() {
		return usuarioMod;
	}

	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public double getTcMov() {
		return tcMov;
	}

	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}

	
	

}
