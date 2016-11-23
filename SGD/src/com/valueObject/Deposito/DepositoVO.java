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
	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
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
		this.setBanco(depVO.getBanco());
		this.setCuentaBanco(depVO.getCuentaBanco());
		this.setMoneda(depVO.getMoneda());
		this.setFuncionario(depVO.getFuncionario());
		this.setNumComprobante(depVO.getNumComprobante());
		this.setObservaciones(depVO.getObservaciones());
		this.setImpTotMn(depVO.getImpTotMn());
		this.setImpTotMo(depVO.getImpTotMo());
		this.setNroTrans(depVO.getNroTrans());
		
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

	public BancoInfo getBanco() {
		return banco;
	}

	public void setBanco(BancoInfo banco) {
		this.banco = banco;
	}

	public CuentaBcoInfo getCuentaBanco() {
		return cuentaBanco;
	}

	public void setCuentaBanco(CuentaBcoInfo cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
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
	
}
