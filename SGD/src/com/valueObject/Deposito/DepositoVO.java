package com.valueObject.Deposito;

import java.sql.Timestamp;

import com.logica.FuncionarioInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.valueObject.MonedaVO;

public class DepositoVO {
	
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private Timestamp fecValor;
	private String codigoBanco;
	private String nombreBanco;
	private String codigoCuentaBanco;
	private String nombreCuentaBanco;
	private FuncionarioInfo funcionario;
	private int numComprobante;
	private String observaciones;
	private double impTotMo;
	
	public DepositoVO(){
		
	}
	
	public DepositoVO(String codDocum, String serieDocum, int nroDocum, Timestamp fecValor, String codigoBanco,
			String nombreBanco, String codigoCuentaBanco, String nombreCuentaBanco, 
			FuncionarioInfo funcionario, int numComprobante, String observaciones, double impTotMo) {
		super();
		this.codDocum = codDocum;
		this.serieDocum = serieDocum;
		this.nroDocum = nroDocum;
		this.fecValor = fecValor;
		this.codigoBanco = codigoBanco;
		this.nombreBanco = nombreBanco;
		this.codigoCuentaBanco = codigoCuentaBanco;
		this.nombreCuentaBanco = nombreCuentaBanco;
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

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getCodigoCuentaBanco() {
		return codigoCuentaBanco;
	}

	public void setCodigoCuentaBanco(String codigoCuentaBanco) {
		this.codigoCuentaBanco = codigoCuentaBanco;
	}

	public String getNombreCuentaBanco() {
		return nombreCuentaBanco;
	}

	public void setNombreCuentaBanco(String nombreCuentaBanco) {
		this.nombreCuentaBanco = nombreCuentaBanco;
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
