package com.valueObject.Docum;

import java.util.ArrayList;



public class FacturaVO extends DatosDocumVO{

	private ArrayList<FacturaDetalleVO> detalle;
	
	private int codProceso;
	private String descProceso;
	
	public FacturaVO(){
		super();
		
		this.detalle = new ArrayList<FacturaDetalleVO>();
	}
	
	public void copiar(FacturaVO t){
		
		super.copiar(t);
		
		this.codProceso = t.codProceso;
		this.descProceso = t.descProceso;
		
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
	
	
}
