package com.valueObject.Docum;

public class DocumSaldoVO extends DatosDocumVO{

	private String codBco;
	private String codCtaBco;
	private String movimiento;
	
	private String codDocumRef;
	private String serieDocumRef;
	private String nroDocumRef;
	
	private int signo;
	
	
	public void copiar(DocumSaldoVO t){
		
		super.copiar(t);
		
		this.codBco = t.codBco;
		this.codCtaBco = t.codCtaBco;
		this.movimiento = t.movimiento;
		
		this.codDocumRef = t.codDocumRef;
		this.serieDocumRef = t.serieDocumRef;
		this.nroDocumRef = t.nroDocumRef;
		
		this.signo = t.signo;
	}


	public String getCodBco() {
		return codBco;
	}


	public void setCodBco(String codBco) {
		this.codBco = codBco;
	}


	public String getCodCtaBco() {
		return codCtaBco;
	}


	public void setCodCtaBco(String codCtaBco) {
		this.codCtaBco = codCtaBco;
	}


	public String getMovimiento() {
		return movimiento;
	}


	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}


	public String getCodDocumRef() {
		return codDocumRef;
	}


	public void setCodDocumRef(String codDocumRef) {
		this.codDocumRef = codDocumRef;
	}


	public String getSerieDocumRef() {
		return serieDocumRef;
	}


	public void setSerieDocumRef(String serieDocumRef) {
		this.serieDocumRef = serieDocumRef;
	}


	public String getNroDocumRef() {
		return nroDocumRef;
	}


	public void setNroDocumRef(String nroDocumRef) {
		this.nroDocumRef = nroDocumRef;
	}


	public int getSigno() {
		return signo;
	}


	public void setSigno(int signo) {
		this.signo = signo;
	}
	
	
}
