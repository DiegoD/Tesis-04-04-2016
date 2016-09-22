package com.valueObject.Numeradores;

public class NumeradoresVO {
	
	private int codigo;
	private int numeroTrans;
	
	public NumeradoresVO(){
		
	}
	
	public NumeradoresVO(int codigo, int numeroTrans) {
		super();
		this.codigo = codigo;
		this.numeroTrans = numeroTrans;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getNumeroTrans() {
		return numeroTrans;
	}
	public void setNumeroTrans(int numeroTrans) {
		this.numeroTrans = numeroTrans;
	}

}
