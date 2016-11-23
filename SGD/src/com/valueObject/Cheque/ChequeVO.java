package com.valueObject.Cheque;

import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.valueObject.Docum.DatosDocumVO;

public class ChequeVO extends DatosDocumVO{

	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
	
	public ChequeVO(){
		
	}
	
	
	public ChequeVO(BancoInfo banco, CuentaBcoInfo cuentaBanco) {
		super();
		this.banco = banco;
		this.cuentaBanco = cuentaBanco;
	}


	public BancoInfo getBanco() {
		return banco;
	}
	public void setBanco(BancoInfo banco) {
		this.banco = banco;
	}
	public CuentaBcoInfo getCuentaBanco() {
		return cuentaBanco;
	}
	public void setCuentaBanco(CuentaBcoInfo cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}
	
	
}
