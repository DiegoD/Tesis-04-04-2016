package com.valueObject.Docum;

import java.sql.Timestamp;

import com.valueObject.AuditoriaVO;
import com.valueObject.MonedaVO;
import com.valueObject.Titular.TitularInfoVO;


public class DatosDocumVO extends AuditoriaVO{

	private Timestamp fecDoc;
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private String codEmp;
	
	private String referencia;
	
	private String codMoneda;
	private String nomMoneda;
	private String simboloMoneda;
	
	private String nomTitular;
	private String codTitular;
	
	private long nroTrans;
	
	private double impTotMn;
	private double impTotMo;
	private double tcMov;
	private Timestamp fecValor;
	

	
	public DatosDocumVO(){
		
	
	}
	
	public void copiar(DatosDocumVO t){
		
		this.fecDoc = t.fecDoc;
		this.codDocum= t.codDocum;
		this.serieDocum= t.serieDocum;
		this.nroDocum= t.nroDocum;
		this.codEmp= t.codEmp;
		
		this.codTitular = t.codTitular;
		this.nomTitular = t.nomTitular;
		
		this.referencia= t.referencia;
		this.nomTitular = t.nomTitular;
		this.codTitular = t.codTitular;
		
		this.codMoneda = t.codMoneda;
		this.nomMoneda = t.nomMoneda;
		this.simboloMoneda = t.simboloMoneda;
		
		this.impTotMn = t.impTotMn;
		this.impTotMo= t.impTotMo;
		this.tcMov = t.tcMov;
		this.fecValor = t.fecValor;
		
		this.nroTrans = t.nroTrans;
		this.setUsuarioMod(t.getUsuarioMod());
		this.setFechaMod(t.getFechaMod());
		this.setOperacion(t.getOperacion());
		

		
	}
	
	public Timestamp getFecDoc() {
		return fecDoc;
	}
	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}
	public String getCodDocum() {
		return codDocum;
	}
	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}
	public String getSerieDocum() {
		return serieDocum;
	}
	public void setSerieDocum(String serieDocum) {
		this.serieDocum = serieDocum;
	}
	public int getNroDocum() {
		return nroDocum;
	}
	public void setNroDocum(int nroDocum) {
		this.nroDocum = nroDocum;
	}
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}

	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	
	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public String getNomTitular() {
		return nomTitular;
	}

	public void setNomTitular(String nomTitular) {
		this.nomTitular = nomTitular;
	}

	public String getCodTitular() {
		return codTitular;
	}

	public void setCodTitular(String codTitular) {
		this.codTitular = codTitular;
	}

	public String getCodMoneda() {
		return codMoneda;
	}

	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}

	public String getNomMoneda() {
		return nomMoneda;
	}

	public void setNomMoneda(String nomMoneda) {
		this.nomMoneda = nomMoneda;
	}

	public double getImpTotMn() {
		return impTotMn;
	}

	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
	}

	public double getImpTotMo() {
		return impTotMo;
	}

	public void setImpTotMo(double impTotMo) {
		this.impTotMo = impTotMo;
	}

	public double getTcMov() {
		return tcMov;
	}

	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}

	public Timestamp getFecValor() {
		return fecValor;
	}

	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
	}

	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}
	
}
