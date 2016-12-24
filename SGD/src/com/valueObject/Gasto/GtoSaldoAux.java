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
	
	public GtoSaldoAux getCopia(){
		
		GtoSaldoAux aux = new GtoSaldoAux();
		
		aux.setNroDocum(this.nroDocum);
		aux.setSaldo(this.saldo);
		
		return aux;
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
