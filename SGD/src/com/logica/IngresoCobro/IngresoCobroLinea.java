package com.logica.IngresoCobro;

import com.logica.Docum.DocumDetalle;
import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;

public class IngresoCobroLinea extends DocumDetalle{

	public IngresoCobroLinea(IngresoCobroDetalleVO t) {
		super(t);
	}
	
	public IngresoCobroLinea() {
		super();
	}

public IngresoCobroDetalleVO retornarDatosDocumVO(){
	
		
		IngresoCobroDetalleVO aux = new IngresoCobroDetalleVO();
		
		
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
		aux.setNroTrans(this.getNroTrans());
		
		aux.setNomMoneda(this.getMoneda().getDescripcion());
		aux.setCodMoneda(this.getMoneda().getCodMoneda());
		
		aux.setSimboloMoneda(this.getMoneda().getSimbolo());
		aux.setNacional(this.getMoneda().isNacional());
		
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
		
		aux.setNroTrans(this.getNroTrans());
		
		return aux;
	}
}
