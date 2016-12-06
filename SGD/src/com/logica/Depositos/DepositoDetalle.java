package com.logica.Depositos;

import com.logica.Cheque;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Deposito.DepositoDetalleVO;

public class DepositoDetalle {
	
	
	private Cheque cheque;
	private long nroTrans;
	private int linea;
	
	public DepositoDetalle(){
		
	}
	
	public DepositoDetalleVO retornarDepositoDetalleVO(DepositoDetalle detalle){
		
		DepositoDetalleVO detalleVO = new DepositoDetalleVO();
		
		detalleVO.setCodBanco(detalle.getCheque().getBanco().getCodBanco());
		detalleVO.setNomBanco(detalle.getCheque().getBanco().getNomBanco());
		detalleVO.setCodCuenta(detalle.getCheque().getCuentaBanco().getCodCuenta());
		detalleVO.setNomCuenta(detalle.getCheque().getCuentaBanco().getNomCuenta());
		detalleVO.setCodMoneda(detalle.getCheque().getMoneda().getCodMoneda());
		detalleVO.setNomMoneda(detalle.getCheque().getMoneda().getDescripcion());
		detalleVO.setSimboloMoneda(detalle.getCheque().getMoneda().getSimbolo());
		detalleVO.setNacional(detalle.getCheque().getMoneda().isNacional());
		detalleVO.setCodCtaInd(detalle.getCheque().getCodCuentaInd());
		detalleVO.setCodDocum(detalle.getCheque().getCodDocum());
		detalleVO.setSerieDocum(detalle.getCheque().getSerieDocum());
		detalleVO.setNroDocum(detalle.getCheque().getNroDocum());
		detalleVO.setCodEmp(detalle.getCheque().getCodEmp());
		detalleVO.setFecDoc(detalle.getCheque().getFecDoc());
		detalleVO.setFecValor(detalle.getCheque().getFecValor());
		detalleVO.setImpTotMn(detalle.getCheque().getImpTotMn());
		detalleVO.setImpTotMo(detalle.getCheque().getImpTotMo());
		detalleVO.setCodTitular(detalle.getCheque().getTitInfo().getCodigo());
		detalleVO.setNomTitular(detalle.getCheque().getTitInfo().getNombre());
		detalleVO.setNroTrans(detalle.getNroTrans());
		detalleVO.setReferencia(detalle.getCheque().getReferencia());
		detalleVO.setTcMov(detalle.getCheque().getTcMov());
		
		detalleVO.setNroTrans(detalle.getNroTrans());
		
		return detalleVO;
	}
	


	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	
	
}
