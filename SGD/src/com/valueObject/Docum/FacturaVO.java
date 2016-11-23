package com.valueObject.Docum;

import java.util.ArrayList;



public class FacturaVO extends DatosDocumVO{

	private ArrayList<FacturaDetalleVO> detalle;
	
	public FacturaVO(){
		super();
		
		this.detalle = new ArrayList<FacturaDetalleVO>();
	}
	
	public void copiar(FacturaVO t){
		
		super.copiar(t);
		
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
	
	
	
}
