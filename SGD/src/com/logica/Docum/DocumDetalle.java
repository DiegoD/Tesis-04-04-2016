package com.logica.Docum;

import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;


public abstract class DocumDetalle extends DatosDocum{
	
	private String codProceso;
	private String referencia;
	
	private double impImpuMn;
	private double impImpuMo ;
	private double impSubMn ;
	private double impSubMo ;
	private double impTotMnd ;
	private double impTtotMod ;
	private double tcMov;
	private CuentaInfo cuenta;
	private RubroInfo rubroInfo;
	private String codCuentaInd; /*cuenta interna del sistema*/
	
	private ImpuestoInfo impuestoInfo;
	
	private int linea;
	
	public DocumDetalle(){
		super();
		this.cuenta = new CuentaInfo();
		this.impuestoInfo = new ImpuestoInfo();
		this.rubroInfo = new RubroInfo();
	}
	
	public DocumDetalle(DocumDetalleVO t){
		
		super(t);
		
		this.impuestoInfo = new ImpuestoInfo(t.getCodImpuesto(), t.getNomImpuesto(), t.getPorcentajeImpuesto());
		
		this.impImpuMn = t.getImpImpuMn();
		this.impImpuMo = t.getImpImpuMo();
		this.impSubMn = t.getImpSubMn();
		this.impSubMo = t.getImpSubMo();
		this.impTotMnd = t.getImpTotMn();
		this.impTtotMod = t.getImpTtotMo();
		this.tcMov = t.getTcMov();
		this.cuenta = new CuentaInfo(t.getCodCuenta(), t.getNomCuenta());
		this.codProceso = t.getCodProceso();
		this.rubroInfo = new RubroInfo(t.getCodRubro(), t.getNomRubro());
		this.codCuentaInd = t.getCodCtaInd();
		this.linea = t.getLinea();
		
	}
	
	public IngresoCobroDetalleVO retornarDatosDocumVO(){
		
		IngresoCobroDetalleVO aux = new IngresoCobroDetalleVO();
		
		aux.setFecDoc(this.getFecDoc());
		aux.setCodDocum(this.getCodDocum());
		aux.setSerieDocum(this.getSerieDocum());
		aux.setNroDocum(this.getNroDocum());
		aux.setCodEmp(this.getCodEmp());
		
		aux.setNomMoneda(this.getMoneda().getDescripcion());
		aux.setCodMoneda(this.getMoneda().getCodMoneda());
		
		aux.setNomTitular(this.getTitInfo().getNombre());
		aux.setCodTitular(this.getTitInfo().getCodigo());
		
		aux.setReferencia(this.getReferencia());
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		
		aux.setCodImpuesto(impuestoInfo.getCodImpuesto());
		aux.setNomImpuesto(impuestoInfo.getNomImpuesto());
		aux.setPorcentajeImpuesto(impuestoInfo.getPorcentaje());
		
		
		aux.setImpImpuMn(this.impImpuMn);
		aux.setImpImpuMo(this.impImpuMo);
		aux.setImpSubMn(this.impSubMn);
		aux.setImpSubMo(this.impSubMo);
		
		aux.setImpTotMn(this.impTotMnd);
		aux.setImpTtotMo(this.impTtotMod);
		aux.setTcMov(this.tcMov);
		
		
		aux.setCodCuenta(this.cuenta.getCodCuenta());
		aux.setNomCuenta(this.cuenta.getNomCuenta());
		
		aux.setNomRubro(this.rubroInfo.getNomRubro());
		aux.setCodRubro(this.rubroInfo.getCodRubro());
		
		return aux;
	}


	public double getImpImpuMn() {
		return impImpuMn;
	}

	public void setImpImpuMn(double impImpuMn) {
		this.impImpuMn = impImpuMn;
	}

	public double getImpImpuMo() {
		return impImpuMo;
	}

	public void setImpImpuMo(double impImpuMo) {
		this.impImpuMo = impImpuMo;
	}

	public double getImpSubMn() {
		return impSubMn;
	}

	public void setImpSubMn(double impSubMn) {
		this.impSubMn = impSubMn;
	}

	public double getImpSubMo() {
		return impSubMo;
	}

	public void setImpSubMo(double impSubMo) {
		this.impSubMo = impSubMo;
	}

	public double getImpTotMn() {
		return impTotMnd;
	}

	public void setImpTotMn(double impTotMn) {
		this.impTotMnd = impTotMn;
	}

	public double getImpTtotMo() {
		return impTtotMod;
	}

	public void setImpTtotMo(double impTtotMo) {
		this.impTtotMod = impTtotMo;
	}

	public double getTcMov() {
		return tcMov;
	}

	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}

	public CuentaInfo getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaInfo cuenta) {
		this.cuenta = cuenta;
	}


	public String getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public ImpuestoInfo getImpuestoInfo() {
		return impuestoInfo;
	}

	public void setImpuestoInfo(ImpuestoInfo impuestoInfo) {
		this.impuestoInfo = impuestoInfo;
	}

	public RubroInfo getRubroInfo() {
		return rubroInfo;
	}

	public void setRubroInfo(RubroInfo rubroInfo) {
		this.rubroInfo = rubroInfo;
	}

	public String getCodCuentaInd() {
		return codCuentaInd;
	}

	public void setCodCuentaInd(String codCuentaInd) {
		this.codCuentaInd = codCuentaInd;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	

	
	
}
