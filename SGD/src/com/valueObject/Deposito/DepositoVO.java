package com.valueObject.Deposito;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.logica.FuncionarioInfo;
import com.logica.MonedaInfo;
import com.logica.Depositos.DepositoDetalle;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.valueObject.AuditoriaVO;
import com.valueObject.MonedaVO;

public class DepositoVO extends AuditoriaVO{
	
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private Timestamp fecValor;
	private Timestamp fecDoc;
	
	private String codBanco;
	private String nomBanco;
	
	private String nomCuenta;
	private String codCuenta;
	private String codMoneda;
	private boolean nacional;
	
	private MonedaInfo moneda;
	private FuncionarioInfo funcionario;
	private int numComprobante;
	private String observaciones;
	private double impTotMo;
	private double impTotMn;
	private long nroTrans;
	
	private ArrayList<DepositoDetalleVO> lstDetalle;
	
	public DepositoVO(){
		setLstDetalle(new ArrayList<DepositoDetalleVO>());
	}

	public void copiar(DepositoVO depVO){
		
		this.setCodDocum(depVO.getCodDocum());
		this.setSerieDocum(depVO.getSerieDocum());
		this.setNroDocum(depVO.getNroDocum());
		this.setFecValor(depVO.getFecValor());
		this.setFecDoc(depVO.getFecDoc());
		this.setMoneda(depVO.getMoneda());
		this.setFuncionario(depVO.getFuncionario());
		this.setNumComprobante(depVO.getNumComprobante());
		this.setObservaciones(depVO.getObservaciones());
		this.setImpTotMn(depVO.getImpTotMn());
		this.setImpTotMo(depVO.getImpTotMo());
		this.setNroTrans(depVO.getNroTrans());
		
		this.setCodBanco(depVO.getCodBanco());
		this.setNomBanco(depVO.getNomBanco());
		this.setNomCuenta(depVO.getNomCuenta());
		this.setCodCuenta(depVO.getCodCuenta());
		this.setCodMoneda(depVO.getCodMoneda());
		this.setNacional(depVO.isNacional());
		
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


	public MonedaInfo getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
	}

	public FuncionarioInfo getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioInfo funcionario) {
		this.funcionario = funcionario;
	}

	public int getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public double getImpTotMo() {
		return impTotMo;
	}

	public void setImpTotMo(double impTotMo) {
		this.impTotMo = impTotMo;
	}

	public double getImpTotMn() {
		return impTotMn;
	}

	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public ArrayList<DepositoDetalleVO> getLstDetalle() {
		return lstDetalle;
	}

	public void setLstDetalle(ArrayList<DepositoDetalleVO> lstDetalle) {
		this.lstDetalle = lstDetalle;
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
	
	
}
