package com.valueObject;

public class DocumentoAuaneroVO {

	private int codDocum;
	private String nomDocum;
	private boolean activo;
	

	public String getNomDocum() {
		return nomDocum;
	}

	public void setNomDocum(String nomDocum) {
		this.nomDocum = nomDocum;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getCodDocum() {
		return codDocum;
	}

	public void setCodDocum(int codDocum) {
		this.codDocum = codDocum;
	}
}
