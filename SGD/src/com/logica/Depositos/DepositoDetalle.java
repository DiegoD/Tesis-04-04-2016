package com.logica.Depositos;

import com.logica.Cheque;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Deposito.DepositoDetalleVO;

public class DepositoDetalle {
	
	
	private Cheque cheque;
	private long nroTrans;
	
	public DepositoDetalle(){
		
	}
	
	public DepositoDetalleVO retornarDepositoDetalleVO(DepositoDetalle detalle){
		
		DepositoDetalleVO detalleVO = new DepositoDetalleVO();
		
		ChequeVO cheque = new ChequeVO();
		cheque.setBanco(detalle.getCheque().getBanco());
		cheque.setCodCtaInd(detalle.getCheque().getCodCuentaInd());
		cheque.setCodDocum(detalle.getCheque().getCodDocum());
		cheque.setCodEmp(detalle.getCheque().getCodEmp());
		cheque.setCodMoneda(detalle.getCheque().getMoneda().getCodMoneda());
		cheque.setCuentaBanco(detalle.getCheque().getCuentaBanco());
		cheque.setFecDoc(detalle.getCheque().getFecDoc());
		cheque.setFecValor(detalle.getCheque().getFecValor());
		cheque.setImpTotMn(detalle.getCheque().getImpTotMn());
		cheque.setImpTotMo(detalle.getCheque().getImpTotMo());
		cheque.setNacional(detalle.getCheque().getMoneda().isNacional());
		cheque.setNomCuenta(detalle.getCheque().getCuenta().getNomCuenta());
		cheque.setNomMoneda(detalle.getCheque().getMoneda().getDescripcion());
		cheque.setNomTitular(detalle.getCheque().getTitInfo().getNombre());
		cheque.setNroDocum(detalle.getCheque().getNroDocum());
		cheque.setNroTrans(detalle.getNroTrans());
		cheque.setOperacion(detalle.getCheque().getOperacion());
		cheque.setReferencia(detalle.getCheque().getReferencia());
		cheque.setSerieDocum(detalle.getCheque().getSerieDocum());
		cheque.setSimboloMoneda(detalle.getCheque().getMoneda().getSimbolo());
		cheque.setTcMov(detalle.getCheque().getTcMov());
		cheque.setTipo(detalle.getCheque().getTitInfo().getTipo());
		cheque.setUsuarioMod(detalle.getCheque().getUsuarioMod());
		cheque.setCodCuenta(detalle.getCheque().getCuenta().getCodCuenta());
		cheque.setCodTitular(detalle.getCheque().getTitInfo().getCodigo());
		cheque.setFechaMod(detalle.getCheque().getFechaMod());
		
		detalleVO.setChequeVO(cheque);
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
	
	
	
}
