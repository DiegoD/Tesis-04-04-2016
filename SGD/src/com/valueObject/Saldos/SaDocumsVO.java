package com.valueObject.Saldos;

public class SaDocumsVO {

	private String cod_docum;
	private String  serie_docum;
	private int  nro_docum;
	private String  cod_emp;
	private String  cod_moneda;
	private int  cod_tit;
	private double  imp_tot_mn;
	private double  imp_tot_mo;
	private String  cuenta;
	
	public SaDocumsVO(){}
	
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
	public String getCod_emp() {
		return cod_emp;
	}
	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
	}
	public String getCod_moneda() {
		return cod_moneda;
	}
	public void setCod_moneda(String cod_moneda) {
		this.cod_moneda = cod_moneda;
	}
	public int getCod_tit() {
		return cod_tit;
	}
	public void setCod_tit(int cod_tit) {
		this.cod_tit = cod_tit;
	}
	public double getImp_tot_mn() {
		return imp_tot_mn;
	}
	public void setImp_tot_mn(double imp_tot_mn) {
		this.imp_tot_mn = imp_tot_mn;
	}
	public double getImp_tot_mo() {
		return imp_tot_mo;
	}
	public void setImp_tot_mo(double imp_tot_mo) {
		this.imp_tot_mo = imp_tot_mo;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	
}
