package com.logica.Conciliacion;

import java.sql.Timestamp;

import com.valueObject.Conciliaciones.ConciliacionDetalleVO;

public class ConciliacionDetalle {
	
	private String cod_docum;
	private String serie_docum;
	private int nro_docum;
	private String cod_doc_ref;
	private String serie_doc_ref;
	private int nro_doc_ref;
	private Timestamp fecValor;
	private Timestamp fecDoc;
	private long nroTrans;
	private long nroTransDoc;
	private double impTotMn;
	private double impTotMo;
	private String cod_emp;
	private String descripcion;
	
	public ConciliacionDetalle(){
		
	} 
	
	public ConciliacionDetalleVO retornarConciliacionDetalleVO(ConciliacionDetalle detalle){
		
		ConciliacionDetalleVO detalleVO = new ConciliacionDetalleVO();
		
		detalleVO.setCod_docum(detalle.getCod_docum());
		detalleVO.setSerie_docum(detalle.getSerie_docum());
		detalleVO.setNro_docum(String.valueOf(detalle.getNro_docum()));
		detalleVO.setFecDoc(detalle.getFecDoc());
		detalleVO.setFecValor(detalle.getFecValor());
		detalleVO.setNroTrans(detalle.getNroTrans());
		detalleVO.setNroTransDoc(detalle.getNroTransDoc());
		detalleVO.setImpTotMn(detalle.getImpTotMn());
		detalleVO.setImpTotMo(detalle.getImpTotMo());
		detalleVO.setCodEmp(detalle.getCod_emp());
		detalleVO.setDescripcion(detalle.getDescripcion());
		detalleVO.setCod_doc_ref(detalle.getCod_doc_ref());
		detalleVO.setSerie_doc_ref(detalle.getSerie_doc_ref());
		detalleVO.setNro_doc_ref(String.valueOf(detalle.getNro_doc_ref()));
		
		return detalleVO;
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
	public long getNroTransDoc() {
		return nroTransDoc;
	}
	
	public void setNroTransDoc(long nroTransDoc) {
		this.nroTransDoc = nroTransDoc;
	}
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
	public int getNro_docum() {
		return nro_docum;
	}
	public void setNro_docum(int nro_docum) {
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

	public String getCod_emp() {
		return cod_emp;
	}

	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
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

	public int getNro_doc_ref() {
		return nro_doc_ref;
	}

	public void setNro_doc_ref(int nro_doc_ref) {
		this.nro_doc_ref = nro_doc_ref;
	}
	
	

}
