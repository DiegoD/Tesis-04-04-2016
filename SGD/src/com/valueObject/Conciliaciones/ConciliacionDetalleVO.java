package com.valueObject.Conciliaciones;

import java.sql.Timestamp;

public class ConciliacionDetalleVO {
	
	private String cod_docum;
	private String serie_docum;
	private String nro_docum;
	private String cod_doc_ref;
	private String serie_doc_ref;
	private String nro_doc_ref;
	private Timestamp fecValor;
	private Timestamp fecDoc;
	private long nroTrans;
	private long nroTransDoc;
	private double impTotMn;
	private double impTotMo;
	private String codEmp;
	private String descripcion;
	
	public String getCod_docum() {
		return cod_docum;
	}
	public void setCod_docum(String cod_docum) {
		this.cod_docum = cod_docum;
	}
	public String getSerie_docum() {
		return serie_docum;
	}
	public void setSerie_docum(String serie_docum) {
		this.serie_docum = serie_docum;
	}
	public String getNro_docum() {
		return nro_docum;
	}
	public void setNro_docum(String nro_docum) {
		this.nro_docum = nro_docum;
	}
	public Timestamp getFecValor() {
		return fecValor;
	}
	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
	}
	public Timestamp getFecDoc() {
		return fecDoc;
	}
	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}
	public long getNroTrans() {
		return nroTrans;
	}
	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}
	public long getNroTransDoc() {
		return nroTransDoc;
	}
	public void setNroTransDoc(long nroTransDoc) {
		this.nroTransDoc = nroTransDoc;
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
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCod_doc_ref() {
		return cod_doc_ref;
	}
	public void setCod_doc_ref(String cod_doc_ref) {
		this.cod_doc_ref = cod_doc_ref;
	}
	public String getSerie_doc_ref() {
		return serie_doc_ref;
	}
	public void setSerie_doc_ref(String serie_doc_ref) {
		this.serie_doc_ref = serie_doc_ref;
	}
	public String getNro_doc_ref() {
		return nro_doc_ref;
	}
	public void setNro_doc_ref(String nro_doc_ref) {
		this.nro_doc_ref = nro_doc_ref;
	}
	
}
