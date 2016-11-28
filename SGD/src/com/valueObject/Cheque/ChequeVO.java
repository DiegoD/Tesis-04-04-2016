package com.valueObject.Cheque;

import java.sql.Timestamp;

import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.valueObject.Docum.DatosDocumVO;

public class ChequeVO { //extends DatosDocumVO{
	
	private Timestamp fecDoc;
	private Timestamp fecValor;
	
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private String codEmp;
	private boolean nacional;
	
	private String referencia;
	
	private String codMoneda;
	private String nomMoneda;
	private String simboloMoneda;
	
	
	private String nomTitular;
	private String codTitular;
	private String tipo;
	
	private long nroTrans;
	
	private double impTotMn;
	private double impTotMo;
	private double tcMov;
	
	private String codCuenta;
	private String nomCuenta;

	private String codCtaInd; /*Cuenta interna del sistema*/

	private String codBanco;
	private String nomBanco;
	
//	private BancoInfo banco;
//	private CuentaBcoInfo cuentaBanco;
	
	public ChequeVO(){
		
	}

	public ChequeVO(Timestamp fecDoc, Timestamp fecValor, String codDocum, String serieDocum, int nroDocum, String codEmp,
			boolean nacional, String referencia, String codMoneda, String nomMoneda, String simboloMoneda,
			String nomTitular, String codTitular, String tipo, long nroTrans, double impTotMn, double impTotMo,
			double tcMov, String codCuenta, String nomCuenta, String codCtaInd, String codBanco, String nomBanco) {
		super();
		this.fecDoc = fecDoc;
		this.fecValor = fecValor;
		this.codDocum = codDocum;
		this.serieDocum = serieDocum;
		this.nroDocum = nroDocum;
		this.codEmp = codEmp;
		this.nacional = nacional;
		this.referencia = referencia;
		this.codMoneda = codMoneda;
		this.nomMoneda = nomMoneda;
		this.simboloMoneda = simboloMoneda;
		this.nomTitular = nomTitular;
		this.codTitular = codTitular;
		this.tipo = tipo;
		this.nroTrans = nroTrans;
		this.impTotMn = impTotMn;
		this.impTotMo = impTotMo;
		this.tcMov = tcMov;
		this.codCuenta = codCuenta;
		this.nomCuenta = nomCuenta;
		this.codCtaInd = codCtaInd;
		this.codBanco = codBanco;
		this.nomBanco = nomBanco;
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

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
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

	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}

	public String getNomCuenta() {
		return nomCuenta;
	}

	public void setNomCuenta(String nomCuenta) {
		this.nomCuenta = nomCuenta;
	}

	public String getCodCtaInd() {
		return codCtaInd;
	}

	public void setCodCtaInd(String codCtaInd) {
		this.codCtaInd = codCtaInd;
	}

	public String getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public String getNomBanco() {
		return nomBanco;
	}

	public void setNomBanco(String nomBanco) {
		this.nomBanco = nomBanco;
	}
	
}
