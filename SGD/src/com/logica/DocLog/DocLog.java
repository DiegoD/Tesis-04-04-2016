package com.logica.DocLog;

import com.logica.Auditoria;

public class DocLog extends Auditoria{
	
	private String cod_docum;
	private String serie_docum;
	private Integer nro_docum;
	private Integer linea;
	private String cod_doca;
	private String serie_doca;
	private Integer nro_doca;
	private String cod_doc_ref;
	private String serie_doc_ref;
	private Integer nro_doc_ref;
	private String cod_tit;
	private Long nro_trans;
	private String cod_moneda;
	private Double imp_tot_mn;
	private Double imp_tot_mo;
	private String cuenta;
	
	public DocLog(){
	}


	public DocLog(String cod_docum, String serie_docum, Integer nro_docum, Integer linea, String cod_doca,
			String serie_doca, Integer nro_doca, String cod_doc_ref, String serie_doc_ref, Integer nro_doc_ref,
			String cod_tit, Long nro_trans, String cod_moneda, Double imp_tot_mn, Double imp_tot_mo, String cuenta) {
		super();
		this.cod_docum = cod_docum;
		this.serie_docum = serie_docum;
		this.nro_docum = nro_docum;
		this.linea = linea;
		this.cod_doca = cod_doca;
		this.serie_doca = serie_doca;
		this.nro_doca = nro_doca;
		this.cod_doc_ref = cod_doc_ref;
		this.serie_doc_ref = serie_doc_ref;
		this.nro_doc_ref = nro_doc_ref;
		this.cod_tit = cod_tit;
		this.nro_trans = nro_trans;
		this.cod_moneda = cod_moneda;
		this.imp_tot_mn = imp_tot_mn;
		this.imp_tot_mo = imp_tot_mo;
		this.cuenta = cuenta;
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

	
	public Integer getLinea() {
		return linea;
	}


	public void setLinea(Integer linea) {
		this.linea = linea;
	}


	public Integer getNro_docum() {
		return nro_docum;
	}

	public void setNro_docum(Integer nro_docum) {
		this.nro_docum = nro_docum;
	}

	public String getCod_doca() {
		return cod_doca;
	}

	public void setCod_doca(String cod_doca) {
		this.cod_doca = cod_doca;
	}

	public String getSerie_doca() {
		return serie_doca;
	}

	public void setSerie_doca(String serie_doca) {
		this.serie_doca = serie_doca;
	}

	public Integer getNro_doca() {
		return nro_doca;
	}

	public void setNro_doca(Integer nro_doca) {
		this.nro_doca = nro_doca;
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

	public Integer getNro_doc_ref() {
		return nro_doc_ref;
	}

	public void setNro_doc_ref(Integer nro_doc_ref) {
		this.nro_doc_ref = nro_doc_ref;
	}

	public String getCod_tit() {
		return cod_tit;
	}

	public void setCod_tit(String cod_tit) {
		this.cod_tit = cod_tit;
	}

	public Long getNro_trans() {
		return nro_trans;
	}

	public void setNro_trans(Long nro_trans) {
		this.nro_trans = nro_trans;
	}

	public String getCod_moneda() {
		return cod_moneda;
	}

	public void setCod_moneda(String cod_moneda) {
		this.cod_moneda = cod_moneda;
	}

	public Double getImp_tot_mn() {
		return imp_tot_mn;
	}

	public void setImp_tot_mn(Double imp_tot_mn) {
		this.imp_tot_mn = imp_tot_mn;
	}

	public Double getImp_tot_mo() {
		return imp_tot_mo;
	}

	public void setImp_tot_mo(Double imp_tot_mo) {
		this.imp_tot_mo = imp_tot_mo;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
}
