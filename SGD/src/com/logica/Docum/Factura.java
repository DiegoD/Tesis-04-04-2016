package com.logica.Docum;

import java.util.ArrayList;

import com.logica.ProcesoInfo;
import com.valueObject.Docum.FacturaDetalleVO;
import com.valueObject.Docum.FacturaVO;


public class Factura extends DatosDocum{

	private ProcesoInfo procesoInfo;
	
	ArrayList<FacturaDetalle> detalle;
	
	public Factura(){
		super();
		
		this.procesoInfo = new ProcesoInfo(0,"No Asignado");
	}
	
	public Factura(FacturaVO t){
		
		super(t);
		
		this.detalle = new ArrayList<FacturaDetalle>();
		
		this.procesoInfo = new ProcesoInfo(t.getCodProceso(), t.getDescProceso());
		
		this.setCuenta(new CuentaInfo(t.getCodCuenta(), t.getNomCuenta(), t.getCodMoneda(), t.isNacional()));
		
		
		FacturaDetalle aux;
		for (FacturaDetalleVO detVO : t.getDetalle()) {
			
			aux = new FacturaDetalle(detVO);
			
			this.detalle.add(aux);
		}
	}
		
	
	public FacturaVO retornarVO(){
			
		
		FacturaVO aux = new FacturaVO();
		
		aux.copiar(super.retornarDatosDocumVO());
				
		aux.setCodProceso(this.procesoInfo.getCodProceso());
		aux.setDescProceso(this.procesoInfo.getDescProceso());
		aux.setCodCtaInd("0");
		aux.setFecValor(aux.getFecValor());
		aux.setFecDoc(aux.getFecDoc());
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		
		aux.setCodEmp(this.getCodEmp());
		
		FacturaDetalleVO auxDet;
		for (FacturaDetalle lin : detalle) {
			auxDet = new FacturaDetalleVO();
			
			auxDet =  lin.retornarVO();
			
			aux.getDetalle().add(auxDet);
		}
	
		return aux;
	}
	

	public ArrayList<FacturaDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(ArrayList<FacturaDetalle> detalle) {
		this.detalle = detalle;
	}

	public ProcesoInfo getProcesoInfo() {
		return procesoInfo;
	}

	public void setProcesoInfo(ProcesoInfo procesoInfo) {
		this.procesoInfo = procesoInfo;
	}
	
	
}
