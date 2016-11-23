package com.valueObject.Deposito;

import com.valueObject.Cheque.ChequeVO;

public class DepositoDetalleVO {
	
	private ChequeVO chequeVO;
	private long nroTrans;
	
	public DepositoDetalleVO(){
		
	}

	public ChequeVO getChequeVO() {
		return chequeVO;
	}

	public void setChequeVO(ChequeVO chequeVO) {
		this.chequeVO = chequeVO;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}
	
	

}
