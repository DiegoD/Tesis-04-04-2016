package com.logica.Docum;

import com.valueObject.Docum.DatosDocumVO;
import com.valueObject.Docum.DocumSaldoVO;

public class DocumSaldo extends DatosDocum{

	private String codBco;
	private String codCtaBco;
	private String movimiento;
	
	private String codDocumRef;
	private String serieDocumRef;
	private int nroDocumRef;
	private int signo;
	
	public DocumSaldo(){
		
		super();
	}
	
	public DocumSaldo(DatosDocumVO d){
		
		super(d);
	}
	
	public DocumSaldo(DocumSaldoVO d){
		
		super(d);
		
		this.codBco = d.getCodBco();
		this.codCtaBco = d.getCodCtaBco();
		this.movimiento = d.getMovimiento();
		
		this.codDocumRef = d.getCodDocumRef();
		this.serieDocumRef = d.getSerieDocumRef();
		this.nroDocumRef = Integer.parseInt(d.getNroDocumRef());
		
		this.signo = d.getSigno();
	}
	

	public DocumSaldoVO retornarDocumSaldoVO(){
			
		DocumSaldoVO aux = new DocumSaldoVO();
		
		
		aux.setFecDoc(this.getFecDoc());
		aux.setCodDocum(this.getCodDocum());
		aux.setSerieDocum(this.getSerieDocum());
		aux.setNroDocum(String.valueOf(this.getNroDocum()));
		aux.setCodEmp(this.getCodDocum());
		
		aux.setNomMoneda(this.getMoneda().getDescripcion());
		aux.setCodMoneda(this.getMoneda().getCodMoneda());
		
		aux.setNomTitular(this.getTitInfo().getNombre());
		aux.setCodTitular(this.getTitInfo().getCodigo());
		
		aux.setImpTotMn(this.getImpTotMn());
		aux.setImpTotMo(this.getImpTotMo());
		aux.setTcMov(this.getTcMov());
		aux.setFecValor(this.getFecValor());
		
		aux.setReferencia(this.getReferencia());
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		aux.setNroTrans(this.getNroTrans());
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodBco(this.getCodBco());
		aux.setCodCtaBco(this.getCodCtaBco());
		aux.setMovimiento(this.getMovimiento());
		
		aux.setCodDocumRef(this.getCodDocumRef());
		aux.setSerieDocumRef(this.getSerieDocumRef());
		aux.setNroDocumRef(String.valueOf(this.getNroDocumRef()));
		
		aux.setSigno(this.getSigno());
		
		
		return aux;
	}
	
	public String getCodBco() {
		return codBco;
	}
	public void setCodBco(String codBco) {
		this.codBco = codBco;
	}
	public String getCodCtaBco() {
		return codCtaBco;
	}
	public void setCodCtaBco(String codCtaBco) {
		this.codCtaBco = codCtaBco;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getCodDocumRef() {
		return codDocumRef;
	}
	public void setCodDocumRef(String codDocumRef) {
		this.codDocumRef = codDocumRef;
	}
	public String getSerieDocumRef() {
		return serieDocumRef;
	}
	public void setSerieDocumRef(String serieDocumRef) {
		this.serieDocumRef = serieDocumRef;
	}
	public int getNroDocumRef() {
		return nroDocumRef;
	}
	public void setNroDocumRef(int nroDocumRef) {
		this.nroDocumRef = nroDocumRef;
	}

	public int getSigno() {
		return signo;
	}

	public void setSigno(int signo) {
		this.signo = signo;
	}
	
	
}
