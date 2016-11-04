package com.logica.Docum;

import java.sql.Timestamp;

import com.logica.Auditoria;
import com.logica.Moneda;
import com.logica.MonedaInfo;
import com.valueObject.Docum.DatosDocumVO;
import com.valueObject.cliente.ClienteVO;

public class DatosDocum extends Auditoria{

	
	private Timestamp fecDoc;
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private String codEmp;
	private MonedaInfo moneda;
	private String referencia;
	private TitularInfo titInfo;
	private long nroTrans;
	
	private double impTotMn;
	private double impTotMo;
	private double tcMov;
	private Timestamp fecValor;
	
	
	
	private CuentaInfo cuenta;
	
	private String codCuentaInd; /*cuenta interna del sistema*/
	
	public DatosDocum(){

		this.moneda = new MonedaInfo();
		this.titInfo = new TitularInfo();
		this.cuenta = new CuentaInfo();
	}
	
	public DatosDocum(DatosDocumVO d){
		
		this.fecDoc = d.getFecDoc();
		this.codDocum = d.getCodDocum();
		this.serieDocum = d.getSerieDocum();
		this.nroDocum = d.getNroDocum();
		this.codEmp = d.getCodEmp();
		this.cuenta = new CuentaInfo(d.getCodCuenta(), d.getNomCuenta());
		
		this.moneda = new MonedaInfo(d.getCodMoneda(), d.getNomMoneda(), d.getSimboloMoneda(), d.isNacional());
		
		this.referencia = d.getReferencia();
		this.nroTrans = d.getNroTrans();
		
		this.titInfo = new TitularInfo(d.getCodTitular(), d.getNomTitular());
		
		this.impTotMn = d.getImpTotMn();
		this.impTotMo = d.getImpTotMo();
		this.tcMov = d.getTcMov();
		this.fecValor = d.getFecValor();
		
		this.setFechaMod(d.getFechaMod());
		this.setUsuarioMod(d.getUsuarioMod());
		this.setOperacion(d.getOperacion());
		this.codCuentaInd = d.getCodCtaInd();
		
	}
	
	public DatosDocumVO retornarDatosDocumVO(){
			
		DatosDocumVO aux = new DatosDocumVO();
		aux.setFecDoc(this.fecDoc);
		aux.setCodDocum(this.codDocum);
		aux.setSerieDocum(this.serieDocum);
		aux.setNroDocum(this.nroDocum);
		aux.setCodEmp(this.codDocum);
		
		aux.setNomMoneda(this.moneda.getDescripcion());
		aux.setCodMoneda(this.moneda.getCodMoneda());
		aux.setSimboloMoneda(this.moneda.getSimbolo());
		
		aux.setNomTitular(this.titInfo.getNombre());
		aux.setCodTitular(this.titInfo.getCodigo());
		aux.setTipo(this.titInfo.getTipo());
		
		aux.setImpTotMn(this.impTotMn);
		aux.setImpTotMo(this.impTotMo);
		aux.setTcMov(this.tcMov);
		aux.setFecValor(this.fecValor);
		
		aux.setReferencia(this.referencia);
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		aux.setNroTrans(this.nroTrans);
		
		if(this.cuenta.getCodCuenta() != null){
			aux.setCodCuenta(this.cuenta.getCodCuenta());
			aux.setNomCuenta(this.cuenta.getNomCuenta());
		}
		
		aux.setCodCtaInd(this.codCuentaInd);
		
		return aux;
	}

	public Timestamp getFecDoc() {
		return fecDoc;
	}

	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}

	public String getCodDocum() {
		return codDocum;
	}

	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}

	public String getSerieDocum() {
		return serieDocum;
	}

	public void setSerieDocum(String serieDocum) {
		this.serieDocum = serieDocum;
	}

	public int getNroDocum() {
		return nroDocum;
	}

	public void setNroDocum(int nroDocum) {
		this.nroDocum = nroDocum;
	}

	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}


	public MonedaInfo getMoneda() {
		return moneda;
	}

	
	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public TitularInfo getTitInfo() {
		return titInfo;
	}

	public void setTitInfo(TitularInfo titInfo) {
		this.titInfo = titInfo;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}
	
	public double getImpTotMn() {
		return impTotMn;
	}


	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
	}


	public double getImpTotMo() {
		return impTotMo;
	}


	public void setImpTotMo(double impTotMo) {
		this.impTotMo = impTotMo;
	}


	public double getTcMov() {
		return tcMov;
	}


	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}


	public Timestamp getFecValor() {
		return fecValor;
	}


	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
	}
	
	public CuentaInfo getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaInfo cuenta) {
		this.cuenta = cuenta;
	}

	public String getCodCuentaInd() {
		return codCuentaInd;
	}

	public void setCodCuentaInd(String codCuentaInd) {
		this.codCuentaInd = codCuentaInd;
	}
	
	
}
