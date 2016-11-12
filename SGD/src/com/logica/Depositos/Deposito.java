package com.logica.Depositos;

import java.sql.Timestamp;

import com.logica.FuncionarioInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;

public class Deposito {
	
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private Timestamp fecValor;
	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
	private FuncionarioInfo funcionario;
	private int numComprobante;
	private String observaciones;
	private double impTotMo;
	
	public Deposito(){
		
	}

	public Deposito(Timestamp fecValor, BancoInfo banco, CuentaBcoInfo cuentaBanco, FuncionarioInfo funcionario,
			int numComprobante, String observaciones, double impTotMo) {
		super();
		this.fecValor = fecValor;
		this.banco = banco;
		this.cuentaBanco = cuentaBanco;
		this.funcionario = funcionario;
		this.numComprobante = numComprobante;
		this.observaciones = observaciones;
		this.impTotMo = impTotMo;
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
	
}
