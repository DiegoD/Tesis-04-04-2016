package com.valueObject.Gasto;

public class GtoSaldoAux {

	private int nroDocum;
	private double saldo;
	

	
	public GtoSaldoAux(){}
	
	public GtoSaldoAux(int nroDocum, double saldo )
	{
		this.nroDocum = nroDocum;
		this.saldo = saldo;
	}
	
	public int getNroDocum() {
		return nroDocum;
	}
	
	public void setNroDocum(int nroDocum) {
		this.nroDocum = nroDocum;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
}
