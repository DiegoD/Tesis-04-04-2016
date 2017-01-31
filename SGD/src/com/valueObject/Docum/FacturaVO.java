package com.valueObject.Docum;

import java.util.ArrayList;



public class FacturaVO extends DatosDocumVO{

	private ArrayList<FacturaDetalleVO> detalle;
	
	private int codProceso;
	private String descProceso;
	
	private double impuTotMn;
	private double impuTotMo;
	private double impSubMo;
	private double impSubMn;
	private String tipoFactura;
	private String tipoContCred;
	
	public FacturaVO(){
		super();
		
		this.detalle = new ArrayList<FacturaDetalleVO>();
	}
	
	public void copiar(FacturaVO t){
		
		super.copiar(t);
		
		this.codProceso = t.codProceso;
		this.descProceso = t.descProceso;
		
		this.impuTotMn = t.impuTotMn;
		this.impuTotMo = t.impuTotMo;
		this.impSubMo = t.impSubMo;
		this.impSubMn = t.impSubMn;
		this.tipoFactura = t.tipoFactura;
		this.tipoContCred = t.tipoContCred;
		
		FacturaDetalleVO aux;
		for (FacturaDetalleVO detVO : t.detalle) {
			
			aux = new FacturaDetalleVO();
			aux.copiar(detVO);
			
			this.detalle.add(aux);
		}
	}

	public ArrayList<FacturaDetalleVO> getDetalle() {
		return detalle;
	}

	public void setDetalle(ArrayList<FacturaDetalleVO> detalle) {
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

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipo) {
		this.tipoFactura = tipo;
	}

	public String getTipoContCred() {
		return tipoContCred;
	}

	public void setTipoContCred(String tipo) {
		this.tipoContCred = tipo;
	}
	
}
