package com.logica.Docum;

public class CuentaBcoInfo {

	private String nomCuenta;
	private String codCuenta;
	private String codMoneda;
	private boolean nacional;
	
	public CuentaBcoInfo(){
		
		
	}
	
	public CuentaBcoInfo(String cod, String nom){
		
		this.nomCuenta = nom;
		this.codCuenta = cod;
	}
	
	public CuentaBcoInfo(String cod, String nom, String codMoneda, boolean nacional){
		
		this.nomCuenta = nom;
		this.codCuenta = cod;
		this.codMoneda = codMoneda;
		this.nacional = nacional;
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
