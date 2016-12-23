package com.valueObject.Docum;

import java.util.ArrayList;

/**
 *
 */
public class NotaCreditoVO extends DatosDocumVO{

private ArrayList<NotaCreditoDetalleVO> detalle;
	
	
	private double impuTotMn ;
	private double impuTotMo;
	private double impSubMo;
	private double impSubMn;
	
	boolean nacionalMonedaCtaBco;
	
	
	public NotaCreditoVO(){
		super();
		
		this.detalle = new ArrayList<NotaCreditoDetalleVO>();
	}
	
	public void copiar(NotaCreditoVO t){
		
		super.copiar(t);
		
		this.impuTotMn = t.impuTotMn;
		this.impuTotMo = t.impuTotMo;
		this.impSubMo = t.impSubMo;
		this.impSubMn = t.impSubMn;
		

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

	public boolean isNacionalMonedaCtaBco() {
		return nacionalMonedaCtaBco;
	}

	public void setNacionalMonedaCtaBco(boolean nacionalMonedaCtaBco) {
		this.nacionalMonedaCtaBco = nacionalMonedaCtaBco;
	}
	
	
}
