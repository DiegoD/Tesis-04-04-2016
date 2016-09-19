package com.logica.Docum;

public class CuentaBcoInfo {

	private String nomCuenta;
	private String codCuenta;
	
	public CuentaBcoInfo(){
		
		
	}
	
	public CuentaBcoInfo(String cod, String nom){
		
		this.nomCuenta = nom;
		this.codCuenta = cod;
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
}
