package com.logica.Docum;

import com.valueObject.Docum.FacturaDetalleVO;

public class FacturaDetalle extends DocumDetalle{

	public FacturaDetalle(FacturaDetalleVO t) {
		super(t);
	}
	
	public FacturaDetalle() {
		super();
	}
	
	public FacturaDetalleVO retornarVO(){
	
		
	FacturaDetalleVO aux = new FacturaDetalleVO();
		
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodProceso(this.getCodProceso());
		aux.setDescProceso(this.getDescProceso());
		
		aux.setFecDoc(this.getFecDoc());
		aux.setFecValor(this.getFecValor());
		aux.setCodDocum(this.getCodDocum());
		aux.setSerieDocum(this.getSerieDocum());
		aux.setNroDocum(String.valueOf(this.getNroDocum()));
		aux.setCodEmp(this.getCodEmp());
		
		aux.setNomMoneda(this.getMoneda().getDescripcion());
		aux.setCodMoneda(this.getMoneda().getCodMoneda());
		
		aux.setSimboloMoneda(this.getMoneda().getSimbolo());
		
		aux.setNomTitular(this.getTitInfo().getNombre());
		aux.setCodTitular(this.getTitInfo().getCodigo());
		
		aux.setReferencia(this.getReferencia());
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		
		aux.setCodImpuesto(this.getImpuestoInfo().getCodImpuesto());
		aux.setNomImpuesto(this.getImpuestoInfo().getNomImpuesto());
		aux.setPorcentajeImpuesto(this.getImpuestoInfo().getPorcentaje());
		
		
		aux.setImpImpuMn(this.getImpImpuMn());
		aux.setImpImpuMo(this.getImpImpuMo());
		aux.setImpSubMn(this.getImpSubMn());
		aux.setImpSubMo(this.getImpSubMo());
		
		aux.setImpTotMn(this.getImpTotMn());
		aux.setImpTotMo(this.getImpTotMo());
		aux.setTcMov(this.getTcMov());
		
		aux.setNomRubro(this.getRubroInfo().getNomRubro());
		aux.setCodRubro(this.getRubroInfo().getCodRubro());
		
		aux.setEstadoGasto(this.getEstadoGasto());
		
		return aux;
	}
}
