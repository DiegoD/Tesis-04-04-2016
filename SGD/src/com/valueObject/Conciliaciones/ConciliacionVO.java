package com.valueObject.Conciliaciones;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.valueObject.AuditoriaVO;
import com.valueObject.Deposito.DepositoVO;

public class ConciliacionVO extends AuditoriaVO{
	
	private String codDocum;
	private String serieDocum;
	private String nroDocum;
	private String cod_emp;
	private double impTotMn;
	private double impTotMo;
	private String cuenta;
	private long nroTrans;
	private Timestamp fecValor;
	private Timestamp fecDoc;
	private String observaciones;
	private String codMoneda;
	private String descripcion;
	private String simbolo;
	private boolean nacional;
	private String codBanco;
	private String nomBanco;
	private String nomCuenta;
	private String codCuenta;
	private ArrayList<ConciliacionDetalleVO> lstDetalle;
	
	public ConciliacionVO(){
		lstDetalle = new ArrayList<ConciliacionDetalleVO>();
	}

	public void copiar(ConciliacionVO conc){
		
		this.setCodDocum(conc.getCodDocum());
		this.setSerieDocum(conc.getSerieDocum());
		this.setNroDocum(conc.getNroDocum());
		this.setCod_emp(conc.getCod_emp());
		this.setImpTotMn(conc.getImpTotMn());
		this.setImpTotMo(conc.getImpTotMo());
		this.setCuenta(conc.getCuenta());
		this.setNroTrans(conc.getNroTrans());
		this.setFecDoc(conc.getFecDoc());
		this.setFecValor(conc.getFecValor());
		this.setObservaciones(conc.getObservaciones());
		this.setCodMoneda(conc.getCodMoneda());
		this.setDescripcion(conc.getDescripcion());
		this.setCodMoneda(conc.getCodMoneda());
		this.setSimbolo(conc.getSimbolo());
		this.setNacional(conc.isNacional());
		this.setCodBanco(conc.getCodBanco());
		this.setNomBanco(conc.getNomBanco());
		this.setNomCuenta(conc.getNomCuenta());
		this.setCodCuenta(conc.getCodCuenta());
		this.setLstDetalle(conc.getLstDetalle());
		
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



	public String getNroDocum() {
		return nroDocum;
	}



	public void setNroDocum(String nroDocum) {
		this.nroDocum = nroDocum;
	}



	public String getCod_emp() {
		return cod_emp;
	}
	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
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
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public long getNroTrans() {
		return nroTrans;
	}
	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCodMoneda() {
		return codMoneda;
	}
	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public boolean isNacional() {
		return nacional;
	}
	public void setNacional(boolean nacional) {
		this.nacional = nacional;
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
	public String getNomCuenta() {
		return nomCuenta;
	}
	public void setNomCuenta(String nomCuenta) {
		this.nomCuenta = nomCuenta;
	}
	public String getCodCuenta() {
		return codCuenta;
	}
	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}
	public ArrayList<ConciliacionDetalleVO> getLstDetalle() {
		return lstDetalle;
	}
	public void setLstDetalle(ArrayList<ConciliacionDetalleVO> lstDetalle) {
		this.lstDetalle = lstDetalle;
	}
	
	
}
