package com.logica;

import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.Recibo;
import com.logica.IngresoCobro.IngresoCobro;

public class Cheque extends DatosDocum{

	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
	
	public Cheque (){
		
		this.banco = new BancoInfo();
		this.cuentaBanco = new CuentaBcoInfo();
	}
	
	public Cheque(BancoInfo banco, CuentaBcoInfo cuentaBanco) {
		super();
		this.banco = banco;
		this.cuentaBanco = cuentaBanco;
	}
	
	public void copiar(Cheque cheque){
		
		this.setFecDoc(cheque.getFecDoc());
		this.setFecValor(cheque.getFecValor());
		this.setCodDocum(cheque.getCodDocum());
		this.setSerieDocum(cheque.getSerieDocum());
		this.setNroDocum(cheque.getNroDocum());
		this.setCodEmp(cheque.getCodEmp());
		this.setReferencia(cheque.getReferencia());
		
		this.setImpTotMn(cheque.getImpTotMn());
		this.setImpTotMo(cheque.getImpTotMo());
		this.setTcMov(cheque.getTcMov());
		
		this.setNroTrans(cheque.getNroTrans());
		this.setUsuarioMod(cheque.getUsuarioMod());
		
		if(cheque.getFechaMod() !=null)
			this.setFechaMod(cheque.getFechaMod());
		this.setOperacion(cheque.getOperacion());
		
	}
	
	public Cheque convierteIng(IngresoCobro ing){
		
		Cheque cheque = new Cheque();
		
		cheque.setCodDocum(ing.getCodDocRef());
		cheque.setSerieDocum(ing.getSerieDocRef());
		cheque.setNroDocum(ing.getNroDocRef());
		cheque.setCodEmp(ing.getCodEmp());
		cheque.setMoneda(ing.getMoneda());
		cheque.setTitInfo(ing.getTitInfo());
		cheque.setImpTotMn(ing.getImpTotMn());
		cheque.setImpTotMo(ing.getImpTotMo());
		cheque.setCodCuentaInd(ing.getCodCuentaInd());
		cheque.setUsuarioMod(ing.getUsuarioMod());
		cheque.setOperacion(ing.getOperacion());
		cheque.setReferencia(ing.getReferencia());
		cheque.setNroTrans(ing.getNroTrans());
		cheque.setBanco(ing.getBancoInfo());
		cheque.setCuentaBanco(ing.getCuentaBcoInfo());
		cheque.setTcMov(ing.getTcMov());
		cheque.setFecDoc(ing.getFecDoc());
		cheque.setFecValor(ing.getFecValor());
		
		return cheque;
		
	}
	
	public Cheque convierteRec(Recibo rec){
		
		Cheque cheque = new Cheque();
		
		cheque.setCodDocum(rec.getCodDocRef());
		cheque.setSerieDocum(rec.getSerieDocRef());
		cheque.setNroDocum(rec.getNroDocRef());
		cheque.setCodEmp(rec.getCodEmp());
		cheque.setMoneda(rec.getMoneda());
		cheque.setTitInfo(rec.getTitInfo());
		cheque.setImpTotMn(rec.getImpTotMn());
		cheque.setImpTotMo(rec.getImpTotMo());
		cheque.setCodCuentaInd(rec.getCodCuentaInd());
		cheque.setUsuarioMod(rec.getUsuarioMod());
		cheque.setOperacion(rec.getOperacion());
		cheque.setReferencia(rec.getReferencia());
		cheque.setNroTrans(rec.getNroTrans());
		cheque.setBanco(rec.getBancoInfo());
		cheque.setCuentaBanco(rec.getCuentaBcoInfo());
		cheque.setTcMov(rec.getTcMov());
		cheque.setFecDoc(rec.getFecDoc());
		cheque.setFecValor(rec.getFecValor());
		
		return cheque;
		
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
