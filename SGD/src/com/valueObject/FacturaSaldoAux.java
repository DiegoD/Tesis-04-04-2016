package com.valueObject;

public class FacturaSaldoAux {

	private String codDocum;
	private String serie;
	private String nroDocum;
	private double saldo;
	
	public FacturaSaldoAux(){}
	
	public FacturaSaldoAux(String codDocum, String serie, String nroDocum, double saldo){
		
		this.codDocum = codDocum;
		this.serie = serie;
		this.nroDocum = nroDocum;
		this.saldo = saldo;
	}
	
	public FacturaSaldoAux getCopia(){
		
		FacturaSaldoAux aux = new FacturaSaldoAux();
		
		aux.codDocum = this.codDocum;
		aux.serie = this.serie;
		aux.nroDocum = this.nroDocum;
		aux.saldo = this.saldo;
		
		return aux;
	}

	public String getCodDocum() {
		return codDocum;
	}

	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNroDocum() {
		return nroDocum;
	}

	public void setNroDocum(String nroDocum) {
		this.nroDocum = nroDocum;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
}
