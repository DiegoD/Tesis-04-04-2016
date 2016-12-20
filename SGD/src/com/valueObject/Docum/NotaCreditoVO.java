package com.valueObject.Docum;

import java.util.ArrayList;

/**
 *
 */
public class NotaCreditoVO extends DatosDocumVO{

private ArrayList<NotaCreditoDetalleVO> detalle;
	
	private int codProceso;
	private String descProceso;
	
	private double impuTotMn ;
	private double impuTotMo;
	private double impSubMo;
	private double impSubMn;
	
	private String mPago;
	private String codDocRef;
	private String serieDocRef;
	private String nroDocRef;
	
	private String codBanco;
	private String nomBanco;
	
	private String codCtaBco;
	private String nomCtaBco;
	private String codMonedaCtaBco;
	boolean nacionalMonedaCtaBco;
	
	
	public NotaCreditoVO(){
		super();
		
		this.detalle = new ArrayList<NotaCreditoDetalleVO>();
	}
	
	public void copiar(NotaCreditoVO t){
		
		super.copiar(t);
		
		this.codProceso = t.codProceso;
		this.descProceso = t.descProceso;
		
		this.impuTotMn = t.impuTotMn;
		this.impuTotMo = t.impuTotMo;
		this.impSubMo = t.impSubMo;
		this.impSubMn = t.impSubMn;
		
		this.mPago = t.mPago;
		this.codDocRef = t.codDocRef;
		this.serieDocRef = t.serieDocRef;
		this.nroDocRef = t.nroDocRef;
		
		this.codBanco = t.codBanco;
		this.nomBanco = t.nomBanco;
		
		this.codCtaBco = t.codCtaBco;
		this.nomCtaBco = t.nomCtaBco;
		this.codMonedaCtaBco = t.codMonedaCtaBco;
		this.nacionalMonedaCtaBco = t.nacionalMonedaCtaBco;
		
		NotaCreditoDetalleVO aux;
		for (NotaCreditoDetalleVO detVO : t.detalle) {
			
			aux = new NotaCreditoDetalleVO();
			aux.copiar(detVO);
			
			this.detalle.add(aux);
		}
	}

	public ArrayList<NotaCreditoDetalleVO> getDetalle() {
		return detalle;
	}

	public void setDetalle(ArrayList<NotaCreditoDetalleVO> detalle) {
		this.detalle = detalle;
	}

	public int getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(int codProceso) {
		this.codProceso = codProceso;
	}

	public String getDescProceso() {
		return descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}

	public double getImpuTotMn() {
		return impuTotMn;
	}

	public void setImpuTotMn(double impuTotMn) {
		this.impuTotMn = impuTotMn;
	}

	public double getImpuTotMo() {
		return impuTotMo;
	}

	public void setImpuTotMo(double impuTotMo) {
		this.impuTotMo = impuTotMo;
	}

	public double getImpSubMo() {
		return impSubMo;
	}

	public void setImpSubMo(double impSubMo) {
		this.impSubMo = impSubMo;
	}

	public double getImpSubMn() {
		return impSubMn;
	}

	public void setImpSubMn(double impSubMn) {
		this.impSubMn = impSubMn;
	}

	public String getmPago() {
		return mPago;
	}

	public void setmPago(String mPago) {
		this.mPago = mPago;
	}

	public String getCodDocRef() {
		return codDocRef;
	}

	public void setCodDocRef(String codDocRef) {
		this.codDocRef = codDocRef;
	}

	public String getSerieDocRef() {
		return serieDocRef;
	}

	public void setSerieDocRef(String serieDocRef) {
		this.serieDocRef = serieDocRef;
	}

	public String getNroDocRef() {
		return nroDocRef;
	}

	public void setNroDocRef(String nroDocRef) {
		this.nroDocRef = nroDocRef;
	}

	public String getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public String getNomBanco() {
		return nomBanco;
	}

	public void setNomBanco(String nomBanco) {
		this.nomBanco = nomBanco;
	}

	public String getCodCtaBco() {
		return codCtaBco;
	}

	public void setCodCtaBco(String codCtaBco) {
		this.codCtaBco = codCtaBco;
	}

	public String getNomCtaBco() {
		return nomCtaBco;
	}

	public void setNomCtaBco(String nomCtaBco) {
		this.nomCtaBco = nomCtaBco;
	}

	public String getCodMonedaCtaBco() {
		return codMonedaCtaBco;
	}

	public void setCodMonedaCtaBco(String codMonedaCtaBco) {
		this.codMonedaCtaBco = codMonedaCtaBco;
	}

	public boolean isNacionalMonedaCtaBco() {
		return nacionalMonedaCtaBco;
	}

	public void setNacionalMonedaCtaBco(boolean nacionalMonedaCtaBco) {
		this.nacionalMonedaCtaBco = nacionalMonedaCtaBco;
	}
	
	
}
