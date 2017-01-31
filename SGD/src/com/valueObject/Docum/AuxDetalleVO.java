package com.valueObject.Docum;

import java.sql.Timestamp;

/***
 * Clase utilizada para pasar los valores de cabezal de factura
 * a la linea
 */
public class AuxDetalleVO {

	private String codMoneda;
	private double tc;
	private Timestamp fecDoc;
	private Timestamp fecValor;
	private String codTitular;
	private int codProceso;
	
	
	
	public String getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}
	public double getTc() {
		return tc;
	}
	public void setTc(double tc) {
		this.tc = tc;
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
	public String getCodTitular() {
		return codTitular;
	}
	public void setCodTitular(String codTitular) {
		this.codTitular = codTitular;
	}
	public int getCodProceso() {
		return codProceso;
	}
	public void setCodProceso(int codProceso) {
		this.codProceso = codProceso;
	}
	
	
}
