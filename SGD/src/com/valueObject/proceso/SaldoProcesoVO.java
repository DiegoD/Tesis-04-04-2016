package com.valueObject.proceso;

import java.sql.Timestamp;

public class SaldoProcesoVO {

	
	private String codProceso;
	private double impTotMO;
	private double impTotMN;
	
	private String codMoneda;
	private String descMoneda;
	private String simboloMoneda;
	private boolean nacional;
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
	
	public void copiar(SaldoProcesoVO saVO){
		
		this.codProceso = saVO.codProceso;
		this.impTotMO = saVO.impTotMO;
		this.impTotMN = saVO.impTotMN;
		
		this.codMoneda = saVO.codMoneda;
		this.descMoneda = saVO.descMoneda;
		this.simboloMoneda = saVO.simboloMoneda;
		this.nacional = saVO.nacional;
		this.codDoca = saVO.getCodDoca();
		this.serieDoca = saVO.getSerieDoca();
		this.nroDoca = saVO.getNroDoca();
		this.codEmp = saVO.getCodEmp();
		this.codTit = saVO.getCodTit();
		this.codCta = saVO.getCodCta();
		this.nroTrans = saVO.getNroTrans();
		this.fecDoc = saVO.getFecDoc();
		this.fecValor = saVO.getFecValor();
		this.usuarioMod = saVO.getUsuarioMod();
		this.operacion = saVO.getOperacion();
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
	
	
	
}
