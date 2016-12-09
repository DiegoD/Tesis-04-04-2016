package com.valueObject.Docum;

import java.sql.Timestamp;

import com.valueObject.AuditoriaVO;
import com.valueObject.MonedaVO;
import com.valueObject.Titular.TitularInfoVO;


public class DatosDocumVO extends AuditoriaVO{

	private Timestamp fecDoc;
	private Timestamp fecValor;
	
	private String codDocum;
	private String serieDocum;
	private String nroDocum;
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
		this.tipo 		= t.tipo;
		
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
		
		if(t.getFechaMod() !=null)
			this.setFechaMod(t.getFechaMod());
		this.setOperacion(t.getOperacion());
		
		this.codCuenta = t.codCuenta;
		
		if(t.nomCuenta != null)
			this.nomCuenta = t.nomCuenta;
		
		this.codCtaInd = t.codCtaInd;
		this.nacional = t.nacional;
		
	}
	
	public String getNroDocum() {
		return nroDocum;
	}
	
	public String getNomTitular() {
		return nomTitular;
	}
	
	public Timestamp getFecDoc() {
		return fecDoc;
	}
	
	public String getSimboloMoneda() {
		return simboloMoneda;
	}
	
	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
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
	
	public void setNroDocum(String nroDocum) {
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



	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
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

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
